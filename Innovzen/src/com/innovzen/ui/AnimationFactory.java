package com.innovzen.ui;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

public class AnimationFactory {

	/**
	 * Defines an animation which fades in a view (gradually changes its alpha from 0 to 1)
	 * 
	 * @param duration
	 *            the duration of the animation
	 * @return
	 * @author MAB
	 */
	public Animation getFadeInAnimation(int duration) {
		Animation fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setInterpolator(new DecelerateInterpolator());
		fadeIn.setDuration(duration);

		return fadeIn;
	}

	/**
	 * Defines an animation which fades out a view (gradually changes its alpha from 1 to 0)
	 * 
	 * @param duration
	 *            the duration of the animation
	 * @return
	 * @author MAB
	 */
	public Animation getFadeOutAnimation(int duration) {
		Animation fadeOut = new AlphaAnimation(1, 0);
		fadeOut.setInterpolator(new DecelerateInterpolator());
		fadeOut.setDuration(duration);

		return fadeOut;
	}

	/**
	 * Scales a view from it's original scale 1f and 1f to the specified values
	 * 
	 * @param duration
	 * @param toXscale
	 * @param toYscale
	 * @return
	 * @author MAB
	 */
	public Animation getScaleAnimation(int duration, float toXscale, float toYscale) {
		ScaleAnimation scaleAnimation = new ScaleAnimation(1f, toXscale, 1f, toYscale, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(duration);

		return scaleAnimation;
	}

}
