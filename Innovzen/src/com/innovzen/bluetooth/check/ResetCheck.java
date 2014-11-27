package com.innovzen.bluetooth.check;

import java.lang.Character.UnicodeBlock;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseIntArray;
import android.widget.Toast;

import com.innovzen.bluetooth.BluetoothCommand;

/**
 * 复位检查类
 * 
 * @author Desmond Duan
 * 
 */
public class ResetCheck extends CheckBase {

	/**
	 * 机器复位状态
	 */
	private static final int INVALID = 0;// 游离状态
	private static final int RESETING = 1;// 正在复位，第8字节第七位为0
	private static final int RESETED = 2;// 复位成功

	/**
	 * 复位移位状态 初始情况 : 0 成功复位0->1: 1 返回初始1->0: 2
	 */
	// 正在运行 正在收藏 正在待机
	public static final int RUNNING = -1;
	public static final int COLLECTING = -2;
	public static final int WAITTING = -3;
	/**
	 * 当前结束状态
	 */
	private int closeStatus = WAITTING;

	/**
	 * 当前机器复位状态
	 */
	private int resetStatus = INVALID;
	/**
	 * 复位检测线程
	 */
	private SingletonHandler resetHandler = new SingletonHandler();
	/**
	 * 结束检测线程
	 */
	private SingletonHandler closeHandler = new SingletonHandler();

	/**
	 * 对应于复位检测
	 */
	private Runnable resetRunnable = new Runnable() {
		@Override
		public void run() {
			BluetoothCommand mBC = BluetoothCommand.getInstance();
			if (mBC != null) {
				if (mBC.getValue(BluetoothCommand.INIT_POSITION_STATUS) == BluetoothCommand.INIT_POSITION_STATUS_INVALID) {
					// 复位状态为0
					resetStatus = RESETING;
					resetHandler.postDelayed(resetRunnable,
							BluetoothCommand.DELAY_TIME);
					// Log.e("复位状态为0", System.currentTimeMillis() + "");
				} else if (mBC.getValue(BluetoothCommand.INIT_POSITION_STATUS) == BluetoothCommand.INIT_POSITION_STATUS_VALID) {
					// 复位状态为1
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
					// Log.e("复位状态为1", System.currentTimeMillis() + "");
				}
				

				/*
				 * else if(dynStatus==RESETED_UP){ Log.e("运行状态",
				 * mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS)+""); if
				 * (mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS) ==
				 * BluetoothCommand.MACHINE_RUN_STATUS_COLLECT) { //在收藏状态
				 * resetStatus = RESETING;
				 * resetHandler.postDelayed(resetRunnable,
				 * BluetoothCommand.DELAY_TIME); Log.e("收藏状态",
				 * System.currentTimeMillis() + ""); } else if
				 * (mBC.getValue(BluetoothCommand.MACHINE_RUN_STATUS) ==
				 * BluetoothCommand.MACHINE_RUN_STATUS_WAIT) { //待机状态
				 * resetStatus = INVALID; dynStatus=RESETED_DOWN;
				 * resetHandler.sendEmptyMessage(0); Log.e("待机状态",
				 * System.currentTimeMillis() + ""); } }
				 */
			}
		}
	};
	/**
	 * 机器是否关闭检测
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
	 * 判断机器是否复位 isLog 是否显示提示
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
	
	// 判断是否关闭
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
	 * 单例线程
	 * 
	 * @author Desmond Duan
	 * 
	 */
	class SingletonHandler extends Handler {
		private boolean isRunning = false;// 判断线程是否在执行

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
