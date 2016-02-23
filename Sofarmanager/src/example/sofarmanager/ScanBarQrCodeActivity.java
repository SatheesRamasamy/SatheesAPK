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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import example.sofarmanager.R;
import example.sofarmanager.reuse.CallApi;

public class ScanBarQrCodeActivity extends Activity
	{
	
	
		ImageView tab1,tab2,tab3,tab4,backbtn;
		
		Button scan,cancle,send;//,email;
		TextView tx;
		String	barcodeValue,success, message,error_response;
		int noofsize = 6;
		Dialog dialog;
		CallApi call;
		@Override
		public void onCreate(Bundle savedInstanceState) 
			{   
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				setContentView(R.layout.scan);   
				
				/*p = (ImageView)findViewById(R.id.iv0);
				p1 = (ImageView)findViewById(R.id.iv1);
				p2 = (ImageView)findViewById(R.id.iv2);
				p3 = (ImageView)findViewById(R.id.iv3);
				p4 = (ImageView)findViewById(R.id.iv4);
				p5 = (ImageView)findViewById(R.id.iv5);
				p6 = (ImageView)findViewById(R.id.iv6);
				p7 = (ImageView)findViewById(R.id.iv7);
				*/
				final int[] imageId = {
										R.drawable.bigbarcode,R.drawable.bigqr,R.drawable.demono,
										R.drawable.demoyes,R.drawable.scanexample,R.drawable.scanfromphone,R.drawable.scanexample
									  };
				
				//final ImageView[] controleId = { p,p1,p2,p3,p4,p5,p6};
				
				ViewPagerAdapter adapter = new ViewPagerAdapter(ScanBarQrCodeActivity.this,imageId,noofsize);
				ViewPager myPager = (ViewPager) findViewById(R.id.view_pager);
				myPager.setAdapter(adapter);
				myPager.setCurrentItem(0);
				
				scan=(Button)findViewById(R.id.scan_but);
				send = (Button)findViewById(R.id.send_but);
				cancle=(Button)findViewById(R.id.cancle_but);
				//email = (Button) findViewById(R.id.email_but);
				tx=(TextView)findViewById(R.id.code_text);
				
				
				
				
				scan.setOnClickListener(new OnClickListener() 
					{
						public void onClick(View v) 
							{
								Intent intent = new Intent("com.google.zxing.client.android.SCAN");
								// intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
								startActivityForResult(intent, 0);
							}
					});
					
				/*email.setOnClickListener(new OnClickListener() 
					{
						public void onClick(View v) 
							{
								Intent intent = null;
								intent = new Intent(Intent.ACTION_SEND);
								intent.setType("plain/text");
								//intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "android.prasath@gmail.com" });
								intent.putExtra(android.content.Intent.EXTRA_SUBJECT, Html.fromHtml("<p>QR code scanner</p>"));
								intent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>"+barcodeValue+"</p>"));
								startActivityForResult(Intent.createChooser(intent, ""),1);	
							}
					});*/
				   
				cancle.setOnClickListener(new OnClickListener() 
					{	
						public void onClick(View v) 
							{
								finish();
							}
					});	
				
				
				
				send.setOnClickListener(new OnClickListener() 
					{	
						public void onClick(View v) 
							{
								verify_send_code();
							}
					});	
				
				
				tab1=(ImageView)findViewById(R.id.tab1);
				tab2=(ImageView)findViewById(R.id.tab2);
				tab3=(ImageView)findViewById(R.id.tab3);
				tab4=(ImageView)findViewById(R.id.tab4);
				
				tab1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(ScanBarQrCodeActivity.this,Main_Screen.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(ScanBarQrCodeActivity.this,Member_Screen.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab3.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(View v) {
					
						Intent mainintent=new Intent(ScanBarQrCodeActivity.this,Attendence.class);
						startActivity(mainintent);
						finish();
						
					}
				});
				
				tab4.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(View v) {
			
						Intent mainintent=new Intent(ScanBarQrCodeActivity.this,Task_Screen.class);
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
		
		public void onActivityResult(int requestCode, int resultCode, Intent intent)
			{
				if (requestCode == 0)
					{
						
						try{
								String contents = intent.getStringExtra("SCAN_RESULT");
								String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
								barcodeValue=contents;
								tx.setText(barcodeValue);
							}
						catch(Exception e)
							{
								tx.setText("Not Scanned");
							}
					}
				else if (resultCode == 1)
					{
						finish();
					}
				else if (resultCode == RESULT_CANCELED)
					{
						// Handle cancel
					}
			} 
		
		
		public void verify_send_code()
			{
				dialog = ProgressDialog.show(ScanBarQrCodeActivity.this,"Verifying Decoded Value...", "please wait...");
				new Thread(new Runnable() {
	
					@Override
					public void run() 
						{
							call = new CallApi();
							String android_id = Secure.getString(getBaseContext().getContentResolver(),Secure.ANDROID_ID); 
							SharedPreferences global_storage =  getSharedPreferences("global_data",Context.MODE_PRIVATE);
							int mem_no = Integer.parseInt(global_storage.getString("MEMBER_NO", "1"));
							barcodeValue = "123abc";
							String response = call.verify_scanned_code(barcodeValue,android_id,mem_no);
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
		
		Handler handler = new Handler() 
			{
				public void handleMessage(Message msg) 
					{
						int val = msg.what;
			
						dialog.dismiss();
						switch (val) 
							{
								case 0:
									AlertDialog.Builder alebl = new AlertDialog.Builder(ScanBarQrCodeActivity.this);
									alebl.setMessage(message).setCancelable(true)
									.setPositiveButton("OK",
													new DialogInterface.OnClickListener() 
														{
															public void onClick(DialogInterface dialog,int id) 
																{
																	finish();
																}
														});
									AlertDialog alertDia = alebl.create();
									alertDia.show();
								break;
								
								case 1:
									AlertDialog.Builder aleb = new AlertDialog.Builder(ScanBarQrCodeActivity.this);
									aleb.setMessage(message).setCancelable(true)
									.setPositiveButton("OK",
													new DialogInterface.OnClickListener() 
														{
															public void onClick(DialogInterface dialog,int id) 
																{
																	//finish();
																}
														});
									AlertDialog alert = aleb.create();
									alert.show();
								break;
								
								case 2:
									AlertDialog.Builder alebl1 = new AlertDialog.Builder(ScanBarQrCodeActivity.this);
									alebl1.setMessage(error_response).setCancelable(true)
									.setPositiveButton("OK",
													new DialogInterface.OnClickListener() 
														{
															public void onClick(DialogInterface dialog,int id) 
																{
																	//finish();
																}
														});
									AlertDialog alertDia1 = alebl1.create();
									alertDia1.show();
									
								break;
								
								case 3:
									
									AlertDialog.Builder ale = new AlertDialog.Builder(ScanBarQrCodeActivity.this);
									ale.setMessage(error_response).setCancelable(true)
									.setPositiveButton("OK",
													new DialogInterface.OnClickListener() 
														{
															public void onClick(DialogInterface dialog,int id) 
																{
																	//finish();
																}
														});
									AlertDialog alertD = ale.create();
									alertD.show();
		
								break;
								
								default:
								break;
							}
					}		
			};
}
