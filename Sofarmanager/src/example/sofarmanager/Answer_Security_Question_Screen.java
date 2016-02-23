package example.sofarmanager;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import example.sofarmanager.reuse.CallApi;
//import example.sofarmanager.reuse.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Answer_Security_Question_Screen extends Activity implements OnItemSelectedListener {
	Spinner question_sp;
	EditText ans;
	TextView send;
	Dialog dialog;
	CallApi call;
	String success, message,email,error_response,mem_id;
	ArrayList<String>ques;
	int q_id[];
	int selected_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.ans_security_question);
			
			ans = (EditText) findViewById(R.id.ans_et);
			send = (TextView) findViewById(R.id.send_ques);
			question_sp = (Spinner) findViewById(R.id.question);
			
			Intent intent = getIntent();
			q_id = intent.getIntArrayExtra("qid");
			ques = intent.getStringArrayListExtra("question");
			mem_id = new String();
			
			ArrayList<String> qu = new ArrayList<String>();
			qu.add("Select Question");
			qu.add(ques.get(0));
			qu.add(ques.get(1));
			
			ArrayAdapter<String>adpt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, qu);
			adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			question_sp.setAdapter(adpt);
			question_sp.setOnItemSelectedListener(this);
			
			
			send.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) 
						{
							 String ans_chk = ans.getText().toString();
							 int q_chk = question_sp.getSelectedItemPosition();
							 int count = 0;
							 
							 if(ans_chk.length()==0)
							 	{
								 	ans.setError("This Filed Can't Be Empty");
							 	}
							 else
							 	{
								 	count = count + 1;
							 	}
							 
							 if(q_chk==0)
							 	{
								 	showAlertDialog("Please Select a Question");
							 	}
							 else
							 	{
								 	count = count + 1;
							 	}
							 
							 if(count == 2)
							 	{
								 	send_question_ans();
							 	}

						}		
				});
		}
	
	
	public void send_question_ans()
	{
		dialog = ProgressDialog.show(Answer_Security_Question_Screen.this,"Verifying Answer...", "please wait...");
		new Thread(new Runnable() {			
			@Override
			public void run() 
				{
					call = new CallApi();
					String ans_send = ans.getText().toString(); 
					String response = call.send_question_ans(ans_send,selected_id);
					if(response.contains("java.net.UnknownHostException")||response.contains("java.net.ConnectException") || response.contains("java.net.SocketException"))
						{
							error_response = new String();
							error_response = response;
							handler.sendEmptyMessage(2);
						}
					else
						{
							try 
								{
									JSONObject obj = new JSONObject(response);
									success = obj.getString("success");
									if (success.equalsIgnoreCase("true"))
										{
											
											message=obj.getString("message");
											handler.sendEmptyMessage(1);
										}
									else
										{
											message=obj.getString("message");
											handler.sendEmptyMessage(0);
										}
								} 
							catch (JSONException e) 
								{
									e.printStackTrace();
									error_response = new String();
									error_response = e.toString();
									handler.sendEmptyMessage(3);
								}
						}
				}
		}).start();
	}
	
	//============================== Check for  valid email============================================
	
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
	
	Handler handler = new Handler() 
		{
			public void handleMessage(Message msg) 
				{
					int val = msg.what;
		
					dialog.dismiss();
					switch (val) 
					{
						case 0:
							showAlertDialog(message);
						break;
						
						case 1:
							mem_id = message;
							//showAlertDialog(message);
							Intent res_pass = new Intent(Answer_Security_Question_Screen.this,ResetPassword.class);
							res_pass.putExtra("mem_id", mem_id);
							System.out.println("memNo 5="+mem_id);
							startActivity(res_pass);
							
						break;
						
						case 2:
							showAlertDialog(error_response);
						break;
						
						case 3:
							showAlertDialog(error_response);
						break;
						
						default:
						break;
					}
				};
		};
	
	public void showAlertDialog(String message)
	{ 
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Answer_Security_Question_Screen.this);
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
	public void onBackPressed()
		{

			Intent open_log = new Intent(Answer_Security_Question_Screen.this,Login_Screen.class);
			startActivity(open_log);
		
			
			super.onBackPressed();
		}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) 
		{
			if(position==0)
				{
					selected_id = -1;
				}
			else  if(position == 1)
				{
					selected_id = q_id[position-1];
				}
			else if(position == 2)
				{
					selected_id = q_id[position-1];
				}
		}


	@Override
	public void onNothingSelected(AdapterView<?> parent) 
		{
			
		}
}
