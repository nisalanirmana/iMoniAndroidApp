package lk.ac.mrt.ent.dialog.imoni.main;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

import android.graphics.Color;

public class Variables {
	public static final String ACK_TIME = "lk.ac.mrt.ent.dialog.imoni.ack_time";
	public static final String ALARM = "lk.ac.mrt.ent.dialog.imoni.alarm";
    public static final String MAP_TYPE = "lk.ac.mrt.ent.dialog.imoni.maptype";
	public static final int CRITICAL = -65536;
	public static final int MAJOR = Color.argb(255, 255, 127, 0);
	public static final int MINOR = -256;
	public static final String EVENT_ID = "lk.ac.mrt.ent.dialog.imoni.event_ID";
	public static final String EVENT_TIME = "lk.ac.mrt.ent.dialog.imoni.event_time";
	
	public static final String IS_ACKED = "lk.ac.mrt.ent.dialog.imoni.is_acknowledged";
	
	public static final String PRIORITY = "lk.ac.mrt.ent.dialog.imoni.priority";
	public static final String RACU_GROUP = "lk.ac.mrt.ent.dialog.imoni.RACUGroup";
	public static final String RACU_NAME = "lk.ac.mrt.ent.dialog.imoni.RACU";
	
	public static final String RACU = "racu";
	public static final String GROUP = "racu-gropu";
	public static final String STATUS = "status";
	public static final String COM_PREFIX = "com-prefix";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	
	public static final String ANALOG_PREF_TITLE_PREFIX = "Analog Input ";
	public static final String ANALOG_PREF_KEY_PREFIX = "analog_input";
	public static final String DIGITAL_PREF_TITLE_PREFIX = "Digital Input ";
	public static final String DIGITAL_PREF_KEY_PREFIX = "digital_input";
	
	public static final String CONFIG_UID = "uid";
	public static final String CONFIG_RACU_NAME = "racu_name";
	public static final String CONFIG_RACU_GROUP = "racu_group";
	public static final String CONFIG_RACU_IP = "racu_ip";
	public static final String CONFIG_SIM_NO = "sim_number";
	//public static final String CONFIG_SERVER_IP = "server_ip";
	//public static final String CONFIG_APN = "apn";
	//public static final String CONFIG_PORT = "port";
	public static final String CONFIG_INTERVAL = "transmission_interval";
	public static final String CONFIG_RELAY_1 = "relay_1";
	public static final String CONFIG_RELAY_2 = "relay_2";
	public static final String CONFIG_INPUTS = "inputs";
	public static final String CONFIG_INPUT_ID = "input_id";
	public static final String CONFIG_INPUT_TYPE = "input_type";
	public static final String CONFIG_INPUT_CHANNEL = "channel";
	public static final String CONFIG_INPUT_NAME = "input_name";
	public static final String CONFIG_INPUT_UT = "upper_threshold";
	public static final String CONFIG_INPUT_LT = "lower_threshold";
	public static final String CONFIG_INPUT_UT_TEXT = "ut_text";
	public static final String CONFIG_INPUT_LT_TEXT = "lt_text";
	public static final String CONFIG_INPUT_ALARM_TEXT = "alarm_text";
	public static final String CONFIG_INPUT_HEALTHY_TEXT = "healthy_text";
	public static final String CONFIG_INPUT_ALARM_ENABLE = "alarm_enable";
	public static final String CONFIG_INPUT_PRIORITY = "priority";
	public static final String CONFIG_INPUT_NO = "normally_open";
    public static final String CONFIG_DATA_VALUE = "value";

	
	public static String mComPrefix = "dlog";
	
	public static String mSelectedRACU = "iMoni Lite - gen";
	public static String mSelectedRACUGroup = "Dialog-Piliyandala Test";
	public static String mUID = "013777002007167";
	
	
}