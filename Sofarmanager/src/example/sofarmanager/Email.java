package example.sofarmanager;

//========================================//
//      Author --> Biplab De              //
//       Date:-> 26/02/2014               //
//========================================//

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Email extends Activity 
{
	CallApi webservice;
	Email inst;
	public ProgressDialog dialog;
	EditText from,subject,message; //receipent,
	TextView clear_btn, send_btn;
	String web_response,web_response_email;
	ArrayList<String> email_list,selected_email_list;
	String from_mem,sub,mesg;
	EditText receipent;
	
	ImageView backbtn;
	
	protected CharSequence[] _options;
	protected boolean[] _selections;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.email_edit);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		
		inst = this;
		webservice = new CallApi();
		dialog = new ProgressDialog(this, AlertDialog.THEME_TRADITIONAL);
		web_response_email = new String();
		selected_email_list = new ArrayList<String>();
		//============================= Initialize all the fields with objects==================
		receipent = (EditText) findViewById(R.id.recipient_text);
		//from = (EditText) findViewById(R.id.from_text);
		subject = (EditText) findViewById(R.id.sub_text);
		message = (EditText) findViewById(R.id.mesage_text);
		send_btn = (TextView) findViewById(R.id.send);
		clear_btn = (TextView) findViewById(R.id.clear);
		//receipent = (AutoCompleteTextView)findViewById(R.id.recipient_text);	 
		
		send_btn.setOnClickListener(all_click_listener);
		clear_btn.setOnClickListener(all_click_listener);
		receipent.setOnClickListener(all_click_listener);
		
		//from.setText(Util.mailId);
		get_mail_id();	
		
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
					
					switch (Id) 
						{
							case R.id.recipient_text:
								showDialog(0);
							break;
																					
							case R.id.send:
									email_list = new ArrayList<String>();
									from_mem = new String();
									sub = new String();
									mesg = new String();
									boolean all_valid_email = true;
									int count=0;
									SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
									String mem_no = global_storage.getString("MEMBER_NO", "");
								
									String e_list = receipent.getText().toString();
									from_mem = mem_no;//from.getText().toString();
									sub = subject.getText().toString();
									mesg = message.getText().toString();
									
									
									if(e_list.length()==0)
									{
										
										receipent.setError("This field can not be blank");
										
									}else
										{
											String[] separated = e_list.split(",");
											for(int i=0;i<separated.length;i++)
												{
													separated[i] = separated[i].trim();
													if(isValidEmail(separated[i]))
														{
															email_list.add(separated[i]);
														}
													else{	
															all_valid_email = false;
															receipent.setError("Please Provide Proper Email-id");
															break;
														}
												}
											if(all_valid_email)
												{
													count = count + 1;
												}
										}
									
									if(from_mem.length()==0)
										{
											//from.setError("This field can not be blank");
										}
									else
										{
											count = count + 1;	
										}
																				
									if(sub.length()==0)
										{
											subject.setError("This field can not be blank");
										}
									else
										{
											count = count + 1;
										}
									
									if(mesg.length()==0)
										{
											message.setError("This field can not be blank");
										}
									else
										{
											count = count + 1;
										}
									if(count == 4)
										{
											send_mail();
										}
									else
										{
											//Toast.makeText(inst,"Please Check Detalils Before Sending",Toast.LENGTH_SHORT).show();
										}
							break;
							
							case R.id.clear:
									clear_all_field();
							break;
							
							default:
							break;
						}
				}
		};// END
	
	//======================================= Clear all the field======================================
		
	public void clear_all_field()
		{
			receipent.setText("");
			//from.setText("");
			subject.setText("");
			message.setText("");
			receipent.setHint("Recipients");
			//from.setHint("From");
			subject.setHint("Subject");
			message.setHint("Type Your Message");
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
	
	
	
	public void get_mail_id() {
		dialog = ProgressDialog.show(inst, "Loading Email id...", "Please wait...",
				true, false);
		dialog.setCanceledOnTouchOutside(false);

		
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (check_network()) {
						SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
						String mem_no = global_storage.getString("MEMBER_NO", "");
					
						String method_to_get_data = "get_email_recipients_list";				
						String sgid= webservice.get_sg_id(mem_no);
						String memno = mem_no;
						JSONObject obj=new JSONObject(sgid);
						sgid=obj.getString("message");
						
						web_response_email = webservice.get_email_receipients(sgid,memno, method_to_get_data);
						
						if(web_response_email.contains("java.net.UnknownHostException")||web_response_email.contains("java.net.ConnectException") || web_response_email.contains("java.net.SocketException"))
							{
								inst.Data_handler.sendEmptyMessage(2);
							}
						else
							{
								if (web_response_email == null) 
									{
										inst.Data_handler.sendEmptyMessage(4);
									} 
								else if (web_response_email != null) 
									{
										inst.Data_handler.sendEmptyMessage(3);
									}
							}
					}
				} 
				catch (Exception e) 
					{
						e.printStackTrace();
						inst.Data_handler.sendEmptyMessage(5);
					}
			}
		});
		thread.start();
	}
	
	
	
	
		
	// =================================== Get all The data from Web Service Code===============================================================

	public void send_mail()
		{
			dialog = ProgressDialog.show(inst, "Sending Mail...", "Please wait...",true, false);
			dialog.setCanceledOnTouchOutside(false);
			Thread thread = new Thread(new Runnable() 
				{
					@Override
					public void run() 
						{
							try {
									if (check_network()) 
										{
											String method_call = "send_email";
											web_response = new String();
											web_response = webservice.Email(email_list,from_mem,sub,mesg,method_call);
											if(web_response.contains("java.net.UnknownHostException")||web_response.contains("java.net.ConnectException") || web_response.contains("java.net.SocketException"))
												{
													inst.Data_handler.sendEmptyMessage(2);
												}
											else{
													if (web_response == null) 
														{
															inst.Data_handler.sendEmptyMessage(0);
														} 
													else if (web_response != null) 
														{
															inst.Data_handler.sendEmptyMessage(1);
														}
												}
										}
									else
										{
										    
										}
								} 
							catch (Exception e) 
								{
									e.printStackTrace();
									inst.Data_handler.sendEmptyMessage(-1);
								}
						}
				});
			thread.start();
		}
	
	
	// =================================== Handler For Async=================================

	private Handler Data_handler = new Handler() 
		{
			@Override
			public void handleMessage(Message msg) 
				{
					switch (msg.what) 
						{
							case 0:
								if (dialog != null) 
									{
										dialog.dismiss();
									}
								String message;
								message = "Mail Sending Failed";
								showAlertDialog(inst,message );
							break;
							
							case 1:
								try {
										if (dialog != null) 
											{
												dialog.dismiss();
											}
																				
										JSONObject jobj = new JSONObject(web_response);
										String success = jobj.getString("success");
										if (success.equalsIgnoreCase("true")) 
											{
												showAlertDialog(inst, jobj.getString("message"));
											    System.out.println("Success = ");
												clear_all_field();
											}
										else{
												showAlertDialog(inst, jobj.getString("message"));
										}
									} 
								catch (Exception e) 
									{
										e.printStackTrace();
									}
							break;
							
							case 2:
								if (dialog != null) 
								{
									dialog.dismiss();
								}
								showAlertDialog(inst, web_response_email);
							break;
							
							case 3:
								try {
										JSONObject jobj = new JSONObject(web_response_email);
										String success = jobj.getString("success");
										if (success.equalsIgnoreCase("true")) 
											{
												email_list = new ArrayList<String>();
												selected_email_list = new ArrayList<String>();
												JSONArray result_array = null;
												result_array = jobj.getJSONArray("items");
												
												// adding the name to the list
												for (int i = 0; i < result_array.length(); i++)
													{
														String emailid = result_array.getJSONObject(i).getString("E_Mail");
														if(isValidEmail(emailid))
															{
																email_list.add(emailid);
															}
													}
											
												_options = new CharSequence[email_list.size()];
												_selections = new boolean[_options.length];
												for (int i = 0; i < email_list.size(); i++) 
													{
														_options[i] = email_list.get(i);
														// System.out.println("_options:"+_options[i]);
													}
											}
										else
											{
												showAlertDialog(inst, jobj.getString("error"));
											}
									
										if (dialog != null) 
											{
												dialog.dismiss();
											}
									}	 
								catch (Exception e) 
									{
										e.printStackTrace();
										showAlertDialog(inst, "Invalid Data");
									}
							break;
							
							case 4:
								System.out.println("In 4");
								showAlertDialog(inst, "Please check the internet connection");
							break;
							
							default:
							break;
						}
					if (dialog != null) 
						{
							dialog.dismiss();
						}
					super.handleMessage(msg);
				}
		};
		
		
		
		
		
	//========================================== Email List for Mail====================================
		@Override
		protected Dialog onCreateDialog(int id)
			{
			
				ContextThemeWrapper cw = new ContextThemeWrapper( this, R.style.AlertDialogTheme );
				return new AlertDialog.Builder(cw)
					.setTitle("Select Email")
					.setMultiChoiceItems(_options, _selections, new DialogSelectionClickHandler())
					.setPositiveButton("OK",new Dialog_Positive_ButtonClickHandler())
					// .setNegativeButton("CANCLE", new
					// Dialog_Negative_ButtonClickHandler())*/
					.create();

			}

		public class DialogSelectionClickHandler implements	DialogInterface.OnMultiChoiceClickListener 
			{
				public void onClick(DialogInterface dialog, int clicked,boolean selected) 
					{
						Log.i("ME", _options[clicked] + "selected: " + selected);
						
		
						if (selected == true) {
							selected_email_list.add((String) _options[clicked]);
						}
						if (selected == false) {
							selected_email_list.remove((String) _options[clicked]);
						}
					}
			}

		public class Dialog_Positive_ButtonClickHandler implements
				DialogInterface.OnClickListener {
			public void onClick(DialogInterface dialog, int clicked) {
				switch (clicked) {
				case DialogInterface.BUTTON_POSITIVE:
					receipent.setText("");
					String set_text = new String("");
					for (int i = 0; i < selected_email_list.size(); i++) {
						String s = selected_email_list.get(i);
						set_text = set_text + s + ",";
					}
					receipent.setText(set_text);
					break;
				}
			}
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
					System.out.println("In 4");
					inst.Data_handler.sendEmptyMessage(4);
					return false;
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

}