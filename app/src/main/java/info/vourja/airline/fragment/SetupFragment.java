package info.vourja.airline.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.vourja.airline.R;
import info.vourja.airline.util.datetime;

/**
 * Created by vourja on 2016/06/22.
 */
public class SetupFragment extends Fragment{

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


}
