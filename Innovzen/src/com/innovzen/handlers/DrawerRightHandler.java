
package com.innovzen.handlers;

import com.innovzen.o2chair.R;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

import com.innovzen.interfaces.FragmentCommunicator;

//ÓÒ²àfragment¹¦ÄÜ¿é
public class DrawerRightHandler implements OnClickListener {

    // Hold references to objects received in the constructor
    private FragmentCommunicator mActivityListener;
    private DrawerLayout mDrawer;

    /**
     * @param activityListener
     *            appropriate method called when one of the options is selected
     * @param drawer
     */
    public DrawerRightHandler(FragmentCommunicator activityListener, DrawerLayout drawer) {
        this.mActivityListener = activityListener;
        this.mDrawer = drawer;

        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawer_right_close_btn:
                hide();
                break;
            case R.id.drawer_right_option_animation:
                mActivityListener.fragGoToAnimationPicker(true);
                break;
            case R.id.drawer_right_option_sound:
                mActivityListener.fragGoToSoundPicker(true);
                break;
            case R.id.drawer_right_option_exercises:
                mActivityListener.fragGoToExercisesPicker(true);
                break;
            case R.id.drawer_right_option_history:
                mActivityListener.fragGoToHistory(true);
                break;
        }
    }

    /**
     * Retrieves the views in the drawer and sets event listeners for them
     * 
     * @author MAB
     */
    private void init() {

        // Set event listeners for the options
        mDrawer.findViewById(R.id.drawer_right_close_btn).setOnClickListener(this);
        mDrawer.findViewById(R.id.drawer_right_option_animation).setOnClickListener(this);
        mDrawer.findViewById(R.id.drawer_right_option_sound).setOnClickListener(this);
        mDrawer.findViewById(R.id.drawer_right_option_exercises).setOnClickListener(this);
        mDrawer.findViewById(R.id.drawer_right_option_history).setOnClickListener(this);

    }

    /**
     * Displays the drawer
     * 
     * @author MAB
     */
    public void show() {
        if (mDrawer != null) {
            mDrawer.openDrawer(Gravity.RIGHT);
        }
    }

    /**
     * Displays the drawer
     * 
     * @author MAB
     */
    public void hide() {
        if (mDrawer != null) {
            mDrawer.closeDrawer(Gravity.RIGHT);
        }
    }

    /**
     * @return the stat of the drawer. <b>true</b> if it's open, <b>false</b> if it's closed
     * @author MAB
     */
    public boolean isOpen() {
        return mDrawer.isDrawerOpen(GravityCompat.END);
    }

}
