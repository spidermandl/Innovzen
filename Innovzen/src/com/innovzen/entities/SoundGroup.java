
package com.innovzen.entities;

import java.util.List;

import android.content.Context;

import com.innovzen.interfaces.ConfigureInterface;

public class SoundGroup implements ConfigureInterface {

    // Possible sound group languages
    public static final String LANG_FRENCH = "fr";
    public static final String LANG_ENGLISH = "eng";

    private int id;
    private String name;
    private List<SoundItem> sounds;
    private String lang;

    public SoundGroup(int id, String name, List<SoundItem> sounds) {
        this.id = id;
        this.name = name;
        this.sounds = sounds;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SoundItem> getSounds() {
        return sounds;
    }

	public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSounds(List<SoundItem> sounds) {
        this.sounds = sounds;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public void configure(Context ctx) {
        for (SoundItem sound : sounds) {
            sound.configure(ctx);
        }
    }

}
