package com.innovzen.fragments;

import adapters.AdapterSound;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;
import com.innovzen.utils.MyPreference;
import com.innovzen.utils.PersistentUtil;

/**
 * 声音界面控制
 * 
 * @author chy
 * 
 */
public class FragVoice extends FragSoundPicker implements OnClickListener {
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
         
            //男
            PersistentUtil.setInt(getActivity(), 0, PERSIST_SELECTED_VOICE);
         
			
			
			break;
		case R.id.woman_voice:
			
			
			man_voice.setBackgroundResource(R.drawable.selector_voice_man);
			woman_voice
					.setBackgroundResource(R.drawable.btn_woman_voice_activated);
			silence_voice
					.setBackgroundResource(R.drawable.selector_voice_silence);
			MyPreference.getInstance(this.getActivity()).writeString(
					MyPreference.VOICE, MyPreference.WOMAN_VOICE);

			
			 //女
            PersistentUtil.setInt(getActivity(), 1, PERSIST_SELECTED_VOICE);
			
			
			
			break;
		case R.id.silence_voice:
			
			
			man_voice.setBackgroundResource(R.drawable.selector_voice_man);
			woman_voice.setBackgroundResource(R.drawable.selector_voice_woman);
			silence_voice
					.setBackgroundResource(R.drawable.btn_silence_voice_activated);
			MyPreference.getInstance(this.getActivity()).writeString(
					MyPreference.VOICE, MyPreference.SILENCE);
			
			  //静音
			PersistentUtil.setInt(getActivity(), 615313351, PERSIST_SELECTED_VOICE);
			
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
		if (myVoice == null || myVoice.equals(MyPreference.MAN_VOICE)) {
			man_voice.setBackgroundResource(R.drawable.btn_man_voice_activated);
			woman_voice.setBackgroundResource(R.drawable.selector_voice_woman);
			silence_voice
					.setBackgroundResource(R.drawable.selector_voice_silence);
		} else if (myVoice.equals(MyPreference.WOMAN_VOICE)) {
			man_voice.setBackgroundResource(R.drawable.selector_voice_man);
			woman_voice
					.setBackgroundResource(R.drawable.btn_woman_voice_activated);
			silence_voice
					.setBackgroundResource(R.drawable.selector_voice_silence);
		} else if (myVoice.equals(MyPreference.SILENCE)) {
			man_voice.setBackgroundResource(R.drawable.selector_voice_man);
			woman_voice.setBackgroundResource(R.drawable.selector_voice_woman);
			silence_voice
					.setBackgroundResource(R.drawable.btn_silence_voice_activated);
		}

	}
	
	
	
	
	
	/*private void asd() {
		// TODO Auto-generated method stub

		   PersistentUtil.setInt(getActivity(), mAdapterVoices.getSelectedSoundId(), PERSIST_SELECTED_VOICE);
           PersistentUtil.setInt(getActivity(), mAdapterAmbiance.getSelectedSoundId(), PERSIST_SELECTED_AMBIANCE);
		
		
           int selectedVoiceSoundId = PersistentUtil.getInt(getActivity(), PERSIST_SELECTED_VOICE);
           if (selectedVoiceSoundId == -1) {
               selectedVoiceSoundId = mAdapterVoices.getFirstSoundId();
               PersistentUtil.setInt(getActivity(), selectedVoiceSoundId, PERSIST_SELECTED_VOICE);
           }
           // Ambiance
           int selectedAmbianceSoundId = PersistentUtil.getInt(getActivity(), PERSIST_SELECTED_AMBIANCE);
           if (selectedAmbianceSoundId == -1) {
               selectedAmbianceSoundId = mAdapterAmbiance.getSecondSoundId(); // Default one is Aquatic SunBeam
               PersistentUtil.setInt(getActivity(), selectedAmbianceSoundId, PERSIST_SELECTED_AMBIANCE);
           }
           
           */
		
		
		
		
		
		
	//}

}
