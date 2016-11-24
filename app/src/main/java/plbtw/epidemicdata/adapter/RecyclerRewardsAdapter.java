package plbtw.epidemicdata.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import plbtw.epidemicdata.activitiy.DetailRewardsActivity;
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.fragment.RewardsFragment;
import plbtw.epidemicdata.model.RewardsModel;

public class RecyclerRewardsAdapter extends RecyclerView.Adapter {
    private Context context;

    private LayoutInflater inflater;

    private List<RewardsModel> rewardsModelList;

    private CoordinatorLayout coordinatorLayout;



    private RewardsFragment rewardsFragment;
    public void setContext(Context context) {
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public RecyclerRewardsAdapter
            (List<RewardsModel> rewardsModelList) {
        this.rewardsModelList = rewardsModelList;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        View view;

        layoutId = R.layout.rewards_item;
        view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new RewardsViewHolder(view);


    }


    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        final RewardsViewHolder rewardsViewHolder =
                (RewardsViewHolder) holder;



        rewardsViewHolder.rewardsModel = rewardsModelList
                .get(position);

        rewardsViewHolder.textDeskripsiRewards.setText(rewardsViewHolder
                .rewardsModel.getDeskripsi_rewards());

        rewardsViewHolder.textSisa.setText(rewardsViewHolder
                .rewardsModel.getSisa()+ "  Pcs");
        rewardsViewHolder.textPoin.setText(rewardsViewHolder
                .rewardsModel.getPoin()+ "  Poin");
        rewardsViewHolder.textNamaRewards.setText(rewardsViewHolder
                .rewardsModel.getNama_rewards());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailRewardsActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("rewardsModel", rewardsViewHolder.rewardsModel);
                intent.putExtras(b);
               // v.getContext().startActivity(intent);
                rewardsFragment.startActivityForResult(intent, 1);
            }
        });


    }



    public void setCoordinatorLayout(CoordinatorLayout coordinatorLayout) {
        this.coordinatorLayout = coordinatorLayout;
    }










    @Override
    public int getItemCount() {
        return (rewardsModelList.size());
    }

    public RewardsFragment getRewardsFragment() {
        return rewardsFragment;
    }

    public void setRewardsFragment(RewardsFragment rewardsFragment) {
        this.rewardsFragment = rewardsFragment;
    }


    public static class RewardsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageRewards)
        ImageView imageRewards;
        @Bind(R.id.textPoin)
        TextView textPoin;

        @Bind(R.id.textNamaRewards)
        TextView textNamaRewards;
        @Bind(R.id.textSisa)
        TextView textSisa;
        @Bind(R.id.textDeskripsiRewards)
        TextView textDeskripsiRewards;

        RewardsModel rewardsModel;

        public RewardsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }



}
