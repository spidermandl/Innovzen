package com.innovzen.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.innovzen.o2chair.R;

public class Util {

	/**
	 * 模拟器设置 true为模拟器
	 * false为真机
	 */
	public static final boolean ISEMULATOR=true;
	
    /**
     * Check to see if the current device is a tablet or not
     * 
     * @param context
     * @return
     */
    public static Boolean isTablet(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int size = ((Activity) context).getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (size >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return true;
        } else if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE || context.getResources().getBoolean(R.bool.isTablet)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     * 
     * @param dp
     *            A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context
     *            Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     * 
     * @param px
     *            A value in px (pixels) unit. Which we need to convert into d
     * @param context
     *            Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    /**
     * Check if device has OS version is Honeycomb or newer
     * 
     * @return true if it does, false otherwise
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Determines the width and height of the screen in the current orientation. This method will return switched values when orientation changes so recall it if necessary.
     * 
     * @param context
     * @return int[2] array with first element being the width of the screen in the current orientation and the second being the height of the screen in the current orientation<br/>
     *         int[0] - width<br/>
     *         int[1] - height<br/>
     *         Value is in <b>pixels</b>
     */
    public static int[] getScreenDimensions(Context context) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        return (new int[] { display.widthPixels, display.heightPixels });
    }

    /**
     * Recycles the bitmap from an imageview
     * 
     * @param img
     * @author MAB
     */
    public static void recycleBitmap(ImageView img) {
        try {
            Bitmap bmp = ((BitmapDrawable) (img).getDrawable()).getBitmap();
            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle();
                bmp = null;
                img.setImageBitmap(null); // VERY IMPORTANT !!!
                System.gc();
            }
        } catch (Exception e) {
        }
    }

    /**
     * @param views
     * @return false if at least ONE view in the array is null OR if the entire list is null
     */
    public static boolean allViewsExist(View... views) {

        if (views == null) {
            return false;
        }

        for (View view : views) {
            if (view == null) {
                return false;
            }
        }

        return true;
    }
}
