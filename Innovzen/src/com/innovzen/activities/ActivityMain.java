package com.innovzen.activities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseIntArray;
import android.widget.Toast;

import com.innovzen.o2chair.R;
import com.innovzen.bluetooth.BluetoothCommand;
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
import com.innovzen.utils.MyPreference;
import com.innovzen.utils.PersistentUtil;

//������
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

	// ��������intent����
	public static final int REQUEST_ENABLE_BT = 1;

	// ���������Ի���
	private FragBluetoothDialog bluetoothDialog = null;
	private BluetoothService mBluetoothService = null;
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothCommand mBluetoothCommand = null;

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	
	public static final Boolean FLAG=false;
	
	
	// The Handler that gets information back from the BluetoothChatService
	private final Handler bluetoothHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:// ����״̬�仯
				switch (msg.arg1) {
				case BluetoothService.STATE_CONNECTED:// ���ӽ���
					//׼������
					mBluetoothCommand.sendCommand(BluetoothCommand.START_MACHINE_VALUES);
					break;
				case BluetoothService.STATE_CONNECTING:// ���ڽ�������

					break;
				case BluetoothService.STATE_LISTEN:// �����˿�
				case BluetoothService.STATE_NONE:
					break;
				}
				break;
			case MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				String writeMessage = new String(writeBuf);
				System.out.println(writeMessage);
				break;
			case MESSAGE_READ:				
				/**
				 * ��������������յ����ݺ��������
				 */
				//byte[] readBuf = (byte[]) msg.obj;
				//mBluetoothCommand.parseCommand(readBuf);
				/**
				 * �ж��ǵ�ǰfragment�Ƿ���FragAnimationTabletNew
				 */
		        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
		        if (currentFragment != null&&currentFragment.getClass().getSimpleName().equalsIgnoreCase("FragAnimationTabletNew")) {
		        	/**
		        	 * �������  FragAnimationTabletNew�﹦�ܰ�ť��״̬
		        	 * �簴ťѡ��״̬�ı�
		        	 * �綯�����ź�ֹͣ��
		        	 */
	        		 if(IS_TABLET){    	
	        			//mBluetoothCommand.printCommand(readBuf);
	        			//��ʼλ
	        			 //Log.e("INIT_POSITION_STATUS", value+"");
	        			SparseIntArray map=new SparseIntArray();
	        			//��������״̬
	        			map.put(BluetoothCommand.MACHINE_RUN_STATUS, mBluetoothCommand.getValue(BluetoothCommand.MACHINE_MASSAGE_STATUS));
	        			((FragAnimationTabletNew)currentFragment).sendMachineMessage(BluetoothCommand.MACHINE_RUN_STATUS,map);

	        			map.put(BluetoothCommand.INIT_POSITION_STATUS,mBluetoothCommand.getValue(BluetoothCommand.INIT_POSITION_STATUS));
	        			map.put(BluetoothCommand.DIRECTION_STATUS,mBluetoothCommand.getValue(BluetoothCommand.DIRECTION_STATUS));
	        			((FragAnimationTabletNew)currentFragment).sendMachineMessage(BluetoothCommand.INIT_POSITION_STATUS,map);
                        //������
	        			map.put(BluetoothCommand.ZERO_STATUS,mBluetoothCommand.getValue(BluetoothCommand.ZERO_STATUS));      			 
	        			((FragAnimationTabletNew)currentFragment).sendMachineMessage(BluetoothCommand.ZERO_STATUS,map);
	        			/**
	        			 * ������λ����
	        			 */
	        			//Log.e("��λ ��ֵ�Ա�", mBluetoothCommand.getValue(BluetoothCommand.WALKING_POSITION_STATUS)+" "+mBluetoothCommand.getValue(BluetoothCommand.DIRECTION_STATUS));
	        			map.put(BluetoothCommand.WALKING_POSITION_STATUS,mBluetoothCommand.getValue(BluetoothCommand.WALKING_POSITION_STATUS));
	        			map.put(BluetoothCommand.DIRECTION_STATUS,mBluetoothCommand.getValue(BluetoothCommand.DIRECTION_STATUS));
	        			((FragAnimationTabletNew)currentFragment).sendMachineMessage(BluetoothCommand.WALKING_POSITION_STATUS,map);
	        			
//	        			if (mBluetoothCommand.getValue(BluetoothCommand.WALKING_POSITION_STATUS) == BluetoothCommand.WALKING_POSITION_STATUS1) {
//	        				//��һ���ź�
//	        				if(mBluetoothCommand.getValue(BluetoothCommand.DIRECTION_STATUS)==BluetoothCommand.DIRECTION_STATUS_UP){//����
//	        					Log.e("���� ��һ���ź�", System.currentTimeMillis()+"");
//	        					mBluetoothCommand.setInhaleTimeStart(System.currentTimeMillis());
//	        				}else if(mBluetoothCommand.getValue(BluetoothCommand.DIRECTION_STATUS)==BluetoothCommand.DIRECTION_STATUS_DOWN){//����
//	        					Log.e("���� ��һ���ź�", System.currentTimeMillis()+"");
//	        					mBluetoothCommand.setExhaleTimeStart(System.currentTimeMillis());
//	        				}
//	        				
//	        			}
//	        			if (mBluetoothCommand.getValue(BluetoothCommand.WALKING_POSITION_STATUS) == BluetoothCommand.WALKING_POSITION_STATUS12){
//	        				//���һ���ź�
//	        				if(mBluetoothCommand.getValue(BluetoothCommand.DIRECTION_STATUS)==BluetoothCommand.DIRECTION_STATUS_UP){//����
//	        					Log.e("���� �����ź�", System.currentTimeMillis()+"");
//	        					mBluetoothCommand.setInhaleTimeEnd(System.currentTimeMillis());
//	        				}else if(mBluetoothCommand.getValue(BluetoothCommand.DIRECTION_STATUS)==BluetoothCommand.DIRECTION_STATUS_DOWN){//����
//	        					Log.e("���� �����ź�", System.currentTimeMillis()+"");
//	        					mBluetoothCommand.setExhaleTimeEnd(System.currentTimeMillis());
//	        				}
//	        				
//	        			}
	        			
	        		 }
		        
		        }
				/**
				 * �ж��ǵ�ǰfragment�Ƿ���FragSettings
				 */
		        if (currentFragment != null&&currentFragment.getClass().getSimpleName().equalsIgnoreCase("FragSettings")) {
		        	/**
		        	 * �������  FragSetting�﹦�ܰ�ť��״̬	        	
		        	 */
		        	if(IS_TABLET){
		        		SparseIntArray map=new SparseIntArray();
		        		map.put(BluetoothCommand.OXYGEN_STATUS, mBluetoothCommand.getValue(BluetoothCommand.OXYGEN_STATUS));
		        		((FragSettings)currentFragment).sendMachineMessage(BluetoothCommand.OXYGEN_STATUS,map);
		        		map.put(BluetoothCommand.LED_STATUS, mBluetoothCommand.getValue(BluetoothCommand.LED_STATUS));
		        		((FragSettings)currentFragment).sendMachineMessage(BluetoothCommand.LED_STATUS,map);
		        		map.put(BluetoothCommand.SWING_STATUS, mBluetoothCommand.getValue(BluetoothCommand.SWING_STATUS));
		        		((FragSettings)currentFragment).sendMachineMessage(BluetoothCommand.SWING_STATUS,map);
		        		map.put(BluetoothCommand.HEAT_STATUS, mBluetoothCommand.getValue(BluetoothCommand.HEAT_STATUS));
		        		((FragSettings)currentFragment).sendMachineMessage(BluetoothCommand.HEAT_STATUS,map);
		        		map.put(BluetoothCommand.BLUETOOTH_STATUS, mBluetoothCommand.getValue(BluetoothCommand.BLUETOOTH_STATUS));
		        		((FragSettings)currentFragment).sendMachineMessage(BluetoothCommand.BLUETOOTH_STATUS,map);
		        		map.put(BluetoothCommand.PULSE_STATUS, mBluetoothCommand.getValue(BluetoothCommand.PULSE_STATUS));
		        		((FragSettings)currentFragment).sendMachineMessage(BluetoothCommand.PULSE_STATUS,map);
		        	}
		        }
				
				break;
			case MESSAGE_DEVICE_NAME:// �����豸����
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

	// �˳�
	@Override
	public void onBackPressed() {
		// ��ȡ��ǰFrameLayoutƬ��
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
			// ������˫�����ؼ��˳�����
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
						// ����ֹͣtoast��ʾ
						mToast.cancel();
						mToast = null;
					}
				}
			}, 2000);

		}

	}

	// �˳�֮ǰ�رղ��ͷ���Դ
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
	 * �����Ի���
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
	 * Desmond �ж������豸�Ƿ���
	 */
	public boolean isBlueToothSetup() {
		// �ж����������豸
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, R.string.bt_not_enabled_leaving, 1000).show();
			return false;
		}
		// �ж������Ƿ���
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			this.startActivityForResult(enableIntent,
					ActivityMain.REQUEST_ENABLE_BT);
			return false;
		}
		// �ж�����ͨ���Ƿ���
		if (mBluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
			bluetoothDialog = FragBluetoothDialog.newInstance("", "");
			bluetoothDialog.show(this.getSupportFragmentManager(), "");
			return false;
		}
		return true;
	}
	
	/**
	 * �ж�������������
	 * @return
	 */
	public boolean isBlueToothConnected(){
		// �ж����������豸
		if (mBluetoothAdapter == null) {
			return false;
		}
		// �ж������Ƿ���
		if (!mBluetoothAdapter.isEnabled()) {
			return false;
		}
		// �ж�����ͨ���Ƿ���
		if (mBluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
			return false;
		}
		return true;
	}

	/**
	 * ��ʼ����������
	 */
	private void initBluetooth() {

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mBluetoothService = new BluetoothService(this, bluetoothHandler);
		mBluetoothCommand = new BluetoothCommand(this,mBluetoothService);
	}

	/**
	 * ���豸����16ָ��
	 */
	private void sendCommand(String message) {
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			byte[] send = message.getBytes();
			mBluetoothService.write(send);
		}
	}

	/**
	 * ���豸����16ָ��
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
	// �����������б�
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
	// �õ�������Χ���б�
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
	// ��������
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
	// ���ŷ�Χ����
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
	// ����backstack����ת����Ļ�����͸����¼�
	private void gotoAnimationScreen(int animationType) {

		// Save the state first
		// �ύϲ��
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

	// �ӿڷ���
	@Override
	public void fragGoToChairInfo(boolean addToBackstack) {
		navigateTo(FragChairInfo.class, null, true);
	}

	@Override
	public void fragGoToAnimation(boolean addToBackstack) {

		super.clearBackstack();

		addToBackstack = true;
		
		// Set the bundle
		Bundle bundle = new Bundle();
		bundle.putInt(FragAnimationBase.KEY_ANIMATION_TYPE, 
				MyPreference.getInstance(this).readInt(MyPreference.GRAPHIC));

		if (IS_TABLET) {
			navigateTo(FragAnimationTabletNew.class, bundle, addToBackstack,
					ActivityMain.FRAG_TAG_ANIMATION);
		} else {
			navigateTo(FragAnimationPhone.class, bundle, addToBackstack,
					ActivityMain.FRAG_TAG_ANIMATION);
		}
	}

	@Override
	public void fragGoToAnimationPicker(boolean addToBackstack) {
		navigateTo(FragAnimationPicker.class, null, addToBackstack);
	}

	@Override
	public void fragGoToSoundPicker(boolean addToBackstack) {
		//navigateTo(FragSoundPicker.class, null, addToBackstack);
		navigateTo(FragVoice.class, null, addToBackstack);
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
		//avigateTo(FragSoundPicker.class, null, addToBackstack);

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
		navigateTo(FragHelpNew.class, null, addToBackstack);
	}

	@Override
	public void fragConnectBluetooth() {
		isBlueToothSetup();
	}
	

	@Override
	public void fragSendCommand(int[] commands) {
		mBluetoothCommand.sendCommand(commands);
	}

	@Override
	public void fragGetCommand(byte[] bytes) {
		mBluetoothCommand.parseCommand(bytes);
		
	}

	@Override
	public void fragCloseBluetooth() {
		mBluetoothService.stop();
		
	}

	
}
