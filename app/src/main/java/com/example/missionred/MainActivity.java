package com.example.missionred;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signup(View view)
    {
        Intent register = new Intent(MainActivity.this, Registration.class);
        startActivity(register);
    }

    public void login(View view)
    {
        Intent find = new Intent(MainActivity.this, Search.class);
        startActivity(find);
    }
}
