package com.example.missionred;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Registration extends AppCompatActivity {



     EditText fullname;
     RadioButton sexf;
     RadioButton sexm;
     EditText email;
     EditText mobile;
     EditText address1;
    EditText address2;
    EditText bloodgroup;
    EditText username;
    EditText password;
    EditText conpassword;
    EditText dist;
    CheckBox checkB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fullname = (EditText) findViewById(R.id.editText3);
        sexf = (RadioButton) findViewById(R.id.radioButton1);
        sexm = (RadioButton)findViewById(R.id.radioButton2);
        email = (EditText) findViewById(R.id.editText4);
        mobile = (EditText) findViewById(R.id.editText5);
        address1 = (EditText) findViewById(R.id.editText6);
        address2 = (EditText) findViewById(R.id.editText7);
        bloodgroup = (EditText) findViewById(R.id.editText8);
        username = (EditText) findViewById(R.id.editText9);
        password = (EditText) findViewById(R.id.editText10);
        conpassword = (EditText) findViewById(R.id.editText11);
        dist = (EditText)findViewById(R.id.editText12);
        checkB = (CheckBox)findViewById(R.id.checkBox1);
    }



    public void submit(View view)
    {
        SharedPreferences sharedpref = getSharedPreferences("signup", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putString("fullname",fullname.getText().toString());
        editor.putBoolean("sexf",sexf.callOnClick());
        editor.putBoolean("sexm",true);
        editor.putString("email",email.getText().toString());
        editor.putString("mobile",mobile.getText().toString());
        editor.putString("address1",address1.getText().toString());
        editor.putString("address2",address2.getText().toString());
        editor.putString("bloodgroup",bloodgroup.getText().toString());
        


        editor.putString("username",username.getText().toString());
       editor.putString("password",password.getText().toString());
       editor.putString("confirmpass",conpassword.getText().toString());
        editor.putString("distance",dist.getText().toString());
      editor.putBoolean("checkBox", true);
        editor.apply();

        

        Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();
        Intent submit = new Intent(Registration.this, Search.class);
        
        startActivity(submit);

    }


}
