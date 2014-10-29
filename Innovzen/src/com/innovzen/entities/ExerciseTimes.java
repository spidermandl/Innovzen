
package com.innovzen.entities;

public class ExerciseTimes {

    /**
     * value in milliseconds<br/>
     * If <= 0 the action will be ignored
     */
    public int inhale;
    /**
     * value in milliseconds<br/>
     * If <= 0 the action will be ignored
     */
    public int holdInhale;
    /**
     * value in milliseconds<br/>
     * If <= 0 the action will be ignored
     */
    public int exhale;
    /**
     * value in milliseconds<br/>
     * If <= 0 the action will be ignored
     */
    public int holdExhale;

    /**
     * The entire time allocated for the exercise<br/>
     * value in milliseconds
     */
    public int exerciseDuration;

    public ExerciseTimes() {
    }

    /**
     * @param inhale
     *            value in milliseconds<br/>
     *            Can not be <= 0
     * @param holdInhale
     *            value in milliseconds<br/>
     *            If <= 0 the action will be ignored
     * @param exhale
     *            value in milliseconds<br/>
     *            Can not be <= 0
     * @param holdExhale
     *            value in milliseconds<br/>
     *            If <= 0 the action will be ignored
     */
    public ExerciseTimes(int inhale, int holdInhale, int exhale, int holdExhale, int exerciseDuration) {
        this.inhale = inhale;
        this.holdInhale = holdInhale;
        this.exhale = exhale;
        this.holdExhale = holdExhale;
        this.exerciseDuration = exerciseDuration;
    }

    @Override
    public String toString() {
        return "ExerciseTimes [inhale=" + inhale + ", holdInhale=" + holdInhale + ", exhale=" + exhale + ", holdExhale=" + holdExhale + ", exerciseDuration=" + exerciseDuration + "]";
    }

}
