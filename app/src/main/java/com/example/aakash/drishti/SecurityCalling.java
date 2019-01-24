package com.example.aakash.drishti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecurityCalling extends AppCompatActivity {
    Button callPerson;
    EditText person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_calling);
        callPerson = (Button)findViewById(R.id.passCall);
        person=(EditText)findViewById(R.id.callPerson);

        callPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = person.getText().toString();
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.setClassName("com.google.android.googlequicksearchbox", "com.google.android.googlequicksearchbox.SearchActivity");
                intent.putExtra("query", query);
                startActivity(intent);
            }
        });

    }
}
