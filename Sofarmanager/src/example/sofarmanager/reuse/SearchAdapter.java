package example.sofarmanager.reuse;

import java.util.ArrayList;

import example.sofarmanager.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {
	ArrayList<String> Fistname, Surname, mobileno, memberno;
	Context context;
	LayoutInflater inflate;

	public SearchAdapter(Context con, ArrayList<String> FName,
			ArrayList<String> SName, ArrayList<String> Mob,
			ArrayList<String> memno) {
		// TODO Auto-generated constructor stub

		context = con;
		Fistname = FName;
		Surname = SName;
		mobileno = Mob;
		memberno = memno;
		inflate = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Fistname.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return Fistname.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = inflate.inflate(R.layout.searchlistitem, null);
		}

		TextView firstname = (TextView) convertView.findViewById(R.id.fname);
		TextView surname = (TextView) convertView.findViewById(R.id.sname);
		TextView mobno = (TextView) convertView.findViewById(R.id.mobno);
		TextView memno = (TextView) convertView.findViewById(R.id.memno);

		firstname.setText(Fistname.get(position));
		surname.setText(Surname.get(position));
		mobno.setText(mobileno.get(position));
		memno.setText(memberno.get(position));

		return convertView;
	}

}
