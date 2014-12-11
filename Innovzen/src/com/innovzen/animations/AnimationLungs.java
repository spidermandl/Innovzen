
package com.innovzen.animations;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.o2chair.R;
import com.innovzen.activities.ActivityBase;
import com.innovzen.animations.interfaces.ExerciseAnimationBase;
import com.innovzen.animations.view.LungsView;
import com.innovzen.entities.ExerciseTimes;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.handlers.CircularSeekBarHandler;
import com.innovzen.handlers.ExerciseManager;
import com.innovzen.utils.Util;

public class AnimationLungs extends ExerciseAnimationBase {

    // Hold the context
    private Context mCtx;

    // Hold view references
    private TextView step_text;
    private View lungs_and_bubbles_container;

    // Hold the step text colors for quick reference
    private int mStepTextColorInhale = -1;
    private int mStepTextColorHold = -1;
    private int mStepTextColorExhale = -1;

    // Hold the lungs imageview reference
    private LungsView lungs;

    /** Hold the handler for the timer */
    private CircularSeekBarHandler mTimerHandler;

    /** Indicate if the layout has been drawn */
    private boolean mIsLayoutDrawn = false;
    /** Indicate if the text view that indicates the step type has been drawn */
    private boolean mIsTextDrawn = false;

    // The lungs view tree observer
    ViewTreeObserver.OnPreDrawListener mLungsOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {

            if (lungs == null) {
                return true;
            }

            // IMPORTANT !!! If not removed, it will enter an infinite loop
            if (lungs.getViewTreeObserver().isAlive()) {
                lungs.getViewTreeObserver().removeOnPreDrawListener(this);
            }

            if (ActivityBase.IS_TABLET) {
                // IMPORTANT !! Do this because it will resize it and the user can see a twitch in the view
                lungs_and_bubbles_container.setVisibility(View.VISIBLE);
            }

            // Configure the lungs
            lungs.initBitmapBubble();
            lungs.initPositions();

            return true;
        }
    };

    public AnimationLungs(Context ctx) {
        this.mCtx = ctx;

        init();
    }

    /**
     * Does proper initializations
     * 
     * @author MAB
     */
    private void init() {

        Resources res = mCtx.getResources();

        // Inflate the layout and get the view references
        super.layout = (RelativeLayout) ((LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.exercise_animation_lungs, null);
        step_text = (TextView) super.layout.findViewById(R.id.animation_step_text);
        lungs_and_bubbles_container = super.layout.findViewById(R.id.animation_lungs_lungs_container);
        lungs = (LungsView) super.layout.findViewById(R.id.animation_lungs_bubbles);

        // Get the step texts color
        mStepTextColorInhale = res.getColor(R.color.animation_lungs_text_inhale);
        mStepTextColorHold = res.getColor(R.color.animation_lungs_text_hold);
        mStepTextColorExhale = res.getColor(R.color.animation_lungs_text_exhale);

        super.layout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                if (layout == null) {
                    return false;
                }

                // IMPORTANT !!! If not removed, it will enter an infinite loop
                if (layout.getViewTreeObserver().isAlive()) {
                    layout.getViewTreeObserver().removeOnPreDrawListener(this);
                }

                // Set up the timer
                if (ActivityBase.IS_TABLET) {
                    int timerDim = (int) (layout.getHeight() * 0.32f);
                    //Desmond
                    mTimerHandler = //new CircularSeekBarHandler(layout.findViewById(R.id.reusable_circular_seekbar_container), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_DOUBLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, false);
                                      new CircularSeekBarHandler(layout.findViewById(R.id.reusable_circular_seekbar_container), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_DOUBLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, false);
                    //Desmond end
                    mTimerHandler.showThumb(false);

                    setupLungs();
                }

                // If we're in portrait mode
                // AND
                // If the text has already been drawn, then we can set the start/end dimension of the gradient
                if (!ActivityBase.IS_TABLET && mIsTextDrawn) { // PHONE

                    // Set the dimensions of the lungs container
                    setLungsDimensions(layout.getHeight() - step_text.getHeight());

                    setupLungs();

                }

                mIsLayoutDrawn = true;

                return true;
            }
        });

        step_text.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {

                if (step_text == null) {
                    return false;
                }

                // IMPORTANT !!! If not removed, it will enter an infinite loop
                if (step_text.getViewTreeObserver().isAlive()) {
                    step_text.getViewTreeObserver().removeOnPreDrawListener(this);
                }

                // If we're in portrait mode
                // AND
                // If the layout has already been drawn, then we can set the dimension of the petals container
                if (!ActivityBase.IS_TABLET && mIsLayoutDrawn) { // PHONE

                    // Set the dimensions of the lungs container
                    setLungsDimensions(layout.getHeight() - step_text.getHeight());

                    setupLungs();

                }

                mIsTextDrawn = true;

                return true;
            }
        });

    }

    @Override
    public void configure(ExerciseTimes times) {

        super.times = times;

    }

    @Override
    protected void processTimes() {
    }

    @Override
    public void inhale(float stepTimeFraction, float globalTimeFraction) {

        // Set text for the step progress
        step_text.setText(FragAnimationBase.STRING_INHALE);

        // Set the color for the step progress text
        step_text.setTextColor(mStepTextColorInhale);

        // Set the positions of the bubbles
        lungs.inhale(stepTimeFraction, globalTimeFraction);

        mTimerHandler.setProgressMillisWithStepType(ExerciseManager.EXERCISE_INHALE,0, super.times.inhale, (int) (super.times.inhale * stepTimeFraction), super.times.exerciseDuration, globalTimeFraction);

    }

    @Override
    public void holdInhale(float stepTimeFraction, float globalTimeFraction) {

        // Set text for the step progress
        step_text.setText(FragAnimationBase.STRING_HOLD);

        // Set the color for the step progress text
        step_text.setTextColor(mStepTextColorHold);

        mTimerHandler.setProgressMillisWithStepType(ExerciseManager.EXERCISE_HOLD_INHALE, 0, super.times.holdInhale, (int) (super.times.holdInhale * stepTimeFraction), super.times.exerciseDuration, globalTimeFraction);

    }

    @Override
    public void exhale(float stepTimeFraction, float globalTimeFraction) {

        // Set text for the step progress
        step_text.setText(FragAnimationBase.STRING_EXHALE);

     // Set the color for the step progress text
        step_text.setTextColor(mStepTextColorExhale);

        // Set the positions of the bubbles
        lungs.exhale(stepTimeFraction, globalTimeFraction);

        mTimerHandler.setProgressMillisWithStepType(ExerciseManager.EXERCISE_EXHALE, 0, super.times.exhale, (int) (super.times.exhale * stepTimeFraction), super.times.exerciseDuration, globalTimeFraction);

    }

    @Override
    public void holdExhale(float stepTimeFraction, float globalTimeFraction) {

        // Set text for the step progress
        step_text.setText(FragAnimationBase.STRING_HOLD);

        // Set the color for the step progress text
        step_text.setTextColor(mStepTextColorHold);

        mTimerHandler.setProgressMillisWithStepType(ExerciseManager.EXERCISE_HOLD_EXHALE, 0, super.times.holdExhale, (int) (super.times.holdExhale * stepTimeFraction), super.times.exerciseDuration, globalTimeFraction);

    }

    @Override
    public void release() {
        
        mTimerHandler.reseStepType();
        
    }

    @Override
    public void reset() {
    }

    @Override
    public void enableFullscreen() {

        super.layout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                if (layout == null) {
                    return false;
                }

                // IMPORTANT !!! If not removed, it will enter an infinite loop
                if (layout.getViewTreeObserver().isAlive()) {
                    layout.getViewTreeObserver().removeOnPreDrawListener(this);
                }

                // Set the dimensions of the lungs container
                setLungsDimensions(layout.getWidth());

                setupLungs();

                return true;
            }
        });

    }

    @Override
    public void disableFullscreen() {

        super.layout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                if (layout == null) {
                    return false;
                }

                // IMPORTANT !!! If not removed, it will enter an infinite loop
                if (layout.getViewTreeObserver().isAlive()) {
                    layout.getViewTreeObserver().removeOnPreDrawListener(this);
                }

                if (!ActivityBase.IS_TABLET) { // PHONE
                    // Set the dimensions of the lungs container
                    setLungsDimensions(layout.getHeight() - step_text.getHeight());
                }

                setupLungs();

                return true;
            }
        });

    }

    private void setupLungs() {
        if (ActivityBase.IS_TABLET) { // TABLET

            // IMPORTANT !! Do this because it will resize it and the user can see a twitch in the view
            lungs_and_bubbles_container.setVisibility(View.INVISIBLE);

            // Do stuff after the layout has been drawn
            lungs_and_bubbles_container.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {

                    if (lungs_and_bubbles_container == null) {
                        return true;
                    }

                    // IMPORTANT !!! If not removed, it will enter an infinite loop
                    if (lungs_and_bubbles_container.getViewTreeObserver().isAlive()) {
                        lungs_and_bubbles_container.getViewTreeObserver().removeOnPreDrawListener(this);
                    }

                    // Set the height of the lungs container
                    ViewGroup.LayoutParams params = lungs_and_bubbles_container.getLayoutParams();
                    params.width = lungs_and_bubbles_container.getHeight();
                    lungs_and_bubbles_container.setLayoutParams(params);

                    // Set the stuff to do when the lungs imageview has finished being measured
                    lungs.getViewTreeObserver().addOnPreDrawListener(mLungsOnPreDrawListener);

                    return true;
                }
            });

            // Set up the timer
            // Not here. See in the view tree observer for the layout (below)

            // Set the left position of the lungs
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) lungs_and_bubbles_container.getLayoutParams();
            /**
             * Desmond
             * ¶¯»­¿¿×ó
             */
            params.leftMargin = 20;//(int) (Util.getScreenDimensions(mCtx)[0] * 0.2f);
            //</Desmond>
            lungs_and_bubbles_container.setLayoutParams(params);

        } else { // PHONE

            // Set the stuff to do when the lungs imageview has finished being measured
            lungs.getViewTreeObserver().addOnPreDrawListener(mLungsOnPreDrawListener);

            // Set up the timer
            int timerDim = (int) (Util.getScreenDimensions(mCtx)[0] * 0.22031f);
            mTimerHandler = //new CircularSeekBarHandler(super.layout.findViewById(R.id.reusable_circular_seekbar_container), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_DOUBLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, false);
            				new CircularSeekBarHandler(layout.findViewById(R.id.reusable_circular_seekbar_container), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_DOUBLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, false);
            mTimerHandler.showThumb(false);
        }

    }

    /**
     * The height is calculated based on the width and a predefined fraction
     * 
     * @param width
     * @author MAB
     */
    private void setLungsDimensions(int width) {
        ViewGroup.LayoutParams params = lungs_and_bubbles_container.getLayoutParams();
        params.width = width;
        params.height = (int) (params.width * 0.909090f);
        lungs_and_bubbles_container.setLayoutParams(params);
    }
    @Override
	public CircularSeekBarHandler getTimeHandler() {
		return mTimerHandler;
	}
}
