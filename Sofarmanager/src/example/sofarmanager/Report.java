package example.sofarmanager;

//========================================//
//      Author --> Biplab De              //
//       Date:-> 26/02/2014               //
//========================================//

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;

public class Report extends Activity {

	CallApi webservice;
	Report inst;
	public ProgressDialog dialog;
	ListView attendance_listView;
	ArrayAdapter<String> list_adapter;
	String selected_grp_id = "3", attendance_result = new String();
	ArrayList<String> names;
	ArrayList<String> member_no;
	ArrayList<String> absent_list;
	String[] outputStrArr;
	String[] selected_member_no;
	String response_result = new String();
	
	ImageView tab1,tab2,tab3,tab4,backbtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.report_layout);
		// this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		
		inst = this;
		webservice = new CallApi();
		names = new ArrayList<String>();
		member_no = new ArrayList<String>();

		// ============================= Intialize all the fields with objects==================
		asyn_get_all_data();


		tab1=(ImageView)findViewById(R.id.tab1);
		tab2=(ImageView)findViewById(R.id.tab2);
		tab3=(ImageView)findViewById(R.id.tab3);
		tab4=(ImageView)findViewById(R.id.tab4);
		
		tab1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Report.this,Main_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Report.this,Member_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Report.this,Attendence.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab4.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
	
				Intent mainintent=new Intent(Report.this,Task_Screen.class);
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

	// ========================== Click Listener For All==========================
	@SuppressWarnings({ "deprecation", "deprecation" })
	View.OnClickListener all_click_listener = new View.OnClickListener() {
		public void onClick(View v) {
			int Id = v.getId();

			switch (Id) {
					case R.id.cancel_btn:
				finish();
				break;

			default:
				break;
			}
		}
	};// END

	// =================================== Get all The data from Web Service Code===============================================================

	public void asyn_get_all_data() {
		dialog = ProgressDialog.show(inst, "Loading Reports...", "Please wait...",
				true, false);
		dialog.setCanceledOnTouchOutside(false);

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (check_network()) {
						String method_to_get_data = "get_small_group_members_and_attendance";	
						SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
						String member_no = global_storage.getString("MEMBER_NO", "");
					
						String sgid=webservice.get_sg_id(member_no);
						JSONObject obj=new JSONObject(sgid);
						sgid=obj.getString("message");
						
						attendance_result = webservice.get_sgMembersAttendance(sgid, method_to_get_data);
						
						if(attendance_result.contains("java.net.UnknownHostException")||attendance_result.contains("java.net.ConnectException") || attendance_result.contains("java.net.SocketException"))
							{
								inst.Data_handler.sendEmptyMessage(2);
							}
						else
							{
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

	// =================================== Handler For Async=================================

	private Handler Data_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (dialog != null) {
					dialog.dismiss();
				}
				String message = "There is no Data";
				Toast.makeText(inst, message, Toast.LENGTH_LONG).show();
				break;

			case 1:
				try {
					Log.i("web return::", attendance_result + "// ");
					JSONObject jobj = new JSONObject(attendance_result);
					String success = jobj.getString("success");
					if (success.equalsIgnoreCase("true")) {
						absent_list = new ArrayList<String>();
						JSONArray attendance_result_array = null;
						attendance_result_array = jobj.getJSONArray("items");

						// adding the name to the list
						for (int i = 0; i < attendance_result_array.length(); i++) {
							String f_name = attendance_result_array.getJSONObject(i).getString("FirstName");
							String surname = attendance_result_array.getJSONObject(i).getString("Surname");
							String name = f_name +" " + surname;
							String memno = attendance_result_array.getJSONObject(i).getString("Mem_No");
							String absent = attendance_result_array.getJSONObject(i).getString("absent");
							names.add(name);
							member_no.add(memno);
							absent_list.add(absent);
						}

						

						report_custom_list list_adapter = new report_custom_list(Report.this, names, absent_list);
						attendance_listView = (ListView) findViewById(R.id.list_for_atten);
						attendance_listView.setAdapter(list_adapter);
						attendance_listView
								.setOnItemClickListener(new AdapterView.OnItemClickListener() {
									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										// Toast.makeText(inst,
										// "You Clicked at ",
										// Toast.LENGTH_SHORT).show();

									}
								});
					}else{
						showAlertDialog(inst,jobj.getString("error"));
						//Toast.makeText(inst, jobj.getString("error"), Toast.LENGTH_SHORT).show();
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case 2:
				showAlertDialog(inst,"Network Error! Please Try Again");
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
