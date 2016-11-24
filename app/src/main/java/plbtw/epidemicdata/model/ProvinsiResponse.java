package plbtw.epidemicdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProvinsiResponse {
    @SerializedName("content")
    private ArrayList<ProvinsiModel> listProvinsi = new ArrayList<>();

    @SerializedName("totalPages")
    private int totalPages;


    public ArrayList<ProvinsiModel> getListProvinsi() {
        return listProvinsi;
    }

    public void setListProvinsi(ArrayList<ProvinsiModel> listProvinsi) {
        this.listProvinsi = listProvinsi;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
