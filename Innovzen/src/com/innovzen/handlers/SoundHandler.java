
package com.innovzen.handlers;

import java.io.InputStream;
import java.util.List;

import com.innovzen.o2chair.R;
import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;

import com.innovzen.activities.ActivityMain;
import com.innovzen.entities.AnimationSounds;
import com.innovzen.entities.SoundGroup;
import com.innovzen.entities.SoundItem;
import com.innovzen.fragments.FragMusic;
import com.innovzen.fragments.FragSoundPicker;
import com.innovzen.interfaces.ConfigureInterface;
import com.innovzen.ui.DialogFactory;
import com.innovzen.utils.JsonUtil;
import com.innovzen.utils.MyPreference;
import com.innovzen.utils.PersistentUtil;

public class SoundHandler implements ConfigureInterface {

    // Hold the context reference
    private Context mCtx;

    // Hold the sounds
    private AnimationSounds mSounds;

    // Hold the player instance
    private MediaPlayer mPlayerVoices;
    private MediaPlayer mPlayerAmbiance;

    public SoundHandler(Context ctx) {
        this.mCtx = ctx;

        configure(ctx);
    }

    public List<SoundGroup> getVoices() {
        return mSounds.voices;
    }

    public List<SoundGroup> getAmbiance() {
        return mSounds.ambiance;
    }

    public void setVoices(List<SoundGroup> voices) {
        this.mSounds.voices = voices;
    }

    public void setAmbiance(List<SoundGroup> ambiance) {
        this.mSounds.ambiance = ambiance;
    }

    @Override
    public void configure(Context ctx) {

        Resources res = mCtx.getResources();

        // Get the input stream for the .json file that holds the sound info
        InputStream in = res.openRawResource(R.raw.sounds);

        // Parse the json
        try {
            mSounds = JsonUtil.jsonToObject(in, AnimationSounds.class);
        } catch (Exception e) {
            DialogFactory.showUserExperienceDialog(mCtx, res.getString(R.string.dialog_user_experience_could_not_load_sound), null);
        }

        // Instantiate players
        // mPlayerVoices = new MediaPlayer();
        // mPlayerAmbiance = new MediaPlayer();

        // Filter out unwanted sounds (i.e. if we're in english mode, then remove all french voices)
        filterSounds();

        // Configure the voice and ambiance sounds
        for (SoundGroup group : mSounds.voices) {
            group.configure(ctx);
        }
        for (SoundGroup group : mSounds.ambiance) {
            group.configure(ctx);
        }

        // In this is the first run of the app, then set the default ambiance sound as the Aquatic SunBeam (id = 1) AND the man as the default one
        if (PersistentUtil.getBoolean(mCtx, ActivityMain.PERSIST_FIRST_RUN, true)) {
            if (mSounds.ambiance.size() >= 1) {
                //PersistentUtil.setInt(mCtx, mSounds.ambiance.get(1).getId(), FragSoundPicker.PERSIST_SELECTED_AMBIANCE);
                MyPreference.getInstance(mCtx).writeInt(FragMusic.PERSIST_SELECTED_AMBIANCE,mSounds.ambiance.get(1).getId());
            }
            if (mSounds.voices.size() >= 1) {
                PersistentUtil.setInt(mCtx, mSounds.voices.get(0).getId(), FragSoundPicker.PERSIST_SELECTED_VOICE);
            }
        }

        // Set if so we know this is the first run of the app, ever
        PersistentUtil.setBoolean(mCtx, false, ActivityMain.PERSIST_FIRST_RUN);

    }

    /**
     * Looks in the provided list for the sound with the corresponding id and returns the item
     * 
     * @param soundsList
     * @param soundId
     * @return May return null if sound Id not found
     * @author MAB
     */
    public SoundGroup getSoundById(List<SoundGroup> soundsList, int soundId) {
        for (SoundGroup group : soundsList) {
            if (group.getId() == soundId) {
                return group;
            }
        }

        return null;
    }

    /**
     * Looks in the provided list for the sound with the corresponding id and type and returns the item
     * 
     * @param soundsList
     * @param soundId
     * @param type
     * @return
     * @author MAB
     */
    public SoundItem getSoundByIdAndType(List<SoundGroup> soundsList, int soundId, int type) {
        SoundGroup group = getSoundById(soundsList, soundId);

        if (group != null) {

            for (SoundItem sound : group.getSounds()) {
                if (sound.getType() == type) {
                    return sound;
                }
            }

        }
        return null;
    }

    /**
     * Looks for the voice sound with the corresponding id and returns the item
     * 
     * @param soundId
     * @return May return null if sound Id not found
     * @author MAB
     */
    public SoundGroup getVoiceSoundById(int soundId) {
        return getSoundById(mSounds.voices, soundId);
    }

    /**
     * Looks for the voice sound with the corresponding id and type and returns the item
     * 
     * @param soundId
     * @param type
     * @return May return null if sound ID not found or the type doesn't match
     * @author MAB
     */
    public SoundItem getVoiceSoundByIdAndType(int soundId, int type) {
        return getSoundByIdAndType(mSounds.voices, soundId, type);
    }

    /**
     * Looks for the ambiance sound with the corresponding id and returns the item
     * 
     * @param soundId
     * @return May return null if sound Id not found
     * @author MAB
     */
    public SoundGroup getAmbianceSoundById(int soundId) {
        return getSoundById(mSounds.ambiance, soundId);
    }

    /**
     * Looks for the ambiance sound with the corresponding id and type and returns the item
     * 
     * @param soundId
     * @param type
     * @return May return null if sound ID not found or the type doesn't match
     * @author MAB
     */
    public SoundItem getAmbianceSoundByIdAndType(int soundId, int type) {
        return getSoundByIdAndType(mSounds.ambiance, soundId, type);
    }

    /**
     * Stops the player and invalidates it
     * 
     * @author MAB
     */
    public void releasePlayers() {

        if (mPlayerVoices != null) {
            stopVoicePlayer();
            mPlayerVoices.release();
            mPlayerVoices = null;
        }

        if (mPlayerAmbiance != null) {
            stopAmbiancePlayer();
            mPlayerAmbiance.release();
            mPlayerAmbiance = null;
        }

        // mPlayerVoices.stop();
        // mPlayerVoices = null;
        //
        // mPlayerAmbiance.stop();
        // mPlayerAmbiance = null;
    }

    /**
     * Stops the voice media player (in case a sound is playing)
     * 
     * @author MAB
     */
    public void stopVoicePlayer() {
        if (mPlayerVoices != null) {
            mPlayerVoices.stop();
        }
    }

    /**
     * Stops the voice media player (in case a sound is playing)
     * 
     * @author MAB
     */
    public void stopAmbiancePlayer() {
        if (mPlayerAmbiance != null) {
            mPlayerAmbiance.stop();
        }
    }

    /**
     * Pauses the mediaplayers (in case sound are playing)
     * 
     * @author MAB
     */
    public void pausePlayers() {
        stopVoicePlayer();
        stopAmbiancePlayer();
    }

    /**
     * Stops the mediaplayers (in case sound are playing)
     * 
     * @author MAB
     */
    public void stopPlayers() {
        stopVoicePlayer();
        stopAmbiancePlayer();
    }

    /**
     * Plays a voice sound
     * 
     * @param sound
     * @author MAB
     */
    public void playVoice(SoundItem sound) {

        if (sound != null) {
            mPlayerVoices = MediaPlayer.create(mCtx, sound.getResUri());
            mPlayerVoices.start();
            // mPlayerVoices.play(mCtx, sound.getResUri(), false, AudioManager.STREAM_MUSIC);
        }

    }

    /**
     * Plays an ambiance sound
     * 
     * @param sound
     * @param loop
     * @author MAB
     */
    public void playAmbiance(SoundItem sound, boolean loop) {

        // Stop any running voice
        stopAmbiancePlayer();

        if (sound != null) {
            mPlayerAmbiance = MediaPlayer.create(mCtx, sound.getResUri());
            mPlayerAmbiance.setLooping(loop);
            mPlayerAmbiance.start();
            // mPlayerAmbiance.play(mCtx, sound.getResUri(), loop, AudioManager.STREAM_MUSIC);
        }

    }

    /**
     * Play the voice sound corresponding to the id.<br/>
     * Before it starts playing it, it stops any previous sounds from playing (if any)
     * 
     * @param soundId
     * @author MAB
     */
    public void playVoice(int soundId, int type) {

        SoundItem sound = getVoiceSoundByIdAndType(soundId, type);

        playVoice(sound);
    }

    /**
     * Play the ambiance sound corresponding to the id.<br/>
     * Before it starts playing it, it stops any previous sounds from playing (if any)
     * 
     * @param soundId
     * @param type
     * @param loop
     * @author MAB
     */
    public void playAmbiance(int soundId, int type, boolean loop) {

        SoundItem sound = getAmbianceSoundByIdAndType(soundId, type);

        playAmbiance(sound, loop);
    }

    /**
     * Filter out unwanted sounds.<br/>
     * i.e. if we're in english mode, then remove the french voices
     * 
     * @author MAB
     */
    private void filterSounds() {

        // Get the language we're currently in
        String languageType = mCtx.getString(R.string.dummy_text_to_quickly_determine_language_type);

        if (languageType != null && mSounds != null) {

            // Go through the list of voice sounds and remove the ones that are NOT of the determined type
            int i = 0;
            while (i < mSounds.voices.size()) {

                if (!mSounds.voices.get(i).getLang().equals(languageType)) {
                    mSounds.voices.remove(i);
                } else {
                    i++;
                }

            }

        }

    }

    /**
     * Set the volume of the ambiance sound
     * 
     * @param volume
     *            needs to be in interval [0.0, 1.0]
     * @author MAB
     */
    public void setAmbianceVolume(float volume) {
        if (mPlayerAmbiance != null) {
            mPlayerAmbiance.setVolume(volume, volume);
        }
    }
}
