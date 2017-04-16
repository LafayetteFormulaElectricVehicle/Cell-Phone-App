package com.cellvscada.lfev2017.Tools;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by ktdilsiz on 4/14/17.
 */

public class DateAndTimeViewGenerator{

    Context context;
    LinearLayout linearLayout;

    EditTextNumeric day;
    EditTextNumeric month;
    EditTextNumeric year;

    EditTextNumeric hour;
    EditTextNumeric minute;
    EditTextNumeric second;

    String title;

    public DateAndTimeViewGenerator(Context context, String title){
        this.context = context;
        this.title = title;
        setup();
    }

    private void setup(){
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(50,20,50,20);

        LinearLayout dateLayout = new LinearLayout(context);
        dateLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout timeLayout = new LinearLayout(context);
        timeLayout.setOrientation(LinearLayout.HORIZONTAL);

        day = new EditTextNumeric(context);
        day.setHint("Day");
        day.setMaxLength(2);
        day.setMinValue(0);
        day.setMaxValue(31);
        day.setLayoutParams(
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );


        month = new EditTextNumeric(context);
        month.setHint("Month");
        month.setMaxLength(2);
        month.setMinValue(0);
        month.setMaxValue(12);
        month.setLayoutParams(
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        year = new EditTextNumeric(context);
        year.setHint("Year");
        year.setMaxLength(4);
        year.setMinValue(0);
        year.setMaxValue(2030);
        year.setLayoutParams(
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        TextView slash = new TextView(context);
        slash.setLayoutParams(
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        slash.setText("/");
        TextView slash2 = new TextView(context);
        slash2.setLayoutParams(
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        slash2.setText("/");

        dateLayout.addView(year);
        dateLayout.addView(slash);
        dateLayout.addView(month);
        dateLayout.addView(slash2);
        dateLayout.addView(day);

        ////////////////TIME

        hour = new EditTextNumeric(context);
        hour.setHint("Hour");
        hour.setMaxLength(2);
        hour.setMinValue(0);
        hour.setMaxValue(23);
        hour.setLayoutParams(
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        minute = new EditTextNumeric(context);
        minute.setHint("Min");
        minute.setMaxLength(2);
        minute.setMinValue(0);
        minute.setMaxValue(59);
        minute.setLayoutParams(
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        second = new EditTextNumeric(context);
        second.setHint("Sec");
        second.setMaxLength(2);
        second.setMinValue(0);
        second.setMaxValue(59);
        second.setLayoutParams(
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        TextView dots = new TextView(context);
        dots.setLayoutParams(
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        dots.setText(":");

        TextView dots2 = new TextView(context);
        dots2.setLayoutParams(
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        dots2.setText(":");

        timeLayout.addView(hour);
        timeLayout.addView(dots);
        timeLayout.addView(minute);
        timeLayout.addView(dots2);
        timeLayout.addView(second);

        TextView titleView = new TextView(context);
        titleView.setText(title);

        linearLayout.addView(titleView);
        linearLayout.addView(dateLayout);
        linearLayout.addView(timeLayout);
    }

    public LinearLayout getPicker(){
        return linearLayout;
    }

    public String getHour(){
        return hour.getText().toString();
    }

    public String getMinute(){
        return minute.getText().toString();
    }

    public String getSecond(){
        return second.getText().toString();
    }

    public String getYear(){
        return year.getText().toString();
    }

    public String getMonth(){
        return month.getText().toString();
    }

    public String getDay(){
        return day.getText().toString();
    }

    public boolean isNull(){

        String hourString = hour.getText().toString().trim();
        String minuteString = minute.getText().toString().trim();
        String secondString = second.getText().toString().trim();
        String yearString = year.getText().toString().trim();
        String monthString = month.getText().toString().trim();
        String dayString = day.getText().toString().trim();

        return TextUtils.isEmpty(hourString) ||
                TextUtils.isEmpty(minuteString) ||
                TextUtils.isEmpty(secondString) ||
                TextUtils.isEmpty(yearString) ||
                TextUtils.isEmpty(monthString) ||
                TextUtils.isEmpty(dayString);
    }

    public String getDate(){

        return getYear() +
                "-" +
                getMonth() +
                "-" +
                getDay() +
                " " +
                getHour() +
                ":" +
                getMinute() +
                ":" +
                getSecond();
    }

}
