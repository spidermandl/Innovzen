package com.innovzen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;

public class FragmentMenu2 extends FragBase implements OnClickListener{
	/**
	 * @author chy
	 */
	// 首页按钮
	private LinearLayout menu_balance, menu_relax, menu_performance,
			menu_session;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_application_button,
				container, false);

		init(view);

		return view;
	}

	@Override
	public void init(View view) {
		menu_balance = (LinearLayout) view.findViewById(R.id.main_menu_balance);
		menu_relax = (LinearLayout) view.findViewById(R.id.main_menu_relax);
		menu_performance = (LinearLayout) view
				.findViewById(R.id.main_menu_performance);
		menu_session = (LinearLayout) view.findViewById(R.id.main_menu_session);
		//监听以下按钮
		menu_balance.setOnClickListener(this);
		menu_relax.setOnClickListener(this);
		menu_performance.setOnClickListener(this);
		menu_session.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_menu_balance:
			Toast.makeText(getActivity(), "yoooooooooooooo", 0).show();
			super.activityListener.fragGoToBalance(true);
			break;
		case R.id.main_menu_relax:
			break;
		case R.id.main_menu_performance:
			break;
		case R.id.main_menu_session:
			break;
		default:
			break;
		}
		
	}

}
