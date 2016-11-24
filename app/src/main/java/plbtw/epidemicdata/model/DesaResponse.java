package plbtw.epidemicdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DesaResponse {
    @SerializedName("content")
    private ArrayList<DesaModel> listDesa = new ArrayList<>();

    @SerializedName("totalPages")
    private int totalPages;


    public ArrayList<DesaModel> getListDesa() {
        return listDesa;
    }

    public void setListDesa(ArrayList<DesaModel> listDesa) {
        this.listDesa = listDesa;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
