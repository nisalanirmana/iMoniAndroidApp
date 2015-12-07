package lk.ac.mrt.ent.dialog.imoni.racu;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import lk.ac.mrt.ent.dialog.imoni.main.R;
import lk.ac.mrt.ent.dialog.imoni.main.Variables;

public class DigitalInput extends PreferenceFragment{
	
	int id;
	String alarmName;
	String healthyText;
	String alarmText;
	String Priority;
	String normallyOpen;
	
	public DigitalInput(){
		this.id = -1;
		this.alarmName = "";
		this.healthyText = "";
		this.alarmText = "";
		this.Priority = "";
		this.normallyOpen = "";
	}
	
	public DigitalInput(int id, String alarmName, String healthyText, String alarmText, String Priority, String normallyOpen){
		this.id = id;
		this.alarmName = alarmName;
		this.healthyText = healthyText;
		this.alarmText = alarmText;
		this.Priority = Priority;
		this.normallyOpen = normallyOpen;
	}

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		addPreferencesFromResource(R.xml.digitalinput);
	}

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		EditTextPreference alarmNamePref = (EditTextPreference) getPreferenceScreen().getPreference(0);
		EditTextPreference healthyTextPref = (EditTextPreference) getPreferenceScreen().getPreference(1);
		EditTextPreference alarmTextPref = (EditTextPreference) getPreferenceScreen().getPreference(2);
		EditTextPreference priorityPref = (EditTextPreference) getPreferenceScreen().getPreference(3);
		EditTextPreference normallyOpenPref = (EditTextPreference) getPreferenceScreen().getPreference(4);
		
		
		alarmNamePref.setText(alarmName);
		healthyTextPref.setText(healthyText);
		alarmTextPref.setText(alarmText);
		priorityPref.setText(Priority);
		normallyOpenPref.setText(normallyOpen);
		
		alarmNamePref.setSummary("Set alarm name. Current name is " + alarmName);
		healthyTextPref.setSummary("Set healthy text. Current text is " + healthyText);
		alarmTextPref.setSummary("Set alarm text. Current text is " + alarmText);
		priorityPref.setSummary("Set priority. Currently " + Priority);
		normallyOpenPref.setSummary("Set contact type. Normally open status is set to " + normallyOpen);
		
		alarmNamePref.setKey("digital" + String.valueOf(id) + Variables.CONFIG_INPUT_NAME);
		healthyTextPref.setKey("digital" + String.valueOf(id) + Variables.CONFIG_INPUT_HEALTHY_TEXT);
		alarmTextPref.setKey("digital" + String.valueOf(id) + Variables.CONFIG_INPUT_ALARM_TEXT);
		priorityPref.setKey("digital" + String.valueOf(id) + Variables.CONFIG_INPUT_PRIORITY);
		normallyOpenPref.setKey("digital" + String.valueOf(id) + Variables.CONFIG_INPUT_NO);
		
		return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
	}
	

	
	
}
