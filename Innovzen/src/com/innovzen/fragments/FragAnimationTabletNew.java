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
//    private View header_buttons_container;
//    private View timer_footer_container;

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
                super.pauseExercise();
                break;
            case R.id.animation_help_btn:
                super.activityListener.fragGoToHelp(true);
                break;
             //<chy settings>
            case R.id.main_animation_setting:
            	super.activityListener.fragGoToSetting(true);
            	break;
            case R.id.main_animation_help:
            	super.activityListener.fragGoToHelpNew(true);
                break;
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

        view.findViewById(R.id.animation_fullscreen).setOnClickListener(this);
        view.findViewById(R.id.main_animation_help).setOnClickListener(this);
      //<chy> settins监听事件
        view.findViewById(R.id.animation_play_overlay_btn).setOnClickListener(this);
        view.findViewById(R.id.main_animation_setting).setOnClickListener(this);
      //</chy>  
        this.mView = view;

        // If simple, default exercise selected, then don't display any timers
        if (isDefaultExercise()) {
        	mIsFooterTimersInvisible = true;
        } else {
            // If only the exercise duration has been customized, then display it only
            if (isExerciseDurationCustomized()) {
            	mIsFooterTimersInvisible = false;
            }
        }
        
        if (!isDefaultExercise() && !isExerciseDurationCustomized() && !isEntireExerciseCustomized()) {
            //timer_footer_container.setVisibility(View.INVISIBLE);

            mIsFooterTimersInvisible = true;
        }
        super.countdown_tv.setPadding(Util.getScreenDimensions(getActivity())[0] / 3, 0, 0, 0);

    }

    @Override
    protected void initLefter(View view){
    	super.initLefter(view);
        leftTop.setBackgroundResource(R.drawable.selector_btn_back);
        leftMid.setBackgroundResource(R.drawable.banner_balance_min);
        leftBottom.setBackgroundResource(R.drawable.selector_btn_volume);
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

           super.disableFullscreen();

    };


    /**
     * When the overlay PLAY btn is pressed, it goes here
     * 
     * @author MAB
     */
    private void overlayPlayBtnPressed() {
        super.overlayBtnPressed();
    }

}
