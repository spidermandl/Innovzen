package com.innovzen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;
import com.innovzen.utils.MyPreference;

public class FragLanguage extends FragBase implements OnClickListener {

	private LinearLayout left_mid;
	private TextView myMinutes;
	private ImageView language_American;
	private ImageView language_German;
	private ImageView language_French;
	private ImageView language_Spanish;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_language, container,
				false);

		init(view);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.language_American:
			language_American.setBackgroundResource(R.drawable.btn_american_activated);
			language_German.setBackgroundResource(R.drawable.selector_icon_german);
			language_French.setBackgroundResource(R.drawable.selector_icon_french);
			language_Spanish.setBackgroundResource(R.drawable.selector_icon_spanish);
			MyPreference.getInstance(getActivity()).writeString(MyPreference.LANGUAGE, MyPreference.ENGLISH);
			break;
		case R.id.language_German:
			language_American.setBackgroundResource(R.drawable.selector_icon_american);
			language_German.setBackgroundResource(R.drawable.btn_german_activated);
			language_French.setBackgroundResource(R.drawable.selector_icon_french);
			language_Spanish.setBackgroundResource(R.drawable.selector_icon_spanish);
			MyPreference.getInstance(getActivity()).writeString(MyPreference.LANGUAGE, MyPreference.GERMAN);
			break;
		case R.id.language_French:
			language_American.setBackgroundResource(R.drawable.selector_icon_american);
			language_German.setBackgroundResource(R.drawable.selector_icon_german);
			language_French.setBackgroundResource(R.drawable.btn_french_activated);
			language_Spanish.setBackgroundResource(R.drawable.selector_icon_spanish);
			MyPreference.getInstance(getActivity()).writeString(MyPreference.LANGUAGE, MyPreference.FRENCH);
			break;
		case R.id.language_Spanish:
			language_American.setBackgroundResource(R.drawable.selector_icon_american);
			language_German.setBackgroundResource(R.drawable.selector_icon_german);
			language_French.setBackgroundResource(R.drawable.selector_icon_french);
			language_Spanish.setBackgroundResource(R.drawable.btn_spanish_activated);
			MyPreference.getInstance(getActivity()).writeString(MyPreference.LANGUAGE, MyPreference.SPANISH);
			break;
		}
	}

	@Override
	public void init(View view) {

		initLefter(view);
		language_American = (ImageView) view
				.findViewById(R.id.language_American);
		language_American.setOnClickListener(this);
		language_German = (ImageView) view.findViewById(R.id.language_German);
		language_German.setOnClickListener(this);
		language_French = (ImageView) view.findViewById(R.id.language_French);
		language_French.setOnClickListener(this);
		language_Spanish = (ImageView) view.findViewById(R.id.language_Spanish);
		language_Spanish.setOnClickListener(this);
		myMinutes = (TextView) view.findViewById(R.id.myMinutes);
		myMinutes.setText(MyPreference.getInstance(this.getActivity()).readInt(
				MyPreference.TIME)
				/ 60000 + MyPreference.MINS);

		left_mid = (LinearLayout) view.findViewById(R.id.left_mid);
		left_mid.setBackgroundResource(R.drawable.banner_language);
		String myLanguage = MyPreference.getInstance(getActivity()).readString(MyPreference.LANGUAGE);
		if(myLanguage.equals(MyPreference.ENGLISH)||myLanguage.equals("")){
			language_American.setBackgroundResource(R.drawable.btn_american_activated);
		}else if(myLanguage.equals(MyPreference.GERMAN)){
			language_American.setBackgroundResource(R.drawable.selector_icon_american);
			language_German.setBackgroundResource(R.drawable.btn_german_activated);;
		}else if(myLanguage.equals(MyPreference.FRENCH)){
			language_American.setBackgroundResource(R.drawable.selector_icon_american);
			language_French.setBackgroundResource(R.drawable.btn_french_activated);
		}else if(myLanguage.equals(MyPreference.SPANISH)){
			language_American.setBackgroundResource(R.drawable.selector_icon_american);
			language_Spanish.setBackgroundResource(R.drawable.btn_spanish_activated);
		}

	}

}
