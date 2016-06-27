package info.vourja.airline.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import info.vourja.airline.AirLineApplication;
import info.vourja.airline.Model.UserInfo;
import info.vourja.airline.Model.UserToken;
import info.vourja.airline.NetService.AirLineService;
import info.vourja.airline.NetService.Request.RegisterRequest;
import info.vourja.airline.NetService.util;
import info.vourja.airline.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginWithTwitterActivity extends Activity {
    private String TAG = LoginWithTwitterActivity.class.getSimpleName();

    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTwitterSession();
    }

    private void initTwitterSession() {
        // Session情報がない -> Twitter連携
        // Session情報はあるけどauth_tokenがない -> token取得
        // Sessionもtokenもある -> レッツスタート！！
        AirLineApplication application = (AirLineApplication)this.getApplication();
        TwitterSession session = application.getTwitterSession();
        if(session == null) {
            createTwitterLoginView();
        } else {
            String accesstoken = application.getAccessToken();
            if(accesstoken == null) {
                AirLineService service = ((AirLineApplication)getApplication()).getAirlineService();
                String token_secret = util.getCredentials(session.getAuthToken().token, session.getAuthToken().secret);

                Call<UserToken> call = service.getToken(token_secret);
                call.enqueue(new Callback<UserToken>() {
                    @Override
                    public void onResponse(Call<UserToken> call, Response<UserToken> response) {
                        if(response.body() != null) {
                            ((AirLineApplication)getApplication()).setAccessToken(response.body().getToken());
                            moveMainActivity();
                        } else {
                            createTwitterLoginView();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserToken> call, Throwable t) {
                        // アクセストークンの取得に失敗した場合は再度登録
                        createTwitterLoginView();
                    }
                });
            } else {
                moveMainActivity();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void registerTwitterInfo(TwitterSession session) {
        AirLineService service = ((AirLineApplication)getApplication()).getAirlineService();

        Call<UserInfo> call =  service.registerUser(new RegisterRequest(
                session.getAuthToken().token,
                session.getAuthToken().secret,
                session.getUserName(),
                session.getUserId()));
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                switch(response.code()) {
                    case 200:
                        String auth_token = response.body().getAuth_token();
                        ((AirLineApplication) getApplication()).setAccessToken(auth_token);
                        moveMainActivity();
                        break;
                    default:
                        // TODO サーバーがダウンとか
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
            }
        });
    }

    private void createTwitterLoginView() {
        setContentView(R.layout.activity_login);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);

        loginButton.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                ((AirLineApplication)getApplication()).setTwitterSession(session);
                registerTwitterInfo(session);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }

    private void moveMainActivity() {
        Intent intent = new Intent(LoginWithTwitterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
