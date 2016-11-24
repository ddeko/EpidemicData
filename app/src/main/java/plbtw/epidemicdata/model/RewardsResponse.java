package plbtw.epidemicdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RewardsResponse {
    @SerializedName("content")
    private ArrayList<RewardsModel> listRewards = new ArrayList<>();

    @SerializedName("totalPages")
    private int totalPages;


    public ArrayList<RewardsModel> getListRewards() {
        return listRewards;
    }

    public void setListChallange(ArrayList<RewardsModel> listRewards) {
        this.listRewards = listRewards;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
