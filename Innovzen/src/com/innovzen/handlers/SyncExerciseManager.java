package com.innovzen.handlers;


import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.innovzen.bluetooth.BluetoothCommand;
import com.innovzen.entities.ExerciseTimes;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.interfaces.FragmentCommunicator;

/**
 * 
 * @author Desmond
 * 实现同步功能exercise动画
 */
public class SyncExerciseManager extends ExerciseManager{

	/**
	 * 等待机器传送最后位行指令
	 * waitHandler的实例只能有一份，当isRunning为true时，此时正在进行等待状态
	 */
	private WaitHandler waitHandler=new WaitHandler(),//
	           exhaleHoldHandler=new WaitHandler(),//
	           inhaleExerciseHandler = new WaitHandler(),//
	           inhaleHoldHandler=new WaitHandler(),//
	           exhaleExerciseHandler = new WaitHandler();//
	
	/**
	 * 校验相位和方向
	 */
	private PositionCheckRunnable 
	           exhaleHoldRunnable=new PositionCheckRunnable(EXERCISE_HOLD_EXHALE),
	           inhaleExerciseRunnable = new PositionCheckRunnable(EXERCISE_INHALE),
	           inhaleHoldRunnable=new PositionCheckRunnable(EXERCISE_HOLD_INHALE),
	           exhaleExerciseRunnable = new PositionCheckRunnable(EXERCISE_EXHALE);
	
	/**
	 * 同步时间误差
	 */
	private long inhaleTimeStart=0,//吸气开始时间
			     inhaleTimeEnd=0,//吸气结束时间
			     exhaleTimeStart=0,//呼气开始时间
			     exhaleTimeEnd=0;//呼气结束时间
	
	/**
	 * 等待线程处理方法
	 */
	private Runnable waitRunnable=new Runnable() {
		public void run() {
			long subtime;
			switch (mCurExercise) {
			case EXERCISE_INHALE:
				subtime=BluetoothCommand.getInstance()==null?0:
					(System.currentTimeMillis()-inhaleTimeEnd);
	        	if(subtime!=0&&subtime<mTimes.inhale){
	        		//long a = mTimes.inhale;
	        		Log.e("inhale 等待成功", subtime+"");
	        		waitHandler.sendEmptyMessage(0);
	        	}else{
	        		Log.e("inhale 没等到", subtime+"");
	        		waitHandler.postDelayed(waitRunnable,BluetoothCommand.DELAY_TIME);
	        	}
				break;
			case EXERCISE_HOLD_INHALE:
				subtime=BluetoothCommand.getInstance()==null?0:
					(System.currentTimeMillis()-exhaleTimeStart);
	        	if(subtime!=0&&subtime<mTimes.holdInhale){
	        		Log.e("EXERCISE_HOLD_INHALE 等待成功", subtime+"");
	        		waitHandler.sendEmptyMessage(0);
	        	}else{
	        		Log.e("EXERCISE_HOLD_INHALE 没等到", subtime+"");
	        		waitHandler.postDelayed(waitRunnable,BluetoothCommand.DELAY_TIME);
	        	}
				break;
			case EXERCISE_EXHALE:
				subtime=BluetoothCommand.getInstance()==null?0:
					(System.currentTimeMillis()-exhaleTimeEnd);
	        	if(subtime!=0&&subtime<mTimes.exhale){
	        		Log.e("exhale 等待成功", subtime+"");
	        		waitHandler.sendEmptyMessage(0);
	        	}else{
	        		Log.e("exhale 没等到", subtime+"");
	        		waitHandler.postDelayed(waitRunnable,BluetoothCommand.DELAY_TIME);
	        	}
				break;
			case EXERCISE_HOLD_EXHALE:
				subtime=BluetoothCommand.getInstance()==null?0:
					(System.currentTimeMillis()-inhaleTimeStart);
	        	if(subtime!=0&&subtime<mTimes.holdExhale){
	        		Log.e("EXERCISE_HOLD_EXHALE 等待成功", subtime+"");
	        		waitHandler.sendEmptyMessage(0);
	        	}else{
	        		Log.e("EXERCISE_HOLD_EXHALE 没等到", subtime+"");
	        		waitHandler.postDelayed(waitRunnable,BluetoothCommand.DELAY_TIME);
	        	}
				break;

			default:
				break;
			}

		}
	};
	
	/**
	 * 构造函数
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
		super(fragmentAnimation, animationHandler, soundHandler, times, voiceSoundId,
				ambianceSoundId);
		// TODO Auto-generated constructor stub
	}

    /**
     * 矫正时间同步
     */
	@Override
	protected void startAppropriateExerciseType(float fraction) {
		boolean isContinue=true;//机器运动滞后则置为false
		long subtime;
		switch (mCurExercise) {
        case EXERCISE_INHALE:
        	/**
        	 * 获取最近一次12行位的时间
        	         如果当前时间减去这个时间和差小于mTimes.inhale 说明按摩椅运动快了，那么直接调用startAppropriateExerciseType(1f);
        	      如果当前时间减去这个时间和差大于mTimes.inhale并且animation.getAnimatedFraction() 这个值为1f， 说明本轮按摩椅运动慢了，那么就等待，以100ms为单位等待
        	 */

        	subtime=BluetoothCommand.getInstance()==null?0:
        		(System.currentTimeMillis()-inhaleTimeEnd);
        	if(subtime!=0&&subtime<mTimes.inhale){//机器运动超前

        		//long a = mTimes.inhale;
        		//Log.e("INHALE 超前", subtime+"");
        		fraction=1f;
        		break;
        	}else if(subtime>mTimes.inhale&&fraction==1f){//机器运动滞后
        		//Log.e("INHALE 滞后", subtime+"");
        		if(!waitHandler.isWaiting()){
	        		waitHandler.setWaiting(true);
	        		waitHandler.postDelayed(waitRunnable, BluetoothCommand.DELAY_TIME); // 1 ms
        		}
	        	isContinue=false;
        	}else{//正常运作状态
        		//Log.e("INHALE 正常运作状态", "fraction"+fraction);
        	}
        	
            break;
        case EXERCISE_HOLD_INHALE:
        	subtime=BluetoothCommand.getInstance()==null?0:
        		(System.currentTimeMillis()-exhaleTimeStart);
        	if(subtime!=0&&subtime<mTimes.holdInhale){//机器运动超前
        		//long a = mTimes.inhale;
        		fraction=1f;
        		break;
        	}else if(subtime>mTimes.holdInhale&&fraction==1f){//机器运动滞后
        		if(!waitHandler.isWaiting()){
	        		waitHandler.setWaiting(true);
	        		waitHandler.postDelayed(waitRunnable, BluetoothCommand.DELAY_TIME); // 1 ms
        		}
	        	isContinue=false;
        	}else{//正常运作状态
        		
        	}
            break;
        case EXERCISE_EXHALE:
        	subtime=BluetoothCommand.getInstance()==null?0:
        		(System.currentTimeMillis()-exhaleTimeEnd);
        	if(subtime!=0&&subtime<mTimes.exhale){//机器运动超前
        		//long a = mTimes.inhale;
        		fraction=1f;
        		break;
        	}else if(subtime>mTimes.exhale&&fraction==1f){//机器运动滞后
        		if(!waitHandler.isWaiting()){
	        		waitHandler.setWaiting(true);
	        		waitHandler.postDelayed(waitRunnable, BluetoothCommand.DELAY_TIME); // 1 ms
        		}
        		isContinue=false;

        	}else{//正常运作状态
        		
        	}
        	
            break;
        case EXERCISE_HOLD_EXHALE:
        	subtime=BluetoothCommand.getInstance()==null?0:
        		(System.currentTimeMillis()-inhaleTimeStart);
        	if(subtime!=0&&subtime<mTimes.holdExhale){//机器运动超前
        		//long a = mTimes.inhale;
        		fraction=1f;
        		break;
        	}else if(subtime>mTimes.holdExhale&&fraction==1f){//机器运动滞后
        		if(!waitHandler.isWaiting()){
	        		waitHandler.setWaiting(true);
	        		waitHandler.postDelayed(waitRunnable, BluetoothCommand.DELAY_TIME); // 1 ms
        		}
        		isContinue=false;

        	}else{//正常运作状态
        		
        	}
        	
            break;
		}
		
		if(isContinue)
			super.startAppropriateExerciseType(fraction);
	}
    
	/**
	 * 等待Handler
	 * @author desmond.duan
	 *
	 */
	class WaitHandler extends Handler{
		private boolean isWaiting=false;//判断线程是否在执行
		@Override
		public void handleMessage(Message msg) {
			SyncExerciseManager.super.startAppropriateExerciseType(1f);
			isWaiting=false;
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
//		inhaleExerciseHandler.setWaiting(false);
//		inhaleHoldHandler.setWaiting(false);
//		exhaleExerciseHandler.setWaiting(false);
//		exhaleHoldHandler.setWaiting(false);
		super.pause(showPlayBtnOverlay);
	}
	
	class PositionCheckRunnable implements Runnable{

		int exerciseType;
		int lastPositison=BluetoothCommand.WALKING_POSITION_STATUS5;
		int lastDirection=BluetoothCommand.DIRECTION_STATUS_RETAIN;
		
		PositionCheckRunnable(int type){
			exerciseType=type;
		}
		@Override
		public void run() {
			switch (exerciseType) {
			case EXERCISE_INHALE:
				if(!inhaleExerciseHandler.isWaiting()){
				}else{
					BluetoothCommand mBC=BluetoothCommand.getInstance();
					if(mBC!=null){
						int currentPos=mBC.getValue(BluetoothCommand.WALKING_POSITION_STATUS);
						int currentDir=mBC.getValue(BluetoothCommand.DIRECTION_STATUS);
						if((lastPositison==BluetoothCommand.WALKING_POSITION_STATUS11&&currentPos==BluetoothCommand.WALKING_POSITION_STATUS12)||
								(lastDirection==BluetoothCommand.DIRECTION_STATUS_UP&&currentDir==BluetoothCommand.DIRECTION_STATUS_STOP)){
							inhaleTimeEnd=System.currentTimeMillis();
						}
						lastPositison=currentPos;
						lastDirection=currentDir;
					}
					inhaleExerciseHandler.postDelayed(inhaleExerciseRunnable, BluetoothCommand.DELAY_TIME/2);
				}
				break;
			case EXERCISE_HOLD_INHALE:
				if(!inhaleHoldHandler.isWaiting()){
				}else{
					BluetoothCommand mBC=BluetoothCommand.getInstance();
					if(mBC!=null){
						int currentPos=mBC.getValue(BluetoothCommand.WALKING_POSITION_STATUS);
						int currentDir=mBC.getValue(BluetoothCommand.DIRECTION_STATUS);
						if((lastPositison==BluetoothCommand.WALKING_POSITION_STATUS12&&currentPos==BluetoothCommand.WALKING_POSITION_STATUS11)||
								(lastDirection==BluetoothCommand.DIRECTION_STATUS_STOP&&currentDir==BluetoothCommand.DIRECTION_STATUS_DOWN)){
							exhaleTimeStart=System.currentTimeMillis();
						}
						lastPositison=currentPos;
						lastDirection=currentDir;
					}
					inhaleExerciseHandler.postDelayed(inhaleHoldRunnable, BluetoothCommand.DELAY_TIME/2);
				}
				break;
			case EXERCISE_EXHALE:
				if(!exhaleExerciseHandler.isWaiting()){
				}else{
					BluetoothCommand mBC=BluetoothCommand.getInstance();
					if(mBC!=null){
						int currentPos=mBC.getValue(BluetoothCommand.WALKING_POSITION_STATUS);
						int currentDir=mBC.getValue(BluetoothCommand.DIRECTION_STATUS);
						if((lastPositison==BluetoothCommand.WALKING_POSITION_STATUS2&&currentPos==BluetoothCommand.WALKING_POSITION_STATUS1)||
								(lastDirection==BluetoothCommand.DIRECTION_STATUS_DOWN&&currentDir==BluetoothCommand.DIRECTION_STATUS_STOP)){
							exhaleTimeEnd=System.currentTimeMillis();
						}
						lastPositison=currentPos;
						lastDirection=currentDir;
					}
					inhaleExerciseHandler.postDelayed(exhaleExerciseRunnable, BluetoothCommand.DELAY_TIME/2);
				}
				break;
			case EXERCISE_HOLD_EXHALE:
				if(!exhaleHoldHandler.isWaiting()){
				}else{
					BluetoothCommand mBC=BluetoothCommand.getInstance();
					if(mBC!=null){
						int currentPos=mBC.getValue(BluetoothCommand.WALKING_POSITION_STATUS);
						int currentDir=mBC.getValue(BluetoothCommand.DIRECTION_STATUS);
						if((lastPositison==BluetoothCommand.WALKING_POSITION_STATUS1&&currentPos==BluetoothCommand.WALKING_POSITION_STATUS2)||
								(lastDirection==BluetoothCommand.DIRECTION_STATUS_STOP&&currentDir==BluetoothCommand.DIRECTION_STATUS_UP)){
							inhaleTimeStart=System.currentTimeMillis();
						}
						lastPositison=currentPos;
						lastDirection=currentDir;
					}
					exhaleHoldHandler.postDelayed(exhaleHoldRunnable, BluetoothCommand.DELAY_TIME/2);
				}
				break;

			default:
				break;
			}
			
		}
		
	}
	

//	@Override
//	public void start() {
//		// Reset the flag that indicates if we're started the sounds for the new step
//        mPlayedSounds = false;
//
//        // If the animation has not been previously started and then paused
//        if (!hasPreviouslyBeenPaused) {
//            // Reset everything, just to be sure
//            reset(false);
//
//            // Keep the current timestamp
//            mFirstStartAnimationTimestamp = System.currentTimeMillis();
//
//            // Start with inhale
//            startValueAnimator(mTimes.inhale);
//
//        } else { // Otherwise the animation has been running and we're just restarting it
//
//            // Adjust the globalTimeOffset because of the pause
//            readjustFirstStartTimestamp();
//
//            // Restart the last step
//            switch (mCurExercise) {
//                case EXERCISE_INHALE:
//                    // Play sounds for the inhale part
//                    startValueAnimator(mTimes.inhale);
//                    break;
//                case EXERCISE_HOLD_INHALE:
//                    // Play sounds for the inhale part
//                    startValueAnimator(mTimes.holdInhale);
//                    break;
//                case EXERCISE_EXHALE:
//                    // Play sounds for the inhale part
//                    startValueAnimator(mTimes.exhale);
//                    break;
//                case EXERCISE_HOLD_EXHALE:
//                    // Play sounds for the inhale part
//                    startValueAnimator(mTimes.holdExhale);
//                    break;
//            }
//
//        }
//	}
//	
//	@Override
//	public void start(float fraction) {
//		mFraction=fraction;
//		start();
//	}
//	@Override
//	public void startValueAnimator(float start, float end,
//			AnimatorUpdateListener updateListener, int duration) {
//        // Cancel and reset any previously started animations and/or set values
//        if (mValueAnimator != null) {
//            mValueAnimator.cancel();
//            mValueAnimator.removeAllListeners();
//        }
//
//        mValueAnimator = ValueAnimator.ofFloat(start, end);
//        mValueAnimator.addUpdateListener(updateListener);
//        mValueAnimator.setDuration(duration);
//		
//        startAppropriateExerciseType(mFraction);
//	}
//	
//	@Override
//	public void startValueAnimator(int duration) {
//		// TODO Auto-generated method stub
//		super.startValueAnimator(duration);
//	}
//	
//	@Override
//	protected void startAppropriateExerciseType(float fraction) {
//		// Keep the fraction value. We'll need this for accurately keeping track of the globalTimeFraction
//        mLastFraction = fraction;
//
//        // Calculate the globalTimeFraction (how much time has passed for the exercise time)
//        // (now - start) / (end - start) OR (now - start) / ((start + duration) - start)
//        float globalFraction = (float) ((System.currentTimeMillis() - mFirstStartAnimationTimestamp));
//        globalFraction /= (float) ((mFirstStartAnimationTimestamp + mTimes.exerciseDuration) - mFirstStartAnimationTimestamp);
//
//        // In case the fraction is more than 1 (which means we've shot over the ending time of the entire exercise), just bring it back to (limit it to max) 100%
//        globalFraction = (globalFraction > 1f) ? 1f : globalFraction;
//
//        // Depending on the current exercise step, call the appropriate method
//        switch (mCurExercise) {
//            case EXERCISE_INHALE:
//                inhale(fraction, globalFraction);
//                break;
//            case EXERCISE_HOLD_INHALE:
//                holdInhale(fraction, globalFraction);
//                break;
//            case EXERCISE_EXHALE:
//                exhale(fraction, globalFraction);
//                break;
//            case EXERCISE_HOLD_EXHALE:
//                holdExhale(fraction, globalFraction);
//                break;
//        }
//	}
//	
//	@Override
//	protected void inhale(float fraction, float globalFraction) {
//		// Start appropriate sounds
//        playSounds(SoundItem.INSPIREZ, mTimes.inhale);
//
//        // Set the volume of the ambiance sound
//        setAmbianceVolume(fraction);
//
//        // Render animation frame
//        mAnimationHandler.inhale(fraction, globalFraction);
//
//        // If step animation ended
//        if (fraction == 1f) {
//            // Set the flag to point to the next appropriate step
//            mCurExercise = EXERCISE_HOLD_INHALE;
//
////            // Restart the value animator
////            if (mTimes.holdInhale == 1000) {
////                startValueAnimator(2000); // HACK. FORCE IT TO BE AT LEAST 2 SEC
////            } else {
////                startValueAnimator(mTimes.holdInhale);
////            }
//
//            // Reset the flag that indicates if we've started the sounds for the new step
//            mPlayedSounds = false;
//
//        }
//	}
//	
//	@Override
//	protected void holdExhale(float fraction, float globalFraction) {
//		if (mTimes.holdExhale > 0) {
//            // Start appropriate sounds
//            playSounds(SoundItem.RETENEZ, mTimes.holdExhale);
//
//            // Set the volume of the ambiance sound
//            setAmbianceVolume(fraction);
//        }
//
//        // Render animation frame
//        mAnimationHandler.holdExhale(fraction, globalFraction);
//
//        // If step animation ended
//        if (fraction == 1f) {
//            // Set the flag to point to the next appropriate step
//            mCurExercise = EXERCISE_INHALE;
//
//            // Restart the value animator
//            //startValueAnimator(mTimes.inhale);
//
//            // Reset the flag that indicates if we've started the sounds for the new step
//            mPlayedSounds = false;
//
//        }
//	}
//	
//	@Override
//	protected void exhale(float fraction, float globalFraction) {
//		// Start appropriate sounds
//        playSounds(SoundItem.EXPIREZ, mTimes.exhale);
//
//        // Set the volume of the ambiance sound
//        setAmbianceVolume(fraction);
//
//        // Render animation frame
//        mAnimationHandler.exhale(fraction, globalFraction);
//
//        // If step animation ended
//        if (fraction == 1f) {
//
//            // Check if the entire exercise is done. If so, then stop here
//            if (globalFraction == 1f) {
//
//                reset(true);
//
//                return;
//            }
//
//            // Set the flag to point to the next appropriate step
//            mCurExercise = EXERCISE_HOLD_EXHALE;
//
//            // Restart the value animator
//            if (mTimes.holdExhale == 1000) {
//                startValueAnimator(2000); // HACK. FORCE IT TO BE AT LEAST 2 SEC
//            } else {
//                startValueAnimator(mTimes.holdExhale);
//            }
//
//            // Reset the flag that indicates if we've started the sounds for the new step
//            mPlayedSounds = false;
//
//        }
//	}
//	
//	@Override
//	protected void holdInhale(float fraction, float globalFraction) {
//		if (mTimes.holdInhale > 0) {
//            // Start appropriate sounds
//            playSounds(SoundItem.RETENEZ, mTimes.holdInhale);
//
//            // Set the volume of the ambiance sound
//            setAmbianceVolume(fraction);
//        }
//
//        // Render animation frame
//        mAnimationHandler.holdInhale(fraction, globalFraction);
//
//        // If step animation ended
//        if (fraction == 1f) {
//            // Set the flag to point to the next appropriate step
//            mCurExercise = EXERCISE_EXHALE;
//
//            // Restart the value animator
//            startValueAnimator(mTimes.exhale);
//
//            // Reset the flag that indicates if we've started the sounds for the new step
//            mPlayedSounds = false;
//
//        }
//	}
}
