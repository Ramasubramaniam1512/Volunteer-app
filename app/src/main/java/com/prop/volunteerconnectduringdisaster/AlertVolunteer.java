package com.prop.volunteerconnectduringdisaster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AlertVolunteer extends MainActivity {

	EditText edtdistrict, edtdetails;
Button btnSubmit,btnSubmit1;
Connection conn;

private String district,details;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alertvolunteer);
		
		edtdistrict= (EditText) findViewById(R.id.edtdistrict);
		edtdetails = (EditText) findViewById(R.id.edtdetails);
		
		btnSubmit = (Button) findViewById(R.id.alertbtn);
		btnSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				district = edtdistrict.getText().toString();
				details = edtdetails.getText().toString();
			
				try {
					if(verify())
					{
						new QuerySQL().execute();
					}
					
		
					} catch (Exception e) {
		        Log.e("ERRO",e.getMessage());
				}

				
			}
		});
		
		btnSubmit1 = (Button) findViewById(R.id.backbtn);
		btnSubmit1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),AdminActivity.class);
				startActivity(i);
				
				
			}
		});

	}
	
	public boolean verify()
	{
//		EditText name, userName, password, cpassword, email, phoneNumber;
		Boolean ret=true;
		if(edtdistrict.getText().toString().length()<1){edtdistrict.setError("Field Required");ret=false;}
		if(edtdetails.getText().toString().length()<1){edtdetails.setError("Field Required");ret=false;}
		
		
		return ret;
	}
	
	
	public class QuerySQL extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;
		ResultSet rs;
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        
	        pDialog = new ProgressDialog(AlertVolunteer.this);
	        pDialog.setTitle("Alert for Disaster");
	        pDialog.setMessage("Sending Alert...");
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
	    }

	    @Override
	    protected Boolean doInBackground(String... args) {
	    	
	    	
	    	
	    	
			try {
				
				
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://103.10.235.63:3306/volunteerconnectdisaster","root","password");		
				} catch (SQLException se) {
				Log.e("ERRO1",se.getMessage());
			} catch (ClassNotFoundException e) {
				Log.e("ERRO2",e.getMessage());
			} catch (Exception e) {
			    Log.e("ERRO3",e.getMessage());
			}
		
			 DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	            Date date = new Date();
	       System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
	       String datetime1=""+dateFormat.format(date);
			try {
				Statement statement = conn.createStatement();
				int success=statement.executeUpdate("insert into alertdetails values('"+district+"','"+details+"','"+datetime1+"')");
						
							
				if (success >= 1) {
					// successfully created product
					
					return true;
					// closing this screen
//					finish();
				} else {
					// failed to create product
					return false;
				}


				
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
                
	    		Toast.makeText(getBaseContext(),"Alert sent to Volunteer success.." ,Toast.LENGTH_LONG).show();
					
//					System.out.println("ELSE(JSON) LOOP EXE");
					try {// try3 open
						
						Intent i = new Intent(getApplicationContext(),
								AdminActivity.class);
						startActivity(i);		
						
					} catch (Exception e1) {
						Toast.makeText(getBaseContext(), e1.toString(),
								Toast.LENGTH_LONG).show();

					}					
				
            
	    	}else
	    	{
	    		if(error!=null)
	    		{
	    			Toast.makeText(getApplicationContext(),error.toString() ,Toast.LENGTH_LONG).show();
	    			Log.d("Error not null...", error.toString());
	    		}
	    		else
	    		{
	    			Toast.makeText(getBaseContext(),"Not crreated your credentials!!!" ,Toast.LENGTH_LONG).show();
	    		}
	    	}
	    	super.onPostExecute(result1);
	    }
	}


}
