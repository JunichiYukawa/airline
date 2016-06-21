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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        AirLineApplication application = (AirLineApplication)this.getApplication();

        TwitterSession session = application.getTwitterSession();
        if(session == null) {
            Intent intent = new Intent(getApplicationContext(), LoginWithTwitterActivity.class);
            startActivityForResult(intent,0);
        } else {
            String username = session.getUserName();
            String msg = "Welcome @" + username;
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }

    }
}
