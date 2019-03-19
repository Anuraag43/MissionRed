package com.example.missionred;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final EditText username = (EditText) findViewById(R.id.editText1);
        final EditText password = (EditText)findViewById(R.id.editText2);
        final Button login = (Button)findViewById(R.id.button1);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

               SharedPreferences preferences = getSharedPreferences("signup", Context.MODE_PRIVATE);

               //String userdetails = preferences.getString(username + password + "data", "username or password is incorrect");






            }
        });


    }

    public void signup(View view)
    {
        Intent register = new Intent(MainActivity.this, Registration.class);
        startActivity(register);
    }

    public void login(View view)

    {
        Intent log = new Intent(MainActivity.this, Search.class);
        startActivity(log);


    }

}
