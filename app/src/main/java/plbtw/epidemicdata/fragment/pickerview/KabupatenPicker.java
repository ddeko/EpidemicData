package plbtw.epidemicdata.fragment.pickerview;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import plbtw.epidemicdata.api.ApiLokasi;
import plbtw.epidemicdata.api.ServiceGenerator;
import plbtw.epidemicdata.model.KabupatenModel;
import plbtw.epidemicdata.model.KabupatenResponse;
import plbtw.epidemicdata.model.ProvinsiModel;
import plbtw.epidemicdata.model.ProvinsiResponse;
import plbtw.epidemicdata.views.PickerView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public abstract class KabupatenPicker extends BasePickerView {
    private ArrayList<String> kabupaten;
    private ArrayList<KabupatenModel> listKabupaten;

    private ApiLokasi apiLokasi = null;

    private String idProvinsi;

    public KabupatenPicker(Context context, String idProvinsi) {
        super(context);

        this.idProvinsi = idProvinsi;
        kabupaten = new ArrayList<>();

        apiLokasi = ServiceGenerator
                .createService(ApiLokasi.class);
    }

    @Override
    protected void loadData() {

        if(kabupaten.isEmpty() || pickerObjects.isEmpty()) {
            visibleProgressBar();
            Call<KabupatenResponse> call = null;
            call = apiLokasi.getKabupatenList("1", "1000", idProvinsi);
            call.enqueue(new Callback<KabupatenResponse>() {
                @Override
                public void onResponse(Response<KabupatenResponse> response,
                                       Retrofit retrofit) {
                    goneProgressBar();
                    if (2 == response.code() / 100) {
                        final KabupatenResponse kabupatenResponse = response.body();
                        listKabupaten = kabupatenResponse.getListKabupaten();

                        kabupaten.clear();
                        pickerObjects.clear();
                        String states;
                        int index = 0;
                        for (int i = 0; i < listKabupaten.size(); i++) {
                            states = listKabupaten.get(i).getNama_kabupaten();

                            kabupaten.add(states);
                            addPickerObject(index, index, states, null, false);
                            index++;
                        }

                        completeLoading();
                    } else {
                        showErrorMessage();
                    }

                }
                @Override
                public void onFailure(Throwable t) {
                    goneProgressBar();
                    Toast.makeText(context, "Failed to load kabupaten", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            resetSelectorsState();
            restoreSelectorsState();
            completeLoading();
        }
    }

    @Override
    protected String getPickerTitle() {
        return "Pilih Nama Kabupaten";
    }

    @Override
    protected String getPickerSubtitle() {
        return null;
    }

    @Override
    protected int getPickerMode() {
        return PickerView.PICKER_MODE_SINGLE_TAP;
    }

    @Override
    public void onPickDone(int pickerMode, ArrayList<PickerView.PickerObject> objects) {
        if(pickerMode == PickerView.PICKER_MODE_SINGLE_TAP) {
            int countryIndex = objects.get(0).getExtIndex();
            String state = kabupaten.get(countryIndex);
            onPickDone(state, listKabupaten.get(countryIndex).getId_kabupaten());
        }
    }

    // restore selected object, this must be implemented on each subclass
    private void restoreSelectorsState() {
        String lastProvince = lastSelectedProvince();
        if(lastProvince == null || lastProvince.isEmpty())
            return;

        int indexToFind = kabupaten.indexOf(lastProvince);
        for(PickerView.PickerObject object : pickerObjects) {
            if((indexToFind >= 0 && indexToFind == object.getExtIndex()) || lastProvince.equals(object.getTitle())) {
                object.setIsSelected(true);
                break;
            }
        }
    }

    protected abstract void onPickDone(String kabupaten, String id);
    protected abstract String lastSelectedProvince();

    private void showErrorMessage() {
        Toast.makeText(context, "gagal", Toast.LENGTH_SHORT).show();

    }
}

