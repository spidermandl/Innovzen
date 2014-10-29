package com.innovzen.animations;

import android.content.Context;
import android.content.res.Resources;
import android.sax.StartElementListener;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.o2chair.R;
import com.innovzen.activities.ActivityBase;
import com.innovzen.animations.interfaces.ExerciseAnimationBase;
import com.innovzen.entities.ExerciseTimes;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.handlers.ExerciseManager;
import com.innovzen.ui.AnimationBeachSunView;
import com.innovzen.utils.Util;

public class AnimationBeach extends ExerciseAnimationBase {

    // Hold the context
    private Context mCtx;

    // Hold view references
    private View beach_red;
    private View beach_green;
    private View sun_container;
    private AnimationBeachSunView sun_red;
    private AnimationBeachSunView sun_green;
    private View dummy_white;
    private View global_progress_bar_container;
    private TextView step_progress_bar_start_time;
    private TextView step_progress_bar_end_time;
    private View step_progress_bar_entire_container;
    private View step_progress_bar_container;
    private View step_progress_bar_red;
    private View step_progress_bar_green;
    private TextView step_progress_bar_text;

    // Hold the width of the sun
    // private int mSunCurDimPixels; // height and width are the same thing. It's a square

    // Hold the dimensions of the shore
    private float mShoreHeightFraction; // fraction out of the total parent container width (screen width)
    private int mShoreHeightPixels;

    // Hold the top margin START and END position of the sun (values in pixels)
    private int mSunStartTopMargin = -1;
    private int mSunEndTopMargin = -1;

    // Hold the start and end scale of the sun
    private int mSunStartDimPixels = -1;
    private int mSunEndDimPixels = -1;

    // Hold the layout parameters of the sun container for quick reference
    private RelativeLayout.LayoutParams mSunContainerParams;

    // Hold the height of the GLOBAL progress bar
    private float mGlobalProgressBarHeightFraction = -1;

    // Hold the layout parameters of the global progress bar
    private RelativeLayout.LayoutParams mGlobalProgressBarParams;

    // Hold the dimensions of the step progress bar (pixels and also fraction)
    private int mStepProgressBarWidthPixels = -1;
    private float mStepProgressBarHeightFraction = -1;
    private float mStepProgressBarWidthFraction = -1;

    // Hold the layout params of the step progress bar container
    private RelativeLayout.LayoutParams mStepProgressBarParams;

    public AnimationBeach(Context ctx) {
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
        super.layout = (RelativeLayout) ((LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.exercise_animation_beach, null);
        beach_red = super.layout.findViewById(R.id.animation_beach_red);
        beach_green = super.layout.findViewById(R.id.animation_beach_green);
        sun_container = super.layout.findViewById(R.id.animation_beach_sun_container);
        sun_red = (AnimationBeachSunView) super.layout.findViewById(R.id.animation_beach_sun_red);
        sun_green = (AnimationBeachSunView) super.layout.findViewById(R.id.animation_beach_sun_green);
        dummy_white = super.layout.findViewById(R.id.animation_beach_dummy_white);
        global_progress_bar_container = super.layout.findViewById(R.id.animation_beach_global_progress_container);
        step_progress_bar_entire_container = super.layout.findViewById(R.id.animation_beach_step_progress_container);
        step_progress_bar_container = super.layout.findViewById(R.id.animation_beach_step_progress_bar);
        step_progress_bar_red = super.layout.findViewById(R.id.animation_beach_step_progress_bar_red);
        step_progress_bar_green = super.layout.findViewById(R.id.animation_beach_step_progress_bar_green);
        step_progress_bar_start_time = (TextView) super.layout.findViewById(R.id.animation_beach_step_progress_start_time);
        step_progress_bar_end_time = (TextView) super.layout.findViewById(R.id.animation_beach_step_progress_end_time);
        step_progress_bar_text = (TextView) super.layout.findViewById(R.id.animation_step_text);

        TypedValue typedValue = new TypedValue();

        // Retrieve the beach height (as a fraction)
        res.getValue(R.dimen.animation_beach_shore_height, typedValue, true);
        mShoreHeightFraction = typedValue.getFloat();

        // Retrieve the height of the global progress bar (as a fraction)
        res.getValue(R.dimen.animation_beach_global_time_progress_bar_height, typedValue, true);
        mGlobalProgressBarHeightFraction = typedValue.getFloat();

        // Determine the start, end and current dimensions of the sun
        res.getValue(R.dimen.animation_beach_sun_width_percentage, typedValue, true);
        int screenW = Util.getScreenDimensions(mCtx)[0];
        if (ActivityBase.IS_TABLET) {
            mSunStartDimPixels = (int) (screenW * 0.272460f);
            mSunEndDimPixels = (int) (screenW * 0.49023f);
        } else {
            mSunStartDimPixels = (int) (screenW * 0.45625f);
            mSunEndDimPixels = (int) (screenW * 0.68281f);
        }

        // Set the initial min and max W and H of the suns
        sun_red.setMinMaxDimensions(mSunStartDimPixels, mSunEndDimPixels, mSunStartDimPixels / 2, mSunEndDimPixels / 2);
        sun_green.setMinMaxDimensions(mSunStartDimPixels, mSunEndDimPixels, mSunStartDimPixels / 2, mSunEndDimPixels / 2);

        // Set the calculated width and height of the sun
        mSunContainerParams = (RelativeLayout.LayoutParams) sun_container.getLayoutParams();
        mSunContainerParams.width = mSunStartDimPixels;
        mSunContainerParams.height = (int) (mSunStartDimPixels / 2);
        sun_container.setLayoutParams(mSunContainerParams);

        // Retrieve the dimensions of the step progress bar (as a fraction)
        res.getValue(R.dimen.animation_beach_step_progress_bar_height, typedValue, true);
        mStepProgressBarHeightFraction = typedValue.getFloat();
        res.getValue(R.dimen.animation_beach_step_progress_bar_width, typedValue, true);
        mStepProgressBarWidthFraction = typedValue.getFloat();

        // Calculate the height of the shore
        mShoreHeightPixels = (int) (screenW * mShoreHeightFraction);

        /*
         * Set the height of the global progress bar
         */
        mGlobalProgressBarParams = (RelativeLayout.LayoutParams) global_progress_bar_container.getLayoutParams();
        if (ActivityBase.IS_TABLET) {
            mGlobalProgressBarParams.height = (int) (mShoreHeightPixels * mGlobalProgressBarHeightFraction / 2);
        } else {
            mGlobalProgressBarParams.height = (int) (mShoreHeightPixels * mGlobalProgressBarHeightFraction);
        }
        global_progress_bar_container.setLayoutParams(mGlobalProgressBarParams);

        /*
         * Set the height and width of the step progress bar
         */
        mStepProgressBarParams = (RelativeLayout.LayoutParams) step_progress_bar_container.getLayoutParams();
        mStepProgressBarParams.height = res.getDimensionPixelSize(R.dimen.animation_beach_step_progress_bar_height);
        step_progress_bar_container.setLayoutParams(mStepProgressBarParams);

        // Set ViewTree observers
        initializeStuffOnLayoutPreDraw();

    }

    @Override
    public void configure(ExerciseTimes times) {

        super.times = times;

        // Set the initial value of the progress bar end time as being the value for the inhale step
        step_progress_bar_end_time.setText((int) (super.times.inhale / 1000) + "sec");

    }

    @Override
    protected void processTimes() {
        // Do nothin'
    }

    @Override
    public void inhale(float stepTimeFraction, float globalTimeFraction) {

        // Change alpha of red bach + sun
        beach_red.setAlpha(1f - stepTimeFraction);
        sun_red.setAlpha(1f - stepTimeFraction);

        // Change alpha of green beach + sun
        beach_green.setAlpha(stepTimeFraction);
        sun_green.setAlpha(stepTimeFraction);

        // Translate and scale the sun container
        mSunContainerParams.topMargin = (int) (((mSunStartTopMargin - mSunEndTopMargin) * (1f - stepTimeFraction)) + mSunEndTopMargin) + 1;
        mSunContainerParams.width = (int) (((mSunEndDimPixels - mSunStartDimPixels) * stepTimeFraction) + mSunStartDimPixels);
        mSunContainerParams.height = (int) (mSunContainerParams.width / 2);

        sun_green.setMaxCurrentDimensions(mSunContainerParams.width / 2, mSunContainerParams.height / 2);
        sun_red.setMaxCurrentDimensions(mSunContainerParams.width / 2, mSunContainerParams.height / 2);

        sun_container.setLayoutParams(mSunContainerParams);

        // Set text for the step progress
        step_progress_bar_text.setText(FragAnimationBase.STRING_INHALE);

        // Refresh the step progress bar (increase width and change time value)
        refreshStepProgressBar(stepTimeFraction, super.times.inhale, ExerciseManager.EXERCISE_INHALE);

        // Refresh the global progress bar (increase width)
        refreshGlobalProgressBar(globalTimeFraction);
    }

    @Override
    public void holdInhale(float stepTimeFraction, float globalTimeFraction) {

        // Set text for the step progress
        step_progress_bar_text.setText(FragAnimationBase.STRING_HOLD);

        // Refresh the step progress bar (increase width and change time value)
        refreshStepProgressBar(stepTimeFraction, super.times.holdInhale, ExerciseManager.EXERCISE_HOLD_INHALE);

        // Refresh the global progress bar (increase width)
        refreshGlobalProgressBar(globalTimeFraction);

    }

    @Override
    public void exhale(float stepTimeFraction, float globalTimeFraction) {

        // Change alpha of red bach + sun
        beach_red.setAlpha(stepTimeFraction);
        sun_red.setAlpha(stepTimeFraction);

        // Change alpha of green beach + sun
        beach_green.setAlpha(1f - stepTimeFraction);
        sun_green.setAlpha(1f - stepTimeFraction);

        // Translate and scale the sun container
        mSunContainerParams.topMargin = (int) (((mSunStartTopMargin - mSunEndTopMargin) * stepTimeFraction) + mSunEndTopMargin);
        mSunContainerParams.width = (int) (((mSunEndDimPixels - mSunStartDimPixels) * (1f - stepTimeFraction)) + mSunStartDimPixels);
        mSunContainerParams.height = (int) (mSunContainerParams.width / 2);

        sun_green.setMaxCurrentDimensions(mSunContainerParams.width / 2, mSunContainerParams.height / 2);
        sun_red.setMaxCurrentDimensions(mSunContainerParams.width / 2, mSunContainerParams.height / 2);

        sun_container.setLayoutParams(mSunContainerParams);

        // Set text for the step progress
        step_progress_bar_text.setText(FragAnimationBase.STRING_EXHALE);

        // Refresh the step progress bar (increase width and change time value)
        refreshStepProgressBar(stepTimeFraction, super.times.exhale, ExerciseManager.EXERCISE_EXHALE);

        // Refresh the global progress bar (increase width)
        refreshGlobalProgressBar(globalTimeFraction);

    }

    @Override
    public void holdExhale(float stepTimeFraction, float globalTimeFraction) {

        // Set text for the step progress
        step_progress_bar_text.setText(FragAnimationBase.STRING_HOLD);

        // Refresh the step progress bar (increase width and change time value)
        refreshStepProgressBar(stepTimeFraction, super.times.holdExhale, ExerciseManager.EXERCISE_HOLD_EXHALE);

        // Refresh the global progress bar (increase width)
        refreshGlobalProgressBar(globalTimeFraction);

    }

    @Override
    public void release() {

        // Recycle bitmaps
        Util.recycleBitmap((ImageView) beach_green);
        Util.recycleBitmap((ImageView) beach_red);
        sun_green.release();
        sun_red.release();

        beach_green = null;
        beach_red = null;
        sun_green = null;
        sun_red = null;
        super.layout = null;

    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
    }

    @Override
    public void enableFullscreen() {

        initializeStuffOnLayoutPreDraw();
    }

    @Override
    public void disableFullscreen() {

        initializeStuffOnLayoutPreDraw();

    }

    /**
     * Changes the width of the global progress bar based on the global time fraction
     * 
     * @param globalTimeFraction
     * @author MAB
     */
    private void refreshGlobalProgressBar(float globalTimeFraction) {
        mGlobalProgressBarParams.width = (int) (layout.getWidth() * globalTimeFraction);
        global_progress_bar_container.setLayoutParams(mGlobalProgressBarParams);
    }

    /**
     * Changes the width of the step progress bar AND also sets the appropriate time value
     * 
     * @param stepTimeFraction
     * @param time
     *            in milliseconds
     * @param exerciseType
     *            see {@link ExerciseManager} for constants
     * @author MAB
     */
    private void refreshStepProgressBar(float stepTimeFraction, int time, int exerciseType) {

        // Set the visibility of the time values
        setBarTimeVisibility(time, exerciseType);

        // Set the end time value
        step_progress_bar_end_time.setText((int) (time / 1000) + "sec");

        // Set the width of the bars
        mStepProgressBarParams.width = (int) (mStepProgressBarWidthPixels * stepTimeFraction);
        step_progress_bar_container.setLayoutParams(mStepProgressBarParams);

        // Set the alpha values
        switch (exerciseType) {
            case ExerciseManager.EXERCISE_INHALE:
                step_progress_bar_red.setAlpha(1f - stepTimeFraction);
                step_progress_bar_green.setAlpha(stepTimeFraction);
                break;
            case ExerciseManager.EXERCISE_EXHALE:
                step_progress_bar_red.setAlpha(stepTimeFraction);
                step_progress_bar_green.setAlpha(1f - stepTimeFraction);
                break;
        }

    }

    /**
     * Based on the type of the exercise AND the time of the exercise, hide of show the time values on the progress bar
     * 
     * @param time
     *            in milliseconds
     * @param exerciseType
     *            see {@link ExerciseManager} for constants
     * @author MAB
     */
    private void setBarTimeVisibility(int time, int exerciseType) {

        // If it's a hold exercise
        if (exerciseType == ExerciseManager.EXERCISE_HOLD_INHALE || exerciseType == ExerciseManager.EXERCISE_HOLD_EXHALE) {

            // If the time is <= 1 sec, then hide the values
            if (time <= 1000) {
                makeInvisible(step_progress_bar_start_time, step_progress_bar_end_time);

                return;
            }
        }

        // If they're not yet visible visible, then make them visible
        makeVisible(step_progress_bar_start_time, step_progress_bar_end_time);

    }

    /**
     * Makes the views visible
     * 
     * @param views
     * @author MAB
     */
    private void makeVisible(View... views) {
        if (views == null) {
            return;
        }
        for (View view : views) {
            if (view != null && view.getVisibility() == View.INVISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Makes the views invisible
     * 
     * @param views
     * @author MAB
     */
    private void makeInvisible(View... views) {
        if (views == null) {
            return;
        }
        for (View view : views) {
            if (view != null && view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initializeStuffOnLayoutPreDraw() {
        super.layout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                if (layout == null) {
                    return true;
                }

                Resources res = mCtx.getResources();

                // IMPORTANT !!! If not removed, it will enter an infinite loop
                if (layout.getViewTreeObserver().isAlive()) {
                    layout.getViewTreeObserver().removeOnPreDrawListener(this);
                }

                /*
                 * Set the height of the dummy view that's between the beach and sun This is used so we don't see the sun images through the beach images when changing alphas
                 */
                ViewGroup.LayoutParams dummyViewParams = dummy_white.getLayoutParams();
                dummyViewParams.height = mShoreHeightPixels;
                dummy_white.setLayoutParams(dummyViewParams);

                /*
                 * Determine the start and end position of the sun
                 */
                mSunStartTopMargin = (int) (layout.getHeight() - mSunStartDimPixels / 2f + (mSunStartDimPixels * 0.10273f));// round up so we don't get a 1 pixel space between the sun and shore
                mSunEndTopMargin = (int) (layout.getHeight() - mShoreHeightPixels - (mSunEndDimPixels / 2f));

                /*
                 * Set the initial position of the sun (almost below the shore line). Just a bit visible
                 */
                mSunContainerParams.topMargin = mSunStartTopMargin;
                sun_container.setLayoutParams(mSunContainerParams);

                /*
                 * Set the position AND width of the entire step progress bar container
                 */
                // Position
                step_progress_bar_entire_container.setPadding(0, 0, res.getDimensionPixelSize(R.dimen.animation_beach_step_progress_bar_padding_right), mShoreHeightPixels + res.getDimensionPixelSize(R.dimen.animation_beach_step_progress_bar_padding_bottom));
                // Width
                mStepProgressBarWidthPixels = (int) (layout.getWidth() * mStepProgressBarWidthFraction);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) step_progress_bar_entire_container.getLayoutParams();
                params.width = mStepProgressBarWidthPixels;
                step_progress_bar_entire_container.setLayoutParams(params);

                return true;
            }
        });
    }
}
