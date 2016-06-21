package info.vourja.airline.NetService;

import java.util.List;

import info.vourja.airline.Model.AirlineActivity;
import info.vourja.airline.Model.UserInfo;
import info.vourja.airline.Model.UserToken;
import info.vourja.airline.NetService.Request.RegisterRequest;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

public interface AirLineService {

    @POST("/api/tw/register")
    void registerUser(
            @Body RegisterRequest body,
            Callback<UserInfo> cb
    );

    @GET("/api/token")
    void getToken(@Header("Authorization") String token_secret,
                  Callback<UserToken> cb);

    @GET("/api/activities")
    void getActivities(@Header("Authorization") String token_secret,
                       Callback<List<AirlineActivity>> cb);

}
