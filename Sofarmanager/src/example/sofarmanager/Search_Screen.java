package example.sofarmanager;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import example.sofarmanager.reuse.CallApi;
import example.sofarmanager.reuse.SearchAdapter;
//import example.sofarmanager.reuse.Util;

public class Search_Screen extends Activity {
	Spinner filter;
	EditText searchtext;
	ListView list;
	TextView search;
	CallApi call;
	Dialog dialog;
	String[] parameter;
	String success, message,res,texttosearch;
	ArrayList<String> Fname, Sname, MobNo, MemNo;
	RelativeLayout search_lay;
	int i;
	
	ImageView tab1,tab2,tab3,tab4,backbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_screen);
		
		filter = (Spinner) findViewById(R.id.searchfilter);
		searchtext = (EditText) findViewById(R.id.stext);
		list = (ListView) findViewById(R.id.searchresultlist);
		search = (TextView) findViewById(R.id.searchimg);
		search_lay = (RelativeLayout) findViewById(R.id.serach_btn);
		call = new CallApi();
		parameter = new String[] { "first_name", "surname", "mobile" };
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.search_array,android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		filter.setAdapter(adapter);

		
		search.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) 
					{
						i = filter.getSelectedItemPosition();
						texttosearch = searchtext.getText().toString();
						
						if (i == 0) 
							{
								Toast.makeText(Search_Screen.this,"Please select any category", Toast.LENGTH_SHORT).show();
							} 
						else if (texttosearch.length() == 0) 
							{
								Toast.makeText(Search_Screen.this,"Please enter any keyword to search",Toast.LENGTH_SHORT).show();

							} 
						else 
							{
								search_member();
							}
					}
			});
		
		search_lay.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) 
					{
						i = filter.getSelectedItemPosition();
						texttosearch = searchtext.getText().toString();
						
						if (i == 0) 
							{
								Toast.makeText(Search_Screen.this,"Please select any category", Toast.LENGTH_SHORT).show();
							} 
						else if (texttosearch.length() == 0) 
							{
								Toast.makeText(Search_Screen.this,"Please enter any keyword to search",Toast.LENGTH_SHORT).show();
	
							} 
						else 
							{
								search_member();
							}
					}
			});
		
		
		tab1=(ImageView)findViewById(R.id.tab1);
		tab2=(ImageView)findViewById(R.id.tab2);
		tab3=(ImageView)findViewById(R.id.tab3);
		tab4=(ImageView)findViewById(R.id.tab4);
		
		tab1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Search_Screen.this,Main_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Search_Screen.this,Member_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Search_Screen.this,Attendence.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab4.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
	
				Intent mainintent=new Intent(Search_Screen.this,Task_Screen.class);
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
	
	
	public void search_member()
		{
			dialog = ProgressDialog.show(Search_Screen.this,"Searching member...", "Please wait...");
			res = new String();
			new Thread(new Runnable() 
				{
					@Override
					public void run() 
						{
							res = call.Search_SG_member(texttosearch,parameter[i - 1], "member_search");
							if(res.contains("java.net.UnknownHostException")||res.contains("java.net.ConnectException") || res.contains("java.net.SocketException"))
								{
									handler.sendEmptyMessage(2);
								}
							else
								{
									try {
											JSONObject jsonj = new JSONObject(res);
											success = jsonj.getString("success");
											if (success.equalsIgnoreCase("true"))
												{
													Fname = new ArrayList<String>();
													Sname = new ArrayList<String>();
													MobNo = new ArrayList<String>();
													MemNo = new ArrayList<String>();
													
													JSONArray array = new JSONArray();
													array = jsonj.getJSONArray("message");
													
													for (int i = 0; i < array.length(); i++) 
														{
															Fname.add(array.getJSONObject(i).getString("FirstName").toString());
															Sname.add(array.getJSONObject(i).getString("Surname").toString());
															MobNo.add(array.getJSONObject(i).getString("Mobile").toString());
															MemNo.add(array.getJSONObject(i).getString("mem_no").toString());
														}
													handler.sendEmptyMessage(0);
												}
											else 
												{
													handler.sendEmptyMessage(1);
												}
										} 
									catch (JSONException e) 
										{
											e.printStackTrace();
										}
								}
							System.out.println(">>>>>>>>>>>" + res);
						}
				}).start();
		}

	Handler handler = new Handler() 
		{
			public void handleMessage(android.os.Message msg)
				{
					int i = msg.what;
					dialog.dismiss();
					
					switch (i) 
						{
							case 0:
								SearchAdapter adapter = new SearchAdapter(Search_Screen.this,Fname, Sname, MobNo, MemNo);
								list.setAdapter(adapter);
							break;
				
							case 1:
								showAlertDialog("There is no result");
							break;
							
							case 2:
								showAlertDialog(res);
							break;
							
							default:
							
							break;
						}
				};
		};

	
	public void showAlertDialog(String message)
	{ 
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Search_Screen.this);
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
}
