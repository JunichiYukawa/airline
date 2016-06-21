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

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    private void init() {
        AirLineApplication application = (AirLineApplication)this.getApplication();
        TwitterSession session = application.getTwitterSession();
        String username = session.getUserName();
        String msg = "Welcome @" + username;
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
