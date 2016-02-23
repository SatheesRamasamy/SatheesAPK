package example.sofarmanager;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import example.sofarmanager.reuse.CallApi;

//import example.sofarmanager.reuse.Util;

public class Main_Screen extends Activity implements OnClickListener {
	
	ImageView tab1,tab2,tab3,tab4;
	ImageView member, attendence, task, scan;
	TextView logout;
	CallApi call;
	Dialog dg;
	String message, success;
	String exception;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mainmenu_screen);
		System.out.println("OnCreate");

		member = (ImageView) findViewById(R.id.main_member);
		attendence = (ImageView) findViewById(R.id.main_attendence);
		task = (ImageView) findViewById(R.id.main_task);
		scan = (ImageView) findViewById(R.id.barcode_scanner);
		logout = (TextView) findViewById(R.id.main_back);

		member.setOnClickListener(this);

		attendence.setOnClickListener(this);
		task.setOnClickListener(this);
		logout.setOnClickListener(this);
		scan.setOnClickListener(this);
		setResponseValues();
		
		tab1=(ImageView)findViewById(R.id.tab1);
		tab2=(ImageView)findViewById(R.id.tab2);
		tab3=(ImageView)findViewById(R.id.tab3);
		tab4=(ImageView)findViewById(R.id.tab4);
		
		tab1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Main_Screen.this,Main_Screen.class);
				startActivity(mainintent);
				
				
			}
		});
		
		tab2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Main_Screen.this,Member_Screen.class);
				startActivity(mainintent);
				
				
			}
		});
		
		tab3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Main_Screen.this,Attendence.class);
				startActivity(mainintent);
				
				
			}
		});
		
		tab4.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
	
				Intent mainintent=new Intent(Main_Screen.this,Task_Screen.class);
				startActivity(mainintent);
				
		
			}
		});

		

	}

	@Override
	protected void onPause() {
		System.out.println("OnPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		System.out.println("onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		System.out.println("onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		System.out.println("onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		System.out.println("onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onClick(View v) {
		if (v.equals(member)) {
			Intent member = new Intent(Main_Screen.this, Member_Screen.class);
			startActivity(member);
			
			
		} else if (v.equals(attendence)) {
			if (success != null) {

				if (success.equalsIgnoreCase("true")) {
					Intent member = new Intent(Main_Screen.this,
							Attendence.class);
					startActivity(member);
				
				
				} else {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							Main_Screen.this);
					alertDialogBuilder
							.setMessage(
									"Member don't have access to attendance")
							.setCancelable(false)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// finish();
										}
									});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
			} else {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						Main_Screen.this);
				alertDialogBuilder
						.setMessage("Member don't have access to attendance")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// finish();
									}
								});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		} else if (v.equals(task)) {
			
			Intent member = new Intent(Main_Screen.this, Task_Screen.class);
			startActivity(member);
			
		
		}

		else if (v.equals(scan)) {
			
			Intent member = new Intent(Main_Screen.this,
					ScanBarQrCodeActivity.class);
			startActivity(member);
			
			
		}

		else if (v.equals(logout)) {
			
			showAlertDialog(this, "Do you want to logout?");
			
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			dg.dismiss();
			int val = msg.what;
			
			switch (val) {
			
			case 1:
				AlertDialog.Builder ErroralertDialogBuilder = new AlertDialog.Builder(
						Main_Screen.this);
				ErroralertDialogBuilder
						.setMessage(exception)
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										finish();
									}
								});
				AlertDialog alertDialog = ErroralertDialogBuilder.create();
				alertDialog.show();
				break;

			case 2:
				AlertDialog.Builder ResponsealertDialogBuilder = new AlertDialog.Builder(
						Main_Screen.this);
				ResponsealertDialogBuilder
						.setMessage("Response Error!.Please Try Again")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										finish();
									}
								});
				AlertDialog alertDialog1 = ResponsealertDialogBuilder.create();
				alertDialog1.show();
				break;

			case 3:
				AlertDialog.Builder ResponsealertDialogBuilder1 = new AlertDialog.Builder(
						Main_Screen.this);
				ResponsealertDialogBuilder1
						.setMessage(message)
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// finish();
										// Util.sg_id="49";
									}
								});
				AlertDialog alertDialog2 = ResponsealertDialogBuilder1.create();
				alertDialog2.show();
				break;

			case 4:

				dg.dismiss();

				break;
			}
		};
	};

	@Override
	public void onBackPressed() {
		showAlertDialog(this, "Do you want to logout?");
	}

	public void showAlertDialog(Context con, String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);

		alertDialogBuilder.setMessage(message).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						SharedPreferences global_storage = getSharedPreferences(
								"global_data", MODE_PRIVATE);
						Editor edit = global_storage.edit();
						edit.putString("MEMBER_NO", "");
						edit.putString("HIGHEST_ROLL", "");
						edit.putString("SGID", "");
						edit.putString("MAILID", "");
						edit.putString("LOGIN", "false");
						edit.commit();
						//finish();
						Intent homeintent=new Intent(Main_Screen.this,Login_Screen.class);
						startActivity(homeintent);
						finish();
						
					}
				});

		alertDialogBuilder
				.setMessage(message)
				.setCancelable(false)
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// finish();
							}
						});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void setResponseValues() {

		dg = ProgressDialog.show(Main_Screen.this, "Content is loading...",
				"Please wait");

		Thread send = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SharedPreferences global_storage = getSharedPreferences(
						"global_data", Context.MODE_PRIVATE);
				String mem_no = global_storage.getString("MEMBER_NO", "");

				call = new CallApi();
				// String res = call.get_SG_IDs(Util.member_no, "");
				System.out.println("mem no = " + mem_no);
				String res = call.get_sg_id(mem_no);

				System.out.println("res = " + res);

				String res1 = call.getMailID(mem_no, "");

				System.out.println("response = " + res1);

				if (res.contains("java.net.UnknownHostException")
						|| res.contains("java.net.ConnectException")
						|| res.contains("java.net.SocketException")) {
					exception = new String();
					exception = res;
					handler.sendEmptyMessage(1);
				} else if (res1.contains("java.net.UnknownHostException")
						|| res1.contains("java.net.ConnectException")
						|| res1.contains("java.net.SocketException")) {
					exception = new String();
					exception = res1;
					handler.sendEmptyMessage(1);
				} else {
					getRes(res);
					gerResOne(res1);
				}
				// System.out.println(">>>>>>>>>>>>." + res);

				handler.sendEmptyMessage(4);

			}
		});
		send.start();

	}

	private void getRes(String res) {

		try {
			
			JSONObject obj = new JSONObject(res);
			success = obj.getString("success");
			
			if (success.equalsIgnoreCase("true")) {
				
				message = obj.getString("message");
				String sg_id = message;

				SharedPreferences global_storage = getSharedPreferences(
						"global_data", MODE_PRIVATE);
				Editor edit = global_storage.edit();
				edit.putString("SGID", sg_id);
				edit.commit();
				
			} else {
				
				message = obj.getString("message");
				handler.sendEmptyMessage(3);
				
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(2);
		}

	}

	private void gerResOne(String res1) {

		try {

			JSONObject obj1 = new JSONObject(res1);
			success = obj1.getString("success");
			String detials = obj1.getString("message");
			if (success.equalsIgnoreCase("true")) {
				JSONObject details_obj = new JSONObject(detials);
				String mailId = details_obj.getString("E_Mail");
				System.out.println("mailid = " + mailId);

				SharedPreferences global_storage = getSharedPreferences(
						"global_data", MODE_PRIVATE);
				Editor edit = global_storage.edit();
				edit.putString("MAILID", mailId);
				edit.commit();
			} else {
				handler.sendEmptyMessage(3);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(2);
		}

	}

}
