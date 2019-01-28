package com.example.aakash.drishti;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;



public class PhoneBook extends Activity {
    Button btnMic;
    EditText load;
    LinearLayout nextCancel;
    Button cancel,next;
    EditText etName;
    TextToSpeech tts;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book);
        etName = (EditText) findViewById(R.id.et_name);
        btnMic = (Button)findViewById(R.id.btnMic);
        btnMic.setVisibility(View.VISIBLE);
        nextCancel = (LinearLayout)findViewById(R.id.layoutcancelNext);
        cancel = (Button)findViewById(R.id.btnCancelName);
        next = (Button)findViewById(R.id.btnNextAct);
        tts = new TextToSpeech(PhoneBook.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!= TextToSpeech.ERROR)
                {
                    tts.setLanguage(Locale.UK);
                }
            }
        });

        cancel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                tts.speak("CLEAR NAME ",TextToSpeech.QUEUE_FLUSH,null);
                return false;
            }
        });
        next.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tts.speak("NEXT ",TextToSpeech.QUEUE_FLUSH,null);
                return false;
            }
        });

        tts.speak("Press at the bottom to speak name", TextToSpeech.QUEUE_FLUSH,null);

        // Creating a button click listener for the "Add Contact" button
        OnClickListener addClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Getting reference to Name EditText


                // Getting reference to Mobile EditText
                EditText etMobile = (EditText) findViewById(R.id.et_mobile_phone);

                // Getting reference to HomePhone EditText


                ArrayList<ContentProviderOperation> ops =
                        new ArrayList<ContentProviderOperation>();

                int rawContactID = ops.size();

                // Adding insert operation to operations list
                // to insert a new raw contact in the table ContactsContract.RawContacts
                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(RawContacts.ACCOUNT_NAME, null)
                        .build());

                // Adding insert operation to operations list
                // to insert display name in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(StructuredName.DISPLAY_NAME, etName.getText().toString())
                        .build());

                // Adding insert operation to operations list
                // to insert Mobile Number in the table ContactsContract.Data
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                        .withValue(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                        .withValue(Phone.NUMBER, etMobile.getText().toString())
                        .withValue(Phone.TYPE, CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());



                try{
                    // Executing all the insert operations as a single database transaction
                    getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                    Toast.makeText(getBaseContext(), "Contact is successfully added", Toast.LENGTH_SHORT).show();
                }catch (RemoteException e) {
                    e.printStackTrace();
                }catch (OperationApplicationException e) {
                    e.printStackTrace();
                }
            }
        };

        // Creating a button click listener for the "Add Contact" button
        OnClickListener contactsClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Creating an intent to open Android's Contacts List
                Intent contacts = new Intent(Intent.ACTION_VIEW,ContactsContract.Contacts.CONTENT_URI);

                // Starting the activity
                startActivity(contacts);
            }
        };

        // Getting reference to "Add Contact" button
/*        final Button btnAdd = (Button) findViewById(R.id.btn_add);

        // Getting reference to "Contacts List" button
        Button btnContacts = (Button) findViewById(R.id.btn_contacts);

        Button btnCall = (Button) findViewById(R.id.btn_call);

        // Setting click listener for the "Add Contact" button
        btnAdd.setOnClickListener(addClickListener);

        // Setting click listener for the "List Contacts" button
        btnContacts.setOnClickListener(contactsClickListener);

        btnCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PhoneBook.this,PhoneCall.class);
                startActivity(i);
                finish();
            }
        });*/
        btnMic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tts.speak("PRESS TO SPEAK NAME ",TextToSpeech.QUEUE_FLUSH,null);
                return false;
            }
        });

        btnMic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, new Locale("hi", "IN"));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 10);

                    nextCancel.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(PhoneBook.this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.getText().clear();
            }
        });
        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PhoneBook.this,NumberPhoneBook.class);
                i.putExtra("Name",etName.getText().toString());
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tts.speak("Press at the bottom to speak name", TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        load = (EditText) findViewById(R.id.et_name);




        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.d("sst",result.get(0));
                    load.setText(result.get(0));
                }
                break;


        }
    }


    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getLayoutInflater().inflate(R.layout.activity_phone_book,null);
        return true;
    }
}
