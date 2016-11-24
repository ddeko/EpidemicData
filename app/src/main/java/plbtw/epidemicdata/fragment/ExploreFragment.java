package plbtw.epidemicdata.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import plbtw.epidemicdata.activitiy.ExploreDetailActivity;
import plbtw.epidemicdata.activitiy.MainActivity;
import plbtw.epidemicdata.activitiy.PilihLokasiActivity;
import plbtw.epidemicdata.adapter.ExploreAdapter;
import plbtw.epidemicdata.fragment.dialogs.FilterDialog;
import plbtw.epidemicdata.model.DiseaseModel;
import plbtw.epidemicdata.model.DiseaseResponse;
import plbtw.epidemicdata.R;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by DedeEko on 5/18/2016.
 */
public class ExploreFragment extends BaseFragment implements ExploreAdapter.ExploreAdapterListener{

    public static int REQUEST_CODE_PILIH_LOKASI = 1;
    private static int RESULT_CODE_PILIH_LOKASI = 1;
    MainActivity activity;

    private RecyclerView recyclerView;

    private ArrayList<DiseaseModel> listPenyakit;

    private ExploreAdapter adapter;


    @Bind(R.id.labelLokasi)
    TextView labelLokasi;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @OnClick(R.id.filter_button)
    void pilihLokasi(View v) {
//        Intent i = new Intent(activity, PilihLokasiActivity.class);
//        startActivityForResult(i, REQUEST_CODE_PILIH_LOKASI);
        FilterDialog filterDialog = new FilterDialog(this);
        filterDialog.show(activity.getSupportFragmentManager(), null);
    }
    @OnClick(R.id.resetBtn)
    void Reset(View v){
        labelLokasi.setText("Lokasi");
        fetchData();
    }


    private String id_desa;
    private String nama_desa;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_explore,
                container, false);
        ButterKnife.bind(this, v);
        recyclerView = (RecyclerView) v.findViewById(R.id.explore_list);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();

        listPenyakit = new ArrayList<>();

        adapter = new ExploreAdapter(listPenyakit, this, activity.getApplicationContext());


    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setUICallbacks() {

    }

    @Override
    public void updateUI() {

        setupActionBar();

    }

    @Override
    public String getPageTitle() {
        return "Explore";
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_explore;
    }

    private void setupActionBar() {
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.setDefaultActionbarIcon();
        mainActivity.setLeftIcon(0);
    }

    public void fetchData(){
        progressBar.setVisibility(View.VISIBLE);
        Call<DiseaseResponse> call = null;
        call = activity.api.getDiseaseList("1","1000");
        call.enqueue(new Callback<DiseaseResponse>() {
            @Override
            public void onResponse(Response<DiseaseResponse> response, Retrofit retrofit) {

                progressBar.setVisibility(View.GONE);
                if (2 == response.code() / 100) {
                    final DiseaseResponse diseaseResponse = response.body();

                    listPenyakit = diseaseResponse.getListDisease();

                    adapter = new ExploreAdapter(listPenyakit, ExploreFragment.this, activity.getApplicationContext());

                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(activity.getApplicationContext(), "Cannot fetching data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void run() {
        setupActionBar();
        fetchData();
    }

    @Override
    public void onItemClick(int position) {
        DiseaseModel item = listPenyakit.get(position);

        Intent i = new Intent(getActivity(), ExploreDetailActivity.class );
        i.putExtra("disease", item);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_CODE_PILIH_LOKASI == resultCode && REQUEST_CODE_PILIH_LOKASI == requestCode) {


            Bundle bundle = data.getExtras();
            if (bundle != null) {
                id_desa = bundle.getString("id_desa");
                nama_desa = bundle.getString("nama_desa");
                labelLokasi.setText("Lokasi, "+nama_desa);
                // Toast.makeText(EditPenyakitActivity.this, id_desa, Toast.LENGTH_SHORT).show();
//                tampilListPenyakitByLokasi();
            }

        }
    }

    public void tampilListPenyakitByLokasi(String idDesa, String namaDesa)
    {
        labelLokasi.setText("Lokasi, "+namaDesa);
        Call<DiseaseResponse> call = null;
        call = activity.api.getDiseaseListByLokasi("1","1000",idDesa);
        call.enqueue(new Callback<DiseaseResponse>() {
            @Override
            public void onResponse(Response<DiseaseResponse> response, Retrofit retrofit) {

                listPenyakit.clear();
                if (2 == response.code() / 100) {
                    final DiseaseResponse diseaseResponse = response.body();


                    listPenyakit = diseaseResponse.getListDisease();

                    adapter = new ExploreAdapter(listPenyakit, ExploreFragment.this, activity.getApplicationContext());

                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(activity.getApplicationContext(), "Cannot fetching data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
}
