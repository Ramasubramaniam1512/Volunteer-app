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

public class UserActivity extends Activity {

	Button viewbtn1, updatebtn1, chatbtn, logoutbtn1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_activity);
		
		viewbtn1=(Button)findViewById(R.id.viewbtn);
		
		updatebtn1=(Button)findViewById(R.id.updatebtn);
		
		chatbtn=(Button)findViewById(R.id.chatbtn);
		
		logoutbtn1=(Button)findViewById(R.id.logoutbtn);
		
		
		viewbtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
			//	startActivity(new Intent(AdminActivity.this,AskLocation.class));
					
				
				
			}
			
		});
		
		
		updatebtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
			//	startActivity(new Intent(AdminActivity.this,ViewEmployee.class));
					
				
				
			}
			
		});
		
		chatbtn.setOnClickListener(new OnClickListener(){

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
				
				
				startActivity(new Intent(UserActivity.this,MainActivity.class));
					
				
				
			}
			
		});
		
	}

	

}
