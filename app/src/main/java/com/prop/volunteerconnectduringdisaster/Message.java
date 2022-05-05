package com.prop.volunteerconnectduringdisaster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;



import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorEventListener;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Message extends Activity implements SensorEventListener,
SurfaceHolder.Callback {
	ListView msglist;
	String recvname="";
	Context context, contextForDialog;
	//String mobile="";
String sendername="";
	Connection conn;
	EditText edmessage;
	Button sendmsg;
	
	private SensorManager senSensorManager;
	private Sensor senAccelerometer;
	private long lastUpdate = 0;
	private float last_x, last_y, last_z;
	public static int SHAKE_THRESHOLD = 150;
	LocationListener locationListener;
	SmsManager smsManager;
	MainActivity ma;
	float x, y, z;
	SurfaceView sv;
	public static boolean picProcess = false;
	public static boolean running = false;
	public byte[] cameraImage;
	SurfaceHolder camSurfaceHolder;
	public Camera theCamera = null;
	
	HashMap<String,String> usersList1 = null;
	ArrayList<HashMap<String,String>> usersList2 = new ArrayList<HashMap<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		context = getApplicationContext();
		contextForDialog = this;
		
		 SharedPreferences preferences1=getSharedPreferences("place", Context.MODE_PRIVATE);
		 recvname=preferences1.getString("place",null);

		 SharedPreferences preferences2=getSharedPreferences("username", Context.MODE_PRIVATE);
		 sendername=preferences2.getString("username",null);


		
		//Intent intent=getIntent();
		// recvname=intent.getStringExtra("name");
		//mobile=intent.getStringExtra("mobile");
		//sendername=intent.getStringExtra("sendername");
		Log.d("Namemessage",recvname);
		//Log.d("Mobilemessage",mobile);
		edmessage=(EditText)findViewById(R.id.ed_msg);
		sendmsg=(Button)findViewById(R.id.butt_send);
		msglist=(ListView)findViewById(R.id.msglist);
//		conn=CONN();
		
		init();
		
		new MessageDisp().execute(recvname,sendername);
		
		sendmsg.setOnClickListener(new OnClickListener(){
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			init();
			String message=edmessage.getText().toString();
			 new sendMessage().execute(recvname,message,sendername);
			// new MessageDisp().execute();
		}
		});
	
	}
	
	@Override
	protected void onNewIntent(Intent newIntent) {
		super.onNewIntent(newIntent);
		String xx=newIntent.getStringExtra("templatemsg").toString();
		edmessage.setText(xx);
		
	}
	
	public void init() {
		running = true;
		senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// Init Accelerometer
		senAccelerometer = senSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		senSensorManager.registerListener(this, senAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		
	}
	

	
	public class MessageDisp extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;
		
		
		ResultSet rs;
		
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        
	        pDialog = new ProgressDialog(Message.this);
	        pDialog.setTitle("Message Display");
	        pDialog.setMessage("Get Message...");
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
	    }

	    @Override
	    protected Boolean doInBackground(String... args) {
	    	
	    	String receiver = new String(args[0]);
	    	String sender = new String(args[1]);
	    	
	    	
			
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
				String COMANDOSQL="select * from message where district='"+receiver+"'";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
				if(rs.next())
				{
					do
					{
						usersList1  = new HashMap<String,String>();				
						usersList1.put("sender",rs.getString("sender"));	
						usersList1.put("receiver",rs.getString("district"));
			            usersList1.put("message",rs.getString("message"));				
			            Log.d("message",usersList1.toString());
			            usersList2.add(usersList1);
			            
					}while(rs.next());
					return true;
				}
				else
				{
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
                
			
					
//					System.out.println("ELSE(JSON) LOOP EXE");
					try {// try3 open
						
						msglist.setAdapter(new MessageAdapter(Message.this, usersList2));
						msglist.setOnItemClickListener(new OnItemClickListener() {

							public void onItemClick(AdapterView<?> parent, View v,
									int position, long id) {
							
							}
						});			
						
					} catch (Exception e1) {
						Toast.makeText(getBaseContext(), e1.toString(),
								Toast.LENGTH_LONG).show();

					}					
				
            
	    	}else
	    	{
	    		if(error!=null)
	    		{
	    			//Toast.makeText(getBaseContext(),error.getMessage().toString() ,Toast.LENGTH_LONG).show();
	    		}
	    		else
	    		{
	    			Toast.makeText(getBaseContext(),"No Message!!!" ,Toast.LENGTH_LONG).show();
	    		}
	    	}
	    	super.onPostExecute(result1);
	    }
	}
	
	
	public class sendMessage extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;
		String Text="";
		ResultSet rs;
		
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        
	        pDialog = new ProgressDialog(Message.this);
	        pDialog.setTitle("Send Message");
	        pDialog.setMessage("Message Sending...");
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
	    }

	    @Override
	    protected Boolean doInBackground(String... args) {
	    	
	    	String recvname = new String(args[0]);
	    	String smessage = new String(args[1]);
	    	String sendername = new String(args[2]);
	    	
	    	
			
			

			try {
				Statement statement = conn.createStatement();
				int success=statement.executeUpdate("insert into message values('"+sendername+"','"+recvname+"','"+smessage+"')");
				Log.d("Msg Updated", Integer.toString(success));
				if(success == 1){
					 Text="Your Message Sent Successfully";
//						edmessage.clearFocus();
//				edmessage.setText("");
				return true;
				}
				else{
					Text="Your Message Not Sent! Some Network Problem" ;
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
                
			
					
//					
						Toast.makeText( getApplicationContext(),Text,Toast.LENGTH_SHORT).show();
						edmessage.clearFocus();
						edmessage.setText("");
						
						Intent intent=new Intent(Message.this,Message.class);
						//intent.putExtra("latitude", lati);
						//intent.putExtra("longitude", longi);
						//intent.putExtra("loginuser", user1);
					
						startActivity(intent);	
									
            
	    	}else
	    	{
	    		if(error!=null)
	    		{
	    			Toast.makeText(getBaseContext(),error.getMessage().toString() ,Toast.LENGTH_LONG).show();
	    		}
	    		else
	    		{
	    			Toast.makeText( getApplicationContext(),Text,Toast.LENGTH_SHORT).show();
	    		}
	    	}
	    	super.onPostExecute(result1);
	    }
	}


	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		Sensor mySensor = event.sensor;
		if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			x = event.values[0];
			y = event.values[1];
			z = event.values[2];
			long curTime = System.currentTimeMillis();

			if ((curTime - lastUpdate) > 100) {
				long diffTime = (curTime - lastUpdate);
				lastUpdate = curTime;
				float speed = Math.abs(x + y + z - last_x - last_y - last_z)
						/ diffTime * 10000;
				
			//	Toast.makeText(context, "Value : "+speed+","+SHAKE_THRESHOLD*10, 1).show();

				if (speed > SHAKE_THRESHOLD * 10) {

					System.out.println(x + "/" + y + "/" + z + "/" + speed
							+ "//" + SHAKE_THRESHOLD * 10);
					if (!picProcess)
					{
						Toast.makeText(context, "Blocked"+speed+","+SHAKE_THRESHOLD*10, 1).show();
						sendmsg.setEnabled(false); 
						edmessage.setEnabled(false); 
						//takePic();
					}

				}
			}
		}
	}

	
}
