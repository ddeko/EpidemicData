package plbtw.epidemicdata.fragment.pickerview;

import android.content.Context;

import android.widget.Toast;

import java.util.ArrayList;


import plbtw.epidemicdata.api.ApiLokasi;
import plbtw.epidemicdata.api.ServiceGenerator;
import plbtw.epidemicdata.model.ProvinsiModel;
import plbtw.epidemicdata.model.ProvinsiResponse;
import plbtw.epidemicdata.views.PickerView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public abstract class ProvincePicker extends BasePickerView {
    private ArrayList<String> provinces;
    private ArrayList<ProvinsiModel> listprovinsi;

    private ApiLokasi apiLokasi = null;

    public ProvincePicker(Context context) {
        super(context);

        provinces = new ArrayList<>();

        apiLokasi = ServiceGenerator
                .createService(ApiLokasi.class);
    }

    @Override
    protected void loadData() {

        if(provinces.isEmpty() || pickerObjects.isEmpty()) {
            visibleProgressBar();
            Call<ProvinsiResponse> call = null;
            call = apiLokasi.getProvinsiList("1", "1000");
            call.enqueue(new Callback<ProvinsiResponse>() {
                @Override
                public void onResponse(Response<ProvinsiResponse> response,
                                       Retrofit retrofit) {
                    goneProgressBar();
                    if (2 == response.code() / 100) {
                        final ProvinsiResponse provinsiResponse = response.body();
                        listprovinsi = provinsiResponse.getListProvinsi();

                        provinces.clear();
                        pickerObjects.clear();
                        String states;
                        int index = 0;
                        for (int i = 0; i < listprovinsi.size(); i++) {
                            states = listprovinsi.get(i).getNama_provinsi();

                            provinces.add(states);
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
                    Toast.makeText(context, "Failed to load province", Toast.LENGTH_LONG).show();
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
        return "Pilih Nama Provinsi";
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
            String state = provinces.get(countryIndex);

            onPickDone(state, listprovinsi.get(countryIndex).getId_provinsi());
        }
    }

    // restore selected object, this must be implemented on each subclass
    private void restoreSelectorsState() {
        String lastProvince = lastSelectedProvince();
        if(lastProvince == null || lastProvince.isEmpty())
            return;

        int indexToFind = provinces.indexOf(lastProvince);
        for(PickerView.PickerObject object : pickerObjects) {
            if((indexToFind >= 0 && indexToFind == object.getExtIndex()) || lastProvince.equals(object.getTitle())) {
                object.setIsSelected(true);
                break;
            }
        }
    }

    protected abstract void onPickDone(String province, String id);
    protected abstract String lastSelectedProvince();

    private void showErrorMessage() {
        Toast.makeText(context, "gagal", Toast.LENGTH_SHORT).show();

    }
}

