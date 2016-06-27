package info.vourja.airline.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.vourja.airline.AirLineApplication;
import info.vourja.airline.Model.AirLineActivity;
import info.vourja.airline.Model.Line;
import info.vourja.airline.NetService.AirLineService;
import info.vourja.airline.NetService.util;
import info.vourja.airline.R;
import info.vourja.airline.activity.CaptureActivityAnyOrientation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LineFragment extends Fragment {

    final String TAG = LineFragment.class.getSimpleName();

    @BindView(R.id.lines_total)
    TextView lines_total;

    @BindView(R.id.reserved_total)
    TextView reserved_total;

    @BindView(R.id.done_total)
    TextView txt_done_total;

    @BindView(R.id.current_number)
    TextView current_number;

    @BindView(R.id.activity_name)
    TextView activity_title;

    private Line currentLine;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_line, container, false);

        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        String act_uuid = getArguments().getString("uuid");

        // Activityの情報(必要か?)
        getActivityInfo(act_uuid);

        // 今処理しているLineの情報
        getCurrentLineInfo(act_uuid);
    }

    private void getActivityInfo(String act_uuid) {
        // Activityの情報(必要か?)
        AirLineApplication application = (AirLineApplication)getActivity().getApplication();
        String token = application.getAccessToken();
        AirLineService service = application.getAirlineService();
        String token_secret = util.getCredentials(token, "unused");

        Call<AirLineActivity> call = service.getActivity(token_secret, act_uuid);
        call.enqueue(new retrofit2.Callback<AirLineActivity>() {
            @Override
            public void onResponse(Call<AirLineActivity> call, Response<AirLineActivity> response) {
                if(response.body() != null ) {
                    String activity_name = response.body().getName();
                    activity_title.setText(activity_name);

                    if(response.body().getLines() != null){
                        int reserved_line = 0;
                        int rest_line = 0;
                        int done_total = 0;
                        for(Line line : response.body().getLines()) {
                            if(line.getPass_date() != null) {
                                reserved_line++;
                            } else {
                                reserved_line++;
                                if(line.getArrived_date() != null) { rest_line++; }
                            }
                        }
                        txt_done_total.setText(String.valueOf(done_total));
                        lines_total.setText(String.valueOf(rest_line));
                        reserved_total.setText(String.valueOf(reserved_line));
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<AirLineActivity> call, Throwable t) {

            }
        });
    }

    private void getCurrentLineInfo(String act_uuid) {
        getLineByExtra(act_uuid, "current");
    }

    private void getLineByExtra(String act_uuid, String type) {
        AirLineApplication application = (AirLineApplication)getActivity().getApplication();
        String token = application.getAccessToken();
        AirLineService service = application.getAirlineService();
        String token_secret = util.getCredentials(token, "unused");

        Call<Line> call = service.getLineByExtra(token_secret, act_uuid, type);
        call.enqueue(new retrofit2.Callback<Line>() {
            @Override
            public void onResponse(Call<Line> call, Response<Line> response) {
                if(response.code() == 404) {
                    current_number.setText("No Line");
                } else {
                    if(response.body() != null ) {
                        currentLine = response.body();
                        current_number.setText(String.valueOf(response.body().getNumber()));
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<Line> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.qr_button)
    void onClickQR() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @OnClick(R.id.next_button)
    void onClickNext() {
        if(currentLine == null) { return; }

        AirLineApplication application = (AirLineApplication)getActivity().getApplication();
        String token = application.getAccessToken();
        AirLineService service = application.getAirlineService();
        String token_secret = util.getCredentials(token, "unused");

        Call<Line> call = service.finishLine(token_secret, currentLine.getUuid().toString());
        call.enqueue(new Callback<Line>() {
            @Override
            public void onResponse(Call<Line> call, Response<Line> response) {
                if(response.code() == 200) {
                    String act_uuid = getArguments().getString("uuid");
                    getCurrentLineInfo(act_uuid);
                } else {
                }
            }

            @Override
            public void onFailure(Call<Line> call, Throwable t) {

            }
        });
    }

}
