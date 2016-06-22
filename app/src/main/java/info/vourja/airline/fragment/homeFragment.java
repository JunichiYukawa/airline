package info.vourja.airline.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.vourja.airline.AirLineApplication;
import info.vourja.airline.Model.AirlineActivity;
import info.vourja.airline.Model.ModelCollection;
import info.vourja.airline.NetService.AirLineService;
import info.vourja.airline.NetService.util;
import info.vourja.airline.R;

public class HomeFragment extends Fragment {

    @BindView(R.id.total_activity) TextView textTotalActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        AirLineApplication application = (AirLineApplication)getActivity().getApplication();
        String token = application.getAccessToken();
        AirLineService service = application.getAirlineService();
        String token_secret = util.getCredentials(token, "unused");

        service.getActivities(token_secret, new Callback<ModelCollection<AirlineActivity>>() {
            @Override
            public void success(Result<ModelCollection<AirlineActivity>> result) {
                textTotalActivity.setText(String.valueOf(result.data.getNum_result()));
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    @OnClick(R.id)
}
