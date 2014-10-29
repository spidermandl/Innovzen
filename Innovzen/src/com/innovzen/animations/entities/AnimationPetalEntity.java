
package com.innovzen.animations.entities;

import android.view.View;
import android.widget.ImageView;

import com.innovzen.animations.AnimationPetals;
import com.innovzen.utils.Util;

public class AnimationPetalEntity {

    /** Keep the index of this petal. Value in interval [0; 7] */
    private int mIndex;

    /** The {@link View} which represents the petal */
    private ImageView mPetal;

    /**
     * The fraction out of the total step progress from which we should start
     * increasing the alpha for this petal
     */
    private float mFracToStartIncreasingAlpha;

    /**
     * The fraction out of the total step progress from which we should start
     * decreasing the alpha for this petal
     */
    private float mFracToStartDecreasingAlpha;

    public AnimationPetalEntity(int petalIndex, ImageView petalView) {
        this.mIndex = petalIndex;
        this.mPetal = petalView;

        init();
    }

    /**
     * Does proper initializations
     * 
     * @author MAB
     */
    private void init() {

        mFracToStartIncreasingAlpha = mIndex * AnimationPetals.PROGRESS_TRIGGER_PETAL_ALPHA;

        mFracToStartDecreasingAlpha = (7 - mIndex) * AnimationPetals.PROGRESS_TRIGGER_PETAL_ALPHA;

    }

    /**
     * Calculate the alpha for this specific petal at a specific step progress
     * 
     * @param stepTimeFraction
     * @return
     * @author MAB
     */
    public float getInhaleAlpha(float stepTimeFraction) {

        // If the total step progress has not yet reached our starting point,
        // then do nothin'
        if (stepTimeFraction >= mFracToStartIncreasingAlpha) {

            float alpha = (stepTimeFraction - mFracToStartIncreasingAlpha) * 2;

            if (alpha > 1f) {
                alpha = 1f;
            }

            return alpha;
        }

        return 0f;
    }

    /**
     * Calculate the alpha for this specific petal at a specific step progress
     * 
     * @param stepTimeFraction
     * @return
     * @author MAB
     */
    public float getExhaleAlpha(float stepTimeFraction) {

        // If the total step progress has not yet reached our starting point,
        // then do nothin'
        if (stepTimeFraction >= mFracToStartDecreasingAlpha) {

            float alpha = 1 - (stepTimeFraction - mFracToStartDecreasingAlpha) * 2;

            if (alpha < 0f) {
                alpha = 0f;
            }

            return alpha;
        }

        return 1f;
    }

    /**
     * Releases any memory consuming objects (i.e. bitmaps if available)
     * 
     * @author MAB
     */
    public void release() {

        if (mPetal != null) {
            Util.recycleBitmap((ImageView) mPetal);
            mPetal = null;
        }

    }

    /**
     * Get the petal ImageView
     * 
     * @return
     * @author MAB
     */
    public View getView() {
        return mPetal;
    }
}
