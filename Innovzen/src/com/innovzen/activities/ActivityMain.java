package com.innovzen.activities;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.innovzen.o2chair.R;
import com.innovzen.bluetooth.BluetoothService;
import com.innovzen.entities.SoundGroup;
import com.innovzen.fragments.FragAnimationPhone;
import com.innovzen.fragments.FragAnimationPicker;
import com.innovzen.fragments.FragAnimationTabletNew;
import com.innovzen.fragments.FragBalance;
import com.innovzen.fragments.FragBluetoothDialog;
import com.innovzen.fragments.FragChairInfo;
import com.innovzen.fragments.FragExercisePicker;
import com.innovzen.fragments.FragGraphic;
import com.innovzen.fragments.FragHelp;
import com.innovzen.fragments.FragHelpNew;
import com.innovzen.fragments.FragHistory;
import com.innovzen.fragments.FragHistoryNew;
import com.innovzen.fragments.FragLanguage;
import com.innovzen.fragments.FragMain;
import com.innovzen.fragments.FragMainMenu;
import com.innovzen.fragments.FragMusic;
import com.innovzen.fragments.FragSession;
import com.innovzen.fragments.FragSettings;
import com.innovzen.fragments.FragSoundPicker;
import com.innovzen.fragments.FragTime;
import com.innovzen.fragments.FragTimer;
import com.innovzen.fragments.FragTimerAdvance;
import com.innovzen.fragments.FragVoice;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.handlers.ExerciseAnimationHandler;
import com.innovzen.handlers.SoundHandler;
import com.innovzen.interfaces.FragmentCommunicator;
import com.innovzen.interfaces.FragmentOnBackPressInterface;
import com.innovzen.utils.PersistentUtil;

//主界面
public class ActivityMain extends ActivityBase implements FragmentCommunicator {

	// Hold fragment tags
	public static final String FRAG_TAG_ANIMATION = "fragment_animation_tag";

	/**
	 * Key for the shared preferences used to keep the state whether this is the
	 * first run of the app EVER or not
	 */
	public static final String PERSIST_FIRST_RUN = "first_run_of_app";

	// Hold the "flag" to indicate whether the user has already pressed back
	// inside a small timeframe
	// Use this to display the "press back again to exist" toast
	private boolean mDoubleBackToExitPressedOnce = false;
	// Hold the toast we're displaying with the "press back again to exit"
	// message
	// Use this reference to cancel the toast on app exist
	private Toast mToast;

	// Hold the sound handler
	private SoundHandler mSoundHandler;

	// 蓝牙开启intent请求
	public static final int REQUEST_ENABLE_BT = 1;

	// 设置蓝牙对话框
	private FragBluetoothDialog bluetoothDialog = null;
	private BluetoothService mBluetoothService = null;
	private BluetoothAdapter mBluetoothAdapter = null;

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	// The Handler that gets information back from the BluetoothChatService


	private int state = 1;
	public void MyCommand(int start,int id,int coding,int checkSum,int end){
		sendCommand(start);
		if (state == 2) {
			sendCommand(id);
		}
		if (state == 3) {
			sendCommand(coding);
		}
		if (state == 4) {
			sendCommand(checkSum);
		}
		if (state == 5) {
			sendCommand(end);
		}
	}
	private final Handler bluetoothHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:// 链接状态变化
				switch (msg.arg1) {
				case BluetoothService.STATE_CONNECTED:// 连接建立
					//准备命令
					MyCommand(0xF0, 0x83, 0x01, 0x11, 0xF1);
					break;
				case BluetoothService.STATE_CONNECTING:// 正在建立连接

					break;
				case BluetoothService.STATE_LISTEN:// 监听端口
				case BluetoothService.STATE_NONE:
					break;
				}
				break;
			case MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				String writeMessage = new String(writeBuf);
				System.out.println(writeMessage);
				System.out.println(state);
				state++;
				if(state>5){
					state=1;
				}

				break;
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				// construct a string from the valid bytes in the buffer
				//String readMessage = new String(readBuf, 0, msg.arg1);
				
				break;
			case MESSAGE_DEVICE_NAME:// 保存设备名称
				// save the connected device's name
				break;
			case MESSAGE_TOAST:
				break;
			}
		}
	};

	public BluetoothService getBluetoothService() {
		return mBluetoothService;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Load the sound information
		loadSoundInfo();
		initBluetooth();
		// By default go to the main menu fragment
		// <chy>
		// super.navigateTo(FragMainMenu.class);
		super.navigateTo(FragMain.class);
		// </chy>
	}

	// 退出
	@Override
	public void onBackPressed() {
		// 获取当前FrameLayout片段
		// Get the current fragment in the FrameLayout
		Fragment frag = getSupportFragmentManager().findFragmentById(
				R.id.fragment_container);

		// If the fragment is allowed to handle the back press, then pass the
		// event to it first
		if (frag != null && frag instanceof FragmentOnBackPressInterface) {
			// If the fragment has NOT handled the back press, then we'll handle
			// it
			if (!(((FragmentOnBackPressInterface) frag).onBackPress())) {
				// Do nothin'. Let it continue with the normal onBackPress flow
			} else { // The fragment handled the event, so we don't have to do
						// anything else

				// Do nothin'
				return;
			}

		}

		// If the backstack still has fragments in it
		if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			// Continue normally and let it pop the fragment from the backstack
			super.onBackPressed();
		} else { // Otherwise ask the user if he really wants to leave
			// 两秒内双击返回键退出程序
			// If this is the second tap inside the small time frame, then exist
			// the app
			if (mDoubleBackToExitPressedOnce) {
				if (mToast != null) {
					mToast.cancel();
				}
				super.onBackPressed();
			}

			// Set the flag to true
			mDoubleBackToExitPressedOnce = true;
			// Display the toast
			mToast = Toast.makeText(this,
					getString(R.string.main_menu_back_again_to_exit),
					Toast.LENGTH_SHORT);
			mToast.show();
			// Start the timeframe during which if the user presses the back
			// again, then it will close the app
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// Cancel the flag after the timeout occurs
					mDoubleBackToExitPressedOnce = false;

					if (mToast != null) {
						// 立即停止toast显示
						mToast.cancel();
						mToast = null;
					}
				}
			}, 2000);

		}

	}

	// 退出之前关闭并释放资源
	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Release the players before exiting
		mSoundHandler.releasePlayers();
		mBluetoothService.stop();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		// case REQUEST_CONNECT_DEVICE:
		// // When DeviceListActivity returns with a device to connect
		// if (resultCode == Activity.RESULT_OK) {
		// // Get the device MAC address
		// String address =
		// data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		// // Get the BLuetoothDevice object
		// BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		// // Attempt to connect to the device
		// mChatService.connect(device);
		// }
		// break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				bluetoothDialog = FragBluetoothDialog
						.newInstance("aaa", "aaaa");
				bluetoothDialog.show(this.getSupportFragmentManager(), "bbb");

			} else {
				// User did not enable Bluetooth or an error occured
				Toast.makeText(this, R.string.bt_not_enabled_leaving,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 启动对话框
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {

		default:
			break;
		}
		return null;
	}

	/**
	 * Desmond 判断蓝牙设备是否开启
	 */
	private boolean isBlueToothSetup() {
		// 判断有无蓝牙设备
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, R.string.bt_not_enabled_leaving, 1000).show();
			return false;
		}
		// 判断蓝牙是否开启
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			this.startActivityForResult(enableIntent,
					ActivityMain.REQUEST_ENABLE_BT);
			return false;
		}
		// 判断蓝牙通信是否建立
		if (mBluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
			bluetoothDialog = FragBluetoothDialog.newInstance("", "");
			bluetoothDialog.show(this.getSupportFragmentManager(), "");
			return false;
		}
		return true;
	}

	/**
	 * 初始化蓝牙服务
	 */
	private void initBluetooth() {

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mBluetoothService = new BluetoothService(this, bluetoothHandler);
	}

	/**
	 * 向设备发送16指令
	 */
	private void sendCommand(String message) {
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			byte[] send = message.getBytes();
			mBluetoothService.write(send);
		}
	}

	/**
	 * 向设备发送16指令
	 */
	protected void sendCommand(int value) {
		byte[] src = new byte[4];
		src[3] = (byte) ((value >> 24) & 0xFF);
		src[2] = (byte) ((value >> 16) & 0xFF);
		src[1] = (byte) ((value >> 8) & 0xFF);
		src[0] = (byte) (value & 0xFF);
		mBluetoothService.write(new byte[] { src[0] });
	}

	/**
	 * Read the data from the .json file and parse it
	 * 
	 * @author MAB
	 */
	// ////////////////////////////////////////////////////////////----------------------
	private void loadSoundInfo() {

		// Configure the sounds (get the resource ids of the files <for quick
		// reference>)
		mSoundHandler = new SoundHandler(this);

	}

	/**
	 * Returns the list of sound groups representing the voices
	 * 
	 * @return
	 * @author MAB
	 */
	// 返回声音组列表
	public List<SoundGroup> getVoices() {
		if (mSoundHandler != null) {
			mSoundHandler.getVoices();
		}

		return null;
	}

	/**
	 * Returns the list of sound groups representing the ambiance
	 * 
	 * @return
	 * @author MAB
	 */
	// 得到声音氛围组列表
	public List<SoundGroup> getAmbiance() {
		if (mSoundHandler != null) {
			mSoundHandler.getAmbiance();
		}

		return null;
	}

	/**
	 * Starts playing the voice sound
	 * 
	 * @param SoundId
	 * @param type
	 * @author MAB
	 */
	// 播放声音
	public void playVoice(int soundId, int type) {
		mSoundHandler.playVoice(soundId, type);
	}

	/**
	 * Starts playing the ambiance sound
	 * 
	 * @param soundId
	 * @param type
	 * @author MAB
	 */
	// 播放氛围声音
	public void playAmbiance(int soundId, int type, boolean loop) {
		mSoundHandler.playAmbiance(soundId, type, loop);
	}

	/**
	 * Clears the backstack and jumps to the animation screen with some extras
	 * attached to the event
	 * 
	 * @param animationType
	 * @author MAB
	 */
	// 清理backstack并跳转到屏幕动画和附加事件
	private void gotoAnimationScreen(int animationType) {

		// Save the state first
		// 提交喜好
		PersistentUtil.setInt(this, animationType,
				FragAnimationPicker.PERSIST_SELECTED_EXERCISE_ANIMATION);

		// // Refresh the backstack
		super.clearFragFromBackstack(FRAG_TAG_ANIMATION);

		// Set the bundle
		Bundle bundle = new Bundle();
		bundle.putInt(FragAnimationBase.KEY_ANIMATION_TYPE, animationType);

		// Display the frag
		if (IS_TABLET) {

			// chy
			/*
			 * navigateTo(FragAnimationTablet.class, bundle, true,
			 * ActivityMain.FRAG_TAG_ANIMATION);
			 */
			navigateTo(FragAnimationTabletNew.class, bundle, true,
					ActivityMain.FRAG_TAG_ANIMATION);
			// chy
		} else {
			navigateTo(FragAnimationPhone.class, bundle, true,
					ActivityMain.FRAG_TAG_ANIMATION);
		}
	}

	// 接口方法
	@Override
	public void fragGoToChairInfo(boolean addToBackstack) {
		navigateTo(FragChairInfo.class, null, true);
	}

	@Override
	public void fragGoToAnimation(boolean addToBackstack) {

		super.clearBackstack();

		addToBackstack = true;

		if (IS_TABLET) {
			navigateTo(FragAnimationTabletNew.class, null, addToBackstack,
					ActivityMain.FRAG_TAG_ANIMATION);
		} else {
			navigateTo(FragAnimationPhone.class, null, addToBackstack,
					ActivityMain.FRAG_TAG_ANIMATION);
		}
	}

	@Override
	public void fragGoToAnimationPicker(boolean addToBackstack) {
		navigateTo(FragAnimationPicker.class, null, addToBackstack);
	}

	@Override
	public void fragGoToSoundPicker(boolean addToBackstack) {
		navigateTo(FragSoundPicker.class, null, addToBackstack);
	}

	@Override
	public void fragGoToExercisesPicker(boolean addToBackstack) {
		navigateTo(FragExercisePicker.class, null, addToBackstack);
	}

	@Override
	public void fragGoToHistory(boolean addToBackstack) {
		navigateTo(FragHistory.class, null, addToBackstack);

	}

	@Override
	public void fragGoToHome(boolean addToBackstack) {
		// Clear the backstack first
		super.clearBackstack();
		// Jump to the main menu screen
		navigateTo(FragMainMenu.class, null, addToBackstack);
	}

	@Override
	public void fragGoToHelp(boolean addToBackstack) {
		navigateTo(FragHelp.class, null, addToBackstack);
	}

	@Override
	public void fragGoToTimer(boolean addToBackstack) {
		navigateTo(FragTimer.class, null, addToBackstack);
	}

	@Override
	public void fragPopOneFromBackstack() {
		onBackPressed();
	}

	@Override
	public void fragGradientAnimationPicked() {
		gotoAnimationScreen(ExerciseAnimationHandler.ANIMATION_GRADIENT);
	}

	@Override
	public void fragPetalsAnimationPicked() {
		gotoAnimationScreen(ExerciseAnimationHandler.ANIMATION_PETALS);
	}

	@Override
	public void fragBeachAnimationPicked() {
		gotoAnimationScreen(ExerciseAnimationHandler.ANIMATION_BEACH);
	}

	@Override
	public void fragLungsAnimationPicked() {
		gotoAnimationScreen(ExerciseAnimationHandler.ANIMATION_LUNGS);
	}

	@Override
	public void fragStopPlayers() {
		mSoundHandler.pausePlayers();
	}

	@Override
	public List<SoundGroup> fragGetAmbiance() {
		if (mSoundHandler != null) {
			return mSoundHandler.getAmbiance();
		}

		return null;
	}

	@Override
	public List<SoundGroup> fragGetVoices() {
		if (mSoundHandler != null) {
			return mSoundHandler.getVoices();
		}

		return null;

	}

	@Override
	public void fragPlayVoice(int soundId, int type) {
		mSoundHandler.playVoice(soundId, type);
	}

	@Override
	public void fragPlayAmbiance(int soundId, int type, boolean loop) {
		mSoundHandler.playAmbiance(soundId, type, loop);
	}

	@Override
	public void fragSetAmbianceVolume(float volume) {
		mSoundHandler.setAmbianceVolume(volume);
	}

	@Override
	public void fragStopVoicePlayer() {
		mSoundHandler.stopVoicePlayer();
	}

	@Override
	public void fragStopAmbiancePlayer() {
		mSoundHandler.stopAmbiancePlayer();
	}

	@Override
	public void fragSoundValidate() {
		onBackPressed();
	}

	@Override
	public void fragGoToAnimationFromTimer() {
		super.clearBackstack();
		fragGoToAnimation(true);
	}

	@Override
	public void fragGoToAnimationFromTimerAdvanced() {
		super.clearBackstack();
		fragGoToAnimation(false);
	}

	@Override
	public void fragGoToTimerAdvance(boolean addToBackstack) {
		navigateTo(FragTimerAdvance.class, null, addToBackstack);
	}

	// //<chy>
	@Override
	public void fragGoToBalance(boolean addToBackstack) {
		navigateTo(FragBalance.class, null, addToBackstack);

	}

	@Override
	public void fragGoToSetting(boolean addToBackstack) {
		navigateTo(FragSettings.class, null, addToBackstack);

	}

	@Override
	public void fragGoToTime(boolean addToBackstack) {
		navigateTo(FragTime.class, null, addToBackstack);

	}

	@Override
	public void fragGoToMusic(boolean addToBackstack) {
		navigateTo(FragMusic.class, null, addToBackstack);

	}

	@Override
	public void fragGoToGraphic(boolean addToBackstack) {
		navigateTo(FragGraphic.class, null, addToBackstack);

	}

	@Override
	public void fragGoToLanguage(boolean addToBackstack) {
		navigateTo(FragLanguage.class, null, addToBackstack);

	}

	@Override
	public void fragGoToVoice(boolean addToBackstack) {
		navigateTo(FragVoice.class, null, addToBackstack);

	}

	@Override
	public void fragGoToHistoryNew(boolean addToBackstack) {
		navigateTo(FragHistoryNew.class, null, addToBackstack);

	}

	@Override
	public void fragGoToSession(boolean addToBackstack) {
		navigateTo(FragSession.class, null, addToBackstack);

	}

	@Override
	public void fragGoToHelpNew(boolean addToBackstack) {
		try {
			super.clearBackstack();
		} catch (Exception e) {
			e.printStackTrace();
			// Do nothin'
		}
		navigateTo(FragHelpNew.class, null, addToBackstack);

	}

	@Override
	public void fragConnectBluetooth() {
		isBlueToothSetup();

	}

	// 运行
	@Override
	public void GoToBegin() {
		MyCommand(0xF0, 0x83, 0x11, 0x11, 0xF1);
		/*sendCommand(0xF0);
		sendCommand(0x83);
		sendCommand(0x11);
		sendCommand(0x11);
		sendCommand(0xF1);*/

	}

	// 关闭
	@Override
	public void GoToEnd() {
		MyCommand(0xF0, 0x83, 0x01, 0x11, 0xF1);
		/*sendCommand(0xF0);
		sendCommand(0x83);
		sendCommand(0x01);
		sendCommand(0x11);
		sendCommand(0xF1);*/

	}

	// 暂停 机器硬件暂时未安装该功能
	@Override
	public void GoToPause() {
		MyCommand(0xF0, 0x83, 0x19, 0x19, 0xF1);
		/*sendCommand(0xF0);
		sendCommand(0x83);
		sendCommand(0x19);
		sendCommand(0x19);
		sendCommand(0xF1);*/
	}

}
