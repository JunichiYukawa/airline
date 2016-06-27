package info.vourja.airline.NetService;

import info.vourja.airline.Model.AirLineActivity;
import info.vourja.airline.Model.Line;
import info.vourja.airline.Model.ModelCollection;
import info.vourja.airline.Model.UserInfo;
import info.vourja.airline.Model.UserToken;
import info.vourja.airline.NetService.Request.RegisterRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AirLineService {

    @POST("/api/tw/register")
    Call<UserInfo> registerUser(
            @Body RegisterRequest body
    );

    @GET("/api/token")
    Call<UserToken> getToken(@Header("Authorization") String token_secret);

    @GET("/api/activities")
    Call<ModelCollection<AirLineActivity>> getActivities(@Header("Authorization") String token_secret);

    @GET("/api/activities")
    Call<AirLineActivity> getActivity(@Header("Authorization") String token_secret,
                                      @Query("i") String i);

    @POST("/api/activities")
    Call<AirLineActivity> postActivities(@Header("Authorization") String token_secret,
                        @Body AirLineActivity activity);

    @GET("/api/line")
    Call<Line> getLineByExtra(@Header("Authorization") String token_secret,
                       @Query("act_id") String act_id, @Query("q") String type);

    @GET("/api/line")
    Call<Line> getLine(@Header("Authorization") String token_secret,
                       @Query("act_id") String act_uuid, @Query("uuid") String uuid);

    @POST("/api/activities/finish")
    Call<AirLineActivity> finishActivity(@Header("Authorization") String token_secret,
                                         @Query("i") String i);

    @POST("/api/line/finish")
    Call<Line> finishLine(@Header("Authorization") String token_secret,
                          @Query("i") String i);

}
