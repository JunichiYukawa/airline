package info.vourja.airline;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

import io.fabric.sdk.android.Fabric;

public class AirLineApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "4WcKZtQ2NRG62m2WQ84sr4MVq";
    private static final String TWITTER_SECRET = "kSgTjWSr2za8lDkjg8dI0jaWlREcZKLOH0TiNWUKiIuDDyXIsV";

    private TwitterSession twitterSession;

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
