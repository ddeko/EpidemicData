package plbtw.epidemicdata.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import plbtw.epidemicdata.activitiy.DetailRewardsActivity;
import plbtw.epidemicdata.activitiy.EditPenyakitActivity;
import plbtw.epidemicdata.model.DiseaseModel;
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.utils.ImageUtil;

public class ExploreAdapter extends RecyclerView.Adapter {
    private Context context;

    private LayoutInflater inflater;

    private ArrayList<DiseaseModel> diseaseModelList;
    private ExploreAdapterListener listener;

    private SharedPreferences sharedPreferences;
    private final String name = "loginUser";
    private static final int mode = Activity.MODE_PRIVATE;

    private String id_user;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public ExploreAdapter(ArrayList<DiseaseModel> diseaseModelList, ExploreAdapterListener listener,Context context) {
        this.diseaseModelList = diseaseModelList;
        this.listener = listener;
        this.context = context;


    }

    public interface ExploreAdapterListener {
        void onItemClick(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_item, parent, false);
        sharedPreferences = v.getContext().getSharedPreferences(name, mode);
        id_user = sharedPreferences.getString("id_user","");
        ImageView imageDisease = (ImageView)v.findViewById(R.id.imageView3);
        TextView textNamaPenyakit = (TextView)v.findViewById(R.id.txt_namaPenyakit);
        TextView textTipePenyakit = (TextView)v.findViewById(R.id.txt_tipePenyakit);
        TextView textLokasi = (TextView)v.findViewById(R.id.txt_lokasi);
        TextView textNama = (TextView)v.findViewById(R.id.txt_namaUser);
        ImageView icEditted = (ImageView)v.findViewById(R.id.ic_editted);
        View item = v.findViewById(R.id.explore_item_item);

        return new ViewHolder(v, imageDisease, textNamaPenyakit, textTipePenyakit, textLokasi, textNama, icEditted, item);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {



        final ViewHolder challangeViewHolder = (ViewHolder) holder;

        final DiseaseModel diseaseModel = diseaseModelList.get(position);

        final String imageUrl = diseaseModel.getMultimedia().get(0).getUrl();

        if(imageUrl!=null) {
            if (!imageUrl.isEmpty())
                ImageUtil.display(this.context, imageUrl, challangeViewHolder.imageDisease, false);
            else
                ImageUtil.display(context, R.drawable.no_image, challangeViewHolder.imageDisease, false);
        }
        challangeViewHolder.textNama.setText("By "+diseaseModel.getNama());

        challangeViewHolder.textNamaPenyakit.setText(diseaseModel.getNama_penyakit());
        challangeViewHolder.textTipePenyakit.setText(diseaseModel.getNama_tipe_penyakit());

        challangeViewHolder.textLokasi.setText(diseaseModel.getNama_desa());

        if(id_user.equalsIgnoreCase(diseaseModel.getId_user_app().toString())) {
            challangeViewHolder.icEditted.setVisibility(View.VISIBLE);
        }
        else
        {
            challangeViewHolder.icEditted.setVisibility(View.INVISIBLE);
        }
        challangeViewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onItemClick(position);
            }
        });

        challangeViewHolder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(id_user.equalsIgnoreCase(diseaseModel.getId_user_app().toString())) {
                    DiseaseModel diseaseModel = diseaseModelList.get(position);
                    ShowOptionMenu(diseaseModel, v);
                }


                return true;
            }
        });


    }


    private void ShowOptionMenu(final DiseaseModel diseaseModel, final View view) {
        final String[] items;
        final Integer[] icons;
        items = new String[]{"Edit Penyakit"};
        icons = new Integer[]{R.drawable.ic_post};

        ListAdapter listAdapter = new ArrayAdapterWithIcon(view.getContext(), items, icons);
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setAdapter(listAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int item) {
                if (items[item].equals("Edit Penyakit")) {
                    showEditPenyakit(diseaseModel, view);
                }
            }
        });
        builder.show();
    }

    private void showEditPenyakit(DiseaseModel diseaseModel, View view)
    {
        Intent intent = new Intent(view.getContext(), EditPenyakitActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("diseaseModel", diseaseModel);
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return diseaseModelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageDisease;
        public TextView textNamaPenyakit;
        public TextView textTipePenyakit;
        public TextView textLokasi;
        public TextView textNama;
        public ImageView icEditted;
        public View item;


        public ViewHolder(View itemView, ImageView imageDisease, TextView textNamaPenyakit,
                          TextView textTipePenyakit, TextView textLokasi, TextView textNama, ImageView icEditted, View item) {
            super(itemView);
            this.imageDisease = imageDisease;
            this.textNamaPenyakit = textNamaPenyakit;
            this.textTipePenyakit = textTipePenyakit;
            this.textLokasi = textLokasi;
            this.textNama = textNama;
            this.icEditted = icEditted;
            this.item = item;
        }
    }



}
