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
import info.vourja.airline.NetService.AirLineService;
import info.vourja.airline.NetService.Request.RegisterRequest;
import info.vourja.airline.R;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginWithTwitterActivity extends Activity {

    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void registerTwitterInfo(TwitterSession session) {
        AirLineService service = ((AirLineApplication)getApplication()).getAirlineService();
        service.registerUser(new RegisterRequest(
                        session.getAuthToken().token,
                        session.getAuthToken().secret,
                        session.getUserName(),
                        session.getUserId()),
                new Callback<UserInfo>() {
                    @Override
                    public void success(UserInfo result, Response response) {
                        String auth_token = result.getAuth_token();
                        ((AirLineApplication)getApplication()).setAccessToken(auth_token);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(error.getResponse().getStatus() == 409) {
                            // すでに登録されている
                        } else {
                            // その他エラー
                        }
                    }
                }
        );
    }

}
