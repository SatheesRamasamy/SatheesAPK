package example.sofarmanager;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;

public class Ask_Security_Questions_Screen extends Activity 
	{
		EditText first_que,first_ans,second_que,second_ans;
		TextView submit, cancel;
		ProgressDialog dialog;
		CallApi call;
		String response,message,error_msg,mem_number;
		
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				setContentView(R.layout.activity_ask_question);
				this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				
						
				mem_number = getIntent().getStringExtra("memnumber");
				System.out.println("memNo ="+mem_number);
				
				
				
				first_que = (EditText) findViewById(R.id.first_q);
				first_ans = (EditText) findViewById(R.id.f_ans);
				second_que = (EditText) findViewById(R.id.second_q);
				second_ans = (EditText) findViewById(R.id.s_ans);
				
				
				submit = (TextView) findViewById(R.id.sub_ans);
				cancel = (TextView) findViewById(R.id.cancel_ans);
				call = new CallApi();
		
				cancel.setOnClickListener(new OnClickListener() 
					{
						@Override
						public void onClick(View v)
							{
								first_que.setText("");
								first_ans.setText("");
								second_que.setText("");
								second_ans.setText("");
							}
					});	
				
				submit.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
							{		
								int count = 0;
								String f_q,f_a,s_q,s_a;
								
								f_q = first_que.getText().toString().trim();
								f_a = first_ans.getText().toString().trim();
								s_q = second_que.getText().toString().trim();
								s_a = second_ans.getText().toString().trim();
								
								if(f_q.length()==0)
									{
										first_que.setError("This Field Can't Be Empty");
									}
								else
									{
										count = count + 1;
									}
								
								if(f_a.length()==0)
									{
										first_ans.setError("This Field Can't Be Empty");
									}
								else
									{
										count = count + 1;
									}
								
								if(s_q.length()==0)
									{
										second_que.setError("This Field Can't Be Empty");
									}
								else
									{
										count = count + 1;
									}
							
								if(s_a.length()==0)
									{
										second_ans.setError("This Field Can't Be Empty");
									}
								else
									{
										count = count + 1;
									}
								
								
								if(count == 4)
									{
										addSecurityQuestions();
									}
								else
									{
										//showAlertDialog(Ask_Security_Questions_Screen.this, "Please Check All The Fields");
									}

							}
					});

		
			}
	
	
		public void addSecurityQuestions()
			{
				dialog = ProgressDialog.show(Ask_Security_Questions_Screen.this, "Adding Security Questions And Answers...", "Please wait...",true, false);
				dialog.setCanceledOnTouchOutside(false);
				new Thread(new Runnable()
					{
						@Override
						public void run()
							{
								String que1, ans1, que2, ans2,memNo;
								
								que1 = first_que.getText().toString().trim();
								ans1 = first_ans.getText().toString().trim();
								que2 = second_que.getText().toString().trim();
								ans2 = second_ans.getText().toString().trim();
								
								
								response = call.add_security_question_and_answer(mem_number, que1, ans1, que2, ans2);
								if(response.contains("java.net.UnknownHostException")||response.contains("java.net.ConnectException") || response.contains("java.net.SocketException"))
									{
										error_msg = new String();
										error_msg = response;
										handlerobject.sendEmptyMessage(1);
									}
								else{
										if(response==null)
											{
												handlerobject.sendEmptyMessage(2);
											}
										else
											{
												try {
														JSONObject obj = new JSONObject(response);
														if(obj!=null)
															{
																String succcess = obj.getString("success");
																message = obj.getString("message");														
																if(succcess.equalsIgnoreCase("true"))
																	{
																		handlerobject.sendEmptyMessage(3);	
																	}
																if(succcess.equalsIgnoreCase("false"))
																	{
																		handlerobject.sendEmptyMessage(4);
																	}
															}
														
														}	 
													catch (JSONException e) 
														{
															e.printStackTrace();
															error_msg = new String();
															error_msg = e.toString();
															handlerobject.sendEmptyMessage(1);
														}
												}
									}
								}
						}).start();
				}
	
	
	

	Handler handlerobject = new Handler() {
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) 
				{
					case 1:
						if (dialog != null) 
							{
								dialog.dismiss();
							}
						showAlertDialog(Ask_Security_Questions_Screen.this, error_msg);
						//clearForm();
					break;
					
					case 2:
						if (dialog != null) 
							{
								dialog.dismiss();
							}
						showAlertDialog(Ask_Security_Questions_Screen.this, "No response from server");
						
					break;
					
					case 3:
						if (dialog != null) 
							{
								dialog.dismiss();
							}
						
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Ask_Security_Questions_Screen.this);
						alertDialogBuilder.setMessage(message).setCancelable(true)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() 
								{
									public void onClick(DialogInterface dialog, int id) 
										{
											Intent login = new Intent(Ask_Security_Questions_Screen.this,Login_Screen.class);
											startActivity(login);
										}
								});
						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
					break;
					
					case 4:
						if (dialog != null) 
							{
								dialog.dismiss();
							}
						showAlertDialog(Ask_Security_Questions_Screen.this, message);
						
					break;
				

					default:
					break;
			}
		};

	};
	
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

	
	public void showAlertDialog(Context con, String message)
		{ 
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
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
	
	public void clearForm()
		{
		
		}

}
