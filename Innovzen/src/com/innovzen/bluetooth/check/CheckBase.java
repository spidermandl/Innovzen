package com.innovzen.bluetooth.check;

import com.innovzen.fragments.base.FragBase;

public class CheckBase implements CheckInterface{

	/**
	 * fragment ui����
	 */
	protected FragBase uiHandler;
	/**
	 * �Ƿ�ʼ���
	 */
	protected boolean trigger=false;
	
	/**
	 * ����ui �߳�
	 * @param uiHandler
	 */
	@Override
	public void setUiHandler(FragBase uiHandler) {
		this.uiHandler = uiHandler;
	}

	@Override
	public boolean isReseted(boolean isLog) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTrigger(boolean trigger) {
		this.trigger=trigger;
	}

	@Override
	public void initlize() {
		// TODO Auto-generated method stub
		
	}
   
	@Override
	public int startOrStop(boolean isLog) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stopThreads() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public  int closeOrNot() {
		// TODO Auto-generated method stub
		return 0;
	}

}
