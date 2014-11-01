package com.innovzen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;

public class FragBalance extends FragBase implements OnClickListener{

	private LinearLayout settings;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance_exercise, container, false);

        init(view);

        return view;
    }
	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.Settings:
		super.activityListener.fragGoToSetting(true);
		break;

	default:
		break;
	}
		
	}

	@Override
	public void init(View view) {
		settings = (LinearLayout) view.findViewById(R.id.Settings);
		settings.setOnClickListener(this);
	}

}
