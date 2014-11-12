
package com.innovzen.handlers;

import com.innovzen.o2chair.R;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.innovzen.customviews.CircularSeekBar;

public class CircularSeekBarHandler {

    // Types of the time displayed on the timer
    public static final int TIME_TYPE_LARGE_DIGITAL_DOUBLE = 0;
    public static final int TIME_TYPE_LARGE_NORMAL_SINGLE = 1;
    public static final int TIME_TYPE_SMALL_DIGITAL_DOUBLE = 2;
    public static final int TIME_TYPE_SMALL_DIGITAL_SINGLE = 3;

    // Types of the progress circular bar displayed on the timer
    public static final int PROGRESS_TYPE_BLUE = 0;
    public static final int PROGRESS_TYPE_GREEN = 1;
    public static final int PROGRESS_TYPE_MIXED = 2;

    // Hold view references
    private View layout;
    private View background;
    private CircularSeekBar progress;
    private View contour;
    private View core;
    private CircularSeekBar thumb;

    // The type of the time displayed on the timer
    private int mTimeType;

    // The type of the progress circular bar
    private int mProgressType;

    // If the timer should be small or large
    private boolean mIsSmall;

    // Timer container dimensions
    private int mTimerDim;

    // Round the last digit
    private boolean roundLastDigit;

    /** The type of the animation step for which we're currently filling the progress of the animation timer */
    private int mCurStepType = -1;

    /** The direction of the timer. Used mostly for the ones placed on animations */
    private boolean mClockwise = true;

    /**
     * 
     * @param layout
     * @param isSmall
     * @param timeType
     *            see constants in {@link CircularSeekBarHandler}
     * @param progressType
     *            see constants in {@link CircularSeekBarHandler}
     * @param timerDim
     *            pixels. Both width and height. It's a square !!<br/>
     *            May be -1 if we'll just let it wrap_content or match_parent
     */
    public CircularSeekBarHandler(View layout, boolean isSmall, int timeType, int progressType, int timerDim, boolean roundTimerLastDigit) {
        this.layout = layout;
        this.mTimeType = timeType;
        this.mProgressType = progressType;
        this.mIsSmall = isSmall;
        this.mTimerDim = timerDim;

        this.roundLastDigit = roundTimerLastDigit;

        init();
    }

    private void init() {

        // Get references
        background = this.layout.findViewById(R.id.circular_seekbar_background);
        progress = (CircularSeekBar) this.layout.findViewById(R.id.circular_seekbar_bottom_layer);
        contour = this.layout.findViewById(R.id.circular_seekbar_contour);
        core = this.layout.findViewById(R.id.circular_seekbar_core);
        thumb = (CircularSeekBar) this.layout.findViewById(R.id.circular_seekbar_top_layer);

        // Set the dimension of the entire timer container
        if (mTimerDim != -1) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            params.width = mTimerDim;
            params.height = mTimerDim;
            layout.setLayoutParams(params);
        }

        // Send progress reference to the thumb
        thumb.setBottomCircularBar(progress);

        // Enable stuff of circular seek bars
        progress.setShowCircle(true);
        thumb.setShowThumb(true);

        // Only the thumb will change the time value
        thumb.setTextTime(true);

        setTimeType();

        // Let the progress know it's small or large
        progress.setIsSmall(mIsSmall);

        // Set the progress image type
        progress.setProgressImageType(mProgressType);

        // Set ViewTree observers
        thumb.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                // IMPORTANT !!! If not removed, it will enter an infinite loop
                if (thumb.getViewTreeObserver().isAlive()) {
                    thumb.getViewTreeObserver().removeOnPreDrawListener(this);
                }

                int margin = (int) (thumb.getThumbDim() / 4);

                setTimerLayerMargins(background, margin);
                setTimerLayerMargins(contour, margin);
                setTimerLayerMargins(progress, margin);
                setTimerLayerMargins(core, margin);

                layout.setVisibility(View.VISIBLE);
                
                return true;
            }
        });

        // set should we round or not the last digit
        this.thumb.setRoundLastDigit(roundLastDigit);

    }

    /**
     * @param min
     *            seconds
     * @param max
     *            seconds
     * @param startingValue
     *            seconds
     * @author MAB
     */
    public void setProgress(int min, int max, float startingValue, int totalDuration, float remainingDuration) {
        thumb.setSeekBarValues(min, totalDuration, remainingDuration);
        progress.setSeekBarValues(min, max, startingValue);
    }

    public void setProgress(int min, int max, float startingValue) {
        thumb.setSeekBarValues(min, max, startingValue);
        progress.setSeekBarValues(min, max, startingValue);
    }

    /**
     * @param min
     *            milliseconds
     * @param max
     *            milliseconds
     * @param startingValue
     *            milliseconds
     * @author MAB
     */
    public void setProgressMillis(int min, int max, float startingValue, float totalDuration, float remainingDuration) {
        setProgress(min / 1000, max / 1000, startingValue / 1000, (int) totalDuration / 1000, (1 - remainingDuration) * totalDuration / 1000);
    }

    /**
     * @param min
     *            milliseconds
     * @param max
     *            milliseconds
     * @param startingValue
     *            milliseconds
     * @author MAB
     */
    public void setProgressMillisWithStepType(int stepType, int min, int max, float startingValue, float totalDuration, float remainingDuration) {

        // If this is a new step, then change the direction that the timer is spinning
        if (mCurStepType != stepType && max > 0) {

            mCurStepType = stepType;

            mClockwise = !mClockwise;

        }

        if (mClockwise) {
            setProgress(min / 1000, max / 1000, (max - startingValue) / 1000, (int) totalDuration / 1000, (1f - remainingDuration) * totalDuration / 1000);
        } else {
            setProgress(min / 1000, max / 1000, startingValue / 1000, (int) totalDuration / 1000, (1f - remainingDuration) * totalDuration / 1000);
        }

    }

    public void setProgressMillis(int min, int max, float startingValue) {
        setProgress(min / 1000, max / 1000, startingValue / 1000);
    }

    /**
     * Set the values only for the progress ring and not the inner text value
     * 
     * @param min
     *            milliseconds
     * @param max
     *            milliseconds
     * @param startingValue
     *            milliseconds
     * @author MAB
     */
    public void setProgressMillisProgressRing(int min, int max, float startingValue) {
        // setProgress(min / 1000, max / 1000, startingValue / 1000);
    }

    /**
     * Set the values only for the inner text value and not the progress ring
     * 
     * @param min
     *            milliseconds
     * @param max
     *            milliseconds
     * @param startingValue
     *            milliseconds
     * @author MAB
     */
    public void setProgressMillisProgressText(int min, int max, float startingValue) {
        // ?????????? setProgress(min / 1000, max / 1000, startingValue / 1000);
    }

    public void showThumb(boolean show) {
        thumb.setShowThumb(show);
    }

    // public void setCurrentProgress(int seconds) {
    // thumb.setCurrentProgress(seconds);
    // progress.setCurrentProgress(seconds);
    // }

    private void setTimerLayerMargins(View view, int margin) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.setMargins(margin, margin, margin, margin);
        view.setLayoutParams(params);
    }

    private void setTimeType() {
        // Set the type of the time (font + other)
        switch (mTimeType) {
            case TIME_TYPE_LARGE_DIGITAL_DOUBLE:
                thumb.setLargeDigitalDouble();
                break;
            case TIME_TYPE_LARGE_NORMAL_SINGLE:
                thumb.setLargeNormalSingle();
                break;
            case TIME_TYPE_SMALL_DIGITAL_DOUBLE:
                thumb.setSmallDigitalDouble();
                break;
            case TIME_TYPE_SMALL_DIGITAL_SINGLE:
                thumb.setSmallDigitalSingle();
                break;
        }
    }

    public void onResume() {

        // The font goes crazy
        setTimeType();

        // Set the progress to there the thumb is
        progress.setProgress(thumb.getProgressValue());
    }

    /**
     * @return milliseconds
     * @author MAB
     */
    public int getProgressMilliseconds() {
        return thumb.getProgressInMillis();
    }

    public void reseStepType() {
        mCurStepType = -1;
    }
    
    public void setVisible(int visible){
    	layout.setVisibility(visible);
    }
}
