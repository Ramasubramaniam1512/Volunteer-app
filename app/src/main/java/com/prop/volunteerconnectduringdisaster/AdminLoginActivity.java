package com.prop.volunteerconnectduringdisaster;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends Activity {

    Button loginbtn,backbtn;
    EditText edtusername,edtpassword;
    String username,password,user1,pass1;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        loginbtn=(Button)findViewById(R.id.admloginbtn);
        backbtn=(Button)findViewById(R.id.backbtn);
        edtusername = (EditText) findViewById(R.id.edtadmusername);
        edtpassword = (EditText) findViewById(R.id.edtadmpassword);


//		conn=CONN();
        loginbtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                username = edtusername.getText().toString();
                password = edtpassword.getText().toString();

                try {
                    new QuerySQL().execute(username,password);

                } catch (Exception e) {
                    Log.e("ERRO",e.getMessage());
                }




                // startActivity(new Intent(AdminLoginActivity.this,AdminActivity.class));



            }

        });
        
        backbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(AdminLoginActivity.this,MainActivity.class));		
				
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

            pDialog = new ProgressDialog(AdminLoginActivity.this);
            pDialog.setTitle("Authentication");
            pDialog.setMessage("Verifying your credentials...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args) {

            user1 = new String(args[0]);
            pass1 = new String(args[1]);



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
                String COMANDOSQL="select * from admintable where username='"+user1+"' && password='"+pass1+"'";
                Statement statement = conn.createStatement();
                rs = statement.executeQuery(COMANDOSQL);
                if(rs.next()){

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

                    Intent intent=new Intent(AdminLoginActivity.this,AdminActivity.class);
                    //intent.putExtra("latitude", lati);
                    //intent.putExtra("longitude", longi);
                    //intent.putExtra("loginuser", user1);

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
