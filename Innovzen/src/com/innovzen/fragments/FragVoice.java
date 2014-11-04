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

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;

/**
 * …˘“ÙΩÁ√Êøÿ÷∆
 * 
 * @author chy
 * 
 */
public class FragVoice extends FragBase implements OnClickListener {
	private ImageView man_voice, woman_voice, silence_voice;
	private String FILE = "saveVoice";
	private SharedPreferences sp = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_voice, container, false);
		sp = getActivity().getSharedPreferences(FILE, Context.MODE_PRIVATE);
		init(view);

		return view;
	}

	@Override
	public void onClick(View v) {
		Editor editor = sp.edit();
		switch (v.getId()) {
		case R.id.man_voice:
              man_voice.setBackgroundResource(R.drawable.btn_man_voice_activated);
              woman_voice.setBackgroundResource(R.drawable.selector_voice_woman);
              silence_voice.setBackgroundResource(R.drawable.selector_voice_silence);
          	editor.putString("voice","man");
			editor.commit();
			break;
		case R.id.woman_voice:
			man_voice.setBackgroundResource(R.drawable.selector_voice_man);
            woman_voice.setBackgroundResource(R.drawable.btn_woman_voice_activated);
            silence_voice.setBackgroundResource(R.drawable.selector_voice_silence);
        	editor.putString("voice","woman");
			editor.commit();
			break;
		case R.id.silence_voice:
			man_voice.setBackgroundResource(R.drawable.selector_voice_man);
            woman_voice.setBackgroundResource(R.drawable.selector_voice_woman);
            silence_voice.setBackgroundResource(R.drawable.btn_silence_voice_activated);
        	editor.putString("voice","silence");
			editor.commit();
			break;
		default:
			break;
		}

	}

	@Override
	public void init(View view) {
		initLefter(view);
		getMyShareSharedPreferences("time");
		man_voice = (ImageView) view.findViewById(R.id.man_voice);
		woman_voice = (ImageView) view.findViewById(R.id.woman_voice);
		silence_voice = (ImageView) view.findViewById(R.id.silence_voice);
		man_voice.setOnClickListener(this);
		woman_voice.setOnClickListener(this);
		silence_voice.setOnClickListener(this);
		String myVoice =sp.getString("voice","");
		if(myVoice==null||myVoice.equals("man")){
			man_voice.setBackgroundResource(R.drawable.btn_man_voice_activated);
		}else if(myVoice.equals("woman")){
			man_voice.setBackgroundResource(R.drawable.btn_woman_voice_activated);
		}else if(myVoice.equals("silence")){
			silence_voice.setBackgroundResource(R.drawable.btn_silence_voice_activated);
		}
	}

}
