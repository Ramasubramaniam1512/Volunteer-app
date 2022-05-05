package com.prop.volunteerconnectduringdisaster;




import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class HomePage extends Activity {
	int splashTime = 2500;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		Thread thread = new Thread() {
			public void run() {
				try {
					synchronized (this) {
						wait(splashTime);
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					startActivity(new Intent(HomePage.this,
							MainActivity.class));
					finish();
				}
			}
		};
		thread.start();
		 
	}

	
}
