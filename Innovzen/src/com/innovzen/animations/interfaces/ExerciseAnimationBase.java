
package com.innovzen.animations.interfaces;

import android.view.View;
import android.view.ViewGroup;

import com.innovzen.entities.ExerciseTimes;
import com.innovzen.handlers.CircularSeekBarHandler;

/**
 * Every exercise animation should implement these methods<br/>
 * 
 * @author MAB
 * 
 */
public abstract class ExerciseAnimationBase {

    /**
     * The values required for the inhale/exhale/holdinhale/holdexhale times<br/>
     * If either one is <= 0 it will be ignore
     */
    protected ExerciseTimes times;

    /**
     * The ViewGroup that holds all the animation's views
     */
    protected ViewGroup layout;

    /**
     * Sets up the values pertaining to the inhale/exhale processes. See {@link #inhale()}, {@link #holdInhale()}, {@link #exhale()}, {@link #holdExhale()} to start the actual animations based on these values
     * 
     * @param times
     *            the times for the 4 types of user actions
     * @author MAB
     */
    public abstract void configure(ExerciseTimes times);

    /**
     * Process the inhale/exhale/hold_inhle/hold_exhale values and obtain necessary time values for each individual animation (i.e. for petal animation determine the time to animate one petal)
     * 
     * @author MAB
     */
    protected abstract void processTimes();

    /**
     * Starts the "inhale" part of the animation. Resets any other animation already started.
     * 
     * @param stepTimeFraction
     *            the time spent in the current exercise step<br/>
     *            value in interval [0.0, 1.0]
     * @param globalTimeFraction
     *            the amount of time passed from the beginning of the entire exercise<br/>
     *            value in interval [0.0, 1.0]
     * @author MAB
     */
    public abstract void inhale(float stepTimeFraction, float globalTimeFraction);

    /**
     * Stops the animation for the period of time marked by the hold inhale value.
     * 
     * @param stepTimeFraction
     *            the time spent in the current exercise step<br/>
     *            value in interval [0.0, 1.0]
     * @param globalTimeFraction
     *            the amount of time passed from the beginning of the entire exercise<br/>
     *            value in interval [0.0, 1.0]
     * @author MAB
     */
    public abstract void holdInhale(float stepTimeFraction, float globalTimeFraction);

    /**
     * Starts the "exhale" part of the animation. Resets any other animation already started.
     * 
     * @param stepTimeFraction
     *            the time spent in the current exercise step<br/>
     *            value in interval [0.0, 1.0]
     * @param globalTimeFraction
     *            the amount of time passed from the beginning of the entire exercise<br/>
     *            value in interval [0.0, 1.0]
     * @author MAB
     */
    public abstract void exhale(float stepTimeFraction, float globalTimeFraction);

    /**
     * Stops the animation for the period of time marked by the hold exhale value;
     * 
     * @param stepTimeFraction
     *            the time spent in the current exercise step<br/>
     *            value in interval [0.0, 1.0]
     * @param globalTimeFraction
     *            the amount of time passed from the beginning of the entire exercise<br/>
     *            value in interval [0.0, 1.0]
     * @author MAB
     */
    public abstract void holdExhale(float stepTimeFraction, float globalTimeFraction);

    /**
     * Releases any memory consuming assets/processes. Recycles bitmaps, etc
     * 
     * @author MAB
     */
    public abstract void release();

    /**
     * Stops animations and resets everything to default values
     * 
     * @author MAB
     */
    public abstract void reset();

    /**
     * Let the animation know that we want to display it in fullscreen or not
     * 
     * @param isFullscreen
     * @author MAB
     */
    public abstract void enableFullscreen();

    /**
     * Let the animation know that we want to revert back to a non-fullscreen mode
     * 
     * @param isFullscreen
     * @author MAB
     */
    public abstract void disableFullscreen();

    /**
     * @return the viewgroup that holds all the views of the animation. This needs to be added to the fragment's/activity's layout
     * @author MAB
     */
    public View getLayout() {
        return layout;
    }
    /**
     * 
     */
    public CircularSeekBarHandler  getTimeHandler() {
		return null;
	}
}
