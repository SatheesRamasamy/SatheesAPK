package example.sofarmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class report_custom_list extends ArrayAdapter<String>{
	
	private final Activity context;
	private final ArrayList<String> names,absent;
	
	public report_custom_list(Activity context,ArrayList<String> names,ArrayList<String> absent)
		{
			super(context, R.layout.report_list_design, names);
			this.context = context;
			this.names = names;
			this.absent = absent;
		}
	@Override
	public View getView(int position, View view, ViewGroup parent) 
		{
			LayoutInflater inflater = context.getLayoutInflater();
			
			View rowView= inflater.inflate(R.layout.report_list_design, null, true);
			
			TextView names_tv = (TextView) rowView.findViewById(R.id.names);
			TextView absent_tv = (TextView) rowView.findViewById(R.id.absent);			
			names_tv.setText(names.get(position));
			absent_tv.setText(absent.get(position)+"%");
			
			return rowView;
		}
}