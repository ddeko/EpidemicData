package plbtw.epidemicdata.fragment.pickerview;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import plbtw.epidemicdata.api.ApiLokasi;
import plbtw.epidemicdata.api.ServiceGenerator;
import plbtw.epidemicdata.model.KabupatenModel;
import plbtw.epidemicdata.model.KabupatenResponse;
import plbtw.epidemicdata.model.KecamatanModel;
import plbtw.epidemicdata.model.KecamatanResponse;
import plbtw.epidemicdata.views.PickerView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public abstract class KecamatanPicker extends BasePickerView {
    private ArrayList<String> kecamatan;
    private ArrayList<KecamatanModel> listKecamatan;

    private ApiLokasi apiLokasi = null;

    private String idKabupaten;

    public KecamatanPicker(Context context, String idKabupaten) {
        super(context);

        this.idKabupaten = idKabupaten;
        kecamatan = new ArrayList<>();

        apiLokasi = ServiceGenerator
                .createService(ApiLokasi.class);
    }

    @Override
    protected void loadData() {

        if(kecamatan.isEmpty() || pickerObjects.isEmpty()) {
            visibleProgressBar();
            Call<KecamatanResponse> call = null;
            call = apiLokasi.getKecamatanList("1", "1000", idKabupaten);
            call.enqueue(new Callback<KecamatanResponse>() {
                @Override
                public void onResponse(Response<KecamatanResponse> response,
                                       Retrofit retrofit) {
                    goneProgressBar();
                    if (2 == response.code() / 100) {


                        final KecamatanResponse kecamatanResponse = response.body();
                        listKecamatan = kecamatanResponse.getListKecamatan();
                        kecamatan.clear();
                        pickerObjects.clear();
                        String states;
                        int index = 0;
                        for (int i = 0; i < listKecamatan.size(); i++) {
                            states = listKecamatan.get(i).getNama_kecamatan();

                            kecamatan.add(states);
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
                    Toast.makeText(context, "Failed to load kecamatan", Toast.LENGTH_LONG).show();
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
        return "Pilih Nama Kecamatan";
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
            String state = kecamatan.get(countryIndex);
            onPickDone(state, listKecamatan.get(countryIndex).getId_kecamatan());
        }
    }

    // restore selected object, this must be implemented on each subclass
    private void restoreSelectorsState() {
        String lastProvince = lastSelectedProvince();
        if(lastProvince == null || lastProvince.isEmpty())
            return;

        int indexToFind = kecamatan.indexOf(lastProvince);
        for(PickerView.PickerObject object : pickerObjects) {
            if((indexToFind >= 0 && indexToFind == object.getExtIndex()) || lastProvince.equals(object.getTitle())) {
                object.setIsSelected(true);
                break;
            }
        }
    }

    protected abstract void onPickDone(String kecamatan, String id);
    protected abstract String lastSelectedProvince();

    private void showErrorMessage() {
        Toast.makeText(context, "gagal", Toast.LENGTH_SHORT).show();

    }
}

