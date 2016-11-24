package plbtw.epidemicdata.model;

public class MultimediaBody {

    private String tipe_multimedia;

    private String photo_name;
    private String id_penyakit;
    private String id_user;
    private String photo_string;


    public String getTipe_multimedia() {
        return tipe_multimedia;
    }

    public void setTipe_multimedia(String tipe_multimedia) {
        this.tipe_multimedia = tipe_multimedia;
    }

    public String getPhoto_name() {
        return photo_name;
    }

    public void setPhoto_name(String photo_name) {
        this.photo_name = photo_name;
    }

    public String getId_penyakit() {
        return id_penyakit;
    }

    public void setId_penyakit(String id_penyakit) {
        this.id_penyakit = id_penyakit;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getPhoto_string() {
        return photo_string;
    }

    public void setPhoto_string(String photo_string) {
        this.photo_string = photo_string;
    }
}
