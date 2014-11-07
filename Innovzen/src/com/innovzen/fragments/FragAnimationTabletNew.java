package com.innovzen.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import android.widget.RelativeLayout;

import com.innovzen.entities.ExerciseTimes;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.handlers.CircularSeekBarHandler;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.o2chair.R;
import com.innovzen.utils.PersistentUtil;
import com.innovzen.utils.Util;
import com.jfeinstein.jazzyviewpager.MainActivity;

/**
 * 新版呼气吸气动画主界面
 * 
 * @author Desmond Duan
 * 
 */
public class FragAnimationTabletNew extends FragAnimationBase implements
		OnClickListener {

	// Hold view references
	private View mView;

	/**
	 * Hold this state so we'll know when we come back from fullscreen to either
	 * set it to invisible or visibles
	 */
	private boolean mIsFooterTimersInvisible = true;

	private View subtitle_container;

	private LinearLayout up;

	private LinearLayout down;

	private LinearLayout right, left_include;

	private LinearLayout animation_center_1, animation_center_2;

	private RelativeLayout animation_play_overlay;

	private RelativeLayout rl;

	private TextView myMinutes;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_animation_new,
				container, false);

		super.onView(view);

		initialize(view);
		initLefter(view);
		/*rl = new RelativeLayout(getActivity());
		RelativeLayout.LayoutParams lp1 =  new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		
		animation_center_1.addView(animation_center_1,lp1);
		getActivity().setContentView(rl);*/
		return view;

	}

	@Override
	public void onResume() {
		super.onResume();
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
		// <chy settings>
		case R.id.main_animation_setting:
			super.activityListener.fragGoToSetting(true);
			break;
		case R.id.main_animation_help:
			super.activityListener.fragGoToHelpNew(true);
			break;
		// </chy>
		case R.id.animation_open_drawer_btn:
			break;
		// 全屏
		case R.id.animation_fullscreen:
			toggleFullscreen();
			break;
		case R.id.animation_play_overlay_btn:
			overlayPlayBtnPressed();
			break;
		// 结束
		case R.id.main_animation_stop:
			super.pauseExercise();
			super.activityListener.GoToEnd();
			break;
		// 开始
		case R.id.main_animation_start:
			overlayPlayBtnPressed();
			super.activityListener.GoToBegin();
			
		
			//

			break;
		// 暂停
		case R.id.main_animation_pause:
			super.pauseExercise();
			super.activityListener.GoToPause();
			break;

		}

	}

	/**
	 * Does proper initializations
	 * 
	 * @author MAB
	 */
	private void initialize(View view) {
            myMinutes = (TextView) view.findViewById(R.id.myMinutes);
		animation_play_overlay = (RelativeLayout) view.findViewById(R.id.animation_play_overlay);
		animation_center_1 = (LinearLayout) view.findViewById(R.id.animation_center_1);
		/*animation_center_2 = (LinearLayout) view.findViewById(R.id.animation_center_2);*/
		subtitle_container = view.findViewById(R.id.animation_type);
		left_include = (LinearLayout) view.findViewById(R.id.left_include);
		down = (LinearLayout) view.findViewById(R.id.main_mid_down_frame);
		right = (LinearLayout) view.findViewById(R.id.main_right_frame);
		up = (LinearLayout) view.findViewById(R.id.main_mid_up_frame);
		view.findViewById(R.id.animation_fullscreen).setOnClickListener(this);
		view.findViewById(R.id.main_animation_help).setOnClickListener(this);
		// <chy> settins监听事件
		view.findViewById(R.id.animation_fullscreen).setOnClickListener(this);
		view.findViewById(R.id.main_animation_pause).setOnClickListener(this);
		view.findViewById(R.id.main_animation_start).setOnClickListener(this);
		view.findViewById(R.id.main_animation_stop).setOnClickListener(this);
		view.findViewById(R.id.animation_play_overlay_btn).setOnClickListener(
				this);
		view.findViewById(R.id.main_animation_setting).setOnClickListener(this);

		// </chy>
		this.mView = view;

		// If simple, default exercise selected, then don't display any timers
		if (isDefaultExercise()) {
			mIsFooterTimersInvisible = true;
		} else {
			// If only the exercise duration has been customized, then display
			// it only
			if (isExerciseDurationCustomized()) {
				mIsFooterTimersInvisible = false;
			}
		}

		if (!isDefaultExercise() && !isExerciseDurationCustomized()
				&& !isEntireExerciseCustomized()) {
			// timer_footer_container.setVisibility(View.INVISIBLE);

			mIsFooterTimersInvisible = true;
		}
		super.countdown_tv.setPadding(
				Util.getScreenDimensions(getActivity())[0] / 3, 0, 0, 0);

	}

	@Override
	protected void initLefter(View view) {
		super.initLefter(view);
		SharedPreferences	sp = getActivity().getSharedPreferences(super.SAVE_TIME_MIN, Context.MODE_PRIVATE);
		String temp = sp.getString("time", "");
		if(temp.equals("")||temp==null){
			myMinutes.setText("5min");
		}else{
		
		getMyShareSharedPreferences("time");
		}
		leftTop.setBackgroundResource(R.drawable.selector_btn_back);
		leftMid.setBackgroundResource(R.drawable.banner_balance);
		leftBottom.setBackgroundResource(R.drawable.selector_btn_volume);
	}

	/* private void toggleFullscreen() { */
	// Check if it's in fullscreen mode by looking at the visibility of the
	// footer
	// <Desmond>
	// if (super.footer.getVisibility() == View.VISIBLE) { // currently NOT in
	// fullscreen mode
	//
	// /* Make stuff disappear */
	//
	// header_buttons_container.setVisibility(View.GONE);
	//
	// animation_type.setVisibility(View.INVISIBLE);
	//
	// timer_footer_container.setVisibility(View.GONE);
	//
	// super.enableFullscreen();
	//
	// } else { // currently in fullscreen mode
	// <Desmond>

	/* Make stuff appear */

	/*
	 * animation_type.setVisibility(View.VISIBLE);
	 * 
	 * super.disableFullscreen();
	 * 
	 * };
	 */
	private void toggleFullscreen() {
		if (up.getVisibility() == View.VISIBLE) {

			subtitle_container.setVisibility(View.GONE);

			up.setVisibility(View.GONE);
			down.setVisibility(View.GONE);
			right.setVisibility(View.GONE);
			left_include.setVisibility(View.GONE);
			
			
			/*animation_center_1.setVisibility(View.GONE);*/
		/*	animation_center_2.setVisibility(View.VISIBLE);*/
			super.enableFullscreen();
			// animation_center_1.l

		} else {
			super.disableFullscreen();
			subtitle_container.setVisibility(View.VISIBLE);
			up.setVisibility(View.VISIBLE);
			down.setVisibility(View.VISIBLE);
			right.setVisibility(View.VISIBLE);
			left_include.setVisibility(View.VISIBLE);
			/*
			 * animation_center_2.setVisibility(View.GONE);
			 * animation_center_1.setVisibility(View.VISIBLE);
			 */

		}
	}

	/**
	 * When the overlay PLAY btn is pressed, it goes here
	 * 
	 * @author MAB
	 */
	private void overlayPlayBtnPressed() {
		super.overlayBtnPressed();
	}

}
