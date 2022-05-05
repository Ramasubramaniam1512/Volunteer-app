package com.prop.volunteerconnectduringdisaster;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ViewAlert extends Activity {
	ListView listView;
	Connection conn;
	Double lat,lon;
	String username;
	
	String lati,longi,loginname,areaname1;
	
	String district;
	HashMap<String,String> usersList1 = null;
	ArrayList<HashMap<String,String>> usersList2 = new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_list);
		 listView = (ListView) findViewById(R.id.listView1);

		//Intent intent=getIntent();
		//doctor=intent.getStringExtra("doctor");
		 SharedPreferences preferences1=getSharedPreferences("place", Context.MODE_PRIVATE);
		 district=preferences1.getString("place",null);


		//date1=intent.getStringExtra("date1");

		try{



			new QuerySQL().execute();
		}
		catch (Exception e){
			System.out.println("NumberFormatException: " + e.getMessage());
		}
	}


	public class QuerySQL extends AsyncTask<Object, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;


		ResultSet rs;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ViewAlert.this);
			pDialog.setTitle("Alert List");
			pDialog.setMessage("View alerts...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(Object... args) {



			try {


				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://103.10.235.63:3306/volunteerconnectdisaster","root","password");			
    				} catch (SQLException se) {
				Log.e("ERRO1",se.getMessage());
			} catch (ClassNotFoundException e) {
				Log.e("ERRO22",e.getMessage());
			} catch (Exception e) {
				Log.e("ERRO3",e.getMessage());
			}


			try {
			

				String status = "requested";
				String COMANDOSQL="select * from alertdetails where district='"+district+"'";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
				while(rs.next()){
					usersList1 = new HashMap<String, String>();
					//	usersList1.put("uname",rs.getString("name"));
					usersList1.put("details",rs.getString(2));
					usersList1.put("datetime1",rs.getString(3));
					
					
					Log.d("Friend List Map :",usersList1.toString());

					usersList2.add(usersList1);


				}


				return true;
				// Toast.makeText(getBaseContext(),
				// "Successfully Inserted.", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				error = e;
				return false;
//				Toast.makeText(getBaseContext(),"Successfully Registered...", Toast.LENGTH_LONG).show();
			}


		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(Boolean result1) {
			pDialog.dismiss ( ) ;
			if(result1)
			{



//					System.out.println("ELSE(JSON) LOOP EXE");
				try {// try3 open

					listView.setAdapter(new AlertAdapter(ViewAlert.this, usersList2));
					

				} catch (Exception e1) {
					Toast.makeText(getBaseContext(), e1.toString(),
							Toast.LENGTH_LONG).show();

				}


			}else
			{
				if(error!=null)
				{
					Toast.makeText(getBaseContext(),error.getMessage().toString() ,Toast.LENGTH_LONG).show();
				}
			}
			super.onPostExecute(result1);
		}
	}


}
