package plbtw.epidemicdata.activitiy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.api.API;
import plbtw.epidemicdata.api.ServiceGenerator;
import plbtw.epidemicdata.callbacks.OnActionbarListener;
import plbtw.epidemicdata.fragment.dialogs.LocationDialog;
import plbtw.epidemicdata.model.DeletePenyakitResponse;
import plbtw.epidemicdata.model.DiseaseModel;
import plbtw.epidemicdata.model.PutPenyakitBody;
import plbtw.epidemicdata.model.PutPenyakitResponse;
import plbtw.epidemicdata.model.TipePenyakitModel;
import plbtw.epidemicdata.model.TipePenyakitResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class EditPenyakitActivity extends BaseActivity {

    public static int REQUEST_CODE_EDIT_PENYAKIT = 1;
    private static int RESULT_CODE_PILIH_LOKASI = 1;
    private Toolbar toolbar;

    public String id_desa = "1";
    public String nama_desa;

    @Bind(R.id.spinnerTipePenyakit)
    Spinner spinnerTipePenyakit;

    private List<TipePenyakitModel> tipePenyakitModelList;

    private String id_user;
    @Bind(R.id.form_item_name)
    EditText form_item_name;


    @Bind(R.id.form_item_location)
    TextView form_item_location;


    @OnClick(R.id.form_item_location)
    void pilihLokasi(View v) {
        LocationDialog locationDialog = new LocationDialog(EditPenyakitActivity.this, LocationDialog.REQUEST_EDIT);
        locationDialog.show(getSupportFragmentManager(), null);
    }


    @Bind(R.id.form_item_number)
    EditText form_item_number;

    @Bind(R.id.form_item_desc)
    EditText form_item_desc;

    @Bind(R.id.form_item_gejala)
    EditText form_item_gejala;

    @Bind(R.id.form_item_saran)
    EditText form_item_saran;

    @Bind(R.id.button_simpan)
    Button button_simpan;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    
    void editPenyakit()
    {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        Call<PutPenyakitResponse> call = null;
        PutPenyakitBody putPenyakitBody = new PutPenyakitBody();


        putPenyakitBody.setDeskripsi(form_item_desc.getText().toString());
        putPenyakitBody.setGejala(form_item_gejala.getText().toString());
        putPenyakitBody.setJumlah_penderita(form_item_number.getText().toString());
        putPenyakitBody.setNama_penyakit(form_item_name.getText().toString());
        putPenyakitBody.setSaran_penanganan(form_item_saran.getText().toString());
        putPenyakitBody.setId_user_app(id_user);
        putPenyakitBody.setId_desa(id_desa);
        putPenyakitBody.setId_tipe_penyakit(tipePenyakitModelList.get(spinnerTipePenyakit.getSelectedItemPosition()).getId_tipe_penyakit());

        Toast.makeText(EditPenyakitActivity.this, tipePenyakitModelList.get(spinnerTipePenyakit.getSelectedItemPosition()).getId_tipe_penyakit(), Toast.LENGTH_SHORT).show();

        call = api.editPenyakit(diseaseData.getId_penyakit(), putPenyakitBody);
        call.enqueue(new Callback<PutPenyakitResponse>() {
            @Override
            public void onResponse(Response<PutPenyakitResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (2 == response.code() / 100) {
                    finish();
                } else {
                    Toast.makeText(EditPenyakitActivity.this, "Error edit penyakit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                dialog.dismiss();
                Toast.makeText(EditPenyakitActivity.this, "Failed edit penyakit", Toast.LENGTH_SHORT).show();
            }
        });


    }



    private RelativeLayout btnGetMap;

    public DiseaseModel diseaseData;

    private API api;
    private SharedPreferences sharedPreferences;
    private final String name = "loginUser";
    private static final int mode = Activity.MODE_PRIVATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(name, mode);
        id_user = sharedPreferences.getString("id_user","");
        Intent i = getIntent();
        Bundle b = i.getExtras();


        diseaseData = (DiseaseModel) i.getSerializableExtra("diseaseModel");

        api = ServiceGenerator.createService(API.class);

        getTipePenyakitList();

        setRightIcon(0);
        setActionBarTitle("Save!");
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    



         btnGetMap = (RelativeLayout) findViewById(R.id.get_map_btn);





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
                editPenyakit();
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_edit_ed;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void updateUI() {
        fetchData();


    }

    public void fetchData(){


        form_item_name.setText(diseaseData.getNama_penyakit());
//        form_item_type.setText(diseaseData.getNama_tipe_penyakit());
        form_item_number.setText(diseaseData.getJumlah_penderita());
        form_item_desc.setText(diseaseData.getDeskripsi());
        form_item_gejala.setText(diseaseData.getGejala());
        form_item_saran.setText(diseaseData.getSaran_penanganan());
        form_item_location.setText(diseaseData.getNama_desa());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_CODE_PILIH_LOKASI == resultCode && REQUEST_CODE_EDIT_PENYAKIT == requestCode) {


            Bundle bundle = data.getExtras();
            if (bundle != null) {
                id_desa = bundle.getString("id_desa");
                nama_desa = bundle.getString("nama_desa");
                form_item_location.setText(nama_desa);
               // Toast.makeText(EditPenyakitActivity.this, id_desa, Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void getTipePenyakitList() {
        progressBar.setVisibility(View.VISIBLE);
        Call<TipePenyakitResponse> call = null;
        call = api.getTipePenyakitList("1", "1000");
        call.enqueue(new Callback<TipePenyakitResponse>() {
            @Override
            public void onResponse(Response<TipePenyakitResponse> response,
                                   Retrofit retrofit) {
                progressBar.setVisibility(View.GONE);
                if (2 == response.code() / 100) {
                    showTipePenyakitList(response);


                } else {
                    showErrorMessage();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "failed get tipe penyakit", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
        //  Snackbar.make(coordinatorLayout, "Error to Load Content", Snackbar.LENGTH_LONG).show();
    }

    private void showTipePenyakitList
            (Response<TipePenyakitResponse> response) {

        final TipePenyakitResponse tipePenyakitResponse = response.body();
        tipePenyakitModelList = tipePenyakitResponse.getListTipePenyakit();

        String[] array = new String[tipePenyakitModelList.size()];
        for (int i = 0; i < tipePenyakitModelList.size(); i++)
            array[i] = tipePenyakitModelList.get(i).getNama_tipe_penyakit();
        ArrayAdapter<String> adapterTipePenyakit = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, R.id.text1, array);
        adapterTipePenyakit.setDropDownViewResource(R.layout.spinner_item);
        spinnerTipePenyakit.setAdapter(adapterTipePenyakit);

        int spinnerPosition = adapterTipePenyakit.getPosition(diseaseData.getNama_tipe_penyakit());
        spinnerTipePenyakit.setSelection(spinnerPosition);

    }

    public void setLokasi(String desa){

        form_item_location.setText(desa);

    }



}
