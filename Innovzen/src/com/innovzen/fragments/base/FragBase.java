package com.innovzen.fragments.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.innovzen.interfaces.FragmentCommunicator;
import com.innovzen.o2chair.R;
import com.innovzen.ui.VerticalSeekBar;
import com.innovzen.utils.MyPreference;

/**
 * fragment 父类 所有fragment共有属性
 * 
 * @author Desmond Duan
 * 
 */
public abstract class FragBase extends Fragment {

	public static final String SHARED_MUSIC = "music";
	public static final String SHARED_TIME = "time";
	public static final String SHARED_GRAPHIC = "graphic";
	public static final String SAVE_TIME_MIN = "saveTimeMin";
	public static final String SAVE_GRAPHIC = "saveGraphic";
	public static final String SAVE_MUSIC = "saveMusic";
	// Holds the reference to where the activity listens for messages from the
	// fragments
	protected FragmentCommunicator activityListener;
	private ImageView iv;
	private int maxVolume;
	private int lastVolumeValue;
    private int currentVolume;
	/**
	 * desmond 界面左侧控制栏
	 */
	protected ImageView leftTop;
	protected LinearLayout leftMid;
	protected ImageView leftBottom;
	protected RelativeLayout voice_progressbar;
	private VerticalSeekBar seekBar;
	private AudioManager audiomanage;
	private ImageView volum_less;

	/**
	 * 接受机器指令handler
	 */
	protected Handler machineHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			handlerMachineMessage(msg);
		};
	};

	/**
	 * Does proper initializations after inflating the view
	 * 
	 * @param view
	 * @author MAB
	 */
	public abstract void init(View view);

	/**
	 * desmond 初始化左侧控制栏
	 * 
	 * @param view
	 */
	protected void initLefter(View view) {
		audiomanage = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);						
		maxVolume = audiomanage.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		lastVolumeValue = MyPreference.getInstance(getActivity()).readInt(MyPreference.LAST_VOLUME);
			
        volum_less = (ImageView) view.findViewById(R.id.volum_less);
        volum_less.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				seekBar.setProgress(0);
				
			}
		});
		leftTop = (ImageView) view.findViewById(R.id.left_top);
		leftMid = (LinearLayout) view.findViewById(R.id.left_mid);
		leftBottom = (ImageView) view.findViewById(R.id.left_bottom);
		voice_progressbar = (RelativeLayout) view.findViewById(R.id.voice_progressbar);
		seekBar = (VerticalSeekBar) view.findViewById(R.id.mySeekBar);
		seekBar.setMax(maxVolume);
		
		if(lastVolumeValue==maxVolume/2||lastVolumeValue==0){
			seekBar.setProgress(maxVolume/2);
			}else{
				seekBar.setProgress(lastVolumeValue);
			}
	//	seekBar.setProgressDrawable(d)
		
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
     

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				audiomanage.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
				currentVolume = audiomanage.getStreamVolume(AudioManager.STREAM_MUSIC);
                seekBar.setProgress(currentVolume);
            
                if(progress==0){
                	volum_less.setBackgroundResource(R.drawable.icon_no_volum);
                }else{
                	volum_less.setBackgroundResource(R.drawable.icon_volum_less);
                }
              
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				 MyPreference.getInstance(getActivity()).writeString(MyPreference.LAST_VOLUME, currentVolume);
				
			}
		});
		
		// 点击返回上一个fragment
		leftTop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(voice_progressbar.getVisibility()==View.VISIBLE){
					voice_progressbar.setVisibility(View.INVISIBLE);
					leftMid.setVisibility(View.VISIBLE);
					leftBottom.setVisibility(View.VISIBLE);
				}else{
				   getActivity().onBackPressed();
				}
			}
		});
		// 点击切换图片
		leftBottom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				leftMid.setVisibility(View.INVISIBLE);
				leftBottom.setVisibility(View.INVISIBLE);
				voice_progressbar.setVisibility(View.VISIBLE);
				
				
				
			
			}
		});
		
		voice_progressbar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				leftMid.setVisibility(View.VISIBLE);
				leftBottom.setVisibility(View.VISIBLE);
				voice_progressbar.setVisibility(View.INVISIBLE);
				
			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof FragmentCommunicator) {
			activityListener = (FragmentCommunicator) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentCommunicator");
		}

	}

	/**
	 * 处理机器指令
	 * @param msg
	 */
	protected void handlerMachineMessage(Message msg){
		
	}
	/**
	 * 发送命令
	 * 
	 * @param command
	 */
	public void sendMachineMessage(int command, SparseIntArray bundle) {
		Message msg = new Message();
		msg.what = command;
		msg.obj = bundle;
		machineHandler.sendMessage(msg);
	}
}
