package plbtw.epidemicdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RedeemResponse {
    @SerializedName("content")
    private ArrayList<RedeemModel> listRedeem = new ArrayList<>();

    @SerializedName("totalPages")
    private int totalPages;


    public ArrayList<RedeemModel> getListRedeem() {
        return listRedeem;
    }

    public void setListRedeem(ArrayList<RedeemModel> listRedeem) {
        this.listRedeem = listRedeem;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
