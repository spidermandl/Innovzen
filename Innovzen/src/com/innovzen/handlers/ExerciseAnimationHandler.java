
package com.innovzen.handlers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.innovzen.animations.AnimationBeach;
import com.innovzen.animations.AnimationGradient;
import com.innovzen.animations.AnimationLungs;
import com.innovzen.animations.AnimationPetals;
import com.innovzen.animations.interfaces.ExerciseAnimationBase;
import com.innovzen.entities.ExerciseTimes;

public class ExerciseAnimationHandler {

    // Hold the available animation types
    public static final int ANIMATION_GRADIENT = 0;
    public static final int ANIMATION_PETALS = 1;
    public static final int ANIMATION_LUNGS = 2;
    public static final int ANIMATION_BEACH = 3;

    // Hold the context
    private Context mCtx;

    // Hold the animation container to which we'll add the appropriate animation ViewGroup
    private ViewGroup animation_container;

    // Hold the current animation placed in the ViewGroup
    private ExerciseAnimationBase mCurAnimation;

    // Hold the animation type
    private int mType;

    // Hold the state of the animation (if it's in fullscreen mode or not)
    private boolean mIsFullscreen = false;

    /**
     * 
     * @param animationContainer
     *            the view to which we'll add the appropriate animation's ViewGroup (depending on the type of the animation)
     * @param animationType
     *            see the constants
     */
    public ExerciseAnimationHandler(Context ctx, View animationContainer, int animationType) {
        this.mCtx = ctx;
        this.animation_container = (ViewGroup) animationContainer;
        this.mType = animationType;

        init();
    }

    /**
     * Does proper initializations
     * 
     * @author MAB
     */
    private void init() {

        // Set the current animation
        switch (mType) {
            case ANIMATION_GRADIENT:
                mCurAnimation = new AnimationGradient(mCtx);
                break;
            case ANIMATION_PETALS:
                mCurAnimation = new AnimationPetals(mCtx);
                break;
            case ANIMATION_LUNGS:
                mCurAnimation = new AnimationLungs(mCtx);
                break;
            case ANIMATION_BEACH:
                mCurAnimation = new AnimationBeach(mCtx);
                break;
        }

        // Get he layout of the animation and set it in the fragment's layout
        animation_container.addView(mCurAnimation.getLayout());

    }

    public void configure(ExerciseTimes times) {
        // Configure the current animation
        mCurAnimation.configure(times);
    }

    public void pause() {
        // TODO:
    }

    public void inhale(float stepTimeFraction, float globalTimeFraction) {
        if (mCurAnimation != null) {
            mCurAnimation.inhale(stepTimeFraction, globalTimeFraction);
        }
    }

    public void holdInhale(float stepTimeFraction, float globalTimeFraction) {
        if (mCurAnimation != null) {
            mCurAnimation.holdInhale(stepTimeFraction, globalTimeFraction);
        }
    }

    public void exhale(float stepTimeFraction, float globalTimeFraction) {
        if (mCurAnimation != null) {
            mCurAnimation.exhale(stepTimeFraction, globalTimeFraction);
        }

    }

    public void holdExhale(float stepTimeFraction, float globalTimeFraction) {
        if (mCurAnimation != null) {
            mCurAnimation.holdExhale(stepTimeFraction, globalTimeFraction);
        }

    }

    public void release() {

        if (mCurAnimation != null) {
            mCurAnimation.release();
        }

        mCurAnimation = null;

        // Remove the layout from the fragment's view
        animation_container.removeAllViews();
        animation_container = null;
    }

    public void reset() {
        // TODO Auto-generated method stub

    }

    public void toggleFullscreen() {

        if (mIsFullscreen) {

            if (mCurAnimation != null) {
                mCurAnimation.disableFullscreen();
            }

            mIsFullscreen = false;
        } else {

            if (mCurAnimation != null) {
                mCurAnimation.enableFullscreen();
            }

            mIsFullscreen = true;
        }
    }
}
