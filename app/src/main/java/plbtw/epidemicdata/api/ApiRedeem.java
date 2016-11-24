package plbtw.epidemicdata.api;

import plbtw.epidemicdata.model.RedeemResponse;
import plbtw.epidemicdata.model.RedeemBody;
import plbtw.epidemicdata.model.RedeemPostResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiRedeem {
    @GET("redeem/{page}/{count_page}")
    Call<RedeemResponse>
    getRedeemList(@Path("page") String page, @Path("count_page") String count_page, @Query("id_user_app") String id_user_app);


    @POST("redeem")
    Call<RedeemPostResponse> postRedeem(@Body RedeemBody redeemBody);
}
