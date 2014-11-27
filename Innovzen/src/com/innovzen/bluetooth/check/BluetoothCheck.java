package com.innovzen.bluetooth.check;

import com.innovzen.activities.ActivityMain;
import com.innovzen.bluetooth.BluetoothCommand;
import com.innovzen.fragments.base.FragBase;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseIntArray;

/**
 * 蓝牙命令检测类 
 * 单例
 * 
 * @author Desmond Duan
 * 
 */
public class BluetoothCheck<T extends CheckBase> implements CheckInterface {

	protected T checker;

	public BluetoothCheck() {
	
	}

	private static BluetoothCheck instance;//单例实例

	/**
	 * 获取单例实例
	 * 
	 * @return
	 */
	public static BluetoothCheck getInstance() {
		
		if (instance == null) {
			instance = new BluetoothCheck();
		}
		return instance;
	}

	public void setCheck(T c){
		if(checker!=null)
			checker.stopThreads();
		checker=c;
	}

	@Override
	public boolean isReseted(boolean isLog) {
		return checker.isReseted(isLog);
		
	}

	@Override
	public void setTrigger(boolean trigger) {
		checker.setTrigger(trigger);
		
	}

	@Override
	public void initlize() {
		checker.initlize();
		
	}


	@Override
	public void stopThreads() {
		// TODO Auto-generated method stub
		checker.stopThreads();
	}

	@Override
	public void setUiHandler(FragBase frag) {
		checker.setUiHandler(frag);
	}

	@Override
	public int closeOrNot() {
		// TODO Auto-generated method stub
		return checker.closeOrNot();
	}




}
