package com.innovzen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;

public class FragSettings extends FragBase implements OnClickListener{
	private ImageView left_top,left_mid,left_bottom;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_setting,
				container, false);

		init(view);

		return view;
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
		default:
			break;
		}
		
	}

	@Override
	public void init(View view) {
		view.findViewById(R.id.set_language).setOnClickListener(this);
		view.findViewById(R.id.set_music).setOnClickListener(this);
		view.findViewById(R.id.set_time).setOnClickListener(this);
		view.findViewById(R.id.set_graphic).setOnClickListener(this);
		view.findViewById(R.id.set_voice).setOnClickListener(this);
		left_top = (ImageView) view.findViewById(R.id.left_top);
		left_mid = (ImageView) view.findViewById(R.id.left_mid);
		left_bottom = (ImageView) view.findViewById(R.id.left_bottom);
		left_top.setBackgroundResource(R.drawable.selector_btn_back);
		left_mid.setBackgroundResource(R.drawable.banner_settings);
		left_bottom.setBackgroundResource(R.drawable.selector_btn_volume);
	}

}
