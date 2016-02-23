package example.sofarmanager;

//========================================//
//      Author --> Biplab De              //
//       Date:-> 26/02/2014               //
//========================================//

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;

public class NewUserRegistartion extends Activity implements OnItemSelectedListener {

	CallApi webservice;
	NewUserRegistartion inst;
	
	TextView cancle_btn, register_btn;
	EditText id,mob_no,pwd,cnf_pwd,email; 
	CheckBox eng,affrk;
	
	ArrayList<String> language_list;
	ArrayList<String> languageid;
	String selected_id,error_msg;
	
	public ProgressDialog dialog;
	String upload_data_response,memNo_reg,messages ;
	
	ImageView tab1,tab2,tab3,tab4,backbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_screen);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		inst = this;
		webservice = new CallApi();
		selected_id = new String();
		
		register_btn = (TextView) findViewById(R.id.registerbtn);
		//cancle_btn = (TextView) findViewById(R.id.canclebtn);
		
		id = (EditText) findViewById(R.id.idno);
		mob_no = (EditText) findViewById(R.id.cellnumber);
		pwd = (EditText) findViewById(R.id.password_r);
		cnf_pwd = (EditText) findViewById(R.id.conformpass);	
		email = (EditText) findViewById(R.id.mail_id_r);	
		
		eng = (CheckBox) findViewById(R.id.engselectcheck);
		affrk = (CheckBox) findViewById(R.id.africanselectcheck); 
		
		
		affrk.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) 
					{
						if (eng.isChecked()) 
							{
								eng.setChecked(false);
							}
						selected_id = "2";
					}
			});
		
		eng.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
				{
					if (affrk.isChecked()) 
						{
							affrk.setChecked(false);
						}
					selected_id = "1";
				}
		});
		
		//cancle_btn.setOnClickListener(all_click_listener);
		register_btn.setOnClickListener(all_click_listener);
		
		
		
		
		tab1=(ImageView)findViewById(R.id.tab1);
		tab2=(ImageView)findViewById(R.id.tab2);
		tab3=(ImageView)findViewById(R.id.tab3);
		tab4=(ImageView)findViewById(R.id.tab4);
		
		tab1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(NewUserRegistartion.this,Login_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				/*Intent mainintent=new Intent(NewUserRegistartion.this,Member_Screen.class);
				startActivity(mainintent);
				finish();*/
				
			}
		});
		
		tab3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
			
				/*Intent mainintent=new Intent(NewUserRegistartion.this,Attendence.class);
				startActivity(mainintent);
				finish();*/
				
			}
		});
		
		tab4.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
	
				/*Intent mainintent=new Intent(NewUserRegistartion.this,Task_Screen.class);
				startActivity(mainintent);
				finish();*/
		
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
	
	
	@Override
	protected void onResume() 
		{
			super.onResume();
		}

	// ========================== Click Listener For All==========================
	@SuppressWarnings({ "deprecation", "deprecation" })
	View.OnClickListener all_click_listener = new View.OnClickListener() 
		{
			public void onClick(View v) 
				{
					int Id = v.getId();
					switch (Id) {
					/*case R.id.canclebtn:
						finish();
					break;*/
					
					case R.id.registerbtn:
						int[] stat_val = new int[6];						
						String sid,smob_no,spwd,scnfpwd,emailid;
						sid = id.getText().toString();
						smob_no = mob_no.getText().toString();
						spwd = pwd.getText().toString();
						scnfpwd = cnf_pwd.getText().toString();
						emailid = email.getText().toString().trim();
						
						stat_val[0] = selected_id.length();
						stat_val[1] = sid.length();
						stat_val[2] = smob_no.length();
						stat_val[3] = spwd.length();
						stat_val[4] = scnfpwd.length();
						stat_val[5] = emailid.length();
						
						int count = 0;
						
						if(stat_val[0] == 0 )
							{
								showAlertDialog("Please select language");
							}
						else
							{
								if(affrk.isChecked() || eng.isChecked())
									{
										count  = count + 1;
									}
								else
									{
										showAlertDialog("Please select language");
									}
							}
						
						if(stat_val[1] == 0 )
							{
								id.setError("This field can't be blank");
							}
						else
							{
								count  = count + 1;
							}
						
						if(stat_val[2] == 0 )
							{
								mob_no.setError("This field can't be blank");
							}
						else
							{
								if(PhoneNumberUtils.isGlobalPhoneNumber(smob_no.toString()))
									{
										count=count+1;
									}
								else
									{
										mob_no.setError("Cellular No is not valid");
									}
							}
						
						if(stat_val[3] == 0 )
							{	
								pwd.setError("This field can't be blank");
							}
						else
							{
								count  = count + 1;
							}
						
						
						if(stat_val[4] == 0 )
							{
								cnf_pwd.setError("This field can't be blank");
							}
						else
							{
								if(spwd.equals(scnfpwd))
									{
										count  = count + 1;
									}
								else
									{
										cnf_pwd.setError("Password doesn't match");
									}
							}
						
						if(stat_val[5] == 0 )
							{
								email.setError("This field can't be blank");
							}
						else
							{
								if(isValidEmail(emailid))
									{
										count  = count + 1;
									}
								else
									{
										email.setError("Please provide valid email");
									}
							}
						
						
						if(count ==6)
							{
								register_user();
								//showAlertDialog("Sending Details");
							}
						else
							{	
								//System.out.println("count last= "+count);
								//showAlertDialog("Please Fill The Details Properly");
							}
						  					
					break;
		
					default:
					break;
				}
			}
	 };
	
	
	
	public final static boolean isValidEmail(String target) {
		CharSequence inputStr = target;
	    if (target == null) {
	        return false;
	    } else {
	    		return android.util.Patterns.EMAIL_ADDRESS.matcher(inputStr).matches();
	    	}
	}
	
	
	

	// ======================================== Send The Data to Web Server Code=============================

	public void register_user() 
		{
			dialog = ProgressDialog.show(inst, "Registering User...","Please wait...", true, false);
			dialog.setCanceledOnTouchOutside(false);
			upload_data_response = new String();
			Thread thread = new Thread(new Runnable()
				{
					@Override
					public void run() {
										try {
												if (check_network()) 
													{															
														String id_no = id.getText().toString();
														String mobile_no = mob_no.getText().toString();
														String language = selected_id;
														String password = pwd.getText().toString();
														String cnf_password = cnf_pwd.getText().toString();
														int platform_id =2 ;
														String mailid = email.getText().toString().trim();
														
														String device_id = Secure.getString(inst.getContentResolver(),Secure.ANDROID_ID);
														
														System.out.println("id = "+device_id);
														
														upload_data_response =webservice.newUserRegistration(id_no,mailid,mobile_no,language,password,cnf_password,platform_id,device_id,"new_registration");
														
														if(upload_data_response.contains("java.net.UnknownHostException")||upload_data_response.contains("java.net.ConnectException") || upload_data_response.contains("java.net.SocketException"))
															{
																error_msg = new String();
																error_msg = upload_data_response;
																Data_handler.sendEmptyMessage(2);
															}
														else{
																if (upload_data_response == null) 
																	{
																		Data_handler.sendEmptyMessage(0);
																	}	 
																else if (upload_data_response != null) 
																	{
																		Data_handler.sendEmptyMessage(1);
																	}
															}
													} 
												else 
													{
														showAlertDialog("No Network Connection");
													}
										} 
									catch (Exception e) 
										{
											e.printStackTrace();
											Data_handler.sendEmptyMessage(2);
										}
								}
						});
				thread.start();
	}
	// ========= End of upload data to server======================
	
	
	

	// =================================== Handler For Async=================================

	private Handler Data_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (dialog != null)
					{
						dialog.dismiss();
					}
				showAlertDialog("Server is busy");
				break;

			case 1:
				try {
						JSONObject jobj = new JSONObject(upload_data_response);
						String success = jobj.getString("success");
						
						if (success.equalsIgnoreCase("true")) 
							{
								messages = new String();
								messages = jobj.getString("message");
								
								memNo_reg = jobj.getString("user_mem_no");
										
								id.setText("");
								mob_no.setText("");
								pwd.setText("");
								cnf_pwd.setText("");
								email.setText("");
								
								if(eng.isChecked())
									{
										eng.setChecked(false);
									}
								
								if(affrk.isChecked())
									{
										affrk.setChecked(false);
									}
								
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewUserRegistartion.this);
								alertDialogBuilder.setMessage(messages).setCancelable(true)
									.setPositiveButton("OK", new DialogInterface.OnClickListener() 
										{
											public void onClick(DialogInterface dialog, int id) 
												{
													Intent add_question = new Intent(NewUserRegistartion.this,Ask_Security_Questions_Screen.class);
													add_question.putExtra("memnumber", memNo_reg);
													startActivity(add_question);
													finish();
												}
										});
								AlertDialog alertDialog = alertDialogBuilder.create();
								alertDialog.show();
								
							}
						else{
								String messages = null;
								messages = jobj.getString("message");
								showAlertDialog(messages);
							}

					} 
				catch (Exception e) 
				  	{
						e.printStackTrace();
				  	}

				break;
				
				case 2:
					showAlertDialog(error_msg);
				break;
				
				default:
				break;
			}
			if (dialog != null) {
				dialog.dismiss();
			}
			super.handleMessage(msg);
		}
	};
	
	public void showAlertDialog(String message)
		{ 
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewUserRegistartion.this);
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

	// ========================Check The internet is avialable or not====================================
	public boolean check_network() 
		{
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if (ni != null && ni.isConnected()) 
				{
					Log.e("Internet is: ", "Present");
					return true;
				} 
			else 
				{
					Log.e("Internet is: ", "Absent");
					return false;
				}
	}

	// ========================= Spinner Selection===============================
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) 
		{
			selected_id = languageid.get(pos);
		}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) 
		{

		}
}
