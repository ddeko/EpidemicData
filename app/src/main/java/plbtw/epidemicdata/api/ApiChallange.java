package plbtw.epidemicdata.api;


import plbtw.epidemicdata.model.ChallangeResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ApiChallange {
    @GET("challange/{page}/{count_page}")
    Call<ChallangeResponse>
    getChallangeList(@Path("page") String page, @Path("count_page") String count_page);


}
