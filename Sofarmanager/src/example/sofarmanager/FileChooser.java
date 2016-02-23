package example.sofarmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileChooser extends Activity {

	private File currentDir;
	private FileArrayAdapter adapter;
	ListView list;
	Button back_to_parent;
	Option o;
	String [] info;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.flie_chooser);
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int wwidth = displaymetrics.widthPixels;

		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.x = -100;
		params.height = height / 2;
		params.width = wwidth;
		params.y = -50;
		this.getWindow().setAttributes(params);
		
		list = (ListView) findViewById(R.id.list);
		info = new String[4];
		//currentDir = Environment.getExternalStorageDirectory();
		//currentDir.getName();
		currentDir = new File("/sdcard/");
		
		fill(currentDir);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,	int position, long arg3)
			{
				// TODO Auto-generated method stub
				o = adapter.getItem(position);
				if (o.getData().contains("Folder") || o.getData().equalsIgnoreCase("...")) {
					currentDir = new File(o.getPath());
					
					fill(currentDir);
				} else {
					onFileClick(o,position);
				}
			}
		});

	}
	
	
	private void fill(File f) {
		File[] dirs = f.listFiles();
		//this.setTitle("File Path: " + f.getPath());
		List<Option> dir = new ArrayList<Option>();
		List<Option> fls = new ArrayList<Option>();
		try {
			for (File ff : dirs) {
				long size = ff.length();				
				if (ff.isDirectory())
					{	
						long milliSeconds = ff.lastModified();
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					    // Create a calendar object that will convert the date and time value in milliseconds to date. 
					    Calendar calendar = Calendar.getInstance();
					    calendar.setTimeInMillis(milliSeconds);
					    String date = formatter.format(calendar.getTime());
					
					    double sz = 0.00;
						String ext = new String();
						if(size>=1024)
				    		{
				    			sz = size/1024;
				    			ext =  String.format("%.1f KB", sz);
				    			if(sz>=1024)
				    				{
				    					sz = sz/1024;
				    					ext = String.format("%.1f MB", sz);
				    					if(sz>=1024)
				    						{
				    							sz = sz/1024;
				    							ext = String.format("%.1f GB", sz);
				    						}
				    				}
				    		}else
				    		{
				    			sz = size;
				    			ext = String.format("%.1f Bytes", sz);
				    		}
							dir.add(new Option(ff.getName(), "Folder Size: " +ext+"      Modified: "+date , ff.getAbsolutePath()));
						}
				else {
					    
					    double sz = 0.00;
					    String ext = new String();
						long milliSeconds = ff.lastModified();
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					    // Create a calendar object that will convert the date and time value in milliseconds to date. 
					    Calendar calendar = Calendar.getInstance();
					    calendar.setTimeInMillis(milliSeconds);
					    String date = formatter.format(calendar.getTime());
					    if(size>=1024)
					    	{
					    		sz = size/1024;
					    		ext =  String.format("%.1f KB", sz);
					    		if(sz>=1024)
					    			{
					    				sz = sz/1024;
					    				ext = String.format("%.1f MB", sz);
					    				if(sz>=1024)
					    					{
					    						sz = sz/1024;
					    						ext = String.format("%.1f GB", sz);
					    					}
					    			}
					    	}else
					    	{
					    		sz = size;
					    		ext = String.format("%.1f Bytes", sz);
					    	}
					   
						fls.add(new Option(ff.getName(), "File Size: "+ ext +"      Modified: "+date , ff.getAbsolutePath()));
				}
			}
		} catch (Exception e) {

		}
		Collections.sort(dir);
		Collections.sort(fls);
		dir.addAll(fls);
		
		if (!f.getName().equalsIgnoreCase("sdcard"))
			dir.add(0, new Option("Parent Directory", "...", f.getParent()));
		adapter = new FileArrayAdapter(FileChooser.this, R.layout.file_view,dir);
		list.setAdapter(adapter);
		
	}

	private void onFileClick(Option o,int index)
		{
				File path = new File(o.getPath());
				long length = path.length();
				
				info[0] = String.valueOf(length);
				info[1] = o.getName();
				info[2] = o.getPath();
				//System.out.println("file size: "+file_sizes.size()+" bytes");
				if(info[1].endsWith(".txt"))
					{				
						File file = new File(info[1]);
						String text = new String();
						String base64 = new String();
						byte[] data;
						try {
					    		BufferedReader br = new BufferedReader(new FileReader(file));
					    		String line;
					    		while ((line = br.readLine()) != null) 
					    			{
					    				//text.append(line);
					    				//text.append('\n');
					    				text = text + line;
					    			}
							}catch (IOException e) {/*edit for error message*/}
						
						try {
		                     	data = text.getBytes("UTF-8");
		                     	base64 = Base64.encodeToString(data, Base64.DEFAULT);
		                     	Log.i("Base 64 ", base64);
							}catch (UnsupportedEncodingException e) 
									{  e.printStackTrace();   }

						info[3] = base64;
						//System.out.println("String:"+base64);
						Intent intent = new Intent();
						intent.putExtra("f_info",info);
						setResult(RESULT_OK, intent);        
						finish();
					}else{
						Toast.makeText(this, "Please Select A text file", Toast.LENGTH_SHORT).show();
					}
				//Toast.makeText(this, "File Clicked: " + o.getPath(), Toast.LENGTH_SHORT).show();
		}
}