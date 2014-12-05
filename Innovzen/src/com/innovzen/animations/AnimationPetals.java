package com.innovzen.animations;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.o2chair.R;
import com.innovzen.activities.ActivityBase;
import com.innovzen.animations.entities.AnimationPetalEntity;
import com.innovzen.animations.interfaces.ExerciseAnimationBase;
import com.innovzen.entities.ExerciseTimes;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.handlers.CircularSeekBarHandler;
import com.innovzen.handlers.ExerciseManager;
import com.innovzen.utils.Util;

public class AnimationPetals extends ExerciseAnimationBase {

	/** Hold the total number of petals */
	public static final int NR_PETALS = 8;

	/** Hold the threshold increment after which we'll start each animation (multiply this by the petal index number) */
	public static final float PROGRESS_TRIGGER_PETAL_ALPHA = 0.5f / NR_PETALS;

	// Hold the context
	private Context mCtx;

	// Hold view references
	private TextView step_text;
	private RelativeLayout petals_container;

	// Hold the petals
	private List<AnimationPetalEntity> mPetals;

	// Hold the step text colors for quick reference
	private int mStepTextColorInhale = -1;
	private int mStepTextColorHold = -1;
	private int mStepTextColorExhale = -1;

	/** Hold the handler for the timer */
	private CircularSeekBarHandler mTimerHandler;

	/** Indicate if the layout has been drawn */
	private boolean mIsLayoutDrawn = false;
	/** Indicate if the text view that indicates the step type has been drawn */
	private boolean mIsTextDrawn = false;

	public AnimationPetals(Context ctx) {
		this.mCtx = ctx;

		init();
	}

	/**
	 * Does proper initializations
	 * 
	 * @author MAB
	 */
	private void init() {

		Resources res = mCtx.getResources();

		// Inflate the layout and get the view references
		super.layout = (RelativeLayout) ((LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.exercise_animation_petals, null);
		petals_container = (RelativeLayout) super.layout.findViewById(R.id.animation_petals_petals_container);
		step_text = (TextView) super.layout.findViewById(R.id.animation_step_text);

		// Get the step texts color
		mStepTextColorInhale = res.getColor(R.color.animation_petals_text_inhale);
		mStepTextColorHold = res.getColor(R.color.animation_petals_text_hold);
		mStepTextColorExhale = res.getColor(R.color.animation_petals_text_exhale);

		// Set up the animation petals
		mPetals = new ArrayList<AnimationPetalEntity>();
		mPetals.add(new AnimationPetalEntity(0, (ImageView) super.layout.findViewById(R.id.animation_petals_petal_0)));
		mPetals.add(new AnimationPetalEntity(1, (ImageView) super.layout.findViewById(R.id.animation_petals_petal_1)));
		mPetals.add(new AnimationPetalEntity(2, (ImageView) super.layout.findViewById(R.id.animation_petals_petal_2)));
		mPetals.add(new AnimationPetalEntity(3, (ImageView) super.layout.findViewById(R.id.animation_petals_petal_3)));
		mPetals.add(new AnimationPetalEntity(4, (ImageView) super.layout.findViewById(R.id.animation_petals_petal_4)));
		mPetals.add(new AnimationPetalEntity(5, (ImageView) super.layout.findViewById(R.id.animation_petals_petal_5)));
		mPetals.add(new AnimationPetalEntity(6, (ImageView) super.layout.findViewById(R.id.animation_petals_petal_6)));
		mPetals.add(new AnimationPetalEntity(7, (ImageView) super.layout.findViewById(R.id.animation_petals_petal_7)));

		// Set up the timer
		if (ActivityBase.IS_TABLET) { // TABLET

			// Set up the timer
			// Not here. See in the view tree observer for the layout (below)

			// Set the left position of the petals
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) petals_container.getLayoutParams();
			/**
			 * Desmond
			 * ¶¯»­¿¿×ó
			 */
			params.leftMargin = 0;//(int) (Util.getScreenDimensions(mCtx)[0] * 0.2f);
			//<Desmond>
			petals_container.setLayoutParams(params);

		} else { // PHONE

			// Set up the timer
			int timerDim = (int) (Util.getScreenDimensions(mCtx)[0] * 0.22031f);
			mTimerHandler = new CircularSeekBarHandler(super.layout.findViewById(R.id.reusable_circular_seekbar_container), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_DOUBLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, false);
			mTimerHandler.showThumb(false);

			// Set the initial position of the gradient (make sure it's top = 0)
			setPetalsContainerTopMargin(0);
		}

		super.layout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				if (layout == null) {
					return false;
				}

				// IMPORTANT !!! If not removed, it will enter an infinite loop
				if (layout.getViewTreeObserver().isAlive()) {
					layout.getViewTreeObserver().removeOnPreDrawListener(this);
				}

				// Set up the timer
				if (ActivityBase.IS_TABLET) {
					int timerDim = (int) (layout.getHeight() * 0.32f);
					mTimerHandler = new CircularSeekBarHandler(layout.findViewById(R.id.reusable_circular_seekbar_container), true, CircularSeekBarHandler.TIME_TYPE_SMALL_DIGITAL_DOUBLE, CircularSeekBarHandler.PROGRESS_TYPE_MIXED, timerDim, false);
					mTimerHandler.showThumb(false);
				}

				// If we're in portrait mode
				// AND
				// If the text has already been drawn, then we can set the start/end dimension of the gradient
				if (!ActivityBase.IS_TABLET && mIsTextDrawn) { // PHONE

					setPetalsContainerDimension(layout.getHeight() - step_text.getHeight());

				}

				mIsLayoutDrawn = true;

				return true;
			}
		});

		step_text.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {

				if (step_text == null) {
					return false;
				}

				// IMPORTANT !!! If not removed, it will enter an infinite loop
				if (step_text.getViewTreeObserver().isAlive()) {
					step_text.getViewTreeObserver().removeOnPreDrawListener(this);
				}

				// If we're in portrait mode
				// AND
				// If the layout has already been drawn, then we can set the dimension of the petals container
				if (!ActivityBase.IS_TABLET && mIsLayoutDrawn) {

					setPetalsContainerDimension(layout.getHeight() - step_text.getHeight());

				}

				mIsTextDrawn = true;

				return true;
			}
		});

	}

	@Override
	public void configure(ExerciseTimes times) {

		// Make a copy by value, not by reference !!!!
		// super.times = new ExerciseTimes(times.inhale, times.holdInhale, times.exhale, times.holdExhale, times.exerciseDuration);
		super.times = times;

	}

	@Override
	protected void processTimes() {
		// TODO:
	}

	@Override
	public void inhale(float stepTimeFraction, float globalTimeFraction) {

		// Set text for the step progress
		step_text.setText(FragAnimationBase.STRING_INHALE);

		// Set the color for the step progress text
		step_text.setTextColor(mStepTextColorInhale);

		// Change the alpha for each individual petal
		for (AnimationPetalEntity petal : mPetals) {
			petal.getView().setAlpha(petal.getInhaleAlpha(stepTimeFraction));
		}

		mTimerHandler.setProgressMillisWithStepType(ExerciseManager.EXERCISE_INHALE, 0, super.times.inhale, (int) (super.times.inhale * stepTimeFraction), super.times.exerciseDuration, globalTimeFraction);

	}

	@Override
	public void holdInhale(float stepTimeFraction, float globalTimeFraction) {

		// Set text for the step progress
		step_text.setText(FragAnimationBase.STRING_HOLD);

		// Set the color for the step progress text
		step_text.setTextColor(mStepTextColorHold);

		mTimerHandler.setProgressMillisWithStepType(ExerciseManager.EXERCISE_HOLD_INHALE, 0, super.times.holdInhale, (int) (super.times.holdInhale * stepTimeFraction), super.times.exerciseDuration, globalTimeFraction);

	}

	@Override
	public void exhale(float stepTimeFraction, float globalTimeFraction) {

		// Set text for the step progress
		step_text.setText(FragAnimationBase.STRING_EXHALE);

		// Set the color for the step progress text
		step_text.setTextColor(mStepTextColorExhale);

		// Change the alpha for each individual petal
		for (AnimationPetalEntity petal : mPetals) {
			petal.getView().setAlpha(petal.getExhaleAlpha(stepTimeFraction));
		}

		mTimerHandler.setProgressMillisWithStepType(ExerciseManager.EXERCISE_EXHALE, 0, super.times.exhale, (int) (super.times.exhale * stepTimeFraction), super.times.exerciseDuration, globalTimeFraction);

	}

	@Override
	public void holdExhale(float stepTimeFraction, float globalTimeFraction) {

		// Set text for the step progress
		step_text.setText(FragAnimationBase.STRING_HOLD);

		// Set the color for the step progress text
		step_text.setTextColor(mStepTextColorHold);

		mTimerHandler.setProgressMillisWithStepType(ExerciseManager.EXERCISE_HOLD_EXHALE, 0, super.times.holdExhale, (int) (super.times.holdExhale * stepTimeFraction), super.times.exerciseDuration, globalTimeFraction);
	}

	@Override
	public void release() {

		for (AnimationPetalEntity petals : mPetals) {
			petals.release();
			petals = null;
		}

		mTimerHandler.reseStepType();

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}

	@Override
	public void enableFullscreen() {

		if (!ActivityBase.IS_TABLET) { // PHONE

			super.layout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

				@Override
				public boolean onPreDraw() {
					if (layout == null) {
						return false;
					}

					// IMPORTANT !!! If not removed, it will enter an infinite loop
					if (layout.getViewTreeObserver().isAlive()) {
						layout.getViewTreeObserver().removeOnPreDrawListener(this);
					}

					// Set the dimensions of the petals container
					setPetalsContainerDimension(layout.getWidth());

					// Place the petals in the center of the screen
					setPetalsContainerTopMargin((int) (layout.getHeight() / 2 - layout.getWidth() / 2));

					return true;
				}
			});

		} else { // TABLET

		}

	}

	@Override
	public void disableFullscreen() {

		if (!ActivityBase.IS_TABLET) { // PHONE

			super.layout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

				@Override
				public boolean onPreDraw() {
					if (layout == null) {
						return false;
					}

					// IMPORTANT !!! If not removed, it will enter an infinite loop
					if (layout.getViewTreeObserver().isAlive()) {
						layout.getViewTreeObserver().removeOnPreDrawListener(this);
					}

					// Set the dimensions of the petals container
					setPetalsContainerDimension(layout.getHeight() - step_text.getHeight());

					// Disable the rule that placed the petals in the center of the screen
					setPetalsContainerTopMargin(0);

					return true;
				}
			});

		} else { // TABLET

		}

	}

	/**
	 * 
	 * @param dim
	 *            both with and height, it's a square
	 * @author MAB
	 */
	private void setPetalsContainerDimension(int dim) {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) petals_container.getLayoutParams();
		params.width = dim;
		params.height = dim;
		petals_container.setLayoutParams(params);
	}

	private void setPetalsContainerTopMargin(int topMargin) {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) petals_container.getLayoutParams();
		params.setMargins(0, topMargin, 0, 0);
		petals_container.setLayoutParams(params);
	}
	@Override
	public CircularSeekBarHandler getTimeHandler() {
		return mTimerHandler;
	}
}
