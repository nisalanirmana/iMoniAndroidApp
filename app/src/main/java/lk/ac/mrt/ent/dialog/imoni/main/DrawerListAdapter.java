package lk.ac.mrt.ent.dialog.imoni.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

public class DrawerListAdapter extends BaseExpandableListAdapter {
  
	private Context _Context;
	private HashMap<String, List<String>> _GroupChildMap;
	private List<String> _Groups;
  
	public DrawerListAdapter(Context context, List<String> groups, HashMap<String, List<String>> groupChildMap) {
		this._Context = context;
		groups.add(0, "All RACUs");
		groupChildMap.put("All RACUs", new ArrayList<String>());
		this._Groups = groups;
		this._GroupChildMap = groupChildMap;
	}
  
	public Object getChild(int groupPosition, int childPosition) {
		return ((List<String>)_GroupChildMap.get(_Groups.get(groupPosition))).get(childPosition);
	}
  
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition | groupPosition << 32;
	}
  
	@SuppressLint("InflateParams")
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = ((LayoutInflater)_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drawer_child_item, null);
		}
		((TextView)convertView.findViewById(R.id.drawer_child_item_text)).setText((String)getChild(groupPosition, childPosition));
		return convertView;
	}
  
	public int getChildrenCount(int groupPosition) {
		return ((List<String>)_GroupChildMap.get(_Groups.get(groupPosition))).size();
	}
  
	public Object getGroup(int groupPosition) {
		return _Groups.get(groupPosition);
	}
  
	public int getGroupCount() {
		return _Groups.size();
	}
  
	public long getGroupId(int groupPosition) {
		return 0xFFFFFFFF | groupPosition << 32;
	}
  
	@SuppressLint("InflateParams")
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = ((LayoutInflater)_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drawer_group_item, null);
		}
		((TextView)convertView.findViewById(R.id.drawer_group_item_text)).setText((String)getGroup(groupPosition));
		return convertView;
	}
  
	public boolean hasStableIds() {
		return false;
	}
  
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}