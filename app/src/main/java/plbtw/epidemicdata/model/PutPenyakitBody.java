package plbtw.epidemicdata.model;

public class PutPenyakitBody {


    private String nama_penyakit;
    private String jumlah_penderita;
    private String deskripsi;
    private String gejala;
    private String saran_penanganan;
    private String id_tipe_penyakit;
    private String id_desa;
    private String id_user_app;



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

    public String getId_user_app() {
        return id_user_app;
    }

    public void setId_user_app(String id_user_app) {
        this.id_user_app = id_user_app;
    }
}
