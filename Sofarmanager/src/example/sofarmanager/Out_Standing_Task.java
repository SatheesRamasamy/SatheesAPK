package example.sofarmanager;

//========================================//
//      Author --> Biplab De              //
//       Date:-> 26/02/2014               //
//========================================//

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;

public class Out_Standing_Task extends Activity {

	ImageView tab1,tab2,tab3,tab4,backbtn;
	
	CallApi webservice;
	Out_Standing_Task inst;
	public ProgressDialog dialog;
	String outstanding_tasks_data;
	ArrayList<String> ost_subject, ost_description, ost_due_date, ost_priority,ost_status;
	ListView list;
	DisplayMetrics metrics;
	int width;
	ExpandableListView expListView;
	Out_Standing_CustomExpandbleList listAdapter;
	Out_satnd_parent parentdata;
	ArrayList<Out_satnd_parent> dataforlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_out_standing_task);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// get the listview
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		width = metrics.widthPixels;

		inst = this;
		webservice = new CallApi();

		dataforlist = new ArrayList<Out_satnd_parent>();
		ost_subject = new ArrayList<String>();
		ost_description = new ArrayList<String>();
		ost_due_date = new ArrayList<String>();
		ost_priority = new ArrayList<String>();
		ost_status = new ArrayList<String>();



		asyn_get_all_data();
		
		
		tab1=(ImageView)findViewById(R.id.tab1);
		tab2=(ImageView)findViewById(R.id.tab2);
		tab3=(ImageView)findViewById(R.id.tab3);
		tab4=(ImageView)findViewById(R.id.tab4);
		
		tab1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Out_Standing_Task.this,Main_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Out_Standing_Task.this,Member_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Out_Standing_Task.this,Attendence.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab4.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
	
				Intent mainintent=new Intent(Out_Standing_Task.this,Task_Screen.class);
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

	// ========================== Click Listener For
	// All==========================

	View.OnClickListener all_click_listener = new View.OnClickListener() {
		public void onClick(View v) {
			int Id = v.getId();
			switch (Id) {
		

			default:
				break;
			}
		}
	};// END

	// =================================== Get all The data from Web Service
	// Code===============================================================

	public void asyn_get_all_data() {
		dialog = ProgressDialog.show(inst, "Loading OutStanding Task...", "Please wait...",
				true, false);
		dialog.setCanceledOnTouchOutside(false);

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (check_network()) {
						// ============= get task types====================
						outstanding_tasks_data = new String();
						String method_to_get_data = "view_outstanding_tasks";
						SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
						String pass_parameter = global_storage.getString("MEMBER_NO", "");
					
						outstanding_tasks_data = webservice
								.get_out_standing_data_MethodCall(
										pass_parameter, method_to_get_data);
						
						if(outstanding_tasks_data.contains("java.net.UnknownHostException")||outstanding_tasks_data.contains("java.net.ConnectException") || outstanding_tasks_data.contains("java.net.SocketException"))
							{
								inst.Data_handler.sendEmptyMessage(2);
							}
						else{
								if (outstanding_tasks_data == null) {
									inst.Data_handler.sendEmptyMessage(0);
								} else if (outstanding_tasks_data != null) {
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

	// ======================== End Get all The data from Web Service
	// Code===================================

	// =================================== Handler For Async get data
	// method=================================

	private Handler Data_handler = new Handler() {
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (dialog != null) {
					dialog.dismiss();
				}

				JSONObject jobj1;
				String message = null;
				try {
					jobj1 = new JSONObject(outstanding_tasks_data);
					String succ = jobj1.getString("success");

					message = jobj1.getString("message");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				showAlertDialog(inst, message);
				break;

			case 1:
				try {
					// Log.i( "web return  outstanding task:=",
					// outstanding_tasks_data + "// ");
					JSONObject jobj = new JSONObject(outstanding_tasks_data);
					String success = jobj.getString("success");
					if (success.equalsIgnoreCase("true")) {
						JSONArray outstanding_message = jobj
								.getJSONArray("message");

						for (int i = 0; i < outstanding_message.length(); i++) {
							String subject = outstanding_message.getJSONObject(
									i).getString("subject");
							String description = outstanding_message
									.getJSONObject(i).getString("description");
							String due_date = outstanding_message
									.getJSONObject(i).getString("due_date");
							String priority = outstanding_message
									.getJSONObject(i).getString("priority");
							String status = outstanding_message
									.getJSONObject(i).getString("status");

							ost_subject.add(subject);
							ost_description.add(description);
							ost_due_date.add(due_date);
							ost_priority.add(priority);
							ost_status.add(status);
						}

						expListView = (ExpandableListView) findViewById(R.id.ost_expl);
						if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
							expListView.setIndicatorBounds(width
									- GetDipsFromPixel(50), width
									- GetDipsFromPixel(10));
						} else {
							expListView.setIndicatorBoundsRelative(width- GetDipsFromPixel(50), width
									- GetDipsFromPixel(10));
						}

						for (int i = 0; i < outstanding_message.length(); i++) {
							parentdata = new Out_satnd_parent();
							ArrayList<Out_stand_child> childdata = new ArrayList<Out_stand_child>();
							Out_stand_child child = new Out_stand_child();

							parentdata.setSubject(ost_subject.get(i));

							child.setDescription(ost_description.get(i));
							child.setDuedate(ost_due_date.get(i));
							child.setPriority(ost_priority.get(i));
							child.setStatus(ost_status.get(i));

							childdata.add(child);
							parentdata.setChilddata(childdata);

							dataforlist.add(parentdata);
						}

						listAdapter = new Out_Standing_CustomExpandbleList(Out_Standing_Task.this, dataforlist);
						// setting list adapter
						expListView.setAdapter(listAdapter);
					} else {
						showAlertDialog(inst, jobj.getString("message"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 2:
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

	public int GetDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	// ========================Check The internet is avialable or
	// not=====================
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
		alertDialogBuilder.setMessage(message).setCancelable(true)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) 
					{
						finish();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}
}
