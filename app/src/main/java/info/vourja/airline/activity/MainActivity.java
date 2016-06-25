package info.vourja.airline.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.vourja.airline.R;
import info.vourja.airline.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.main_title);

        setupFragment();
    }

    private void setupFragment() {
        FragmentManager manager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        homeFragment.setArguments(bundle);

        FragmentTransaction tr = manager.beginTransaction();
        tr.add(R.id.fragment_layout, homeFragment, "home");
        tr.commit();
    }
}
