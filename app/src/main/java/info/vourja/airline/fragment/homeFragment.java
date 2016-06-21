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
import com.twitter.sdk.android.core.TwitterSession;

import java.util.List;

import info.vourja.airline.AirLineApplication;
import info.vourja.airline.Model.AirlineActivity;
import info.vourja.airline.NetService.AirLineService;
import info.vourja.airline.NetService.util;
import info.vourja.airline.R;

public class HomeFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        AirLineApplication application = (AirLineApplication)getActivity().getApplication();
        TwitterSession session = application.getTwitterSession();
        AirLineService service = application.getAirlineService();
        String token_secret = util.getCredentials(session.getAuthToken().token, session.getAuthToken().secret);

        service.getActivities(token_secret, new Callback<List<AirlineActivity>>() {
            @Override
            public void success(Result<List<AirlineActivity>> result) {

            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

    }
}
