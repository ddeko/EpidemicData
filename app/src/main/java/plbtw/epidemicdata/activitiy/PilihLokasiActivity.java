package plbtw.epidemicdata.activitiy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.adapter.RecyclerRedeemAdapter;
import plbtw.epidemicdata.api.API;
import plbtw.epidemicdata.api.ApiLokasi;
import plbtw.epidemicdata.api.ServiceGenerator;
import plbtw.epidemicdata.callbacks.OnActionbarListener;
import plbtw.epidemicdata.model.DesaModel;
import plbtw.epidemicdata.model.DesaResponse;
import plbtw.epidemicdata.model.DiseaseModel;
import plbtw.epidemicdata.model.KabupatenModel;
import plbtw.epidemicdata.model.KabupatenResponse;
import plbtw.epidemicdata.model.KecamatanModel;
import plbtw.epidemicdata.model.KecamatanResponse;
import plbtw.epidemicdata.model.ProvinsiModel;
import plbtw.epidemicdata.model.ProvinsiResponse;
import plbtw.epidemicdata.model.PutPenyakitBody;
import plbtw.epidemicdata.model.PutPenyakitResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class PilihLokasiActivity extends BaseActivity {

    private Toolbar toolbar;


    private static int RESULT_CODE_PILIH_LOKASI = 1;
    private String id_user;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private ApiLokasi apiLokasi = null;
    private RecyclerRedeemAdapter adapter;
    @Bind(R.id.contentRedeemLayout)
    LinearLayout contentRedeemLayout;


    @Bind(R.id.spinnerProvinsi)
    Spinner spinnerProvinsi;
    @Bind(R.id.spinnerKabupaten)
    Spinner spinnerKabupaten;
    @Bind(R.id.spinnerKecamatan)
    Spinner spinnerKecamatan;
    @Bind(R.id.spinnerDesa)
    Spinner spinnerDesa;

    @Bind(R.id.btnPilihDesa)
    Button btnPilihDesa;

    @OnClick(R.id.btnPilihDesa)
    void pilihDesa(View v) {
        Intent data = new Intent();
        data.putExtra("id_desa", desaModelList.get(spinnerDesa.getSelectedItemPosition()).getId_desa());
        data.putExtra("nama_desa", desaModelList.get(spinnerDesa.getSelectedItemPosition()).getNama_desa());

        setResult(RESULT_CODE_PILIH_LOKASI, data);

        finish();
    }

    private List<ProvinsiModel> provinsiModelList;
    private List<KabupatenModel> kabupatenModelList;
    private List<KecamatanModel> kecamatanModelList;
    private List<DesaModel> desaModelList;


    private RelativeLayout btnGetMap;

    public DiseaseModel diseaseData;

    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        apiLokasi = ServiceGenerator
                .createService(ApiLokasi.class);
        getProvinsiList();
    }

    @Override
    public void initView() {
    /*    toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

*/
    }


    @Override
    public void setUICallbacks() {
        setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                onBackPressed();
            }

            @Override
            public void onRightIconClick() {

            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_pilih_lokasi;
    }

    @Override
    public void updateUI() {
        fetchData();

    }

    public void fetchData() {


    }

    private void getProvinsiList() {
        Call<ProvinsiResponse> call = null;
        call = apiLokasi.getProvinsiList("1", "1000");
        call.enqueue(new Callback<ProvinsiResponse>() {
            @Override
            public void onResponse(Response<ProvinsiResponse> response,
                                   Retrofit retrofit) {
                if (2 == response.code() / 100) {
                    showProvinsiList(response);


                } else {
                    showErrorMessage();
                }
                progressBar.setVisibility(View.GONE);
                contentRedeemLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(coordinatorLayout, "Failed to Load Content",
                        Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                contentRedeemLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getKabupatenList(String id_provinsi) {
        Call<KabupatenResponse> call = null;
        call = apiLokasi.getKabupatenList("1", "1000", id_provinsi);
        call.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Response<KabupatenResponse> response,
                                   Retrofit retrofit) {
                if (2 == response.code() / 100) {
                    showKabupatenList(response);
                } else {
                    showErrorMessage();
                }
                progressBar.setVisibility(View.GONE);
                contentRedeemLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(coordinatorLayout, "Failed to Load Content",
                        Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                contentRedeemLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getKecamatanList(String id_kabupaten) {
        Call<KecamatanResponse> call = null;
        call = apiLokasi.getKecamatanList("1", "1000", id_kabupaten);
        call.enqueue(new Callback<KecamatanResponse>() {
            @Override
            public void onResponse(Response<KecamatanResponse> response,
                                   Retrofit retrofit) {
                if (2 == response.code() / 100) {
                    showKecamatanList(response);
                } else {
                    showErrorMessage();
                }
                progressBar.setVisibility(View.GONE);
                contentRedeemLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(coordinatorLayout, "Failed to Load Content",
                        Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                contentRedeemLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getDesaList(String id_kecamatan) {
        Call<DesaResponse> call = null;
        call = apiLokasi.getDesaList("1", "1000", id_kecamatan);
        call.enqueue(new Callback<DesaResponse>() {
            @Override
            public void onResponse(Response<DesaResponse> response,
                                   Retrofit retrofit) {
                if (2 == response.code() / 100) {
                    showDesaList(response);
                } else {
                    showErrorMessage();
                }
                progressBar.setVisibility(View.GONE);
                contentRedeemLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(coordinatorLayout, "Failed to Load Content",
                        Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                contentRedeemLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
        //  Snackbar.make(coordinatorLayout, "Error to Load Content", Snackbar.LENGTH_LONG).show();
    }

    private void showProvinsiList
            (Response<ProvinsiResponse> response) {

        final ProvinsiResponse provinsiResponse = response.body();
        provinsiModelList = provinsiResponse.getListProvinsi();

        String[] array = new String[provinsiModelList.size()];
        for (int i = 0; i < provinsiModelList.size(); i++)
            array[i] = provinsiModelList.get(i).getNama_provinsi();
        ArrayAdapter<String> adapterProvinsi = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, array);
        adapterProvinsi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvinsi.setAdapter(adapterProvinsi);

        spinnerProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
                getKabupatenList(provinsiModelList.get(position).getId_provinsi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void showKabupatenList
            (Response<KabupatenResponse> response) {

        final KabupatenResponse kabupatenResponse = response.body();
        kabupatenModelList = kabupatenResponse.getListKabupaten();

        String[] array = new String[kabupatenModelList.size()];
        for (int i = 0; i < kabupatenModelList.size(); i++)
            array[i] = kabupatenModelList.get(i).getNama_kabupaten();
        ArrayAdapter<String> adapterKabupaten = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, array);
        adapterKabupaten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKabupaten.setAdapter(adapterKabupaten);

        spinnerKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
                getKecamatanList(kabupatenModelList.get(position).getId_kabupaten());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void showKecamatanList
            (Response<KecamatanResponse> response) {

        final KecamatanResponse kecamatanResponse = response.body();
        kecamatanModelList = kecamatanResponse.getListKecamatan();

        String[] array = new String[kecamatanModelList.size()];
        for (int i = 0; i < kecamatanModelList.size(); i++)
            array[i] = kecamatanModelList.get(i).getNama_kecamatan();
        ArrayAdapter<String> adapterKecamatan = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, array);
        adapterKecamatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKecamatan.setAdapter(adapterKecamatan);

        spinnerKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
                getDesaList(kecamatanModelList.get(position).getId_kecamatan());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void showDesaList
            (Response<DesaResponse> response) {

        final DesaResponse desaResponse = response.body();
        desaModelList = desaResponse.getListDesa();

        String[] array = new String[desaModelList.size()];
        for (int i = 0; i < desaModelList.size(); i++)
            array[i] = desaModelList.get(i).getNama_desa();
        ArrayAdapter<String> adapterDesa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, array);
        adapterDesa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDesa.setAdapter(adapterDesa);


    }

}
