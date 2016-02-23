package example.sofarmanager;

//========================================//
//      Author --> Biplab De              //
//       Date:-> 26/02/2014               //
//========================================//

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;

public class AddTask extends Activity implements OnItemSelectedListener {

	ImageView tab1,tab2,tab3,tab4,backbtn;
	
	CallApi webservice;
	AddTask inst;
	String task_type_data,sub_task_type_data, task_owner_data, upload_data_response, success,
			message, method_to_get_data, method_to_post_data, pass_parameter,task_type_net_exception,sub_task_type_net_exception;
	
	String msubject,mdescription,mduedate,mpriority,mtaskonner_cc,tsk_grp_msg,seleted_task_grp_id,selected_task_owner;
	public ProgressDialog dialog;
	EditText subject, description_about, due_date, taskowner_cc,attachment_file;
	TextView cancle_btn, submit_btn;
	Spinner task_type,sub_task_type, task_contact_list,priority,task_grp,task_owner_spinner;

	Calendar myCalendar = Calendar.getInstance();
	List<String> task_owner_name_list = new ArrayList<String>();
	protected CharSequence[] _options;
	protected boolean[] _selections;

	String[] file_information = {"","","",""};
	List<String> task_type_group_name,task_type_group_id;
	List<String> task_type_list,sub_task_type_list;
	List<Integer> task_type_ids,sub_task_type_ids;
	int selected_task_id,selected_sub_task_id;
	String selected_task_string;
	List<String> task_owner_list,task_owner_list1,task_owner_id,task_owner_selected_id;
	int mo_Day,mo_Year,mo_Month,m_Day,m_Year,m_Month;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_task);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		
		
		
		selected_sub_task_id = 0;
		inst = this;
		webservice = new CallApi();
		task_type_data = new String();
		selected_task_owner = new String();
		
		subject = (EditText) findViewById(R.id.subjecttext);
		description_about = (EditText) findViewById(R.id.description);
		due_date = (EditText) findViewById(R.id.duedatetext);
		priority = (Spinner) findViewById(R.id.prioritytext);
		task_grp = (Spinner) findViewById(R.id.task_group_spinner);
		taskowner_cc = (EditText) findViewById(R.id.taskownertext);
		attachment_file = (EditText) findViewById(R.id.attachment);
		attachment_file.setCompoundDrawablesWithIntrinsicBounds(null, null,getResources().getDrawable(R.drawable.attachment_button_press),null);
		task_type = (Spinner) findViewById(R.id.task_type_spinner);
		sub_task_type = (Spinner) findViewById(R.id.sub_task_type_spinner); 
		
		submit_btn = (TextView) findViewById(R.id.reg_submit);
		//cancle_btn = (TextView) findViewById(R.id.reg_cancel);
		task_owner_spinner =  (Spinner) findViewById(R.id.owner_task);

		
		// task type 
		task_type_list = new ArrayList<String>();
		task_type_ids = new ArrayList<Integer>();
		
		//sub task type
		sub_task_type_list = new ArrayList<String>();
		sub_task_type_ids = new ArrayList<Integer>();
		
		ArrayList priority_list = new ArrayList<String>();
		priority_list.add("1(Low)");
		priority_list.add("2");
		priority_list.add("3");
		priority_list.add("4");
		priority_list.add("5(High)");
		
		ArrayAdapter<String> dataA = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, priority_list);
		dataA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		priority.setAdapter(dataA);
		priority.setOnItemSelectedListener(inst);
		
		
		due_date.setOnClickListener(all_click_listener);
		taskowner_cc.setOnClickListener(all_click_listener);
		//cancle_btn.setOnClickListener(all_click_listener);
		attachment_file.setOnClickListener(all_click_listener);
		submit_btn.setOnClickListener(all_click_listener);
		asyn_get_all_data();
		
		
		
		tab1=(ImageView)findViewById(R.id.tab1);
		tab2=(ImageView)findViewById(R.id.tab2);
		tab3=(ImageView)findViewById(R.id.tab3);
		tab4=(ImageView)findViewById(R.id.tab4);
		
		tab1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(AddTask.this,Main_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(AddTask.this,Member_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(AddTask.this,Attendence.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab4.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
	
				Intent mainintent=new Intent(AddTask.this,Task_Screen.class);
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
	
	
	
	//============================================================================
	// ========================== Click Listener For All==========================
	//============================================================================
	@SuppressWarnings({ "deprecation", "deprecation" })
	View.OnClickListener all_click_listener = new View.OnClickListener() 
		{
			public void onClick(View v) 
				{
					int Id = v.getId();
					switch (Id) {
				
		
					case R.id.duedatetext:
						
						mo_Year=myCalendar.get(Calendar.YEAR);
						mo_Month=myCalendar.get(Calendar.MONTH);
						mo_Day = myCalendar.get(Calendar.DAY_OF_MONTH);
						
						DatePickerDialog dialogdate = new DatePickerDialog(AddTask.this, date,
								myCalendar.get(Calendar.YEAR),
								myCalendar.get(Calendar.MONTH),
								myCalendar.get(Calendar.DAY_OF_MONTH))
						{
							@Override
							public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
								{   
									if (mo_Year > year)
										{
											view.updateDate(mo_Year, mo_Month, mo_Day);
										}
									if (mo_Month > monthOfYear && mo_Year == year)
										{
											view.updateDate(mo_Year, mo_Month, mo_Day);
										}

									if (mo_Day > dayOfMonth && mo_Year == year && mo_Month == monthOfYear)
										{
											view.updateDate(mo_Year, mo_Month, mo_Day);
										}
								}
							};
						dialogdate.show();
					break;
						
					case R.id.taskownertext:
						showDialog(0);
					break;
						
					case R.id.attachment:
						Intent file_chooser_activity = new Intent(AddTask.this,FileChooser.class);
						startActivityForResult(file_chooser_activity, 1);
					break;
						
					case R.id.reg_submit:
						int count = 0;
						String sub = subject.getText().toString().trim();
						String des = description_about.getText().toString().trim();
						String date = due_date.getText().toString().trim();
						
						if(sub.equals(""))
							{	
								subject.setError("This filed can't be left blank");
							}
						else
							{
								count = count + 1;
							}
						
						if(des.equals(""))
							{	
								description_about.setError("This filed can't be left blank");
							}
						else
							{
								count = count + 1;
							}
						
						if(date.equals(""))
							{	
								due_date.setError("This filed can't be left blank");
							}
						else
							{
								count = count + 1;
							}
						
						if(selected_task_owner.equals("NA"))
							{
								AlertDialog.Builder alertError = new AlertDialog.Builder(inst);
								alertError.setMessage("Please Select Task Owner").setCancelable(true)
									.setPositiveButton("OK", new DialogInterface.OnClickListener() 
										{
											public void onClick(DialogInterface dialog, int id) 
												{
													//finish();
												}
										});
								AlertDialog al = alertError.create();
								al.show();
							}
						else
							{
								count = count + 1;
							}
						
						if(selected_sub_task_id==0 || selected_sub_task_id==-3)
							{
								AlertDialog.Builder alertError = new AlertDialog.Builder(inst);
								alertError.setMessage("Please Enter Sub Task Type").setCancelable(true)
									.setPositiveButton("OK", new DialogInterface.OnClickListener() 
										{
											public void onClick(DialogInterface dialog, int id) 
												{
													//finish();
												}
										});
								AlertDialog al = alertError.create();
								al.show();
							}
						else
							{
								count = count + 1;
							}
						
						if(count == 5)
							{
								msubject=subject.getText().toString().trim();
								mdescription=description_about.getText().toString().trim();
								mduedate=due_date.getText().toString().trim();
								mduedate = mduedate.replace("/", "");
								sync_send_data_to_server();
							}
						else 
							{
								AlertDialog.Builder alertError = new AlertDialog.Builder(inst);
								alertError.setMessage("Please enter all the details").setCancelable(true)
									.setPositiveButton("OK", new DialogInterface.OnClickListener() 
										{
											public void onClick(DialogInterface dialog, int id) 
												{
													//finish();
												}
										});
								AlertDialog al = alertError.create();
								al.show();
							}
					break;
		
					/*case R.id.reg_cancel:
						finish();
					break;*/
					
					default:
						
					break;
				 }
		}
	};
	
	
    //=======================================================================================
	// ========================= Code for Date Picker========================================
	//=======================================================================================
	final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() 
		{
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
				{
					m_Year=year;
					m_Month=monthOfYear+1;
					m_Day=dayOfMonth;
				
					//myCalendar.set(Calendar.YEAR, year);
					//myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					//myCalendar.set(Calendar.MONTH, monthOfYear);
					updateLabel();
				}
		};

	private void updateLabel()
		{
			String myFormat = "yyyy/MM/dd"; // In which you need put here
			SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
			due_date.setText(sdf.format(myCalendar.getTime()));
		}

	
	//============================================================================================
	// ================================ Task owner List Multiple Selection =======================
	//============================================================================================
	@Override
	protected Dialog onCreateDialog(int id)
		{
			return new AlertDialog.Builder(this)
					.setTitle("Task Owner")
					.setIcon(R.drawable.task_owner_list_press)
					.setMultiChoiceItems(_options, _selections,
							new DialogSelectionClickHandler())
					.setPositiveButton("OK",new Dialog_Positive_ButtonClickHandler())
					// .setNegativeButton("CANCLE", new
					// Dialog_Negative_ButtonClickHandler())
					.create();
		}

	public class DialogSelectionClickHandler implements DialogInterface.OnMultiChoiceClickListener
		{
			public void onClick(DialogInterface dialog, int clicked,boolean selected)
				{
					Log.i("ME", _options[clicked] + "selected: " + selected);
					task_owner_selected_id = new ArrayList<String>();
		
					if (selected == true) 
						{
							task_owner_name_list.add((String) _options[clicked]);
							task_owner_selected_id.add(task_owner_id.get(clicked));
						}
					if (selected == false) 
						{
							task_owner_name_list.remove((String) _options[clicked]);
							task_owner_selected_id.remove(task_owner_id.get(clicked));
						}
				}
		}

	public class Dialog_Positive_ButtonClickHandler implements DialogInterface.OnClickListener
		{
			public void onClick(DialogInterface dialog, int clicked)
				{
					switch (clicked) 
						{
							case DialogInterface.BUTTON_POSITIVE:
								taskowner_cc.setText("");
								String set_text = new String("");
								for (int i = 0; i < task_owner_name_list.size(); i++) 
									{
										String s = task_owner_name_list.get(i);
										set_text = set_text + s + ",";
									}
								taskowner_cc.setText(set_text);
							break;
						}
				}
		}

	public class Dialog_Negative_ButtonClickHandler implements  DialogInterface.OnClickListener
		{
			public void onClick(DialogInterface dialog, int clicked)
				{
					switch (clicked) 
						{
							case DialogInterface.BUTTON_NEGATIVE:
								
							break;
						}
				}
		}

	
	//======================================================================================
	// ==================================== Attach Files====================================
	//======================================================================================

	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
		{
			if (requestCode == 1) 
				{
					if (resultCode == RESULT_OK) 
						{
							String file_info[] = data.getStringArrayExtra("f_info");
							file_information[0] = file_info[0];
							file_information[1] = file_info[1];
							file_information[2] = file_info[2];
							file_information[3] = file_info[3];
							attachment_file.setText(file_info[1]);
						}
				}
		}

	//========================================================================================
	// =================================== Get all The data from Web Service Code=============
	//========================================================================================

	public void asyn_get_all_data() 
		{
			dialog = ProgressDialog.show(inst, "Loading Task Contents...", "Please wait...",true, false);
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
											String mem_no = global_storage.getString("MEMBER_NO", "");
									
											String tsk_gr = webservice.get_task_type_group();
											tsk_grp_msg = new String();
											pass_parameter = mem_no;
											task_owner_data = webservice.task_owner_MethodCall(	pass_parameter, "get_task_contact_list");

											
											//================task group data=================================
											if(tsk_gr.contains("java.net.UnknownHostException")||tsk_gr.contains("java.net.ConnectException") || tsk_gr.contains("java.net.SocketException"))
												{
													tsk_grp_msg = tsk_gr;
													inst.Data_handler.sendEmptyMessage(1);
												}
											else
												{
													JSONObject tsk_grp_obj = new JSONObject(tsk_gr);
													String success = tsk_grp_obj.getString("success");
													
													if(success.equalsIgnoreCase("true"))
														{
															tsk_grp_msg = tsk_grp_obj.getString("message");
															JSONArray  tsk = new  JSONArray(tsk_grp_msg);
															task_type_group_name = new ArrayList<String>();
															task_type_group_id =  new ArrayList<String>();
															task_type_group_name.add("Select");
															task_type_group_id.add("-1");
															for(int i=0;i<tsk.length();i++)
																{
																	String group_name = tsk.getJSONObject(i).getString("group_name");
																	task_type_group_name.add(group_name);
																	
																	String group_id = tsk.getJSONObject(i).getString("id");
																	task_type_group_id.add(group_id);
																}
															inst.Data_handler.sendEmptyMessage(2);
														}
													else
														{
															tsk_grp_msg  = tsk_grp_obj.getString("message");
															inst.Data_handler.sendEmptyMessage(1);
														}
												}
											
														
											// =============get the task contact owner list================
											if(task_owner_data.contains("java.net.UnknownHostException")||task_owner_data.contains("java.net.ConnectException") || task_owner_data.contains("java.net.SocketException"))
												{
													inst.Data_handler.sendEmptyMessage(4);
												}
											else
												{
													if (task_owner_data == null) 
														{
															inst.Data_handler.sendEmptyMessage(0);
														} 
													else if (task_owner_data != null) 
														{
															//System.out.println("Task owner="+task_owner_data);
															inst.Data_handler.sendEmptyMessage(3);
														}
												}
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

	public void get_task_types(String id)
		{
			dialog = ProgressDialog.show(inst, "Loading Task Types...", "Please wait...",true, false);
			dialog.setCanceledOnTouchOutside(false);
	
			Thread thread = new Thread(new Runnable() 
				{
					@Override
					public void run() 
						{
							try {
									if (check_network()) 
										{
											task_type_data = webservice.get_task_type_list(seleted_task_grp_id);
											System.out.println("Task types = "+task_type_data);
											if(task_type_data.contains("java.net.UnknownHostException")||task_type_data.contains("java.net.ConnectException") || task_type_data.contains("java.net.SocketException"))
												{
													task_type_net_exception = task_type_data;
													inst.Data_handler.sendEmptyMessage(6);
												}
											else
												{
													JSONObject tsk_type_obj = new JSONObject(task_type_data);
													String success = tsk_type_obj.getString("success");
													
													if(success.equalsIgnoreCase("true"))
														{
															String msg = tsk_type_obj.getString("message");
															JSONArray  tsk_typ = new  JSONArray(msg);
															task_type_list.clear();
															task_type_ids.clear();
															task_type_list.add("Select");
															task_type_ids.add(-2);
															for(int i=0;i<tsk_typ.length();i++)
																{
																	String task_type = tsk_typ.getJSONObject(i).getString("type_name");
																	task_type_list.add(task_type);
																	int task_id = tsk_typ.getJSONObject(i).getInt("id");
																	task_type_ids.add(task_id);
																}
															
															inst.Data_handler.sendEmptyMessage(7);
														}
													else
														{
															task_type_net_exception = tsk_type_obj.getString("message");
															inst.Data_handler.sendEmptyMessage(6);
														}
												}
										
										}
									else
										{
											inst.Data_handler.sendEmptyMessage(4);
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
	
	
	//======================================================================================
	//=============================== Get the sub task type=================================
	//======================================================================================
	public void get_sub_task_types(final int tsk)
		{
			dialog = ProgressDialog.show(inst, "Loading Sub Task Types...", "Please wait...",true, false);
			dialog.setCanceledOnTouchOutside(false);
			
			Thread thread = new Thread(new Runnable() 
				{
					@Override
					public void run() 
						{
							try 
								{
									if (check_network()) 
										{
											sub_task_type_data = webservice.get_sub_task_type_list(tsk);
											System.out.println("Sub Task types = "+sub_task_type_data);
											if(sub_task_type_data.contains("java.net.UnknownHostException")||sub_task_type_data.contains("java.net.ConnectException") || sub_task_type_data.contains("java.net.SocketException"))
												{
													sub_task_type_net_exception = sub_task_type_data;
													inst.Data_handler.sendEmptyMessage(8);
												}
											else
												{
													JSONObject sub_tsk_type_obj = new JSONObject(sub_task_type_data);
													String success = sub_tsk_type_obj.getString("success");
													
													if(success.equalsIgnoreCase("true"))
														{
															String msg = sub_tsk_type_obj.getString("message");
															JSONArray  sb_tsk_typ = new  JSONArray(msg);
															sub_task_type_list.clear();
															sub_task_type_ids.clear();
															sub_task_type_list.add("Select");
															sub_task_type_ids.add(-3);
															for(int i=0;i<sb_tsk_typ.length();i++)
																{
																	String sb_task_type = sb_tsk_typ.getJSONObject(i).getString("sub_type_name");
																	sub_task_type_list.add(sb_task_type);
																	int sb_task_id = sb_tsk_typ.getJSONObject(i).getInt("id");
																	sub_task_type_ids.add(sb_task_id);
																}	
															inst.Data_handler.sendEmptyMessage(9);
														}
													else
														{
															sub_task_type_net_exception = sub_tsk_type_obj.getString("message");
															inst.Data_handler.sendEmptyMessage(8);
														}
												}
										}
									else
										{
											inst.Data_handler.sendEmptyMessage(4);
										}
								} 
							catch (Exception e)
								{
									e.printStackTrace();
									sub_task_type_net_exception = e.toString();
									inst.Data_handler.sendEmptyMessage(10);
								}
						}
					});thread.start();
			}
	
	

	//=======================================================================================================
	// ======================================== Send The Data to Web Server Code=============================
	//=======================================================================================================

	public void sync_send_data_to_server() 
		{
			dialog = ProgressDialog.show(inst, "Adding task...","Please wait...", true, false);
			dialog.setCanceledOnTouchOutside(false);	
			
			Thread thread = new Thread(new Runnable() 
				{
					@Override
					public void run() 
						{
							try {
									int size;
									//task_owner_selected_id = new ArrayList<String>();
									
									if (file_information[0].equals("")) 
										{
											size = 0;
										} 
									else 
										{
											size = Integer.valueOf(file_information[0]);
										}
									
									if (file_information[1].equals("")) 
										{
											file_information[1] = "nofile";
										}
									
									if (file_information[3].equals("")) 
										{
											file_information[3] = "nodata";
										}
									
									if (check_network()) 
										{
											//String prio = priority.getSelectedItem().toString();
											String prio = String.valueOf(priority.getSelectedItemPosition() + 1);
											
											SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
											String mem_no = global_storage.getString("MEMBER_NO", "");
											
											upload_data_response = webservice.post_data_MethodCall(
													msubject, mdescription, selected_sub_task_id,
													selected_task_owner, mem_no, mduedate, prio,
													task_owner_selected_id, file_information[1],
													file_information[3], size, "add_task");
											System.out.println("Upload data = "+upload_data_response);
											if(upload_data_response.contains("java.net.UnknownHostException")||upload_data_response.contains("java.net.ConnectException") || upload_data_response.contains("java.net.SocketException"))
												{
													inst.Data_handler.sendEmptyMessage(4);
												}
											else{
													if (upload_data_response == null) 
														{
															inst.Data_handler.sendEmptyMessage(0);
														} 
													else if (upload_data_response != null) 
														{
															inst.Data_handler.sendEmptyMessage(5);
															
														}
												}
										} 
									else 
										{
											showAlertDialog( "Please Check The Internet Connection");
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
	
	
	

	
	//=======================================================================================
	// =================================== Handler For Async=================================
	//=======================================================================================

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
								showAlertDialog("There is no Data");
							break;
				
							case 1:
								if (dialog != null)
									{
										dialog.dismiss();
									}
								showAlertDialog(tsk_grp_msg);
							break;
							
							
							case 2:
								if (dialog != null)
									{
										dialog.dismiss();
									}
								ArrayAdapter<String> datalist = new ArrayAdapter<String>(inst,android.R.layout.simple_spinner_item, task_type_group_name);
								datalist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								task_grp.setAdapter(datalist);
								task_grp.setOnItemSelectedListener(inst);
								
								
							break;
													
							
							case 3:
								try {
										JSONObject J_obj = new JSONObject(task_owner_data);
										String success_msg = J_obj.getString("success");
										//System.out.println("to = "+success_msg);
										if (success_msg.equalsIgnoreCase("true"))
											{
												String user_msg = J_obj.getString("message");
												
												if(user_msg.equalsIgnoreCase("Member cannot assign tasks to anyone"))
													{
														AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(inst);
														alertDialogBuilder.setMessage(user_msg).setCancelable(true)
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
												else
													{
														task_owner_id = new ArrayList<String>();
														task_owner_list = new ArrayList<String>();
														task_owner_list1 = new ArrayList<String>();
														JSONArray task_owner_contact_list = null;
														task_owner_contact_list = J_obj.getJSONArray("message");
														
														task_owner_list.add("Select");
																											
														for (int i = 0; i < task_owner_contact_list.length(); i++) 
															{
																String task_owner_name = task_owner_contact_list.getJSONObject(i).getString("FirstName");
																task_owner_list.add(task_owner_name);
															}
														
														for (int i = 0; i < task_owner_contact_list.length(); i++) 
															{
																String task_owner_name = task_owner_contact_list.getJSONObject(i).getString("FirstName");
																task_owner_list1.add(task_owner_name);
															}
														
														for (int i = 0; i < task_owner_contact_list.length(); i++)
															{
																task_owner_id.add(task_owner_contact_list.getJSONObject(i).getString("mem_id"));
															}
														
														// setting up   the task owner cc arraylist
														_options = new CharSequence[task_owner_list1.size()];
														_selections = new boolean[_options.length];
										
														for (int i = 0; i < task_owner_list1.size(); i++) 
															{
																_options[i] = task_owner_list1.get(i);
															}
														
														
														// setting up the task owner
														
														ArrayAdapter<String> ownerAdp = new ArrayAdapter<String>(inst,android.R.layout.simple_spinner_item,task_owner_list );
														ownerAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
														task_owner_spinner.setAdapter(ownerAdp);
														task_owner_spinner.setOnItemSelectedListener(inst);
														
													}
											}
										else
											{
												String user_msg = J_obj.getString("message");
												AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(inst);
												alertDialogBuilder.setMessage(user_msg).setCancelable(true)
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
							catch (Exception e) 
								{
									e.printStackTrace();
								}
						break;

						
			
						case 4:
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(inst);
							alertDialogBuilder.setMessage("Network Error! Please Try Again").setCancelable(true)
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
			
			
						case 5:
							try {
									JSONObject jobj = new JSONObject(upload_data_response);
									success = jobj.getString("success");
									String alert_msg = new String();
									
									if (success.equalsIgnoreCase("true")) 
										{
											alert_msg = jobj.getString("message");
										} 
									else 
										{
											alert_msg = jobj.getString("message");
										}
									
									AlertDialog.Builder adb= new AlertDialog.Builder(inst);
									adb.setMessage(alert_msg).setCancelable(true)
										.setPositiveButton("OK", new DialogInterface.OnClickListener() 
											{
												public void onClick(DialogInterface dialog, int id) 
													{
														//finish();
													}
											});
									AlertDialog ad = adb.create();
									ad.show();
								} 
							catch (Exception e)
								{
									e.printStackTrace();
								}
						break;
						
						case 6:
								if (dialog != null) 
									{
										dialog.dismiss();
									}
								
								AlertDialog.Builder alert = new AlertDialog.Builder(inst);
								alert.setMessage(task_type_net_exception).setCancelable(true)
									.setPositiveButton("OK", new DialogInterface.OnClickListener() 
										{
											public void onClick(DialogInterface dialog, int id) 
												{
													finish();
												}
										});
								AlertDialog alert1 = alert.create();
								alert1.show();
						break;
						
						case 7:
								ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(inst,android.R.layout.simple_spinner_item, task_type_list);
								dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								task_type.setAdapter(dataAdapter);
								task_type.setOnItemSelectedListener(inst);
								if (dialog != null) 
									{
										dialog.dismiss();
									}
						break;
						
						case 8:
							if (dialog != null) 
								{
									dialog.dismiss();
								}
							
							AlertDialog.Builder sub_tp_alrt = new AlertDialog.Builder(inst);
							sub_tp_alrt.setMessage(sub_task_type_net_exception).setCancelable(true)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() 
								{
									public void onClick(DialogInterface dialog, int id) 
										{
											//finish();
										}
								});
							AlertDialog sub_tp = sub_tp_alrt.create();
							sub_tp.show();
						break;
						
						case 9:
							ArrayAdapter<String> Adapter = new ArrayAdapter<String>(inst,android.R.layout.simple_spinner_item, sub_task_type_list);
							Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							sub_task_type.setAdapter(Adapter);
							sub_task_type.setOnItemSelectedListener(inst);
							if (dialog != null) 
								{
									dialog.dismiss();
								}
						
						break;
						
						case 10:
							if (dialog != null) 
								{
									dialog.dismiss();
								}
							
							AlertDialog.Builder json_exp = new AlertDialog.Builder(inst);
							json_exp.setMessage(sub_task_type_net_exception).setCancelable(true)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() 
								{
									public void onClick(DialogInterface dialog, int id) 
										{
											finish();
										}
								});
							AlertDialog json_exptn = json_exp.create();
							json_exptn.show();
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
	
	public void showAlertDialog(String message)
		{ 
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(inst);
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

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) 
	{
		int Id = parent.getId();
		switch (Id) 
			{
				case R.id.task_type_spinner:
					selected_task_id = task_type_ids.get(pos);
					selected_task_string = parent.getItemAtPosition(pos).toString();
					//get_sub_task_types(Integer.toString(selected_task_id));
					if(selected_task_id!=-2)
						{
							get_sub_task_types(selected_task_id);
						}
				break;
				
				case R.id.task_group_spinner:
					seleted_task_grp_id = task_type_group_id.get(pos);
					if(seleted_task_grp_id!="-1")
						{
							ArrayList arr = new ArrayList<String>();
							arr.add("Select");
							
							ArrayAdapter<String> dataAd = new ArrayAdapter<String>(inst,android.R.layout.simple_spinner_item, arr);
							dataAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							task_type.setAdapter(dataAd);
							
							ArrayAdapter<String> Ad = new ArrayAdapter<String>(inst,android.R.layout.simple_spinner_item, arr);
							Ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							sub_task_type.setAdapter(Ad);
						
							get_task_types(seleted_task_grp_id );
						}
				break;
				
				case R.id.prioritytext:
					//System.out.println("Prio = "+String.valueOf(priority.getSelectedItemPosition() + 1));
				break;
				
				case R.id.owner_task:
					if(pos==0)
						{
							selected_task_owner = "NA";
						}
					else
						{
							selected_task_owner = task_owner_id.get(pos-1);
						}
					
				break;
				
				case R.id.sub_task_type_spinner:
					selected_sub_task_id = sub_task_type_ids.get(pos);
					System.out.println("Sel Sub Task id = "+selected_sub_task_id);
				break;
			}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) 
	{
	}
}
