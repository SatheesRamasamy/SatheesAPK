package example.sofarmanager.reuse;

import java.util.ArrayList;
import java.util.List;

import example.sofarmanager.Country;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyCustomAdapter extends ArrayAdapter<Country> {
	private static ArrayList<Country> countryList;

	public MyCustomAdapter(Context context, int resource,
			int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, countryList);
		this.countryList = new ArrayList<Country>();
		this.countryList.addAll(countryList);
		// TODO Auto-generated constructor stub
	}

}
