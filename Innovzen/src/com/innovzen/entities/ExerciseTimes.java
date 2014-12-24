
package com.innovzen.entities;

import android.content.Context;

import com.innovzen.utils.MyPreference;

public class ExerciseTimes {

    /** Constants used to represent the beginner times */
    public static final int DEFAULT_TIMER_INHALE = 5000;
    public static final int DEFAULT_TIMER_HOLD_INHALE = 1000;
    public static final int DEFAULT_TIMER_EXHALE = 5000;
    public static final int DEFAULT_TIMER_HOLD_EXHALE = 1000;
    
    /** Constants used to represent the relax times */
    public static final int RELAX_TIMER_INHALE = 4000;
    public static final int RELAX_TIMER_HOLD_INHALE = 1000;
    public static final int RELAX_TIMER_EXHALE = 6000;
    public static final int RELAX_TIMER_HOLD_EXHALE = 1000;
    
    /** Constants used to represent the performance times */
    public static final int PERFORMANCE_TIMER_INHALE = 6000;
    public static final int PERFORMANCE_TIMER_HOLD_INHALE = 1000;
    public static final int PERFORMANCE_TIMER_EXHALE = 4000;
    public static final int PERFORMANCE_TIMER_HOLD_EXHALE = 1000;
    
    /**beginner*/
    public static final int BEGINNER_TIMER_INHALE = 4000;
    public static final int BEGINNER_TIMER_HOLD_INHALE = 4000;
    public static final int BEGINNER_TIMER_EXHALE = 4000;
    public static final int BEGINNER_TIMER_HOLD_EXHALE = 1000;
    /**intermediate*/
    public static final int INTERMEDIATE_TIMER_INHALE = 4000;
    public static final int INTERMEDIATE_TIMER_HOLD_INHALE = 6000;
    public static final int INTERMEDIATE_TIMER_EXHALE = 8000;
    public static final int INTERMEDIATE_TIMER_HOLD_EXHALE = 1000;
    /**pro*/
    public static final int PRO_TIMER_INHALE = 4000;
    public static final int PRO_TIMER_HOLD_INHALE = 8000;
    public static final int PRO_TIMER_EXHALE = 12000;
    public static final int PRO_TIMER_HOLD_EXHALE = 4000;
    /**
     * 类型名称
     */
    public static final int DEFAULT_EXERCISE_TIME=0;
    public static final int RELAX_EXERCISE_TIME=1;
    public static final int PERFORMANCE_EXERCISE_TIME=2;
    
    public static final int BEGINNER_TIME=3;
    public static final int INTERMEDIATE_TIME=4;
    public static final int PRO_TIME=5;
    /**
     * 时间类型
     * 0 balanced
     * 1 relax
     * 2 performance
     */
    public int type=0;
    
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
    
    /**
     * 设置时间类型
     * @param type
     */
    public void setTimeType(int type){
    	switch (type) {
		case DEFAULT_EXERCISE_TIME:
	        this.inhale = DEFAULT_TIMER_INHALE;
	        this.holdInhale = DEFAULT_TIMER_HOLD_INHALE;
	        this.exhale = DEFAULT_TIMER_EXHALE;
	        this.holdExhale = DEFAULT_TIMER_HOLD_EXHALE;
			break;
		case RELAX_EXERCISE_TIME:
	        this.inhale = RELAX_TIMER_INHALE;
	        this.holdInhale = RELAX_TIMER_HOLD_INHALE;
	        this.exhale = RELAX_TIMER_EXHALE;
	        this.holdExhale = RELAX_TIMER_HOLD_EXHALE;
			break;
		
		case PERFORMANCE_EXERCISE_TIME:
	        this.inhale = PERFORMANCE_TIMER_INHALE;
	        this.holdInhale = PERFORMANCE_TIMER_HOLD_INHALE;
	        this.exhale = PERFORMANCE_TIMER_EXHALE;
	        this.holdExhale = PERFORMANCE_TIMER_HOLD_EXHALE;
			break;
		case BEGINNER_TIME:
	        this.inhale = BEGINNER_TIMER_INHALE;
	        this.holdInhale = BEGINNER_TIMER_HOLD_INHALE;
	        this.exhale = BEGINNER_TIMER_EXHALE;
	        this.holdExhale = BEGINNER_TIMER_HOLD_EXHALE;
	        break;
		case INTERMEDIATE_TIME:
	        this.inhale = INTERMEDIATE_TIMER_INHALE;
	        this.holdInhale = INTERMEDIATE_TIMER_HOLD_INHALE;
	        this.exhale = INTERMEDIATE_TIMER_EXHALE;
	        this.holdExhale = INTERMEDIATE_TIMER_HOLD_EXHALE;
	        break;
		case PRO_TIME:
	        this.inhale = PRO_TIMER_INHALE;
	        this.holdInhale = PRO_TIMER_HOLD_INHALE;
	        this.exhale = PRO_TIMER_EXHALE;
	        this.holdExhale = PRO_TIMER_HOLD_EXHALE;
			break;
		default:
			break;
		}
    }
    /**
     * 
     * @param context
     */
    public void initTime(Context context){
    	exerciseDuration = MyPreference.getInstance(context).readInt(MyPreference.TIME);
    	//exerciseDuration=20*1000;
    }

}
