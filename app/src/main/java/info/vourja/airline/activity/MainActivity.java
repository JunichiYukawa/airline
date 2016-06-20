package info.vourja.airline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterSession;

import info.vourja.airline.AirLineApplication;
import info.vourja.airline.R;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "4WcKZtQ2NRG62m2WQ84sr4MVq";
    private static final String TWITTER_SECRET = "kSgTjWSr2za8lDkjg8dI0jaWlREcZKLOH0TiNWUKiIuDDyXIsV";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        AirLineApplication application = (AirLineApplication)this.getApplication();

        TwitterSession session = application.getTwitterSession();
        if(session == null) {
            Intent intent = new Intent(getApplicationContext(), LoginWithTwitterActivity.class);
            startActivityForResult(intent,0);
        } else {
            // TODO MainActivityの処理を書く
            String username = session.getUserName();
            String msg = "Welcome @" + username;
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }

    }
}
