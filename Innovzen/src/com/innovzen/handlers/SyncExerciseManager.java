package com.innovzen.handlers;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

import com.innovzen.entities.ExerciseTimes;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.interfaces.FragmentCommunicator;

/**
 * 
 * @author Desmond
 * 实现同步功能exercise动画
 */
public class SyncExerciseManager extends ExerciseManager{

	public SyncExerciseManager(FragAnimationBase fragmentAnimation,
			ExerciseAnimationHandler animationHandler,
			FragmentCommunicator soundHandler, ExerciseTimes times,
			int voiceSoundId, int ambianceSoundId) {
		super(fragmentAnimation, animationHandler, soundHandler, times, voiceSoundId,
				ambianceSoundId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() {
		// Reset the flag that indicates if we're started the sounds for the new step
        mPlayedSounds = false;

        // If the animation has not been previously started and then paused
        if (!hasPreviouslyBeenPaused) {
            // Reset everything, just to be sure
            reset(false);

            // Keep the current timestamp
            mFirstStartAnimationTimestamp = System.currentTimeMillis();

            // Start with inhale
            startValueAnimator(mTimes.inhale);

        } else { // Otherwise the animation has been running and we're just restarting it

            // Adjust the globalTimeOffset because of the pause
            readjustFirstStartTimestamp();

            // Restart the last step
            switch (mCurExercise) {
                case EXERCISE_INHALE:
                    // Play sounds for the inhale part
                    startValueAnimator(mTimes.inhale);
                    break;
                case EXERCISE_HOLD_INHALE:
                    // Play sounds for the inhale part
                    startValueAnimator(mTimes.holdInhale);
                    break;
                case EXERCISE_EXHALE:
                    // Play sounds for the inhale part
                    startValueAnimator(mTimes.exhale);
                    break;
                case EXERCISE_HOLD_EXHALE:
                    // Play sounds for the inhale part
                    startValueAnimator(mTimes.holdExhale);
                    break;
            }

        }
	}
	
	@Override
	public void startValueAnimator(float start, float end,
			AnimatorUpdateListener updateListener, int duration) {
        // Cancel and reset any previously started animations and/or set values
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            mValueAnimator.removeAllListeners();
        }

        mValueAnimator = ValueAnimator.ofFloat(start, end);
        mValueAnimator.addUpdateListener(updateListener);
        mValueAnimator.setDuration(duration);

        updateListener.onAnimationUpdate(mValueAnimator);
	}
	
	@Override
	public void startValueAnimator(int duration) {
		// TODO Auto-generated method stub
		super.startValueAnimator(duration);
	}
	
	@Override
	protected void startAppropriateExerciseType(float fraction) {
		// Keep the fraction value. We'll need this for accurately keeping track of the globalTimeFraction
        mLastFraction = fraction;

        // Calculate the globalTimeFraction (how much time has passed for the exercise time)
        // (now - start) / (end - start) OR (now - start) / ((start + duration) - start)
        float globalFraction = (float) ((System.currentTimeMillis() - mFirstStartAnimationTimestamp));
        globalFraction /= (float) ((mFirstStartAnimationTimestamp + mTimes.exerciseDuration) - mFirstStartAnimationTimestamp);

        // In case the fraction is more than 1 (which means we've shot over the ending time of the entire exercise), just bring it back to (limit it to max) 100%
        globalFraction = (globalFraction > 1f) ? 1f : globalFraction;

        // Depending on the current exercise step, call the appropriate method
        switch (mCurExercise) {
            case EXERCISE_INHALE:
                inhale(fraction, globalFraction);
                break;
            case EXERCISE_HOLD_INHALE:
                holdInhale(fraction, globalFraction);
                break;
            case EXERCISE_EXHALE:
                exhale(fraction, globalFraction);
                break;
            case EXERCISE_HOLD_EXHALE:
                holdExhale(fraction, globalFraction);
                break;
        }
	}
	
	@Override
	protected void inhale(float fraction, float globalFraction) {
		// TODO Auto-generated method stub
		super.inhale(fraction, globalFraction);
	}
	
	@Override
	protected void holdExhale(float fraction, float globalFraction) {
		// TODO Auto-generated method stub
		super.holdExhale(fraction, globalFraction);
	}
	
	@Override
	protected void exhale(float fraction, float globalFraction) {
		// TODO Auto-generated method stub
		super.exhale(fraction, globalFraction);
	}
	
	@Override
	protected void holdInhale(float fraction, float globalFraction) {
		// TODO Auto-generated method stub
		super.holdInhale(fraction, globalFraction);
	}
}
