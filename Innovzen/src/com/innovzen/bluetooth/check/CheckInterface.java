package com.innovzen.bluetooth.check;

import com.innovzen.fragments.base.FragBase;

public interface CheckInterface {
	/**
	 * ����ui fragment
	 * @param frag
	 */
	public void setUiHandler(FragBase frag);
	/**
	 * ��ʼ��״̬����
	 */
	public void initlize();
	/**
	 * ���������Ч����
	 * @param trigger
	 */
	public void setTrigger(boolean trigger);
	/**
	 * �жϻ����Ƿ�λ
	 * isLog �Ƿ���ʾ��ʾ
	 * @return
	 */
	public boolean isReseted(boolean isLog) ;
   
	/**
	 * ֹͣ�����߳�
	 */
	public void stopThreads();
	/**
	 * �жϻ����Ƿ�ر�
	 */
	public int  closeOrNot();
	
}
