package com.innovzen.fragments;

import adapters.AdapterSound;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innovzen.entities.SoundItem;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;
import com.innovzen.utils.MyPreference;
import com.innovzen.utils.PersistentUtil;

public class FragVoice extends FragBase implements OnClickListener {

	private ImageView man_voice, woman_voice, silence_voice;
	// Hold shared pref keys
	public static final String PERSIST_SELECTED_VOICE = "selected_voice";

	private AdapterSound mAdapterVoices;
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

			// Mark it as selected
			int soundId = mAdapterVoices.markAsSelected(1);

			// Stop the ambiance player (in case it's running)
			super.activityListener.fragStopAmbiancePlayer();

			// If the sound is a silence one, then don't play anything
			if (soundId != AdapterSound.SILENCE_ID) {
				// Play the INSPIREZ sound
				super.activityListener.fragPlayVoice(soundId,
						SoundItem.INSPIREZ);
			} else {
				super.activityListener.fragStopVoicePlayer();
			}

			man_voice.setBackgroundResource(R.drawable.btn_man_voice_activated);
			woman_voice.setBackgroundResource(R.drawable.selector_voice_woman);
			silence_voice
					.setBackgroundResource(R.drawable.selector_voice_silence);
			MyPreference.getInstance(getActivity()).writeString(
					MyPreference.VOICE, MyPreference.MAN_VOICE);
			PersistentUtil
					.setInt(getActivity(), mAdapterVoices.getSelectedSoundId(),
							PERSIST_SELECTED_VOICE);

			break;
		case R.id.woman_voice:

			// Mark it as selected
			int soundId1 = mAdapterVoices.markAsSelected(2);

			// Stop the ambiance player (in case it's running)
			super.activityListener.fragStopAmbiancePlayer();

			// If the sound is a silence one, then don't play anything
			if (soundId1 != AdapterSound.SILENCE_ID) {
				// Play the INSPIREZ sound
				super.activityListener.fragPlayVoice(soundId1,
						SoundItem.INSPIREZ);
			} else {
				super.activityListener.fragStopVoicePlayer();
			}

			man_voice.setBackgroundResource(R.drawable.selector_voice_man);
			woman_voice
					.setBackgroundResource(R.drawable.btn_woman_voice_activated);
			silence_voice
					.setBackgroundResource(R.drawable.selector_voice_silence);
			MyPreference.getInstance(getActivity()).writeString(
					MyPreference.VOICE, MyPreference.WOMAN_VOICE);
			PersistentUtil
					.setInt(getActivity(), mAdapterVoices.getSelectedSoundId(),
							PERSIST_SELECTED_VOICE);
			break;

		case R.id.silence_voice:

			int soundId2 = mAdapterVoices.markAsSelected(0);

			// Stop the ambiance player (in case it's running)
			super.activityListener.fragStopAmbiancePlayer();

			// If the sound is a silence one, then don't play anything
			if (soundId2 != AdapterSound.SILENCE_ID) {
				// Play the INSPIREZ sound
				super.activityListener.fragPlayVoice(soundId2,
						SoundItem.INSPIREZ);
			} else {
				super.activityListener.fragStopVoicePlayer();
			}

			man_voice.setBackgroundResource(R.drawable.selector_voice_man);
			woman_voice.setBackgroundResource(R.drawable.selector_voice_woman);
			silence_voice
					.setBackgroundResource(R.drawable.btn_silence_voice_activated);
			MyPreference.getInstance(getActivity()).writeString(
					MyPreference.VOICE, MyPreference.SILENCE);
			PersistentUtil
					.setInt(getActivity(), mAdapterVoices.getSelectedSoundId(),
							PERSIST_SELECTED_VOICE);
			break;

		default:
			break;
		}

	}

	@Override
	public void init(View view) {
		initLefter(view);
		man_voice = (ImageView) view.findViewById(R.id.man_voice);
		woman_voice = (ImageView) view.findViewById(R.id.woman_voice);
		silence_voice = (ImageView) view.findViewById(R.id.silence_voice);
		LinearLayout left_mid =(LinearLayout) view.findViewById(R.id.left_mid);
		
		left_mid.setBackgroundResource(R.drawable.banner_voice);
		myMinutes = (TextView) view.findViewById(R.id.myMinutes);
		man_voice.setOnClickListener(this);
		woman_voice.setOnClickListener(this);
		silence_voice.setOnClickListener(this);
		myMinutes.setText(MyPreference.getInstance(this.getActivity())
				.readString(MyPreference.TIME));
		// Voice
		int selectedVoiceSoundId = PersistentUtil.getInt(getActivity(),
				PERSIST_SELECTED_VOICE);
		if (selectedVoiceSoundId == -1) {
			selectedVoiceSoundId = mAdapterVoices.getFirstSoundId();
			PersistentUtil.setInt(getActivity(), selectedVoiceSoundId,
					PERSIST_SELECTED_VOICE);
		}
		mAdapterVoices = new AdapterSound(getActivity(),
				super.activityListener.fragGetVoices(), selectedVoiceSoundId);

		String myVoice = MyPreference.getInstance(getActivity()).readString(
				MyPreference.VOICE);
		if (myVoice.equals(MyPreference.MAN_VOICE) || myVoice == null) {
			man_voice.setBackgroundResource(R.drawable.btn_man_voice_activated);
		} else if (myVoice.equals(MyPreference.WOMAN_VOICE)) {
			woman_voice
					.setBackgroundResource(R.drawable.btn_woman_voice_activated);

		} else if (myVoice.equals(MyPreference.SILENCE)) {
			silence_voice
					.setBackgroundResource(R.drawable.btn_silence_voice_activated);
		}
	}

}