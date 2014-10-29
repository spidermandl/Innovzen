
package com.innovzen.entities;

import android.content.Context;
import android.net.Uri;

import com.innovzen.interfaces.ConfigureInterface;

public class SoundItem implements ConfigureInterface {

    // Hold the sound types
    public static final int INSPIREZ = 0;
    public static final int RETENEZ = 1;
    public static final int EXPIREZ = 2;

    private String file; // without the extension
    private int type; // see constants above

    // Hold the resource int value of the sound file in the raw folder
    private int mResId = -1;

    // Hold the uri of the sound in the raw folder
    private Uri mRawUri;

    public SoundItem(String file, int type, int mResId) {
        this.file = file;
        this.type = type;
        this.mResId = mResId;
    }

    public String getFile() {
        return file;
    }

    public int getType() {
        return type;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getResourceId() {
        return mResId;
    }

    public Uri getResUri() {
        return mRawUri;
    }

    @Override
    public void configure(Context ctx) {
        // Find and set the resource id for quick reference
        mResId = ctx.getResources().getIdentifier(file, "raw", ctx.getPackageName());

        // Configure the uri of the file in the raw folder
        mRawUri = Uri.parse("android.resource://" + ctx.getPackageName() + "/" + mResId);
    }

}
