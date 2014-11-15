package com.innovzen.fragments;

import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innovzen.bluetooth.BluetoothCommand;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;
import com.innovzen.utils.MyPreference;

public class FragSettings extends FragBase implements OnClickListener{
//FragBase  ��Ϊ�̳�FragAnimationBase
	private TextView myMinutes;
	private ImageView oxygen,swing,led,heat,bluetooth;

	Handler machineHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			HashMap<Integer, Integer> map = (HashMap<Integer, Integer>)msg.obj;
			switch (msg.what) {
			case BluetoothCommand.OXYGEN_STATUS:
				if(map.get(BluetoothCommand.OXYGEN_STATUS)!=null&&map.get(BluetoothCommand.OXYGEN_STATUS)==BluetoothCommand.OXYGEN_STATUS_OFF){
					oxygen.setBackgroundResource(R.drawable.selector_icon_o2);
				}else{
					oxygen.setBackgroundResource(R.drawable.btn_o2_activated);
				}
				
				break;		
			}
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_setting,
				container, false);

		init(view);
        initLefter(view);
        initdata();
		return view;
	}
	private void initdata() {
		int Oxygen = MyPreference.getInstance(getActivity()).readInt(MyPreference.OXTGEN);
		if(Oxygen==MyPreference.OXTGEN_OPEN){
			oxygen.setBackgroundResource(R.drawable.btn_o2_activated);
		}else{
			oxygen.setBackgroundResource(R.drawable.selector_icon_o2);
		}
		int Swing = MyPreference.getInstance(getActivity()).readInt(MyPreference.SWING);
		if(Swing==MyPreference.SWING_OPEN){
			swing.setBackgroundResource(R.drawable.btn_swing_activated);
		}else{
			swing.setBackgroundResource(R.drawable.selector_icon_swing);
		}
		int Led = MyPreference.getInstance(getActivity()).readInt(MyPreference.LED);
		if(Led==MyPreference.LED_OPEN){
			led.setBackgroundResource(R.drawable.btn_leds_activated);
		}else{
			led.setBackgroundResource(R.drawable.selector_icon_leds);
		}
		int Heat = MyPreference.getInstance(getActivity()).readInt(MyPreference.HEAT);
		if(Heat==MyPreference.HEAT_OPEN){
			heat.setBackgroundResource(R.drawable.btn_heat_activated);
		}else{
			heat.setBackgroundResource(R.drawable.selector_icon_heat);
		}
		int Bluetooth = MyPreference.getInstance(getActivity()).readInt(MyPreference.BLUETOOTH);
		if(Bluetooth==MyPreference.BLUETOOTH_OPEN){
			bluetooth.setBackgroundResource(R.drawable.btn_bluetooth_activated);
		}else{
			bluetooth.setBackgroundResource(R.drawable.selector_icon_bluetooth);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.set_language:
			super.activityListener.fragGoToLanguage(true);
            break;
		case R.id.set_music:
			super.activityListener.fragGoToMusic(true);
			break;
		case R.id.set_time:
			super.activityListener.fragGoToTime(true);
			break;
		case R.id.set_graphic:
			super.activityListener.fragGoToGraphic(true);
			break;
		case R.id.set_voice:
			super.activityListener.fragGoToVoice(true);
			break;
		case R.id.set_history:
			super.activityListener.fragGoToHistoryNew(true);
			break;
		case R.id.set_oxygen:
			super.activityListener.fragSendCommand(BluetoothCommand.OXYGEN_MACHINE_VALUES);
			break;
		case R.id.set_heat:
			super.activityListener.fragSendCommand(BluetoothCommand.HEAT_MACHINE_VALUES);
			break;
		case R.id.set_swing:
			super.activityListener.fragSendCommand(BluetoothCommand.SWING_MACHINE_VALUES);
			break;
		case R.id.set_led:
			super.activityListener.fragSendCommand(BluetoothCommand.LED_MACHINE_VALUES);
			break;
		case R.id.set_bluetooth:
			super.activityListener.fragConnectBluetooth();
			
			break;
		default:
			break;
		}
		
	}

	@Override
	public void init(View view) {
		initLefter(view);
		myMinutes = (TextView) view.findViewById(R.id.myMinutes);
		myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
		view.findViewById(R.id.set_language).setOnClickListener(this);
		view.findViewById(R.id.set_music).setOnClickListener(this);
		view.findViewById(R.id.set_time).setOnClickListener(this);
		view.findViewById(R.id.set_graphic).setOnClickListener(this);
		view.findViewById(R.id.set_voice).setOnClickListener(this);
		view.findViewById(R.id.set_history).setOnClickListener(this);
		oxygen = (ImageView) view.findViewById(R.id.set_oxygen);
		oxygen.setOnClickListener(this);
		swing = (ImageView) view.findViewById(R.id.set_swing);
		swing.setOnClickListener(this);
		led = (ImageView) view.findViewById(R.id.set_led);
		led.setOnClickListener(this);
		heat = (ImageView) view.findViewById(R.id.set_heat);
		heat.setOnClickListener(this);
		bluetooth = (ImageView) view.findViewById(R.id.set_bluetooth);
		bluetooth.setOnClickListener(this);
	}
	
	@Override
	protected void initLefter(View view) {
		super.initLefter(view);
		leftTop.setBackgroundResource(R.drawable.selector_btn_back);
		leftMid.setBackgroundResource(R.drawable.banner_settings);
		leftBottom.setBackgroundResource(R.drawable.selector_btn_volume);
	}
	/**
	 * ��������
	 * @param command
	 */
	public void sendMachineMessage(int command,HashMap<Integer, Integer> bundle){
		Message msg=new Message();
		msg.what=command;
		msg.obj=bundle;
		machineHandler.sendMessage(msg);
	}

}
