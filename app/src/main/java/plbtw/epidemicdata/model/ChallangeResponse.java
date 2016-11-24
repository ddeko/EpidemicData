package plbtw.epidemicdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChallangeResponse {
    @SerializedName("content")
    private ArrayList<ChallangeModel> listChallange = new ArrayList<>();

    @SerializedName("totalPages")
    private int totalPages;


    public ArrayList<ChallangeModel> getListChallange() {
        return listChallange;
    }

    public void setListChallange(ArrayList<ChallangeModel> listChallange) {
        this.listChallange = listChallange;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
