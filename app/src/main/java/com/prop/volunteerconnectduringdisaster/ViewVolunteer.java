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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ViewVolunteer extends Activity {
	ListView listView;
	Connection conn;
	Double lat,lon;
	String username;
	EditText edt1;
	String id;
	Button btn1,btn2,btn3,btn4;
	String sendername,doctor;
	HashMap<String,String> usersList1 = null;
	ArrayList<HashMap<String,String>> usersList2 = new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.volunteer_list);
		 listView = (ListView) findViewById(R.id.listView1);

		//Intent intent=getIntent();
		//doctor=intent.getStringExtra("doctor");
		// SharedPreferences preferences1=getSharedPreferences("projectname", Context.MODE_PRIVATE);
		// projectname=preferences1.getString("projectname",null);

		 edt1 = (EditText) findViewById(R.id.edt1);
		 
		 btn1=(Button)findViewById(R.id.edit);
			btn2=(Button)findViewById(R.id.delete);
			btn3=(Button)findViewById(R.id.back);
			
			btn1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					id = edt1.getText().toString();
					try{


						if(verify())
						{
						new QuerySQL1().execute();
						}
					}
					catch (Exception e){
						System.out.println("Exception: " + e.getMessage());
					}	
					
					
				}
				
			});
			btn2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					id = edt1.getText().toString();
					try{


						if(verify())
						{
						new QuerySQL2().execute();
						}
					}
					catch (Exception e){
						System.out.println("Exception: " + e.getMessage());
					}	
				}
				
			});
			
			btn3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					Intent i = new Intent(getApplicationContext(),AdminActivity.class);
					
					startActivity(i);
						
					
					
				}
				
			});
			

		//date1=intent.getStringExtra("date1");

		try{



			new QuerySQL().execute();
		}
		catch (Exception e){
			System.out.println("NumberFormatException: " + e.getMessage());
		}
	}
	public boolean verify()
	{
//		EditText name, userName, password, cpassword, email, phoneNumber;
		Boolean ret=true;
		//if(edtName.getText().toString().length()<1){edtName.setError("Field Required");ret=false;}
		if(edt1.getText().toString().length()<1){edt1.setError("Field Required");ret=false;}
		
		
		return ret;
	}
	

	public class QuerySQL extends AsyncTask<Object, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;


		ResultSet rs;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ViewVolunteer.this);
			pDialog.setTitle("Volunteer List");
			pDialog.setMessage("View Volunteer...");
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
				String COMANDOSQL="select * from userdetails";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
				while(rs.next()){
					usersList1 = new HashMap<String, String>();
					//	usersList1.put("uname",rs.getString("name"));
					
					
					usersList1.put("username",rs.getString(2));
					usersList1.put("name",rs.getString(1));
					usersList1.put("mobile",rs.getString(5));
					usersList1.put("district",rs.getString(6));
					
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

					listView.setAdapter(new VolunteerAdapter(ViewVolunteer.this, usersList2));
					

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

	public class QuerySQL1 extends AsyncTask<Object, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;


		ResultSet rs;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ViewVolunteer.this);
			pDialog.setTitle("Volunteer");
			pDialog.setMessage("Edit Volunteer...");
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
			

				String COMANDOSQL="select * from userdetails where username='"+id+"'";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
				if(rs.next()){
					usersList1 = new HashMap<String, String>();
					//	usersList1.put("uname",rs.getString("name"));
					usersList1.put("username",rs.getString(2));
					usersList1.put("name",rs.getString(1));
					usersList1.put("password",rs.getString(3));
					usersList1.put("emailid",rs.getString(4));
					usersList1.put("mobile",rs.getString(5));
					usersList1.put("district",rs.getString(6));
					
					Log.d("Friend List Map :",usersList1.toString());

					usersList2.add(usersList1);

					return true;
				}


				return false;
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

					Intent i = new Intent(getApplicationContext(),
							EditVolunteer.class);
					i.putExtra("id", id);
					i.putExtra("name", usersList2.get(0)
							.get("name"));
					i.putExtra("password", usersList2.get(0)
							.get("password"));
					i.putExtra("emailid", usersList2.get(0)
							.get("emailid"));
					i.putExtra("mobile", usersList2.get(0)
							.get("mobile"));
					i.putExtra("district", usersList2.get(0)
							.get("district"));
					
					startActivity(i);	
					

				} catch (Exception e1) {
					Toast.makeText(getBaseContext(), e1.toString(),
							Toast.LENGTH_LONG).show();

				}


			}else
			{
				if(error!=null)
				{
					Toast.makeText(getBaseContext(),"Volunteer not available",Toast.LENGTH_LONG).show();
				}
			}
			super.onPostExecute(result1);
		}
	}
	public class QuerySQL2 extends AsyncTask<Object, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;


		ResultSet rs;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ViewVolunteer.this);
			pDialog.setTitle("Volunteer");
			pDialog.setMessage("Delete Volunteer...");
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
				String COMANDOSQL="select * from userdetails where username='"+id+"'";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
				if(rs.next()){
					String query = "delete from userdetails where username = ?";
				      PreparedStatement preparedStmt = conn.prepareStatement(query);
				      preparedStmt.setString(1, id);

				      // execute the preparedstatement
				      preparedStmt.execute();
				      return true;
				}

				return false;
				
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


				Toast.makeText(getBaseContext(),"Successfully deleted volunteer." ,Toast.LENGTH_LONG).show();
				
//				System.out.println("ELSE(JSON) LOOP EXE");
				try {// try3 open
					
					Intent i = new Intent(getApplicationContext(),
							ViewVolunteer.class);
					//i.putExtra("username", username);
					startActivity(i);		
					
				} catch (Exception e1) {
					Toast.makeText(getBaseContext(), e1.toString(),
							Toast.LENGTH_LONG).show();

				}

			}else
			{
				if(error!=null)
				{
					Toast.makeText(getBaseContext(),"Volunteer not available" ,Toast.LENGTH_LONG).show();
				}
			}
			super.onPostExecute(result1);
		}
	}

}
