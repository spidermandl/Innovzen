
package com.innovzen.ui;

import com.innovzen.o2chair.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class AnimationBeachSunView extends View {

    /** The resource id of the background for this view */
    private int mBackgroundRes = -1;

    /** The sun bitmap */
    private Bitmap mSun;

    /** The rectangle holding the portion of the bitmap that we want to draw at this time */
    private Rect mRectDest = new Rect();

    /** The rectangle holding the bounds of this view */
    private Rect mRectSrc = new Rect();

    /** The min and max dim of the sun's width */
    private int mMinDimWidth = -1, mMaxDimWidth = -1;

    /** The min and max dim of the sun's height */
    private int mMinDimHeight = -1, mMaxDimHeight = -1;

    /** The width and height of the original bmp file so we don't call getHeight every time */
    private int mSunBmpW = -1, mSunBmpH = -1;

    public AnimationBeachSunView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public AnimationBeachSunView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public AnimationBeachSunView(Context context) {
        super(context);

        init(null);
    }

    private void init(AttributeSet attrs) {

        if (attrs != null) {
            // Get the background resource id
            // mBackgroundRes = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", 0);
            mBackgroundRes = R.drawable.exercise_animation_beach_sun_red;

            // Get the bitmap
            if (mBackgroundRes != 0) {
                mSun = BitmapFactory.decodeResource(getResources(), mBackgroundRes);

                mSunBmpW = mSun.getWidth();
                mSunBmpH = mSun.getHeight();
            }

        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // // Re-set the destination rect (this entire view)
        // mRectDest.set(0, 0, w, h);
        //
        // /*
        // * Calculate and set the chunk of the bitmap that we want to cut out and draw
        // */
        // if (mMinDimWidth > 0 && mMaxDimWidth > 0 && mMinDimHeight > 0 && mMaxDimHeight > 0) {
        //
        // // float x = ((w - mMinDimWidth) / (mMaxDimWidth - mMinDimWidth));
        // // float y = ((h - mMinDimHeight) / (mMaxDimHeight - mMinDimHeight));
        // float x = w * 1.0f / mMaxDimWidth * 1.0f;
        // float y = h * 1.0f / mMaxDimHeight * 1.0f;
        //
        // // System.out.println("==============================================");
        // // System.out.println("==============================================");
        // // System.out.println("================= minH " + mMinDimHeight);
        // // System.out.println("================= maxH: " + mMaxDimHeight);
        // // System.out.println("================= h: " + h);
        // // System.out.println("================= sun bmp height: " + mSunBmpH);
        // // System.out.println("================= y: " + y);
        // // System.out.println("================= mSunBmpH * (y): " + (mSunBmpH * (y)));
        // // System.out.println("==============================================");
        // // System.out.println("================= minW " + mMinDimWidth);
        // // System.out.println("================= maxW: " + mMaxDimWidth);
        // // System.out.println("================= w: " + w);
        // // System.out.println("================= sun bmp width: " + mSunBmpW);
        // // System.out.println("================= x: " + x);
        // // System.out.println("================= mSunBmpW * (x): " + (mSunBmpW * (x)));
        //
        // mRectSrc.set(0, 0, (int) (mSunBmpW * (x)), (int) (mSunBmpH * (y)));
        //
        // }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mSun != null && mRectSrc != null && mRectDest != null) {

            // canvas.drawBitmap(mSun, mRectSrc, mRectDest, null);

        }

    }

    /**
     * Set the minimum and maximum possible values for the sun. Only one value for each as it's a square.
     * 
     * @param minW
     * @param maxW
     * @param minH
     * @param minH
     * @author MAB
     */
    public void setMinMaxDimensions(int minW, int maxW, int minH, int maxH) {
        mMinDimWidth = minW;
        mMaxDimWidth = maxW;

        mMinDimHeight = minH;
        mMaxDimHeight = maxH;
    }

    public void release() {
        if (mSun != null && !mSun.isRecycled()) {
            mSun.recycle();
            mSun = null;
        }
    }

    public void setMaxCurrentDimensions(int maxWidth, int maxHeight) {
        mMaxDimWidth = maxWidth;
        mMaxDimHeight = maxHeight;
    }
}
