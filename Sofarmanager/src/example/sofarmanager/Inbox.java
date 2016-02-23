package example.sofarmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import example.sofarmanager.reuse.CallApi;
import example.sofarmanager.reuse.Child;
import example.sofarmanager.reuse.Parent;
import android.annotation.SuppressLint;
//import example.sofarmanager.reuse.Util;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class Inbox extends Activity
	{
	
		ImageView tab1,tab2,tab3,tab4,backbtn;
		
		ExpandableListAdapter listAdapter;
		ExpandableListView expListView;
		List<String> listDataHeader;
		HashMap<String, List<String>> listDataChild;
		DisplayMetrics metrics;
		int width;
		public ProgressDialog dialog;
		String inbox_web_service_return;
		Inbox inst;
		ArrayList<String> subject;
		ImageView back_button;
		ArrayList<String> creator_name_list;
		ArrayList<String> date_created_list;
		ArrayList<String> description_list;
		ArrayList<String> due_date_list;
		ArrayList<String> id_list;
		ArrayList<String> status_list;
		ArrayList<String> subject_list;
		Parent parentdata;
		ArrayList<Parent> dataforlist;
	
		@SuppressLint("NewApi")
		@Override
		protected void onCreate(Bundle savedInstanceState) 
			{
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				setContentView(R.layout.activity_inbox);
				this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				// get the listview
				
				metrics = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(metrics);
				width = metrics.widthPixels;
				// this code for adjusting the group indicator into right side of the
				// view
		
				subject = new ArrayList<String>();
				inst = this;
		
				expListView = (ExpandableListView) findViewById(R.id.lvExp);
				
				if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) 
					{
						expListView.setIndicatorBounds(width - GetDipsFromPixel(50), width- GetDipsFromPixel(10));
					} 
				else 
					{
						expListView.setIndicatorBoundsRelative(width - GetDipsFromPixel(50), width - GetDipsFromPixel(10));
					}
		
				creator_name_list = new ArrayList<String>();
				date_created_list = new ArrayList<String>();
				description_list = new ArrayList<String>();
				due_date_list = new ArrayList<String>();
				id_list = new ArrayList<String>();
				status_list = new ArrayList<String>();
				subject_list = new ArrayList<String>();
		
				dataforlist = new ArrayList<Parent>();
		
				asyn_get_all_data();
				
	
				expListView.setOnItemClickListener(new OnItemClickListener() 
					{
		
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
					{
						// TODO Auto-generated method stub
					}
				});
	
				
				tab1=(ImageView)findViewById(R.id.tab1);
				tab2=(ImageView)findViewById(R.id.tab2);
				tab3=(ImageView)findViewById(R.id.tab3);
				tab4=(ImageView)findViewById(R.id.tab4);
				
				tab1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(Inbox.this,Main_Screen.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(Inbox.this,Member_Screen.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab3.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(Inbox.this,Attendence.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab4.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(View v) {
			
						Intent mainintent=new Intent(Inbox.this,Task_Screen.class);
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
	
		public int GetDipsFromPixel(float pixels)
			{
				// Get the screen's density scale
				final float scale = getResources().getDisplayMetrics().density;
				// Convert the dps to pixels, based on density scale
				return (int) (pixels * scale + 0.5f);
			}
	
		/*
		 * Preparing the list data
		 */
		private void prepareListData()
			{
				listDataHeader = new ArrayList<String>();
				listDataChild = new HashMap<String, List<String>>();
				// Adding child data
				for (int i = 0; i < creator_name_list.size(); i++) 
					{
						listDataHeader.add(creator_name_list.get(i));	
					}
	
	
				// Adding child data
				List<String> top250 = new ArrayList<String>();
				top250.add("The Shawshank Redemption");
				listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
			}
	
		class ExpandableListAdapter extends BaseExpandableListAdapter 
			{
				private Context _context;
				private List<String> _listDataHeader; // header titles
				// child data in format of header title, child title
				private HashMap<String, List<String>> _listDataChild;
				ArrayList<String> msubject, mCreator, mdescription, mduedate, mstatus,
						mdate;
				int lastExpandedGroupPosition;
				ArrayList<Parent> data;
		
				public ExpandableListAdapter(Context context,List<String> listDataHeader,HashMap<String, List<String>> listChildData,
						ArrayList<String> sub) 
					{
						this._context = context;
						this._listDataHeader = listDataHeader;
						this._listDataChild = listChildData;
					}
		
				public ExpandableListAdapter(Context context,
						List<String> listDataHeader, ArrayList<String> subject,
						ArrayList<String> creator, ArrayList<String> date,
						ArrayList<String> duedate, ArrayList<String> description,
						ArrayList<String> status) 
					{
						this._context = context;
						this._listDataHeader = listDataHeader;
						this.msubject = subject;
						this.mdate = date;
						this.mCreator = creator;
						this.mdescription = description;
						this.mstatus = status;
						this.mduedate = duedate;
					}
		
				public ExpandableListAdapter(Context con, ArrayList<Parent> data)
					{
						// TODO Auto-generated constructor stub
						this._context = con;
						this.data = data;
					}
		
				@Override
				public Object getChild(int groupPosition, int childPosititon) {
					// TODO Auto-generated method stub
					return this.mstatus.get(groupPosition);
				}
		
				@Override
				public long getChildId(int groupPosition, int childPosition)
					{
						// TODO Auto-generated method stub
						return childPosition;
					}
		
				@Override
				public View getChildView(int groupPosition, final int childPosition,
						boolean isLastChild, View convertView, ViewGroup parent) 
					{
						if (convertView == null) 
							{
								LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
								convertView = infalInflater.inflate(R.layout.inbox_listitem,null);
								
								TextView descrition = (TextView) convertView.findViewById(R.id.description_details);
								TextView duedate = (TextView) convertView.findViewById(R.id.duedatetext_details);
								TextView status = (TextView) convertView.findViewById(R.id.status_text);
		
								descrition.setText(data.get(groupPosition).getChilddata().get(childPosition).getDescription());
								duedate.setText(data.get(groupPosition).getChilddata().get(childPosition).getDuedate());
		
								System.out.println("Due Date in adapter...."
								+ data.get(groupPosition).getChilddata()
										.get(childPosition).getDuedate());
								status.setText(data.get(groupPosition).getChilddata().get(childPosition).getStatus());
		
								/*
								 * descrition.setText(mdescription.get(groupPosition));
								 * duedate.setText(mduedate.get(groupPosition));
								 * status.setText(mstatus.get(groupPosition));
								 */
							}
		
						TextView descrition = (TextView) convertView.findViewById(R.id.description_details);
						TextView duedate = (TextView) convertView.findViewById(R.id.duedatetext_details);
						TextView status = (TextView) convertView.findViewById(R.id.status_text);
		
						descrition.setText(data.get(groupPosition).getChilddata().get(childPosition).getDescription());
						duedate.setText(data.get(groupPosition).getChilddata().get(childPosition).getDuedate());
		
						System.out.println("Due Date in adapter...."
							+ data.get(groupPosition).getChilddata().get(childPosition)
									.getDuedate());
						status.setText(data.get(groupPosition).getChilddata().get(childPosition).getStatus());
		
						// TextView txtListChild = (TextView)
						// convertView.findViewById(R.id.lblListItem);
		
						// txtListChild.setText(childText);
						return convertView;
					}
		
				@Override
				public int getChildrenCount(int groupPosition) 
					{
						return data.get(groupPosition).getChilddata().size();
					}
		
				@Override
				public Object getGroup(int groupPosition) 
					{
						return this.data.get(groupPosition);
					}
		
				@Override
				public int getGroupCount() 
					{
						return this.data.size();
					}
		
				@Override
				public long getGroupId(int groupPosition) 
					{
						return groupPosition;
					}
		
				@Override
				public void onGroupExpanded(int groupPosition) 
					{
						// collapse the old expanded group, if not the same
						// as new group to expand
						if (groupPosition != lastExpandedGroupPosition)
							{
								expListView.collapseGroup(lastExpandedGroupPosition);
							}
						super.onGroupExpanded(groupPosition);
						lastExpandedGroupPosition = groupPosition;
					}
		
				@Override
				public View getGroupView(int groupPosition, boolean isExpanded,	View convertView, ViewGroup parent) 
					{
						// String headerTitle = (String) getGroup(groupPosition);
						if (convertView == null) 
							{
								LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
								convertView = infalInflater.inflate(R.layout.inbox_listgroup,null);
								TextView header = (TextView) convertView.findViewById(R.id.Header_creator_name_text);
								TextView subject = (TextView) convertView.findViewById(R.id.subject_text);
								TextView c_date = (TextView) convertView.findViewById(R.id.created_date_text);
		
								header.setText(data.get(groupPosition).getHearder());
								subject.setText(data.get(groupPosition).getSubject());
								c_date.setText(data.get(groupPosition).getCreatedDate());
		
								/*
								 * header.setText(mCreator.get(groupPosition));
								 * subject.setText(msubject.get(groupPosition));
								 * c_date.setText(mdate.get(groupPosition));
								 */
							}
		
						TextView header = (TextView) convertView.findViewById(R.id.Header_creator_name_text);
						TextView subject = (TextView) convertView.findViewById(R.id.subject_text);
						TextView c_date = (TextView) convertView.findViewById(R.id.created_date_text);
		
						header.setText(data.get(groupPosition).getHearder());
						subject.setText(data.get(groupPosition).getSubject());
						c_date.setText(data.get(groupPosition).getCreatedDate());
		
						/*
						 * TextView lblListHeader = (TextView)
						 * convertView.findViewById(R.id.lblListHeader);
						 * lblListHeader.setTypeface(null, Typeface.BOLD);
						 * lblListHeader.setText(headerTitle);
						 */
						return convertView;
					}
		
				@Override
				public boolean hasStableIds() 
					{
						return false;
					}
		
				@Override
				public boolean isChildSelectable(int groupPosition, int childPosition)
					{
						return true;
					}
			}
	
		// ============================= Getting All The Data From Web Service========================
		public void asyn_get_all_data() 
			{
				dialog = ProgressDialog.show(this, "Loading Inbox content...", "Please wait...",true, false);
				dialog.setCanceledOnTouchOutside(false);
	
				Thread thread = new Thread(new Runnable()
					{
						@Override
						public void run()
							{
								try {
										if (check_network())
											{
												// ============= get task types====================
												CallApi webservice = new CallApi();
												String method_to_call = "get_user_inbox";
							
												SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
												String mem_no = global_storage.getString("MEMBER_NO", "");
						
							
												String parameter = mem_no;
												inbox_web_service_return = webservice.inbox_get_data_MethodCall(parameter,method_to_call);
												if(inbox_web_service_return.contains("java.net.UnknownHostException")||inbox_web_service_return.contains("java.net.ConnectException") || inbox_web_service_return.contains("java.net.SocketException"))
													{
														inst.Data_handler.sendEmptyMessage(2);
													}
												else
													{
														if (inbox_web_service_return == null) 
															{
																inst.Data_handler.sendEmptyMessage(0);
															} 
														else if (inbox_web_service_return != null)
															{
																inst.Data_handler.sendEmptyMessage(1);
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
	
		// ======== handler for the thread====================
		private Handler Data_handler = new Handler() 
			{
				@Override
				public void handleMessage(Message msg) 
					{
						dialog.dismiss();
						
						switch (msg.what) 
							{
								case 0:
									if (dialog != null)
										{
											dialog.dismiss();
										}
									showAlertDialog(inst,"No Response");
								break;
	
								case 1:
									try {
											Log.i("ibox data:", inbox_web_service_return + ":");
											JSONObject jobj = new JSONObject(inbox_web_service_return);
											String success_val = jobj.getString("success");
	
											if (success_val.equalsIgnoreCase("true")) 
												{
													JSONArray inbox_data = null;
													inbox_data = jobj.getJSONArray("message");
	
													for (int i = 0; i < inbox_data.length(); i++)
														{
															parentdata = new Parent();
															ArrayList<Child> childdata = new ArrayList<Child>();
															Child child = new Child();
															parentdata.setCreatedDate(inbox_data.getJSONObject(i).getString("date_created"));
	
															parentdata.setHearder(inbox_data.getJSONObject(i).getString("creator_name"));
															parentdata.setSubject(inbox_data.getJSONObject(i).getString("subject"));
	
															child.setDescription(inbox_data.getJSONObject(i).getString("description"));
															child.setDuedate(inbox_data.getJSONObject(i).getString("due_date"));
															System.out.println("Due Date...."+ inbox_data.getJSONObject(i).getString("due_date"));
															child.setStatus(inbox_data.getJSONObject(i).getString("status"));
	
															childdata.add(child);
															parentdata.setChilddata(childdata);
	
															dataforlist.add(parentdata);
								
															/*
															 * creator_name_list.add(inbox_data.getJSONObject(i)
															 * .getString("creator_name"));
															 * date_created_list.add(inbox_data.getJSONObject(i)
															 * .getString("date_created"));
															 * description_list.add(inbox_data.getJSONObject(i)
															 * .getString("description"));
															 * due_date_list.add(inbox_data.getJSONObject(i)
															 * .getString("due_date"));
															 * System.out.println("du_d=" +
															 * due_date_list.get(i).toString());
															 * id_list.add(inbox_data
															 * .getJSONObject(i).getString( "id"));
															 * status_list.add(inbox_data.getJSONObject(i)
															 * .getString("status"));
															 * subject_list.add(inbox_data.getJSONObject(i)
															 * .getString("subject"));
															 */
														}
	
																			/*
													 * listAdapter = new ExpandableListAdapter(Inbox.this,
													 * listDataHeader, subject_list, creator_name_list,
													 * date_created_list, due_date_list, description_list,
													 * status_list);
													 */
							
													listAdapter = new ExpandableListAdapter(Inbox.this,dataforlist);
													// setting list adapter
													expListView.setAdapter(listAdapter);
							
												}
											else 
												{
													showAlertDialog(inst,jobj.getString("message"));
													//Toast.makeText(inst,jobj.getString("message"),Toast.LENGTH_LONG).show();
												}
										} 
									catch (Exception e) 
										{
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
						if (dialog != null)
							{
								dialog.dismiss();
							}
						super.handleMessage(msg);
					}
			};
	
		// ========================Check The internet is avialable or not=============================
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
	
		
		
		public void showAlertDialog(Context con, String message) 
			{
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
				alertDialogBuilder.setMessage(message).setCancelable(true)
					.setPositiveButton("OK", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id) 
								{
									finish();
								}
						});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
}
