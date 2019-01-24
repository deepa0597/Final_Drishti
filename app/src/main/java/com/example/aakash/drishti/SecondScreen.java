package com.example.aakash.drishti;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Locale;



public class SecondScreen extends Fragment implements View.OnClickListener {
    private View mView;
    Button button5, button6, button7, button8;
    TextToSpeech tts;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondScreen() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_second_screen, container, false);


        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });

        button5 = (Button) mView.findViewById(R.id.five);
        button5.setOnClickListener(this);
        button6 = (Button) mView.findViewById(R.id.six);
        button6.setOnClickListener(this);
        button7 = (Button) mView.findViewById(R.id.seven);
        button7.setOnClickListener(this);
        button8 = (Button) mView.findViewById(R.id.eight);
        button8.setOnClickListener(this);


        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.five:

                tts.speak("Private Chat Room", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(getActivity(), MainActivityPCR.class);
                startActivity(intent);
                break;
            case R.id.six:
                tts.speak("Private Chat Room", TextToSpeech.QUEUE_FLUSH, null);
               Boolean a = openApp(getContext(),"org.tensorflow.demo");
                break;
            case R.id.seven:
                tts.speak("Private Chat Room", TextToSpeech.QUEUE_FLUSH, null);
                Boolean b = openApp1(getContext(),"com.teapink.ocr_reader");
                break;
            case R.id.eight:
                tts.speak("Private Chat Room", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent3 = new Intent(getActivity(), MainActivityPCR.class);
                startActivity(intent3);
                break;
        }
    }

    private Boolean openApp1(Context context, String s) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(s);
            if (i == null) {
                return false;
                //throw new ActivityNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private Boolean openApp(Context context, String s) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(s);
            if (i == null) {
                return false;
                //throw new ActivityNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}