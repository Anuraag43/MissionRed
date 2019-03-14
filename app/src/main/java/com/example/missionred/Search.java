package com.example.missionred;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Search extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void call(View view)
    {
        Intent contact = new Intent(Intent.ACTION_DIAL);
        startActivity(contact);
    }
}
