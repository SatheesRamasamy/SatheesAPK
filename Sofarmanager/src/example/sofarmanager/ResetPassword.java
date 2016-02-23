package example.sofarmanager;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;

public class ResetPassword extends Activity 
	{
		EditText password, conformpassword;
		TextView send;
		Dialog dialog;
		CallApi call;
		String success, message,memNum,error_response;
	
		ImageView backbtn;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) 
			{
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				setContentView(R.layout.resetpassword);
				memNum = getIntent().getStringExtra("mem_id");
				
				password = (EditText) findViewById(R.id.s_pwd);
				conformpassword = (EditText) findViewById(R.id.c_pwd);
				send = (TextView) findViewById(R.id.s_btn);
				send.setOnClickListener(new OnClickListener() 
					{
						@Override
						public void onClick(View v) 
							{
								String pass = password.getText().toString().trim();
								String conform = conformpassword.getText().toString().trim();
								
								int count = 0;
								
								if (pass.length()==0) 
									{
										password.setError("Plese Enter Password");
									}
								else
									{
										count = count + 1;
									}
								
								if (conform.length()==0) 
									{
										conformpassword.setError("Plese Enter Confirm Password");
									}
								else
									{
										if(conform.equals(pass))
											{
												count = count + 1;
											}
										else
											{
												conformpassword.setError("Password Doesn't Match");
											}
									}
									
								if(count ==2)
									{
										reset_password();		
									}
								else 
									{

									}
								}
						});
				
				backbtn=(ImageView)findViewById(R.id.back_btn);
				backbtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						onBackPressed();
						
					}
				});
				
			}
		

		public void reset_password()
			{
				dialog = ProgressDialog.show(ResetPassword.this,"Resetting password...", "please wait...");
				
				new Thread(new Runnable() 
					{
						@Override
						public void run() 
							{
								call = new CallApi();
								String pass = password.getText().toString().trim();
								String conf = conformpassword.getText().toString().trim();
								
								System.out.println("memNo ="+memNum);
								
								String response = call.reset_password(memNum,pass,conf);
								
								if(response.contains("java.net.UnknownHostException")||response.contains("java.net.ConnectException") || response.contains("java.net.SocketException"))
									{
										error_response = new String();
										error_response = response;
										handler.sendEmptyMessage(1);
									}
								else
									{
										try {
												JSONObject obj = new JSONObject(response);
												success = new String();
												success = obj.getString("success");
												message = new String();
												if (success.equalsIgnoreCase("true"))
													{
														message=obj.getString("message");
														handler.sendEmptyMessage(2);
													}
												else
													{
														message = obj.getString("message");
														handler.sendEmptyMessage(3);
													}
											} 
										catch (JSONException e) 
											{
												e.printStackTrace();
												error_response = new String();
												error_response = e.toString();
												handler.sendEmptyMessage(1);
											}
										}
							}
					}).start();
		}
			
		
		Handler handler = new Handler()
			{
				public void handleMessage(android.os.Message msg) 
					{
						int val = msg.what;
						dialog.dismiss();
						
						switch (val) 
							{
								case 1:
									showAlertDialog(error_response);
								break;
								
								case 2:
									
									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ResetPassword.this);
									alertDialogBuilder.setMessage(message).setCancelable(true)
										.setPositiveButton("OK", new DialogInterface.OnClickListener() 
											{
												public void onClick(DialogInterface dialog, int id) 
													{
														Intent log = new Intent(ResetPassword.this,Login_Screen.class);
														startActivity(log);
														
													}
											});
									AlertDialog alertDialog = alertDialogBuilder.create();
									alertDialog.show();
									
								break;
								
								case 3:
									showAlertDialog(message);
								break;
								
								default:
								
								break;
							}
	
					};
			};
			
		@Override
		public void onBackPressed()
			{
				Intent open_log = new Intent(ResetPassword.this,Login_Screen.class);
				startActivity(open_log);
			
				super.onBackPressed();
			}
				
		
		
		public void showAlertDialog(String message)
			{
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ResetPassword.this);
				alertDialogBuilder.setMessage(message).setCancelable(true)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() 
						{
							public void onClick(DialogInterface dialog, int id) 
								{
									// if this button is clicked, close current activity								
									//finish();
								}
						});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		
		public boolean isConnectingToInternet()
			{
				ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
				if (connectivity != null) 
					{
						NetworkInfo[] info = connectivity.getAllNetworkInfo();
						if (info != null)
						{
							for (int i = 0; i < info.length; i++)
								{	
									if (info[i].getState() == NetworkInfo.State.CONNECTED)
										{
											return true;
										}
								}
						}
				}
				return false;
		}
}
