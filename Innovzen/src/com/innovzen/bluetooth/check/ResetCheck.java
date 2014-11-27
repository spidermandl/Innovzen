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
	private int closeStatus = WAITTING;

	/**
	 * ��ǰ������λ״̬
	 */
	private int resetStatus = INVALID;
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
				if (mBC.getValue(BluetoothCommand.INIT_POSITION_STATUS) == BluetoothCommand.INIT_POSITION_STATUS_INVALID) {
					// ��λ״̬Ϊ0
					resetStatus = RESETING;
					resetHandler.postDelayed(resetRunnable,
							BluetoothCommand.DELAY_TIME);
					// Log.e("��λ״̬Ϊ0", System.currentTimeMillis() + "");
				} else if (mBC.getValue(BluetoothCommand.INIT_POSITION_STATUS) == BluetoothCommand.INIT_POSITION_STATUS_VALID) {
					// ��λ״̬Ϊ1
					resetStatus = RESETED;
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
				Log.e("closeStatus", mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS)+"");
				if (mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS) == BluetoothCommand.MACHINE_RUN_STATUS_RUNNING) {
					closeStatus = RUNNING;
					closeHandler.postDelayed(closeRunnable,
							BluetoothCommand.DELAY_TIME);
					Log.e("Running", "-11111111111111");
				} else if (mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS) == BluetoothCommand.MACHINE_RUN_STATUS_COLLECT) {
					closeStatus = COLLECTING;
					closeHandler.postDelayed(closeRunnable,
							BluetoothCommand.DELAY_TIME);
					Log.e("Collecting", "-222222222222222");
				} else if (mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS) == BluetoothCommand.MACHINE_RUN_STATUS_WAIT) {
					closeStatus = WAITTING;
					Log.e("Waitting", "-3333333333333");
					SparseIntArray map = new SparseIntArray();
					map.put(BluetoothCommand.MACHINE_RUN_STATUS,
							mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS));
					closeHandler.sendEmptyMessage(0);

				
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
		if (resetStatus == INVALID) {
			if (!resetHandler.isRunning()) {
				resetHandler.postDelayed(resetRunnable,
						BluetoothCommand.DELAY_TIME * 2);
				resetHandler.setRunning(true);
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
		closeStatus = WAITTING;
		resetStatus = INVALID;
		super.initlize();
	}
	
	// �ж��Ƿ�ر�
	@Override
	public int  closeOrNot(){
//		switch (closeStatus) {
//		case RUNNING:
//			if(!closeHandler.isRunning()){
//				closeHandler.postDelayed(closeRunnable,
//						BluetoothCommand.DELAY_TIME * 2);
//				closeHandler.setRunning(true);
//			}
//			break;
//		case COLLECTING:
//			break;
//		case WAITTING:
//			break;
//		
//		}
		if(!closeHandler.isRunning()){
			closeHandler.postDelayed(closeRunnable,
					BluetoothCommand.DELAY_TIME * 2);
			closeHandler.setRunning(true);
		}
		return closeStatus;
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

		public boolean isRunning() {
			return isRunning;
		}

		public void setRunning(boolean isRunning) {
			this.isRunning = isRunning;
		}
	}
}
