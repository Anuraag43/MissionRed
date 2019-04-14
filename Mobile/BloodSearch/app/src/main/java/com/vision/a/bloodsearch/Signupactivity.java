package com.vision.a.bloodsearch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class Signupactivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText userName,name,phoneNumber,doornumber,cityNumber,zipcode,bloodGroup;
    private EditText mPasswordView,confirmPassword,milesToTravel;
    private RadioGroup gender;
    private CheckBox aggreeTerms;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        userName = (EditText) findViewById(R.id.userName);
        name = (EditText) findViewById(R.id.name);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        doornumber = (EditText) findViewById(R.id.doornumber);
        cityNumber = (EditText) findViewById(R.id.cityNumber);
        zipcode = (EditText) findViewById(R.id.zipcode);
        bloodGroup = (EditText) findViewById(R.id.bloodGroup);
        milesToTravel = (EditText) findViewById(R.id.milesToTravel);

        gender = (RadioGroup) findViewById(R.id.gender);

        aggreeTerms = (CheckBox) findViewById(R.id.agreeTerms);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }






    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invald_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(name.getText().toString())) {
            name.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(userName.getText().toString())) {
            userName.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
            phoneNumber.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(doornumber.getText().toString())) {
            doornumber.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(cityNumber.getText().toString())) {
            cityNumber.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(zipcode.getText().toString())) {
            zipcode.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(bloodGroup.getText().toString())) {
            bloodGroup.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(mPasswordView.getText().toString())) {
            mPasswordView.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
            confirmPassword.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(milesToTravel.getText().toString())) {
            milesToTravel.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
            cancel = true;
        }

        if(aggreeTerms.isChecked() == false) {
            showAlert("Please agree for to be donor");
        }

        else if(gender.getCheckedRadioButtonId() < 0) {
            showAlert("Please select gender");
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password,name.getText().toString(),userName.getText().toString(),phoneNumber.getText().toString(),doornumber.getText().toString(),cityNumber.getText().toString(),zipcode.getText().toString(),bloodGroup.getText().toString(),milesToTravel.getText().toString(), ((RadioButton)findViewById(gender.getCheckedRadioButtonId())).getText().toString());
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Signupactivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword,name,userName,phoneNumber,door,city,zip,bloodGroup,milesToTravel,gender;
        String responseString;


        UserLoginTask(String email, String password,String name,String userName,String phoneNumber,String door,String city,String zip,String bloodGroup,String milesToTravel,String gender) {
            mEmail = email;
            mPassword = password;
            this.name = name;
            this.userName = userName;
            this.phoneNumber = phoneNumber;
            this.door = door;
            this.city = city;
            this.zip = zip;
            this.bloodGroup = bloodGroup;
            this.milesToTravel = milesToTravel;
            this.gender = gender;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(LoginActivity.urlIP + "Signup");
            // HttpPost post = new
            // HttpPost("http://192.168.0.120:8080/WebApplication1/LoginAction");
            // HttpPost post = new
            // HttpPost("http://127.0.0.1:8080/WebApplication1/LoginAction");

            JSONObject json = new JSONObject();
            try {
                json.put("userName",userName );
                json.put("password", mPassword);
                json.put("email", mEmail);
                json.put("name", name);
                json.put("phoneNumber", phoneNumber);
                json.put("door", door);
                json.put("city", city);
                json.put("zip", zip);
                json.put("bloodGroup", bloodGroup);
                json.put("milesToTravel", milesToTravel);
                json.put("gender", gender);



//				Log.d("====request=====", "" + email.getText().toString());
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            try {
                StringEntity strEntity = new StringEntity(json.toString());
                post.setEntity(strEntity);
                HttpResponse response = client.execute(post);
                responseString = EntityUtils.toString(response.getEntity());
                Log.v("response log", "Got response " + responseString);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
            // TODO: register the new account here.

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            try {


                JSONObject jsonObject = new JSONObject(responseString);
                Log.d("====message===", jsonObject.optString("userid"));
                // id=jsonObject.optString("userid");

                if(jsonObject.optString("status").equalsIgnoreCase("success")) {

                    SharedPreferences prfe = getSharedPreferences("Login",MODE_PRIVATE);
                    prfe.edit().putString("user", userName).commit();

                    Intent intent = new Intent(Signupactivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    showAlert(jsonObject.getString("msg"));
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                showAlert("Unable to connect to server");
            }catch (Exception e) {
                // TODO: handle exception
                showAlert("Unable to connect to server");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void showAlert(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                Signupactivity.this);
        builder.setMessage(mess);
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }
}

