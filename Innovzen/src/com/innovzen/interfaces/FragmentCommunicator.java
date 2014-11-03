
package com.innovzen.interfaces;

import java.util.List;

import com.innovzen.entities.SoundGroup;

public interface FragmentCommunicator {
  //<chy>fragGoToLanguage
	public void fragGoToHelpNew(boolean addToBackstack);
	public void fragGoToHistoryNew(boolean addToBackstack);
	public void fragGoToVoice(boolean addToBackstack);
	public void fragGoToTime(boolean addToBackstack);
	public void fragGoToMusic(boolean addToBackstack);
	public void fragGoToGraphic(boolean addToBackstack);
	public void fragGoToSetting(boolean addToBackstack);
	public void fragGoToLanguage(boolean addToBackstack);
	 public void fragGoToBalance(boolean addToBackstack);
 //</chy>	
    public void fragGoToChairInfo(boolean addToBackstack);

    public void fragGoToAnimation(boolean addToBackstack);

    public void fragGoToAnimationPicker(boolean addToBackstack);

    public void fragGoToSoundPicker(boolean addToBackstack);

    public void fragGoToExercisesPicker(boolean addToBackstack);

    public void fragGoToHistory(boolean addToBackstack);

    public void fragGoToHome(boolean addToBackstack);

    public void fragGoToHelp(boolean addToBackstack);

    public void fragGoToTimer(boolean addToBackstack);

    public void fragGoToTimerAdvance(boolean addToBackstack);

    public void fragPopOneFromBackstack();

    public void fragGradientAnimationPicked();

    public void fragPetalsAnimationPicked();

    public void fragBeachAnimationPicked();

    public void fragLungsAnimationPicked();

    public void fragStopPlayers();

    public List<SoundGroup> fragGetVoices();

    public List<SoundGroup> fragGetAmbiance();

    /**
     * @param soundId
     * @param type
     *            see SoundItem for constants
     * @author MAB
     */
    public void fragPlayVoice(int soundId, int type);

    /**
     * @param soundId
     * @param type
     *            see SoundItem for constants
     * @param loop
     * @author MAB
     */
    public void fragPlayAmbiance(int soundId, int type, boolean loop);

    public void fragSetAmbianceVolume(float volume);

    public void fragStopVoicePlayer();

    public void fragStopAmbiancePlayer();

    public void fragSoundValidate();

    public void fragGoToAnimationFromTimer();

    public void fragGoToAnimationFromTimerAdvanced();

}
