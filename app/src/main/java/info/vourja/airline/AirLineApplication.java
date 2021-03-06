package info.vourja.airline;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

import info.vourja.airline.NetService.AirLineService;
import io.fabric.sdk.android.Fabric;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AirLineApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "L6StgFi57qsxCS3GOvzRrj5I7";
    private static final String TWITTER_SECRET = "DYu4bES4onS68ZSf6jViuHjqXzDr6GaBBAAhHAhywo3ju6DPIP";

    // アクセス情報
    private TwitterSession twitterSession;
    private String accessToken;

    // サーバー情報
    Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

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
        if(mRetrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://vourja.info")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return mRetrofit.create(AirLineService.class);
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
