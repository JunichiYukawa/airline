package info.vourja.airline;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

public class AirLineApplication extends Application {

    private TwitterSession twitterSession;

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

        writePreference();
    }

    private void writePreference() {
        SharedPreferences data = getSharedPreferences("Twitter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putString("token", twitterSession.getAuthToken().token);
        editor.putString("secret", twitterSession.getAuthToken().secret);
        editor.putLong("userid", twitterSession.getUserId());
        editor.putString("username", twitterSession.getUserName());
        editor.apply();
    }
}
