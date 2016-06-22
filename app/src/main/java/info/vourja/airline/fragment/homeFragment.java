package info.vourja.airline.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.vourja.airline.AirLineApplication;
import info.vourja.airline.Model.AirlineActivity;
import info.vourja.airline.Model.ModelCollection;
import info.vourja.airline.NetService.AirLineService;
import info.vourja.airline.NetService.util;
import info.vourja.airline.R;

public class HomeFragment extends Fragment {

    @BindView(R.id.total_activity) TextView textTotalActivity;
    @BindView(R.id.total_lines) TextView textTotalLines;
    @BindView(R.id.avg_visitor) TextView textAvgLines;

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
                long total_activity_count = result.data.getTotal();
                textTotalActivity.setText(String.valueOf(total_activity_count));

                long total_line = 0;
                for( AirlineActivity act : result.data.getObjects()) {
                    total_line += act.getLines().size();
                }
                textTotalLines.setText(String.valueOf(total_line));

                if(total_activity_count > 0) {
                    long avg_line = (total_line * 100) / total_activity_count;
                    long avg_line_dec = avg_line % 100;
                    avg_line /= 100;
                    textAvgLines.setText(String.valueOf(avg_line) + "." + String.valueOf(avg_line_dec));
                }
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    @OnClick(R.id.setup_button)
    void onClickSetupButton() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        SetupFragment next = new SetupFragment();
        ft.replace(R.id.fragment_layout, next);
        ft.addToBackStack(null);
        ft.commit();
    }
}
