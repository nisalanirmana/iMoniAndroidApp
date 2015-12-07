package lk.ac.mrt.ent.dialog.imoni.racu;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

import lk.ac.mrt.ent.dialog.imoni.main.R;
import lk.ac.mrt.ent.dialog.imoni.main.SectionPagerAdapter;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PreferenceListFragment extends PreferenceFragment {
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		addPreferencesFromResource(R.xml.preferences);
	}
	
	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		return paramLayoutInflater.inflate(R.layout.preference_list_fragment, paramViewGroup, false);
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		String key = preference.getKey();
		if(key.equalsIgnoreCase(getResources().getString(R.string.prefs_gprs_key))){
			SectionPagerAdapter.mRACUFragment.setPreferenceFragmentBody(RACUSectionFragment.mGprspref);
		} else if(key.equalsIgnoreCase(getResources().getString(R.string.prefs_relay_key))){
			SectionPagerAdapter.mRACUFragment.setPreferenceFragmentBody(RACUSectionFragment.mRelaypref);
		} else if(key.equalsIgnoreCase(getResources().getString(R.string.prefs_analog_key))){
			SectionPagerAdapter.mRACUFragment.setPreferenceFragmentBody(RACUSectionFragment.mAnalogpref);
		} else if(key.equalsIgnoreCase(getResources().getString(R.string.prefs_digital_key))){
			SectionPagerAdapter.mRACUFragment.setPreferenceFragmentBody(RACUSectionFragment.mDigitalpref);
		} else {
			SectionPagerAdapter.mRACUFragment.setPreferenceFragmentBody(RACUSectionFragment.mGeneralpref);
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	
	
}