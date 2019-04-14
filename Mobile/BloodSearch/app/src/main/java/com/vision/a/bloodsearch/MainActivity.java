package com.vision.a.bloodsearch;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText bloodGroup;

    static JSONArray _listOfDonars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bloodGroup = (EditText) findViewById(R.id.searchText);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(bloodGroup.getText().toString().isEmpty()) {
                showAlert("Please enter search text");
            }else {
                new sendData(bloodGroup.getText().toString()).execute();
            }
             }
        });

        if(checkLocationPermission()) {
            Intent loginService = new Intent(getApplicationContext(), LocationTracker.class);

            startService(loginService);
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location permission")
                        .setMessage("Location permission required")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        Intent loginService = new Intent(getApplicationContext(), LocationTracker.class);

                        startService(loginService);
//                        locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.logout) {

            SharedPreferences prfe = getSharedPreferences("Login",
                    MODE_PRIVATE);
            prfe.edit().clear().commit();
            Intent logout = new Intent(this, LoginActivity.class);
            startActivity(logout);
//            stopService(new Intent(getApplicationContext(), LocationTracker.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class sendData extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress;
        private String responseString = null;
        private String searchText;

        sendData(String searchText) {
            this.searchText = searchText;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(LoginActivity.urlIP
                    + "Searchdonors");


            JSONObject json = new JSONObject();
            try {
                json.put("searchBy", searchText);
                SharedPreferences prfe = getSharedPreferences("Login",
                        MODE_PRIVATE);

                json.put("username", prfe.getString("user",""));

//				json.put("searchString",tempSearchString);
//                Geocoder geocoder;
//                List<Address> addresses;
//                geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

//                addresses = geocoder.getFromLocation(Double.parseDouble(LocationTracker.lati), Double.parseDouble(LocationTracker.longi), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//
//                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                String city = addresses.get(0).getLocality();
//                String state = addresses.get(0).getAdminArea();
//                String country = addresses.get(0).getCountryName();
//                String postalCode = addresses.get(0).getPostalCode();
//                String knownName = addresses.get(0).getFeatureName();
//                json.put("searchString",postalCode);
                // Log.d("====request=====", "" + email.getText().toString());
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }catch (Exception ex){
                ex.printStackTrace();
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
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progress = ProgressDialog.show(MainActivity.this, "",
                    "Authenticating...");
//            tempBlodGrp = bloodgrps[_bloodGrp.getSelectedItemPosition()];
//            tempSearchBy = _searchBy.getSelectedItem().toString();
//            tempSearchString =  _data.getText().toString();

        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {
                progress.dismiss();

                JSONObject jsonObject = new JSONObject(responseString);
                Log.d("====message===", jsonObject.optString("userid"));
                String status = jsonObject.optString("status");
                if (status.equalsIgnoreCase("success")) {
                    if(jsonObject.has("donars") && jsonObject.getJSONArray("donars").length() > 0) {
                        _listOfDonars = jsonObject.getJSONArray("donars");

                        for (int i =0; i < _listOfDonars.length();i++) {
                            Address address = getLocationFromAddress(((JSONObject)_listOfDonars.get(i)).getString("address"));
                            if (address != null) {
                                ((JSONObject) _listOfDonars.get(i)).put("lat", address.getLatitude());
                                ((JSONObject) _listOfDonars.get(i)).put("longi", address.getLongitude());
                                double distance = distance(Double.parseDouble(LocationTracker.lati),Double.parseDouble(LocationTracker.longi),address.getLatitude(),address.getLongitude());
                                ((JSONObject) _listOfDonars.get(i)).put("distance", distance);

                            }

                        }


                        Intent donars = new Intent(MainActivity.this, DonarsList.class);
                        startActivity(donars);
                    }else {
                        showAlert("No donars to display");
                    }


                } else {
                    showAlert(jsonObject.getString("msg"));
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                showAlert("Unable to connect to server");
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                showAlert("Unable to connect to server");
            }
        }
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void showAlert(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                MainActivity.this);
        builder.setMessage(mess);
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    public Address getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address;
//        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

//            p1 = new GeoPoint((double) (location.getLatitude() * 1E6),
//                    (double) (location.getLongitude() * 1E6));

            return location;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private ArrayList<JSONObject> sortBasedDistance(JSONArray jsonArr) {
//        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
//        for (int i = 0; i < jsonArr.length(); i++) {
//            try {
//                jsonValues.add(jsonArr.getJSONObject(i));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        Collections.sort( jsonValues, new Comparator<JSONObject>() {
//            //You can change "Name" with "ID" if you want to sort by ID
//            private static final String KEY_NAME = "Name";
//
//            @Override
//            public int compare(JSONObject a, JSONObject b) {
//                String valA = new String();
//                String valB = new String();
//
//                try {
//                    valA = (String) a.get(KEY_NAME);
//                    valB = (String) b.get(KEY_NAME);
//                }
//                catch (JSONException e) {
//                    //do something
//                }
//
//                return valA.compareTo(valB);
//                //if you want to change the sort order, simply use the following:
//                //return -valA.compareTo(valB);
//            }
//        });
//
//        for (int i = 0; i < jsonArr.length(); i++) {
//            sortedJsonArray.put(jsonValues.get(i));
//        }
//    }
}
