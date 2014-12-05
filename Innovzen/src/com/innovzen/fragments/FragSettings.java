package com.innovzen.fragments;

import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.innovzen.activities.ActivityMain;
import com.innovzen.bluetooth.BluetoothCommand;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;
import com.innovzen.ui.VerticalSeekBar;
import com.innovzen.utils.MyPreference;

public class FragSettings extends FragBase implements OnClickListener{
	private TextView myMinutes;
	private ImageView oxygen,swing,led,heat,bluetooth,pulse,breathe;
	private boolean breathe_activited=false;

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
		
		if(((ActivityMain)getActivity()).isBlueToothConnected()){
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
			//super.activityListener.fragGoToHistoryNew(true);
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
			//super.activityListener.fragSendCommand(BluetoothCommand.BLUETOOTH_MACHINE_VALUES);
			//super.activityListener.fragSendCommand(BluetoothCommand.START_MACHINE_VALUES);
			break;
		case R.id.set_breathe:
			if(!breathe_activited)
				super.activityListener
					.fragSendCommand(BluetoothCommand.BREATHE_UP_MACHINE_VALUES);
			else
				super.activityListener
					.fragSendCommand(BluetoothCommand.BREATHE_DOWN_MACHINE_VALUES);
			break;
		default:
			break;
		}
		
	}
   void breatheAddCommand(){
		super.activityListener.fragSendCommand(BluetoothCommand.BREATHE_UP_MACHINE_VALUES);
 
   }
   void breatheSubCommand(){
		super.activityListener.fragSendCommand(BluetoothCommand.BREATHE_DOWN_MACHINE_VALUES);

  }
	@Override
	public void init(View view) {
		initLefter(view);
		
		myMinutes = (TextView) view.findViewById(R.id.myMinutes);
		myMinutes.setText(MyPreference.getInstance(this.getActivity()).readInt(MyPreference.TIME)/60000+MyPreference.MINS);
		view.findViewById(R.id.set_language).setOnClickListener(this);
		view.findViewById(R.id.set_music).setOnClickListener(this);
		view.findViewById(R.id.set_time).setOnClickListener(this);
		view.findViewById(R.id.set_graphic).setOnClickListener(this);
		view.findViewById(R.id.set_voice).setOnClickListener(this);
		//view.findViewById(R.id.set_history).setOnClickListener(this);
		
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
		pulse = (ImageView) view.findViewById(R.id.set_pulse);
		pulse.setOnClickListener(this);
		breathe=(ImageView)view.findViewById(R.id.set_breathe);
		breathe.setOnClickListener(this);
	}
	
	@Override
	protected void initLefter(View view) {
		super.initLefter(view);
		leftTop.setBackgroundResource(R.drawable.selector_btn_back);
		leftMid.setBackgroundResource(R.drawable.banner_settings);
		leftBottom.setBackgroundResource(R.drawable.selector_btn_volume);
	}

	@Override
	protected void handlerMachineMessage(Message msg) {
		SparseIntArray map = (SparseIntArray)msg.obj;
		switch (msg.what) {
		case BluetoothCommand.OXYGEN_STATUS:
			if(map.get(BluetoothCommand.OXYGEN_STATUS)==BluetoothCommand.OXYGEN_STATUS_OFF){
				oxygen.setBackgroundResource(R.drawable.selector_icon_o2);
			}else{
				oxygen.setBackgroundResource(R.drawable.btn_o2_activated);
			}				
			break;	
		case BluetoothCommand.LED_STATUS:
			if(map.get(BluetoothCommand.LED_STATUS)==BluetoothCommand.LED_STATUS_OFF){
				led.setBackgroundResource(R.drawable.selector_icon_leds);
			}else{
				led.setBackgroundResource(R.drawable.btn_leds_activated);
			}	
			break;
		case BluetoothCommand.SWING_STATUS:
			if(map.get(BluetoothCommand.SWING_STATUS)==BluetoothCommand.SWING_STATUS_OFF){
				swing.setBackgroundResource(R.drawable.selector_icon_swing);
			}else{
				swing.setBackgroundResource(R.drawable.btn_swing_activated);
			}	
			break;
		case BluetoothCommand.HEAT_STATUS:
			if(map.get(BluetoothCommand.HEAT_STATUS)==BluetoothCommand.HEAT_STATUS_OFF){
				heat.setBackgroundResource(R.drawable.selector_icon_heat);
			}else{
				heat.setBackgroundResource(R.drawable.btn_heat_activated);
			}	
			break;
		case BluetoothCommand.BLUETOOTH_STATUS:
			if(map.get(BluetoothCommand.BLUETOOTH_STATUS)==BluetoothCommand.BLUETOOTH_STATUS_ON){
				bluetooth.setBackgroundResource(R.drawable.btn_bluetooth_activated);				
				
			}else{
				bluetooth.setBackgroundResource(R.drawable.selector_icon_bluetooth);
			}	
			break;
		case BluetoothCommand.PULSE_STATUS:
			if(map.get(BluetoothCommand.PULSE_STATUS)==BluetoothCommand.PULSE_STATUS_OFF){
				pulse.setBackgroundResource(R.drawable.selector_icon_pulse);
			}else{
				pulse.setBackgroundResource(R.drawable.btn_pulse_activated);
			}	
			break;
		case BluetoothCommand.BREATHE_STATUS:
			if(map.get(BluetoothCommand.BREATHE_STATUS)<BluetoothCommand.BREATHE_STATUS_VIGOR3){
				breathe.setBackgroundResource(R.drawable.btn_breathe);
				breathe_activited=false;
			}else{
				breathe.setBackgroundResource(R.drawable.btn_breathe_activated);
				breathe_activited=true;
			}	
			break;
		case BluetoothCommand.INIT_POSITION_STATUS:
			break;
			
		}
	}
	
}
