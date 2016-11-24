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
import plbtw.epidemicdata.model.ChallangeModel;

public class RecyclerChallangeAdapter extends RecyclerView.Adapter {
    private Context context;

    private LayoutInflater inflater;

    private List<ChallangeModel> challangeModelList;

    private CoordinatorLayout coordinatorLayout;



    public void setContext(Context context) {
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public RecyclerChallangeAdapter
            (List<ChallangeModel> challangeModelList) {
        this.challangeModelList = challangeModelList;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        View view;

        layoutId = R.layout.challange_item;
        view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ChallangeViewHolder(view);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ChallangeViewHolder challangeViewHolder =
                (ChallangeViewHolder) holder;



        challangeViewHolder.challangeModel = challangeModelList
                .get(position);

        challangeViewHolder.textDeskripsiChallange.setText(challangeViewHolder
                .challangeModel.getDeskripsi());

        challangeViewHolder.textPoin.setText(challangeViewHolder
                .challangeModel.getPoin_diperoleh());



    }



    public void setCoordinatorLayout(CoordinatorLayout coordinatorLayout) {
        this.coordinatorLayout = coordinatorLayout;
    }










    @Override
    public int getItemCount() {
        return (challangeModelList.size());
    }


    public static class ChallangeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageChallange)
        ImageView imageChallange;
        @Bind(R.id.textPoin)
        TextView textPoin;
        @Bind(R.id.textDeskripsiChallange)
        TextView textDeskripsiChallange;

        ChallangeModel challangeModel;

        public ChallangeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }



}
