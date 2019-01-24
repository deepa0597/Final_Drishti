package com.example.aakash.drishti;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CurrentDateTime extends AppCompatActivity {
TextView tvDate,tvTime,t,d;
TextToSpeech t1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_date_time);
        tvDate=(TextView)findViewById(R.id.tvDate);
        tvTime=(TextView)findViewById(R.id.tvTime);
        t=(TextView)findViewById(R.id.t);
        d=(TextView)findViewById(R.id.d);
        Calendar cal = Calendar.getInstance();
        int hour_of_day = cal.get(Calendar.HOUR_OF_DAY);
        int minute= cal.get(Calendar.MINUTE);
        int year = cal.get(Calendar.YEAR);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String date = df.format(Calendar.getInstance().getTime());
        String time = dateFormat.format(Calendar.getInstance().getTime());
        tvTime.setText(time);
        tvDate.setText(date);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!=TextToSpeech.ERROR)
                {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                String time = dateFormat.format(Calendar.getInstance().getTime());
                t1.speak(time,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                String time = dateFormat.format(Calendar.getInstance().getTime());
                t1.speak(time,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                t1.speak(date,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                t1.speak(date,TextToSpeech.QUEUE_FLUSH,null);
            }
        });


    }
}
