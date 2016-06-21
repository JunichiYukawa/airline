package info.vourja.airline.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import info.vourja.airline.fragment.HomeFragment;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.main_title);

        setupFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setupFragment() {
        FragmentManager manager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        homeFragment.setArguments(bundle);

        FragmentTransaction tr = manager.beginTransaction();
        tr.add(R.id.home_layout, homeFragment, "home");
        tr.commit();
    }
}
