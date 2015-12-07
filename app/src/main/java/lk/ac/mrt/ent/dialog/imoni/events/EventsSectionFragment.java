package lk.ac.mrt.ent.dialog.imoni.events;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

import java.util.ArrayList;

import lk.ac.mrt.ent.dialog.imoni.main.R;
import lk.ac.mrt.ent.dialog.imoni.main.Variables;
import lk.ac.mrt.ent.dialog.imoni.web.ConnectWS;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class EventsSectionFragment extends Fragment {
  
	public static EventsGridViewAdapter mGridAdapter;
  
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		GridView gridView = (GridView)inflater.inflate(R.layout.fragment_events, container, false);
		mGridAdapter = new EventsGridViewAdapter(getActivity(), R.layout.grid_item, 2131427345, new ArrayList<Bundle>());
		gridView.setAdapter(mGridAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				EventsSectionFragment.mGridAdapter.ShowInfoDialog(position);
			}
		});
		ConnectWS.GetEventsList(Variables.mComPrefix, Variables.mSelectedRACUGroup, Variables.mSelectedRACU);
		return gridView;
	}
	
}