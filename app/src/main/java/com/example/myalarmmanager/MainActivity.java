package com.example.myalarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    private AlarmReceiver alarmReceiver;
    private TextView tvOnceDate;
    private TextView tvOnceTime;
    private EditText edtOnceMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// inisiasi view untuk one time alarm

        ImageButton btnOnceDate = findViewById(R.id.btn_one_date);
        ImageButton btnOnceTime = findViewById(R.id.btn_once_time);
        Button btnSetOnce = findViewById(R.id.btn_set_once_alarm);
        tvOnceDate = findViewById(R.id.tv_once_date);
        tvOnceTime = findViewById(R.id.tv_once_time);
        edtOnceMessage = findViewById(R.id.edt_once_message);

        // listener one time alarm

        btnOnceDate.setOnClickListener(this);
        btnOnceTime.setOnClickListener(this);
        btnSetOnce.setOnClickListener(this);

        alarmReceiver = new AlarmReceiver();

    }

    private final static String DATE_PICKER_TAG = "date";
    private final static String TIME_PICKER_ONCE_TAG = "timePicker ONce";
    private final static String TIME_PICKER_REPEAT_TAG = "timePicker repeat";

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_one_date){
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(),DATE_PICKER_TAG );
        }else if (v.getId() == R.id.btn_once_time){
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            timePickerFragment.show(getSupportFragmentManager(), TIME_PICKER_ONCE_TAG);
        }else if (v.getId() == R.id.btn_set_once_alarm){
            String onceDate = tvOnceDate.getText().toString();
            String onceTIme = tvOnceTime.getText().toString();
            String onceMessage = edtOnceMessage.getText().toString();

            alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME, onceDate, onceTIme, onceMessage );
        }
    }

    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth){
        //siapkan date formatter nya terlebih dahulu
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        //set text dari textview once
        tvOnceDate.setText(dateFormat.format(calendar.getTime()));
    }


    public void onDialogTimeSet(String tag, int hourOfDay, int minute){
        //siapkan time formatternya terlebih dahulu
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        //set text dari textview berdasarkan tag
        switch (tag){
            case TIME_PICKER_ONCE_TAG:
                tvOnceTime.setText(dateFormat.format(calendar.getTime()));
                break;
            case TIME_PICKER_REPEAT_TAG:
                break;
            default:
                break;
        }
    }



}
