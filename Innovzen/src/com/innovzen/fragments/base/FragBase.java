package com.innovzen.fragments.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovzen.fragments.VerticalSeekBar;

import com.innovzen.interfaces.FragmentCommunicator;
import com.innovzen.o2chair.R;

/**
 * fragment ���� ����fragment��������
 * 
 * @author Desmond Duan
 * 
 */
public abstract class FragBase extends Fragment {

	// Holds the reference to where the activity listens for messages from the
	// fragments
	protected FragmentCommunicator activityListener;
	private ImageView iv;
	private int maxVolume;
    private int currentVolume;
	/**
	 * desmond ������������
	 */
	protected ImageView leftTop;
	protected LinearLayout leftMid;
	protected ImageView leftBottom;
	protected LinearLayout voice_progressbar;
	private TextView myMinutes;
	private VerticalSeekBar seekBar;
	private AudioManager audiomanage;
	private ImageView volum_less;

	/**
	 * @author chy ȡ��ShareSharedPreferences��ֵ
	 */
	protected void getMyShareSharedPreferences(String str) {
		SharedPreferences sp = null;
		if (str.equals("time")) {
			sp = getActivity().getSharedPreferences("saveTimeMin",
					Context.MODE_PRIVATE);
			String mytime = sp.getString(str, "");
			myMinutes.setText(mytime);
		} else if (str.equals("graphic")) {
			sp = getActivity().getSharedPreferences("saveGraphic",
					Context.MODE_PRIVATE);
			Toast.makeText(getActivity(), "ok", 0).show();
		}
	}

	/**
	 * Does proper initializations after inflating the view
	 * 
	 * @param view
	 * @author MAB
	 */
	public abstract void init(View view);

	/**
	 * desmond ��ʼ����������
	 * 
	 * @param view
	 */
	protected void initLefter(View view) {
		audiomanage = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);						
		maxVolume = audiomanage.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
						
        volum_less = (ImageView) view.findViewById(R.id.volum_less);
		leftTop = (ImageView) view.findViewById(R.id.left_top);
		leftMid = (LinearLayout) view.findViewById(R.id.left_mid);
		leftBottom = (ImageView) view.findViewById(R.id.left_bottom);
		voice_progressbar = (LinearLayout) view
				.findViewById(R.id.voice_progressbar);
		seekBar = (VerticalSeekBar) view.findViewById(R.id.mySeekBar);
		seekBar.setMax(maxVolume);
		seekBar.setProgress(currentVolume);
		/*seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
            
			@Override
			public void onStopTrackingTouch(VerticalSeekBar VerticalSeekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(VerticalSeekBar VerticalSeekBar) {
				

			}

			@Override
			public void onProgressChanged(VerticalSeekBar VerticalSeekBar,
					int progress, boolean fromUser) {
				audiomanage.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
				currentVolume = audiomanage.getStreamVolume(AudioManager.STREAM_MUSIC);
                seekBar.setProgress(currentVolume);
                if(progress==0){
                	volum_less.setBackgroundResource(R.drawable.icon_no_volum);
                }else{
                	volum_less.setBackgroundResource(R.drawable.icon_volum_less);
                }
			}
		});*/
		myMinutes = (TextView) view.findViewById(R.id.myMinutes);
		// ���������һ��fragment
		leftTop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();

			}
		});
		// ����л�ͼƬ
		leftBottom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				leftMid.setVisibility(View.INVISIBLE);
				leftBottom.setVisibility(View.INVISIBLE);
				voice_progressbar.setVisibility(View.VISIBLE);
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

}
