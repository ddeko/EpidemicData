package plbtw.epidemicdata.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RedeemPostResponse {

    private boolean Success;

    private String Info;

    public boolean getSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        this.Success = success;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        this.Info = info;
    }
}
