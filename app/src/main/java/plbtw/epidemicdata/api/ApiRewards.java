package plbtw.epidemicdata.api;


import plbtw.epidemicdata.model.RewardsResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ApiRewards {
    @GET("rewards/{page}/{count_page}")
    Call<RewardsResponse>
    getRewardsList(@Path("page") String page, @Path("count_page") String count_page);


}
