package com.innovzen.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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
import com.innovzen.utils.PersistentUtil;

public class FragTime extends FragBase implements OnClickListener {
	private ImageView time_5min, time_10min, time_15min, time_20min,
			time_25min, time_30min;
	private LinearLayout left_mid;
	private TextView myMinutes;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_time, container, false);
		init(view);

		return view;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.time_5min:
			time_5min.setBackgroundResource(R.drawable.btn_5min_activated);
			time_10min.setBackgroundResource(R.drawable.selector_time_10min);
			time_15min.setBackgroundResource(R.drawable.selector_time_15min);
			time_20min.setBackgroundResource(R.drawable.selector_time_20min);
			time_25min.setBackgroundResource(R.drawable.selector_time_25min);
			time_30min.setBackgroundResource(R.drawable.selector_time_30min);
			PersistentUtil.setInt(getActivity(), 5 * 60000,FragAnimationBase.PERSIST_TOTAL_SELECTED_EXERCISE_DURATION);
			MyPreference.getInstance(this.getActivity()).writeInt(MyPreference.TIME, 5*60*1000);
			myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
			super.activityListener.fragSendCommand(BluetoothCommand.TIME5_MACHINE_VALUES);
			break;
		case R.id.time_10min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
			time_10min.setBackgroundResource(R.drawable.btn_10min_activated);
			time_15min.setBackgroundResource(R.drawable.selector_time_15min);
			time_20min.setBackgroundResource(R.drawable.selector_time_20min);
			time_25min.setBackgroundResource(R.drawable.selector_time_25min);
			time_30min.setBackgroundResource(R.drawable.selector_time_30min);
			PersistentUtil.setInt(getActivity(), 10 * 60000,FragAnimationBase.PERSIST_TOTAL_SELECTED_EXERCISE_DURATION);
			MyPreference.getInstance(this.getActivity()).writeInt(MyPreference.TIME, 5*60*1000);
			myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
			super.activityListener.fragSendCommand(BluetoothCommand.TIME10_MACHINE_VALUES);
			break;
		case R.id.time_15min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
			time_10min.setBackgroundResource(R.drawable.selector_time_10min);
			time_15min.setBackgroundResource(R.drawable.btn_15min_activated);
			time_20min.setBackgroundResource(R.drawable.selector_time_20min);
			time_25min.setBackgroundResource(R.drawable.selector_time_25min);
			time_30min.setBackgroundResource(R.drawable.selector_time_30min);
			PersistentUtil.setInt(getActivity(), 15 * 60000,FragAnimationBase.PERSIST_TOTAL_SELECTED_EXERCISE_DURATION);
			MyPreference.getInstance(this.getActivity()).writeInt(MyPreference.TIME, 5*60*1000);
			myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
			super.activityListener.fragSendCommand(BluetoothCommand.TIME15_MACHINE_VALUES);
			break;
		case R.id.time_20min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
			time_10min.setBackgroundResource(R.drawable.selector_time_10min);
			time_15min.setBackgroundResource(R.drawable.selector_time_15min);
			time_20min.setBackgroundResource(R.drawable.btn_20min_activated);
			time_25min.setBackgroundResource(R.drawable.selector_time_25min);
			time_30min.setBackgroundResource(R.drawable.selector_time_30min);
			PersistentUtil.setInt(getActivity(), 20 * 60000,FragAnimationBase.PERSIST_TOTAL_SELECTED_EXERCISE_DURATION);
			MyPreference.getInstance(this.getActivity()).writeInt(MyPreference.TIME, 5*60*1000);
			myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
			super.activityListener.fragSendCommand(BluetoothCommand.TIME20_MACHINE_VALUES);
			break;
		case R.id.time_25min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
			time_10min.setBackgroundResource(R.drawable.selector_time_10min);
			time_15min.setBackgroundResource(R.drawable.selector_time_15min);
			time_20min.setBackgroundResource(R.drawable.selector_time_20min);
			time_25min.setBackgroundResource(R.drawable.btn_25min_activated);
			time_30min.setBackgroundResource(R.drawable.selector_time_30min);
			PersistentUtil.setInt(getActivity(), 25 * 60000,FragAnimationBase.PERSIST_TOTAL_SELECTED_EXERCISE_DURATION);
			MyPreference.getInstance(this.getActivity()).writeInt(MyPreference.TIME, 5*60*1000);
			myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
			super.activityListener.fragSendCommand(BluetoothCommand.TIME25_MACHINE_VALUES);
			break;
		case R.id.time_30min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
			time_10min.setBackgroundResource(R.drawable.selector_time_10min);
			time_15min.setBackgroundResource(R.drawable.selector_time_15min);
			time_20min.setBackgroundResource(R.drawable.selector_time_20min);
			time_25min.setBackgroundResource(R.drawable.selector_time_25min);
			time_30min.setBackgroundResource(R.drawable.btn_30min_activated);
			PersistentUtil.setInt(getActivity(), 30 * 60000,FragAnimationBase.PERSIST_TOTAL_SELECTED_EXERCISE_DURATION);
			MyPreference.getInstance(this.getActivity()).writeInt(MyPreference.TIME, 5*60*1000);
			myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
			super.activityListener.fragSendCommand(BluetoothCommand.TIME30_MACHINE_VALUES);
			break;
		default:
			break;
		}
	}

	@Override
	public void init(View view) {
		initLefter(view);
		myMinutes = (TextView) view.findViewById(R.id.myMinutes);
		left_mid = (LinearLayout) view.findViewById(R.id.left_mid);
		left_mid.setBackgroundResource(R.drawable.banner_time);
		time_5min = (ImageView) view.findViewById(R.id.time_5min);
		time_5min.setOnClickListener(this);
		time_10min = (ImageView) view.findViewById(R.id.time_10min);
		time_10min.setOnClickListener(this);
		time_15min = (ImageView) view.findViewById(R.id.time_15min);
		time_15min.setOnClickListener(this);
		time_20min = (ImageView) view.findViewById(R.id.time_20min);
		time_20min.setOnClickListener(this);
		time_25min = (ImageView) view.findViewById(R.id.time_25min);
		time_25min.setOnClickListener(this);
		time_30min = (ImageView) view.findViewById(R.id.time_30min);
		time_30min.setOnClickListener(this);
		time_5min.setBackgroundResource(R.drawable.btn_5min_activated);
		int myTime = MyPreference.getInstance(this.getActivity()).readInt(MyPreference.TIME);
		switch (myTime) {
		case 5*60*1000:
			time_5min.setBackgroundResource(R.drawable.btn_5min_activated);
			break;
		case 10*60*1000:
			time_10min.setBackgroundResource(R.drawable.btn_10min_activated);
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
			break;
		case 15*60*1000:
			time_15min.setBackgroundResource(R.drawable.btn_15min_activated);
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
			break;
		case 20*60*1000:
			time_20min.setBackgroundResource(R.drawable.btn_20min_activated);
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
			break;
		case 25*60*1000:
			time_25min.setBackgroundResource(R.drawable.btn_25min_activated);
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
			break;
		case 30*60*1000:
			time_30min.setBackgroundResource(R.drawable.btn_30min_activated);
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
			break;
		default:
			break;
		}
		myMinutes.setText(myTime+"");
	}

}
