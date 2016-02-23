package example.sofarmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class Out_Standing_CustomExpandbleList extends BaseExpandableListAdapter 
	{
		private Context _context;
		private List<String> _listDataHeader; // header titles
		// child data in format of header title, child title
		private HashMap<String, List<String>> _listDataChild;
		int lastExpandedGroupPosition;
		ArrayList<Out_satnd_parent> data;
		//ExpandableListAdapter listAdapter;
		//ExpandableListView expListView;
		List<String> listDataHeader;

		/*public Out_Standing_CustomExpandbleList(Context context,List<String> listDataHeader,
				HashMap<String, List<String>> listChildData,
				ArrayList<String> sub) 
			{
				this._context = context;
				this._listDataHeader = listDataHeader;
				this._listDataChild = listChildData;
			}

		public Out_Standing_CustomExpandbleList(Context context,
				List<String> listDataHeader, ArrayList<String> subject,
				ArrayList<String> creator, ArrayList<String> date,
				ArrayList<String> duedate, ArrayList<String> description,
				ArrayList<String> status) 
			{
				this._context = context;
				this._listDataHeader = listDataHeader;
				this.msubject = subject;
				this.mdescription = description;
				this.mstatus = status;
				this.mduedate = duedate;
			}

*/		public Out_Standing_CustomExpandbleList(Context con, ArrayList<Out_satnd_parent> dataforlist) 
			{
				// TODO Auto-generated constructor stub
				this._context = con;
				this.data = dataforlist;
			}

		@Override
		public Object getChild(int groupPosition, int childPosititon) 
			{
				// TODO Auto-generated method stub
				return this.data.get(groupPosition);
			}

		@Override
		public long getChildId(int groupPosition, int childPosition) 
			{
				// TODO Auto-generated method stub
				return childPosition;
			}

		@Override
		public View getChildView(int groupPosition, final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) 
			{

				if (convertView == null) 
					{
						LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						convertView = infalInflater.inflate(R.layout.outs_standing_exp_child_list,null);
						
						TextView description = (TextView) convertView
								.findViewById(R.id.desc_details);
						
						TextView due_date = (TextView) convertView
								.findViewById(R.id.duedt_details);

						TextView priority = (TextView) convertView
								.findViewById(R.id.prior_text);
						
						TextView status = (TextView) convertView
								.findViewById(R.id.stat_txt);
						
						
						System.out.println("Due Date in adapter...."
								+ data.get(groupPosition).getChilddata()
										.get(childPosition).getDuedate());
						description.setText("new data");

						//description.setText(data.get(groupPosition).getChilddata().get(childPosition).getDescription());
						due_date.setText(data.get(groupPosition).getChilddata().get(childPosition).getDuedate());
						priority.setText(data.get(groupPosition).getChilddata().get(childPosition).getPriority());
						status.setText(data.get(groupPosition).getChilddata().get(childPosition).getStatus());

					}
				
				TextView description = (TextView) convertView
						.findViewById(R.id.desc_details);
				
				TextView due_date = (TextView) convertView
						.findViewById(R.id.duedt_details);

				TextView priority = (TextView) convertView
						.findViewById(R.id.prior_text);
				
				TextView status = (TextView) convertView
						.findViewById(R.id.stat_txt);
				
				
				
				description.setText(data.get(groupPosition).getChilddata().get(childPosition).getDescription());
				due_date.setText(data.get(groupPosition).getChilddata().get(childPosition).getDuedate());
				priority.setText(data.get(groupPosition).getChilddata().get(childPosition).getPriority());
				status.setText(data.get(groupPosition).getChilddata().get(childPosition).getStatus());
				return convertView;
			}

			@Override
			public int getChildrenCount(int groupPosition) 
				{
					return data.get(groupPosition).getChilddata().size();
				}
			
			@Override
			public Object getGroup(int groupPosition) 
				{
					return this.data.get(groupPosition);
				}

			@Override
			public int getGroupCount() 
				{
					return this.data.size();
				}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public void onGroupExpanded(int groupPosition) {
			// collapse the old expanded group, if not the same
			// as new group to expand
			if (groupPosition != lastExpandedGroupPosition) {
				//expListView.collapseGroup(lastExpandedGroupPosition);
			}
			super.onGroupExpanded(groupPosition);
			lastExpandedGroupPosition = groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// String headerTitle = (String) getGroup(groupPosition);

			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.outs_standing_exp_header_list,
						null);
				
				
				TextView header = (TextView) convertView
						.findViewById(R.id.sub_description);
				
				

				header.setText(data.get(groupPosition).getSubject());
				
			}

			TextView header = (TextView) convertView
					.findViewById(R.id.sub_description);
			
			

			header.setText(data.get(groupPosition).getSubject());	
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
}