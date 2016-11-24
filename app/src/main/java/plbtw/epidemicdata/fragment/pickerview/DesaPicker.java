package plbtw.epidemicdata.fragment.pickerview;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import plbtw.epidemicdata.api.ApiLokasi;
import plbtw.epidemicdata.api.ServiceGenerator;
import plbtw.epidemicdata.model.DesaModel;
import plbtw.epidemicdata.model.DesaResponse;
import plbtw.epidemicdata.model.KecamatanModel;
import plbtw.epidemicdata.model.KecamatanResponse;
import plbtw.epidemicdata.views.PickerView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public abstract class DesaPicker extends BasePickerView {
    private ArrayList<String> desa;
    private ArrayList<DesaModel> listDesa;

    private ApiLokasi apiLokasi = null;

    private String idKecamatan;

    public DesaPicker(Context context, String idKecamatan) {
        super(context);

        this.idKecamatan = idKecamatan;
        desa = new ArrayList<>();

        apiLokasi = ServiceGenerator
                .createService(ApiLokasi.class);
    }

    @Override
    protected void loadData() {

        if(desa.isEmpty() || pickerObjects.isEmpty()) {
            visibleProgressBar();
            Call<DesaResponse> call = null;
            call = apiLokasi.getDesaList("1", "1000", idKecamatan);
            call.enqueue(new Callback<DesaResponse>() {
                @Override
                public void onResponse(Response<DesaResponse> response,
                                       Retrofit retrofit) {
                    goneProgressBar();
                    if (2 == response.code() / 100) {

                        final DesaResponse DesaResponse = response.body();
                        listDesa = DesaResponse.getListDesa();
                        desa.clear();
                        pickerObjects.clear();
                        String states;
                        int index = 0;
                        for (int i = 0; i < listDesa.size(); i++) {
                            states = listDesa.get(i).getNama_desa();

                            desa.add(states);
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
                    Toast.makeText(context, "Failed to load desa", Toast.LENGTH_LONG).show();
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
        return "Pilih Nama Desa";
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
            String state = desa.get(countryIndex);
            onPickDone(state, listDesa.get(countryIndex).getId_desa());
        }
    }

    // restore selected object, this must be implemented on each subclass
    private void restoreSelectorsState() {
        String lastProvince = lastSelectedProvince();
        if(lastProvince == null || lastProvince.isEmpty())
            return;

        int indexToFind = desa.indexOf(lastProvince);
        for(PickerView.PickerObject object : pickerObjects) {
            if((indexToFind >= 0 && indexToFind == object.getExtIndex()) || lastProvince.equals(object.getTitle())) {
                object.setIsSelected(true);
                break;
            }
        }
    }

    protected abstract void onPickDone(String desa, String id);
    protected abstract String lastSelectedProvince();

    private void showErrorMessage() {
        Toast.makeText(context, "gagal", Toast.LENGTH_SHORT).show();

    }
}

