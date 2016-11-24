package plbtw.epidemicdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TipePenyakitResponse {
    @SerializedName("content")
    private ArrayList<TipePenyakitModel> listTipePenyakit = new ArrayList<>();

    @SerializedName("totalPages")
    private int totalPages;




    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<TipePenyakitModel> getListTipePenyakit() {
        return listTipePenyakit;
    }

    public void setListTipePenyakit(ArrayList<TipePenyakitModel> listTipePenyakit) {
        this.listTipePenyakit = listTipePenyakit;
    }
}
