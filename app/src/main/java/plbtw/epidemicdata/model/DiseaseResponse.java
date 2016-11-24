package plbtw.epidemicdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by DedeEko on 5/18/2016.
 */
public class DiseaseResponse {
    @SerializedName("content")
    private ArrayList<DiseaseModel> listDisease = new ArrayList<>();

    @SerializedName("totalPages")
    private int totalPages;

    public ArrayList<DiseaseModel> getListDisease() {
        return listDisease;
    }

    public void setListDisease(ArrayList<DiseaseModel> listDisease) {
        this.listDisease = listDisease;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
