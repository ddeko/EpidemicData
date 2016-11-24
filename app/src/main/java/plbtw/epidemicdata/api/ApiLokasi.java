package plbtw.epidemicdata.api;



import plbtw.epidemicdata.model.DesaResponse;
import plbtw.epidemicdata.model.KabupatenResponse;
import plbtw.epidemicdata.model.KecamatanResponse;
import plbtw.epidemicdata.model.ProvinsiResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiLokasi {
    @GET("provinsi/{page}/{count_page}")
    Call<ProvinsiResponse>
    getProvinsiList(@Path("page") String page, @Path("count_page") String count_page);

    @GET("kabupaten/{page}/{count_page}")
    Call<KabupatenResponse>
    getKabupatenList(@Path("page") String page, @Path("count_page") String count_page, @Query("id_provinsi") String id_provinsi);


    @GET("kecamatan/{page}/{count_page}")
    Call<KecamatanResponse>
    getKecamatanList(@Path("page") String page, @Path("count_page") String count_page , @Query("id_kabupaten") String id_kabupaten);

    @GET("desa/{page}/{count_page}")
    Call<DesaResponse>
    getDesaList(@Path("page") String page, @Path("count_page") String count_page, @Query("id_kecamatan") String id_kecamatan);

}
