package example.sofarmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;



public class Attendence extends Activity implements OnClickListener {
	ImageView captureimg, reportimg;
	ImageView tab1,tab2,tab3,tab4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_attendence_screen);
		
		
		captureimg = (ImageView) findViewById(R.id.captureimg);
		reportimg = (ImageView) findViewById(R.id.reportimg);
		captureimg.setOnClickListener(this);
		reportimg.setOnClickListener(this);
		
		
		tab1=(ImageView)findViewById(R.id.tab1);
		tab2=(ImageView)findViewById(R.id.tab2);
		tab3=(ImageView)findViewById(R.id.tab3);
		tab4=(ImageView)findViewById(R.id.tab4);
		
		tab1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Attendence.this,Main_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Attendence.this,Member_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Attendence.this,Attendence.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab4.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
	
				Intent mainintent=new Intent(Attendence.this,Task_Screen.class);
				startActivity(mainintent);
				finish();
		
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		if (v.equals(captureimg)) 
			{
				Intent member = new Intent(Attendence.this, AttendanceCapture.class);
				startActivity(member);
				
			} 
		else if (v.equals(reportimg)) 
			{
				Intent member = new Intent(Attendence.this, Report.class);
				startActivity(member);
				
			} 
		
	}
}
