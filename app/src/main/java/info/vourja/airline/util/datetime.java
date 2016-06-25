package info.vourja.airline.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import info.vourja.airline.R;
import info.vourja.airline.fragment.DateTimeDialogFragment;

/**
 * Created by vourja on 2016/06/23.
 */
public class datetime {

    public static final String format = "yyyy/MM/dd HH:mm";

    public static String Cal2Str(Calendar cal) {
        return Cal2Str(cal, format);
    }

    public static String Cal2Str(Calendar cal, String Pattern) {
        if(cal == null || Pattern == null) { return null; }

        return new SimpleDateFormat(Pattern).format(cal.getTime());
    }

    public static void showDateTimePicker(FragmentManager manager, final EditText editText, int title_id) {
        DateTimeDialogFragment fragment = new DateTimeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("title_id", title_id);
        fragment.setArguments(bundle);
        fragment.bindTextEditListener(new DateTimeDialogFragment.TextEditListener() {
            @Override
            public EditText getEditText() {
                return editText;
            }
        });
        fragment.show(manager, "datetime");
    }

    public static Date parse(String str) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            return simpleDateFormat.parse(str);
        } catch ( ParseException e ) {
            return null;
        }
    }

    public static String date2isostr(Date date) {
        if(date == null) { return null; }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return simpleDateFormat.format(date);
        } catch ( IllegalArgumentException e ) {
            return null;
        }
    }
}
