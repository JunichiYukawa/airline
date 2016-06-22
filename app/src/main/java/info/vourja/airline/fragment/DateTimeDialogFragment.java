package info.vourja.airline.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import info.vourja.airline.R;
import info.vourja.airline.util.datetime;

/**
 * Created by vourja on 2016/06/23.
 */
public class DateTimeDialogFragment extends DialogFragment {

    private TextEditListener textEditListener;

    public DateTimeDialogFragment() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        if(this.textEditListener == null) { return null; }

        final EditText editText = this.textEditListener.getEditText();
        int title_id = getArguments().getInt("title_id");

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_datetime);
        dialog.setTitle(title_id);
        DatePicker dp = (DatePicker)dialog.findViewById(R.id.dialog_date_picker);
        TimePicker tp = (TimePicker)dialog.findViewById(R.id.dialog_time_picker);

        tp.setIs24HourView(true);

        DatePicker.OnDateChangedListener dp_listener = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar date = (Calendar)editText.getTag();
                if(date == null) { date = new GregorianCalendar(); }
                date.set(year, monthOfYear, dayOfMonth);
                editText.setTag(date);
                editText.setText(datetime.Cal2Str(date));
            }
        };

        TimePicker.OnTimeChangedListener tp_listener = new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar date = (Calendar)editText.getTag();
                if(date == null) { date = new GregorianCalendar(); }
                date.set(
                        date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE),
                        hourOfDay, minute);
                editText.setTag(date);
                editText.setText(datetime.Cal2Str(date));
            }
        };

        Calendar date = (Calendar)editText.getTag();
        if(date != null) {
            dp.init(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), dp_listener);
            tp.setCurrentHour(date.get(Calendar.HOUR));
            tp.setCurrentMinute(date.get(Calendar.MINUTE));
            tp.setOnTimeChangedListener(tp_listener);
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            dp.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), dp_listener);
            tp.setCurrentHour(calendar.get(Calendar.HOUR));
            tp.setCurrentMinute(calendar.get(Calendar.MINUTE));
            tp.setOnTimeChangedListener(tp_listener);
        }

        return dialog;
    }

    public void bindTextEditListener(TextEditListener listener) {
        this.textEditListener = listener;
    }

    public void unbindTextEditListener() {
        this.textEditListener = null;
    }

    public interface TextEditListener {
        public EditText getEditText();
    }
}
