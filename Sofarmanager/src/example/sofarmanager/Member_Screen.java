package example.sofarmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
//import example.sofarmanager.reuse.Util;


public class Member_Screen extends Activity implements OnClickListener
	{
		ImageView addmemberimg, emailimg, searchimg,view_member, back_btn,
				  mainmenu,member, attendence, task,scan;
		
		ImageView tab1,tab2,tab3,tab4;
	
		@Override
		protected void onCreate(Bundle savedInstanceState) 
			{
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				setContentView(R.layout.activity_member_screen);
				
				addmemberimg = (ImageView) findViewById(R.id.addsgmember);
				emailimg = (ImageView) findViewById(R.id.email);
				searchimg = (ImageView) findViewById(R.id.search);
				//back_btn = (ImageView) findViewById(R.id.back_btn);
						
				view_member = (ImageView) findViewById(R.id.view_all_sg_member);
				
				mainmenu=(ImageView)findViewById(R.id.tab1);
				member=(ImageView)findViewById(R.id.tab2);
				attendence=(ImageView)findViewById(R.id.tab3);
				task=(ImageView)findViewById(R.id.tab4);
				
				
				
				mainmenu.setOnClickListener(this);
				member.setOnClickListener(this);
				attendence.setOnClickListener(this);
				task.setOnClickListener(this);
				
		
				addmemberimg.setOnClickListener(this);
				emailimg.setOnClickListener(this);
				searchimg.setOnClickListener(this);

				view_member.setOnClickListener(this);
				
				/*back_btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent homeintent=new Intent(Member_Screen.this,Main_Screen.class);
						startActivity(homeintent);
						finish();
					}
				});*/
				
				
				
			}
	
		@Override
		public void onClick(View v) 
			{
				if (v == addmemberimg ) 
					{
						final Dialog choosedig = new Dialog(Member_Screen.this);
						choosedig.requestWindowFeature(Window.FEATURE_NO_TITLE);
						choosedig.setCanceledOnTouchOutside(false);
						choosedig.setContentView(R.layout.dialoglayout);
						
						TextView one = (TextView) choosedig.findViewById(R.id.one);
						TextView two = (TextView) choosedig.findViewById(R.id.two);
						TextView three=(TextView) choosedig.findViewById(R.id.three);
						
						one.setOnClickListener(new OnClickListener() 
							{
								@Override
								public void onClick(View v) 
									{
										Intent next = new Intent(Member_Screen.this,Add_sg_Member.class);
										startActivity(next);
										choosedig.dismiss();
										
									}
							});
						
						two.setOnClickListener(new OnClickListener()
							{
								@Override
								public void onClick(View v) 
									{
										Intent next = new Intent(Member_Screen.this,Add_sg_Visitor.class);
										startActivity(next);
										choosedig.dismiss();
										
									}
							});
						
						three.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
								choosedig.dismiss();
								
							}
						});
						
						choosedig.show();
					} 
				
				else if (v == emailimg ) 
					{
						Intent next = new Intent(Member_Screen.this,Email.class);
						startActivity(next);
						
					} 
				else if (v == searchimg)
					{
						Intent next = new Intent(Member_Screen.this,Search_Screen.class);
						startActivity(next);
					
					} 
				
				else if(v == view_member)
					{
						Intent next = new Intent(Member_Screen.this,View_Members_Screen.class);
						startActivity(next);
						
					}
				
				else if(v.equals(mainmenu)){
					
					Intent member = new Intent(Member_Screen.this, Main_Screen.class);
					startActivity(member);
					
				}
				
				else if (v.equals(member)) 
				{
					Intent member = new Intent(Member_Screen.this, Member_Screen.class);
					startActivity(member);
					
				} 
				
				else if (v.equals(attendence)) 
				{
					
					Intent member = new Intent(Member_Screen.this, Attendence.class);
					startActivity(member);	
				
					
				} 
				
				else if (v.equals(task) ) 
				{
					Intent member = new Intent(Member_Screen.this, Task_Screen.class);
					startActivity(member);
					
				}
		
			}
}
