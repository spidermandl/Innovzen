package com.innovzen.bluetooth.check;

import java.lang.Character.UnicodeBlock;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseIntArray;
import android.widget.Toast;

import com.innovzen.bluetooth.BluetoothCommand;

/**
 * ��λ�����
 * 
 * @author Desmond Duan
 * 
 */
public class ResetCheck extends CheckBase {

	/**
	 * ������λ״̬
	 */
	private static final int INVALID = 0;// ����״̬
	private static final int RESETING = 1;// ���ڸ�λ����8�ֽڵ���λΪ0
	private static final int RESETED = 2;// ��λ�ɹ�

	/**
	 * ��λ��λ״̬ ��ʼ��� : 0 �ɹ���λ0->1: 1 ���س�ʼ1->0: 2
	 */
	// �������� �����ղ� ���ڴ���
	public static final int RUNNING = -1;
	public static final int COLLECTING = -2;
	public static final int WAITTING = -3;
	/**
	 * ��ǰ����״̬
	 */
	private int closeStatus = RUNNING;

	public static final int INITIAL = 0;// ��ʼ���
	public static final int RESETED_UP = 1;// �ɹ���λ
	public static final int RESETED_DOWN = 2;// ���س�ʼ
	/**
	 * ��ǰ������λ״̬
	 */
	private int resetStatus = INVALID;
	/**
	 * ��ǰ������λ״̬
	 */
	private int dynStatus = INITIAL;
	/**
	 * ��λ����߳�
	 */
	private SingletonHandler resetHandler = new SingletonHandler();
	/**
	 * ��������߳�
	 */
	private SingletonHandler closeHandler = new SingletonHandler();

	/**
	 * ��Ӧ�ڸ�λ���
	 */
	private Runnable resetRunnable = new Runnable() {
		@Override
		public void run() {
			BluetoothCommand mBC = BluetoothCommand.getInstance();
			if (mBC != null) {
				if (dynStatus == INITIAL) {
					if (mBC.getValue(BluetoothCommand.INIT_POSITION_STATUS) == BluetoothCommand.INIT_POSITION_STATUS_INVALID) {
						// ��λ״̬Ϊ0
						resetStatus = RESETING;
						resetHandler.postDelayed(resetRunnable,
								BluetoothCommand.DELAY_TIME);
						// Log.e("��λ״̬Ϊ0", System.currentTimeMillis() + "");
					} else if (mBC
							.getValue(BluetoothCommand.INIT_POSITION_STATUS) == BluetoothCommand.INIT_POSITION_STATUS_VALID) {
						// ��λ״̬Ϊ1
						resetStatus = RESETED;
						dynStatus = RESETED_UP;
						resetHandler.sendEmptyMessage(0);
						SparseIntArray map = new SparseIntArray();
						map.put(BluetoothCommand.INIT_POSITION_STATUS,
								mBC.getValue(BluetoothCommand.INIT_POSITION_STATUS));
						map.put(BluetoothCommand.DIRECTION_STATUS,
								mBC.getValue(BluetoothCommand.DIRECTION_STATUS));
						if (uiHandler != null
								&& uiHandler
										.getClass()
										.getSimpleName()
										.equalsIgnoreCase(
												"FragAnimationTabletNew"))
							uiHandler.sendMachineMessage(
									BluetoothCommand.INIT_POSITION_STATUS, map);
						// Log.e("��λ״̬Ϊ1", System.currentTimeMillis() + "");
					}
				}

				/*
				 * else if(dynStatus==RESETED_UP){ Log.e("����״̬",
				 * mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS)+""); if
				 * (mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS) ==
				 * BluetoothCommand.MACHINE_RUN_STATUS_COLLECT) { //���ղ�״̬
				 * resetStatus = RESETING;
				 * resetHandler.postDelayed(resetRunnable,
				 * BluetoothCommand.DELAY_TIME); Log.e("�ղ�״̬",
				 * System.currentTimeMillis() + ""); } else if
				 * (mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS) ==
				 * BluetoothCommand.MACHINE_RUN_STATUS_WAIT) { //����״̬
				 * resetStatus = INVALID; dynStatus=RESETED_DOWN;
				 * resetHandler.sendEmptyMessage(0); Log.e("����״̬",
				 * System.currentTimeMillis() + ""); } }
				 */
			}
		}
	};
	/**
	 * �����Ƿ�رռ��
	 */
	private Runnable closeRunnable = new Runnable() {
		@Override
		public void run() {
			BluetoothCommand mBC = BluetoothCommand.getInstance();
			if (mBC != null) {
				if (mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS) == BluetoothCommand.MACHINE_RUN_STATUS_RUNNING) {
					closeStatus = RUNNING;
					closeHandler.postDelayed(closeRunnable,
							BluetoothCommand.DELAY_TIME);
				} else if (mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS) == BluetoothCommand.MACHINE_RUN_STATUS_COLLECT) {
					closeStatus = COLLECTING;
					closeHandler.postDelayed(closeRunnable,
							BluetoothCommand.DELAY_TIME);
				} else if (mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS) == BluetoothCommand.MACHINE_RUN_STATUS_WAIT) {
					closeStatus = WAITTING;
				
					SparseIntArray map = new SparseIntArray();
					map.put(BluetoothCommand.MACHINE_RUN_STATUS,
							mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS));

				
				}
			}
		}
	};

	/**
	 * �жϻ����Ƿ�λ isLog �Ƿ���ʾ��ʾ
	 * 
	 * @return
	 */
	public boolean isReseted(boolean isLog) {
		if (resetStatus == INVALID || dynStatus == RESETED_DOWN) {
			if (!resetHandler.isWaiting()) {
				resetHandler.postDelayed(resetRunnable,
						BluetoothCommand.DELAY_TIME * 2);
				resetHandler.setWaiting(true);
			}
			return false;
		} else if (resetStatus == RESETING) {
			if (isLog && uiHandler != null)
				Toast.makeText(uiHandler.getActivity(),
						"The machine is reseting now", 1000).show();
			return false;
		} else if (resetStatus == RESETED)
			return true;

		return false;
	}
	

	@Override
	public void initlize() {
		dynStatus = INITIAL;
		resetStatus = INVALID;
		super.initlize();
	}
	/**
	 * �жϻ����Ƿ�ر�
	 */
	public boolean isClosed(){
		if(closeStatus==RUNNING){
			if(!closeHandler.isWaiting()){
				closeHandler.postDelayed(closeRunnable,
						BluetoothCommand.DELAY_TIME * 2);
				closeHandler.setWaiting(true);
			}
			return false;
		}else if(closeStatus==COLLECTING){
			
			return false;
		}else if(closeStatus==WAITTING){
			return true;
		}
		return false;
	}
	// �ж��Ƿ�ر�
	@Override
	public int  closeOrNot(){
		switch (closeStatus) {
		case RUNNING:
			if(!closeHandler.isWaiting()){
				closeHandler.postDelayed(closeRunnable,
						BluetoothCommand.DELAY_TIME * 2);
				closeHandler.setWaiting(true);
			}
			break;
		case COLLECTING:
			break;
		case WAITTING:
			break;
		
		}
		return 0;
	}
	
	@Override
	public int startOrStop(boolean isLog) {
		switch (dynStatus) {
		case INITIAL:
			break;
		case RESETED_UP:
			if (!resetHandler.isWaiting()) {
				resetHandler.postDelayed(resetRunnable,
						BluetoothCommand.DELAY_TIME * 2);
				resetHandler.setWaiting(true);
			}
			if (isLog && uiHandler != null && resetStatus == RESETING)
				Toast.makeText(
						uiHandler.getActivity(),
						"Please wait a moment until the machine finishes reseting",
						1000).show();
			break;
		case RESETED_DOWN:
			break;
		}
		return dynStatus;
	}

	@Override
	public void stopThreads() {
		// TODO Auto-generated method stub
		super.stopThreads();
	}

	/**
	 * �����߳�
	 * 
	 * @author Desmond Duan
	 * 
	 */
	class SingletonHandler extends Handler {
		private boolean isRunning = false;// �ж��߳��Ƿ���ִ��

		@Override
		public void handleMessage(Message msg) {
			isRunning = false;
		}

		public boolean isWaiting() {
			return isRunning;
		}

		public void setWaiting(boolean isRunning) {
			this.isRunning = isRunning;
		}
	}
}
