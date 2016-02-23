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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;

public class Add_sg_Visitor extends Activity {

	
	ImageView tab1,tab2,tab3,tab4,backbtn;
	
	EditText first_name, sur_name, dob, street_name, suburb, email, phone,town;
	Spinner maritalstatus;
	RadioButton male, female;
	RadioGroup gendergroup;
	TextView submit, cancel;
	int day, month, year;
	RelativeLayout datelayout;
	String mfirstname, msurname, mdob, mstreetname,
		   msuburb, memail, mphoneno, mtown, response, message;
	ProgressDialog dialog;
	int mgender=0;
	int m_maritalstatus = 0;
	CallApi call;
	String sg_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_sg_visitor);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		
		first_name = (EditText) findViewById(R.id.firstname);
		sur_name = (EditText) findViewById(R.id.surname);
		dob = (EditText) findViewById(R.id.date);
		street_name = (EditText) findViewById(R.id.streetname);
		suburb = (EditText) findViewById(R.id.suburb);
		email = (EditText) findViewById(R.id.emailaddr);
		phone = (EditText) findViewById(R.id.cellno);
		town = (EditText) findViewById(R.id.town);
		submit = (TextView) findViewById(R.id.submit);
		cancel = (TextView) findViewById(R.id.cancel);
		maritalstatus = (Spinner) findViewById(R.id.maritalstatus);
		datelayout = (RelativeLayout) findViewById(R.id.datelayout);
		gendergroup = (RadioGroup) findViewById(R.id.gendergroup);
		male=(RadioButton)findViewById(R.id.maleradio);
		female=(RadioButton)findViewById(R.id.femaleradio);
		sg_id = new String();
		
		datelayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(1);
			}
		});

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.maritalstatus_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		maritalstatus.setAdapter(adapter);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		call = new CallApi();
		
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
				{
					finish();
				}
		
		});	
		
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mfirstname = first_name.getText().toString();
				msurname = sur_name.getText().toString();
				mdob = dob.getText().toString();
				
				m_maritalstatus = maritalstatus.getSelectedItemPosition();
				
				
				if (male.isChecked()) 
					{
						mgender = 2;
					}	 
				else if (female.isChecked()) 
					{
						mgender = 3;
					}
				else
					{
						mgender = 1;
					}
				
				memail = email.getText().toString();
				mphoneno = phone.getText().toString();
				mstreetname = street_name.getText().toString();
				msuburb = suburb.getText().toString();
				mtown = town.getText().toString();
				
				int count = 0;
				
				if (mfirstname.length() == 0) 
					{
						first_name.setError("This Field Can't Be Left Blank");
						first_name.setFocusable(true);
					}
				else{
						count = count + 1;
					}
				if (msurname.length() == 0) 
					{
						sur_name.setError("This Field Can't Be Left Blank");
						sur_name.setFocusable(true);
					}
				else{
						count = count + 1;
					} 
				
				if (mgender == 0)
					{
						gendergroup.setFocusable(true);
					}
				else
					{
						count = count + 1;
					}
				
				if (mdob.length() == 0) 
					{
						//dob.setError("This Field Can't Be Left Blank");
						dob.setFocusable(true);
					}
				else
					{
						count = count + 1;
					}
				
				if (m_maritalstatus == 0) 
					{
						maritalstatus.setFocusable(true);
					}
				else
					{
						count = count + 1;
					}
				
				if (mstreetname.length() == 0) 
					{
						street_name.setError("This Field Can't Be Left Blank");
						street_name.setFocusable(true);
					}
				else{
						count = count + 1;
					}
				
				if (msuburb.length() == 0) 
					{
						suburb.setError("This Field Can't Be Left Blank");
						suburb.setFocusable(true);
					}
				else
					{
						count = count + 1;
					}
				
				if (mtown.length() == 0) 
					{
						town.setError("This Field Can't Be Left Blank");
						town.setFocusable(true);
					}
				else{
						count = count + 1;
					}
				
				if (memail.matches("")) 
					{
						email.setError("This Field Can't Be Left Blank");
						email.setFocusable(true);
					}
				else{
						String chkMail = memail.trim();
						if(isValidEmail(chkMail))
							{
								count = count + 1;
							}
						else{
								email.setError("Please Provide Valid Mail Id");
							}
					}

				if (mphoneno.length() == 0) 
					{
						phone.setFocusable(true);
						phone.setError("This Field Can't Be Left Blank");
					}
				else{
						count = count + 1;
					}
				
				
				if(count == 10)
					{
						dialog = ProgressDialog.show(Add_sg_Visitor.this, "Adding visitor...", "Please wait...",true, false);
						dialog.setCanceledOnTouchOutside(false);
						new Thread(new Runnable()
							{
								@Override
								public void run()
									{
										SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
										String memno = global_storage.getString("MEMBER_NO", "");
									
										response = call.addSmallgroup_Visitor(
												memno, mfirstname, msurname,
												mgender, mdob, m_maritalstatus,
												mstreetname, msuburb, mtown, memail,
												mphoneno, sg_id, "add_sg_visitor");
										try {
												JSONObject obj = new JSONObject(response);
												if(obj!=null)
													{
														String succcess = obj.getString("success");
														message = obj.getString("message");
														System.out.println(">>>>>>>>>>."+succcess);
														
														if(succcess.equalsIgnoreCase("true"))
															{
																handlerobject.sendEmptyMessage(1);	
															}
														if(succcess.equalsIgnoreCase("false"))
															{
																handlerobject.sendEmptyMessage(2);
															}
													}
												else
													{
														handlerobject.sendEmptyMessage(3);
													}
											} 
										catch (JSONException e) 
											{
												e.printStackTrace();
											}
									}
							}).start();
					}
				else{
						showAlertDialog(Add_sg_Visitor.this, "Please Check All The Fields");
					}

			}
		});

		
		set_sg_id();
		
		
		tab1=(ImageView)findViewById(R.id.tab1);
		tab2=(ImageView)findViewById(R.id.tab2);
		tab3=(ImageView)findViewById(R.id.tab3);
		tab4=(ImageView)findViewById(R.id.tab4);
		backbtn=(ImageView)findViewById(R.id.back_btn);
		
		tab1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Add_sg_Visitor.this,Main_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Add_sg_Visitor.this,Member_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Add_sg_Visitor.this,Attendence.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab4.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
	
				Intent mainintent=new Intent(Add_sg_Visitor.this,Task_Screen.class);
				startActivity(mainintent);
				finish();
		
			}
		});
		
		
		backbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				onBackPressed();
				
			}
		});
		
	}
	
	
	public void set_sg_id()
		{
			dialog = ProgressDialog.show(Add_sg_Visitor.this, "Fetching SG_ID...", "Please wait...",true, false);
			dialog.setCanceledOnTouchOutside(false);
			new Thread(new Runnable()
				{
					@Override
					public void run()
						{
							SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
							String memno = global_storage.getString("MEMBER_NO", "");
							
							response = call.get_sg_id(memno);
							if(response.contains("java.net.UnknownHostException")||response.contains("java.net.ConnectException") || response.contains("java.net.SocketException"))
								{
									handlerobject.sendEmptyMessage(6);
								}
							else{
									if(response==null)
									{
										handlerobject.sendEmptyMessage(3);
									}
									else
									{
										try {
												JSONObject obj = new JSONObject(response);
												if(obj!=null)
													{
														String succcess = obj.getString("success");
														message = obj.getString("message");
														System.out.println(">>>>>>>>>>."+succcess);
														
														if(succcess.equalsIgnoreCase("true"))
															{
																handlerobject.sendEmptyMessage(4);	
															}
														if(succcess.equalsIgnoreCase("false"))
															{
																handlerobject.sendEmptyMessage(5);
															}
													}
												else
													{
														handlerobject.sendEmptyMessage(3);
													}
											} 
										catch (JSONException e) 
											{
												e.printStackTrace();
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
						showAlertDialog(Add_sg_Visitor.this, message);
						clearForm();
					break;
					
					case 2:
						if (dialog != null) 
							{
								dialog.dismiss();
							}
						showAlertDialog(Add_sg_Visitor.this, message);
					break;
					
					case 3:
						if (dialog != null) 
							{
								dialog.dismiss();
							}
						showAlertDialog(Add_sg_Visitor.this,"No Response");
					break;
					
					case 4:
						if (dialog != null) 
							{
								dialog.dismiss();
							}
						sg_id = message;
						break;
					
					case 5:
						if (dialog != null) 
							{
								dialog.dismiss();
							}
						showAlertDialog(Add_sg_Visitor.this,"No Response");
					break;
					
					case 6:
						if (dialog != null) 
							{
								dialog.dismiss();
							}
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add_sg_Visitor.this);
						alertDialogBuilder.setMessage("Network Error! Please Try Again").setCancelable(true)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() 
								{
									public void onClick(DialogInterface dialog, int id) 
										{
											finish();
										}
								});
						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
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
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 1:

			// open datepicker dialog.
			// set date picker for current date
			// add pickerListener listner to date picker
			return new DatePickerDialog(this, pickerListener, year, month, day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// Show selected date
			dob.setText(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).append(" "));
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
			first_name.setText("");
			sur_name.setText("");
			dob.setText("");
			street_name.setText("");
			suburb.setText("");
			email.setText("");
			phone.setText("");
			town.setText("");
			gendergroup.clearCheck();
			mgender = 0;
			maritalstatus.setSelection(0);
			m_maritalstatus = 0;
		}

}
