package plbtw.epidemicdata.fragment;

import android.content.Intent;
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
import plbtw.epidemicdata.activitiy.TabMenuActivity;
import plbtw.epidemicdata.adapter.RecyclerRewardsAdapter;
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.api.ApiRewards;
import plbtw.epidemicdata.model.RewardsModel;
import plbtw.epidemicdata.model.RewardsResponse;
import plbtw.epidemicdata.api.ServiceGenerator;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RewardsFragment extends Fragment {

    public RewardsFragment() {
    }


    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.listRewards)
    RecyclerView recyclerView;
    private ApiRewards apiRewards = null;
    private RecyclerRewardsAdapter adapter;
    @Bind(R.id.contentRewardsLayout)
    LinearLayout contentRewardsLayout;
    private List<RewardsModel> rewardsModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rewards,
                container, false);
        ButterKnife.bind(this, v);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity().getApplicationContext()));
        progressBar.setVisibility(View.VISIBLE);
        apiRewards = ServiceGenerator
                .createService(ApiRewards.class);
        getRewardsList();
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (1 == resultCode && 1 == requestCode) {
            //getRewardsList();
            ((TabMenuActivity)getActivity()).refresh();

        }
    }
    public void getRewardsList() {
        progressBar.setVisibility(View.VISIBLE);
        contentRewardsLayout.setVisibility(View.GONE);
        Call<RewardsResponse> call = null;
        call = apiRewards.getRewardsList("1", "1000");
        call.enqueue(new Callback<RewardsResponse>() {
            @Override
            public void onResponse(Response<RewardsResponse> response,
                                   Retrofit retrofit) {
                if (2 == response.code() / 100) {
                    showRewardsList(response);
                } else {
                    showErrorMessage();
                }
                progressBar.setVisibility(View.GONE);
                contentRewardsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(coordinatorLayout, "Failed to Load Content",
                        Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                contentRewardsLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(getContext().getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
      //  Snackbar.make(coordinatorLayout, "Error to Load Content", Snackbar.LENGTH_LONG).show();
    }

   private void showRewardsList
           (Response<RewardsResponse> response) {

        final RewardsResponse rewardsResponse = response.body();
        rewardsModelList = rewardsResponse.getListRewards();

        adapter = new RecyclerRewardsAdapter(rewardsModelList);
        adapter.setCoordinatorLayout(coordinatorLayout);
       adapter.setRewardsFragment(this);
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
