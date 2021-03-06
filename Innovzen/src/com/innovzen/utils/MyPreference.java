package com.innovzen.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 封装sharedPreference
 * 
 * @author desmond.duan
 * 
 */
public class MyPreference {
	private static MyPreference myPrefs;// 私有化
	private SharedPreferences sp;
	private static Context globleContext;// 全局context

	private final static String INNOVZEN_PREFERENCE = "INNOVZEN_PREFERENCE";
	//记录用户语言
	public final static String LANGUAGE = "language";
	public final static String ENGLISH = "English";
	public final static String FRENCH = "French";
	public final static String GERMAN = "German";
	public final static String SPANISH = "Spanish";
	// 记录用户的session模式
	public final static String SESSION_MODE = "session mod";
	public final static String BEGINNER = "Beginner";
	public final static String INTERMEDAITE = "Intermedaite";
	public final static String PRO = "Pro";
	public final static String CUSTOMISE = "Customise";
	// 记录声音上一次的大小
	public final static int LAST_VOLUME_INT=-1;
	public final static String LAST_VOLUME = "last volume";
	// 记录Voice键状态
	public final static String SELECTED_VOICE="selected voice";
	public final static String VOICE = "voice";
	public final static String MAN_VOICE = "Man voice";
	public final static String WOMAN_VOICE = "Woman voice";
	public final static String SILENCE = "silence";
	// 记录Music键状态
	public static final String MUSIC = "music";
	public static final String SELECT_MUSIC1 = "music1";
	public static final String SELECT_MUSIC2 = "music2";
	public static final String SELECT_MUSIC3 = "music3";
	public static final String SELECT_MUSIC4 = "music4";
	public static final String SELECT_MUSIC5 = "music5";
	// 记录Graphic键状态
	public final static String GRAPHIC = "graphic";
	// 记录Time键状态
	public final static String TIME = "time";

	public final static String RECEIVE_COMMAND = "receive command";
	// 记录从哪个键位进入动画界面
	public final static String BLANCE = "blance";
	public final static String RELAX = "relax";
	public final static String PERFORMANCE = "performance";
	public final static String BLANCE_RELAX_PERFORMANCE = "blance_relax_performance";
	// 记录Oxygen键状态
	public final static String OXTGEN = "oxygen";
	// Oxygen键状态 开
	public final static int OXTGEN_OPEN = 1;
	// Oxygen键状态 关
	public final static int OXTGEN_CLOSE = 0;
	// 记录Swing键状态
	public final static String SWING = "swing";
	// Swing键状态 开
	public final static int SWING_OPEN = 1;
	// Swing键状态 关
	public final static int SWING_CLOSE = 0;
	// 记录Leds键状态
	public final static String LED = "led";
	// Leds键状态 开
	public final static int LED_OPEN = 1;
	// Leds键状态 关
	public final static int LED_CLOSE = 0;
	// Heat键状态
	public final static String HEAT = "heat";
	// Hear键状态 开
	public final static int HEAT_OPEN = 1;
	// Hear键状态 关
	public final static int HEAT_CLOSE = 0;
	// Bluetooth键状态
	public final static String BLUETOOTH = "bluetooth";
	// Bluetooth键状态 开
	public final static int BLUETOOTH_OPEN = 1;
	// Bluetooth键状态 关
	public final static int BLUETOOTH_CLOSE = 0;
	// Zero gravity键状态
	public final static String ZERO = "zero";
	// Zero gravity键状态 开
	public final static int ZERO_OPEN = 1;
	// Zero gravity键状态 关
	public final static int ZERO_CLOSE = 0;

	public final static String FIRSTRUN = "firstrun";
	
	public final static String MINS= "mins";

	// 提供私有的构造方法
	private MyPreference() {
	}

	/**
	 * 对外提供的初始化方法
	 * 
	 * @return
	 */
	public static MyPreference getInstance(Context ctx) {
		// 初始化自身对象
		if (myPrefs == null) {
			myPrefs = new MyPreference();
			globleContext = ctx.getApplicationContext();
			myPrefs.initSharedPreferences();
		}
		return myPrefs;
	}

	/**
	 * 初始化SharedPreferences对象
	 * 
	 * @param context
	 */
	public MyPreference initSharedPreferences() {
		// 获取SharedPreferences对象
		if (sp == null) {
			sp = globleContext.getSharedPreferences(INNOVZEN_PREFERENCE,
					Context.MODE_PRIVATE);
		}
		return myPrefs;
	}

	/**
	 * 向SharedPreferences中写入String类型的数据
	 * 
	 * @param text
	 */
	public void writeString(String key, String value) {
		// 获取编辑器对象
		Editor editor = sp.edit();
		// 写入数据
		editor.putString(key, value);
		editor.commit();// 提交写入的数据
	}

	/**
	 * 向SharedPreferences中写入integer类型的数据
	 * 
	 * @param text
	 */
	public void writeInt(String key, int value) {
		// 获取编辑器对象
		Editor editor = sp.edit();
		// 写入数据
		editor.putInt(key, value);
		editor.commit();// 提交写入的数据
	}

	/**
	 * 根据key读取SharedPreferences中的String类型的数据
	 * 
	 * @param key
	 * @return
	 */
	public String readString(String key) {
		return sp.getString(key, "");
	}

	/**
	 * 根据key读取SharedPreferences中的Integer类型的数据
	 * 
	 * @param key
	 * @return
	 */
	public int readInt(String key) {
		return sp.getInt(key, -1);
	}
	
}
