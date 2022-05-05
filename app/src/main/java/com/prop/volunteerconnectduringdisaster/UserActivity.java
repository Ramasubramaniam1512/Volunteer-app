package com.prop.volunteerconnectduringdisaster;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.prop.volunteerconnectduringdisaster.LoginActivity.QuerySQL;

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

public class UserActivity extends Activity {

	Button viewbtn1, updatebtn1, chatbtn, logoutbtn1;
	String username,name,password,place,mobile,emailid,district;
	Connection conn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_activity);
		
		 SharedPreferences preferences1=getSharedPreferences("username", Context.MODE_PRIVATE);
		 username=preferences1.getString("username",null);
		 
		 SharedPreferences preferences2=getSharedPreferences("place", Context.MODE_PRIVATE);
		 district=preferences2.getString("place",null);
		
		viewbtn1=(Button)findViewById(R.id.viewbtn);
		
		updatebtn1=(Button)findViewById(R.id.updatebtn);
		
		chatbtn=(Button)findViewById(R.id.chatbtn);
		
		logoutbtn1=(Button)findViewById(R.id.logoutbtn);
		
		
		viewbtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				startActivity(new Intent(UserActivity.this,ViewAlert.class));
					
				
				
			}
			
		});
		
		
		updatebtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				new QuerySQL().execute();
					
				
				
			}
			
		});
		
		chatbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent intent=new Intent(UserActivity.this,Message.class);
				intent.putExtra("sendername", username);
				intent.putExtra("name", district);
				
			
				startActivity(intent);	
					
				
				
			}
			
		});
		
		
		logoutbtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				startActivity(new Intent(UserActivity.this,MainActivity.class));
					
				
				
			}
			
		});
		
	}

	public class QuerySQL extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;
		ResultSet rs;
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        
	        pDialog = new ProgressDialog(UserActivity.this);
	        pDialog.setTitle("Authentication");
	        pDialog.setMessage("Verifying your credentials...");
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
			

			try {
				String COMANDOSQL="select * from userdetails where username='"+username+"'";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
			if(rs.next()){
				
			name = rs.getString(1);
			password = rs.getString(3);
			emailid = rs.getString(4);
			mobile = rs.getString(5);
			place = rs.getString(6);

					
				
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
						
						Intent intent=new Intent(UserActivity.this,UpdateActivity.class);
						intent.putExtra("name", name);
						intent.putExtra("password", password);
						intent.putExtra("emailid", emailid);
						intent.putExtra("mobile", mobile);
						intent.putExtra("place", place);
						startActivity(intent);			
						
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
	    		else
	    		{
	    			Toast.makeText(getBaseContext(),"Check your credentials!!!" ,Toast.LENGTH_LONG).show();
	    		}
	    	}
	    	super.onPostExecute(result1);
	    }
	}
	

}
