package com.innovzen.handlers;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.innovzen.bluetooth.BluetoothCommand;
import com.innovzen.entities.ExerciseTimes;
import com.innovzen.entities.SoundItem;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.interfaces.FragmentCommunicator;

/**
 * 
 * @author Desmond 实现同步功能exercise动画
 */
public class SyncExerciseManager extends ExerciseManager {

	/**
	 * 通讯时间补偿
	 */
	static final int DELTA_TIME = 0;

	/**
	 * 等待机器传送最后位行指令 waitHandler的实例只能有一份，当isRunning为true时，此时正在进行等待状态
	 */
	private WaitHandler waitHandler = new WaitHandler(),// 等待线程，等代时间戳达到相位
			exhaleHoldHandler = new WaitHandler(),// 呼气屏住等待线程
			inhaleExerciseHandler = new WaitHandler(),// 吸气等待线程
			inhaleHoldHandler = new WaitHandler(),// 吸气屏住线程
			exhaleExerciseHandler = new WaitHandler();// 呼气等待线程

	/**
	 * 校验相位和方向
	 */
	private PositionCheckRunnable 
			exhaleHoldRunnable = new PositionCheckRunnable(EXERCISE_HOLD_EXHALE),// 呼气屏住等待逻辑
			inhaleExerciseRunnable = new PositionCheckRunnable(EXERCISE_INHALE),// 吸气等待逻辑
			inhaleHoldRunnable = new PositionCheckRunnable(EXERCISE_HOLD_INHALE),// 吸气屏住等待逻辑
			exhaleExerciseRunnable = new PositionCheckRunnable(EXERCISE_EXHALE);// 呼气等待逻辑

	/**
	 * 同步时间误差
	 */
	private long inhaleTimeStart = 0,// 吸气开始时间
			inhaleTimeEnd = 0,// 吸气结束时间
			exhaleTimeStart = 0,// 呼气开始时间
			exhaleTimeEnd = 0;// 呼气结束时间

	/**
	 * 等待线程处理方法
	 */
	private Runnable waitRunnable = new Runnable() {
		public void run() {
			long subtime;
			switch (mCurExercise) {
			case EXERCISE_INHALE:
				subtime = BluetoothCommand.getInstance() == null ? 0 : (System.currentTimeMillis() - inhaleTimeEnd);
				if (subtime != 0 && subtime < mTimes.inhale + DELTA_TIME) {
					 //Log.e("inhale 等待成功", subtime+"");
					waitHandler.sendEmptyMessage(0);
				} else {
					//Log.e("inhale 没等到", subtime+"");
					boolean shutOff = 
							BluetoothCommand.getInstance() == null ? false: 
								(BluetoothCommand.getInstance().getValue(BluetoothCommand.MACHINE_RUN_STATUS) == BluetoothCommand.MACHINE_RUN_STATUS_COLLECT ? true: false);
                    
//					boolean isPause=
//							BluetoothCommand.getInstance() == null ? false: 
//								(BluetoothCommand.getInstance().getValue(BluetoothCommand.PAUSE_STATUS) == BluetoothCommand.PAUSE_STATUS_ON ? true: false);
					////Log.e("EXERCISE_INHALE pause", String.valueOf(isPause));
					if (!shutOff)
						waitHandler.postDelayed(waitRunnable,BluetoothCommand.DELAY_TIME);
					else
						waitHandler.setWaiting(false);
				}
				break;
			case EXERCISE_HOLD_INHALE:
				subtime = BluetoothCommand.getInstance() == null ? 0 : (System.currentTimeMillis() - exhaleTimeStart);
				if (subtime != 0 && subtime < mTimes.holdInhale) {
					//Log.e("EXERCISE_HOLD_INHALE 等待成功", subtime+"");
					waitHandler.sendEmptyMessage(0);
				} else {
					//Log.e("EXERCISE_HOLD_INHALE 没等到", subtime+"");
					boolean shutOff = BluetoothCommand.getInstance() == null ? false
							: (BluetoothCommand.getInstance().getValue(
									BluetoothCommand.MACHINE_RUN_STATUS) == BluetoothCommand.MACHINE_RUN_STATUS_COLLECT ? true
									: false);
//					boolean isPause=
//							BluetoothCommand.getInstance() == null ? false: 
//								(BluetoothCommand.getInstance().getValue(BluetoothCommand.PAUSE_STATUS) == BluetoothCommand.PAUSE_STATUS_ON ? true: false);
					////Log.e("EXERCISE_HOLD_INHALE pause", String.valueOf(isPause));
					if (!shutOff)
						waitHandler.postDelayed(waitRunnable,
								BluetoothCommand.DELAY_TIME);
					else
						waitHandler.setWaiting(false);
				}
				break;
			case EXERCISE_EXHALE:
				subtime = BluetoothCommand.getInstance() == null ? 
						0 : (System.currentTimeMillis() - exhaleTimeEnd);
				if (subtime != 0 && subtime < mTimes.exhale + DELTA_TIME) {
					//Log.e("exhale 等待成功", subtime+"");
					waitHandler.sendEmptyMessage(0);
				} else {
					//Log.e("exhale 没等到", subtime+"");
					boolean shutOff = BluetoothCommand.getInstance() == null ? false
							: (BluetoothCommand.getInstance().getValue(
									BluetoothCommand.MACHINE_RUN_STATUS) == BluetoothCommand.MACHINE_RUN_STATUS_COLLECT ? true
									: false);
//					boolean isPause=
//							BluetoothCommand.getInstance() == null ? false: 
//								(BluetoothCommand.getInstance().getValue(BluetoothCommand.PAUSE_STATUS) == BluetoothCommand.PAUSE_STATUS_ON ? true: false);
					////Log.e("EXERCISE_EXHALE pause", String.valueOf(isPause));
					if (!shutOff)
						waitHandler.postDelayed(waitRunnable,
								BluetoothCommand.DELAY_TIME);
					else
						waitHandler.setWaiting(false);
				}
				break;
			case EXERCISE_HOLD_EXHALE:
				subtime = BluetoothCommand.getInstance() == null ? 0 : (System
						.currentTimeMillis() - inhaleTimeStart);
				if (subtime != 0 && subtime < mTimes.holdExhale) {
					//Log.e("EXERCISE_HOLD_EXHALE 等待成功", subtime+"");
					waitHandler.sendEmptyMessage(0);
				} else {
					//Log.e("EXERCISE_HOLD_EXHALE 没等到", subtime+"");
					boolean shutOff = BluetoothCommand.getInstance() == null ? false
							: (BluetoothCommand.getInstance().getValue(
									BluetoothCommand.MACHINE_RUN_STATUS) == BluetoothCommand.MACHINE_RUN_STATUS_COLLECT ? true
									: false);
//					boolean isPause=
//							BluetoothCommand.getInstance() == null ? false: 
//								(BluetoothCommand.getInstance().getValue(BluetoothCommand.PAUSE_STATUS) == BluetoothCommand.PAUSE_STATUS_ON ? true: false);
					////Log.e("EXERCISE_HOLD_EXHALE pause", String.valueOf(isPause));
					if (!shutOff)
						waitHandler.postDelayed(waitRunnable,
								BluetoothCommand.DELAY_TIME);
					else
						waitHandler.setWaiting(false);
				}
				break;

			default:
				break;
			}

		}
	};

	/********************************************************************************************
	 * 一下为方法
	 ********************************************************************************************/
	/**
	 * 构造函数
	 * 
	 * @param fragmentAnimation
	 * @param animationHandler
	 * @param soundHandler
	 * @param times
	 * @param voiceSoundId
	 * @param ambianceSoundId
	 */
	public SyncExerciseManager(FragAnimationBase fragmentAnimation,
			ExerciseAnimationHandler animationHandler,
			FragmentCommunicator soundHandler, ExerciseTimes times,
			int voiceSoundId, int ambianceSoundId) {
		super(fragmentAnimation, animationHandler, soundHandler, times,
				voiceSoundId, ambianceSoundId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reinitUI(FragAnimationBase fragmentAnimation,
			ExerciseAnimationHandler animationHandler) {
		super.reinitUI(fragmentAnimation, animationHandler);
	}

	/**
	 * 矫正时间同步
	 */
	@Override
	protected void startAppropriateExerciseType(float fraction) {
		boolean isContinue = true;// 机器运动滞后则置为false
		long subtime;
		switch (mCurExercise) {
		case EXERCISE_INHALE:
			/**
			 * 获取最近一次12行位的时间 如果当前时间减去这个时间和差小于mTimes.inhale
			 * 说明按摩椅运动快了，那么直接调用startAppropriateExerciseType(1f);
			 * 如果当前时间减去这个时间和差大于mTimes.inhale并且animation.getAnimatedFraction()
			 * 这个值为1f， 说明本轮按摩椅运动慢了，那么就等待，以100ms为单位等待
			 */

			subtime = BluetoothCommand.getInstance() == null ? 0 : (System
					.currentTimeMillis() - inhaleTimeEnd);
			if (subtime != 0 && subtime < mTimes.inhale + DELTA_TIME) {// 机器运动超前

				// long a = mTimes.inhale;
				// //Log.e("INHALE 超前", subtime+"");
				fraction = 1f;
				break;
			} else if (subtime > mTimes.inhale + DELTA_TIME && fraction == 1f) {// 机器运动滞后
				// //Log.e("INHALE 滞后", subtime+"");
				if (!waitHandler.isWaiting()) {
					waitHandler.setWaiting(true);
					waitHandler.postDelayed(waitRunnable,
							BluetoothCommand.DELAY_TIME); // 100 ms
				}
				isContinue = false;
			} else {// 正常运作状态
					// //Log.e("INHALE 正常运作状态", "fraction"+fraction);
			}

			break;
		case EXERCISE_HOLD_INHALE:
			subtime = BluetoothCommand.getInstance() == null ? 0 : (System
					.currentTimeMillis() - exhaleTimeStart);
			if (subtime != 0 && subtime < mTimes.holdInhale) {// 机器运动超前
				// //Log.e("INHALE HOLE 超前", subtime+"");
				fraction = 1f;
				break;
			} else if (subtime > mTimes.holdInhale && fraction == 1f) {// 机器运动滞后
				// //Log.e("INHALE HOLE 滞后", subtime+"");
				if (!waitHandler.isWaiting()) {
					waitHandler.setWaiting(true);
					waitHandler.postDelayed(waitRunnable,
							BluetoothCommand.DELAY_TIME); // 1 ms
				}
				isContinue = false;
			} else {// 正常运作状态
					// //Log.e("INHALE HOLE 正常运作状态", subtime+"");
			}
			break;
		case EXERCISE_EXHALE:
			subtime = BluetoothCommand.getInstance() == null ? 0 : (System
					.currentTimeMillis() - exhaleTimeEnd);
			if (subtime != 0 && subtime < mTimes.exhale + DELTA_TIME) {// 机器运动超前
				// //Log.e("EXHALE 超前", subtime+"");
				fraction = 1f;
				break;
			} else if (subtime > mTimes.exhale + DELTA_TIME && fraction == 1f) {// 机器运动滞后
				// //Log.e("EXHALE 滞后", subtime+"");
				if (!waitHandler.isWaiting()) {
					waitHandler.setWaiting(true);
					waitHandler.postDelayed(waitRunnable,
							BluetoothCommand.DELAY_TIME); // 1 ms
				}
				isContinue = false;

			} else {// 正常运作状态
					// //Log.e("EXHALE 正常运作状态", subtime+"");
			}

			break;
		case EXERCISE_HOLD_EXHALE:
			subtime = BluetoothCommand.getInstance() == null ? 0 : (System
					.currentTimeMillis() - inhaleTimeStart);
			if (subtime != 0 && subtime < mTimes.holdExhale) {// 机器运动超前
				// //Log.e("EXHALE HOLD 超前", subtime+"");
				fraction = 1f;
				break;
			} else if (subtime > mTimes.holdExhale && fraction == 1f) {// 机器运动滞后
				// //Log.e("EXHALE HOLD 滞后", subtime+"");
				if (!waitHandler.isWaiting()) {
					waitHandler.setWaiting(true);
					waitHandler.postDelayed(waitRunnable,
							BluetoothCommand.DELAY_TIME); // 1 ms
				}
				isContinue = false;

			} else {// 正常运作状态
					// //Log.e("EXHALE HOLD 正常运作状态", subtime+"");
			}

			break;
		}

		if (isContinue)
			super.startAppropriateExerciseType(fraction);
	}

	/**
	 * 等待Handler
	 * 
	 * @author desmond.duan
	 * 
	 */
	class WaitHandler extends Handler {
		private boolean isWaiting = false;// 判断线程是否在执行

		@Override
		public void handleMessage(Message msg) {
			SyncExerciseManager.super.startAppropriateExerciseType(1f);
			isWaiting = false;
		}

		public boolean isWaiting() {
			return isWaiting;
		}

		public void setWaiting(boolean isWaiting) {
			this.isWaiting = isWaiting;
		}
	}

	@Override
	public void start() {
		// //Log.e("--------------------------------打开动画", "on");
		inhaleExerciseHandler.setWaiting(true);
		inhaleExerciseHandler.post(inhaleExerciseRunnable);
		inhaleHoldHandler.setWaiting(true);
		inhaleHoldHandler.post(inhaleHoldRunnable);
		exhaleExerciseHandler.setWaiting(true);
		exhaleExerciseHandler.post(exhaleExerciseRunnable);
		exhaleHoldHandler.setWaiting(true);
		exhaleHoldHandler.post(exhaleHoldRunnable);
		super.start();
	}

	@Override
	public void pause(boolean showPlayBtnOverlay) {
		// //Log.e("--------------------------------关闭动画", "off");
		super.pause(showPlayBtnOverlay);
		
	}
   @Override
	public void stopAllThreads() {
		inhaleExerciseHandler.setWaiting(false);
		inhaleHoldHandler.setWaiting(false);
		exhaleExerciseHandler.setWaiting(false);
		exhaleHoldHandler.setWaiting(false);
		
	}

	@Override
	protected void inhale(float fraction, float globalFraction) {
		// Start appropriate sounds
		playSounds(SoundItem.INSPIREZ, mTimes.inhale);

		// Set the volume of the ambiance sound
		setAmbianceVolume(fraction);

		// Render animation frame
		if (!isSoundOnly&&mAnimationHandler!=null)
			mAnimationHandler.inhale(fraction, globalFraction);

		// If step animation ended
		if (fraction == 1f) {
			// Set the flag to point to the next appropriate step
			mCurExercise = EXERCISE_HOLD_INHALE;

			// Restart the value animator
			if (mTimes.holdInhale == 1000) {
				startValueAnimator(2000); // HACK. FORCE IT TO BE AT LEAST 2 SEC
			} else {
				startValueAnimator(mTimes.holdInhale);
			}

			// Reset the flag that indicates if we've started the sounds for the
			// new step
			mPlayedSounds = false;

		}
	}

	@Override
	protected void exhale(float fraction, float globalFraction) {
		// Start appropriate sounds
		playSounds(SoundItem.EXPIREZ, mTimes.exhale);

		// Set the volume of the ambiance sound
		setAmbianceVolume(fraction);

		// Render animation frame
		if (!isSoundOnly&&mAnimationHandler!=null)
			mAnimationHandler.exhale(fraction, globalFraction);

		// If step animation ended
		if (fraction == 1f) {

			// Check if the entire exercise is done. If so, then stop here
			if (globalFraction == 1f) {

				reset(true);

				BluetoothCommand mBluetoothCommand =BluetoothCommand.getInstance();
                if(mBluetoothCommand!=null){
                         mBluetoothCommand.sendCommand(BluetoothCommand.START_MACHINE_VALUES);
                       //  mTimes.exerciseDuration=30*1000;
                         if(mFragAnimation!=null){
                        	 //chy
                        	// mFragAnimation.stopExercise();
                        	 //chy
                         }
                         //Log.e("关机", "关机！！！！！！！！！！！！！！！！！！！！！！");
                }
				
				return;
			}

			// Set the flag to point to the next appropriate step
			mCurExercise = EXERCISE_HOLD_EXHALE;

			// Restart the value animator
			if (mTimes.holdExhale == 1000) {
				startValueAnimator(2000); // HACK. FORCE IT TO BE AT LEAST 2 SEC
			} else {
				startValueAnimator(mTimes.holdExhale);
			}

			// Reset the flag that indicates if we've started the sounds for the
			// new step
			mPlayedSounds = false;

		}
	}

	@Override
	protected void holdInhale(float fraction, float globalFraction) {
		if (mTimes.holdInhale > 0) {
			// Start appropriate sounds
			playSounds(SoundItem.RETENEZ, mTimes.holdInhale);

			// Set the volume of the ambiance sound
			setAmbianceVolume(fraction);
		}

		// Render animation frame
		if (!isSoundOnly&&mAnimationHandler!=null)
			mAnimationHandler.holdInhale(fraction, globalFraction);

		// If step animation ended
		if (fraction == 1f) {
			// Set the flag to point to the next appropriate step
			mCurExercise = EXERCISE_EXHALE;

			// Restart the value animator
			startValueAnimator(mTimes.exhale);

			// Reset the flag that indicates if we've started the sounds for the
			// new step
			mPlayedSounds = false;

		}
	}

	@Override
	protected void holdExhale(float fraction, float globalFraction) {
		if (mTimes.holdExhale > 0) {
			// Start appropriate sounds
			playSounds(SoundItem.RETENEZ, mTimes.holdExhale);

			// Set the volume of the ambiance sound
			setAmbianceVolume(fraction);
		}

		// Render animation frame
		if (!isSoundOnly&&mAnimationHandler!=null)
			mAnimationHandler.holdExhale(fraction, globalFraction);

		// If step animation ended
		if (fraction == 1f) {
			// Set the flag to point to the next appropriate step
			mCurExercise = EXERCISE_INHALE;

			// Restart the value animator
			startValueAnimator(mTimes.inhale);

			// Reset the flag that indicates if we've started the sounds for the
			// new step
			mPlayedSounds = false;

		}
	}

	@Override
	protected void playSounds(int soundType, int curStepDuration) {
		//mPlayedSounds=false;
		super.playSounds(soundType, curStepDuration);
	}
	
	/**
	 * 相位限等待逻辑
	 * 
	 * @author Desmond Duan
	 * 
	 */
	class PositionCheckRunnable implements Runnable {

		int exerciseType;
		int lastPositison = BluetoothCommand.WALKING_POSITION_STATUS5;
		int lastDirection = BluetoothCommand.DIRECTION_STATUS_RETAIN;

		PositionCheckRunnable(int type) {
			exerciseType = type;
		}

		@Override
		public void run() {
			switch (exerciseType) {
			case EXERCISE_INHALE:
				if (!inhaleExerciseHandler.isWaiting()) {
				} else {
					BluetoothCommand mBC = BluetoothCommand.getInstance();
					if (mBC != null) {
						int currentPos = mBC
								.getValue(BluetoothCommand.WALKING_POSITION_STATUS);
						int currentDir = mBC
								.getValue(BluetoothCommand.DIRECTION_STATUS);
						if ((lastPositison == BluetoothCommand.WALKING_POSITION_STATUS11 && currentPos == BluetoothCommand.WALKING_POSITION_STATUS12)
								|| (lastDirection == BluetoothCommand.DIRECTION_STATUS_UP && currentDir == BluetoothCommand.DIRECTION_STATUS_STOP)) {
							// if((lastPositison==BluetoothCommand.WALKING_POSITION_STATUS12&&currentPos==BluetoothCommand.WALKING_POSITION_STATUS11)||
							// (lastDirection==BluetoothCommand.DIRECTION_STATUS_STOP&&currentDir==BluetoothCommand.DIRECTION_STATUS_DOWN)){
							/**
							 * 相位由11跳12 或方向位由上限跳为停止
							 */
							inhaleTimeEnd = System.currentTimeMillis();
						}
						lastPositison = currentPos;
						lastDirection = currentDir;
					}
					inhaleExerciseHandler.postDelayed(inhaleExerciseRunnable,
							BluetoothCommand.DELAY_TIME / 2);
				}
				break;
			case EXERCISE_HOLD_INHALE:
				if (!inhaleHoldHandler.isWaiting()) {
				} else {
					BluetoothCommand mBC = BluetoothCommand.getInstance();
					if (mBC != null) {
						int currentPos = mBC
								.getValue(BluetoothCommand.WALKING_POSITION_STATUS);
						int currentDir = mBC
								.getValue(BluetoothCommand.DIRECTION_STATUS);
						if ((lastPositison == BluetoothCommand.WALKING_POSITION_STATUS12 && currentPos == BluetoothCommand.WALKING_POSITION_STATUS11)
								|| (lastDirection == BluetoothCommand.DIRECTION_STATUS_STOP && currentDir == BluetoothCommand.DIRECTION_STATUS_DOWN)) {
							/**
							 * 相位由12跳11 或方向位由停止跳为下限
							 */
							exhaleTimeStart = System.currentTimeMillis();
						}
						lastPositison = currentPos;
						lastDirection = currentDir;
					}
					inhaleHoldHandler.postDelayed(inhaleHoldRunnable,
							BluetoothCommand.DELAY_TIME / 2);
				}
				break;
			case EXERCISE_EXHALE:
				if (!exhaleExerciseHandler.isWaiting()) {
				} else {
					BluetoothCommand mBC = BluetoothCommand.getInstance();
					if (mBC != null) {
						int currentPos = mBC
								.getValue(BluetoothCommand.WALKING_POSITION_STATUS);
						int currentDir = mBC
								.getValue(BluetoothCommand.DIRECTION_STATUS);
						if ((lastPositison == BluetoothCommand.WALKING_POSITION_STATUS2 && currentPos == BluetoothCommand.WALKING_POSITION_STATUS1)
								|| (lastDirection == BluetoothCommand.DIRECTION_STATUS_DOWN && currentDir == BluetoothCommand.DIRECTION_STATUS_STOP)) {
							// if((lastPositison==BluetoothCommand.WALKING_POSITION_STATUS1&&currentPos==BluetoothCommand.WALKING_POSITION_STATUS2)||
							// (lastDirection==BluetoothCommand.DIRECTION_STATUS_STOP&&currentDir==BluetoothCommand.DIRECTION_STATUS_UP)){
							/**
							 * 相位由2跳1 或方向位由下限跳为停止
							 */
							exhaleTimeEnd = System.currentTimeMillis();
						}
						lastPositison = currentPos;
						lastDirection = currentDir;
					}
					exhaleExerciseHandler.postDelayed(exhaleExerciseRunnable,
							BluetoothCommand.DELAY_TIME / 2);
				}
				break;
			case EXERCISE_HOLD_EXHALE:
				if (!exhaleHoldHandler.isWaiting()) {
				} else {
					BluetoothCommand mBC = BluetoothCommand.getInstance();
					if (mBC != null) {
						int currentPos = mBC
								.getValue(BluetoothCommand.WALKING_POSITION_STATUS);
						int currentDir = mBC
								.getValue(BluetoothCommand.DIRECTION_STATUS);
						if ((lastPositison == BluetoothCommand.WALKING_POSITION_STATUS1 && currentPos == BluetoothCommand.WALKING_POSITION_STATUS2)
								|| (lastDirection == BluetoothCommand.DIRECTION_STATUS_STOP && currentDir == BluetoothCommand.DIRECTION_STATUS_UP)) {
							/**
							 * 相位由1跳2 或方向位由停止跳为上限
							 */
							inhaleTimeStart = System.currentTimeMillis();
						}
						lastPositison = currentPos;
						lastDirection = currentDir;
					}
					exhaleHoldHandler.postDelayed(exhaleHoldRunnable,
							BluetoothCommand.DELAY_TIME / 2);
				}
				break;

			default:
				break;
			}
		}
	}

}
