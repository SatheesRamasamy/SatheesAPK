package example.sofarmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class Task_Screen extends Activity {
	
	ImageView add_task_btn, inbox, outstanding_task;
	ImageView tab1,tab2,tab3,tab4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_task_screen);
		
		add_task_btn = (ImageView) findViewById(R.id.addtask);
		inbox = (ImageView) findViewById(R.id.inbox_img);
		outstanding_task = (ImageView) findViewById(R.id.outstanding_task);
		
		View.OnClickListener all_click_listener = new View.OnClickListener() {
			public void onClick(View v) {
				int Id = v.getId();
				
				switch (Id) {
				
				case R.id.addtask:
					Intent open_activity = new Intent(Task_Screen.this,AddTask.class);
					startActivity(open_activity);
				
					break;
				case R.id.inbox_img:
					Intent open_inbox = new Intent(Task_Screen.this,Inbox.class);
					startActivity(open_inbox);
					
					break;
				case R.id.outstanding_task:
					Intent open_outtask = new Intent(Task_Screen.this,Out_Standing_Task.class);
					startActivity(open_outtask);
				
					break;
				
					
				default:
					break;
				}

			}
		};

		add_task_btn.setOnClickListener(all_click_listener);
		inbox.setOnClickListener(all_click_listener);
		outstanding_task.setOnClickListener(all_click_listener);
		
		tab1=(ImageView)findViewById(R.id.tab1);
		tab2=(ImageView)findViewById(R.id.tab2);
		tab3=(ImageView)findViewById(R.id.tab3);
		tab4=(ImageView)findViewById(R.id.tab4);
		
		tab1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Task_Screen.this,Main_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Task_Screen.this,Member_Screen.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
			
				Intent mainintent=new Intent(Task_Screen.this,Attendence.class);
				startActivity(mainintent);
				finish();
				
			}
		});
		
		tab4.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
	
				Intent mainintent=new Intent(Task_Screen.this,Task_Screen.class);
				startActivity(mainintent);
				finish();
		
			}
		});

	}

}
