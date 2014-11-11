package com.innovzen.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;
import com.innovzen.utils.MyPreference;

/**
 * 第一个主界面，启动机器，进入练习
 * @author Desmond Duan
 *
 */
public class FragMain extends FragBase implements OnClickListener{
	 private TextView myMintues;

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
			 MyPreference.getInstance(this.getActivity()).writeString(MyPreference.TIME, MyPreference.FIVE_MINUTES);
			 MyPreference.getInstance(this.getActivity()).writeString(MyPreference.BLANCE_RELAX_PERFORMANCE, MyPreference.BLANCE);
			super.activityListener.fragGoToAnimation(true);
			
			break;
		case R.id.menu_relax:
			 MyPreference.getInstance(this.getActivity()).writeString(MyPreference.BLANCE_RELAX_PERFORMANCE, MyPreference.RELAX);

			MyPreference.getInstance(this.getActivity()).writeString(MyPreference.TIME, MyPreference.TEN_MINUTES);
			super.activityListener.fragGoToAnimation(true);
			
			break;
		case R.id.menu_performance:
			 MyPreference.getInstance(this.getActivity()).writeString(MyPreference.BLANCE_RELAX_PERFORMANCE, MyPreference.PERFORMANCE);

			MyPreference.getInstance(this.getActivity()).writeString(MyPreference.TIME, MyPreference.FIFTEEN_MINUTES);
			super.activityListener.fragGoToAnimation(true);
			
			break;
		case R.id.menu_mySession:
			super.activityListener.fragGoToSession(true);
			break;
		case R.id.left_top:
			super.activityListener.fragConnectBluetooth();
			break;
		case R.id.left_bottom:
			super.activityListener.fragGoToHelpNew(true);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void init(View view) {
	myMintues = (TextView) view.findViewById(R.id.myMinutes);
		
		view.findViewById(R.id.menu_balance).setOnClickListener(this);
		view.findViewById(R.id.menu_mySession).setOnClickListener(this);
		view.findViewById(R.id.menu_performance).setOnClickListener(this);
		view.findViewById(R.id.menu_relax).setOnClickListener(this);
	    initLefter(view);
	    myMintues.setText(MyPreference.getInstance(this.getActivity()).readString(MyPreference.TIME));
		leftTop.setOnClickListener(this);
		leftMid.setOnClickListener(this);
		leftBottom.setOnClickListener(this);
		leftTop.setBackgroundResource(R.drawable.selector_btn_home);
		leftMid.setBackgroundResource(R.drawable.banner_choose_exercise);
		leftBottom.setBackgroundResource(R.drawable.selector_btn_help);
	}

}
