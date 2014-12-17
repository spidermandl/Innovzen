package com.innovzen.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Message;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.innovzen.bluetooth.BluetoothCommand;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;
import com.innovzen.ui.VerticalSeekBar;
import com.innovzen.utils.MyPreference;

public class FragSession extends FragBase implements OnClickListener {
	/**
	 * sessionΩÁ√Ê
	 */

	private ImageView back, setting, help, beginner, intermadiate,
			session_stop, session_start, session_pause, session_zero, pro,
			customise;
	private TextView min;
	private ImageView session_volume;
	private ImageView image_beginner;
	private ImageView image_intermediate;
	private ImageView image_pro;
	private RelativeLayout voice_progressbar2;
	private ImageView volum_less2;
	private ImageView volum_max2;
	private VerticalSeekBar mySeekBar2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mysession, container,
				false);
		init(view);
		

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.session_back:
			getActivity().onBackPressed();
			break;
		case R.id.session_volume:
			voice_progressbar2.setVisibility(View.VISIBLE);
			break;
		case R.id.session_setting:
			super.activityListener.fragGoToSetting(true);
			break;
		case R.id.session_help:
			super.activityListener.fragGoToHelpNew(true);
			break;
		case R.id.session_zero:
			super.activityListener
					.fragSendCommand(BluetoothCommand.ZERO_GRAVITY_MACHINE_VALUES);
			break;
		case R.id.session_stop:
			super.activityListener
					.fragSendCommand(BluetoothCommand.START_MACHINE_VALUES);
			break;
		case R.id.session_start:
			String blance_relax_performance = MyPreference.getInstance(
					getActivity()).readString(
					MyPreference.BLANCE_RELAX_PERFORMANCE);
			if (blance_relax_performance.equals(MyPreference.BLANCE)) {
				super.activityListener
						.fragSendCommand(BluetoothCommand.BLANCE_MACHINE_VALUES);
			} else if (blance_relax_performance.equals(MyPreference.RELAX)) {
				super.activityListener
						.fragSendCommand(BluetoothCommand.RELAX_MACHINE_VALUES);
			} else if (blance_relax_performance
					.equals(MyPreference.PERFORMANCE)) {
				super.activityListener
						.fragSendCommand(BluetoothCommand.PERFORMANCE_MACHINE_VALUES);
			} else {
				super.activityListener
						.fragSendCommand(BluetoothCommand.BLANCE_MACHINE_VALUES);
			}
			break;
		case R.id.session_pause:
			super.activityListener
					.fragSendCommand(BluetoothCommand.PAUSE_MACHINE_VALUES);
			// super.pauseExercise();
			break;
		case R.id.beginner:
			MyPreference.getInstance(getActivity()).writeString(
					MyPreference.SESSION_MODE, MyPreference.BEGINNER);
			beginner.setBackgroundResource(R.drawable.btn_my_session_left_beginner_activated);
			intermadiate
					.setBackgroundResource(R.drawable.selector_icon_intermadiate);
			pro.setBackgroundResource(R.drawable.selector_icon_pro);
			customise.setBackgroundResource(R.drawable.selector_icon_customise);
			image_beginner.setVisibility(View.VISIBLE);
			image_intermediate.setVisibility(View.GONE);
			image_pro.setVisibility(View.GONE);
			break;
		case R.id.intermadiate:
			MyPreference.getInstance(getActivity()).writeString(
					MyPreference.SESSION_MODE, MyPreference.INTERMEDAITE);
			beginner.setBackgroundResource(R.drawable.selector_icon_beginner);
			intermadiate
					.setBackgroundResource(R.drawable.btn_my_session_intermediate_activated);
			pro.setBackgroundResource(R.drawable.selector_icon_pro);
			customise.setBackgroundResource(R.drawable.selector_icon_customise);
			image_beginner.setVisibility(View.GONE);
			image_intermediate.setVisibility(View.VISIBLE);
			image_pro.setVisibility(View.GONE);
			break;
		case R.id.pro:
			MyPreference.getInstance(getActivity()).writeString(
					MyPreference.SESSION_MODE, MyPreference.PRO);
			beginner.setBackgroundResource(R.drawable.selector_icon_beginner);
			intermadiate
					.setBackgroundResource(R.drawable.selector_icon_intermadiate);
			pro.setBackgroundResource(R.drawable.btn_my_session_pro_activated);
			customise.setBackgroundResource(R.drawable.selector_icon_customise);
			image_beginner.setVisibility(View.GONE);
			image_intermediate.setVisibility(View.GONE);
			image_pro.setVisibility(View.VISIBLE);
			break;
		case R.id.customise:
			MyPreference.getInstance(getActivity()).writeString(
					MyPreference.SESSION_MODE, MyPreference.CUSTOMISE);
			beginner.setBackgroundResource(R.drawable.selector_icon_beginner);
			intermadiate
					.setBackgroundResource(R.drawable.selector_icon_intermadiate);
			pro.setBackgroundResource(R.drawable.selector_icon_pro);
			customise
					.setBackgroundResource(R.drawable.btn_my_session_customise_activated);
			break;
		case R.id.voice_progressbar2:
			voice_progressbar2.setVisibility(View.GONE);
			break;
		case R.id.volum_less2:

			mySeekBar2.setProgress(0);
			MyPreference.getInstance(FragSession.this.getActivity()).writeInt(MyPreference.LAST_VOLUME, 0);
			break;
		case R.id.volumn_max2:
			break;
		default:
			break;
		}
	}

	@Override
	public void init(View view) {
		
		volum_less2 = (ImageView) view.findViewById(R.id.volum_less2);
		volum_less2.setOnClickListener(this);
		volum_max2 = (ImageView) view.findViewById(R.id.volumn_max2);
		mySeekBar2 = (VerticalSeekBar) view.findViewById(R.id.mySeekBar2);
		volum_max2.setOnClickListener(this);
		voice_progressbar2 = (RelativeLayout) view.findViewById(R.id.voice_progressbar2);
		voice_progressbar2.setOnClickListener(this);
		back = (ImageView) view.findViewById(R.id.session_back);
		back.setOnClickListener(this);
		setting = (ImageView) view.findViewById(R.id.session_setting);
		setting.setOnClickListener(this);
		help = (ImageView) view.findViewById(R.id.session_help);
		help.setOnClickListener(this);
		min = (TextView) view.findViewById(R.id.session_min);
		beginner = (ImageView) view.findViewById(R.id.beginner);
		beginner.setOnClickListener(this);
		intermadiate = (ImageView) view.findViewById(R.id.intermadiate);
		intermadiate.setOnClickListener(this);
		pro = (ImageView) view.findViewById(R.id.pro);
		pro.setOnClickListener(this);
		customise = (ImageView) view.findViewById(R.id.customise);
		customise.setOnClickListener(this);
		session_zero = (ImageView) view.findViewById(R.id.session_zero);
		session_zero.setOnClickListener(this);
		session_stop = (ImageView) view.findViewById(R.id.session_stop);
		session_stop.setOnClickListener(this);
		session_start = (ImageView) view.findViewById(R.id.session_start);
		session_start.setOnClickListener(this);
		session_pause = (ImageView) view.findViewById(R.id.session_pause);
		session_pause.setOnClickListener(this);
		session_volume = (ImageView) view.findViewById(R.id.session_volume);
		session_volume.setOnClickListener(this);
		image_beginner = (ImageView) view.findViewById(R.id.image_beginner);
		
		image_intermediate = (ImageView) view.findViewById(R.id.image_intermediate);
		
		image_pro = (ImageView) view.findViewById(R.id.image_pro);
		
		min.setText(MyPreference.getInstance(getActivity()).readInt(
				MyPreference.TIME)+"");
		String mySession = MyPreference.getInstance(getActivity()).readString(
				MyPreference.SESSION_MODE);
		beginner.setBackgroundResource(R.drawable.btn_my_session_left_beginner_activated);
		if (mySession.equals(MyPreference.BEGINNER) || mySession == null) {
			beginner.setBackgroundResource(R.drawable.btn_my_session_left_beginner_activated);
		} else if (mySession.equals(MyPreference.INTERMEDAITE)) {
			beginner.setBackgroundResource(R.drawable.selector_icon_beginner);
			intermadiate
					.setBackgroundResource(R.drawable.btn_my_session_intermediate_activated);
		} else if (mySession.equals(MyPreference.PRO)) {
			beginner.setBackgroundResource(R.drawable.selector_icon_beginner);
			pro.setBackgroundResource(R.drawable.btn_my_session_pro_activated);
		} else if (mySession.equals(MyPreference.CUSTOMISE)) {
			beginner.setBackgroundResource(R.drawable.selector_icon_beginner);
			customise
					.setBackgroundResource(R.drawable.btn_my_session_customise_activated);
		}
		
mySeekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				//audiomanage.setStreamVolume(AudioManager.STREAM_MUSIC,progress, 0);

				MyPreference.getInstance(FragSession.this.getActivity()).writeInt(MyPreference.LAST_VOLUME,progress);
				if (progress == 0) {
					volum_less2.setBackgroundResource(R.drawable.icon_no_volum);
				} else {
					volum_less2
							.setBackgroundResource(R.drawable.icon_volum_less);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
				
			}
		});
		
		//int lastVolumeValue = MyPreference.getInstance(getActivity()).readInt(MyPreference.LAST_VOLUME);
    
	}

	@Override
		protected void handlerMachineMessage(Message msg) {
			SparseIntArray map = (SparseIntArray)msg.obj;
			switch (msg.what) {
			case BluetoothCommand.ZERO_STATUS:
				if(map.get(BluetoothCommand.ZERO_STATUS)==BluetoothCommand.ZERO_STATUS_CLOSE){
                       session_zero.setBackgroundResource(R.drawable.selector_btn_gravity);
				 }else{
					 session_zero.setBackgroundResource(R.drawable.btn_gravity_activated);
				 }
				break;
			case BluetoothCommand.PAUSE_STATUS:
				if(map.get(BluetoothCommand.PAUSE_STATUS)==BluetoothCommand.PAUSE_STATUS_ON){
					session_pause.setBackgroundResource(R.drawable.btn_exercise_pause_activated);
				}else{
					session_pause.setBackgroundResource(R.drawable.selector_btn_pause);
				}
				break;
			}
	    }
}
