package com.example.aakash.drishti;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

/**
 * Created by manan on 2/1/2015.
 */
public class GestureListActivity1 extends Activity implements TextToSpeech.OnInitListener {
    private static final String TAG = "GestureListActivity";
    private String mCurrentGestureNaam,navuNaam;
    private ListView mGestureListView;
    private static ArrayList<GestureHolder> mGestureList;
    private GestureAdapter mGestureAdapter;
    private GestureLibrary gLib;
    //private ImageView mMenuItemView;
    Button btnCancel;
    TextToSpeech tts;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_list1);

        tts = new TextToSpeech(this, this);


        btnCancel=(Button)findViewById(R.id.cancelBtn);
        Log.d(TAG, getApplicationInfo().dataDir);

        openOptionsMenu();

        mGestureListView = (ListView) findViewById((R.id.gestures_list));
        mGestureList = new ArrayList<GestureHolder>();

        listBanav();
        if(mGestureList.size()!=0)
        {
            Intent i =new Intent(GestureListActivity1.this,GestureActivity.class);
            startActivity(i);
            finish();
        }
        else
        {
            tts.speak("Click anywhere on the screen to save password!", TextToSpeech.QUEUE_FLUSH, null);
        }
        mGestureAdapter = new GestureAdapter(mGestureList, GestureListActivity1.this);
        mGestureListView.setLongClickable(true);
        mGestureListView.setAdapter(mGestureAdapter);

        // displays the popup context top_menu to either delete or resend measurement
        registerForContextMenu(mGestureListView);

    }

    @Override
    public void onResume(){
        super.onResume();
        setContentView(R.layout.activity_gesture_list1);
        Log.d(TAG, getApplicationInfo().dataDir);


        //openOptionsMenu();

        mGestureListView = (ListView) findViewById((R.id.gestures_list));
        mGestureList = new ArrayList<GestureHolder>();
        listBanav();
        mGestureAdapter = new GestureAdapter(mGestureList, GestureListActivity1.this);
        mGestureListView.setLongClickable(true);

        mGestureListView.setAdapter(mGestureAdapter);
        // displays the popup context top_menu to either delete or resend measurement
        registerForContextMenu(mGestureListView);
    }

    /**
     * badha gestures laine emne list ma mukse
     */
    private void listBanav() {
        try {
            mGestureList = new ArrayList<GestureHolder>();
            gLib = GestureLibraries.fromFile(getExternalFilesDir(null) + "/" + "gesture.txt");
            gLib.load();
            Set<String> gestureSet = gLib.getGestureEntries();
            for(String gestureNaam: gestureSet){
                ArrayList<Gesture> list = gLib.getGestures(gestureNaam);
                for(Gesture g : list) {
                    mGestureList.clear();
                    mGestureList.add(new GestureHolder(g, gestureNaam));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void populateMenu(View view){
        //ImageView idView = (ImageView) view.findViewById(R.id.gesture_id);
        //Log.d(TAG, "ha ha" + idView.getText().toString());
        LinearLayout vwParentRow = (LinearLayout)view.getParent().getParent();
        TextView tv = (TextView)vwParentRow.findViewById(R.id.gesture_name_ref);
        mCurrentGestureNaam = tv.getText().toString();
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.gesture_item_options, popup.getMenu());
        popup.show();
    }

    public void addButtonClick(View view)
    { tts.speak("Draw a password and click on top to save ", TextToSpeech.QUEUE_FLUSH, null);
        Intent saveGesture = new Intent(GestureListActivity1.this, SaveGestureActivity.class);
        startActivity(saveGesture);
        finish();
    }

    public void testButtonClick(View view){
        Intent testGesture = new Intent(GestureListActivity1.this, GestureActivity.class);
        startActivity(testGesture);
        finish();
    }

    public void deleteButtonClick(MenuItem item){
        gLib.removeEntry(mCurrentGestureNaam);
        gLib.save();
        mCurrentGestureNaam = "";
        onResume();
    }

    public void renameButtonClick(MenuItem item){

        AlertDialog.Builder namePopup = new AlertDialog.Builder(this);
        namePopup.setTitle(getString(R.string.enter_new_name));
        //namePopup.setMessage(R.string.enter_name);

        final EditText nameField = new EditText(this);
        namePopup.setView(nameField);

        namePopup.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!nameField.getText().toString().matches("")) {
                    navuNaam = nameField.getText().toString();
                    saveGesture();
                } else {
                    renameButtonClick(null);  //TODO : validation
                    showToast(getString(R.string.invalid_name));
                }
            }
        });
        namePopup.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                navuNaam = "";
                mCurrentGestureNaam = "";
                return;
            }
        });

        namePopup.show();
    }

    private void saveGesture() {
        ArrayList<Gesture> list = gLib.getGestures(mCurrentGestureNaam);


        if (list.size() > 0) {

            gLib.removeEntry(mCurrentGestureNaam);
            gLib.addGesture(navuNaam, list.get(0));
            if (gLib.save()) {
                Log.e(TAG, "gesture renamed!");
                onResume();
            }
        }
        navuNaam = "";
    }
    private void showToast(String string){
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    public void onInit(int initStatus) {

        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if(tts.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                tts.setLanguage(Locale.US);

            tts.speak("Welcome to Drishti!", TextToSpeech.QUEUE_FLUSH, null);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }
}