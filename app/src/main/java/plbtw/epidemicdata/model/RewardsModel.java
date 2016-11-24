package plbtw.epidemicdata.model;

import java.io.Serializable;

public class RewardsModel implements Serializable {
    private String id_rewards;
    private String nama_rewards;
    private String deskripsi_rewards;
    private String poin;
    private String sisa;


    public String getId_rewards() {
        return id_rewards;
    }

    public void setId_rewards(String id_rewards) {
        this.id_rewards = id_rewards;
    }

    public String getNama_rewards() {
        return nama_rewards;
    }

    public void setNama_rewards(String nama_rewards) {
        this.nama_rewards = nama_rewards;
    }

    public String getDeskripsi_rewards() {
        return deskripsi_rewards;
    }

    public void setDeskripsi_rewards(String deskripsi_rewards) {
        this.deskripsi_rewards = deskripsi_rewards;
    }

    public String getPoin() {
        return poin;
    }

    public void setPoin(String poin) {
        this.poin = poin;
    }

    public String getSisa() {
        return sisa;
    }

    public void setSisa(String sisa) {
        this.sisa = sisa;
    }
}
