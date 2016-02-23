package example.sofarmanager;

//========================================//
//      Author --> Biplab De              //
//       Date:-> 26/02/2014               //
//========================================//

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;

public class AttendanceCapture extends Activity {

	CallApi webservice;
	int mo_Day,mo_Year,mo_Month,m_Day,m_Year,m_Month;
	AttendanceCapture inst;
	public ProgressDialog dialog;
	TextView cancle_bt, submit_bt;
	EditText date_set;
	Calendar myCalendar = Calendar.getInstance();
	// Spinner group_id;
	ListView attendance_listView;
	ArrayAdapter<String> list_adapter;
	// ArrayList<String>g_id;
	String selected_grp_id = "3", attendance_result = new String();
	ArrayList<String> names;
	ArrayList<String> member_no;
	String[] outputStrArr;
	String[] selected_member_no;
	String response_result = new String();
	//String year, month, day;
	
	ImageView backbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.attendance_capture_layout);
		// this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		inst = this;
		webservice = new CallApi();
		names = new ArrayList<String>();
		member_no = new ArrayList<String>();
		// ============================= Intialize all the fields with
		// objects==================
		submit_bt = (TextView) findViewById(R.id.submit_btn);
		cancle_bt = (TextView) findViewById(R.id.cancel_btn);
		date_set = (EditText) findViewById(R.id.datetext);

		asyn_get_all_data();

		cancle_bt.setOnClickListener(all_click_listener);
		submit_bt.setOnClickListener(all_click_listener);
		date_set.setOnClickListener(all_click_listener);
		
		backbtn=(ImageView)findViewById(R.id.back_btn);
		backbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				onBackPressed();
				
			}
		});
		
	}

	// ========================== Click Listener For
	// All==========================
	@SuppressWarnings({ "deprecation", "deprecation" })
	View.OnClickListener all_click_listener = new View.OnClickListener() {
		public void onClick(View v) {
			int Id = v.getId();

			switch (Id) {
			/*case R.id.back_or_finish:
				finish();
			break;
				
			case R.id.ac_back_lay:
				finish();
			break;*/
			
			case R.id.submit_btn:
				SparseBooleanArray checked = attendance_listView
						.getCheckedItemPositions();
				ArrayList<String> selectedItems = new ArrayList<String>();
				ArrayList<String> selected_id = new ArrayList<String>();
				for (int i = 0; i < checked.size(); i++) {
					// Item position in adapter
					int position = checked.keyAt(i);
					// Add sport if it is checked i.e.) == TRUE!
					if (checked.valueAt(i)) {
						selectedItems.add(list_adapter.getItem(position));
						selected_id.add(member_no.get(position));
					}
				}

				outputStrArr = new String[selectedItems.size()];
				selected_member_no = new String[selectedItems.size()];
				for (int i = 0; i < selectedItems.size(); i++) 
				{
					outputStrArr[i] = selectedItems.get(i);
					System.out.println("it" + selected_id.get(i));
					selected_member_no[i] = selected_id.get(i);
				}
				if (!date_set.getText().toString().equals("")) 
					{
						if(selectedItems.size() > 0)
							{
								update_attendance_record();
							}
						else
							{
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(inst);
								alertDialogBuilder.setMessage("Please Select Member")
												  .setCancelable(true)
												  .setPositiveButton("OK",new DialogInterface.OnClickListener() 
												  	{
													  	public void onClick(DialogInterface dialog, int id) 
													  		{
													  			// finish();
													  		}
												  	});
								AlertDialog alertDialog = alertDialogBuilder.create();
								alertDialog.show();
							}
					}	 
				else
					{
						if(selectedItems.size() == 0)
							{
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(inst);
								alertDialogBuilder.setMessage("Please Enter Date and Select Member")
												  .setCancelable(true)
												  .setPositiveButton("OK",new DialogInterface.OnClickListener() 
												  	{
													  	public void onClick(DialogInterface dialog, int id) 
													  		{
													  			// finish();
													  		}
												  	});
								AlertDialog alertDialog = alertDialogBuilder.create();
								alertDialog.show();
							}
						else
							{
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(inst);
								alertDialogBuilder.setMessage("Please Enter Date")
												  .setCancelable(true)
												  .setPositiveButton("OK",new DialogInterface.OnClickListener() 
												  	{
													  	public void onClick(DialogInterface dialog, int id) 
													  		{
													  			// finish();
													  		}
												  	});
								AlertDialog alertDialog = alertDialogBuilder.create();
								alertDialog.show();
							}
					}
				break;

			case R.id.cancel_btn:
				finish();
				break;

			case R.id.datetext:
				date_set.setEnabled(false);
				
				mo_Year=myCalendar.get(Calendar.YEAR);
				mo_Month=myCalendar.get(Calendar.MONTH);
				mo_Day = myCalendar.get(Calendar.DAY_OF_MONTH);
				DatePickerDialog dialogdate = new DatePickerDialog(
						AttendanceCapture.this, date,
						myCalendar.get(Calendar.YEAR),
						myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)){
				@Override
		          public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		          {   
		              if (mo_Year < year)
		                  view.updateDate(mo_Year, mo_Month, mo_Day);

		              if (mo_Month < monthOfYear && mo_Year == year)
		                  view.updateDate(mo_Year, mo_Month, mo_Day);

		              if (mo_Day < dayOfMonth && mo_Year == year && mo_Month == monthOfYear)
		                  view.updateDate(mo_Year, mo_Month, mo_Day);

		          }
		      };
			

				dialogdate.setCanceledOnTouchOutside(false);
				dialogdate.setButton(DatePickerDialog.BUTTON_NEGATIVE,
						"Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == DatePickerDialog.BUTTON_NEGATIVE) {
									date_set.setEnabled(true);

								}
							}
						});

				dialogdate.show();
				break;

			default:
				break;
			}
		}
	};// END

	// ========================= Code for Date Picker========================================
	final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
		
			m_Year=year;
			m_Month=monthOfYear+1;
			m_Day=dayOfMonth;
			
			
			updateLabel();
			date_set.setEnabled(true);
		}
	};

	private void updateLabel() {
		
		String v_mon=String.valueOf(m_Month);
		String v_day=String.valueOf(m_Day);
		if(v_mon.length()==1)
		{
			v_mon="0"+v_mon;
			//m_Month=Integer.parseInt(v_mon);
		}
		if(v_day.length()==1)
		{
			v_day="0"+v_day;
			//m_Day=Integer.parseInt(v_day);
		}

		String myFormat = m_Year+"-"+v_mon+"-"+v_day; // In which you need put here
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date_set.setText(myFormat);
	}

	// =================================== Get all The data from Web Service
	// Code===============================================================

	public void asyn_get_all_data() {
		dialog = ProgressDialog.show(inst, "Loading Members...",
				"Please wait...", true, false);
		dialog.setCanceledOnTouchOutside(false);

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (check_network()) {
						
						SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
						String mem_no = global_storage.getString("MEMBER_NO", "");
					
						String sgid = webservice.get_sg_id(mem_no);
						JSONObject obj = new JSONObject(sgid);
						sgid = obj.getString("message");
						System.out.println("Group ID .." + sgid);

						String method_to_get_data = "get_small_group_members_and_attendance";
						// String pass_parameter = selected_grp_id;
						attendance_result = webservice.get_sgMembersAttendance(sgid, method_to_get_data);

						if (attendance_result
								.contains("java.net.UnknownHostException")
								|| attendance_result
										.contains("java.net.ConnectException")
								|| attendance_result
										.contains("java.net.SocketException")) {
							inst.Data_handler.sendEmptyMessage(3);
						} else {
							if (attendance_result == null) {
								inst.Data_handler.sendEmptyMessage(0);
							} else if (attendance_result != null) {
								inst.Data_handler.sendEmptyMessage(1);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					inst.Data_handler.sendEmptyMessage(-1);
				}
			}
		});
		thread.start();
	}

	public void update_attendance_record() {
		dialog = ProgressDialog.show(inst, "Updating Record...",
				"Please wait...", true, false);
		dialog.setCanceledOnTouchOutside(false);

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (check_network()) {
						String method_to_call = "add_small_group_attendance";
						String date = date_set.getText().toString()
								.replace("/", "");
						SimpleDateFormat datef = new SimpleDateFormat("MM-dd-yy HH:mm");
						
						SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
						String mem = global_storage.getString("MEMBER_NO", "");
					
						String mem_no = mem;
						response_result = webservice.update_attendance_MethodCall(selected_grp_id,selected_member_no, date, mem_no,
										method_to_call);

						if (response_result
								.contains("java.net.UnknownHostException")
								|| response_result
										.contains("java.net.ConnectException")
								|| response_result
										.contains("java.net.SocketException")) {
							inst.Data_handler.sendEmptyMessage(3);
						} else {
							if (response_result == null) {
								inst.Data_handler.sendEmptyMessage(0);
							} else if (response_result != null) {
								inst.Data_handler.sendEmptyMessage(2);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					inst.Data_handler.sendEmptyMessage(-1);
				}
			}
		});
		thread.start();
	}

	// =================================== Handler For
	// Async=================================

	private Handler Data_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (dialog != null) {
					dialog.dismiss();
				}
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						inst);
				alertDialogBuilder
						.setMessage("There is no data")
						.setCancelable(true)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										finish();
									}
								});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
				break;

			case 1:
				try {
					Log.i("web return::", attendance_result + "// ");
					JSONObject jobj = new JSONObject(attendance_result);
					String success = jobj.getString("success");
					if (success.equalsIgnoreCase("true")) {
						JSONArray attendance_result_array = null;
						attendance_result_array = jobj.getJSONArray("items");

						// adding the name to the list
						for (int i = 0; i < attendance_result_array.length(); i++) {
							String f_name = attendance_result_array
									.getJSONObject(i).getString("FirstName");
							String s_name = attendance_result_array
									.getJSONObject(i).getString("Surname");
							String name = f_name + " " + s_name;
							String memno = attendance_result_array
									.getJSONObject(i).getString("Mem_No");
							names.add(name);
							member_no.add(memno);
						}
						attendance_listView = (ListView) findViewById(R.id.list_for_attendance);
						list_adapter = new ArrayAdapter<String>(
								AttendanceCapture.this,
								android.R.layout.simple_list_item_multiple_choice,
								names);
						attendance_listView
								.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
						attendance_listView.setAdapter(list_adapter);
					} else {
						showAlertDialog(inst, jobj.getString("error"));
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case 2:
				try {
					JSONObject jobj = new JSONObject(response_result);
					String success = jobj.getString("success");
					if (success.equalsIgnoreCase("true")) {
						String result = jobj.getString("message");
						Toast.makeText(inst, result, Toast.LENGTH_LONG).show();
					} else {
						String result = jobj.getString("message");
						AlertDialog.Builder alebl = new AlertDialog.Builder(
								inst);

						// set dialog message
						alebl.setMessage(result)
								.setCancelable(true)
								.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												// if this button is clicked,
												// close
												// current activity

												finish();

											}
										});

						// create alert dialog
						AlertDialog alertDia = alebl.create();

						// show it
						alertDia.show();
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case 3:
				showAlertDialog(inst, "Network Error! Please Try Again");
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

	// ========================Check The internet is avialable or not=====================
	public boolean check_network() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnected()) {
			Log.e("Internet is: ", "Present");
			return true;
		} else {
			Log.e("Internet is: ", "Absent");
			return false;
		}
	}

	public void showAlertDialog(Context con, String message) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);

		// set dialog message
		alertDialogBuilder.setMessage(message).setCancelable(true)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, close
						// current activity

						finish();

					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

}
