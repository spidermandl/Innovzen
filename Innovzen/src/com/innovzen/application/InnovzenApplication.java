package com.innovzen.application;

import com.innovzen.handlers.UEHandler;

import android.app.Application;

/**
 * 
 * @author Desmond Duan
 *
 */
public class InnovzenApplication extends Application {

	private static InnovzenApplication instance;
	/**
	 * ≥Ã–Ú±¿¿£“Ï≥£¿‡
	 */
	private UEHandler ueHandler;
	public static InnovzenApplication getInstance(){
		return instance;
	}
	@Override
	public void onCreate() {
		instance=this;
		
		ueHandler = new UEHandler(this); 
		Thread.setDefaultUncaughtExceptionHandler(ueHandler);
		super.onCreate();
	}
}
