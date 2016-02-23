package example.sofarmanager;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class Splash_Screen extends Activity {
	
	
	ImageView login,register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash__screen);
		
		
		File sf = new File("/data/data/example.sofarmanager/shared_prefs/global_data.xml");
		
		if (sf.exists())
			{
				Log.d("TAG", "SharedPreferences global_data : exist");
				SharedPreferences global_storage =  getSharedPreferences("global_data",MODE_PRIVATE);
				String Login = global_storage.getString("LOGIN", "");
				
				System.out.println("available");
				if(Login.equals("true"))
					{
						Intent intent = new Intent(Splash_Screen.this,Main_Screen.class);
						startActivity(intent);
						
					}
				else
					{
						System.out.println("no login before");
						login = (ImageView)findViewById(R.id.login);
						register = (ImageView)findViewById(R.id.register);
						
						login.setOnClickListener(new OnClickListener() 
							{
								@Override
								public void onClick(View v) 
									{
										Intent loginScreenintent = new Intent(Splash_Screen.this,Login_Screen.class);
										startActivity(loginScreenintent);
										
									}
							});
						
						register.setOnClickListener(new OnClickListener() 
							{
								@Override
								public void onClick(View v)
									{
										Intent registerScreenintent = new Intent(Splash_Screen.this,NewUserRegistartion.class);
										startActivity(registerScreenintent);
										
									}
							});		
					}
			}
		else
			{
				Log.d("TAG", "Setup default preferences");
				SharedPreferences global_storage =  getSharedPreferences("global_data",MODE_PRIVATE);
				System.out.println("new shared pref");
				Editor edit = global_storage.edit();
				edit.putString("SGID", "");
				edit.putString("MAILID","");
				edit.putString("MEMBER_NO","");
				edit.putString("HIGHEST_ROLL","");
				edit.putString("LOGIN","false");
				edit.commit();
				
				login = (ImageView)findViewById(R.id.login);
				register = (ImageView)findViewById(R.id.register);
				
				login.setOnClickListener(new OnClickListener() 
					{
						@Override
						public void onClick(View v) 
							{
								Intent loginScreenintent = new Intent(Splash_Screen.this,Login_Screen.class);
								startActivity(loginScreenintent);
								
							}
					});
				
				register.setOnClickListener(new OnClickListener() 
					{
						@Override
						public void onClick(View v)
							{
								Intent registerScreenintent = new Intent(Splash_Screen.this,NewUserRegistartion.class);
								startActivity(registerScreenintent);
								
							}
					});	
				
			}
		
		
		
		
	
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash__screen, menu);
		return true;
	}

}
