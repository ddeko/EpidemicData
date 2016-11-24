package plbtw.epidemicdata.fragment;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import plbtw.epidemicdata.adapter.RecyclerChallangeAdapter;
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.api.ApiChallange;
import plbtw.epidemicdata.model.ChallangeModel;
import plbtw.epidemicdata.model.ChallangeResponse;
import plbtw.epidemicdata.api.ServiceGenerator;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ChallangeFragment extends Fragment {

    public ChallangeFragment() {
    }


    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.listChallange)
    RecyclerView recyclerView;
    private ApiChallange apiChallange = null;
    private RecyclerChallangeAdapter adapter;
    @Bind(R.id.contentChallangeLayout)
    LinearLayout contentChallangeLayout;
    private List<ChallangeModel> challangeModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_challange,
                container, false);
        ButterKnife.bind(this, v);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity().getApplicationContext()));
        progressBar.setVisibility(View.VISIBLE);
        apiChallange = ServiceGenerator
                .createService(ApiChallange.class);
        getChallangeList();
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void getChallangeList() {
        progressBar.setVisibility(View.VISIBLE);
        contentChallangeLayout.setVisibility(View.GONE);
        Call<ChallangeResponse> call = null;
        call = apiChallange.getChallangeList("1", "1000");
        call.enqueue(new Callback<ChallangeResponse>() {
            @Override
            public void onResponse(Response<ChallangeResponse> response,
                                   Retrofit retrofit) {
                if (2 == response.code() / 100) {
                    showChallangeList(response);
                } else {
                    showErrorMessage();
                }
                progressBar.setVisibility(View.GONE);
                contentChallangeLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(coordinatorLayout, "Failed to Load Content",
                        Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                contentChallangeLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(getContext().getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
      //  Snackbar.make(coordinatorLayout, "Error to Load Content", Snackbar.LENGTH_LONG).show();
    }

   private void showChallangeList
            (Response<ChallangeResponse> response) {

        final ChallangeResponse challangeResponse = response.body();
        challangeModelList = challangeResponse.getListChallange();

        adapter = new RecyclerChallangeAdapter(challangeModelList);
        adapter.setCoordinatorLayout(coordinatorLayout);
       adapter.setInflater(getActivity().getLayoutInflater());
       recyclerView.setAdapter(adapter);

   }

    /*
    private void loadMorePushNotificationHistory
            (Response<PushNotificationHistoryResponse> response) {
        PushNotificationHistoryResponse pushNotificationHistoryResponse = response.body();
        if (0 == pushNotificationHistoryResponse.getListPushNotification().size()) {
            Snackbar.make(coordinatorLayout, "No More Article", Snackbar.LENGTH_LONG).show();
        } else {
            for (PushNotificationModel pushNotificationModel :
                    pushNotificationHistoryResponse.getListPushNotification()) {
                pushNotificationModelList.add(pushNotificationModel);
                adapter.notifyItemInserted(pushNotificationModelList.size());
            }
        }
        adapter.setLoaded();
    }*/
}
