package com.vision.a.bloodsearch;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

public class DonarsList extends Activity implements OnItemLongClickListener {

	ListView _donarsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listofdonars);
		_donarsList = (ListView) findViewById(R.id.donars);
		_donarsList.setAdapter(new Adapter());
		_donarsList.setOnItemLongClickListener(this);
	}

	class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return MainActivity._listOfDonars.length();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			if (arg1 == null) {
				arg1 = getLayoutInflater().inflate(R.layout.donardata, arg2, false);
			}
			TextView name = (TextView) arg1.findViewById(R.id.donarName);
			TextView mobile = (TextView) arg1.findViewById(R.id.donarPhoneNumber);
			TextView address = (TextView) arg1.findViewById(R.id.address);
			TextView bloodGroup = (TextView) arg1.findViewById(R.id.bloodGroup);
			TextView distance = (TextView) arg1.findViewById(R.id.distance);
			Button call = (Button) arg1.findViewById(R.id.call);
			Button msg = (Button) arg1.findViewById(R.id.map);
			try {
				name.setText("Name : " + MainActivity._listOfDonars.getJSONObject(arg0).getString("name"));
				mobile.setText("" + MainActivity._listOfDonars.getJSONObject(arg0).getString("mobilenumber"));
				address.setText("" + MainActivity._listOfDonars.getJSONObject(arg0).getString("address"));
				bloodGroup.setText("" + MainActivity._listOfDonars.getJSONObject(arg0).getString("bloodgroup"));
				distance.setText("Distance: " + MainActivity._listOfDonars.getJSONObject(arg0).getString("distance"));
				call.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							callToNumber(MainActivity._listOfDonars.getJSONObject(arg0).getString("mobilenumber"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				msg.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
									Uri.parse("google.navigation:q="+MainActivity._listOfDonars.getJSONObject(arg0).getString("address")));
							startActivity(intent);
//							messageNumber(MainActivity._listOfDonars.getJSONObject(arg0).getString("mobilenumber"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return arg1;
		}

	}

	private void callToNumber(String call) {
		Intent intent = new Intent(Intent.ACTION_CALL);


		intent.setData(Uri.parse("tel:" + call));
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		this.startActivity(intent);

	}

	private void messageNumber(String call) {
		Intent intent = new Intent(Intent.ACTION_VIEW);


		intent.setData(Uri.parse("sms:" + call));
		this.startActivity(intent);

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
								   long arg3) {
		// TODO Auto-generated method stub
		//call to number
		Intent intent = new Intent(Intent.ACTION_CALL);

		try {
			intent.setData(Uri.parse("tel:" + MainActivity._listOfDonars.getJSONObject(arg2).getString("mobilenumber")));
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return false;
			}
			this.startActivity(intent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
