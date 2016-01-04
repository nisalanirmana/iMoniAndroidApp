package lk.ac.mrt.ent.dialog.imoni.events;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

import java.util.List;

import lk.ac.mrt.ent.dialog.imoni.main.R;
import lk.ac.mrt.ent.dialog.imoni.main.Variables;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventsGridViewAdapter extends ArrayAdapter<Bundle> {
	private Context _Context;
	private List<Bundle> _Events;
  
	public EventsGridViewAdapter(Context context, int resource, int textViewResourceId, List<Bundle> objects) {
		super(context, resource, textViewResourceId, objects);
		this._Events = objects;
		this._Context = context;
	}
  
	public void ShowInfoDialog(int position) {
		Bundle event = (Bundle)_Events.get(position);
		new Builder(_Context, AlertDialog.THEME_HOLO_DARK)
    		.setTitle(event.getString(Variables.RACU_NAME))
    		.setMessage("Group: " + event.getString(Variables.RACU_GROUP) + "\n" 
    			+ "Alarm: " + event.getString(Variables.ALARM) + "\n" 
    			+ "Priority: " + event.getString(Variables.PRIORITY) + "\n"
                + "Event time: " + event.getString(Variables.EVENT_TIME) + "\n"
                    + "Type: " + event.getString(Variables.MAP_TYPE) + "\n"
    			+ "Acknowledged time: " + event.getString(Variables.ACK_TIME) + "\n")

    		.setPositiveButton("OK", new OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				dialog.dismiss();
    			}
    		})
    		.show();
	}
  
	@SuppressLint({ "InflateParams", "ViewHolder" })
	public View getView(int position, View convertView, ViewGroup parent) {
		View gridItem = ((LayoutInflater)_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.grid_item, null);
		TextView racu = (TextView)gridItem.findViewById(R.id.event_grid_item_RACU_name);
		TextView alarm = (TextView)gridItem.findViewById(R.id.event_grid_item_alarm_name);
        TextView typename = (TextView)gridItem.findViewById(R.id.event_grid_item_type_name);
		String priority = ((Bundle)_Events.get(position)).getString(Variables.PRIORITY);
		if (priority.equalsIgnoreCase("CRITICAL")) {
			gridItem.setBackgroundColor(Variables.CRITICAL);
		} else if (priority.equalsIgnoreCase("MAJOR")) {
	        gridItem.setBackgroundColor(Variables.MAJOR);
	    } else {
	        gridItem.setBackgroundColor(Variables.MINOR);
	    }
		
		gridItem.getBackground().setAlpha(250);
		racu.setTextColor(racu.getTextColors().withAlpha(250));
		alarm.setTextColor(alarm.getTextColors().withAlpha(250));
		if (((Bundle)_Events.get(position)).getBoolean(Variables.IS_ACKED)){
			gridItem.getBackground().setAlpha(127);
			racu.setTextColor(racu.getTextColors().withAlpha(127));
			alarm.setTextColor(alarm.getTextColors().withAlpha(127));
		}
		racu.setText(((Bundle)_Events.get(position)).getString(Variables.RACU_NAME));
		alarm.setText(((Bundle)_Events.get(position)).getString(Variables.ALARM));
        typename.setText(((Bundle)_Events.get(position)).getString(Variables.MAP_TYPE));
		gridItem.setTag(_Events.get(position));
		return gridItem;
    }
}