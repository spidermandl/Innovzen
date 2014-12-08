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

import com.innovzen.activities.ActivityMain;
import com.innovzen.entities.SoundItem;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.o2chair.R;
import com.innovzen.ui.VerticalSeekBar;
import com.innovzen.utils.MyPreference;
import com.innovzen.utils.PersistentUtil;

public class FragMusic extends FragBase implements OnClickListener {

	private ImageView left_top;
	LinearLayout left_mid;
	private ImageView music1;
	private ImageView music2;
	private ImageView music3;
	private ImageView music4;
	private ImageView music5;
	private TextView myMinutes;
	public static final String PERSIST_SELECTED_AMBIANCE = "selected_ambiance";
	private AdapterSound mAdapterAmbiance;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_music, container, false);
		init(view);

		return view;
	}

	// MyMediaPlayer mediaPlay = new MyMediaPlayer();
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.music1:
			music1.setBackgroundResource(R.drawable.btn_aquatic_music_activated);
			music2.setBackgroundResource(R.drawable.selector_music_bondi);
			music3.setBackgroundResource(R.drawable.selector_music_angelic);
			music4.setBackgroundResource(R.drawable.selector_music_ithaca);
			music5.setBackgroundResource(R.drawable.selector_music_silence);
			MyPreference.getInstance(this.getActivity()).writeString(
					MyPreference.MUSIC, MyPreference.SELECT_MUSIC1);

			// Mark it as selected
			int soundId = mAdapterAmbiance.markAsSelected(2);

			// Stop the voice player (in case it's running)
			super.activityListener.fragStopVoicePlayer();

			// If the sound is a silence one, then don't play anything
			if (soundId != AdapterSound.SILENCE_ID) {
				// Play the INSPIREZ sound
				super.activityListener.fragPlayAmbiance(soundId,
						SoundItem.INSPIREZ, false);
			} else {
				super.activityListener.fragStopAmbiancePlayer();
			}
			PersistentUtil.setInt(getActivity(),
					mAdapterAmbiance.getSelectedSoundId(),
					PERSIST_SELECTED_AMBIANCE);
			MyPreference.getInstance(getActivity()).writeInt(PERSIST_SELECTED_AMBIANCE, mAdapterAmbiance.getSelectedSoundId());
			((ActivityMain)getActivity()).getExerciseManager().reinitAmbiance(soundId);
			break;
		case R.id.music2:
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
			music2.setBackgroundResource(R.drawable.btn_bondi_music_activated);
			music3.setBackgroundResource(R.drawable.selector_music_angelic);
			music4.setBackgroundResource(R.drawable.selector_music_ithaca);
			music5.setBackgroundResource(R.drawable.selector_music_silence);
			MyPreference.getInstance(this.getActivity()).writeString(
					MyPreference.MUSIC, MyPreference.SELECT_MUSIC2);

			// Mark it as selected
			int soundId1 = mAdapterAmbiance.markAsSelected(3);

			// Stop the voice player (in case it's running)
			super.activityListener.fragStopVoicePlayer();

			// If the sound is a silence one, then don't play anything
			if (soundId1 != AdapterSound.SILENCE_ID) {
				// Play the INSPIREZ sound
				super.activityListener.fragPlayAmbiance(soundId1,
						SoundItem.INSPIREZ, false);
			} else {
				super.activityListener.fragStopAmbiancePlayer();
			}

			PersistentUtil.setInt(getActivity(),
					mAdapterAmbiance.getSelectedSoundId(),
					PERSIST_SELECTED_AMBIANCE);
			MyPreference.getInstance(getActivity()).writeInt(PERSIST_SELECTED_AMBIANCE, mAdapterAmbiance.getSelectedSoundId());
			((ActivityMain)getActivity()).getExerciseManager().reinitAmbiance(soundId1);
			break;
		case R.id.music3:
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
			music2.setBackgroundResource(R.drawable.selector_music_bondi);
			music3.setBackgroundResource(R.drawable.btn_angelic_music_activated);
			music4.setBackgroundResource(R.drawable.selector_music_ithaca);
			music5.setBackgroundResource(R.drawable.selector_music_silence);
			MyPreference.getInstance(this.getActivity()).writeString(
					MyPreference.MUSIC, MyPreference.SELECT_MUSIC3);

			// Mark it as selected
			int soundId2 = mAdapterAmbiance.markAsSelected(1);

			// Stop the voice player (in case it's running)
			super.activityListener.fragStopVoicePlayer();

			// If the sound is a silence one, then don't play anything
			if (soundId2 != AdapterSound.SILENCE_ID) {
				// Play the INSPIREZ sound
				super.activityListener.fragPlayAmbiance(soundId2,
						SoundItem.INSPIREZ, false);
			} else {
				super.activityListener.fragStopAmbiancePlayer();
			}
			PersistentUtil.setInt(getActivity(),
					mAdapterAmbiance.getSelectedSoundId(),
					PERSIST_SELECTED_AMBIANCE);
			MyPreference.getInstance(getActivity()).writeInt(PERSIST_SELECTED_AMBIANCE, mAdapterAmbiance.getSelectedSoundId());
			((ActivityMain)getActivity()).getExerciseManager().reinitAmbiance(soundId2);
			break;
		case R.id.music4:
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
			music2.setBackgroundResource(R.drawable.selector_music_bondi);
			music3.setBackgroundResource(R.drawable.selector_music_angelic);
			music4.setBackgroundResource(R.drawable.btn_ithaca_music_activated);
			music5.setBackgroundResource(R.drawable.selector_music_silence);
			MyPreference.getInstance(this.getActivity()).writeString(
					MyPreference.MUSIC, MyPreference.SELECT_MUSIC4);

			// Mark it as selected
			int soundId3 = mAdapterAmbiance.markAsSelected(4);

			// Stop the voice player (in case it's running)
			super.activityListener.fragStopVoicePlayer();

			// If the sound is a silence one, then don't play anything
			if (soundId3 != AdapterSound.SILENCE_ID) {
				// Play the INSPIREZ sound
				super.activityListener.fragPlayAmbiance(soundId3,
						SoundItem.INSPIREZ, false);
			} else {
				super.activityListener.fragStopAmbiancePlayer();
			}
			PersistentUtil.setInt(getActivity(),
					mAdapterAmbiance.getSelectedSoundId(),
					PERSIST_SELECTED_AMBIANCE);

			MyPreference.getInstance(getActivity()).writeInt(PERSIST_SELECTED_AMBIANCE, mAdapterAmbiance.getSelectedSoundId());
			((ActivityMain)getActivity()).getExerciseManager().reinitAmbiance(soundId3);
			break;
		case R.id.music5:
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
			music2.setBackgroundResource(R.drawable.selector_music_bondi);
			music3.setBackgroundResource(R.drawable.selector_music_angelic);
			music4.setBackgroundResource(R.drawable.selector_music_ithaca);
			music5.setBackgroundResource(R.drawable.btn_silence_music_activated);
			MyPreference.getInstance(this.getActivity()).writeString(
					MyPreference.MUSIC, MyPreference.SELECT_MUSIC5);

			// Mark it as selected
			int soundId4 = mAdapterAmbiance.markAsSelected(0);

			// Stop the voice player (in case it's running)
			super.activityListener.fragStopVoicePlayer();

			// If the sound is a silence one, then don't play anything
			if (soundId4 != AdapterSound.SILENCE_ID) {
				// Play the INSPIREZ sound
				super.activityListener.fragPlayAmbiance(soundId4,
						SoundItem.INSPIREZ, false);
			} else {
				super.activityListener.fragStopAmbiancePlayer();
			}
			PersistentUtil.setInt(getActivity(),
					mAdapterAmbiance.getSelectedSoundId(),
					PERSIST_SELECTED_AMBIANCE);
			MyPreference.getInstance(getActivity()).writeInt(PERSIST_SELECTED_AMBIANCE, mAdapterAmbiance.getSelectedSoundId());
			
			((ActivityMain)getActivity()).getExerciseManager().reinitAmbiance(soundId4);
			break;
		case R.id.left_top:
			// mediaPlay.play(getActivity(), MyMediaPlayer.SILENCE);
			
			super.activityListener.fragStopAmbiancePlayer();
			super.activityListener.fragStopVoicePlayer();
			getActivity().onBackPressed();
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
				.readInt(MyPreference.TIME)/60000+MyPreference.MINS);
		left_top = (ImageView) view.findViewById(R.id.left_top);
		
		left_mid = (LinearLayout) view.findViewById(R.id.left_mid);
		left_top.setBackgroundResource(R.drawable.selector_btn_back);
		left_mid.setBackgroundResource(R.drawable.banner_music);
		music1 = (ImageView) view.findViewById(R.id.music1);
		music2 = (ImageView) view.findViewById(R.id.music2);
		music3 = (ImageView) view.findViewById(R.id.music3);
		music4 = (ImageView) view.findViewById(R.id.music4);
		music5 = (ImageView) view.findViewById(R.id.music5);
		music1.setOnClickListener(this);
		music2.setOnClickListener(this);
		music3.setOnClickListener(this);
		music4.setOnClickListener(this);
		music5.setOnClickListener(this);
		left_top.setOnClickListener(this);
		mAdapterAmbiance = new AdapterSound(getActivity(),
				super.activityListener.fragGetAmbiance());

		// Ambiance
		int selectedAmbianceSoundId = //PersistentUtil.getInt(getActivity(),PERSIST_SELECTED_AMBIANCE);
		MyPreference.getInstance(getActivity()).readInt(PERSIST_SELECTED_AMBIANCE);
		
		if (selectedAmbianceSoundId == -1) {
			// Default one is Aquatic SunBeam
			selectedAmbianceSoundId = mAdapterAmbiance.getSecondSoundId();
			PersistentUtil.setInt(getActivity(), selectedAmbianceSoundId,
					PERSIST_SELECTED_AMBIANCE);
			MyPreference.getInstance(getActivity()).writeInt(PERSIST_SELECTED_AMBIANCE, selectedAmbianceSoundId);
		}

		music1.setBackgroundResource(R.drawable.btn_aquatic_music_activated);
		String myMusic = MyPreference.getInstance(this.getActivity())
				.readString(MyPreference.MUSIC);
		if (myMusic == null || myMusic.equals(MyPreference.SELECT_MUSIC1)) {
			music1.setBackgroundResource(R.drawable.btn_aquatic_music_activated);
		} else if (myMusic.equals(MyPreference.SELECT_MUSIC2)) {
			music2.setBackgroundResource(R.drawable.btn_bondi_music_activated);
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
		} else if (myMusic.equals(MyPreference.SELECT_MUSIC3)) {
			music3.setBackgroundResource(R.drawable.btn_angelic_music_activated);
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
		} else if (myMusic.equals(MyPreference.SELECT_MUSIC4)) {
			music4.setBackgroundResource(R.drawable.btn_ithaca_music_activated);
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);
		} else if (myMusic.equals(MyPreference.SELECT_MUSIC5)) {
			music5.setBackgroundResource(R.drawable.btn_silence_music_activated);
			music1.setBackgroundResource(R.drawable.selector_music_aquatic);

		}
	}

}
