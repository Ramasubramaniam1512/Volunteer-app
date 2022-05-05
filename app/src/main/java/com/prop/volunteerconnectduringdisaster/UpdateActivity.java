package com.prop.volunteerconnectduringdisaster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends MainActivity {

	EditText edtName, edtMobileNo, edtEmail, 	edtPassword, edtprof,edtplace;
Button btnSubmit,btnSubmit1;
Connection conn;

private String name, mobilenumber, email, username,prof, password,place;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		
		edtName = (EditText) findViewById(R.id.register_name);
		
		edtEmail = (EditText) findViewById(R.id.register_email);
		edtPassword = (EditText) findViewById(R.id.register_password);
		edtMobileNo = (EditText) findViewById(R.id.register_phno);
		edtprof = (EditText) findViewById(R.id.register_prof);
		edtplace = (EditText) findViewById(R.id.register_place);
		
		SharedPreferences preferences1=getSharedPreferences("username", Context.MODE_PRIVATE);
		 username=preferences1.getString("username",null);
		Intent intent=getIntent();
		name=intent.getStringExtra("name");
		password=intent.getStringExtra("password");
		place=intent.getStringExtra("place");
		mobilenumber=intent.getStringExtra("mobile");
		email=intent.getStringExtra("emailid");
		
		edtName.setText(name);
		edtPassword.setText(password);
		edtprof.setText(password);
		edtplace.setText(place);
		edtMobileNo.setText(mobilenumber);
		edtEmail.setText(email);
	
		btnSubmit = (Button) findViewById(R.id.register_btn_reg);
		btnSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				name = edtName.getText().toString();
				
				email = edtEmail.getText().toString();
				password = edtPassword.getText().toString();
				mobilenumber = edtMobileNo.getText().toString();
				prof = edtprof.getText().toString();
				place = edtplace.getText().toString();
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
		
		btnSubmit1 = (Button) findViewById(R.id.register_btn_cancel);
		btnSubmit1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),UserActivity.class);
				startActivity(i);
				
				
			}
		});

	}
	
	public boolean verify()
	{
//		EditText name, userName, password, cpassword, email, phoneNumber;
		Boolean ret=true;
		if(edtName.getText().toString().length()<1){edtName.setError("Field Required");ret=false;}
		if(edtPassword.getText().toString().length()<1){edtPassword.setError("Field Required");ret=false;}
		if(edtprof.getText().toString().length()<1){edtprof.setError("Field Required");ret=false;}
		if(!edtPassword.getText().toString().equals(edtprof.getText().toString())){edtPassword.setError("Password not same");ret=false;}
		if(edtplace.getText().toString().length()<1){edtplace.setError("Field Required");ret=false;}
		
		if(!edtEmail.getText().toString().contains("@")){edtEmail.setError("E-Mail ID Invalid");ret=false;}
		if(edtEmail.getText().toString().length()<1){edtEmail.setError("Field Required");ret=false;}
		if(edtMobileNo.getText().toString().length()<10){edtMobileNo.setError("Invalid Phone Number");ret=false;}//It will Set but ok it wont be visible
		if(edtMobileNo.getText().toString().length()<1){edtMobileNo.setError("Field Required");ret=false;}
		
		String expression = "^([0-9\\+]|\\(\\d{0,1}\\))[0-9\\-\\. ]{0,15}$";
        CharSequence inputString = edtMobileNo.getText().toString();
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
		
        }
        else
        {
        	edtMobileNo.setError("Invalid Number");ret=false;
        }
		
		
		return ret;
	}
	
	
	public class QuerySQL extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;
		ResultSet rs;
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        
	        pDialog = new ProgressDialog(UpdateActivity.this);
	        pDialog.setTitle("Updation");
	        pDialog.setMessage("Updating your changes...");
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
		
			 DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	            Date date = new Date();
	       System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

			try {
				
				String query = "delete from userdetails where username = ?";
			      PreparedStatement preparedStmt = conn.prepareStatement(query);
			      preparedStmt.setString(1, username);
			      // execute the preparedstatement
			      preparedStmt.execute();
				
				Statement statement = conn.createStatement();
				int success=statement.executeUpdate("insert into userdetails values('"+name+"','"+username+"','"+password+"','"+email+"','"+mobilenumber+"','"+place+"')");
						
							
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
                
	    		Toast.makeText(getBaseContext(),"Updated Successfully" ,Toast.LENGTH_LONG).show();
					
//					System.out.println("ELSE(JSON) LOOP EXE");
					try {// try3 open
						
						Intent i = new Intent(getApplicationContext(),
								UserActivity.class);
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
