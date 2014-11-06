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


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mysession, container, false);
		init(view);

		return view;
	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void init(View view) {
		
	}

}
