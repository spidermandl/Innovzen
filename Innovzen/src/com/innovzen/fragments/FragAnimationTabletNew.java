package com.innovzen.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseIntArray;
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
import android.widget.Toast;

import com.innovzen.activities.ActivityMain;
import com.innovzen.bluetooth.BluetoothCommand;
import com.innovzen.fragments.base.FragAnimationBase;
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
	// power状态 为true时播放可用
	private boolean isPowerOn = false;
	// power关闭 按键是否有效状态
	private boolean closePower = false;
	// 是否处于待机状态
	private boolean wait = false;
	//蓝牙是否可以被关闭
	private boolean closeBluetooth=false;
	//动画是否重新倒计时
	//private boolean countDown=true;
	
	// Hold view references
	private View mView;


	/**
	 * Hold this state so we'll know when we come back from fullscreen to either
	 * set it to invisible or visibles
	 */
	private boolean mIsFooterTimersInvisible = true;
	// private View subtitle_container;
	private LinearLayout up, down, right, left_include;
	private RelativeLayout animation_play_overlay;

	private RelativeLayout anim_container;
	private TextView myMinutes;
	public static final int START_ANIMATION = 1;
	public static final int END_ANIMATION = 2;
	/**
	 * 半屏动画 布局 全屏动画 布局
	 */
	private RelativeLayout.LayoutParams inAnimLayoutParam, fullAnimLayoutParam;

	private ImageView backRestUp, backRestDown, footUp, footDown, zero, pause;

	/**
	 * 机器复位状态
	 */
	private static final int INVALID = 0;// 游离状态
	private static final int RESETING = 1;// 正在复位，第8字节第七位为0
	private static final int RESETED = 2;// 复位成功
	/**
	 * 当前机器复位状态
	 */
	private int resetStatus = INVALID;

	/**
	 * 复位检测线程
	 */
	private SingletonHandler resetHandler = new SingletonHandler();

	/**
	 * 对应于复位检测
	 */
	private Runnable resetRunnable = new Runnable() {
		@Override
		public void run() {
			BluetoothCommand mBC = BluetoothCommand.getInstance();
			if (mBC != null) {
				if (mBC.getValue(BluetoothCommand.INIT_POSITION_STATUS) == BluetoothCommand.INIT_POSITION_STATUS_INVALID) {
					// 复位状态为0
					resetStatus = RESETING;
					resetHandler.postDelayed(resetRunnable,
							BluetoothCommand.DELAY_TIME);
					Log.e("复位状态为0", System.currentTimeMillis() + "");
				} else if (mBC.getValue(BluetoothCommand.INIT_POSITION_STATUS) == BluetoothCommand.INIT_POSITION_STATUS_VALID) {
					// 复位状态为1
					resetStatus = RESETED;
					resetHandler.sendEmptyMessage(0);
					SparseIntArray map = new SparseIntArray();
					map.put(BluetoothCommand.INIT_POSITION_STATUS,
							mBC.getValue(BluetoothCommand.INIT_POSITION_STATUS));
					map.put(BluetoothCommand.DIRECTION_STATUS,
							mBC.getValue(BluetoothCommand.DIRECTION_STATUS));
					sendMachineMessage(BluetoothCommand.INIT_POSITION_STATUS,
							map);
					Log.e("复位状态为1", System.currentTimeMillis() + "");
				}
			}
		}
	};
	private TextView countdown_tv;

	/**
	 * 接受机器指令handler
	 */

	@Override
	protected void handlerMachineMessage(Message msg) {
		SparseIntArray map = (SparseIntArray) msg.obj;
		switch (msg.what) {
		case BluetoothCommand.INIT_POSITION_STATUS:
			// 这个地方要和BluetoothCommand里的一个常量对应
			// /////正常动画
			if ((!isAnimationRunning) && isReseted(false)) {
				Log.e("******************************startanimation",
						System.currentTimeMillis() + "");
				overlayBtnPressed();
			}
			if (map.get(BluetoothCommand.INIT_POSITION_STATUS) == BluetoothCommand.INIT_POSITION_STATUS_VALID) {
				closePower = true;
			} else {
				closePower = false;
			}
			
			break;
		case BluetoothCommand.PAUSE_STATUS:
			if (map.get(BluetoothCommand.PAUSE_STATUS) == BluetoothCommand.PAUSE_STATUS_OFF)// 这个地方的1要和BluetoothCommand里的一个常量对应
			{    
				pause.setBackgroundResource(R.drawable.selector_btn_pause);
			} else {
				endAnimationPressed();
				pause.setBackgroundResource(R.drawable.btn_exercise_pause_activated);
			}
			break;
		case BluetoothCommand.ZERO_STATUS:// 控制Zero按键状态
			if (map.get(BluetoothCommand.ZERO_STATUS) == BluetoothCommand.ZERO_STATUS_CLOSE) {
				int valuesss = map.get(BluetoothCommand.ZERO_STATUS);
				System.out.println(valuesss);
				zero.setBackgroundResource(R.drawable.selector_btn_gravity);
			} else {
				zero.setBackgroundResource(R.drawable.btn_gravity_activated);
			}
			break;

		case BluetoothCommand.WALKING_POSITION_STATUS:
			// 行位控制信号
			break;
		case BluetoothCommand.MACHINE_RUN_STATUS:
			if (map.get(BluetoothCommand.MACHINE_RUN_STATUS) == BluetoothCommand.MACHINE_RUN_STATUS_WAIT) {
				wait = true;
				//countDown=true;
			} 
			if(map.get(BluetoothCommand.MACHINE_RUN_STATUS)==BluetoothCommand.MACHINE_RUN_STATUS_RUNNING){
				wait=false;
			}
			if(map.get(BluetoothCommand.MACHINE_RUN_STATUS)==BluetoothCommand.MACHINE_RUN_STATUS_PAUSE){
				
			}
		
		default:
			break;
		}

	};

	/**
	 * 判断机器是否复位
	 * isLog 是否显示提示
	 * @return
	 */
	public boolean isReseted(boolean isLog) {
		if (resetStatus == INVALID) {
			if (!resetHandler.isWaiting()) {
				resetHandler.postDelayed(resetRunnable, 200);
				resetHandler.setWaiting(true);
			}
			if(isLog)
				Toast.makeText(getActivity(), "The machine has not started,please press the rebooting botton", 1000).show();
			return false;
		} else if (resetStatus == RESETING){
			if(isLog)
				Toast.makeText(getActivity(), "The machine is reseting now", 1000).show();
			return false;
		}
		else if (resetStatus == RESETED)
			
			return true;

		return false;
	}

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
		/*	if(closeBluetooth==true){  //如果蓝牙boolean变量被设置为true，那么再按一下按钮就关闭蓝牙
				super.activityListener.fragCloseBluetooth();
				closeBluetooth=false;
			}else{*/
			/*	if(isPowerOn==false&&wait==true){//如果机器处于待机状态按一下开机
					isPowerOn=true;
					super.activityListener.fragSendCommand(BluetoothCommand.START_MACHINE_VALUES);
				}
				if(closePower==true&&isPowerOn==true&&isAnimationRunning){//如果机器已经初始化完毕，并且正在运动 并且动画正在运行 则按一下按钮就关闭机器
					super.pauseExercise();
					super.activityListener.fragSendCommand(BluetoothCommand.START_MACHINE_VALUES);
					isPowerOn=false;
					//closeBluetooth=true;//把蓝牙设置成可关闭
				}

				//当关机后设置倒计时可显示  下次再播放动画就可以有倒计时
				if(countDown==true){
					countdown_tv.setVisibility(View.VISIBLE);
				}*/
			super.pauseExercise();
			super.activityListener.fragSendCommand(BluetoothCommand.START_MACHINE_VALUES);
			//}		
				
			break;
		// 开始
		case R.id.main_animation_start:
			if(isReseted(true)){//只有机器复位才能播放动画
				String blance_relax_performance = 
						MyPreference.getInstance(getActivity()).readString(MyPreference.BLANCE_RELAX_PERFORMANCE);
				if (blance_relax_performance.equals(MyPreference.BLANCE)) {
					super.activityListener
							.fragSendCommand(BluetoothCommand.BLANCE_MACHINE_VALUES);
				} else if (blance_relax_performance.equals(MyPreference.RELAX)) {
					super.activityListener
							.fragSendCommand(BluetoothCommand.RELAX_MACHINE_VALUES);
				} else if (blance_relax_performance.equals(MyPreference.PERFORMANCE)) {
					super.activityListener
							.fragSendCommand(BluetoothCommand.PERFORMANCE_MACHINE_VALUES);
				}
				 
			}
			break;
		// 暂停
		case R.id.main_animation_pause:
			super.activityListener
					.fragSendCommand(BluetoothCommand.PAUSE_MACHINE_VALUES);

			super.pauseExercise();

			//如果机器处于暂停状态 countdown_tv就设置为隐藏   知道机器关机为止设置为可见
			/*if(countDown==false){
				countdown_tv.setVisibility(View.INVISIBLE);
			}else{
				countdown_tv.setVisibility(View.VISIBLE);
			}*/
			/*
			 * }else{
			 * 
			 * }
			 */
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
		case R.id.left_top:
			super.pauseExercise();
			super.activityListener.fragSendCommand(BluetoothCommand.START_MACHINE_VALUES);						
			getActivity().onBackPressed();
			break;
		}
	}

	/**
	 * Does proper initializations
	 * 
	 * @author MAB
	 */
	private void initialize(View view) {
	
		countdown_tv = (TextView) view.findViewById(R.id.animation_countdown);
		
		closeBluetooth = ((ActivityMain)this.getActivity()).isBlueToothConnected();
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
		pause = (ImageView) view.findViewById(R.id.main_animation_pause);
		pause.setOnClickListener(this);
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
		// 像机器发送时间命令
		if (myMinutes.equals(MyPreference.FIVE_MINUTES)) {
			super.activityListener
					.fragSendCommand(BluetoothCommand.TIME5_MACHINE_VALUES);
		} else if (myMinutes.equals(MyPreference.TEN_MINUTES)) {
			super.activityListener
					.fragSendCommand(BluetoothCommand.TIME10_MACHINE_VALUES);
		} else if (myMinutes.equals(MyPreference.FIFTEEN_MINUTES)) {
			super.activityListener
					.fragSendCommand(BluetoothCommand.TIME15_MACHINE_VALUES);
		} else if (myMinutes.equals(MyPreference.TWENTY_MINUTES)) {
			super.activityListener
					.fragSendCommand(BluetoothCommand.TIME20_MACHINE_VALUES);
		} else if (myMinutes.equals(MyPreference.TWENTY_FIVE_MINUTES)) {
			super.activityListener
					.fragSendCommand(BluetoothCommand.TIME25_MACHINE_VALUES);
		} else if (myMinutes.equals(MyPreference.THIRTY_MINUTES)) {
			super.activityListener
					.fragSendCommand(BluetoothCommand.TIME30_MACHINE_VALUES);
		}

		leftTop.setBackgroundResource(R.drawable.selector_btn_back);
		view.findViewById(R.id.left_top).setOnClickListener(this);
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

	// 停止动画
	private void endAnimationPressed() {
		super.pauseExercise();
	}

	@Override
	public void sendMachineMessage(int command, SparseIntArray bundle) {
		Message msg = new Message();
		msg.what = command;
		msg.obj = bundle;
		handlerMachineMessage(msg);
	}
	/**
	 * 单例线程
	 * 
	 * @author Desmond Duan
	 * 
	 */
	class SingletonHandler extends Handler {
		private boolean isWaiting = false;// 判断线程是否在执行

		@Override
		public void handleMessage(Message msg) {
			isWaiting = false;
		}

		public boolean isWaiting() {
			return isWaiting;
		}

		public void setWaiting(boolean isWaiting) {
			this.isWaiting = isWaiting;
		}
	}

}
