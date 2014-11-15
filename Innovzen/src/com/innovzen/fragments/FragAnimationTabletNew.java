package com.innovzen.fragments;

import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.RelativeLayout;

import com.innovzen.animations.AnimationGradient;
import com.innovzen.bluetooth.BluetoothCommand;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.handlers.ExerciseManager;
import com.innovzen.o2chair.R;
import com.innovzen.utils.MyPreference;
import com.innovzen.utils.Util;

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
	// private View subtitle_container;
	private LinearLayout up,down,right,left_include;
	private RelativeLayout animation_play_overlay;

	private RelativeLayout anim_container;
	private TextView myMinutes;
    public static final int START_ANIMATION=1;
    public static final int END_ANIMATION=2;
	/**
	 * 半屏动画 布局 全屏动画 布局
	 */
	private RelativeLayout.LayoutParams inAnimLayoutParam, fullAnimLayoutParam;

	private ImageView backRestUp,backRestDown,footUp,footDown,zero;

	Handler machineHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			HashMap<Integer, Integer> map = (HashMap<Integer, Integer>)msg.obj;
			switch (msg.what) {
			case BluetoothCommand.INIT_POSITION_STATUS:
				//这个地方要和BluetoothCommand里的一个常量对应 
				if(!isAnimationRunning&&
						map.get(BluetoothCommand.INIT_POSITION_STATUS)!=null&&
						map.get(BluetoothCommand.INIT_POSITION_STATUS)==BluetoothCommand.INIT_POSITION_STATUS_VALID&&
						map.get(BluetoothCommand.DIRECTION_STATUS)!=null&&
						(map.get(BluetoothCommand.DIRECTION_STATUS)==BluetoothCommand.DIRECTION_STATUS_UP||map.get(BluetoothCommand.DIRECTION_STATUS)==BluetoothCommand.DIRECTION_STATUS_DOWN))
					overlayBtnPressed();
				break;
			case BluetoothCommand.PAUSE_STATUS:
				if(map.get(BluetoothCommand.PAUSE_STATUS)!=null&&map.get(BluetoothCommand.PAUSE_STATUS)==1)//这个地方的1要和BluetoothCommand里的一个常量对应
					endAnimationPressed();
				break;
			case BluetoothCommand.ZERO_STATUS://控制Zero按键状态
				if(map.get(BluetoothCommand.ZERO_STATUS)!=null&&map.get(BluetoothCommand.ZERO_STATUS)==BluetoothCommand.ZERO_STATUS_CLOSE){
					int valuesss = map.get(BluetoothCommand.ZERO_STATUS);
					System.out.println(valuesss);
					zero.setBackgroundResource(R.drawable.selector_btn_gravity);
				}else{
					zero.setBackgroundResource(R.drawable.btn_gravity_activated);
				}
				break;
			case BluetoothCommand.WALKING_POSITION_STATUS://动画行为
				if(map.get(BluetoothCommand.WALKING_POSITION_STATUS)!=null&&
				   map.get(BluetoothCommand.DIRECTION_STATUS)!=null){
					
					int direction=map.get(BluetoothCommand.DIRECTION_STATUS);
					int walkingStatus=0;
					switch (direction) {
					case BluetoothCommand.DIRECTION_STATUS_DOWN:
						walkingStatus=1-map.get(BluetoothCommand.WALKING_POSITION_STATUS)/12;
						walkingStatus=walkingStatus<0?0:walkingStatus;
						walkingStatus=walkingStatus>1?1:walkingStatus;
						mExerciseManager.start(walkingStatus);
						break;
					case BluetoothCommand.DIRECTION_STATUS_RETAIN:

						break;
					case BluetoothCommand.DIRECTION_STATUS_STOP:

						break;
					case BluetoothCommand.DIRECTION_STATUS_UP:
						walkingStatus=map.get(BluetoothCommand.WALKING_POSITION_STATUS)/12;
						walkingStatus=walkingStatus<0?0:walkingStatus;
						walkingStatus=walkingStatus>1?1:walkingStatus;
						mExerciseManager.start(walkingStatus);
						break;
					default:
						break;
					}

				}
				break;
//			//msg.what 返回1播放动画  返回2停止动画
//			case START_ANIMATION:
//				overlayBtnPressed();
//				break;
//			case END_ANIMATION:
//				endAnimationPressed();
//				break;
			default:
				break;
			}
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_animation_new,
				container, false);

		super.onView(view);
		initialize(view);
		initLefter(view);

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
			super.activityListener
					.fragSendCommand(BluetoothCommand.START_MACHINE_VALUES);
		//	super.pauseExercise();
			break;
		// 开始
		case R.id.main_animation_start:
			super.activityListener
					.fragSendCommand(BluetoothCommand.BLANCE_MACHINE_VALUES);

			// overlayPlayBtnPressed();

			break;
		// 暂停
		case R.id.main_animation_pause:
			super.activityListener
					.fragSendCommand(BluetoothCommand.PAUSE_MACHINE_VALUES);
			super.pauseExercise();

			break;
		case R.id.main_animation_breathe_up:
			super.activityListener
					.fragSendCommand(BluetoothCommand.BREATHE_UP_MACHINE_VALUES);
			break;
		case R.id.main_animation_breathe_down:
			super.activityListener
					.fragSendCommand(BluetoothCommand.BREATHE_DOWN_MACHINE_VALUES);
			break;
		case R.id.main_animation_zero:
			super.activityListener
					.fragSendCommand(BluetoothCommand.ZERO_GRAVITY_MACHINE_VALUES);
			break;
		}
	}

	/**
	 * Does proper initializations
	 * 
	 * @author MAB
	 */
	private void initialize(View view) {
		view.findViewById(R.id.main_animation_breathe_up).setOnClickListener(
				this);
		view.findViewById(R.id.main_animation_breathe_down).setOnClickListener(
				this);
		zero = (ImageView) view.findViewById(R.id.main_animation_zero);
		zero.setOnClickListener(this);
		backRestUp = (ImageView) view
				.findViewById(R.id.main_animation_backrest_up);
		backRestDown = (ImageView) view
				.findViewById(R.id.main_animation_backrest_down);
		footUp = (ImageView) view.findViewById(R.id.main_animation_foot_up);
		footDown = (ImageView) view.findViewById(R.id.main_animation_foot_down);
		myMinutes = (TextView) view.findViewById(R.id.myMinutes);
		animation_play_overlay = (RelativeLayout) view
				.findViewById(R.id.animation_play_overlay);
		// subtitle_container = view.findViewById(R.id.animation_type);
		left_include = (LinearLayout) view.findViewById(R.id.left_include);
		down = (LinearLayout) view.findViewById(R.id.main_mid_down_frame);
		right = (LinearLayout) view.findViewById(R.id.main_right_frame);
		up = (LinearLayout) view.findViewById(R.id.main_mid_up_frame);
		anim_container = (RelativeLayout) view
				.findViewById(R.id.main_animation_container);
		inAnimLayoutParam = (RelativeLayout.LayoutParams) anim_container
				.getLayoutParams();
		fullAnimLayoutParam = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

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
		backRestUp.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					backRestUp
							.setBackgroundResource(R.drawable.btn_backrest_adjust_up_activated);
					FragAnimationTabletNew.super.activityListener
							.fragSendCommand(BluetoothCommand.BACK_REST_UP_MACHINE_VALUES);
					System.out.println("back up");
					break;
				case MotionEvent.ACTION_UP:
					backRestUp
							.setBackgroundResource(R.drawable.btn_backrest_adjust_up);
					FragAnimationTabletNew.super.activityListener
							.fragSendCommand(BluetoothCommand.BACK_REST_UP_STOP_MACHINE_VALUES);
					System.out.println("back up stop");
					break;
				default:
					break;
				}
				return true;
			}
		});
		backRestDown.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					backRestDown
							.setBackgroundResource(R.drawable.btn_backrest_adjust_down_activated);
					FragAnimationTabletNew.super.activityListener
							.fragSendCommand(BluetoothCommand.BACK_REST_DOWN_MACHINE_VALUES);
					System.out.println("back down");
					break;
				case MotionEvent.ACTION_UP:
					backRestDown
							.setBackgroundResource(R.drawable.btn_backrest_adjust_down);
					FragAnimationTabletNew.super.activityListener
							.fragSendCommand(BluetoothCommand.BACK_REST_DOWN_STOP_MACHINE_VALUES);
					System.out.println("back down stop");
					break;
				default:
					break;
				}
				return true;
			}
		});
		footUp.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					footUp.setBackgroundResource(R.drawable.btn_foot_adjust_up_activated);
					FragAnimationTabletNew.super.activityListener
							.fragSendCommand(BluetoothCommand.FOOT_UP_MACHINE_VALUES);
					System.out.println("foot up");
					break;
				case MotionEvent.ACTION_UP:
					footUp.setBackgroundResource(R.drawable.btn_foot_adjust_up);
					FragAnimationTabletNew.super.activityListener
							.fragSendCommand(BluetoothCommand.FOOT_UP_STOP_MACHINE_VALUES);
					System.out.println("foot up stop");
					break;
				default:
					break;
				}
				return true;
			}
		});
		footDown.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					footDown.setBackgroundResource(R.drawable.btn_foot_adjust_down_activated);
					FragAnimationTabletNew.super.activityListener
							.fragSendCommand(BluetoothCommand.FOOT_DOWN_MACHINE_VALUES);
					System.out.println("foot down");
					break;
				case MotionEvent.ACTION_UP:
					footDown.setBackgroundResource(R.drawable.btn_foot_adjust_down);
					FragAnimationTabletNew.super.activityListener
							.fragSendCommand(BluetoothCommand.FOOT_DOWN_STOP_MACHINE_VALUES);
					System.out.println("foot down stop");
					break;
				default:
					break;
				}
				return true;
			}
		});
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
		myMinutes.setText(MyPreference.getInstance(this.getActivity())
				.readString(MyPreference.TIME));

		leftTop.setBackgroundResource(R.drawable.selector_btn_back);
		// 根据不同的按钮点击进入动画界面，leftmid显示不同的背景
		String midBackground = MyPreference.getInstance(this.getActivity())
				.readString(MyPreference.BLANCE_RELAX_PERFORMANCE);
		if (midBackground.equals(MyPreference.BLANCE)) {
			leftMid.setBackgroundResource(R.drawable.banner_balance);
		} else if (midBackground.equals(MyPreference.RELAX)) {
			leftMid.setBackgroundResource(R.drawable.banner_relax);
		} else if (midBackground.equals(MyPreference.PERFORMANCE)) {
			leftMid.setBackgroundResource(R.drawable.banner_performance);
		}
		leftBottom.setBackgroundResource(R.drawable.selector_btn_volume);
	}

	private void toggleFullscreen() {
		if (up.getVisibility() == View.VISIBLE) {

			// subtitle_container.setVisibility(View.GONE);
			up.setVisibility(View.GONE);
			down.setVisibility(View.GONE);
			right.setVisibility(View.GONE);
			left_include.setVisibility(View.GONE);

			anim_container.setLayoutParams(fullAnimLayoutParam);

			super.enableFullscreen();

		} else {

			// subtitle_container.setVisibility(View.VISIBLE);
			up.setVisibility(View.VISIBLE);
			down.setVisibility(View.VISIBLE);
			right.setVisibility(View.VISIBLE);
			left_include.setVisibility(View.VISIBLE);

			anim_container.setLayoutParams(inAnimLayoutParam);

			super.disableFullscreen();
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
	//停止动画
	private void endAnimationPressed() {
		super.pauseExercise();
	}
	/**
	 * 发送命令
	 * @param command
	 */
	public void sendMachineMessage(int command,HashMap<Integer, Integer> bundle){
		Message msg=new Message();
		msg.what=command;
		msg.obj=bundle;
		machineHandler.sendMessage(msg);
	}


}
