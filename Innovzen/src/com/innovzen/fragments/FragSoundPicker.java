package com.innovzen.fragments;

import adapters.AdapterSound;
import com.innovzen.o2chair.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.entities.SoundItem;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.handlers.HeaderHandler;
import com.innovzen.handlers.SubheaderHandler;
import com.innovzen.utils.PersistentUtil;

public class FragSoundPicker extends FragBase implements OnClickListener, OnItemClickListener {

    // Hold shared pref keys
    public static final String PERSIST_SELECTED_VOICE = "selected_voice";
    public static final String PERSIST_SELECTED_AMBIANCE = "selected_ambiance";

    // Hold the header handler
    private HeaderHandler mHeaderHandler;

    // Hold the listviews
    private ListView listview_ambiance;
    private ListView listview_voices;

    // Hold listview adapters
    private AdapterSound mAdapterAmbiance;
    private AdapterSound mAdapterVoices;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sound_picker, container, false);

        init(view);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        super.activityListener.fragStopPlayers();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sound_picker_validate_btn:
                // Save the values
                PersistentUtil.setInt(getActivity(), mAdapterVoices.getSelectedSoundId(), PERSIST_SELECTED_VOICE);
                PersistentUtil.setInt(getActivity(), mAdapterAmbiance.getSelectedSoundId(), PERSIST_SELECTED_AMBIANCE);
                // Let the activity know we've validated the selected sounds
                super.activityListener.fragSoundValidate();
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {

        if (adapter.equals(listview_voices)) { // VOICES

            // Mark it as selected
            int soundId = mAdapterVoices.markAsSelected(position);

            // Stop the ambiance player (in case it's running)
            super.activityListener.fragStopAmbiancePlayer();

            // If the sound is a silence one, then don't play anything
            if (soundId != AdapterSound.SILENCE_ID) {
                // Play the INSPIREZ sound
                super.activityListener.fragPlayVoice(soundId, SoundItem.INSPIREZ);
            } else {
                super.activityListener.fragStopVoicePlayer();
            }

        } else if (adapter.equals(listview_ambiance)) { // AMBIANCE

            // Mark it as selected
            int soundId = mAdapterAmbiance.markAsSelected(position);

            // Stop the voice player (in case it's running)
            super.activityListener.fragStopVoicePlayer();

            // If the sound is a silence one, then don't play anything
            if (soundId != AdapterSound.SILENCE_ID) {
                // Play the INSPIREZ sound
                super.activityListener.fragPlayAmbiance(soundId, SoundItem.INSPIREZ, false);
            } else {
                super.activityListener.fragStopAmbiancePlayer();
            }

        }

    }

    @Override
    public void init(View view) {

        // Get references
        listview_voices = (ListView) view.findViewById(R.id.sound_voices_listview);
        listview_ambiance = (ListView) view.findViewById(R.id.sound_ambiance_listview);

        // Init the header
        mHeaderHandler = new HeaderHandler(getActivity(), (RelativeLayout) view.findViewById(R.id.reusable_header), true, false, null);
        mHeaderHandler.showLeftArrow(null);

        // Init the subheader
        new SubheaderHandler(getResources(), (TextView) view.findViewById(R.id.reusable_subheader), getString(R.string.subheader_base_reglages), getString(R.string.subheader_sound));

        // Init the footer
        new FooterHandler(super.activityListener, (RelativeLayout) view.findViewById(R.id.sound_footer), FooterHandler.HOME, -1, -1);

        // Configure ambiance listview + adapter
        mAdapterAmbiance = new AdapterSound(getActivity(), super.activityListener.fragGetAmbiance());
        listview_ambiance.setAdapter(mAdapterAmbiance);
        listview_ambiance.setOnItemClickListener(this);

        /*
         * Get AND in case it's -1, then set the default selected sounds (voice and ambiance)
         */
        // Voice
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

        // Set initial selected sound for ambiance
        mAdapterAmbiance.setSelectedSoundID(selectedAmbianceSoundId);

        // Configure voices listview + adapter
        mAdapterVoices = new AdapterSound(getActivity(), super.activityListener.fragGetVoices(), selectedVoiceSoundId);
        listview_voices.setAdapter(mAdapterVoices);
        listview_voices.setOnItemClickListener(this);

        // Set event listeners
        view.findViewById(R.id.sound_picker_validate_btn).setOnClickListener(this);

    }

}
