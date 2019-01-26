package com.example.aakash.drishti;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GestureActivity extends Activity {
    private GestureLibrary gLib;
    private static final String TAG = "GestureActivity";
    TextView application;
    Button btnGesture;
    String change;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        application = (TextView) findViewById(R.id.application);
        btnGesture = (Button) findViewById(R.id.btngesture);

        btnGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestureActivity.this, GestureActivity1.class);
                startActivity(intent);
            }
        });


        openOptionsMenu();
        gLib = GestureLibraries.fromFile(getExternalFilesDir(null) + "/" + "gesture.txt");
        gLib.load();

        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.addOnGesturePerformedListener(handleGestureListener);
        gestures.setGestureStrokeAngleThreshold(90.0f);
    }

    /**
     * our gesture listener
     */
    private OnGesturePerformedListener handleGestureListener = new OnGesturePerformedListener() {
        @Override
        public void onGesturePerformed(GestureOverlayView gestureView,
                                       Gesture gesture) {
            application.setText("DRAW");
            ArrayList<Prediction> predictions = gLib.recognize(gesture);

            // one prediction needed
            if (predictions.size() > 0) {
                Prediction prediction = predictions.get(0);
                // checking prediction
                if (prediction.score > 1.0) {
                    // and action
                   /* Toast.makeText(GestureActivity.this, prediction.name,
                            Toast.LENGTH_SHORT).show();*/
                    application.setText(prediction.name);

                    change = application.getText().toString();
                    if(change.equals("Camera"))
                    {
                        Intent i = new Intent(GestureActivity.this, MainActivity.class);
                        startActivity(i);

                    }
                    else
                    {
                        Toast.makeText(GestureActivity.this,"Incorrect Password",Toast.LENGTH_LONG).show();
                    }


                }
            }
        }
    };
}