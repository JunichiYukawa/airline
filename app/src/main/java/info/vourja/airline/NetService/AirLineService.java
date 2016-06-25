package info.vourja.airline.NetService;

import info.vourja.airline.Model.AirLineActivity;
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

public interface AirLineService {

    @POST("/api/tw/register")
    Call<UserInfo> registerUser(
            @Body RegisterRequest body
    );

    @GET("/api/token")
    Call<UserToken> getToken(@Header("Authorization") String token_secret);

    @GET("/api/activities")
    Call<ModelCollection<AirLineActivity>> getActivities(@Header("Authorization") String token_secret);

    @POST("/api/activities")
    Call<AirLineActivity> postActivities(@Header("Authorization") String token_secret,
                        @Body AirLineActivity activity);

}
