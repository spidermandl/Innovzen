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
import android.widget.TextView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;
import com.innovzen.utils.MyPreference;

/**
 * …˘“ÙΩÁ√Êøÿ÷∆
 * 
 * @author chy
 * 
 */
public class FragVoice extends FragBase implements OnClickListener {
	private ImageView man_voice, woman_voice, silence_voice;
	private LinearLayout left_mid;
	private TextView myMinutes;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_voice, container, false);
		init(view);
		return view;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.man_voice:
			man_voice.setBackgroundResource(R.drawable.btn_man_voice_activated);
			woman_voice.setBackgroundResource(R.drawable.selector_voice_woman);
			silence_voice
					.setBackgroundResource(R.drawable.selector_voice_silence);
			MyPreference.getInstance(this.getActivity()).writeString(
					MyPreference.VOICE, MyPreference.MAN_VOICE);
			break;
		case R.id.woman_voice:
			man_voice.setBackgroundResource(R.drawable.selector_voice_man);
			woman_voice
					.setBackgroundResource(R.drawable.btn_woman_voice_activated);
			silence_voice
					.setBackgroundResource(R.drawable.selector_voice_silence);
			MyPreference.getInstance(this.getActivity()).writeString(
					MyPreference.VOICE, MyPreference.WOMAN_VOICE);

			break;
		case R.id.silence_voice:
			man_voice.setBackgroundResource(R.drawable.selector_voice_man);
			woman_voice.setBackgroundResource(R.drawable.selector_voice_woman);
			silence_voice
					.setBackgroundResource(R.drawable.btn_silence_voice_activated);
			MyPreference.getInstance(this.getActivity()).writeString(
					MyPreference.VOICE, MyPreference.SILENCE);

			break;
		default:
			break;
		}

	}

	@Override
	public void init(View view) {
		initLefter(view);
		myMinutes = (TextView) view.findViewById(R.id.myMinutes);
		myMinutes.setText(MyPreference.getInstance(this.getActivity())
				.readString(MyPreference.TIME));

		left_mid = (LinearLayout) view.findViewById(R.id.left_mid);
		left_mid.setBackgroundResource(R.drawable.banner_voice);
		man_voice = (ImageView) view.findViewById(R.id.man_voice);
		woman_voice = (ImageView) view.findViewById(R.id.woman_voice);
		silence_voice = (ImageView) view.findViewById(R.id.silence_voice);
		LinearLayout left_mid = (LinearLayout) view.findViewById(R.id.left_mid);
		left_mid.setBackgroundResource(R.drawable.banner_music);
		man_voice.setOnClickListener(this);
		woman_voice.setOnClickListener(this);
		silence_voice.setOnClickListener(this);
		man_voice.setBackgroundResource(R.drawable.btn_man_voice_activated);
		String myVoice = MyPreference.getInstance(this.getActivity())
				.readString(MyPreference.VOICE);
		if (myVoice == null || myVoice.equals("man")) {
			man_voice.setBackgroundResource(R.drawable.btn_man_voice_activated);
		} else if (myVoice.equals("woman")) {
			man_voice.setBackgroundResource(R.drawable.selector_voice_man);
			man_voice
					.setBackgroundResource(R.drawable.btn_woman_voice_activated);
		} else if (myVoice.equals("silence")) {
			man_voice.setBackgroundResource(R.drawable.selector_voice_man);
			silence_voice
					.setBackgroundResource(R.drawable.btn_silence_voice_activated);
		}
	}

}
