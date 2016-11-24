package plbtw.epidemicdata.api;


import plbtw.epidemicdata.model.LoginResponse;
import plbtw.epidemicdata.model.RedeemBody;
import plbtw.epidemicdata.model.RedeemPostResponse;
import plbtw.epidemicdata.model.SignupBody;
import plbtw.epidemicdata.model.SignupPostResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface ApiUsers {



    @POST("users")
    Call<SignupPostResponse> postSignup(@Body SignupBody signupBody);

    @GET("users/1/1000")
    Call<LoginResponse> cekLogin(@Query("username") String username, @Query("password") String password);

    @GET("users/1/1000")
    Call<LoginResponse> getUser(@Query("username") String username, @Query("password") String password);
}
