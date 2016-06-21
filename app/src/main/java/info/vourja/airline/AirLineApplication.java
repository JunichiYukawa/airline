package info.vourja.airline;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

import info.vourja.airline.NetService.AirLineService;
import retrofit.RestAdapter;

public class AirLineApplication extends Application {

    // アクセス情報
    private TwitterSession twitterSession;
    private String accessToken;

    // サーバー情報
    AirLineService mService;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences data = getSharedPreferences("Twitter", Context.MODE_PRIVATE);
        if(data != null) {

            String token = data.getString("token", null);
            String secret = data.getString("secret", null);
            long userid = data.getLong("userid", 0);
            String username = data.getString("username", null);

            if(token == null || secret == null || userid == 0 || username == null) {
                // Do Nothing
            } else {
                TwitterAuthToken authToken = new TwitterAuthToken(token, secret);
                twitterSession = new TwitterSession(authToken, userid, username);
            }
        }
    }

    public TwitterSession getTwitterSession() {
        return twitterSession;
    }

    public void setTwitterSession(TwitterSession twitterSession) {
        this.twitterSession = twitterSession;

        writeTwitterPreference();
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AirLineService getAirlineService() {
        if(mService == null) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint("http://192.168.111.109:5000")
                    .build();
            mService = adapter.create(AirLineService.class);
        }
        return mService;
    }

    private void writeTwitterPreference() {
        SharedPreferences data = getSharedPreferences("Twitter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putString("token", twitterSession.getAuthToken().token);
        editor.putString("secret", twitterSession.getAuthToken().secret);
        editor.putLong("userid", twitterSession.getUserId());
        editor.putString("username", twitterSession.getUserName());
        editor.apply();
    }

}
