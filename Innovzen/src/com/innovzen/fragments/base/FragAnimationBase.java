
package com.innovzen.fragments.base;

import com.innovzen.o2chair.R;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.activities.ActivityMain;
import com.innovzen.bluetooth.check.ResetCheck;
import com.innovzen.db.History;
import com.innovzen.entities.ExerciseTimes;
import com.innovzen.fragments.FragAnimationPicker;
import com.innovzen.fragments.FragMusic;
import com.innovzen.fragments.FragSoundPicker;
import com.innovzen.fragments.FragTimer;
import com.innovzen.handlers.ExerciseAnimationHandler;
import com.innovzen.handlers.ExerciseManager;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.interfaces.FragmentOnBackPressInterface;
import com.innovzen.utils.LocalDbUtil;
import com.innovzen.utils.MyPreference;
import com.innovzen.utils.PersistentUtil;

/**
 * Fragment should be called with some extras. See ANIMATION_xxx for types of animations available (int values) and also use {@link #KEY_ANIMATION_TYPE} for the bundle key<br/>
 * Note: If no animation type specified in the bundle, the default {@link #ANIMATION_PETALS} will be used
 * 
 * @author MAB
 * 
 */

/**
 * 嵌入有呼气动画的fragment，抽象成父类
 * @author desmond.duan
 * 
 */

public class FragAnimationBase extends FragBase implements FragmentOnBackPressInterface {
	

    /** The delay between each value of the countdown */ 
    private static final int COUNTDOWN_DELAY = 1000; // 1 sec

    /** The minimum time (in miliseconds) for the duration of an exercise */
    public static final int MIN_TIME_EXERCISE_DURATION = 6 * 60 * 1000; // 5min

    /** Hold the types of possible text values displayed at the top of the screen */
    // beginning
    public static final int EXERCISE_TYPE_START_NORMAL = 1000; // Exercise
    public static final int EXERCISE_TYPE_START_CUSTOM = 1001; // Reglages personalises
    // middle
    public static final int EXERCISE_TYPE_MIDDLE_ADVANCE = 2000; // ADVANCES. May be left -1
    // end
    public static final int EXERCISE_TYPE_END_BALANCE = 3000;
    public static final int EXERCISE_TYPE_END_PERFORMANCE = 3001;
    public static final int EXERCISE_TYPE_END_RELAX = 3002;
    public static final int EXERCISE_TYPE_END_BEGINNER = 3003;
    public static final int EXERCISE_TYPE_END_INTERMEDIARY = 3004;
    public static final int EXERCISE_TYPE_END_PRO = 3005;
    public static final int EXERCISE_TYPE_END_CUSTOM = 3006;

    /**
     * Hold the shared preferences key for the exercise type displayed on the animation screen at the top, as a subtitle<br/>
     * Of type int<br/>
     * Default value is {@link #EXERCISE_TYPE_BALANCE}
     */
    public static final String PERSIST_EXERCISE_TYPE_START = "exercise_type_start_index";
    public static final String PERSIST_EXERCISE_TYPE_MIDDLE = "exercise_type_middle_index";
    public static final String PERSIST_EXERCISE_TYPE_END = "exercise_type_end_index";

    /** The shared preferences keys for each of the 4 times in an animation */
    public static final String PERSIST_TIME_INHALE = "time_inhale";
    public static final String PERSIST_TIME_HOLD_INHALE = "time_hold_inhale";
    public static final String PERSIST_TIME_EXHALE = "time_exhale";
    public static final String PERSIST_TIME_HOLD_EXHALE = "time_hold_exhale";

    /** The shared preferences key for the selected duration of the entire exercise */
    public static final String PERSIST_TOTAL_SELECTED_EXERCISE_DURATION = "total_selected_exercise_duration";

    /** Hold the time (in millis) for the play button scale and fade out */
    protected final int PLAY_BTN_ANIM_DURATION = 450; // millis

    /** Hold the toScale X and also Y of the play button */
    protected final float PLAY_BTN_TO_SCALE = 3f;

    /** Hold bundle keys */
    public static final String KEY_ANIMATION_TYPE = "animation_type";

    /**
     * Hold the strings used for inhale, exhale, hold<br/>
     * Do it like this so we don't have to use getString() on every animation frame
     */
    public static String STRING_INHALE;
    public static String STRING_HOLD;
    public static String STRING_EXHALE;

    // Hold view references
    //<Desmond>
    //protected DrawerRightHandler mDrawerHandler;
    //</Desmond>
    protected ImageView fullscreen_btn;
    //protected TextView animation_type;
    protected View play_overlay;
    protected View play_overlay_btn;
    protected RelativeLayout footer;
    protected View header;
    protected View dummy_white_background_for_fullscreen;
    protected View animation_parent_container;
    protected TextView countdown_tv;

    /** Hold the footer handler */
    protected FooterHandler mFooterHandler;

    /** Hold the object that handles the exercise time keeper */
    protected ExerciseManager mExerciseManager;
    
    /** Hold the selected animation by the user */
    protected int mSelectedExerciseAnimationType = -1;
//一个标志来指示方法animationCountdown是否应该继续,开始另一个1秒延迟
    /** A flag to indicate whether the method animationCountdown should continue and start another 1 sec delay */
    protected boolean mContinueCountdown = false;
    
    /**判断动画是否正在运行*/
    protected boolean isAnimationRunning=false;

    /** The current second on which the countdown is on */
    protected int mCurCountdownSecond = 0;

    /**
     * Hold the current exercise type index. Based on this, load the appropriate string in the "subtitle" section.<br/>
     * Default value {@link #EXERCISE_TYPE_BALANCE}
     */
    protected int mExerciseTypeStartIndex = -1;
    protected int mExerciseTypeMiddleIndex = -1;
    protected int mExerciseTypeEndIndex = -1;

    /** Hold the inhale, hold_inhale, exhale, hold_exhale values for the animation. Values in miliseconds. */
    protected ExerciseTimes mTimes = new ExerciseTimes();
    
    
    /**动画view*/
    protected ExerciseAnimationHandler animationHandler;

    /** Hold the animation event listener for the play animation overlay */
    //动画设置
    AnimationListener mPlayAnimListener = new AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {

            // Hide the view immediately
            hidePlayBtn();

            // Set the flag so we'll know in the animateCountdown to start animating
            mContinueCountdown = true;

            // Set the initial value of the countdown
            mCurCountdownSecond = 3;
            //是否倒数321
            
            
            // Hide countdown in case it's visible
           
            countdown_tv.setVisibility(View.VISIBLE);

            // Start and animate the countdown
            animateCountdown();
        }
    };

    /** The onclick listener for this class */
    protected OnClickListener mClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.footer_icon_right:
                	//<Desmond>
//                    if (mDrawerHandler != null) {
//
//                        pauseExercise();
//
//                        mDrawerHandler.show();
//                    }
                  //<Desmond>
                    break;
                case R.id.animation_play_overlay_btn:
                    overlayBtnPressed();
                    break;
            }
        }
    };

    /**
     * When the user presses the overlay button, we need to display the countdown first and only then start the exercise and save to history
     * 
     * @author MAB
     */
    //只能按一次
    protected void overlayBtnPressed() {
        // Disable the button (so we can't click on it again)
        play_overlay_btn.setClickable(false);
        // Perform the animation
        animatePlayButton();
    }

    /**
     * After all the animations are over, we can start the exercise and store the event in the database for the history
     * 
     * @author MAB
     */
    //点击开始练习按钮
    protected void startExercise() {

        mCurCountdownSecond = 0;

        mContinueCountdown = false;

        // Hide the countdown overlay
        countdown_tv.setVisibility(View.GONE);

        // Re-enable the button (so we can click on it once again)
        play_overlay_btn.setClickable(true);

        // Start the exercise animation
        mExerciseManager.start();

        // Save the data in the databases
        //LocalDbUtil.getHistoryDao(getActivity()).insert(new History(null, System.currentTimeMillis(), mTimes.inhale / 1000, mTimes.holdInhale / 1000, mTimes.exhale / 1000, mTimes.holdExhale / 1000, (long) mTimes.exerciseDuration / 1000));
    }

    /**
     * Call this in the onCreateView method of the subclass
     * 
     * @param view
     * @author MAB
     */
    public void onView(View view) {

        /*
         * Get the animation type from the bundle (if available)
         */
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(KEY_ANIMATION_TYPE)) {
                mSelectedExerciseAnimationType = bundle.getInt(KEY_ANIMATION_TYPE);
            }
        }
        // If it's still -1 at this point ...
        if (mSelectedExerciseAnimationType == -1) {
            // Get the one stored in the shared preferences
            mSelectedExerciseAnimationType = PersistentUtil.getInt(getActivity(), FragAnimationPicker.PERSIST_SELECTED_EXERCISE_ANIMATION);

            // If this one is -1 as well, then just load the dafault one
            if (mSelectedExerciseAnimationType == -1) {
                mSelectedExerciseAnimationType = FragAnimationPicker.DEFAULT_EXERCISE_ANIMATION;
            } else {
            }
        }

        /*
         * Get the type of the exercise that needs to be placed in the "subtitle" section
         */
        mExerciseTypeStartIndex = PersistentUtil.getInt(getActivity(), PERSIST_EXERCISE_TYPE_START);
        mExerciseTypeMiddleIndex = PersistentUtil.getInt(getActivity(), PERSIST_EXERCISE_TYPE_MIDDLE);
        mExerciseTypeEndIndex = PersistentUtil.getInt(getActivity(), PERSIST_EXERCISE_TYPE_END);

        init(view);

    }

    @Override
    public boolean onBackPress() {
    	//<Desmond>
//        if (mDrawerHandler.isOpen()) {
//
//            mDrawerHandler.hide();
//
//            return true;
//        }
      //<Desmond>

        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
      
        mExerciseManager.reinitUI(null, null);
        //pauseExercise();
        
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //mExerciseManager.release();

    }

    @Override
    public void init(View view) {
    	mBluetoothCheck=((ActivityMain)getActivity()).getResetCheck();
    	
    	
        // Get references
    	//<Desmond>
        //DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        //animation_type = (TextView) view.findViewById(R.id.animation_type);
    	//<Desmond>
        play_overlay = view.findViewById(R.id.animation_play_overlay);
        animation_parent_container = view.findViewById(R.id.animation_animation_container);
        play_overlay_btn = view.findViewById(R.id.animation_play_overlay_btn);
      //<Desmond>
      //header = view.findViewById(R.id.reusable_header);
      //<Desmond>
        fullscreen_btn = (ImageView) view.findViewById(R.id.animation_fullscreen);
        dummy_white_background_for_fullscreen = view.findViewById(R.id.animation_dummy_white_view_for_fullscreen);
        countdown_tv = (TextView) view.findViewById(R.id.animation_countdown);

        // Set event listeners
        // ...

        // Get and keep the strings used on the animations for the steps (inhale, hold and exhale)
        FragAnimationBase.STRING_INHALE = getString(R.string.animation_step_inhale);
        FragAnimationBase.STRING_HOLD = getString(R.string.animation_step_hold);
        FragAnimationBase.STRING_EXHALE = getString(R.string.animation_step_exhale);

        /*
         * Initialize the drawer
         */
        // Init the handler
      //<Desmond>
//        mDrawerHandler = new DrawerRightHandler(super.activityListener, drawer);
//        // Don't display the semi-transparent black overlay around the drawer
//        drawer.setScrimColor(Color.parseColor("#00FFFFFF"));
//        // Disable the drawer toggle using the swipe gestures
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
      //<Desmond>

        // Get the times for the 4 steps of an exercise
//        mTimes.inhale = PersistentUtil.getInt(getActivity(), PERSIST_TIME_INHALE, 0);
//        mTimes.holdInhale = PersistentUtil.getInt(getActivity(), PERSIST_TIME_HOLD_INHALE, 0);
//        mTimes.exhale = PersistentUtil.getInt(getActivity(), PERSIST_TIME_EXHALE, 0);
//        mTimes.holdExhale = PersistentUtil.getInt(getActivity(), PERSIST_TIME_HOLD_EXHALE, 0);
//        // In case they're all 0, then set the default values for the BEGINNER times
//        if (mTimes.inhale == 0 && mTimes.holdInhale == 0 && mTimes.exhale == 0 && mTimes.holdExhale == 0) {
//            mTimes.inhale = FragTimerAdvance.DEFAULT_TIMER_INHALE;
//            mTimes.holdInhale = FragTimerAdvance.DEFAULT_TIMER_HOLD_INHALE;
//            mTimes.exhale = FragTimerAdvance.DEFAULT_TIMER_EXHALE;
//            mTimes.holdExhale = FragTimerAdvance.DEFAULT_TIMER_HOLD_EXHALE;
//        }
//
//        // Get the selected duration for the entire exercise
//        mTimes.exerciseDuration = PersistentUtil.getInt(getActivity(), PERSIST_TOTAL_SELECTED_EXERCISE_DURATION, MIN_TIME_EXERCISE_DURATION);
//
//        /*
//         * Get the exercise times and place them in an object
//         */
//        ExerciseTimes times = new ExerciseTimes(mTimes.inhale, mTimes.holdInhale, mTimes.exhale, mTimes.holdExhale, mTimes.exerciseDuration);
//
//        /*
//         * Init the animation handler
//         */
//        // Instantiate an animation handler
//        animationHandler = new ExerciseAnimationHandler(getActivity(), animation_parent_container, mSelectedExerciseAnimationType);
//        animationHandler.configure(times);
//
//        /*
//         * Init the exercise time handler
//         */
//        int voiceSoundId = PersistentUtil.getInt(getActivity(), FragSoundPicker.PERSIST_SELECTED_VOICE);
//        int ambianceSoundId = PersistentUtil.getInt(getActivity(), FragSoundPicker.PERSIST_SELECTED_AMBIANCE);
//        
        mExerciseManager = 
        		/**
        		 * Desmond
        		 */
        		//new ExerciseManager(this, animationHandler, super.activityListener, times, voiceSoundId, ambianceSoundId);
        		//new SyncExerciseManager(this, animationHandler, super.activityListener, times, voiceSoundId, ambianceSoundId);
        		((ActivityMain)getActivity()).getExerciseManager();      
        /*
	      * Init the animation handler
	      */
	    // Instantiate an animation handler
        animationHandler = new ExerciseAnimationHandler(getActivity(), animation_parent_container, mSelectedExerciseAnimationType);
        animationHandler.getCurAnimation().getTimeHandler().setProgressMillis(FragTimer.DEFAULT_TIMER_MIN, FragTimer.DEFAULT_TIMER_MAX, mTimes.exerciseDuration);
        mExerciseManager.reinitUI(this, animationHandler);
        mTimes=mExerciseManager.getExerciseTimes();
        animationHandler.configure(mTimes);

        int voiceSoundId = //PersistentUtil.getInt(getActivity(), FragSoundPicker.PERSIST_SELECTED_VOICE);
        		MyPreference.getInstance(getActivity()).readInt(MyPreference.SELECTED_VOICE);
        int ambianceSoundId = //PersistentUtil.getInt(getActivity(), FragSoundPicker.PERSIST_SELECTED_AMBIANCE);
        		MyPreference.getInstance(getActivity()).readInt(FragMusic.PERSIST_SELECTED_AMBIANCE);
        mExerciseManager.reinitSound(voiceSoundId, ambianceSoundId);
        /*
         * Based on the exercise type index, load the appropriate string in the "subtitle" section
         */
        setSubtitleText();

        // Make sure it's on top
        play_overlay.bringToFront();

        // Force the device NOT to enter stand-by mode when this view is visible a.k.a. when this fragment is visible
        animation_parent_container.setKeepScreenOn(true);

    }

    /**
     * Animates the play button scale and fade out after which it starts the exercise animation
     * 
     * @author MAB
     */
    //加入动画 播放动画
    protected void animatePlayButton() {

//        AnimationFactory animFactory = new AnimationFactory();
//
//        AnimationSet animSet = new AnimationSet(true);
//        animSet.addAnimation(animFactory.getScaleAnimation(PLAY_BTN_ANIM_DURATION, PLAY_BTN_TO_SCALE, PLAY_BTN_TO_SCALE));
//        animSet.addAnimation(animFactory.getFadeOutAnimation(PLAY_BTN_ANIM_DURATION));
//        animSet.setAnimationListener(mPlayAnimListener);
//
//        play_overlay.startAnimation(animSet);
        
        // Hide the view immediately
        hidePlayBtn();

        // Set the flag so we'll know in the animateCountdown to start animating
        mContinueCountdown = true;
        
        // Set the initial value of the countdown
        mCurCountdownSecond = 3;

        // Hide countdown in case it's visible
        // Start and animate the countdown
        animateCountdown();
    }

    /**
     * Start a 1 sec timer used for the countdown.<br/>
     * IMPORTANT !!!! Method is recursive.<br/>
     * After it has been called N times (3 times because of 3 2 1 countdown)
     * 
     * @author MAB
     */
    protected void animateCountdown() {

        // Because we can cancel the start, we should wait and not
        if (mContinueCountdown) {

        	isAnimationRunning=true;
        	
            // Set the new value of the countdown
        	//chy
        	if(mExerciseManager.getPlayCount()>0){
        	   countdown_tv.setVisibility(View.INVISIBLE);
        	}else{
      		  countdown_tv.setVisibility(View.VISIBLE);

        	}
            countdown_tv.setText(mCurCountdownSecond + "");

            // Start a delay of 1 second
            //延时一秒启动里练习
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    // If we've just finished "animating" second 1, then the next step is to stop and start the exercise
                    if (mCurCountdownSecond == 1) {

                    	/**
                    	 * Desmond
                    	 */
                        startExercise();
                    	//</Desmond>

                    } else {

                        mCurCountdownSecond -= 1;

                        animateCountdown();

                    }

                }
            }, COUNTDOWN_DELAY); // 1 sec

        }

    }

    /**
     * Stops everything in its tracks and also displays the play btn overlay
     * 
     * @author MAB
     */
    protected void pauseExercise() {
        
    	isAnimationRunning=false;
    	
        // In case we're counting down, set this flag so the method animateCountdown will know to stop
        mContinueCountdown = false;

        // Reset the second on which the counter is currently on
        mCurCountdownSecond = 0;

        // Hide countdown in case it's visible
        countdown_tv.setVisibility(View.GONE);

        // Re-enable the button (so we can click on it once again)
        play_overlay_btn.setClickable(true);

        // Stop everything related to the exercise animation
        
        mExerciseManager.pause(true);
        
    }

    /**
     * Stops everything in its tracks and also displays the play btn overlay
     * 
     * @author MAB
     */
    public void stopExercise() {
        
    	isAnimationRunning=false;
    	
        // In case we're counting down, set this flag so the method animateCountdown will know to stop
        mContinueCountdown = false;

        // Reset the second on which the counter is currently on
        mCurCountdownSecond = 0;

        // Hide countdown in case it's visible
        countdown_tv.setVisibility(View.GONE);

        // Stop everything related to the exercise animation
        mExerciseManager.stopAllThreads();
        mExerciseManager.reset(false);
    }
    /**
     * Makes the play button overlay visible
     * 
     * @author MAB
     */
    public void showPlayBtn() {
        play_overlay.setVisibility(View.VISIBLE);
    }

    /**
     * Makes the play button overlay invisible (in fact GONE)
     * 
     * @author MAB
     */
    protected void hidePlayBtn() {
        play_overlay.setVisibility(View.GONE);

    }

    /**
     * Based on the values got from the shared preferences, populate the subtitle section with the appropriate text
     * 
     * @author MAB
     */
    protected void setSubtitleText() {
        Resources res = getResources();
        String str = "";

        // START
        switch (mExerciseTypeStartIndex) {
            case EXERCISE_TYPE_START_NORMAL:
                str += res.getString(R.string.animation_subtitle_start_normal);
                break;
            case EXERCISE_TYPE_START_CUSTOM:
                str += res.getString(R.string.animation_subtitle_start_custom);
                break;
            case -1:
            default:
                str += res.getString(R.string.animation_subtitle_start_normal);
                break;
        }

        // MIDDLE
        str += " ";
        switch (mExerciseTypeMiddleIndex) {
            case EXERCISE_TYPE_MIDDLE_ADVANCE:
                str += res.getString(R.string.animation_subtitle_middle_advance);
                if (mExerciseTypeEndIndex == EXERCISE_TYPE_END_CUSTOM) {
                    // Nothin
                } else {
                    str += ", ";
                }
                break;
            case -1:
            default:
                break;
        }

        // END
        str += " ";
        switch (mExerciseTypeEndIndex) {
            case EXERCISE_TYPE_END_BALANCE:
                str += res.getString(R.string.animation_subtitle_end_balance);
                break;
            case EXERCISE_TYPE_END_PERFORMANCE:
                str += res.getString(R.string.animation_subtitle_end_performance);
                break;
            case EXERCISE_TYPE_END_RELAX:
                str += res.getString(R.string.animation_subtitle_end_relax);
                break;
            case EXERCISE_TYPE_END_BEGINNER:
                str += res.getString(R.string.animation_subtitle_end_beginner);
                break;
            case EXERCISE_TYPE_END_INTERMEDIARY:
                str += res.getString(R.string.animation_subtitle_end_intermediary);
                break;
            case EXERCISE_TYPE_END_PRO:
                str += res.getString(R.string.animation_subtitle_end_pro);
                break;
            case EXERCISE_TYPE_END_CUSTOM:
                // Nope. Nothin to see here.
                break;
            case -1:
            default:
                str += res.getString(R.string.animation_subtitle_end_balance);
                break;
        }

        // Set the string
        //<Desmond>
        //animation_type.setText(str);
        //</Desmond>
    }

    /**
     * Make stuff disappear
     * 
     * @author MAB
     */
    protected void enableFullscreen() {

       dummy_white_background_for_fullscreen.setVisibility(View.VISIBLE);

        fullscreen_btn.setImageResource(R.drawable.selector_icon_fullscreen_out);

        mExerciseManager.toggleFullscreen();
      //<Desmond>
//        footer.setVisibility(View.GONE);
//        header.setVisibility(View.GONE);
      //<Desmond>

    }

    /**
     * Make stuff appear
     * 
     * @author MAB
     */
    protected void disableFullscreen() {

        dummy_white_background_for_fullscreen.setVisibility(View.GONE);

        fullscreen_btn.setImageResource(R.drawable.selector_icon_fullscreen_in);

        mExerciseManager.toggleFullscreen();
      //<Desmond>
//        footer.setVisibility(View.VISIBLE);
//        header.setVisibility(View.VISIBLE);
      //<Desmond>
    }

    /**
     * @return true if the user has selected one of the exercises in the Exercise Picker
     * @author MAB
     */
    protected boolean isDefaultExercise() {
        return (mExerciseTypeStartIndex == EXERCISE_TYPE_START_NORMAL);
    }

    /**
     * @return true if the user has ONLY customized the exercise duration and nothin else
     * @author MAB
     */
    protected boolean isExerciseDurationCustomized() {
        return (mExerciseTypeStartIndex == EXERCISE_TYPE_START_CUSTOM && mExerciseTypeMiddleIndex != EXERCISE_TYPE_MIDDLE_ADVANCE);
    }

    /**
     * @return true if the user has customized all the 4 steps
     * @author MAB
     */
    protected boolean isEntireExerciseCustomized() {
        return (mExerciseTypeStartIndex == EXERCISE_TYPE_START_CUSTOM && mExerciseTypeMiddleIndex != -1);
    }
}
