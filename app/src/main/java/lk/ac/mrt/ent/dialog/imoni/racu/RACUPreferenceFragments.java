package lk.ac.mrt.ent.dialog.imoni.racu;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

import java.util.List;

import lk.ac.mrt.ent.dialog.imoni.main.MainActivity;
import lk.ac.mrt.ent.dialog.imoni.main.R;
import android.os.Bundle;
import android.preference.ListPreference;

public class RACUPreferenceFragments {

	public static class GeneralPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle paramBundle) {
			super.onCreate(paramBundle);
			addPreferencesFromResource(R.xml.generalpreference);
			if(MainActivity.mSelectedTab == 1 && MainActivity.mGroups != null){
				List<String> groupsList = MainActivity.mGroups;
				ListPreference groups = (ListPreference)this.getPreferenceScreen().getPreferenceManager().findPreference(getActivity().getText(R.string.general_prefs_groups_key));
				groups.setEntries(groupsList.toArray(new String[groupsList.size()]));
				groups.setEntryValues(groupsList.toArray(new String[groupsList.size()]));
			}
		}
	}

	public static class GPRSPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle paramBundle) {
			super.onCreate(paramBundle);
			addPreferencesFromResource(R.xml.gprspreference);
		}
	}

	public static class RelayPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle paramBundle) {
			super.onCreate(paramBundle);
			addPreferencesFromResource(R.xml.relaypreference);
		}
	}

	public static class AnalogPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle paramBundle) {
			super.onCreate(paramBundle);
			addPreferencesFromResource(R.xml.analogpreference);
		}		
	}

	public static class DigitalPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle paramBundle) {
			super.onCreate(paramBundle);
			addPreferencesFromResource(R.xml.digitalpreference);
		}
	}

}
