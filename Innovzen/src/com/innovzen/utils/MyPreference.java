package com.innovzen.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * ��װsharedPreference
 * @author desmond.duan
 *
 */
public class MyPreference {
	private static MyPreference myPrefs;//˽�л�
	private SharedPreferences sp;
	private static Context globleContext;//ȫ��context
	
	private final static String INNOVZEN_PREFERENCE="INNOVZEN_PREFERENCE";
	
	public final static String GRAPHIC="graphic";
	//�ṩ˽�еĹ��췽��
	private MyPreference(){}
	/**
	 * �����ṩ�ĳ�ʼ������
	 * @return
	 */
	public static MyPreference getInstance(Context ctx){
		//��ʼ����������
		if(myPrefs == null){
			myPrefs = new MyPreference();
			globleContext=ctx.getApplicationContext();
			myPrefs.initSharedPreferences();
		}
		return myPrefs;
	}
	
	/**
	 * ��ʼ��SharedPreferences����
	 * @param context
	 */
	public MyPreference initSharedPreferences(){
		//��ȡSharedPreferences����
		if(sp == null){
			sp=globleContext.getSharedPreferences(INNOVZEN_PREFERENCE, Context.MODE_PRIVATE);
		}
		return myPrefs;
	}
	
	/**
	 * ��SharedPreferences��д��String���͵�����
	 * @param text
	 */
	public void writeString(String key, String value){
		//��ȡ�༭������
		Editor editor = sp.edit();
		//д������
		editor.putString(key, value);
		editor.commit();//�ύд�������
	}
	
	/**
	 * ��SharedPreferences��д��integer���͵�����
	 * @param text
	 */
	public void writeString(String key, int value){
		//��ȡ�༭������
		Editor editor = sp.edit();
		//д������
		editor.putInt(key, value);
		editor.commit();//�ύд�������
	}
	
	/**
	 * ����key��ȡSharedPreferences�е�String���͵�����
	 * @param key
	 * @return
	 */
	public String readString(String key){
		return sp.getString(key, "");
	}
	
	/**
	 * ����key��ȡSharedPreferences�е�Integer���͵�����
	 * @param key
	 * @return
	 */
	public int readInt(String key){
		return sp.getInt(key, 0);
	}
}