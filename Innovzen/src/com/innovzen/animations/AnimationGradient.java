
package com.innovzen.animations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.o2chair.R;
import com.innovzen.activities.ActivityBase;
import com.innovzen.animations.interfaces.ExerciseAnimationBase;
import com.innovzen.animations.view.GradientView;
import com.innovzen.entities.ExerciseTimes;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.handlers.CircularSeekBarHandler;
import com.innovzen.handlers.ExerciseManager;
import com.innovzen.utils.Util;

/**
 * 
 * @author Boldizsar Zsolt
 * 
 */
public class AnimationGradient extends ExerciseAnimationBase {

    /** Used for tablets only. It gives the gradient a small top and bottom margin so when it's large it won't hit the top and bottom view limits */
    private final float GRADIENT_PADDING_TABLET_MODE = 0.01f;

    private Context mCtx;

    // Widget references
    private GradientView gradient;
    private TextView step_text;

    /** The starting dimension of the gradient (pixels) */
    private int mGradientStart = -1;
    /** The end dimension of the gradient (pixels) */
    private int mGradientEnd = -1;

    /** Indicate if the layout has been drawn */
    private boolean mIsLayoutDrawn = false;
    /** Indicate if the text view that indicates the step type has been drawn */
    private boolean mIsTextDrawn = false;

    /** Hold the handler for the timer */
    private CircularSeekBarHandler mTimerHandler;

    public AnimationGradient(final Context context) {
        this.mCtx = context;

        init();

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

        int radius = (int) (((mGradientEnd - mGradientStart) * stepTimeFraction + mGradientStart) / 2);
        if (radius > 0) {
            gradient.setRadius(mCtx, radius);
        }

        // update Step Label to the current action
        step_text.setText(FragAnimationBase.STRING_INHALE);
        step_text.setTextColor(mCtx.getResources().getColor(R.color.animation_gradient_step_inhale_text_color));

        mTimerHandler.setProgressMillisWithStepType(ExerciseManager.EXERCISE_INHALE, 0, super.times.inhale, (int) (super.times.inhale * stepTimeFraction), super.times.exerciseDuration, globalTimeFraction);

    }

    @Override
    public void holdInhale(float stepTimeFraction, float globalTimeFraction) {

        gradient.setRadius(mCtx, (int) (mGradientEnd / 2));

        // update Step Label to the current action
        step_text.setText(FragAnimationBase.STRING_HOLD);
        step_text.setTextColor(mCtx.getResources().getColor(R.color.animation_gradient_step_hold_text_color));

        mTimerHandler.setProgressMillisWithStepType(ExerciseManager.EXERCISE_HOLD_INHALE, 0, super.times.holdInhale, (int) (super.times.holdInhale * stepTimeFraction), super.times.exerciseDuration, globalTimeFraction);

    }

    @Override
    public void exhale(float stepTimeFraction, float globalTimeFraction) {

        int radius = (int) (((mGradientEnd - mGradientStart) * (1f - stepTimeFraction) + mGradientStart) / 2);
        if (radius > 0) {
            gradient.setRadius(mCtx, radius);
        }

        // update Step Label to the current action
        step_text.setText(FragAnimationBase.STRING_EXHALE);
        step_text.setTextColor(mCtx.getResources().getColor(R.color.animation_gradient_step_exhale_text_color));

        mTimerHandler.setProgressMillisWithStepType(ExerciseManager.EXERCISE_EXHALE, 0, super.times.exhale, (int) (super.times.exhale * stepTimeFraction), super.times.exerciseDuration, globalTimeFraction);

    }

    @Override
    public void holdExhale(float stepTimeFraction, float globalTimeFraction) {

        gradient.setRadius(mCtx, (int) (mGradientStart / 2));

        // update Step Label to the current action
        step_text.setText(FragAnimationBase.STRING_HOLD);
        step_text.setTextColor(mCtx.getResources().getColor(R.color.animation_gradient_step_hold_text_color));

        mTimerHandler.setProgressMillisWithStepType(ExerciseManager.EXERCISE_HOLD_EXHALE, 0, super.times.holdExhale, (int) (super.times.holdExhale * stepTimeFraction), super.times.exerciseDuration, globalTimeFraction);

    }

    @Override
    public void release() {
        times = null;
        step_text = null;
        gradient = null;
        layout = null;

        mTimerHandler.reseStepType();
    }

    @Override
    public void reset() {
    }

    @Override
    public void enableFullscreen() {

        recalculateGradientCharacteristics(true);

    }

    @Override
    public void disableFullscreen() {

        recalculateGradientCharacteristics(false);

    }

    /**
     * Does proper initializations
     * 
     * @author MAB
     */
    private void init() {

        // Get view references
        super.layout = (RelativeLayout) LayoutInflater.from(mCtx).inflate(R.layout.exercise_animation_gradient, null);
        gradient = (GradientView) super.layout.findViewById(R.id.animation_gradient);
        step_text = (TextView) super.layout.findViewById(R.id.animation_step_text);

        if (ActivityBase.IS_TABLET) { // TABLET

            // Set up the timer
            // Not here. See in the view tree observer for the layout (below)

            // Set the left position of the petals
            setGradientPosition((int) (Util.getScreenDimensions(mCtx)[0] * 0.15f), 0);

        } else { // PHONE

            // Set up the timer
            int timerDim = (int) (Util.getScreenDimensions(mCtx)[0] * 0.22031f);
            mTimerHandler = new CircularSeekBarHandler(super.layout.findViewById(R.id.reusable_circular_seekbar_container), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_DOUBLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, false);
            mTimerHandler.showThumb(false);

            // Set up the initial position of the gradient (make sure it's left & top = 0 & 0
            setGradientPosition(0, 0);
        }

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

                // If we're in portrait mode
                // AND
                // If the text has already been drawn, then we can set the start/end dimension of the gradient
                if (!ActivityBase.IS_TABLET && mIsTextDrawn) { // PHONE

                    mGradientStart = (int) (layout.getHeight() * 0.26453f);
                    if (layout.getWidth() > layout.getHeight() - step_text.getHeight()) {
                        mGradientEnd = layout.getHeight() - step_text.getHeight();
                    } else {
                        mGradientEnd = layout.getWidth();
                    }

                    initGradient(true);

                } else
                // If we're in tablet mode, then we don't care about the text
                if (ActivityBase.IS_TABLET) { // TABLET

                    mGradientStart = (int) (layout.getHeight() * 0.26453f);
                    mGradientEnd = (int) (layout.getHeight() - layout.getHeight() * GRADIENT_PADDING_TABLET_MODE); // Give it a little padding

                    initGradient(true);
                }

                // Set up the timer
                if (ActivityBase.IS_TABLET) {
                    int timerDim = (int) (layout.getHeight() * 0.32f);
                    mTimerHandler = new CircularSeekBarHandler(layout.findViewById(R.id.reusable_circular_seekbar_container), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_DOUBLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, false);
                    mTimerHandler.showThumb(false);
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
                // If the layout has already been drawn, then we can set the start/end dimension of the gradient
                if (!ActivityBase.IS_TABLET && mIsLayoutDrawn) {

                    mGradientStart = (int) (layout.getHeight() * 0.26453f);
                    if (layout.getWidth() > layout.getHeight() - step_text.getHeight()) {
                        mGradientEnd = layout.getHeight() - step_text.getHeight();
                    } else {
                        mGradientEnd = layout.getWidth();
                    }

                    initGradient(true);
                }

                mIsTextDrawn = true;

                return true;
            }
        });

    }

    /**
     * IMPORTANT !!! Only call this when you have the text and gradient dimensions (a.k.a. they won't change afterwards)
     * 
     * @param setInitialRadius
     * @author MAB
     */
    public void initGradient(boolean setInitialRadius) {

        // Hold the layout dimensions
        int layoutW = super.layout.getWidth();
        int layoutH = super.layout.getHeight();

        // The height of the layout without the height of the text
        int layoutLessHeight = layoutH - step_text.getHeight();

        // Set dimensions
        if (ActivityBase.IS_TABLET) { // TABLET

            gradient.setWidth(layoutH);
            gradient.setHeight(layoutH);

        } else { // PHONE

            ViewGroup.LayoutParams params = gradient.getLayoutParams();

            if (layoutW > layoutLessHeight) {
                params.width = layoutLessHeight;
                params.height = params.width;
            } else {
                params.width = layoutW;
                params.height = params.width;
            }

            gradient.setLayoutParams(params);

            gradient.setWidth(params.width);
            gradient.setHeight(params.height);

        }

        // Set the initial radius
        if (setInitialRadius) {
            gradient.setRadius(mCtx, (int) (mGradientStart / 2));
        }

    }

    /**
     * Calculates the start and end dimensions of the gradient after the layout loads and also it's position<br/>
     * IMPORTANT !!! Used only for landscape mode
     * 
     * @param isFullscreen
     * @author MAB
     */
    private void recalculateGradientCharacteristics(final boolean isFullscreen) {
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

                if (ActivityBase.IS_TABLET) { // TABLET

                    // Determine the end dimension
                    // float dimension = Math.min(layout.getHeight(), layout.getWidth() / 2);
                    mGradientEnd = (int) (layout.getHeight() - layout.getHeight() * GRADIENT_PADDING_TABLET_MODE); // Give it a little padding

                    // Set the left position of the petals
                    if (isFullscreen) {
                        setGradientPosition(0, 0);
                    } else {
                        setGradientPosition((int) (layout.getWidth() * 0.15f), 0);
                    }

                    initGradient(false);

                } else { // PHONE

                    mGradientStart = (int) (layout.getHeight() * 0.26453f);
                    if (isFullscreen) {
                        mGradientEnd = layout.getWidth();
                    } else {
                        mGradientEnd = layout.getHeight() - step_text.getHeight();
                    }
                    initGradient(false);

                    // Set position
                    if (isFullscreen) {
                        setGradientPosition(0, (int) (layout.getHeight() / 2 - mGradientEnd / 2));
                    } else {
                        setGradientPosition(0, 0);
                    }

                }

                return true;
            }
        });

    }

    private void setGradientPosition(int left, int top) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) gradient.getLayoutParams();
        params.setMargins(left, top, 0, 0);
        gradient.setLayoutParams(params);
    }
}
