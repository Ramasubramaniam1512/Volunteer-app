package com.prop.volunteerconnectduringdisaster;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button adminbtn1, userbtn1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		adminbtn1=(Button)findViewById(R.id.Admin);
		
		userbtn1=(Button)findViewById(R.id.User);
		

		adminbtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				startActivity(new Intent(MainActivity.this,AdminLoginActivity.class));
					
				
				
			}
			
		});
		
		
		userbtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				startActivity(new Intent(MainActivity.this,LoginActivity.class));
					
				
				
			}
			
		});
	}



}
