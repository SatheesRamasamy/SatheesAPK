package example.sofarmanager;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ForgetPassword_Screen extends Activity {
	EditText mailid;
	TextView send;
	Dialog dialog;
	CallApi call;
	String success, message,email,error_response;
	ArrayList<String>questions;
	int id_arr[]={0,0};
	
	ImageView backbtn,tab1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.forgetpasswordscreen);
			
			mailid = (EditText) findViewById(R.id.fg_mailid);
			send = (TextView) findViewById(R.id.login_btn);
			
			tab1=(ImageView)findViewById(R.id.forgettab1);
			
			tab1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					Intent homeintent=new Intent(ForgetPassword_Screen.this,Login_Screen.class);
					startActivity(homeintent);
					finish();
					
				}
			});
	
			send.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) 
						{
							email = new String();
							email = mailid.getText().toString().trim();
							
							if(isValidEmail(email))
								{
									get_security_questions();
								}
							else
								{	
									mailid.setError("Please Provide Proper Email-id");
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
	
	
	public void get_security_questions()
	{
		dialog = ProgressDialog.show(ForgetPassword_Screen.this,"Loading Questions...", "please wait...");
		new Thread(new Runnable() {

			@Override
			public void run() 
				{
					call = new CallApi();
					String response = call.getSecurityQuestion(email);
					if(response.contains("java.net.UnknownHostException")||response.contains("java.net.ConnectException") || response.contains("java.net.SocketException"))
						{
							error_response = new String();
							error_response = response;
							handler.sendEmptyMessage(2);
						}
					else
						{
							try 
								{
									JSONObject obj = new JSONObject(response);
									success = obj.getString("success");
									if (success.equalsIgnoreCase("true"))
										{
											questions = new ArrayList<String>();
											JSONArray result_array = obj.getJSONArray("message");
											
											for (int i = 0; i < result_array.length(); i++)
												{
													String qid = result_array.getJSONObject(i).getString("question_id");
													id_arr[i] = Integer.parseInt(qid);
													
													String que = result_array.getJSONObject(i).getString("question");
													questions.add(que);
												}
											handler.sendEmptyMessage(1);
										}
									else
										{
											message=obj.getString("message");
											handler.sendEmptyMessage(0);
										}
								} 
							catch (JSONException e) 
								{
									e.printStackTrace();
									error_response = new String();
									error_response = e.toString();
									handler.sendEmptyMessage(3);
								}
						}
				}
		}).start();
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
	
	Handler handler = new Handler() 
		{
			public void handleMessage(Message msg) 
				{
					int val = msg.what;
		
					dialog.dismiss();
					switch (val) 
					{
						case 0:
							showAlertDialog(message);
						break;
						
						case 1:
							//showAlertDialog(message);
							Intent que_ans = new Intent(ForgetPassword_Screen.this,Answer_Security_Question_Screen.class);
							que_ans.putExtra("qid", id_arr);
							que_ans.putStringArrayListExtra("question", questions);
							startActivity(que_ans);
							
						break;
						
						case 2:
							showAlertDialog(error_response);
						break;
						
						case 3:
							showAlertDialog(error_response);
						break;
						
						default:
						break;
					}
				};
		};
		
	@Override
	public void onBackPressed()
		{

			Intent open_log = new Intent(ForgetPassword_Screen.this,Login_Screen.class);
			startActivity(open_log);
			
			
			super.onBackPressed();
		}
		
	
	public void showAlertDialog(String message)
	{ 
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ForgetPassword_Screen.this);
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
}
