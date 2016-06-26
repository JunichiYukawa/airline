package info.vourja.airline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import info.vourja.airline.AirLineApplication;
import info.vourja.airline.Model.AirLineActivity;
import info.vourja.airline.NetService.AirLineService;
import info.vourja.airline.NetService.util;
import info.vourja.airline.R;
import info.vourja.airline.fragment.HomeFragment;
import info.vourja.airline.fragment.LineFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LineActivity extends AppCompatActivity {

    private String mUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        if(savedInstanceState != null) {
            mUUID = savedInstanceState.getString("uuid");
        } else {
            Intent i = getIntent();
            mUUID = i.getStringExtra("uuid");
        }

        // 時間表示したら面白いかも
        setTitle(R.string.main_title);

        setupFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("uuid", mUUID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item = menu.add(R.string.action_finish);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // おしまい
                AirLineApplication application = (AirLineApplication)getApplication();
                String token = application.getAccessToken();
                AirLineService service = application.getAirlineService();
                String token_secret = util.getCredentials(token, "unused");

                Call<AirLineActivity> call = service.finishActivity(token_secret, mUUID);
                call.enqueue(new Callback<AirLineActivity>() {
                    @Override
                    public void onResponse(Call<AirLineActivity> call, Response<AirLineActivity> response) {
                        if(response.code() == 200) {
                            finish();
                        } else {
                            // TODO 終了するのに失敗したぞ、どうしたらいいんだ
                        }

                    }

                    @Override
                    public void onFailure(Call<AirLineActivity> call, Throwable t) {
                        // TODO 終了するのに失敗したぞ、どうしたらいいんだ
                    }
                });

                return false;
            }
        });
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            String uuid = scanResult.getContents();
        }
    }

    private void setupFragment() {
        FragmentManager manager = getSupportFragmentManager();
        LineFragment lineFragment = new LineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uuid", mUUID);
        lineFragment.setArguments(bundle);

        FragmentTransaction tr = manager.beginTransaction();
        tr.add(R.id.fragment_layout, lineFragment, "home");
        tr.commit();
    }

}
