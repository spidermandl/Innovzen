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
	private int closeStatus = RUNNING;

	public static final int INITIAL = 0;// 初始情况
	public static final int RESETED_UP = 1;// 成功复位
	public static final int RESETED_DOWN = 2;// 返回初始
	/**
	 * 当前机器复位状态
	 */
	private int resetStatus = INVALID;
	/**
	 * 当前机器移位状态
	 */
	private int dynStatus = INITIAL;
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
				if (dynStatus == INITIAL) {
					if (mBC.getValue(BluetoothCommand.INIT_POSITION_STATUS) == BluetoothCommand.INIT_POSITION_STATUS_INVALID) {
						// 复位状态为0
						resetStatus = RESETING;
						resetHandler.postDelayed(resetRunnable,
								BluetoothCommand.DELAY_TIME);
						// Log.e("复位状态为0", System.currentTimeMillis() + "");
					} else if (mBC
							.getValue(BluetoothCommand.INIT_POSITION_STATUS) == BluetoothCommand.INIT_POSITION_STATUS_VALID) {
						// 复位状态为1
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
						// Log.e("复位状态为1", System.currentTimeMillis() + "");
					}
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
	 * 判断机器是否复位 isLog 是否显示提示
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
	 * 判断机器是否关闭
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
	// 判断是否关闭
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

		public boolean isWaiting() {
			return isRunning;
		}

		public void setWaiting(boolean isRunning) {
			this.isRunning = isRunning;
		}
	}
}
