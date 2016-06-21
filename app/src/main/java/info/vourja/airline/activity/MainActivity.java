package info.vourja.airline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import info.vourja.airline.AirLineApplication;
import info.vourja.airline.Model.UserToken;
import info.vourja.airline.NetService.AirLineService;
import info.vourja.airline.NetService.util;
import info.vourja.airline.R;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private final String TAG =  MainActivity.class.getSimpleName();

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "4WcKZtQ2NRG62m2WQ84sr4MVq";
    private static final String TWITTER_SECRET = "kSgTjWSr2za8lDkjg8dI0jaWlREcZKLOH0TiNWUKiIuDDyXIsV";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        // Session情報がない -> Twitter連携
        // Session情報はあるけどauth_tokenがない -> token取得
        // Sessionもtokenもある -> レッツスタート！！
        AirLineApplication application = (AirLineApplication)this.getApplication();
        TwitterSession session = application.getTwitterSession();
        if(session == null) {
            Intent intent = new Intent(getApplicationContext(), LoginWithTwitterActivity.class);
            startActivityForResult(intent,0);
        } else {

            String accesstoken = application.getAccessToken();
            if(accesstoken == null) {
                AirLineService service = ((AirLineApplication)getApplication()).getAirlineService();
                String token_secret = util.getCredentials(session.getAuthToken().token, session.getAuthToken().secret);
                service.getToken(token_secret, new Callback<UserToken>() {
                    @Override
                    public void success(Result<UserToken> result) {
                        Log.d(TAG, "user token: " + result.data.getToken());
                        ((AirLineApplication)getApplication()).setAccessToken(result.data.getToken());
                        init();
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // アクセストークンの取得に失敗した場合は再度登録
                        Intent intent = new Intent(getApplicationContext(), LoginWithTwitterActivity.class);
                        startActivityForResult(intent,0);
                    }
                });

            } else {
                init();
            }
        }

    }

    private void init() {
        AirLineApplication application = (AirLineApplication)this.getApplication();
        TwitterSession session = application.getTwitterSession();
        String username = session.getUserName();
        String msg = "Welcome @" + username;
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
