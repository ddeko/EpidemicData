package plbtw.epidemicdata.api;

import plbtw.epidemicdata.model.DiseaseBody;
import plbtw.epidemicdata.model.DiseasePostResponse;
import plbtw.epidemicdata.model.DeletePenyakitResponse;
import plbtw.epidemicdata.model.DiseaseResponse;
import plbtw.epidemicdata.model.MultimediaBody;
import plbtw.epidemicdata.model.MultimediaPostResponse;
import plbtw.epidemicdata.model.PutPenyakitBody;
import plbtw.epidemicdata.model.PutPenyakitResponse;
import plbtw.epidemicdata.model.TipePenyakitResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by DedeEko on 5/18/2016.
 */
public interface API {

    //Penyakit
    @GET("penyakitall/{page}/{count_page}")
    Call<DiseaseResponse>
    getDiseaseList(@Path("page") String page, @Path("count_page") String count_page);

    @GET("penyakit/{page}/{count_page}")
    Call<DiseaseResponse>
    getDiseaseListByLokasi(@Path("page") String page, @Path("count_page") String count_page, @Query("id_desa") String id_desa);

    @POST("penyakit")
    Call<DiseasePostResponse>
    postDisease(@Body DiseaseBody diseaseBody);
    @DELETE("penyakit")
    Call<DeletePenyakitResponse> deletePenyakit(@Query("Id_penyakit") String id_penyakit);

    @PUT("penyakit")
    Call<PutPenyakitResponse> editPenyakit(@Query("Id_penyakit") String id_penyakit,
                                             @Body PutPenyakitBody putPenyakitBody);

    @GET("tipepenyakit/{page}/{count_page}")
    Call<TipePenyakitResponse>
    getTipePenyakitList(@Path("page") String page, @Path("count_page") String count_page);

    @POST("multimedia")
    Call<MultimediaPostResponse>
    postMultimedia(@Body MultimediaBody multimediaBody);
}
