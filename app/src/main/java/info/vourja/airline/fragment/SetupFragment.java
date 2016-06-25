package info.vourja.airline.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.vourja.airline.AirLineApplication;
import info.vourja.airline.Model.AirLineActivity;
import info.vourja.airline.NetService.AirLineService;
import info.vourja.airline.NetService.util;
import info.vourja.airline.R;
import info.vourja.airline.activity.LineActivity;
import info.vourja.airline.activity.MainActivity;
import info.vourja.airline.util.datetime;
import retrofit2.Call;
import retrofit2.Response;

public class SetupFragment extends Fragment{

    private final String TAG = SetupFragment.class.getSimpleName();

    @BindView(R.id.activity_name) EditText activity_name;
    @BindView(R.id.activity_location) EditText activity_location;
    @BindView(R.id.activity_start) EditText activity_start;
    @BindView(R.id.activity_end) EditText activity_end;
    @BindView(R.id.activity_description) EditText activity_description;
    @BindView(R.id.activity_url) EditText activity_url;
    @BindView(R.id.activity_template) EditText activity_template;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_setup, container, false);

        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.activity_start)
    void onClickStart(EditText editText) {
        datetime.showDateTimePicker(getFragmentManager(), editText, R.string.activity_startdate_hint);
    }

    @OnClick(R.id.activity_end)
    void onClickEnd(EditText editText) {
        datetime.showDateTimePicker(getFragmentManager(), editText, R.string.activity_enddate_hint);
    }

    @OnClick(R.id.start_button)
    void onClickStartButton() {
        AirLineActivity act = new AirLineActivity();

        Calendar start = ((Calendar)activity_start.getTag());
        Calendar end = ((Calendar)activity_end.getTag());

        Date start_date = start != null ? start.getTime() : null;
        Date end_date = end != null ? end.getTime() : null;

        act.setName(activity_name.getText().toString());
        act.setLocation(activity_location.getText().toString());
        act.setStart_date(datetime.date2isostr(start_date));
        act.setEnd_date(datetime.date2isostr(end_date));
        act.setDescription(activity_description.getText().toString());
        act.setUrl(activity_url.getText().toString());
        act.setTemplate(activity_template.getText().toString());

        start_activity(act);
    }

    private void start_activity(AirLineActivity act) {
        AirLineApplication application = ((AirLineApplication)getActivity().getApplication());
        AirLineService service = application.getAirlineService();
        String token = application.getAccessToken();
        String token_secret = util.getCredentials(token, "unused");

        Call<AirLineActivity> call = service.postActivities(token_secret, act);
        call.enqueue(new retrofit2.Callback<AirLineActivity>() {
            @Override
            public void onResponse(Call<AirLineActivity> call, Response<AirLineActivity> response) {

                Intent intent = new Intent(getActivity(), LineActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<AirLineActivity> call, Throwable t) {

            }
        });
    }
}
