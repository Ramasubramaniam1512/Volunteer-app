package com.prop.volunteerconnectduringdisaster;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AdminActivity extends Activity {

	Button viewbtn1, alertbtn1, logoutbtn1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_activity);
		
		viewbtn1=(Button)findViewById(R.id.viewbtn);
		
		alertbtn1=(Button)findViewById(R.id.alertbtn);
		
		
		
		logoutbtn1=(Button)findViewById(R.id.logoutbtn);
		
		
		viewbtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
			//	startActivity(new Intent(AdminActivity.this,AskLocation.class));
					
				
				
			}
			
		});
		
		
		alertbtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
			//	startActivity(new Intent(AdminActivity.this,ViewEmployee.class));
					
				
				
			}
			
		});
		
		
		logoutbtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				startActivity(new Intent(AdminActivity.this,MainActivity.class));
					
				
				
			}
			
		});
		
	}

	

}
