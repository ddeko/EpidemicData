package plbtw.epidemicdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KabupatenResponse {
    @SerializedName("content")
    private ArrayList<KabupatenModel> listKabupaten = new ArrayList<>();

    @SerializedName("totalPages")
    private int totalPages;


    public ArrayList<KabupatenModel> getListKabupaten() {
        return listKabupaten;
    }

    public void setListKabupaten(ArrayList<KabupatenModel> listKabupaten) {
        this.listKabupaten = listKabupaten;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
