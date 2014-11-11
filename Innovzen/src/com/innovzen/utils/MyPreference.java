package com.innovzen.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 封装sharedPreference
 * @author desmond.duan
 *
 */
public class MyPreference {
	private static MyPreference myPrefs;//私有化
	private SharedPreferences sp;
	private static Context globleContext;//全局context
	
	private final static String INNOVZEN_PREFERENCE="INNOVZEN_PREFERENCE";
	public final static String VOICE="voice";
	public final static String MAN_VOICE="Man voice";
	public final static String WOMAN_VOICE="Woman voice";
	public final static String SILENCE="silence";
	public static final String MUSIC="music";
	public static final String SELECT_MUSIC1 = "music1";
	public static final String SELECT_MUSIC2 = "music2";
	public static final String SELECT_MUSIC3 = "music3";
	public static final String SELECT_MUSIC4 = "music4";
	public static final String SELECT_MUSIC5 = "music5";
	public final static String GRAPHIC="graphic";
	public final static String TIME="time";
	public final static String FIVE_MINUTES="5min";
	public final static String TEN_MINUTES="10min";
	public final static String FIFTEEN_MINUTES="15min";
	public final static String TWENTY_MINUTES="20min";
	public final static String TWENTY_FIVE_MINUTES="25min";
	public final static String THIRTY_MINUTES="25min";
	public final static String RECEIVE_COMMAND="receive command";
	public final static String BLANCE="blance";
	public final static String RELAX="relax";
	public final static String PERFORMANCE="performance";
	
	public final static String OXTGEN="oxygen";
	public final static int OXTGEN_OPEN=1;
	public final static int OXTGEN_CLOSE=1;
	public final static String BLANCE_RELAX_PERFORMANCE="blance_relax_performance";
	//提供私有的构造方法
	private MyPreference(){}
	/**
	 * 对外提供的初始化方法
	 * @return
	 */
	public static MyPreference getInstance(Context ctx){
		//初始化自身对象
		if(myPrefs == null){
			myPrefs = new MyPreference();
			globleContext=ctx.getApplicationContext();
			myPrefs.initSharedPreferences();
		}
		return myPrefs;
	}
	
	/**
	 * 初始化SharedPreferences对象
	 * @param context
	 */
	public MyPreference initSharedPreferences(){
		//获取SharedPreferences对象
		if(sp == null){
			sp=globleContext.getSharedPreferences(INNOVZEN_PREFERENCE, Context.MODE_PRIVATE);
		}
		return myPrefs;
	}
	
	/**
	 * 向SharedPreferences中写入String类型的数据
	 * @param text
	 */
	public void writeString(String key, String value){
		//获取编辑器对象
		Editor editor = sp.edit();
		//写入数据
		editor.putString(key, value);
		editor.commit();//提交写入的数据
	}
	
	/**
	 * 向SharedPreferences中写入integer类型的数据
	 * @param text
	 */
	public void writeString(String key, int value){
		//获取编辑器对象
		Editor editor = sp.edit();
		//写入数据
		editor.putInt(key, value);
		editor.commit();//提交写入的数据
	}
	
	/**
	 * 根据key读取SharedPreferences中的String类型的数据
	 * @param key
	 * @return
	 */
	public String readString(String key){
		return sp.getString(key, "");
	}
	
	/**
	 * 根据key读取SharedPreferences中的Integer类型的数据
	 * @param key
	 * @return
	 */
	public int readInt(String key){
		return sp.getInt(key, 0);
	}
}
