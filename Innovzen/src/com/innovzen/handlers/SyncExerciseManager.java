package com.innovzen.handlers;

import android.os.Handler;
import android.os.Message;

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
//	private float mFraction;
	/**
	 * 等待机器传送最后位行指令
	 */
	private Handler waitHandler=null;
	
	/**
	 * 等待线程处理方法
	 */
	private Runnable waitRunnable=new Runnable() {
		public void run() {
			long subtime=BluetoothCommand.getInstance()==null?0:System.currentTimeMillis()-BluetoothCommand.getInstance().getInhaleTimeError();
        	if(subtime!=0&&subtime<mTimes.inhale*60*1000){
        		//long a = mTimes.inhale;
        		waitHandler.sendEmptyMessage(0);
        	}else{
        		waitHandler.post(waitRunnable);
        	}
		}
	};
	
	public SyncExerciseManager(FragAnimationBase fragmentAnimation,
			ExerciseAnimationHandler animationHandler,
			FragmentCommunicator soundHandler, ExerciseTimes times,
			int voiceSoundId, int ambianceSoundId) {
		super(fragmentAnimation, animationHandler, soundHandler, times, voiceSoundId,
				ambianceSoundId);
		// TODO Auto-generated constructor stub
	}

    // Hold the inhale/exhale animation
	@Override
	protected void startAppropriateExerciseType(float fraction) {
		boolean isContinue=true;
		switch (mCurExercise) {
        case EXERCISE_INHALE:
        	/**
        	 * 获取最近一次12行位的时间
        	         如果当前时间减去这个时间和差小于mTimes.inhale 说明按摩椅运动快了，那么直接调用startAppropriateExerciseType(1f);
        	      如果当前时间减去这个时间和差大于mTimes.inhale并且animation.getAnimatedFraction() 这个值为1f， 说明本轮按摩椅运动慢了，那么就等待，以100ms为单位等待
        	 */
        	long subtime=BluetoothCommand.getInstance()==null?0:System.currentTimeMillis()-BluetoothCommand.getInstance().getInhaleTimeError();
        	if(subtime!=0&&subtime<mTimes.inhale*60*1000){
        		//long a = mTimes.inhale;
        		fraction=1f;
        		break;
        	}else if(subtime>mTimes.inhale*60*1000&&fraction==1f){
        		if(waitHandler!=null){
	        		waitHandler=new Handler(){
	        			@Override
	        			public void handleMessage(Message msg) {
	        				SyncExerciseManager.super.startAppropriateExerciseType(1f);
	        				waitHandler=null;
	        			}
	        		};
	        		waitHandler.postDelayed(waitRunnable, 100); // 1 sec
	        		isContinue=false;
        		}

        	}
        	
            break;
        case EXERCISE_HOLD_INHALE:
            break;
        case EXERCISE_EXHALE:
        	/*subtime=BluetoothCommand.getInstance()==null?0:BluetoothCommand.getInstance().getExhaleTimeError();
        	if(subtime!=0&&subtime<mTimes.exhale){
        		startAppropriateExerciseType(1f);
        	}else if(subtime>mTimes.exhale&&fraction==1f){
        		
        	}*/
            break;
        case EXERCISE_HOLD_EXHALE:
            break;
		}
		
		if(isContinue)
			super.startAppropriateExerciseType(fraction);
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
