package example.sofarmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Out_Standing_CustomList extends ArrayAdapter<String> {

	private final Activity context;
	// private final String[] web;
	// private final Integer[] imageId;
	private final ArrayList<String> subject, description, due_date, priority,
			status;

	public Out_Standing_CustomList(Activity context, ArrayList<String> subject,
			ArrayList<String> description, ArrayList<String> due_date,
			ArrayList<String> priority, ArrayList<String> status) {
		super(context, R.layout.outs_standing_list, subject);
		this.context = context;
		// this.web = web;
		// this.imageId = imageId;
		this.subject = subject;
		this.description = description;
		this.due_date = due_date;
		this.priority = priority;
		this.status = status;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();

		View rowView = inflater
				.inflate(R.layout.outs_standing_list, null, true);

		TextView subject_tv = (TextView) rowView.findViewById(R.id.sub_txt);
		TextView description_tv = (TextView) rowView
				.findViewById(R.id.desc_details);
		TextView due_date_tv = (TextView) rowView
				.findViewById(R.id.duedt_details);
		TextView priority_tv = (TextView) rowView.findViewById(R.id.prior_text);
		TextView status_tv = (TextView) rowView.findViewById(R.id.stat_txt);

		// ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

		subject_tv.setText(subject.get(position));
		description_tv.setText(description.get(position));

		due_date_tv.setText(due_date.get(position));
		priority_tv.setText(priority.get(position));
		status_tv.setText(status.get(position));
		// imageView.setImageResource(imageId[position]);

		return rowView;
	}
}