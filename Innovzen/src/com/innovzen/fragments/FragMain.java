package com.innovzen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;

/**
 * 第一个主界面，启动机器，进入练习
 * @author Desmond Duan
 *
 */
public class FragMain extends FragBase implements OnClickListener{

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.fragment_user_application_button, container, false);

	        init(view);

	        return view;
	    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_balance:
			//test
			//super.activityListener.fragGoToSetting(true);
			super.activityListener.fragGoToAnimation(true);
			break;
		case R.id.menu_relax:
			super.activityListener.fragGoToAnimation(true);
			break;
		case R.id.menu_performance:
			super.activityListener.fragGoToAnimation(true);
			break;
		case R.id.left_top:
			super.activityListener.fragConnectBluetooth();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void init(View view) {
		view.findViewById(R.id.menu_balance).setOnClickListener(this);
		view.findViewById(R.id.menu_mySession).setOnClickListener(this);
		view.findViewById(R.id.menu_performance).setOnClickListener(this);
		view.findViewById(R.id.menu_relax).setOnClickListener(this);

	    initLefter(view);
		leftTop.setOnClickListener(this);
		leftMid.setOnClickListener(this);
		leftBottom.setOnClickListener(this);
		leftTop.setBackgroundResource(R.drawable.selector_btn_home);
		leftMid.setBackgroundResource(R.drawable.banner_choose_exercise);
	}

}
