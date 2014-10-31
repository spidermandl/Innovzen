package com.innovzen.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.innovzen.o2chair.R;
import com.innovzen.utils.PersistentUtil;

public class ActivitySplashScreen extends ActivityBase {

    /** App contains only the "Play" (Exercise) screen. No slider and no password protected content. */
    private static final int APP_TYPE_INNOVZEN = 0;
    /** App contains both the "Play" and "Slider" categories and they are password protected. */
    private static final int APP_TYPE_O2CHAIR = 1;

    /*
     * ----------------------------------------------------------
     */
    /**
     * <pre>
     * The current type of the app. Based on this, the app will load or hide certain features / functionality / etc
     *      
     * What to change when this gets modified:
     * 1. package name (right click on the project -> Android tools -> Rename Application Package) com.innovzen and com.innovzen.o2chair
     * 2. app_name string in strings.xml - IN BOTH STRINGS ... "en" and "fr" !!!!
     * 3. override the ic_launcher icons
     * 4. obviously, change the CURRENT_APP_TYPE value below
     * </pre>
     */
    public static final int CURRENT_APP_TYPE = APP_TYPE_O2CHAIR;
    /*
     * ----------------------------------------------------------
     */

    // The time the splash screen will stay visible (millisecs)
    private final int SPLASH_TIME_OUT = 0; // millisecs TODO: change this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
         //延时0毫秒线程加入队列
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
            	//是否已经登录过
                if (ActivitySplashScreen.isInnovzenApp() || PersistentUtil.getBoolean(ActivitySplashScreen.this, ActivityLogin.SHARED_PREF_IS_LOGGED_IN, false)) {
                    //登录过跳转到主界面
                    // Jump to the main menu activity
                    Intent intent = new Intent(ActivitySplashScreen.this, ActivityMain.class);
                    startActivity(intent);
                    finish();

                } else {
                  //未登录跳转到登录界面
                    // Jump to the login activity
                    Intent intent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                    startActivity(intent);
                    finish();

                }

            }
        }, SPLASH_TIME_OUT);
    }

    /**
     * @return true if the {@link #CURRENT_APP_TYPE} is of type {@link #APP_TYPE_INNOVZEN}<br/>
     *         false if it's of type {@link #APP_TYPE_O2CHAIR}
     * @author MAB
     */
    public static final boolean isInnovzenApp() {
        return (ActivitySplashScreen.CURRENT_APP_TYPE == ActivitySplashScreen.APP_TYPE_INNOVZEN);
    }
}
