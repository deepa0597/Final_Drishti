package com.example.aakash.drishti;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.os.Environment;
import android.os.TestLooperManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class SaveGestureActivity extends Activity implements TextToSpeech.OnInitListener {
    private GestureLibrary gLib;
    private static final String TAG = "SaveGestureActivity";
    private boolean mGestureDrawn;                      //tc
    private Gesture mCurrentGesture,mCurrentGesture1;                    //tc
    private String mGesturename,mGesturename1;//tc
    Button btnSaveGesture;
    TextToSpeech tts;
    //Button btnDeleteGesture;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_gesture);
        tts = new TextToSpeech(SaveGestureActivity.this, this);
        btnSaveGesture = (Button)findViewById(R.id.btnSave);
       // btnDeleteGesture = (Button)findViewById(R.id.btnDelete);

        btnSaveGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mGestureDrawn){
                    getName();
                } else{
                    showToast(getString(R.string.no_gesture));
                }

            }
        });

       /* btnDeleteGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SaveGestureActivity.this, GestureListActivity.class);
                startActivity(i);
            }
        });*/

        Log.d(TAG, "path = " + Environment.getExternalStorageDirectory().getAbsolutePath());

//        openOptionsMenu();

        gLib = GestureLibraries.fromFile(getExternalFilesDir(null) + "/" + "gesture.txt");
        gLib.load();

        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.save_gesture);
        gestures.addOnGestureListener(mGestureListener);

        resetEverything();
    }

    /**
     * our gesture listener
     */
    private GestureOverlayView.OnGestureListener mGestureListener = new GestureOverlayView.OnGestureListener() {
        @Override
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
            mGestureDrawn = true;
            Log.d(TAG, "andar");
        }

        @Override
        public void onGesture(GestureOverlayView overlay, MotionEvent event) {
            mCurrentGesture = overlay.getGesture();
        }

        @Override
        public void onGestureEnded(GestureOverlayView gestureView, MotionEvent motion) {
            Log.d(TAG, "bahar");
        }

        @Override
        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
            Log.d(TAG, "cancel");
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.DELETE:
                reDrawGestureView();
                break;

            case R.id.Save:
                if(mGestureDrawn){
                   getName();
                } else{
                    showToast(getString(R.string.no_gesture));
                }

                //TODO : Save gesture as image, dont delete this code
                /*
                String pattern = "mm ss";
                SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                String time = formatter.format(new Date());
                String path = ("/d-codepages" + time + ".png");

                File file = new File(Environment.getExternalStorageDirectory()
                        + path);

                try {
                    //DrawBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                    //new FileOutputStream(file));
                    Toast.makeText(this, "File Saved ::" + path, Toast.LENGTH_SHORT)
                            .show();
                } catch (Exception e) {
                    Toast.makeText(this, "ERROR" + e.toString(), Toast.LENGTH_SHORT)
                            .show();
                }   */
        }
        return super.onOptionsItemSelected(item);
    }
//Change UI for alert box
    private void getName()
    {
        AlertDialog.Builder namePopup = new AlertDialog.Builder(this);
        namePopup.setTitle(getString(R.string.enter_name));
        //namePopup.setMessage(R.string.enter_name);


        final TextView nameField = new TextView(this);
        namePopup.setView(nameField);
        namePopup.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //db.updateExistingMeasurement(measurement);
                nameField.setText("Camera");
                mGesturename = nameField.getText().toString();
                saveGesture();
                tts.speak("Saved Gesture",TextToSpeech.QUEUE_FLUSH,null);
                Intent intent = new Intent(SaveGestureActivity.this, GestureActivity.class);
                startActivity(intent);
               /* if (!nameField.getText().toString().matches("Camera")) {
                    mGesturename = nameField.getText().toString();
                    saveGesture();
                } else {
                    getName();  //TODO : set name field with old name string user added
                    showToast(getString(R.string.invalid_name));
                }*/
                //return;
            }
        });
        namePopup.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tts.speak("Gesture Cancelled", TextToSpeech.QUEUE_FLUSH,null);
                mGesturename = "";
                return;
            }
        });

        namePopup.show();


    }

    private void showToast(String string){
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    //private void checkforDuplicateName

    private void saveGesture() {
        mGesturename1=mGesturename;
        mCurrentGesture1=mCurrentGesture;
         if(mGesturename.matches("Camera")) {
             gLib.removeEntry(mGesturename);
             gLib.save();
             mGesturename = "";
         }
        //TODO: check kar k same naam valu gesture che k nai

        gLib.addGesture(mGesturename1, mCurrentGesture1);


        if (!gLib.save()) {
            Log.e(TAG, "gesture not saved!");
        }else {
            showToast(getString(R.string.gesture_saved) + getExternalFilesDir(null) + "/gesture.txt");
        }
        reDrawGestureView();
        // }
    }
    private void resetEverything(){
        mGestureDrawn = false;
        mCurrentGesture = null;
        mGesturename = "";
    }

    private void reDrawGestureView() {
        setContentView(R.layout.save_gesture);
        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.save_gesture);
        gestures.removeAllOnGestureListeners();
        gestures.addOnGestureListener(mGestureListener);
        resetEverything();
    }

    public void onInit(int initStatus) {

        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if(tts.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                tts.setLanguage(Locale.US);

            tts.speak("Save Password", TextToSpeech.QUEUE_FLUSH, null);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }
}