package plbtw.epidemicdata.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import plbtw.epidemicdata.adapter.RecyclerRedeemAdapter;
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.api.ApiRedeem;
import plbtw.epidemicdata.model.RedeemModel;
import plbtw.epidemicdata.model.RedeemResponse;
import plbtw.epidemicdata.api.ServiceGenerator;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RedeemFragment extends Fragment {

    public RedeemFragment() {
    }


    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.listRedeem)
    RecyclerView recyclerView;
    private ApiRedeem apiRedeem = null;
    private RecyclerRedeemAdapter adapter;
    @Bind(R.id.contentRedeemLayout)
    LinearLayout contentRedeemLayout;
    private List<RedeemModel> redeemModelList;

    private SharedPreferences sharedPreferences;
    private final String name = "loginUser";
    private static final int mode = Activity.MODE_PRIVATE;

    private String id_user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_redeem,
                container, false);
        ButterKnife.bind(this, v);
        sharedPreferences = getActivity().getSharedPreferences(name, mode);
        id_user = sharedPreferences.getString("id_user","");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity().getApplicationContext()));
        progressBar.setVisibility(View.VISIBLE);
        apiRedeem = ServiceGenerator
                .createService(ApiRedeem.class);
        getRedeemList();
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void getRedeemList() {
        progressBar.setVisibility(View.VISIBLE);
        contentRedeemLayout.setVisibility(View.GONE);
        Call<RedeemResponse> call = null;
        call = apiRedeem.getRedeemList("1", "1000",id_user);
        call.enqueue(new Callback<RedeemResponse>() {
            @Override
            public void onResponse(Response<RedeemResponse> response,
                                   Retrofit retrofit) {
                if (2 == response.code() / 100) {
                    showRedeemList(response);
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
        Toast.makeText(getContext().getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
      //  Snackbar.make(coordinatorLayout, "Error to Load Content", Snackbar.LENGTH_LONG).show();
    }

   private void showRedeemList
           (Response<RedeemResponse> response) {

        final RedeemResponse redeemResponse = response.body();
        redeemModelList = redeemResponse.getListRedeem();

        adapter = new RecyclerRedeemAdapter(redeemModelList);
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
