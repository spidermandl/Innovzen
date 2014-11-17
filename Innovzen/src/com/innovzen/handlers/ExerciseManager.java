package com.innovzen.handlers;

import java.security.KeyStore.ProtectionParameter;

import adapters.AdapterSound;
import android.animation.ValueAnimator;
import android.text.Html;

import com.innovzen.entities.ExerciseTimes;
import com.innovzen.entities.SoundItem;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.interfaces.FragmentCommunicator;

public class ExerciseManager {

    // Hold the types of exercises
    public static final int EXERCISE_INHALE = 0;
    public static final int EXERCISE_HOLD_INHALE = 1;
    public static final int EXERCISE_EXHALE = 2;
    public static final int EXERCISE_HOLD_EXHALE = 3;

    // Hold the fragment animation reference (so we can access methods/attributes more easily)
    protected FragAnimationBase mFragAnimation;

    // Hold the 4 time values for the exercise
    protected ExerciseTimes mTimes;

    // Hold the animation handler
    protected ExerciseAnimationHandler mAnimationHandler;

    // Hold the fragment-activity communicator. Used mostly for the sound management
    protected FragmentCommunicator mSoundHandler;

    // Hold the current exercise type being run
    protected int mCurExercise = -1;

    // A flag to incidate whether the animation has been paused. If this is false, then it means that by starting the animation it will start from the beginning
    protected boolean hasPreviouslyBeenPaused = false;

    // Hold the value animator
    protected ValueAnimator mValueAnimator;

    // Hold the fraction value received from the last time the value animator update listener was called
    protected float mLastFraction = -1;

    // Keep the timestamp when the animation had been started the very first time
    protected long mFirstStartAnimationTimestamp = -1;

    // Keep the timestamp when the animation had been paused
    protected long mPausedAnimationTimestamp = -1;

    // Hold the id of the voice and ambiance sounds selected by the user in the sound picker screen
    protected int mVoiceSoundId = -1;
    protected int mAmbianceSoundId = -1;

    // Hold a flag to indicate whether we've started to play the sounds for the new step
    // Reset this flag every time a step is finished
    protected boolean mPlayedSounds = false;

    /** The volume of the ambiance sound */
    protected float mAmbianceVolume = -1;

    /**
     * The power used in calculating the ambiance sound based on the step time fraction.<br/>
     * It can be either 0.5f (meaning the ambiance sound will go just to half it's max sound) or 1f (ambiance sound will be maxed out)
     */
    protected float mAmbianceSoundFormulaPower = 1f;

    // Hold the inhale/exhale animation
    protected ValueAnimator.AnimatorUpdateListener mValueAnimatorListener = new ValueAnimator.AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {

            startAppropriateExerciseType(animation.getAnimatedFraction());

        }
    };

    /**
     * @param fragmentAnimation
     * @param times
     */
    public ExerciseManager(FragAnimationBase fragmentAnimation, ExerciseAnimationHandler animationHandler, FragmentCommunicator soundHandler, ExerciseTimes times, int voiceSoundId, int ambianceSoundId) {
        this.mFragAnimation = fragmentAnimation;
        this.mSoundHandler = soundHandler;
        this.mAnimationHandler = animationHandler;
        this.mTimes = times;
        this.mVoiceSoundId = voiceSoundId;
        this.mAmbianceSoundId = ambianceSoundId;

        init();
    }

    /**
     * Does proper initializations
     * 
     * @author MAB
     */
    private void init() {

        // Always start with the inhale animation
        mCurExercise = ExerciseManager.EXERCISE_INHALE;
    }

    /**
     * Calls the appropriate method to handle the current exercise step
     * 
     * @param fraction
     *            the amount of time already passed from the start of the current exercise step<br/>
     * @author MAB
     */
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

    /**
     * Start (with inhale) or RESUME the exercise animation<br/>
     * Note: In case it's a resume, it will resume from the beginning of the last step in which it was in<br/>
     * i.e. if it did only 90% out of inhale, it will resume from the beginning of the inhale step
     * 
     * @author MAB
     */
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

    public void start(float fraction){
    	start();
    }
    /**
     * Stops everything in its tracks.
     * 
     * @param showPlayBtnOverlay
     *            in case the animation stopped, we also need to make the play btn overlay visible
     * @author MAB
     */
    public void pause(boolean showPlayBtnOverlay) {

        // Store the current timestamp when the animation has been paused
        mPausedAnimationTimestamp = System.currentTimeMillis();

        // Stop the value animator
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }

        // Stop the sound
        if (mSoundHandler != null) {
            mSoundHandler.fragStopPlayers();
        }
        // Set the flag so in case we'll restart the animation using start(), we'll know it's in fact a "resume"
        hasPreviouslyBeenPaused = true;

        if (showPlayBtnOverlay) {
            // Make the play button overlay visible
            //mFragAnimation.showPlayBtn();
        }

        resetValues();

    }

    /**
     * Resets all timers and
     * 
     * @param showPlayBtnOverlay
     *            in case the animation stopped, we also need to make the play btn overlay visible
     * @author MAB
     */
    public void reset(boolean showPlayBtnOverlay) {

        // Pause first, just in case
        pause(showPlayBtnOverlay);

        resetValues();

    }

    public void resetValues() {
        mAnimationHandler.reset();

        mSoundHandler.fragStopPlayers();

        mCurExercise = ExerciseManager.EXERCISE_INHALE;

        hasPreviouslyBeenPaused = false;

        mLastFraction = -1;

        mFirstStartAnimationTimestamp = -1;

        mPausedAnimationTimestamp = -1;

        mPlayedSounds = false;
    }

    /**
     * Tries and clears any memory consuming items (recycles bitmaps, etc)
     * 
     * @author MAB
     */
    public void release() {

        // Pause first (just in case)
        pause(true);

        /*
         * Clear as much stuff as possible
         */
        // Animation
        if (mAnimationHandler != null) {
            mAnimationHandler.release();
            mAnimationHandler = null;
        }

        // Value animator
        if (mValueAnimator != null) {
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator = null;
        }

    }

    /**
     * In case an animator is running, it cancels it and sets up a new one. After it does, it calls its start() method
     * 
     * @param start
     * @param end
     * @param updateListener
     * @param duration
     *            in milliseconds
     * @author MAB
     */
    public void startValueAnimator(float start, float end, ValueAnimator.AnimatorUpdateListener updateListener, int duration) {

        // Cancel and reset any previously started animations and/or set values
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            mValueAnimator.removeAllListeners();
        }

        mValueAnimator = ValueAnimator.ofFloat(start, end);
        mValueAnimator.addUpdateListener(updateListener);
        mValueAnimator.setDuration(duration);

        mValueAnimator.start();
    }

    /**
     * In case an animator is running, it cancels it and sets up a new one. After it does, it calls its start() method
     * 
     * @param duration
     *            in milliseconds
     * @author MAB
     */
    public void startValueAnimator(int duration) {
        startValueAnimator(0f, 1f, mValueAnimatorListener, duration);
    }

    /**
     * Start the inhale animation, play appropriate sound, etc
     * 
     * @param fraction
     *            the amount (as a fraction, %) of the time passed for this exercise step
     * @param globalFraction
     *            the amount (as a fraction, %) already passed from the total time allocated for the exercise
     * @author MAB
     */
    protected void inhale(float fraction, float globalFraction) {

        // Start appropriate sounds
        playSounds(SoundItem.INSPIREZ, mTimes.inhale);

        // Set the volume of the ambiance sound
        setAmbianceVolume(fraction);

        // Render animation frame
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

            // Reset the flag that indicates if we've started the sounds for the new step
            mPlayedSounds = false;

        }

    }

    /**
     * Start the hold-inhale animation, play appropriate sound, etc
     * 
     * @param fraction
     *            the amount (as a fraction, %) of the time passed for this exercise step
     * @param globalFraction
     *            the amount (as a fraction, %) already passed from the total time allocated for the exercise
     * @author MAB
     */
    protected void holdInhale(float fraction, float globalFraction) {

        if (mTimes.holdInhale > 0) {
            // Start appropriate sounds
            playSounds(SoundItem.RETENEZ, mTimes.holdInhale);

            // Set the volume of the ambiance sound
            setAmbianceVolume(fraction);
        }

        // Render animation frame
        mAnimationHandler.holdInhale(fraction, globalFraction);

        // If step animation ended
        if (fraction == 1f) {
            // Set the flag to point to the next appropriate step
            mCurExercise = EXERCISE_EXHALE;

            // Restart the value animator
            startValueAnimator(mTimes.exhale);

            // Reset the flag that indicates if we've started the sounds for the new step
            mPlayedSounds = false;

        }
    }

    /**
     * Start the exhale animation, play appropriate sound, etc
     * 
     * @param fraction
     *            the amount (as a fraction, %) of the time passed for this exercise step
     * @param globalFraction
     *            the amount (as a fraction, %) already passed from the total time allocated for the exercise
     * @author MAB
     */
    protected void exhale(float fraction, float globalFraction) {

        // Start appropriate sounds
        playSounds(SoundItem.EXPIREZ, mTimes.exhale);

        // Set the volume of the ambiance sound
        setAmbianceVolume(fraction);

        // Render animation frame
        mAnimationHandler.exhale(fraction, globalFraction);

        // If step animation ended
        if (fraction == 1f) {

            // Check if the entire exercise is done. If so, then stop here
            if (globalFraction == 1f) {

                reset(true);

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

            // Reset the flag that indicates if we've started the sounds for the new step
            mPlayedSounds = false;

        }

    }

    /**
     * Start the hold-exhale animation, play appropriate sound, etc
     * 
     * @param fraction
     *            the amount (as a fraction, %) of the time passed for this exercise step
     * @param globalFraction
     *            the amount (as a fraction, %) already passed from the total time allocated for the exercise
     * @author MAB
     */
    protected void holdExhale(float fraction, float globalFraction) {

        if (mTimes.holdExhale > 0) {
            // Start appropriate sounds
            playSounds(SoundItem.RETENEZ, mTimes.holdExhale);

            // Set the volume of the ambiance sound
            setAmbianceVolume(fraction);
        }

        // Render animation frame
        mAnimationHandler.holdExhale(fraction, globalFraction);

        // If step animation ended
        if (fraction == 1f) {
            // Set the flag to point to the next appropriate step
            mCurExercise = EXERCISE_INHALE;

            // Restart the value animator
            startValueAnimator(mTimes.inhale);

            // Reset the flag that indicates if we've started the sounds for the new step
            mPlayedSounds = false;

        }
    }

    /**
     * When an animation is resumed, this method will adjust the starting timestamp to take into consideration the "stand-by" time
     * 
     * @author MAB
     */
    protected void readjustFirstStartTimestamp() {

        // Get the current timestamp
        long curTimestamp = System.currentTimeMillis();

        // If the animation was paused BEFORE it actually started, which doesn't make any sense
        // (might be a programming error somewhere OR user might have changed timezones)
        // then just start the entire animation from the very beginning
        if (mPausedAnimationTimestamp < mFirstStartAnimationTimestamp || curTimestamp < mPausedAnimationTimestamp) {

            mFirstStartAnimationTimestamp = 0;

            return;
        }

        // Adjust the starting time by shifting it in future with the amount of time passed during the pause state
        // Basically ignore the time in which it was paused
        mFirstStartAnimationTimestamp += curTimestamp - mPausedAnimationTimestamp;

    }

    /**
     * If we haven't yet played the sounds for the step, then start playing them.<br/>
     * If the sounds are plying, then do nothin'
     * 
     * @param soundType
     * @param the
     *            current step duration. Value in milliseconds
     * @author MAB
     */
    protected void playSounds(int soundType, int curStepDuration) {

        if (!mPlayedSounds) {

            // In case we're during a hold portion AND the hold time is <= 2, then don't play the voice sound
            if (soundType != SoundItem.RETENEZ || (soundType == SoundItem.RETENEZ && curStepDuration > 2000)) {
                mSoundHandler.fragPlayVoice(mVoiceSoundId, soundType);
            }
            // In case we're during a hold portion AND the hold time is == 1, then don't play the ambiance sound
            if (soundType != SoundItem.RETENEZ){// || (soundType == SoundItem.RETENEZ && curStepDuration > 1000)) {
                mSoundHandler.fragPlayAmbiance(mAmbianceSoundId, soundType, true);
            } else { // Otherwise stop the ambiance sound 'cause it's in a loop !!!
                mSoundHandler.fragStopAmbiancePlayer();
            }

            // Set the flag so we don't enter here the next update call
            mPlayedSounds = true;
        }
    }

    /**
     * Sets the ambiance volume based on the progress of the current step
     * 
     * @param stepTimeFraction
     * @author MAB
     */
    protected void setAmbianceVolume(float stepTimeFraction) {
        if (mSoundHandler != null) {

            // In case the voice sound is turned on as well, limit the ambiance to half of it's volume
            mAmbianceSoundFormulaPower = (mVoiceSoundId == AdapterSound.SILENCE_ID) ? 1f : 0.4f;

            // Determine the volume
            mAmbianceVolume = (float) (-4 * mAmbianceSoundFormulaPower * Math.pow(stepTimeFraction - 0.5f, 2) + (1 * mAmbianceSoundFormulaPower));
            // Limit it to 1f
            mAmbianceVolume = (mAmbianceVolume >= 1f) ? 1f : mAmbianceVolume;

            mSoundHandler.fragSetAmbianceVolume(mAmbianceVolume);
        }
    }

    public void toggleFullscreen() {
        if (mAnimationHandler != null) {
            mAnimationHandler.toggleFullscreen();
        }
    }

    public void setNewTimesForAnimation(ExerciseTimes times) {
        if (mAnimationHandler != null) {
            mTimes = times;
            mAnimationHandler.configure(times);
        }
    }

}
