package plbtw.epidemicdata.adapter;

import android.content.Context;
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
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.model.RedeemModel;

public class RecyclerRedeemAdapter extends RecyclerView.Adapter {
    private Context context;

    private LayoutInflater inflater;

    private List<RedeemModel> redeemModelList;

    private CoordinatorLayout coordinatorLayout;



    public void setContext(Context context) {
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public RecyclerRedeemAdapter
            (List<RedeemModel> redeemModelList) {
        this.redeemModelList = redeemModelList;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        View view;

        layoutId = R.layout.redeem_item;
        view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new RedeemViewHolder(view);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RedeemViewHolder redeemViewHolder =
                (RedeemViewHolder) holder;



        redeemViewHolder.redeemModel = redeemModelList
                .get(position);

        redeemViewHolder.textNama.setText(redeemViewHolder
                .redeemModel.getNama());

        redeemViewHolder.textNamaRewards.setText(redeemViewHolder
                .redeemModel.getNama_rewards());
        redeemViewHolder.textRedeemKey.setText(redeemViewHolder
                .redeemModel.getRedeem_key());



    }



    public void setCoordinatorLayout(CoordinatorLayout coordinatorLayout) {
        this.coordinatorLayout = coordinatorLayout;
    }










    @Override
    public int getItemCount() {
        return (redeemModelList.size());
    }


    public static class RedeemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageRedeem)
        ImageView imageRedeem;
        @Bind(R.id.textNama)
        TextView textNama;

        @Bind(R.id.textNamaRewards)
        TextView textNamaRewards;
        @Bind(R.id.textRedeemKey)
        TextView textRedeemKey;

        RedeemModel redeemModel;

        public RedeemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }



}
