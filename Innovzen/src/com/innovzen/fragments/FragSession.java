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

public class FragSession extends FragBase implements OnClickListener {


	private ImageView back;
	private ImageView setting;
	private ImageView help;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mysession, container, false);
		init(view);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.session_back:
			getActivity().onBackPressed();
			break;
		case R.id.session_setting:
			super.activityListener.fragGoToSetting(true);
			break;
		case R.id.session_help:
			super.activityListener.fragGoToHelpNew(true);
			break;
		default:
			break;
		}
	}

	@Override
	public void init(View view) {
		back = (ImageView) view.findViewById(R.id.session_back);
		back.setOnClickListener(this);
		setting = (ImageView) view.findViewById(R.id.session_setting);
		setting.setOnClickListener(this);
		help = (ImageView) view.findViewById(R.id.session_help);
		help.setOnClickListener(this);
	}

}
