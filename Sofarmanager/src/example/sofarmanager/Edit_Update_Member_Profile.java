package example.sofarmanager;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;

public class Edit_Update_Member_Profile extends Activity implements OnItemSelectedListener
	{
		EditText FirstName,Surname,Full_Names, DOB,dob_tv,Email,MobileNumber,WorkNumber,HomePhone,
		ResidentialAddress,Street,Subur,City,Province,Occupation;
		TextView edit, cancel;
		int day, month, year;
		String mem_details_response,update_mem_details_response,message,mem_no,error_msg,lang_id,lang_selected_task_id;
		ProgressDialog dialog;
		CallApi call;
		Spinner Language;
		RelativeLayout date_layout;
				
		ImageView tab1,tab2,tab3,tab4,backbtn;
		
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				setContentView(R.layout.activity_edit_update_member_profile);
				this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			
				call = new CallApi();
				
				mem_no = getIntent().getStringExtra("memid");
				System.out.println("memid = "+mem_no);
				
				final Calendar c = Calendar.getInstance();
				year = c.get(Calendar.YEAR);
				month = c.get(Calendar.MONTH);
				day = c.get(Calendar.DAY_OF_MONTH);
				
				FirstName = (EditText) findViewById(R.id.f_name);
				Surname = (EditText) findViewById(R.id.s_name);
				Full_Names  = (EditText) findViewById(R.id.full_name);
				DOB = (EditText) findViewById(R.id.dob_txt);
				Language = (Spinner) findViewById(R.id.lang_name);
				Email = (EditText) findViewById(R.id.email_txt);
				MobileNumber = (EditText) findViewById(R.id.mobile_no);
				WorkNumber = (EditText) findViewById(R.id.work_no_txt);
				HomePhone = (EditText) findViewById(R.id.home_no_txt);
				//ResidentialAddress = (EditText) findViewById(R.id.residential_address_txt);
				Street = (EditText) findViewById(R.id.street_txt);
				Subur = (EditText) findViewById(R.id.suburbn_txt);
				City = (EditText) findViewById(R.id.scity_txt);
				Province = (EditText) findViewById(R.id.province_txt);
				Occupation = (EditText) findViewById(R.id.occupation_txt);
				
				edit = (TextView) findViewById(R.id.vmem_edit);
				//cancel = (TextView) findViewById(R.id.vmem_cancel);
				dob_tv =  (EditText)findViewById(R.id.dob_txt);
				
				date_layout = (RelativeLayout) findViewById(R.id.dt_layout);
							
				date_layout.setOnClickListener(new OnClickListener() 
					{
						@Override
						public void onClick(View v) 
							{
								if(edit.getText().toString().equals("Update"))
								{
									showDialog(1);
								}
							}
					});
				
				dob_tv.setOnClickListener(new OnClickListener() 
					{
						@Override
						public void onClick(View v) 
							{
								if(edit.getText().toString().equals("Update"))
								{
									showDialog(1);
								}
							}
					});
				
				deactive_edit_option();
				
				ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Language,android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				Language.setAdapter(adapter);
				
			
				
				
				/*cancel.setOnClickListener(new OnClickListener() 
					{
						@Override
						public void onClick(View v)
							{
								finish();//deactive_edit_option();
							}
					});	*/
				
				edit.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
							{
								if(edit.getText().toString().equals("Update"))
									{
										update_member_details();
									}
								else
									{
										active_edit_option();
									}
							}
					});
	
				Language.setOnItemSelectedListener(this);
				get_member_details();
				
				
				
				tab1=(ImageView)findViewById(R.id.tab1);
				tab2=(ImageView)findViewById(R.id.tab2);
				tab3=(ImageView)findViewById(R.id.tab3);
				tab4=(ImageView)findViewById(R.id.tab4);
				backbtn=(ImageView)findViewById(R.id.back_btn);
				
				tab1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(Edit_Update_Member_Profile.this,Main_Screen.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(Edit_Update_Member_Profile.this,Member_Screen.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab3.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(Edit_Update_Member_Profile.this,Attendence.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab4.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(View v) {
			
						Intent mainintent=new Intent(Edit_Update_Member_Profile.this,Task_Screen.class);
						startActivity(mainintent);
						finish();
				
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
		
		
		public void active_edit_option()
			{
				FirstName.setEnabled(true);
				Surname.setEnabled(true);
				Full_Names.setEnabled(true);
				DOB.setEnabled(true);
				Language.setEnabled(true);
				Email.setEnabled(true);
				MobileNumber.setEnabled(true);
				WorkNumber.setEnabled(true);
				HomePhone.setEnabled(true);
				//ResidentialAddress.setEnabled(true);
				Street.setEnabled(true);
				Subur.setEnabled(true);
				City.setEnabled(true);
				Province.setEnabled(true);
				Occupation.setEnabled(true);
				edit.setText("Update");
			}
		
		public void deactive_edit_option()
			{
				FirstName.setEnabled(false);
				Surname.setEnabled(false);
				Full_Names.setEnabled(false);
				DOB.setEnabled(false);
				Language.setEnabled(false);
				Email.setEnabled(false);
				MobileNumber.setEnabled(false);
				WorkNumber.setEnabled(false);
				HomePhone.setEnabled(false);
				//ResidentialAddress.setEnabled(false);
				Street.setEnabled(false);
				Subur.setEnabled(false);
				City.setEnabled(false);
				Province.setEnabled(false);
				Occupation.setEnabled(false);
				edit.setText("   Edit   ");
			}
		
		
		public void get_member_details()
			{
				dialog = ProgressDialog.show(Edit_Update_Member_Profile.this, "Member Details Loading...", "Please wait...",true, false);
				dialog.setCanceledOnTouchOutside(false);
				new Thread(new Runnable()
					{
						@Override
						public void run()
							{
								mem_details_response = call.get_mem_details(mem_no);
								
								if(mem_details_response.contains("java.net.UnknownHostException")||mem_details_response.contains("java.net.ConnectException") || mem_details_response.contains("java.net.SocketException"))
									{
										error_msg = mem_details_response;
										handlerobject.sendEmptyMessage(1);
									}
								else{
										if(mem_details_response==null)
											{
												handlerobject.sendEmptyMessage(2);
											}
										else
											{
												try {
														JSONObject obj = new JSONObject(mem_details_response);
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
												catch (JSONException e) 
													{
														e.printStackTrace();
														error_msg = e.toString();
														handlerobject.sendEmptyMessage(1);
													}
											}
									}
								}
						}).start();
				}
		
		
		public void update_member_details()
			{
				dialog = ProgressDialog.show(Edit_Update_Member_Profile.this, "Updating Member Details...", "Please wait...",true, false);
				dialog.setCanceledOnTouchOutside(false);
				new Thread(new Runnable()
					{
						@Override
						public void run()
							{
							
								String f_name,s_name,fl_Names,dob,lang,emailid,mobile,work_n,home_n,
								addr,street,subur,city,province,occupation;
								
								f_name = FirstName.getText().toString();
								s_name = Surname.getText().toString();
								fl_Names = Full_Names.getText().toString();
								
								dob = DOB.getText().toString();
								dob = "20140211";//dob.replace("-", "");
								
								lang = lang_selected_task_id;
								emailid = Email.getText().toString();
								mobile = MobileNumber.getText().toString();
								work_n = WorkNumber.getText().toString();
								home_n = HomePhone.getText().toString();
								//addr = ResidentialAddress.getText().toString();
								street = Street.getText().toString();
								subur = Subur.getText().toString();
								city = City.getText().toString();
								province = Province.getText().toString();
								occupation = Occupation.getText().toString();
								
								update_mem_details_response = call.update_mem_details(mem_no,f_name,s_name,fl_Names,dob,lang,emailid,mobile,
										work_n,home_n,street,subur,city,province,occupation);
								
								if(update_mem_details_response.contains("java.net.UnknownHostException")||update_mem_details_response.contains("java.net.ConnectException") || update_mem_details_response.contains("java.net.SocketException"))
									{
										error_msg = update_mem_details_response;
										handlerobject.sendEmptyMessage(1);
									}
								else{
										if(update_mem_details_response==null)
											{
												handlerobject.sendEmptyMessage(2);
											}
										else
											{
												try {
														JSONObject obj = new JSONObject(update_mem_details_response);
														String succcess = obj.getString("success");
														message = obj.getString("message");														
														if(succcess.equalsIgnoreCase("true"))
															{
																handlerobject.sendEmptyMessage(5);	
															}
														if(succcess.equalsIgnoreCase("false"))
															{
																handlerobject.sendEmptyMessage(6);
															}
													} 
												catch (JSONException e) 
													{
														e.printStackTrace();
														error_msg = e.toString();
														handlerobject.sendEmptyMessage(1);
													}
											}
									}
								}
						}).start();
				}
	
	
	
		
		
		Handler handlerobject = new Handler() 
			{
				public void handleMessage(Message msg)
					{
						int what = msg.what;
						switch (what) 
							{
								case 1:
									if (dialog != null) 
										{
											dialog.dismiss();
										}
									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Edit_Update_Member_Profile.this);
									alertDialogBuilder.setMessage(error_msg).setCancelable(true)
										.setPositiveButton("OK", new DialogInterface.OnClickListener() 
											{
												public void onClick(DialogInterface dialog, int id) 
													{
														//finish();
													}
											});
									AlertDialog alertDialog = alertDialogBuilder.create();
									alertDialog.show();
								break;
						
								case 2:
									if (dialog != null) 
										{
											dialog.dismiss();
										}
									showAlertDialog(Edit_Update_Member_Profile.this, "No Response From Server");
								break;
						
								case 3:
									if (dialog != null) 
										{
											dialog.dismiss();
										}
									JSONObject obj;
									try {
											obj = new JSONObject(message);
											
											FirstName.setText(obj.getString("firstname"));
											Surname.setText(obj.getString("surname"));
											Full_Names.setText(obj.getString("fullnames"));
											DOB.setText(obj.getString("Date_Birth"));
								
											lang_id = obj.getString("language");
											
											if(lang_id.equals("1"))
												{
													Language.setSelection(0);
												}
											if(lang_id.equals("2"))
												{
													Language.setSelection(1);
												}
											
								
											Email.setText(obj.getString("E_Mail"));
											MobileNumber.setText(obj.getString("mobile"));
											WorkNumber.setText(obj.getString("work_number"));
											HomePhone.setText(obj.getString("home_phone"));
								
											String addr1 = obj.getString("Phys_Addr1");
											String addr2 = obj.getString("Phys_Addr2");
											String addr3 = obj.getString("Phys_Addr3");
											String addr4 = obj.getString("Phys_Addr4");
											String add_pc = obj.getString("Phys_PC");
											String addr = addr1 +", "+addr2+", "+addr3+", "+addr4+", "+add_pc;
											//ResidentialAddress.setText(addr);
								
											Street.setText(obj.getString("street"));
											Subur.setText(obj.getString("suburb"));
											City.setText(obj.getString("city"));	
											Province.setText(obj.getString("province"));
											Occupation.setText(obj.getString("occupation"));
					
										} 
									catch (JSONException e)
										{
											e.printStackTrace();
										}
								break;
						
								case 4:
									if (dialog != null) 
										{
											dialog.dismiss();
										}
									AlertDialog.Builder aB = new AlertDialog.Builder(Edit_Update_Member_Profile.this);
									aB.setMessage(message).setCancelable(true)
									.setPositiveButton("OK", new DialogInterface.OnClickListener() 
										{
											public void onClick(DialogInterface dialog, int id) 
												{
													finish();
												}
										});
									AlertDialog alertD = aB.create();
									alertD.show();
								break;
						
								case 5:
									if (dialog != null) 
										{
											dialog.dismiss();
										}
									AlertDialog.Builder aB2 = new AlertDialog.Builder(Edit_Update_Member_Profile.this);
									aB2.setMessage(message).setCancelable(true)
									.setPositiveButton("OK", new DialogInterface.OnClickListener() 
										{
											public void onClick(DialogInterface dialog, int id) 
												{
													finish();
												}
										});
									AlertDialog alertD2 = aB2.create();
									alertD2.show();
								break;
								
								case 6:
									if (dialog != null) 
										{
											dialog.dismiss();
										}
									AlertDialog.Builder aB1 = new AlertDialog.Builder(Edit_Update_Member_Profile.this);
									aB1.setMessage(message).setCancelable(true)
									.setPositiveButton("OK", new DialogInterface.OnClickListener() 
										{
											public void onClick(DialogInterface dialog, int id) 
												{
													//finish();
												}
										});
									AlertDialog alertD1 = aB1.create();
									alertD1.show();
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
	
		@Override
		protected Dialog onCreateDialog(int id) 
			{
				switch (id) 
					{
						case 1:
							return new DatePickerDialog(this, pickerListener, year, month, day);
					}
				return null;
			}
	
		private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener()
			{
				@Override
				public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) 
					{
						year = selectedYear;
						month = selectedMonth;
						day = selectedDay;
						DOB.setText(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).append(" "));
					}
	
			};
		
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


		@Override
		public void onItemSelected(AdapterView<?> parent, View view,int position, long id) 
			{
				int Id = parent.getId();
				switch (Id) 
					{
						case R.id.lang_name:
							lang_selected_task_id = Integer.toString(position+1);
						break;
						
						default:
						
						break;
					}
			
			}


		@Override
		public void onNothingSelected(AdapterView<?> parent)
			{
				
			}

}
