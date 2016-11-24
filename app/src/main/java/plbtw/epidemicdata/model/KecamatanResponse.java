package plbtw.epidemicdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KecamatanResponse {
    @SerializedName("content")
    private ArrayList<KecamatanModel> listKecamatan = new ArrayList<>();

    @SerializedName("totalPages")
    private int totalPages;


    public ArrayList<KecamatanModel> getListKecamatan() {
        return listKecamatan;
    }

    public void setListKecamatan(ArrayList<KecamatanModel> listKecamatan) {
        this.listKecamatan = listKecamatan;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
