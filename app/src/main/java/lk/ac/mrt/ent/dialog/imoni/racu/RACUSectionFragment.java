package lk.ac.mrt.ent.dialog.imoni.racu;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import lk.ac.mrt.ent.dialog.imoni.main.MainActivity;
import lk.ac.mrt.ent.dialog.imoni.main.R;
import lk.ac.mrt.ent.dialog.imoni.main.Variables;
import lk.ac.mrt.ent.dialog.imoni.web.ConnectWS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class RACUSectionFragment extends Fragment implements OnSharedPreferenceChangeListener {
	
	public static RACUPreferenceFragments.GeneralPreferenceFragment mGeneralpref;
	public static RACUPreferenceFragments.GPRSPreferenceFragment mGprspref;
	public static RACUPreferenceFragments.RelayPreferenceFragment mRelaypref;
	public static RACUPreferenceFragments.AnalogPreferenceFragment mAnalogpref;
	public static RACUPreferenceFragments.DigitalPreferenceFragment mDigitalpref;
	
	private PreferenceFragment mCurrentChildFragment;
	private List<PreferenceFragment> mChildPreferenceList = new ArrayList<PreferenceFragment>();
	
	private List<String> mChangedKeyList = new ArrayList<String>();
	
	public void setPreferenceFragmentBody(PreferenceFragment fragment){
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.hide(mCurrentChildFragment).show(fragment).commit();
		mCurrentChildFragment = fragment;
	}
	
	public void addChildPreferenceFragment(PreferenceFragment fragment){
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.add(R.id.racu_settings_body, fragment);
		ft.hide(fragment);
		ft.commit();
		mChildPreferenceList.add(fragment);
	}
	
	public void unregisterOnSharedPreferenceChangeListener(){
		PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
	}
	
	public void registerOnSharedPreferenceChangeListener(){
		PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
	}
	
	public void addToChangedKeyList(String key){
		mChangedKeyList.add(key);
	}
  
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		sharedPref.edit().clear().commit();
		mChangedKeyList.clear();
		mChildPreferenceList.clear();
		if(MainActivity.mSelectedTab != 1){
			android.support.v4.app.FragmentTransaction ftv4 = getFragmentManager().beginTransaction();
			ftv4.detach(this);
			ftv4.commit();
		} else if(Variables.mSelectedRACU.isEmpty()){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("Please select an RACU..")
				.setCancelable(false)
				.setPositiveButton("OK", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						MainActivity.mViewPager.setCurrentItem(0);		
					}
				})
				.show();
		}
		PreferenceListFragment preferenceList = new PreferenceListFragment();
		mGeneralpref = new RACUPreferenceFragments.GeneralPreferenceFragment();
		mGprspref = new RACUPreferenceFragments.GPRSPreferenceFragment();
		mRelaypref = new RACUPreferenceFragments.RelayPreferenceFragment();
		mAnalogpref = new RACUPreferenceFragments.AnalogPreferenceFragment();
		mDigitalpref = new RACUPreferenceFragments.DigitalPreferenceFragment();
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.add(R.id.racu_settings_list, preferenceList);
		ft.add(R.id.racu_settings_body, mGprspref);
		ft.hide(mGprspref);
		ft.add(R.id.racu_settings_body, mRelaypref);
		ft.hide(mRelaypref);
		ft.add(R.id.racu_settings_body, mAnalogpref);
		ft.hide(mAnalogpref);
		ft.add(R.id.racu_settings_body, mDigitalpref);
		ft.hide(mDigitalpref);
		ft.add(R.id.racu_settings_body, mGeneralpref);
		mCurrentChildFragment = mGeneralpref;
		ft.commit();
		return inflater.inflate(R.layout.fragment_racu, container, false);
	}

	@Override
	public void onResume() {
		PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
		if(MainActivity.mSelectedTab == 1 && !Variables.mSelectedRACU.isEmpty()){
			ConnectWS.GetRACUConfig(getActivity(), Variables.mComPrefix, Variables.mSelectedRACUGroup, Variables.mSelectedRACU);
		}
		super.onResume();
	}
	
	@Override
	public void onPause() {
		PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
		super.onPause();
	}
	
	@Override
	public void onDestroyView() {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		Iterator<String> iterator = mChangedKeyList.iterator();
		JSONObject configKeyValueMap = new JSONObject();
		try {
			configKeyValueMap.put(Variables.CONFIG_UID, Variables.mUID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		String key;
		Multimap<String, Bundle> analogInMap = ArrayListMultimap.create();
		Multimap<String, Bundle> digitalInMap = ArrayListMultimap.create();
		String keys = ""; //remove
		while(iterator.hasNext()){
			key = iterator.next();
			try{
				if(key.contains("analog")){
					Bundle enrty = new Bundle();
					enrty.putString(key.substring(7), sharedPref.getString(key, ""));
					analogInMap.put(Character.toString(key.charAt(6)), enrty);
				} else if(key.contains("digital")){
					Bundle enrty = new Bundle();
					enrty.putString(key.substring(8), sharedPref.getString(key, ""));
					digitalInMap.put(Character.toString(key.charAt(7)), enrty);
				} else{
					configKeyValueMap.put(key, sharedPref.getString(key, ""));
				}
			} catch(ClassCastException e ){
				try {
					configKeyValueMap.put(key, String.valueOf(sharedPref.getBoolean(key, false)));
				} catch (JSONException e1) {}
			} catch (JSONException e) {}
			keys = keys + "\n" + key; //remove
		}
		
		JSONArray inputs = new JSONArray();
		
		Iterator<String> analogInIterator = analogInMap.keySet().iterator();
		Iterator<String> digitalInIterator = digitalInMap.keySet().iterator();
		
		while(analogInIterator.hasNext()){
			String analogInKey = analogInIterator.next();
			Collection<Bundle> analogInEntries = analogInMap.get(analogInKey);
			JSONObject analogJSONObject = new JSONObject();
			for(Bundle analogInEntry: analogInEntries){
				String entryKey = (String) analogInEntry.keySet().toArray()[0];
				try {
					analogJSONObject.put(entryKey, analogInEntry.getString(entryKey));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			inputs.put(analogJSONObject);
		}
		
		while(digitalInIterator.hasNext()){
			String digitalInKey = digitalInIterator.next();
			Collection<Bundle> digitalInEntries = digitalInMap.get(digitalInKey);
			JSONObject digitalJSONObject = new JSONObject();
			for(Bundle digitalInEntry: digitalInEntries){
				String entryKey = (String) digitalInEntry.keySet().toArray()[0];
				try {
					digitalJSONObject.put(entryKey, digitalInEntry.getString(entryKey));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			inputs.put(digitalJSONObject);
		}
		
		try {
			configKeyValueMap.put(Variables.CONFIG_INPUTS, inputs);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		ConnectWS.postRACUConfig(getActivity(), configKeyValueMap);
		Log.e("ANALOG MAP", analogInMap.toString());
		Log.e("DIGITAL MAP", digitalInMap.toString());
		Log.e("JSON STRING", configKeyValueMap.toString());  //remove
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.detach(mGeneralpref);
		ft.detach(mGprspref);
		ft.detach(mRelaypref);
		ft.detach(mAnalogpref);
		ft.detach(mDigitalpref);
		for(PreferenceFragment fragment: mChildPreferenceList){
			ft.detach(fragment);
		}
		ft.commit();
		super.onDestroyView();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if(!mChangedKeyList.contains(key)){
			mChangedKeyList.add(key);
		}
	}
	
}