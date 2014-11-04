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

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;

public class FragTime extends FragBase implements OnClickListener {
	//private String FILE = "saveTimeMin";// 用于保存SharedPreferences的文件
	private SharedPreferences sp = null;// 声明一个SharedPreferences存贮gtaphic图片样式
	private ImageView time_5min, time_10min, time_15min, time_20min,
			time_25min, time_30min;
	private LinearLayout left_mid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_time, container, false);
		sp = getActivity().getSharedPreferences(super.SAVE_TIME_MIN, Context.MODE_PRIVATE);
		init(view);

		return view;
	}

	@Override
	public void onClick(View v) {
		Editor editor = sp.edit();
		switch (v.getId()) {
		case R.id.time_5min:
               time_5min.setBackgroundResource(R.drawable.btn_5min_activated);
               time_10min.setBackgroundResource(R.drawable.selector_time_10min);
               time_15min.setBackgroundResource(R.drawable.selector_time_15min);
               time_20min.setBackgroundResource(R.drawable.selector_time_20min);
               time_25min.setBackgroundResource(R.drawable.selector_time_25min);
               time_30min.setBackgroundResource(R.drawable.selector_time_30min);
               editor.putString("time","5min");
   			editor.commit();
   			getMyShareSharedPreferences(super.SHARED_TIME);
			break;
		case R.id.time_10min:
			 time_5min.setBackgroundResource(R.drawable.selector_time_5min);
             time_10min.setBackgroundResource(R.drawable.btn_10min_activated);
             time_15min.setBackgroundResource(R.drawable.selector_time_15min);
             time_20min.setBackgroundResource(R.drawable.selector_time_20min);
             time_25min.setBackgroundResource(R.drawable.selector_time_25min);
             time_30min.setBackgroundResource(R.drawable.selector_time_30min);
             editor.putString("time","10min");
 			editor.commit();
 			getMyShareSharedPreferences(super.SHARED_TIME);
			break;
		case R.id.time_15min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
            time_10min.setBackgroundResource(R.drawable.selector_time_10min);
            time_15min.setBackgroundResource(R.drawable.btn_15min_activated);
            time_20min.setBackgroundResource(R.drawable.selector_time_20min);
            time_25min.setBackgroundResource(R.drawable.selector_time_25min);
            time_30min.setBackgroundResource(R.drawable.selector_time_30min);
            editor.putString("time","15min");
			editor.commit();
			getMyShareSharedPreferences("time");
			break;
		case R.id.time_20min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
            time_10min.setBackgroundResource(R.drawable.selector_time_10min);
            time_15min.setBackgroundResource(R.drawable.selector_time_15min);
            time_20min.setBackgroundResource(R.drawable.btn_20min_activated);
            time_25min.setBackgroundResource(R.drawable.selector_time_25min);
            time_30min.setBackgroundResource(R.drawable.selector_time_30min);
            editor.putString("time","20min");
			editor.commit();
			getMyShareSharedPreferences("time");
			break;
		case R.id.time_25min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
            time_10min.setBackgroundResource(R.drawable.selector_time_10min);
            time_15min.setBackgroundResource(R.drawable.selector_time_15min);
            time_20min.setBackgroundResource(R.drawable.selector_time_20min);
            time_25min.setBackgroundResource(R.drawable.btn_25min_activated);
            time_30min.setBackgroundResource(R.drawable.selector_time_30min);
            editor.putString("time","25min");
			editor.commit();
			getMyShareSharedPreferences("time");
			break;
		case R.id.time_30min:
			time_5min.setBackgroundResource(R.drawable.selector_time_5min);
            time_10min.setBackgroundResource(R.drawable.selector_time_10min);
            time_15min.setBackgroundResource(R.drawable.selector_time_15min);
            time_20min.setBackgroundResource(R.drawable.selector_time_20min);
            time_25min.setBackgroundResource(R.drawable.selector_time_25min);
            time_30min.setBackgroundResource(R.drawable.btn_30min_activated);
            editor.putString("time","30min");
			editor.commit();
			getMyShareSharedPreferences("time");
			break;
		default:
			break;
		}
	}

	@Override
	public void init(View view) {
		initLefter(view);
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
		String myTime =sp.getString("time","");
		if(myTime==null||myTime.equals("5min")){
			time_5min.setBackgroundResource(R.drawable.btn_5min_activated);
		}else if(myTime.equals("10min")){
			time_10min.setBackgroundResource(R.drawable.btn_10min_activated);
		}else if(myTime.equals("15min")){
			time_15min.setBackgroundResource(R.drawable.btn_15min_activated);
		}else if(myTime.equals("20min")){
			time_20min.setBackgroundResource(R.drawable.btn_20min_activated);
		}else if(myTime.equals("25min")){
			time_25min.setBackgroundResource(R.drawable.btn_25min_activated);
		}else if(myTime.equals("30min")){
			time_30min.setBackgroundResource(R.drawable.btn_30min_activated);
		}
		getMyShareSharedPreferences("time");
	}

}
