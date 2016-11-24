package plbtw.epidemicdata.model;

import java.io.Serializable;

/**
 * Created by DedeEko on 6/1/2016.
 */
public class MultimediaModel implements Serializable {

    private String id_multimedia;
    private String tipe_multimedia;
    private String url;

    public String getId_multimedia() {
        return id_multimedia;
    }

    public void setId_multimedia(String id_multimedia) {
        this.id_multimedia = id_multimedia;
    }

    public String getTipe_multimedia() {
        return tipe_multimedia;
    }

    public void setTipe_multimedia(String tipe_multimedia) {
        this.tipe_multimedia = tipe_multimedia;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
