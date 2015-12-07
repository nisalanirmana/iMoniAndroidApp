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

public class AnalogInput extends PreferenceFragment{
	
	int id;
	String alarmName;
	String upperThreshold;
	String lowerThreshold;
	String UTText;
	String LTText;
	String Priority;
	
	public AnalogInput(){
		this.id = -1;
		this.alarmName = "";
		this.upperThreshold = "";
		this.lowerThreshold = "";
		this.UTText = "";
		this.LTText = "";
		this.Priority = "";
	}
	
	public AnalogInput(int id, String alarmName, String upperThreshold, String lowerThreshold, String UTText, String LTText, String Priority){
		this.id = id;
		this.alarmName = alarmName;
		this.upperThreshold = upperThreshold;
		this.lowerThreshold = lowerThreshold;
		this.UTText = UTText;
		this.LTText = LTText;
		this.Priority = Priority;
	}

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		addPreferencesFromResource(R.xml.analoginput);
	}

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		EditTextPreference alarmNamePref = (EditTextPreference) getPreferenceScreen().getPreference(0);
		EditTextPreference upperThresholdPref = (EditTextPreference) getPreferenceScreen().getPreference(1);
		EditTextPreference lowerThresholdPref = (EditTextPreference) getPreferenceScreen().getPreference(2);
		EditTextPreference UTTextPref = (EditTextPreference) getPreferenceScreen().getPreference(3);
		EditTextPreference LTTextPref = (EditTextPreference) getPreferenceScreen().getPreference(4);
		EditTextPreference priorityPref = (EditTextPreference) getPreferenceScreen().getPreference(5);
		
		alarmNamePref.setText(alarmName);
		upperThresholdPref.setText(upperThreshold);
		lowerThresholdPref.setText(lowerThreshold);
		UTTextPref.setText(UTText);
		LTTextPref.setText(LTText);
		priorityPref.setText(Priority);
		
		alarmNamePref.setSummary("Set alarm name. Current name is " + alarmName);
		upperThresholdPref.setSummary("Set upper threshold. Current value is " + upperThreshold);
		lowerThresholdPref.setSummary("Set lower threshold. Current value is " + lowerThreshold);
		UTTextPref.setSummary("Set upper threshold text. Current text is " + UTText);
		LTTextPref.setSummary("Set lower threshold text. Current text is " + LTText);
		priorityPref.setSummary("Set priority. Currently " + Priority);
		
		alarmNamePref.setKey("analog" + String.valueOf(id) + Variables.CONFIG_INPUT_NAME);
		upperThresholdPref.setKey("analog" + String.valueOf(id) + Variables.CONFIG_INPUT_UT);
		lowerThresholdPref.setKey("analog" + String.valueOf(id) + Variables.CONFIG_INPUT_LT);
		UTTextPref.setKey("analog" + String.valueOf(id) + Variables.CONFIG_INPUT_UT_TEXT);
		LTTextPref.setKey("analog" + String.valueOf(id) + Variables.CONFIG_INPUT_LT_TEXT);
		priorityPref.setKey("analog" + String.valueOf(id) + Variables.CONFIG_INPUT_PRIORITY);
		
		return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
	}
	

	
	
}
