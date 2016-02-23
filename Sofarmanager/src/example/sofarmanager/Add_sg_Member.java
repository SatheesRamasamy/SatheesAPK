package example.sofarmanager;

import java.util.ArrayList;

import org.json.JSONArray;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;

public class Add_sg_Member extends Activity implements OnItemSelectedListener 
{
	ListView list;
	Dialog dialog;
	CallApi call;
	String response, message,seraching_txt,selected_text;
	ArrayList<String> m_name,mf_name, msur_name, mmem_id,dob,street_addr,suburb_addr,city_addr,email,mobile;
	ArrayList<String> temp_m_name,temp_mf_name, temp_msur_name, temp_mmem_id,temp_dob,temp_street_addr,temp_suburb_addr,temp_city_addr,temp_email,temp_mobile;
	AlertDialog  aldialog;
	Spinner filter;
	TextView search_btn;
	//EditText stext;
	AutoCompleteTextView stext;
	int pos,univ_index;
	RelativeLayout search;
	
	ImageView tab1,tab2,tab3,tab4,backbtn;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_sg_member);
		
		
		list = (ListView) findViewById(R.id.member_list);
		
		get_members();
		
		search = (RelativeLayout) findViewById(R.id.serach_button);
		search_btn = (TextView) findViewById(R.id.s_img);
		filter = (Spinner) findViewById(R.id.search_option);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.search_array,android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		filter.setAdapter(adapter);
		filter.setOnItemSelectedListener(Add_sg_Member.this);
		
		stext = (AutoCompleteTextView) findViewById(R.id.search_text);
		
		stext.setOnItemClickListener(new OnItemClickListener()
			{
		    	public void onItemClick(AdapterView<?> parent, View view, int position, long rowId)
		    		{
		    			selected_text = (String)parent.getItemAtPosition(position);
		    			System.out.println("sel = "+selected_text);
		    		}
			});
	
		
		search_btn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
				{
					pos = filter.getSelectedItemPosition();
					seraching_txt = stext.getText().toString();
					
					if (pos == 0) 
						{
							Toast.makeText(Add_sg_Member.this,"Please select any category", Toast.LENGTH_SHORT).show();
						} 
					else if (seraching_txt.length() == 0) 
						{
							Toast.makeText(Add_sg_Member.this,"Please enter any keyword to search",Toast.LENGTH_SHORT).show();
						} 
					else 
						{
							dialog = ProgressDialog.show(Add_sg_Member.this, "Searching member...","Please wait...");
		    				selected_text = stext.getText().toString();
		    				if(pos==1)
		    					{
		    						if(mf_name.contains(selected_text))
		    							{
		    								univ_index = mf_name.indexOf(selected_text);
		    							
		    							}
		    						else
		    							{
			    							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add_sg_Member.this);
			    							
			    							alertDialogBuilder.setMessage("There is no such First Name in record").setCancelable(true)
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
		    				
		    				if(pos==2)
		    					{
		    						if(msur_name.contains(selected_text))
		    							{
		    								univ_index = msur_name.indexOf(selected_text);
		    							}
		    						else
		    							{
			    							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add_sg_Member.this);
			    							
			    							alertDialogBuilder.setMessage("There is no such Sur Name in record ").setCancelable(true)
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
		    				
		    				if(pos==3)
		    					{
		    						if(mobile.contains(selected_text))
		    							{
		    								univ_index = mf_name.indexOf(selected_text);
		    							}
		    						else
		    							{
			    							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add_sg_Member.this);
			    							
			    							alertDialogBuilder.setMessage("There is no such mobile no in record").setCancelable(true)
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
		    				temp_m_name = new ArrayList<String>();
		    				temp_mf_name = new ArrayList<String>(); 
		    				temp_msur_name = new ArrayList<String>();
		    				temp_mmem_id = new ArrayList<String>();
		    				temp_dob = new ArrayList<String>();
		    				temp_street_addr = new ArrayList<String>();
		    				temp_suburb_addr = new ArrayList<String>();
		    				temp_city_addr = new ArrayList<String>();
		    				temp_email = new ArrayList<String>();
		    				temp_mobile = new ArrayList<String>();
		    				
		    				
		    				temp_m_name.add(m_name.get(univ_index));
		    				temp_mf_name.add(mf_name.get(univ_index)); 
		    				temp_msur_name.add(msur_name.get(univ_index));
		    				temp_mmem_id.add(mmem_id.get(univ_index));
		    				temp_dob.add(dob.get(univ_index));
		    				temp_street_addr.add(street_addr.get(univ_index));
		    				temp_suburb_addr.add(suburb_addr.get(univ_index));
		    				temp_city_addr.add(city_addr.get(univ_index));
		    				temp_email.add(email.get(univ_index));
		    				temp_mobile.add(mobile.get(univ_index));
		    				

							CustomAdapter adapter = new CustomAdapter(Add_sg_Member.this);
							list.setAdapter(adapter);
		    				System.out.println("sel = "+selected_text);
		    				dialog.dismiss();
						}
				}
			});

		
		
		search.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) 
					{
						pos = filter.getSelectedItemPosition();
						seraching_txt = stext.getText().toString();
						
						if (pos == 0) 
							{
								Toast.makeText(Add_sg_Member.this,"Please select any category", Toast.LENGTH_SHORT).show();
							} 
						else if (seraching_txt.length() == 0) 
							{
								Toast.makeText(Add_sg_Member.this,"Please enter any keyword to search",Toast.LENGTH_SHORT).show();
							} 
						else 
							{
								dialog = ProgressDialog.show(Add_sg_Member.this, "Searching member...","Please wait...");
			    				selected_text = stext.getText().toString();
			    				if(pos==1)
			    					{
			    						if(mf_name.contains(selected_text))
			    							{
			    								univ_index = mf_name.indexOf(selected_text);
			    							
			    							}
			    						else
			    							{
				    							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add_sg_Member.this);
				    							
				    							alertDialogBuilder.setMessage("There is no such First Name in record").setCancelable(true)
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
			    				
			    				if(pos==2)
			    					{
			    						if(msur_name.contains(selected_text))
			    							{
			    								univ_index = msur_name.indexOf(selected_text);
			    							}
			    						else
			    							{
				    							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add_sg_Member.this);
				    							
				    							alertDialogBuilder.setMessage("There is no such Sur Name in record ").setCancelable(true)
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
			    				
			    				if(pos==3)
			    					{
			    						if(mobile.contains(selected_text))
			    							{
			    								univ_index = mf_name.indexOf(selected_text);
			    							}
			    						else
			    							{
				    							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add_sg_Member.this);
				    							
				    							alertDialogBuilder.setMessage("There is no such mobile no in record").setCancelable(true)
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
			    				temp_m_name = new ArrayList<String>();
			    				temp_mf_name = new ArrayList<String>(); 
			    				temp_msur_name = new ArrayList<String>();
			    				temp_mmem_id = new ArrayList<String>();
			    				temp_dob = new ArrayList<String>();
			    				temp_street_addr = new ArrayList<String>();
			    				temp_suburb_addr = new ArrayList<String>();
			    				temp_city_addr = new ArrayList<String>();
			    				temp_email = new ArrayList<String>();
			    				temp_mobile = new ArrayList<String>();
			    				
			    				
			    				temp_m_name.add(m_name.get(univ_index));
			    				temp_mf_name.add(mf_name.get(univ_index)); 
			    				temp_msur_name.add(msur_name.get(univ_index));
			    				temp_mmem_id.add(mmem_id.get(univ_index));
			    				temp_dob.add(dob.get(univ_index));
			    				temp_street_addr.add(street_addr.get(univ_index));
			    				temp_suburb_addr.add(suburb_addr.get(univ_index));
			    				temp_city_addr.add(city_addr.get(univ_index));
			    				temp_email.add(email.get(univ_index));
			    				temp_mobile.add(mobile.get(univ_index));
			    				

								CustomAdapter adapter = new CustomAdapter(Add_sg_Member.this);
								list.setAdapter(adapter);
			    				System.out.println("sel = "+selected_text);
			    				dialog.dismiss();
							}
					}
			});
		
		
		list.setOnItemClickListener(new OnItemClickListener() 
			{
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,long arg3) 
					{
						add_member(arg2);
					}
			});
		
		
		tab1=(ImageView)findViewById(R.id.tab1);
		tab2=(ImageView)findViewById(R.id.tab2);
		tab3=(ImageView)findViewById(R.id.tab3);
		tab4=(ImageView)findViewById(R.id.tab4);
		backbtn=(ImageView)findViewById(R.id.back_btn);
		
		tab1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Add_sg_Member.this,Main_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Add_sg_Member.this,Member_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Add_sg_Member.this,Attendence.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab4.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
	
				Intent mainintent=new Intent(Add_sg_Member.this,Task_Screen.class);
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

	
	public void get_members()
		{
			dialog = ProgressDialog.show(Add_sg_Member.this, "Loading Data...","Please wait...");
			
			new Thread(new Runnable() 
				{
					@Override
					public void run() 
						{
							call = new CallApi();
							SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
							String memno = global_storage.getString("MEMBER_NO", "");
							response = call.get_members_in_sg_leader_congregation(memno,"get_members_in_sg_leader_congregation");
							try {
									JSONObject obj = new JSONObject(response);
									String success = obj.getString("success");
									
									if (success.equalsIgnoreCase("true")) 
										{
											mf_name = new ArrayList<String>();
											msur_name = new ArrayList<String>();
											m_name = new ArrayList<String>();
											mmem_id = new ArrayList<String>();
											dob = new ArrayList<String>();
											street_addr = new ArrayList<String>();
											suburb_addr = new ArrayList<String>();
											city_addr = new ArrayList<String>();
											email = new ArrayList<String>();
											mobile = new ArrayList<String>();
											JSONArray dataarr = obj.getJSONArray("message");
											System.out.println("Json ARRAY SiZE :"+dataarr.length());
											for (int i = 0; i < dataarr.length(); i++)
												{
													JSONObject data = dataarr.getJSONObject(i);
													mf_name.add(data.getString("FirstName"));
													msur_name.add(data.getString("Surname"));
													
													m_name.add(mf_name.get(i)+" "+msur_name.get(i));
													mmem_id.add(data.getString("Mem_No"));
													dob.add(data.getString("Date_Birth"));
													street_addr.add(data.getString("street"));
													suburb_addr.add(data.getString("suburb"));
													city_addr.add(data.getString("city"));
													email.add(data.getString("E_Mail"));
													mobile.add(data.getString("Mobile"));
												}
											hanlerobj.sendEmptyMessage(1);
										} 
									else 
										{
											message = obj.getString("message");
											hanlerobj.sendEmptyMessage(2);
										}
							} 
						catch (JSONException e)
							{
								e.printStackTrace();
								hanlerobj.sendEmptyMessage(3);
							}
						}
				}).start();
			}
	
	
	
	/*public void search_member(int index)
		{
			dialog = ProgressDialog.show(Add_sg_Member.this, "Adding member...","Please wait...");
			
			new Thread(new Runnable()
				{
					@Override
					public void run()
						{
							try{
									
							   }
							catch(Exception e)
								{
									
								}
						}
				}).start();
		}*/
	
	
	public void add_member(final int item)
		{
			dialog = ProgressDialog.show(Add_sg_Member.this, "Adding member...","Please wait...");
			
			new Thread(new Runnable() 
				{
					@Override
					public void run()
						{
							SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
							String c_mem = global_storage.getString("MEMBER_NO", "");
						
							int creator_mem_no =  Integer.parseInt(c_mem);
							//System.out.println("creator_mem_no = "+memno);
							int memno = Integer.parseInt(temp_mmem_id.get(item));
							System.out.println("memno = "+memno);
							response = call.addSG_member(creator_mem_no, memno,"add_sg_member");
							
							try {
									JSONObject obj = new JSONObject(response);
									String success = obj.getString("success");
									if (success.equalsIgnoreCase("true"))
										{
											message = obj.getString("message");
										} 
									else 
										{
											message = obj.getString("message");
										}
									hanlerobj.sendEmptyMessage(4);
								}
							catch (JSONException e) 
								{
									e.printStackTrace();
									hanlerobj.sendEmptyMessage(3);
								}		
						}
				}).start();
		}
	
	
	
	
	Handler hanlerobj = new Handler() 
		{
			@SuppressWarnings("deprecation")
			public void handleMessage(Message msg)
				{
					dialog.dismiss();
		
					int what = msg.what;
					switch (what) 
					{
						case 1:
							/*CustomAdapter adapter = new CustomAdapter(Add_sg_Member.this);
							list.setAdapter(adapter);*/
							
						
						break;
			
						case 2:
							showAlertDialog(message);
						break;
						
						case 3:
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add_sg_Member.this);
							alertDialogBuilder.setMessage("Network Error. Please Try Again").setCancelable(true)
								.setPositiveButton("OK", new DialogInterface.OnClickListener() 
									{
										public void onClick(DialogInterface dialog, int id) 
											{
												finish();
											}
									});
							AlertDialog erroralertDialog = alertDialogBuilder.create();
							erroralertDialog.show();
						break;
						
						case 4:
							AlertDialog alertDialog;
				            alertDialog = new AlertDialog.Builder(Add_sg_Member.this).create();
				            //alertDialog.setTitle("Message");
				            alertDialog.setMessage(message);
				            alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
			
				                  public void onClick(DialogInterface dialog, int id) 
				                  {
				                    // finish(); 
				                  } 
				                }); 
				            alertDialog.show();
							
						break;
			
						default:
						break;
				}
			}
		};
	
	public void showAlertDialog(String message)
		{ 
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add_sg_Member.this);
			
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
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) 
		{				
			//stext = (AutoCompleteTextView) findViewById(R.id.search_text);

			if(position == 1)
				{
					String []at = new String[mf_name.size()];
					for(int i=0;i<mf_name.size();i++)
						{
							at[i] = mf_name.get(i);
							//System.out.println("i = "+at[i]);
						}
					ArrayAdapter<String> adpt = new ArrayAdapter(this,android.R.layout.simple_list_item_1,at);
					stext.setAdapter(adpt);
					stext.setThreshold(1);
				}
			if(position==2)
				{
					String []at = new String[msur_name.size()];
					for(int i=0;i<msur_name.size();i++)
						{
							at[i] = msur_name.get(i);
							//System.out.println("i = "+at[i]);
						}
					ArrayAdapter<String> adpt = new ArrayAdapter(this,android.R.layout.simple_list_item_1,at);
					stext.setAdapter(adpt);
					stext.setThreshold(1);
				}
			if(position==3)
				{
					/*String []at = new String[mobile.size()];
					for(int i=0;i<mobile.size();i++)
						{
							at[i] = mobile.get(i);
							//System.out.println("i = "+at[i]);
						}
					ArrayAdapter<String> adpt = new ArrayAdapter(this,android.R.layout.simple_list_item_1,at);
					stext.setAdapter(adpt);
					stext.setThreshold(1);*/
				}
		}


	@Override
	public void onNothingSelected(AdapterView<?> parent)
		{
			
		}
	
	
	
	class CustomAdapter extends BaseAdapter
	{
		Context context;
		
		public CustomAdapter(Context con) 
			{
				context = con;
			}

		@Override
		public int getCount()
			{
				return temp_m_name.size();
			}

		@Override
		public Object getItem(int position) 
			{
				return temp_m_name.get(position);
			}

		@Override
		public long getItemId(int position)
			{
				return position;
			}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
			{
				LayoutInflater lay = LayoutInflater.from(context);
				convertView = lay.inflate(R.layout.listitem, null);
				
				TextView name = (TextView) convertView.findViewById(R.id.name_txt);
				//TextView mem_id = (TextView) convertView.findViewById(R.id.mid_t);
				//TextView dob_tv = (TextView) convertView.findViewById(R.id.dob_t);
				//TextView street = (TextView) convertView.findViewById(R.id.street_t);
				//TextView suburbn = (TextView) convertView.findViewById(R.id.suburb_t);
				//TextView city = (TextView) convertView.findViewById(R.id.city_t);
				//TextView email_tv = (TextView) convertView.findViewById(R.id.email_t);
				//TextView phno = (TextView) convertView.findViewById(R.id.ph_no_t);
				
				name.setText(temp_m_name.get(position));
				//mem_id.setText(temp_mmem_id.get(position));
				//dob_tv.setText(temp_dob.get(position));
				//street.setText(temp_street_addr.get(position));
				//suburbn.setText(temp_suburb_addr.get(position));
				//city.setText(temp_city_addr.get(position));
				//email_tv.setText(temp_email.get(position));
				//phno.setText(temp_mobile.get(position));
				
				return convertView;
			}
	}

}
