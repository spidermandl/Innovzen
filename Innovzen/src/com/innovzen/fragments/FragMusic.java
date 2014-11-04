package com.innovzen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;

public class FragMusic extends FragBase implements OnClickListener{
	private ImageView left_top,left_bottom;
	LinearLayout left_mid;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        init(view);

        return view;
    }
	@Override
	public void onClick(View v) {
		
		
	}

	@Override
	public void init(View view) {
		initLefter(view);
		getMyShareSharedPreferences("time");
		left_top = (ImageView) view.findViewById(R.id.left_top);
		left_mid = (LinearLayout) view.findViewById(R.id.left_mid);
		left_bottom = (ImageView) view.findViewById(R.id.left_bottom);
		left_top.setBackgroundResource(R.drawable.selector_btn_back);
		left_mid.setBackgroundResource(R.drawable.banner_music);
		left_bottom.setBackgroundResource(R.drawable.selector_btn_volume);		
	}

}
