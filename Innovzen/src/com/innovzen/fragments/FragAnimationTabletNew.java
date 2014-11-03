package com.innovzen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.innovzen.entities.ExerciseTimes;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.handlers.CircularSeekBarHandler;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.o2chair.R;
import com.innovzen.utils.PersistentUtil;
import com.innovzen.utils.Util;
/**
 * 新版呼气吸气动画主界面
 * @author Desmond Duan
 *
 */
public class FragAnimationTabletNew extends FragAnimationBase implements OnClickListener {

    // Hold view references
    private View mView;
    private View header_buttons_container;
    private View timer_footer_container;

    /** Handler for the footer timer - inhale */
    private CircularSeekBarHandler mFooterTimerInhaleHandler;

    /** Handler for the footer timer - hold inhale */
    private CircularSeekBarHandler mFooterTimerHoldInhaleHandler;

    /** Handler for the footer timer - exhale */
    private CircularSeekBarHandler mFooterTimerExhaleHandler;

    /** Handler for the footer timer - hold exhale */
    private CircularSeekBarHandler mFooterTimerHoldExhaleHandler;

    /** Handler for the footer timer - exercise duration */
    private CircularSeekBarHandler mFooterTimerExerciseDurationHandler;

    /** Hold this state so we'll know when we come back from fullscreen to either set it to invisible or visibles */
    private boolean mIsFooterTimersInvisible = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animation_new, container, false);

        super.onView(view);

        initialize(view);
        initLefter(view);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        mFooterTimerInhaleHandler.onResume();
        mFooterTimerHoldInhaleHandler.onResume();
        mFooterTimerExhaleHandler.onResume();
        mFooterTimerHoldExhaleHandler.onResume();
        mFooterTimerExerciseDurationHandler.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.animation_pause_btn:

                toggleTimerThumbs(true);

                super.pauseExercise();
                break;
            case R.id.animation_help_btn:
                super.activityListener.fragGoToHelp(true);
                break;
             //<chy settings>
            case R.id.main_animation_setting:
            	super.activityListener.fragGoToSetting(true);
              //</chy>  
            case R.id.animation_open_drawer_btn:
                break;
            case R.id.animation_fullscreen:
                toggleFullscreen();
                break;
            case R.id.animation_play_overlay_btn:
                overlayPlayBtnPressed();
                break;
                
        }

    }

    /**
     * Does proper initializations
     * 
     * @author MAB
     */
    private void initialize(View view) {

        // Get references
    	//<Desmond>
        //super.footer = (RelativeLayout) view.findViewById(R.id.animation_footer);
      //<Desmond>
        header_buttons_container = view.findViewById(R.id.animation_header_buttons_container);
        timer_footer_container = view.findViewById(R.id.animation_footer_container);

        // Set click listeners
      //<Desmond>
//        view.findViewById(R.id.animation_pause_btn).setOnClickListener(this);
//        view.findViewById(R.id.animation_help_btn).setOnClickListener(this);
//        view.findViewById(R.id.animation_open_drawer_btn).setOnClickListener(this);
      //<Desmond>
        view.findViewById(R.id.animation_fullscreen).setOnClickListener(this);
        view.findViewById(R.id.animation_play_overlay_btn).setOnClickListener(this);
      //<chy> settins监听事件
        view.findViewById(R.id.main_animation_setting).setOnClickListener(this);
        ImageView left_mid =(ImageView) view.findViewById(R.id.left_mid);
        ImageView left_bottom = (ImageView) view.findViewById(R.id.left_bottom);
        ImageView left_top = (ImageView) view.findViewById(R.id.left_top);
        left_top.setBackgroundResource(R.drawable.selector_btn_back);
        left_mid.setBackgroundResource(R.drawable.banner_balance_min);
        left_bottom.setBackgroundResource(R.drawable.selector_btn_volume);
      //</chy>  
        this.mView = view;

        /*
         * Init the footer timers
         */
        // Calculate the timers dimension
        int timerDim = (int) (getResources().getDimensionPixelSize(R.dimen.reusable_footer_height));
        // Inhale
        mFooterTimerInhaleHandler = new CircularSeekBarHandler(mView.findViewById(R.id.animation_footer_timer_inhale), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_SINGLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, false);
        // Hold inhale
        mFooterTimerHoldInhaleHandler = new CircularSeekBarHandler(mView.findViewById(R.id.animation_footer_timer_hold_inhale), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_SINGLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, false);
        // Exhale
        mFooterTimerExhaleHandler = new CircularSeekBarHandler(mView.findViewById(R.id.animation_footer_timer_exhale), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_SINGLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, false);
        // Hold exhale
        mFooterTimerHoldExhaleHandler = new CircularSeekBarHandler(mView.findViewById(R.id.animation_footer_timer_hold_exhale), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_SINGLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, false);
        // Exercise duration
        mFooterTimerExerciseDurationHandler = new CircularSeekBarHandler(mView.findViewById(R.id.animation_footer_timer_exercise_duration), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_DOUBLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, true);
        // Set the times
        setTimesOnFooterTimers(mTimes);

        // If simple, default exercise selected, then don't display any timers
        if (isDefaultExercise()) {

            timer_footer_container.setVisibility(View.INVISIBLE);

            mIsFooterTimersInvisible = true;

        } else {

            // If only the exercise duration has been customized, then display it only
            if (isExerciseDurationCustomized()) {

                mView.findViewById(R.id.animation_footer_timer_inhale_container).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.animation_footer_timer_hold_inhale_container).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.animation_footer_timer_exhale_container).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.animation_footer_timer_hold_exhale_container).setVisibility(View.INVISIBLE);

                mIsFooterTimersInvisible = false;
            }

        }

        if (!isDefaultExercise() && !isExerciseDurationCustomized() && !isEntireExerciseCustomized()) {
            timer_footer_container.setVisibility(View.INVISIBLE);

            mIsFooterTimersInvisible = true;
        }

        /*
         * Init the footer
         */
      //<Desmond>
//        super.mFooterHandler = new FooterHandler(super.activityListener, super.footer, FooterHandler.HOME, -1, -1);
//        super.mFooterHandler.setRightIconOnClickListener(super.mClickListener);
      //<Desmond>

        // Just for the tablet, set the countdown value a bit to the right
        super.countdown_tv.setPadding(Util.getScreenDimensions(getActivity())[0] / 3, 0, 0, 0);

    }

    private void toggleFullscreen() {
        // Check if it's in fullscreen mode by looking at the visibility of the footer
    	//<Desmond>
//        if (super.footer.getVisibility() == View.VISIBLE) { // currently NOT in fullscreen mode
//
//            /* Make stuff disappear */
//
//            header_buttons_container.setVisibility(View.GONE);
//
//            animation_type.setVisibility(View.INVISIBLE);
//
//            timer_footer_container.setVisibility(View.GONE);
//
//            super.enableFullscreen();
//
//        } else { // currently in fullscreen mode
    	//<Desmond>

            /* Make stuff appear */

            animation_type.setVisibility(View.VISIBLE);

            header_buttons_container.setVisibility(View.VISIBLE);

            timer_footer_container.setVisibility(mIsFooterTimersInvisible ? View.INVISIBLE : View.VISIBLE);

            super.disableFullscreen();

        //}
    };

    private void toggleTimerThumbs(boolean show) {
        if (mFooterTimerInhaleHandler != null) {
            mFooterTimerInhaleHandler.showThumb(show);
        }
        if (mFooterTimerHoldInhaleHandler != null) {
            mFooterTimerHoldInhaleHandler.showThumb(show);
        }
        if (mFooterTimerExhaleHandler != null) {
            mFooterTimerExhaleHandler.showThumb(show);
        }
        if (mFooterTimerHoldExhaleHandler != null) {
            mFooterTimerHoldExhaleHandler.showThumb(show);
        }
        if (mFooterTimerExerciseDurationHandler != null) {
            mFooterTimerExerciseDurationHandler.showThumb(show);
        }
    }

    /**
     * When the overlay PLAY btn is pressed, it goes here
     * 
     * @author MAB
     */
    private void overlayPlayBtnPressed() {

        // Retrieve the values from the timers
        boolean ok = false;
        if (super.isEntireExerciseCustomized()) {
            super.mTimes.inhale = mFooterTimerInhaleHandler.getProgressMilliseconds();
            super.mTimes.holdInhale = mFooterTimerHoldInhaleHandler.getProgressMilliseconds();
            super.mTimes.exhale = mFooterTimerExhaleHandler.getProgressMilliseconds();
            super.mTimes.holdExhale = mFooterTimerHoldExhaleHandler.getProgressMilliseconds();
            super.mTimes.exerciseDuration = mFooterTimerExerciseDurationHandler.getProgressMilliseconds();
            ok = true;
        } else if (super.isExerciseDurationCustomized()) {
            super.mTimes.exerciseDuration = mFooterTimerExerciseDurationHandler.getProgressMilliseconds();
            ok = true;
        }

        // If the values have changed, then save them AND set them on the actual timers
        if (ok) {
            // Store the step times
            FragTimerAdvance.storeExerciseTimesInPreferences(getActivity(), super.mTimes);
            // Store the entire exercise duration time
            PersistentUtil.setInt(getActivity(), super.mTimes.inhale, FragAnimationBase.PERSIST_TIME_INHALE);
            PersistentUtil.setInt(getActivity(), super.mTimes.holdInhale, FragAnimationBase.PERSIST_TIME_HOLD_INHALE);
            PersistentUtil.setInt(getActivity(), super.mTimes.exhale, FragAnimationBase.PERSIST_TIME_EXHALE);
            PersistentUtil.setInt(getActivity(), super.mTimes.holdExhale, FragAnimationBase.PERSIST_TIME_HOLD_EXHALE);
            PersistentUtil.setInt(getActivity(), super.mTimes.exerciseDuration, FragAnimationBase.PERSIST_TOTAL_SELECTED_EXERCISE_DURATION);

            // Set the times on the
            setTimesOnFooterTimers(super.mTimes);

            // Send the new times to the animation
            super.mExerciseManager.setNewTimesForAnimation(super.mTimes);

        }

        // Show the thumbs
        toggleTimerThumbs(false);

        // Do the appropriate things on overlay click
        super.overlayBtnPressed();
    }

    private void setTimesOnFooterTimers(ExerciseTimes times) {
        // Inhale
        mFooterTimerInhaleHandler.setProgressMillis(FragTimerAdvance.MIN_INHALE_AND_EXHALE_TIMES, FragTimerAdvance.MAX_TIMER_TIME, times.inhale);
        // Hold inhale
        mFooterTimerHoldInhaleHandler.setProgressMillis(FragTimerAdvance.MIN_HOLD_TIMES, FragTimerAdvance.MAX_TIMER_TIME, times.holdInhale);
        // Exhale
        mFooterTimerExhaleHandler.setProgressMillis(FragTimerAdvance.MIN_INHALE_AND_EXHALE_TIMES, FragTimerAdvance.MAX_TIMER_TIME, times.exhale);
        // Hold exhale
        mFooterTimerHoldExhaleHandler.setProgressMillis(FragTimerAdvance.MIN_HOLD_TIMES, FragTimerAdvance.MAX_TIMER_TIME, times.holdExhale);
        // Exercise duration
        mFooterTimerExerciseDurationHandler.setProgressMillis(FragTimer.DEFAULT_TIMER_MIN, FragTimer.DEFAULT_TIMER_MAX, times.exerciseDuration);
    }
}
