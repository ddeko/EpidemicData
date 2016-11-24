package plbtw.epidemicdata.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by DedeEko on 5/18/2016.
 */
public class DiseaseModel implements Serializable{
    private String id_penyakit;
    private String nama_penyakit;
    private String jumlah_penderita;
    private String deskripsi;
    private String gejala;
    private String saran_penanganan;
    private String nama_tipe_penyakit;
    private String nama_desa;
    private String nama_user;
    private String id_desa;
    private String id_tipe_penyakit;
    private String id_user_app;
    private ArrayList<MultimediaModel> multimedia;

    public String getId_tipe_penyakit() {
        return id_tipe_penyakit;
    }

    public void setId_tipe_penyakit(String id_tipe_penyakit) {
        this.id_tipe_penyakit = id_tipe_penyakit;
    }

    public String getId_desa() {
        return id_desa;
    }

    public void setId_desa(String id_desa) {
        this.id_desa = id_desa;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public ArrayList<MultimediaModel> getMultimedia() {

        return multimedia;
    }

    public void setMultimedia(ArrayList<MultimediaModel> multimedia) {
        this.multimedia = multimedia;
    }

    public String getId_penyakit() {
        return id_penyakit;
    }

    public void setId_penyakit(String id_penyakit) {
        this.id_penyakit = id_penyakit;
    }

    public String getNama_penyakit() {
        return nama_penyakit;
    }

    public void setNama_penyakit(String nama_penyakit) {
        this.nama_penyakit = nama_penyakit;
    }

    public String getJumlah_penderita() {
        return jumlah_penderita;
    }

    public void setJumlah_penderita(String jumlah_penderita) {
        this.jumlah_penderita = jumlah_penderita;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public String getSaran_penanganan() {
        return saran_penanganan;
    }

    public void setSaran_penanganan(String saran_penanganan) {
        this.saran_penanganan = saran_penanganan;
    }

    public String getNama_tipe_penyakit() {
        return nama_tipe_penyakit;
    }

    public void setNama_tipe_penyakit(String nama_tipe_penyakit) {
        this.nama_tipe_penyakit = nama_tipe_penyakit;
    }

    public String getNama_desa() {
        return nama_desa;
    }

    public void setNama_desa(String nama_desa) {
        this.nama_desa = nama_desa;
    }

    public String getNama() {
        return nama_user;
    }

    public void setNama(String nama_user) {
        this.nama_user = nama_user;
    }


    public String getId_user_app() {
        return id_user_app;
    }

    public void setId_user_app(String id_user_app) {
        this.id_user_app = id_user_app;
    }
}
