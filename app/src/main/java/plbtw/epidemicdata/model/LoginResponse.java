package plbtw.epidemicdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse {

    @SerializedName("content")
    private ArrayList<LoginBody> listLoginBody = new ArrayList<>();

    @SerializedName("totalPages")
    private int totalPages;


    public ArrayList<LoginBody> getListLoginBody() {
        return listLoginBody;
    }

    public void setListLoginBody(ArrayList<LoginBody> listLoginBody) {
        this.listLoginBody = listLoginBody;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
