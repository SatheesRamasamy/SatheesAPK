package example.sofarmanager;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;

/**
 * This class is for login activity, it is getting the login credentials from
 * the user through UI and pass that values to the login method in the api and
 * get the response
 * 
 * @author Muthaiya.M
 * 
 */

public class Login_Screen extends Activity 
	{
		ImageView register,clickhere;
		CallApi call;
		EditText usermailid, userpasswrd;
		String muserName, mPassword, msuccess, mMessage, mhighestrole, mmeber_no,mFirstName, mSurname;
		ProgressDialog dialog;
		TextView loginbtn;
		String response;
		
		ImageView tab1,tab2,tab3,tab4;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) 
			{
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				setContentView(R.layout.activity_login_screen);
				
				call = new CallApi();
				response = new String();
				loginbtn = (TextView) findViewById(R.id.login_btn);
				usermailid = (EditText) findViewById(R.id.userid);
				userpasswrd = (EditText) findViewById(R.id.password);
				clickhere = (ImageView) findViewById(R.id.fg_pwd);
				
				register=(ImageView)findViewById(R.id.reg_btn);
				
				register.setOnClickListener(new OnClickListener() 
					{
						@Override
						public void onClick(View v) 
							{
								Intent forget = new Intent(Login_Screen.this,NewUserRegistartion.class);
								startActivity(forget);
							
							}
					});
				
				clickhere.setOnTouchListener(new OnTouchListener() 
					{
						@Override
						public boolean onTouch(View v, MotionEvent event)
							{
								Intent forget = new Intent(Login_Screen.this,ForgetPassword_Screen.class);
								startActivity(forget);
								
								return false;
							}
					});
				
				loginbtn.setOnTouchListener(new OnTouchListener() 
					{
						@Override
						public boolean onTouch(View v, MotionEvent event) 
							{
								muserName = usermailid.getText().toString();
								mPassword = userpasswrd.getText().toString();
								int count = 0;
								
								if (muserName.length() == 0)
									{
										usermailid.setError("Please provide a mail id");
									}
								else
									{
										if (isValidEmail(muserName)) 
											{
												
												count = count + 1;
											}	 
										else
											{
												usermailid.setError("Please provide a valid mail id");
											}
									}
							
								if( mPassword.length() == 0) 
									{
										userpasswrd.setError("Password must not be left blank");
									}		
								else 
									{
										count = count + 1;
									}
							
								if(count ==2)
									{
									
									login_response();							
										
									}
								
								return false;
							}
					});
				
				
				
				tab1=(ImageView)findViewById(R.id.tab1);
				tab2=(ImageView)findViewById(R.id.tab2);
				tab3=(ImageView)findViewById(R.id.tab3);
				tab4=(ImageView)findViewById(R.id.tab4);
				
				tab1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(Login_Screen.this,Login_Screen.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					
						/*Intent mainintent=new Intent(Login_Screen.this,Member_Screen.class);
						startActivity(mainintent);
						finish();*/
						
					}
				});
				
				tab3.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(View v) {
					
						/*Intent mainintent=new Intent(Login_Screen.this,Attendence.class);
						startActivity(mainintent);
						finish();
						*/
					}
				});
				
				tab4.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(View v) {
			
						/*Intent mainintent=new Intent(Login_Screen.this,Task_Screen.class);
						startActivity(mainintent);
						finish();*/
				
					}
				});
				
				
			}

		Handler loginhandler = new Handler() 
			{
				public void handleMessage(Message msg) 
					{
						int selection = msg.what;
						switch (selection) 
							{
								case 1:
									if (msuccess.equalsIgnoreCase("true"))
										{
											Intent open_main = new Intent(Login_Screen.this,Main_Screen.class);
											startActivity(open_main);
											SharedPreferences global_storage =  getSharedPreferences("global_data",MODE_PRIVATE);
											Editor edit = global_storage.edit();
											edit.putString("LOGIN","true");
											edit.commit();
											finish();
										} 
									else if (mMessage.equals(""))
										{
											dialog.dismiss();
											showAlertDialog("No Response!.Please Try Again");
										}
									else
										{
											showAlertDialog(mMessage);
										}
									break;
				
								case 2:
									dialog.dismiss();
									showAlertDialog(response);
								break;
							}
						dialog.dismiss();
					}
			};
	
		public void showAlertDialog(String message)
			{ 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login_Screen.this);
			
				alertDialogBuilder.setMessage(message).setCancelable(true)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() 
						{
							public void onClick(DialogInterface dialog, int id) 
								{
									//finish();
								}
						});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		
		
		public void login_response()
			{
				dialog = ProgressDialog.show(Login_Screen.this,	"Loging in...", "Please wait...");
		
				if (isConnectingToInternet()) 
					{
						new Thread(new Runnable() 
							{
								@Override
								public void run() 
									{
										System.out.println("username" + muserName+ "....." + "pass" + mPassword);
										response = call.loginMethodCall(muserName, mPassword, "login");
										System.out.println("Response.." + response);
								
										if(response.contains("java.net.UnknownHostException")||response.contains("java.net.ConnectException") || response.contains("java.net.SocketException"))
											{
												loginhandler.sendEmptyMessage(2);
											}
										else
											{
												try {
														JSONObject obj = new JSONObject(response);
														msuccess = obj.getString("success");
														
														if (msuccess.equalsIgnoreCase("true")) 
															{
																JSONObject message = obj.getJSONObject("message");
																mmeber_no = message.getString("mem_no");
																mFirstName = message.getString("first_name");
																mSurname = message.getString("surname_name");
																mhighestrole = message.getString("highest_role");
			
																System.out.println(">>>>>>>"
																		+ mmeber_no + "/n"
																		+ "mfirstname" + mFirstName
																		+ "/n mlastname" + mSurname
																		+ "/n mhighestrole"
																		+ mhighestrole);
																String mem_no = mmeber_no;//"17961";
																String highest_roll = mhighestrole;
													
																SharedPreferences global_storage =  getSharedPreferences("global_data",MODE_PRIVATE);
																Editor edit = global_storage.edit();
																edit.putString("MEMBER_NO",mem_no);
																edit.putString("HIGHEST_ROLL",highest_roll);
																edit.commit();
															}
														else
															{
																mMessage = obj.getString("message");
															}
														loginhandler.sendEmptyMessage(1);
													}
												catch (JSONException e)
													{
															System.out.println("in excpt");
															e.printStackTrace();
															loginhandler.sendEmptyMessage(2);
													}
											}
									}
							}).start();
						} 
					else 
						{
							dialog.dismiss();
							Toast.makeText(Login_Screen.this,"Network is not available", Toast.LENGTH_SHORT).show();
						}
				}
		
		
		//============================== Check for  valid email============================================
		
		public final static boolean isValidEmail(String chk_mail)
			{
				CharSequence inputStr = chk_mail;
			    if (chk_mail == null) 
			    	{
			        	return false;
			    	} 
			    else 
			    	{
			    		return android.util.Patterns.EMAIL_ADDRESS.matcher(inputStr).matches();
			    	}
			}
		
		
		
		
		
		/**
		 * This method is used to check the internet connection status
		 * 
		 * @return boolean status of the internet connection
		 */
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


		@Override
		public void onBackPressed() {
			
			finish();
			
			super.onBackPressed();
			
		}
		
		
		
	
}
