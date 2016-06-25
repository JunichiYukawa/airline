package info.vourja.airline.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import info.vourja.airline.R;
import info.vourja.airline.fragment.HomeFragment;
import info.vourja.airline.fragment.LineFragment;


public class LineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        // 時間表示したら面白いかも
        setTitle(R.string.main_title);

        setupFragment();
    }

    private void setupFragment() {
        FragmentManager manager = getSupportFragmentManager();
        LineFragment lineFragment = new LineFragment();
        Bundle bundle = new Bundle();
        lineFragment.setArguments(bundle);

        FragmentTransaction tr = manager.beginTransaction();
        tr.add(R.id.fragment_layout, lineFragment, "home");
        tr.commit();
    }

}
