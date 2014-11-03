
package com.innovzen.activities;

import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.innovzen.o2chair.R;
import com.innovzen.entities.SoundGroup;
import com.innovzen.fragments.FragAnimationPhone;
import com.innovzen.fragments.FragAnimationPicker;
import com.innovzen.fragments.FragAnimationTablet;
import com.innovzen.fragments.FragAnimationTabletNew;
import com.innovzen.fragments.FragBalance;
import com.innovzen.fragments.FragChairInfo;
import com.innovzen.fragments.FragExercisePicker;
import com.innovzen.fragments.FragGraphic;
import com.innovzen.fragments.FragHelp;
import com.innovzen.fragments.FragHistory;
import com.innovzen.fragments.FragHistory2;
import com.innovzen.fragments.FragLanguage;
import com.innovzen.fragments.FragMain;
import com.innovzen.fragments.FragMainMenu;
import com.innovzen.fragments.FragMusic;
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

    /** Key for the shared preferences used to keep the state whether this is the first run of the app EVER or not */
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
    
    /**
     * Desmond
     * 蓝牙设备适配器
     */
    private BluetoothAdapter mBluetoothAdapter = null;
    //蓝牙开启intent请求
    private static final int REQUEST_ENABLE_BT = 1;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the sound information
        loadSoundInfo();
        
        //获取蓝牙设备适配器实例
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // By default go to the main menu fragment
        //<chy>
       // super.navigateTo(FragMainMenu.class);
        super.navigateTo(FragMain.class);
        //</chy>
    }
    
    //退出
    @Override
    public void onBackPressed() {
        //获取当前FrameLayout片段
        // Get the current fragment in the FrameLayout
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

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
             //两秒内双击返回键退出程序
        	
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
            mToast = Toast.makeText(this, getString(R.string.main_menu_back_again_to_exit), Toast.LENGTH_SHORT);
            mToast.show();
            // Start the timeframe during which if the user presses the back
            // again, then it will close the app
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Cancel the flag after the timeout occurs
                    mDoubleBackToExitPressedOnce = false;

                    if (mToast != null) {
                    	//立即停止toast显示
                        mToast.cancel();
                        mToast = null;
                    }
                }
            }, 2000);

        }

    }
   //退出之前关闭并释放资源
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Release the players before exiting
        mSoundHandler.releasePlayers();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
//        case REQUEST_CONNECT_DEVICE:
//            // When DeviceListActivity returns with a device to connect
//            if (resultCode == Activity.RESULT_OK) {
//                // Get the device MAC address
//                String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
//                // Get the BLuetoothDevice object
//                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
//                // Attempt to connect to the device
//                mChatService.connect(device);
//            }
//            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                
            } else {
                // User did not enable Bluetooth or an error occured
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * Desmond
     * 判断蓝牙设备是否开启
     */
    private void isBlueToothSetup(){
    	// If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            return;
        }
        
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        
        } else {// 进入蓝牙通讯
            
        }
    }
    /**
     * Read the data from the .json file and parse it
     * 
     * @author MAB
     */
    //////////////////////////////////////////////////////////////----------------------
    private void loadSoundInfo() {

        // Configure the sounds (get the resource ids of the files <for quick reference>)
        mSoundHandler = new SoundHandler(this);

    }

    /**
     * Returns the list of sound groups representing the voices
     * 
     * @return
     * @author MAB
     */
    //返回声音组列表
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
    //得到声音氛围组列表
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
    //播放声音
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
    //播放氛围声音
    public void playAmbiance(int soundId, int type, boolean loop) {
        mSoundHandler.playAmbiance(soundId, type, loop);
    }

    /**
     * Clears the backstack and jumps to the animation screen with some extras attached to the event
     * 
     * @param animationType
     * @author MAB
     */
    //清理backstack并跳转到屏幕动画和附加事件
    private void gotoAnimationScreen(int animationType) {

        // Save the state first
    	//提交喜好
        PersistentUtil.setInt(this, animationType, FragAnimationPicker.PERSIST_SELECTED_EXERCISE_ANIMATION);

        // // Refresh the backstack
        super.clearFragFromBackstack(FRAG_TAG_ANIMATION);

        // Set the bundle
        Bundle bundle = new Bundle();
        bundle.putInt(FragAnimationBase.KEY_ANIMATION_TYPE, animationType);

        // Display the frag
        if (IS_TABLET) {
            navigateTo(FragAnimationTablet.class, bundle, true, ActivityMain.FRAG_TAG_ANIMATION);
        } else {
            navigateTo(FragAnimationPhone.class, bundle, true, ActivityMain.FRAG_TAG_ANIMATION);
        }
    }
//接口方法
    @Override
    public void fragGoToChairInfo(boolean addToBackstack) {
        navigateTo(FragChairInfo.class, null, true);
    }

    @Override
    public void fragGoToAnimation(boolean addToBackstack) {

        super.clearBackstack();

        addToBackstack = true;

        if (IS_TABLET) {
            navigateTo(FragAnimationTabletNew.class, null, addToBackstack, ActivityMain.FRAG_TAG_ANIMATION);
        } else {
            navigateTo(FragAnimationPhone.class, null, addToBackstack, ActivityMain.FRAG_TAG_ANIMATION);
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
    ////<chy>
	@Override
	public void fragGoToBalance(boolean addToBackstack) {
		navigateTo(FragBalance.class,null,addToBackstack);
		
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
	public void fragGoToHistory2(boolean addToBackstack) {
		navigateTo(FragHistory2.class, null, addToBackstack);
		
	}

	
}
