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

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;
import com.innovzen.utils.MyPreference;

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
              MyPreference.getInstance(this.getActivity()).writeString(MyPreference.TIME, MyPreference.FIVE_MINUTES);
   			myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
			break;
		case R.id.time_10min:
			 time_5min.setBackgroundResource(R.drawable.selector_time_5min);
             time_10min.setBackgroundResource(R.drawable.btn_10min_activated);
             time_15min.setBackgroundResource(R.drawable.selector_time_15min);
             time_20min.setBackgroundResource(R.drawable.selector_time_20min);
             time_25min.setBackgroundResource(R.drawable.selector_time_25min);
             time_30min.setBackgroundResource(R.drawable.selector_time_30min);
             MyPreference.getInstance(this.getActivity()).writeString(MyPreference.TIME, MyPreference.TEN_MINUTES);
             myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
			break;
		case R.id.time_15min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
            time_10min.setBackgroundResource(R.drawable.selector_time_10min);
            time_15min.setBackgroundResource(R.drawable.btn_15min_activated);
            time_20min.setBackgroundResource(R.drawable.selector_time_20min);
            time_25min.setBackgroundResource(R.drawable.selector_time_25min);
            time_30min.setBackgroundResource(R.drawable.selector_time_30min);
            MyPreference.getInstance(this.getActivity()).writeString(MyPreference.TIME, MyPreference.FIFTEEN_MINUTES);
            myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
			break;
		case R.id.time_20min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
            time_10min.setBackgroundResource(R.drawable.selector_time_10min);
            time_15min.setBackgroundResource(R.drawable.selector_time_15min);
            time_20min.setBackgroundResource(R.drawable.btn_20min_activated);
            time_25min.setBackgroundResource(R.drawable.selector_time_25min);
            time_30min.setBackgroundResource(R.drawable.selector_time_30min);
            MyPreference.getInstance(this.getActivity()).writeString(MyPreference.TIME, MyPreference.TWENTY_MINUTES);
            myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
			break;
		case R.id.time_25min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
            time_10min.setBackgroundResource(R.drawable.selector_time_10min);
            time_15min.setBackgroundResource(R.drawable.selector_time_15min);
            time_20min.setBackgroundResource(R.drawable.selector_time_20min);
            time_25min.setBackgroundResource(R.drawable.btn_25min_activated);
            time_30min.setBackgroundResource(R.drawable.selector_time_30min);
            MyPreference.getInstance(this.getActivity()).writeString(MyPreference.TIME, MyPreference.TWENTY_FIVE_MINUTES);
            myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
			break;
		case R.id.time_30min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
            time_10min.setBackgroundResource(R.drawable.selector_time_10min);
            time_15min.setBackgroundResource(R.drawable.selector_time_15min);
            time_20min.setBackgroundResource(R.drawable.selector_time_20min);
            time_25min.setBackgroundResource(R.drawable.selector_time_25min);
            time_30min.setBackgroundResource(R.drawable.btn_30min_activated);
            MyPreference.getInstance(this.getActivity()).writeString(MyPreference.TIME, MyPreference.THIRTY_MINUTES);
            myMinutes.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
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
		String myTime =	MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME);
		if(myTime==null||myTime.equals(MyPreference.FIVE_MINUTES)){
			time_5min.setBackgroundResource(R.drawable.btn_5min_activated);
		}else if(myTime.equals(MyPreference.TEN_MINUTES)){
			time_10min.setBackgroundResource(R.drawable.btn_10min_activated);
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
		}else if(myTime.equals(MyPreference.FIFTEEN_MINUTES)){
			time_15min.setBackgroundResource(R.drawable.btn_15min_activated);
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
		}else if(myTime.equals(MyPreference.TWENTY_MINUTES)){
			time_20min.setBackgroundResource(R.drawable.btn_20min_activated);
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
		}else if(myTime.equals(MyPreference.TWENTY_FIVE_MINUTES)){
			time_25min.setBackgroundResource(R.drawable.btn_25min_activated);
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
		}else if(myTime.equals(MyPreference.THIRTY_MINUTES)){
			time_30min.setBackgroundResource(R.drawable.btn_30min_activated);
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
		}
		/*myTime=	MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME);*/
		myMinutes.setText(myTime);
	}

}
