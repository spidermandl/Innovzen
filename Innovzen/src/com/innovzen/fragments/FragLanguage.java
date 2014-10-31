package com.innovzen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;

public class FragLanguage extends FragBase implements OnClickListener{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        init(view);

        return view;
    }
	@Override
	public void onClick(View v) {
		
		
	}

	@Override
	public void init(View view) {
		// TODO Auto-generated method stub
		
	}

}
