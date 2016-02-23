package example.sofarmanager;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class View_Members_Screen extends Activity implements OnClickListener
	{
		CallApi webservice;
		View_Members_Screen inst;
		public ProgressDialog dialog;
		String attendance_result,error_response;
		ListView member_list;
		ArrayAdapter<String> list_adapter;
		ArrayList<String>names,member_no;
		
		ImageView tab1,tab2,tab3,tab4,backbtn;
	
		@Override
		protected void onCreate(Bundle savedInstanceState) 
			{
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				setContentView(R.layout.activity_view_members_screen);
				//this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				
				
				inst = this;
				webservice = new CallApi();
				names = new ArrayList<String>();
				member_no = new ArrayList<String>();
				
				get_all_members();
				
				tab1=(ImageView)findViewById(R.id.tab1);
				tab2=(ImageView)findViewById(R.id.tab2);
				tab3=(ImageView)findViewById(R.id.tab3);
				tab4=(ImageView)findViewById(R.id.tab4);
				
				tab1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(View_Members_Screen.this,Main_Screen.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(View_Members_Screen.this,Member_Screen.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab3.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(View_Members_Screen.this,Attendence.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab4.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(View v) {
			
						Intent mainintent=new Intent(View_Members_Screen.this,Task_Screen.class);
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
	
		@Override
		public void onClick(View v) 
			{
				
			}
		
		public void get_all_members()
			{
				dialog = ProgressDialog.show(inst, "Loading Members...", "Please wait...",true, false);
				dialog.setCanceledOnTouchOutside(false);
				
				Thread thread = new Thread(new Runnable() 
					{
						@Override
						public void run() 
							{
								try {
										if (check_network()) 
											{   
												SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
												String sgid = global_storage.getString("SGID", "");
										
												attendance_result = webservice.get_sgMembersAttendance(sgid, "get_small_group_members_and_attendance");
										
												if(attendance_result.contains("java.net.UnknownHostException")||attendance_result.contains("java.net.ConnectException") || attendance_result.contains("java.net.SocketException"))
													{
														error_response = attendance_result;
														inst.Data_handler.sendEmptyMessage(1);
													}
												else
													{
														if (attendance_result == null) 	
															{
																inst.Data_handler.sendEmptyMessage(2);
															} 
														else if (attendance_result != null) 
															{
																inst.Data_handler.sendEmptyMessage(3);
															}
													}
											}
								} 
								catch (Exception e) 
									{
										e.printStackTrace();
										inst.Data_handler.sendEmptyMessage(1);
									}
							}
					});
				thread.start();
			}
		
		
		
		private Handler Data_handler = new Handler() 
			{
				@Override
				public void handleMessage(Message msg) 
					{
						switch (msg.what) 
							{
								case 1:
									if (dialog != null) 
										{
											dialog.dismiss();
										}
									
									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(inst);
									alertDialogBuilder.setMessage(error_response).setCancelable(true)
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
								
								case 2:
									if (dialog != null) 
										{
											dialog.dismiss();
										}
								
									AlertDialog.Builder aBuilder = new AlertDialog.Builder(inst);
									aBuilder.setMessage("No Response From Server").setCancelable(true)
										.setPositiveButton("OK", new DialogInterface.OnClickListener()
											{
												public void onClick(DialogInterface dialog, int id) 
													{
														finish();
													}
											});
									AlertDialog alertDg = aBuilder.create();
									alertDg.show();
								break;
								
								case 3:
									try {
											JSONObject jobj = new JSONObject(attendance_result);
											String success = jobj.getString("success");
											
											if (success.equalsIgnoreCase("true")) 
												{
													JSONArray attendance_result_array = null;
													attendance_result_array = jobj.getJSONArray("items");
									
													//adding the name to the list
													for (int i = 0; i < attendance_result_array.length(); i++)
														{
															String f_name = attendance_result_array.getJSONObject(i).getString("FirstName");
															String s_name = attendance_result_array.getJSONObject(i).getString("Surname");
															String name = f_name + " " + s_name;
															String  memno = attendance_result_array.getJSONObject(i).getString("Mem_No");
															names.add(name);
															member_no.add(memno);
														}
													member_list = (ListView)findViewById(R.id.list_of_members);		
													member_list.setAdapter(new ArrayAdapter<String>(inst, R.layout.mem_list, R.id.mem_name, names));
													member_list.setOnItemClickListener(new OnItemClickListener() 
														{
															@Override
															public void onItemClick(AdapterView<?> arg0, View arg1, final int pos,long arg3) 
																{
																	//add_member(arg2);
																	goto_edit_member(member_no.get(pos));
																}
														});
												}
											else
												{
													String result = jobj.getString("message");
													AlertDialog.Builder alebl = new AlertDialog.Builder(inst);
													alebl.setMessage(result).setCancelable(true)
													.setPositiveButton("OK", new DialogInterface.OnClickListener() 
														{
															public void onClick(DialogInterface dialog, int id) 
																{
																	finish();
																}
														});

													AlertDialog alertDia = alebl.create();
													alertDia.show();
												}
											} 
										catch (Exception e) 
											{
												e.printStackTrace();
											}	
									if (dialog != null) 
										{
											dialog.dismiss();
										}
								break;
								
								case 4:
									
								break;
								
							}
						super.handleMessage(msg);
					}
			};
		
		
		public void goto_edit_member(String mid)
			{
				Intent edit_mem = new Intent(View_Members_Screen.this,Edit_Update_Member_Profile.class);
				edit_mem.putExtra("memid", mid);
				startActivity(edit_mem);
				
				System.out.println("id = "+mid);
				finish();
			}
		
		
		// ========================Check The internet is avialable or not=====================
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
}
