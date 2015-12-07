package lk.ac.mrt.ent.dialog.imoni.web;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import lk.ac.mrt.ent.dialog.imoni.events.EventsSectionFragment;
import lk.ac.mrt.ent.dialog.imoni.main.MainActivity;
import lk.ac.mrt.ent.dialog.imoni.main.R;
import lk.ac.mrt.ent.dialog.imoni.main.SectionPagerAdapter;
import lk.ac.mrt.ent.dialog.imoni.main.Variables;
import lk.ac.mrt.ent.dialog.imoni.racu.AnalogInput;
import lk.ac.mrt.ent.dialog.imoni.racu.DigitalInput;
import lk.ac.mrt.ent.dialog.imoni.racu.RACUSectionFragment;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ConnectWS {
	
//	TODO: Get web service up. Using mJSONStringConfig until WS is ready
//	static final String mJSONStringRACUs = "{\"Group1\":[\"RACU1\",\"Racu2\",\"RACU222\",\"gfef\",\"scasdcsc\",\"xzcz x\",\"RACU Test\"],\"Group2\":[\"xzcz xddd\",\"xzcz xddd1\",\"xzcz xddd13\",\"trest\",\"test\"]}";
//	static final String mJSONStringEvents = "{\"events\":[{\"GROUP\":\"Group1\",\"PRIORITY\":\"Minor\",\"EVENT_ID\":\"2\",\"ALARM\":\"Humidity\",\"EVENT_TIME\":\"2014-04-03 10:29:24.0\",\"ACK_TIME\":\"2014-02-25 12:23:17.0\",\"RACU\":\"Racu2\"},{\"GROUP\":\"Group1\",\"PRIORITY\":\"Critical\",\"EVENT_ID\":\"3\",\"ALARM\":\"Solar Current\",\"EVENT_TIME\":\"2014-04-03 10:36:25.0\",\"ACK_TIME\":\"2014-06-26 11:35:17.0\",\"RACU\":\"Racu2\"},{\"GROUP\":\"Group1\",\"PRIORITY\":\"Major\",\"EVENT_ID\":\"4\",\"ALARM\":\"Load Current\",\"EVENT_TIME\":\"2014-04-03 10:36:25.0\",\"ACK_TIME\":\"2014-05-28 13:40:05.0\",\"RACU\":\"Racu2\"},{\"GROUP\":\"Group1\",\"PRIORITY\":\"Minor\",\"EVENT_ID\":\"5\",\"ALARM\":\"Battery Voltage\",\"EVENT_TIME\":\"2014-02-06 14:45:46.0\",\"ACK_TIME\":null,\"RACU\":\"Racu2\"}]}";
//	static final String mJSONStringConfig = "{\"port\":\"22\",\"racu_group\":\"Group1\",\"uid\":\"12345678910\",\"relay_1\":\"false\",\"sim_number\":\"1111\",\"racu_name\":\"Racu2\",\"relay_2\":\"false\",\"server_ip\":\"y.y.y.y\",\"transmission_interval\":\"1\",\"racu_ip\":\"\",\"apn\":\"yy\",\"inputs\":[{\"input_id\":\"2\",\"input_type\":\"Analog Input\",\"channel\":\"2\",\"input_name\":\"xzcas\",\"upper_threshold\":\"0\",\"lower_threshold\":\"10\",\"ut_text\":\"test\",\"lt_text\":\"test\",\"alarm_text\":\"test\",\"healthy_text\":\"test\",\"alarm_enable\":\"true\",\"priority\":\"Minor\",\"normally_open\":\"0\"},{\"input_id\":\"3\",\"input_type\":\"Analog Input\",\"channel\":\"3\",\"input_name\":\"sdcsdc\",\"upper_threshold\":\"0\",\"lower_threshold\":\"10\",\"ut_text\":\"test\",\"lt_text\":\"test\",\"alarm_text\":\"test\",\"healthy_text\":\"test\",\"alarm_enable\":\"false\",\"priority\":\"Critical\",\"normally_open\":\"test\"},{\"input_id\":\"4\",\"input_type\":\"Analog Input\",\"channel\":\"4\",\"input_name\":\"sdvsv\",\"upper_threshold\":\"0\",\"lower_threshold\":\"10\",\"ut_text\":\"test\",\"lt_text\":\"test\",\"alarm_text\":\"test\",\"healthy_text\":\"test\",\"alarm_enable\":\"false\",\"priority\":\"Major\",\"normally_open\":\"test\"},{\"input_id\":\"5\",\"input_type\":\"Analog Input\",\"channel\":\"5\",\"input_name\":\"sdfcsd\",\"upper_threshold\":\"0\",\"lower_threshold\":\"10\",\"ut_text\":\"test\",\"lt_text\":\"test\",\"alarm_text\":\"test\",\"healthy_text\":\"test\",\"alarm_enable\":\"true\",\"priority\":\"Minor\",\"normally_open\":\"test\"},{\"input_id\":\"6\",\"input_type\":\"Analog Input\",\"channel\":\"1\",\"input_name\":\"svsv\",\"upper_threshold\":\"0\",\"lower_threshold\":\"10\",\"ut_text\":\"test\",\"lt_text\":\"test\",\"alarm_text\":\"test\",\"healthy_text\":\"test\",\"alarm_enable\":\"false\",\"priority\":\"Critical\",\"normally_open\":\"test\"}]}";
			
	public static void GetEventsList(String comPrefix, String group, String racu) {
		RequestParams params = new RequestParams();
		params.add("com-prefix", comPrefix);
		params.add("racu-gropu", group);
		params.add("racu", racu);
		getEventsfromWS(params);
	}
  
	private static void getEventsfromWS(RequestParams params) {
		MainActivity.mProgressDialog.show(); 
		new AsyncHttpClient().get("http://203.189.68.253:8081/imoniws/events", params, new AsyncHttpResponseHandler() {
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				MainActivity.mProgressDialog.dismiss();
				MainActivity.ShowToast("Connection failed..events");
			}
      
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					EventsSectionFragment.mGridAdapter.clear();
					JSONArray eventsArray = new JSONObject(new String(responseBody)).getJSONArray("events");
					JSONObject eventObject;
					Bundle eventDetails;
					for(int i=eventsArray.length()-1; i>=0; i--){
						eventObject = eventsArray.getJSONObject(i);
						eventDetails = new Bundle();
						String ackTime;
						boolean isAcked;
						eventDetails.putString(Variables.RACU_NAME, eventObject.getString("RACU"));
						eventDetails.putString(Variables.RACU_GROUP, eventObject.getString("GROUP"));
						eventDetails.putString(Variables.ALARM, eventObject.getString("ALARM"));
                        eventDetails.putString(Variables.EVENT_TIME, eventObject.getString("EVENT_TIME"));
						ackTime = eventObject.getString("ACK_TIME");
						isAcked = ackTime.equalsIgnoreCase("null");
						eventDetails.putBoolean(Variables.IS_ACKED, !isAcked);
						if (isAcked) {
							ackTime = "Not acknowledged";
						}
						eventDetails.putString(Variables.ACK_TIME, ackTime);
						eventDetails.putString(Variables.PRIORITY, eventObject.getString("PRIORITY"));
						EventsSectionFragment.mGridAdapter.add(eventDetails);
					}
					EventsSectionFragment.mGridAdapter.notifyDataSetChanged();
					MainActivity.mProgressDialog.dismiss();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
  
	public static void getRACUMap(String comPrefix) {
		RequestParams params = new RequestParams();
		params.add("com-prefix", comPrefix);
		getRACUsfromWS(params);
	}
  
	private static void getRACUsfromWS(RequestParams params) {
		MainActivity.mProgressDialog.show();
		new AsyncHttpClient().get("http://203.189.68.253:8081/imoniws/racus", params, new AsyncHttpResponseHandler() {
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				MainActivity.mProgressDialog.dismiss();
				MainActivity.ShowToast("Connection failed..racus");
			}
      
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				HashMap<String, List<String>> groupChildMap = new HashMap<String, List<String>>();
				JSONObject object;
				Iterator<?> iterator;
				String group;
				JSONArray racus;
				ArrayList<String> racuList;
				try {
					object = new JSONObject(new String(responseBody));
					iterator = object.keys();
					while(iterator.hasNext()){
						group = (String)iterator.next();
						racus = object.getJSONArray(group);
						racuList = new ArrayList<String>();
						for(int i=0; i<racus.length(); i++){
							racuList.add(racus.getString(i));
						}
						groupChildMap.put(group, racuList);
					}
					MainActivity.mGroupChildMap = groupChildMap;
					MainActivity.mGroups = new ArrayList<String>(MainActivity.mGroupChildMap.keySet());
					MainActivity.mProgressDialog.dismiss();
					MainActivity.SetExpandableDrawerAdapter();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void GetRACUConfig(Context context, String comPrefix, String group, String racu) {
		RequestParams params = new RequestParams();
		params.add("com-prefix", comPrefix);
		params.add("racu-gropu", group);
		params.add("racu", racu);
		getRACUConfigfromWS(context, params);
	}
	
	@SuppressLint("DefaultLocale")
	private static void getRACUConfigfromWS(final Context context, RequestParams params) {
		MainActivity.mProgressDialog.show();
		new AsyncHttpClient().get("http://203.189.68.253:8081/imoniws/config", params, new AsyncHttpResponseHandler() {
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				MainActivity.mProgressDialog.dismiss();
				MainActivity.ShowToast("Connection failed..config");
			}
      
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					JSONObject object = new JSONObject(new String(responseBody));
					Variables.mUID = object.getString(Variables.CONFIG_UID);
					
					SectionPagerAdapter.mRACUFragment.unregisterOnSharedPreferenceChangeListener();
					
					EditTextPreference racuName = (EditTextPreference)RACUSectionFragment.mGeneralpref.getPreferenceScreen().getPreference(0);
					ListPreference racuGroup = (ListPreference)RACUSectionFragment.mGeneralpref.getPreferenceScreen().getPreference(1);
					EditTextPreference racuIP = (EditTextPreference)RACUSectionFragment.mGeneralpref.getPreferenceScreen().getPreference(2);
					EditTextPreference simNo = (EditTextPreference)RACUSectionFragment.mGeneralpref.getPreferenceScreen().getPreference(3);
					
					EditTextPreference serverIp = (EditTextPreference)RACUSectionFragment.mGprspref.getPreferenceScreen().getPreference(0);
					EditTextPreference apn = (EditTextPreference)RACUSectionFragment.mGprspref.getPreferenceScreen().getPreference(1);
					EditTextPreference port = (EditTextPreference)RACUSectionFragment.mGprspref.getPreferenceScreen().getPreference(2);
					EditTextPreference interval = (EditTextPreference)RACUSectionFragment.mGprspref.getPreferenceScreen().getPreference(3);
					
					SwitchPreference relay1 = (SwitchPreference)RACUSectionFragment.mRelaypref.getPreferenceScreen().getPreference(0);
					SwitchPreference relay2 = (SwitchPreference)RACUSectionFragment.mRelaypref.getPreferenceScreen().getPreference(0);
					
					racuName.setText(object.getString(Variables.CONFIG_RACU_NAME));
					racuGroup.setValue(object.getString(Variables.CONFIG_RACU_GROUP));
					racuIP.setText(object.getString(Variables.CONFIG_RACU_IP));
					simNo.setText(object.getString(Variables.CONFIG_SIM_NO));
					racuName.setSummary(context.getText(R.string.general_prefs_racu_name_summary) + ". Current name is " + object.getString(Variables.CONFIG_RACU_NAME));
					racuGroup.setSummary(context.getText(R.string.general_prefs_groups_summary) + ". Current group is " + object.getString(Variables.CONFIG_RACU_GROUP));
					racuIP.setSummary(context.getText(R.string.general_prefs_racu_ip_summary) + ". Current ip is " + object.getString(Variables.CONFIG_RACU_IP));
					simNo.setSummary(context.getText(R.string.general_prefs_sim_no_summary) + ". Current sim no is " + object.getString(Variables.CONFIG_SIM_NO));
					
					
					serverIp.setText(object.getString(Variables.CONFIG_SERVER_IP));
					apn.setText(object.getString(Variables.CONFIG_APN));
					port.setText(object.getString(Variables.CONFIG_PORT));
					interval.setText(object.getString(Variables.CONFIG_INTERVAL));
					serverIp.setSummary(context.getText(R.string.gprs_prefs_server_ip_summary) + ". Current server IP is " + object.getString(Variables.CONFIG_SERVER_IP));
					apn.setSummary(context.getText(R.string.gprs_prefs_apn_summary) + ". Current APN is " + object.getString(Variables.CONFIG_APN));
					port.setSummary(context.getText(R.string.gprs_prefs_port_summary) + ". Current port is " + object.getString(Variables.CONFIG_PORT));
					interval.setSummary(context.getText(R.string.gprs_prefs_interval_summary) + ". Current interval is " + object.getString(Variables.CONFIG_INTERVAL));
					
					relay1.setChecked(object.getBoolean(Variables.CONFIG_RELAY_1));
					relay2.setChecked(object.getBoolean(Variables.CONFIG_RELAY_2));
					
					PreferenceScreen analogScreen = RACUSectionFragment.mAnalogpref.getPreferenceScreen();
					PreferenceScreen digitalScreen = RACUSectionFragment.mDigitalpref.getPreferenceScreen();
					
					JSONArray inputs = object.getJSONArray(Variables.CONFIG_INPUTS);
					
					for(int i=0; i<inputs.length(); i++){
						JSONObject input = inputs.getJSONObject(i);
						if(input.getString(Variables.CONFIG_INPUT_TYPE).equalsIgnoreCase("Analog Input")){
							String alarmName = input.getString(Variables.CONFIG_INPUT_NAME);
							String upperThreshold = input.getString(Variables.CONFIG_INPUT_UT);
							String lowerThreshold = input.getString(Variables.CONFIG_INPUT_LT);
							String UTText = input.getString(Variables.CONFIG_INPUT_UT_TEXT);
							String LTText = input.getString(Variables.CONFIG_INPUT_LT_TEXT);
							String Priority = input.getString(Variables.CONFIG_INPUT_PRIORITY);
							SectionPagerAdapter.mRACUFragment.addToChangedKeyList("analog" + String.valueOf(i) + "input_id");
							PreferenceManager.getDefaultSharedPreferences(context).edit().putString("analog" + String.valueOf(i) + "input_id", input.getString(Variables.CONFIG_INPUT_ID)).commit();
							final AnalogInput ain = new AnalogInput(i, alarmName, upperThreshold, lowerThreshold, UTText, LTText, Priority);
							SectionPagerAdapter.mRACUFragment.addChildPreferenceFragment(ain);
							Preference pref = new Preference(context);
							if(!alarmName.isEmpty()){
								pref.setTitle(alarmName.substring(0, 1).toUpperCase() + alarmName.substring(1));
							}
							pref.setSummary("Click for settings");
							pref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
								@Override
								public boolean onPreferenceClick(Preference preference) {
									SectionPagerAdapter.mRACUFragment.setPreferenceFragmentBody(ain);
									return true;
								}
							});
							analogScreen.addPreference(pref);					
						} else {
							String alarmName = input.getString(Variables.CONFIG_INPUT_NAME);
							String healthyText = input.getString(Variables.CONFIG_INPUT_HEALTHY_TEXT);
							String alarmText = input.getString(Variables.CONFIG_INPUT_ALARM_TEXT);
							String priority = input.getString(Variables.CONFIG_INPUT_PRIORITY);
							String normallyOpen = input.getString(Variables.CONFIG_INPUT_NO);
							SectionPagerAdapter.mRACUFragment.addToChangedKeyList("digital" + String.valueOf(i) + "input_id");
							PreferenceManager.getDefaultSharedPreferences(context).edit().putString("digital" + String.valueOf(i) + "input_id", input.getString(Variables.CONFIG_INPUT_ID)).commit();
							final DigitalInput din = new DigitalInput(i, alarmName, healthyText, alarmText, priority, normallyOpen);
							SectionPagerAdapter.mRACUFragment.addChildPreferenceFragment(din);
							Preference pref = new Preference(context);
							pref.setTitle(alarmName.substring(0, 1).toUpperCase() + alarmName.substring(1));
							pref.setSummary("Click for settings");
							pref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
								@Override
								public boolean onPreferenceClick(Preference preference) {
									SectionPagerAdapter.mRACUFragment.setPreferenceFragmentBody(din);
									return true;
								}
							});
							digitalScreen.addPreference(pref);
						}
					}
					SectionPagerAdapter.mRACUFragment.registerOnSharedPreferenceChangeListener();
					MainActivity.mProgressDialog.dismiss();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void postRACUConfig(final Context context, JSONObject configKeyValueMap){
		ByteArrayEntity entity = new ByteArrayEntity(configKeyValueMap.toString().getBytes());
		MainActivity.mProgressDialog.show();
		new AsyncHttpClient().post(context, "http://203.189.68.253:8081/iMoniService/modifyRACUString", entity, "application/json", new AsyncHttpResponseHandler(){
			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {}
		});
	}

}