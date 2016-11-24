package plbtw.epidemicdata.model;

import java.io.Serializable;

/**
 * Created by DedeEko on 6/1/2016.
 */
public class DiseasePostResponse{
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
