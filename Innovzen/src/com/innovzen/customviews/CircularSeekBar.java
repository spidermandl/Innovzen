/*
 * 
 * Copyright 2013 Matt Joseph
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * 
 * 
 * This custom view/widget was inspired and guided by:
 * 
 * HoloCircleSeekBar - Copyright 2012 Jes�s Manzano
 * HoloColorPicker - Copyright 2012 Lars Werkman (Designed by Marie Schweiz)
 * 
 * Although I did not used the code from either project directly, they were both used as 
 * reference material, and as a result, were extremely helpful.
 */

package com.innovzen.customviews;

import com.innovzen.o2chair.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.innovzen.handlers.CircularSeekBarHandler;

public class CircularSeekBar extends View {

    /**
     * Used to scale the dp units to pixels
     */
    private final float DPTOPX_SCALE = getResources().getDisplayMetrics().density;

    /**
     * Minimum touch target size in DP. 48dp is the Android design recommendation
     */
    private final float MIN_TOUCH_TARGET_DP = 100;

    private static final float DEFAULT_START_ANGLE = 270f; // Geometric (clockwise, relative to 3 o'clock)
    private static final float DEFAULT_END_ANGLE = 270f; // Geometric (clockwise, relative to 3 o'clock)
    private static final int DEFAULT_MAX = 100;
    private static final int DEFAULT_PROGRESS = 0;
    private static final int DEFAULT_CIRCLE_COLOR = Color.DKGRAY;
    private static final int DEFAULT_CIRCLE_PROGRESS_COLOR = Color.argb(235, 74, 138, 255);
    private static final int DEFAULT_POINTER_COLOR = Color.argb(235, 74, 138, 255);
    private static final int DEFAULT_POINTER_HALO_COLOR = Color.argb(135, 74, 138, 255);
    private static final int DEFAULT_POINTER_HALO_COLOR_ONTOUCH = Color.argb(135, 74, 138, 255);
    private static final int DEFAULT_CIRCLE_FILL_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_POINTER_ALPHA = 135;
    private static final int DEFAULT_POINTER_ALPHA_ONTOUCH = 100;
    private static final boolean DEFAULT_MAINTAIN_EQUAL_CIRCLE = true;

    /**
     * {@code Paint} instance used to draw the inactive circle.
     */
    private Paint mCirclePaint;

    /**
     * {@code Paint} instance used to draw the circle fill.
     */
    private Paint mCircleFillPaint;

    /**
     * {@code Paint} instance used to draw the active circle (represents progress).
     */
    private Paint mCircleProgressPaint;

    /**
     * {@code Paint} instance used to draw the glow from the active circle.
     */
    private Paint mCircleProgressGlowPaint;

    /**
     * {@code Paint} instance used to draw the center of the pointer. Note: This is broken on 4.0+, as BlurMasks do not work with hardware acceleration.
     */
    private Paint mPointerPaint;

    /**
     * {@code Paint} instance used to draw the halo of the pointer. Note: The halo is the part that changes transparency.
     */
    private Paint mPointerHaloPaint;

    /**
     * {@code Paint} instance used to draw the border of the pointer, outside of the halo.
     */
    private Paint mPointerHaloBorderPaint;

    /**
     * The width of the circle (in pixels).
     */
    private float mCircleStrokeWidth;

    /**
     * The X radius of the circle (in pixels).
     */
    private float mCircleXRadius;

    /**
     * The Y radius of the circle (in pixels).
     */
    private float mCircleYRadius;

    /**
     * The radius of the pointer (in pixels).
     */
    private float mPointerRadius;

    /**
     * The width of the pointer halo (in pixels).
     */
    private float mPointerHaloWidth;

    /**
     * The width of the pointer halo border (in pixels).
     */
    private float mPointerHaloBorderWidth;

    /**
     * Start angle of the CircularSeekBar. Note: If mStartAngle and mEndAngle are set to the same angle, 0.1 is subtracted from the mEndAngle to make the circle function properly.
     */
    private float mStartAngle;

    /**
     * End angle of the CircularSeekBar. Note: If mStartAngle and mEndAngle are set to the same angle, 0.1 is subtracted from the mEndAngle to make the circle function properly.
     */
    private float mEndAngle;

    /**
     * {@code RectF} that represents the circle (or ellipse) of the seekbar.
     */
    private RectF mCircleRectF = new RectF();

    /**
     * Holds the color value for {@code mPointerPaint} before the {@code Paint} instance is created.
     */
    private int mPointerColor = DEFAULT_POINTER_COLOR;

    /**
     * Holds the color value for {@code mPointerHaloPaint} before the {@code Paint} instance is created.
     */
    private int mPointerHaloColor = DEFAULT_POINTER_HALO_COLOR;

    /**
     * Holds the color value for {@code mPointerHaloPaint} before the {@code Paint} instance is created.
     */
    private int mPointerHaloColorOnTouch = DEFAULT_POINTER_HALO_COLOR_ONTOUCH;

    /**
     * Holds the color value for {@code mCirclePaint} before the {@code Paint} instance is created.
     */
    private int mCircleColor = DEFAULT_CIRCLE_COLOR;

    /**
     * Holds the color value for {@code mCircleFillPaint} before the {@code Paint} instance is created.
     */
    private int mCircleFillColor = DEFAULT_CIRCLE_FILL_COLOR;

    /**
     * Holds the color value for {@code mCircleProgressPaint} before the {@code Paint} instance is created.
     */
    private int mCircleProgressColor = DEFAULT_CIRCLE_PROGRESS_COLOR;

    /**
     * Holds the alpha value for {@code mPointerHaloPaint}.
     */
    private int mPointerAlpha = DEFAULT_POINTER_ALPHA;

    /**
     * Holds the OnTouch alpha value for {@code mPointerHaloPaint}.
     */
    private int mPointerAlphaOnTouch = DEFAULT_POINTER_ALPHA_ONTOUCH;

    /**
     * Distance (in degrees) that the the circle/semi-circle makes up. This amount represents the max of the circle in degrees.
     */
    private float mTotalCircleDegrees;

    /**
     * Distance (in degrees) that the current progress makes up in the circle.
     */
    private float mProgressDegrees;

    /**
     * {@code Path} used to draw the circle/semi-circle.
     */
    private Path mCirclePath;

    /**
     * {@code Path} used to draw the progress on the circle.
     */
    private Path mCircleProgressPath;

    /**
     * Max value that this CircularSeekBar is representing.
     */
    private int mMax;

    /**
     * Progress value that this CircularSeekBar is representing.
     */
    private float mProgress;

    /**
     * If true, then the user can specify the X and Y radii. If false, then the View itself determines the size of the CircularSeekBar.
     */
    private boolean mCustomRadii;

    /**
     * Maintain a perfect circle (equal x and y radius), regardless of view or custom attributes. The smaller of the two radii will always be used in this case. The default is to be a circle and not an ellipse, due to the behavior of the ellipse.
     */
    private boolean mMaintainEqualCircle;

    /**
     * Once a user has touched the circle, this determines if moving outside the circle is able to change the position of the pointer (and in turn, the progress).
     */
    private boolean mMoveOutsideCircle;

    /**
     * Used for when the user moves beyond the start of the circle when moving counter clockwise. Makes it easier to hit the 0 progress mark.
     */
    private boolean lockAtStart = true;

    /**
     * Used for when the user moves beyond the end of the circle when moving clockwise. Makes it easier to hit the 100% (max) progress mark.
     */
    private boolean lockAtEnd = false;

    /**
     * When the user is touching the circle on ACTION_DOWN, this is set to true. Used when touching the CircularSeekBar.
     */
    private boolean mUserIsMovingPointer = false;

    /**
     * Represents the clockwise distance from {@code mStartAngle} to the touch angle. Used when touching the CircularSeekBar.
     */
    private float cwDistanceFromStart;

    /**
     * Represents the counter-clockwise distance from {@code mStartAngle} to the touch angle. Used when touching the CircularSeekBar.
     */
    private float ccwDistanceFromStart;

    /**
     * Represents the clockwise distance from {@code mEndAngle} to the touch angle. Used when touching the CircularSeekBar.
     */
    private float cwDistanceFromEnd;

    /**
     * Represents the counter-clockwise distance from {@code mEndAngle} to the touch angle. Used when touching the CircularSeekBar. Currently unused, but kept just in case.
     */
    @SuppressWarnings("unused")
    private float ccwDistanceFromEnd;

    /**
     * The previous touch action value for {@code cwDistanceFromStart}. Used when touching the CircularSeekBar.
     */
    private float lastCWDistanceFromStart;

    /**
     * Represents the clockwise distance from {@code mPointerPosition} to the touch angle. Used when touching the CircularSeekBar.
     */
    private float cwDistanceFromPointer;

    /**
     * Represents the counter-clockwise distance from {@code mPointerPosition} to the touch angle. Used when touching the CircularSeekBar.
     */
    private float ccwDistanceFromPointer;

    /**
     * True if the user is moving clockwise around the circle, false if moving counter-clockwise. Used when touching the CircularSeekBar.
     */
    private boolean mIsMovingCW;

    /**
     * F The width of the circle used in the {@code RectF} that is used to draw it. Based on either the View width or the custom X radius.
     */
    private float mCircleWidth;

    /**
     * The height of the circle used in the {@code RectF} that is used to draw it. Based on either the View width or the custom Y radius.
     */
    private float mCircleHeight;

    /**
     * Represents the progress mark on the circle, in geometric degrees. This is not provided by the user; it is calculated;
     */
    private float mPointerPosition;

    /**
     * Pointer position in terms of X and Y coordinates.
     */
    private float[] mPointerPositionXY = new float[2];

    private Bitmap seekBarThumb, seekBarProgress;
    private Paint paint, progressPaint;
    private RectF mOval;
    private Boolean showThumb = false;
    private Boolean showCircle = false;
    private CircularSeekBar bottomCircularSeekBar;
    private boolean showTime = false, isThumbRescaled = false, calculateAngles = false;
    private Boolean largeDigitalDouble = false, largeNormalSingle = false, smallDigitalSingle = false, smallDigitalDouble = false;
    private String separatorLargeDigitalDouble = ":", separatorSmallDigitalDouble = "'", separator = "";
    private float textSizeLargeDigitalDouble = 240, textSizeLargeNormalSingle = 350, textSizeSmallDigitalSingle = 240, textSizeSmallDigitalDouble = 240, chosenTextSize = 200;

    private int maxValue = 3600, minValue = 0;
    private int minAngle = 0;
    Typeface digitalTypeface;
    Typeface normalTypeface;

    private boolean isSmall;
    private boolean roundLastDigit = true;

    public float thumbWidth, thumbHeight;

    /** The dimensions received in the onMeasure method */
    private int mWidth = -1;
    private int mHeight = -1;

    private float totalSeconds;

    /**
     * Listener.
     */
    private OnCircularSeekBarChangeListener mOnCircularSeekBarChangeListener;

    public CircularSeekBar(Context context) {
        super(context);
        init(null, 0, context);
    }

    public CircularSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, context);
    }

    public CircularSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle, context);
    }

    private void init(AttributeSet attrs, int defStyle, Context context) {
        final TypedArray attrArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircularSeekBar, defStyle, 0);
        initAttributes(attrArray);
        attrArray.recycle();

        digitalTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/DS-DIGI.TTF");
        normalTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Vera.ttf");

        initPaints();
    }

    /**
     * Initialize the CircularSeekBar with the attributes from the XML style. Uses the defaults defined at the top of this file when an attribute is not specified by the user.
     * 
     * @param attrArray
     *            TypedArray containing the attributes.
     */
    private void initAttributes(TypedArray attrArray) {
        mPointerAlphaOnTouch = attrArray.getInt(R.styleable.CircularSeekBar_pointer_alpha_ontouch, DEFAULT_POINTER_ALPHA_ONTOUCH);
        if (mPointerAlphaOnTouch > 255 || mPointerAlphaOnTouch < 0) {
            mPointerAlphaOnTouch = DEFAULT_POINTER_ALPHA_ONTOUCH;
        }

        mMax = attrArray.getInt(R.styleable.CircularSeekBar_max, DEFAULT_MAX);
        mProgress = attrArray.getInt(R.styleable.CircularSeekBar_progress, DEFAULT_PROGRESS);
        mMaintainEqualCircle = attrArray.getBoolean(R.styleable.CircularSeekBar_maintain_equal_circle, DEFAULT_MAINTAIN_EQUAL_CIRCLE);

        // Modulo 360 right now to avoid constant conversion
        mStartAngle = ((360f + (attrArray.getFloat((R.styleable.CircularSeekBar_start_angle), DEFAULT_START_ANGLE) % 360f)) % 360f);
        mEndAngle = ((360f + (attrArray.getFloat((R.styleable.CircularSeekBar_end_angle), DEFAULT_END_ANGLE) % 360f)) % 360f);

        if (mStartAngle == mEndAngle) {
            mEndAngle = mEndAngle - .1f;
        }
    }

    /**
     * Initializes the {@code Paint} objects with the appropriate styles.
     */
    private void initPaints() {
        if (isSmall) {
            seekBarThumb = BitmapFactory.decodeResource(getResources(), R.drawable.circular_seekbar_thumb_small);
        } else {
            seekBarThumb = BitmapFactory.decodeResource(getResources(), R.drawable.circular_seekbar_thumb_large);
        }
        seekBarProgress = BitmapFactory.decodeResource(getResources(), R.drawable.circular_seekbar_gradient_background_large);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.main_menu_title_short));
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOval = new RectF();
    }

    /**
     * Calculates the total degrees between mStartAngle and mEndAngle, and sets mTotalCircleDegrees to this value.
     */
    private void calculateTotalDegrees() {
        mTotalCircleDegrees = (360f - (mStartAngle - mEndAngle)) % 360f; // Length of the entire circle/arc
        if (mTotalCircleDegrees <= 0f) {
            mTotalCircleDegrees = 360f;
        }
    }

    /**
     * Calculate the degrees that the progress represents. Also called the sweep angle. Sets mProgressDegrees to that value.
     */
    private void calculateProgressDegrees() {
        mProgressDegrees = mPointerPosition - mStartAngle; // Verified
        mProgressDegrees = (mProgressDegrees < 0 ? 360f + mProgressDegrees : mProgressDegrees); // Verified

        if (bottomCircularSeekBar != null) {
            bottomCircularSeekBar.setProgressDegrees(mProgressDegrees);
        }
    }

    private void calculateProgressDegreesWithoutSendingTheValueToBackground() {
        mProgressDegrees = mPointerPosition - mStartAngle; // Verified
        mProgressDegrees = (mProgressDegrees < 0 ? 360f + mProgressDegrees : mProgressDegrees); // Verified
    }

    /**
     * Calculate the pointer position (and the end of the progress arc) in degrees. Sets mPointerPosition to that value.
     */
    private void calculatePointerAngle() {
        mPointerPosition = (((float) mProgress / (float) mMax) * mTotalCircleDegrees) + mStartAngle;
        mPointerPosition = mPointerPosition % 360f;
    }

    private void calculatePointerXYPosition() {
        PathMeasure pm = new PathMeasure(mCircleProgressPath, false);
        boolean returnValue = pm.getPosTan(pm.getLength(), mPointerPositionXY, null);
        if (!returnValue) {
            pm.setPath(mCirclePath, false);
            // pm = new PathMeasure(mCirclePath, false); //TODO: well. yeah
            // returnValue = pm.getPosTan(0, mPointerPositionXY, null); //TODO: huh? wuz this ?! It's redundant
        }
    }

    /**
     * Initialize the {@code Path} objects with the appropriate values.
     */
    private void initPaths() {
        mCirclePath = new Path();
        mCirclePath.addArc(mCircleRectF, mStartAngle, mTotalCircleDegrees);

        mCircleProgressPath = new Path();
        mCircleProgressPath.addArc(mCircleRectF, mStartAngle, mProgressDegrees);
    }

    /**
     * Initialize the {@code RectF} objects with the appropriate values.
     */
    private void initRects() {
        mCircleRectF.set(-mCircleWidth, -mCircleHeight, mCircleWidth, mCircleHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Matrix m = new Matrix();
        RectF src = new RectF(0, 0, seekBarProgress.getWidth(), seekBarProgress.getHeight());
        RectF dst = new RectF(0, 0, w, h);
        m.setRectToRect(src, dst, ScaleToFit.CENTER);
        m.mapRect(mOval, src);

        Shader shader = new BitmapShader(seekBarProgress, TileMode.CLAMP, TileMode.CLAMP);
        shader.setLocalMatrix(m);
        progressPaint.setShader(shader);

        if (getWidth() > 0 && getHeight() > 0) {
            seekBarThumb = getResizedBitmap(seekBarThumb, (mOval.right / 928), (mOval.right / 928));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int roundedDegrees = Math.round(mProgressDegrees);
        if (mProgressDegrees < minAngle) {
            roundedDegrees = minAngle;
            mProgressDegrees = minAngle;
        }

        if (showCircle) {
            canvas.drawArc(mOval, -90, roundedDegrees, true, progressPaint);
        }

        if (showTime) {

            if (largeDigitalDouble || smallDigitalDouble) {

                int minutes, seconds;
                String minuteString, secondString;
                if (roundLastDigit) {
                    totalSeconds = roundedDegrees * (maxValue / (float) mMax);
                } else {
                    totalSeconds = mProgressDegrees * (maxValue / (float) mMax);
                }

                // Calculate the minutes
                minutes = (int) (totalSeconds / 60);
                if (minutes < 10) {
                    minuteString = "0" + minutes;
                } else {
                    minuteString = minutes + "";
                }

                // Calculate the seconds
                seconds = (int) (totalSeconds % 60);
                if (roundLastDigit) {
                    seconds = ((int) (seconds / 10)) * 10;
                    totalSeconds = ((int) totalSeconds / 10) * 10;
                    if (seconds < 10) {
                        secondString = "00";
                    } else {
                        secondString = seconds + "";
                    }
                } else {
                    if (seconds < 10) {
                        secondString = "0" + seconds;
                    } else {
                        secondString = seconds + "";
                    }
                }

                String timeValue = minuteString + ":" + secondString;
                paint.setTextSize(chosenTextSize * (getWidth() / 928f));

                // Draw the text
                canvas.drawText(minuteString + this.separator + secondString, getWidth() / 2 - paint.measureText(timeValue) / 2, getWidth() / 2 + (Math.abs(paint.ascent()) + paint.descent() + 1) / 4, paint);

            } else if (largeNormalSingle || smallDigitalSingle) {

                totalSeconds = (int) (roundedDegrees * (maxValue / (float) mMax));
                int seconds = (int) totalSeconds;
                String secondsString;
                if (totalSeconds < 10) {
                    secondsString = "0" + seconds;
                } else {
                    secondsString = seconds + "";
                }

                paint.setTextSize(chosenTextSize * (getWidth() / 928f));
                canvas.drawText(secondsString, getWidth() / 2 - paint.measureText(secondsString) / 2, getWidth() / 2 + (Math.abs(paint.ascent()) + paint.descent() + 1) / 4, paint);
            }
        }

        if (showThumb) {

            // DON'T REMOVE THIS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! We can use this to understand the code 3-4 lines below :)
            // DON'T REMOVE THIS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // DON'T REMOVE THIS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // // Determine the left post
            // float width = getWidth() / 2f - seekBarThumb.getWidth() / 2;
            // double sin = Math.sin(Math.toRadians(roundedDegrees));
            // float x = width + (float) (sin * width);
            //
            // // Determine the top pos
            // float height = getWidth() / 2f - seekBarThumb.getHeight() / 2;
            // double cos = Math.cos(Math.toRadians(roundedDegrees));
            // float y = height - (float) (cos * height);
            //
            // // seekBarThumb.setHeight((int)
            // // (seekBarThumb.getHeight()*(mOval.right/928)));
            //
            // DON'T REMOVE THIS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // DON'T REMOVE THIS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // DON'T REMOVE THIS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            // Set the paint
            paint.setFilterBitmap(true);
            paint.setDither(true);

            // Draw the thumb
            canvas.drawBitmap(seekBarThumb, (float) ((getWidth() / 2f - seekBarThumb.getWidth() / 2) * (1f + (Math.sin(Math.toRadians(roundedDegrees))))), (float) ((getWidth() / 2f - seekBarThumb.getHeight() / 2) * (1f - (Math.cos(Math.toRadians(roundedDegrees))))), paint);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, float scaleHeight, float scaleWidth) {
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth + ((1 - scaleHeight) / 4), scaleHeight + ((1 - scaleHeight) / 4));

        // thumbHeight = bm.getHeight() * scaleHeight;
        // thumbWidth = bm.getWidth() * scaleWidth;
        thumbHeight = bm.getHeight() * scaleHeight;
        thumbWidth = (getWidth() * 0.154854f);

        // System.out.println("============== view width " + getWidth() + "xxxwidth    " + thumbWidth);
        // recreate the new Bitmap
        // Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
        // bm.getHeight(), matrix, false);
        // Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0,
        // (int)(getWidth()*0.154854f), (int)(getWidth()*0.154854f), null,
        // false);

        isThumbRescaled = true;

        return Bitmap.createScaledBitmap(seekBarThumb, (int) thumbWidth, (int) thumbWidth, false);
    }

    /**
     * Get the progress of the CircularSeekBar.
     * 
     * @return The progress of the CircularSeekBar.
     */
    public int getProgress() {
        int progress = Math.round((float) mMax * mProgressDegrees / mTotalCircleDegrees);
        return progress;
    }

    /**
     * Set the progress of the CircularSeekBar. If the progress is the same, then any listener will not receive a onProgressChanged event.
     * 
     * @param progress
     *            The progress to set the CircularSeekBar to.
     */
    public void setProgress(float progress) {
        if (mProgress != progress) {
            mProgress = progress;
            if (mOnCircularSeekBarChangeListener != null) {
                mOnCircularSeekBarChangeListener.onProgressChanged(this, progress, false);
            }

            recalculateAll();
            invalidate();
        }
    }

    private void setProgressBasedOnAngle(float angle) {
        mPointerPosition = angle;
        calculateProgressDegrees();
        mProgress = Math.round((float) mMax * mProgressDegrees / mTotalCircleDegrees);
    }

    private void recalculateAll() {
        calculateTotalDegrees();
        calculatePointerAngle();
        calculateProgressDegrees();

        initRects();

        initPaths();

        calculatePointerXYPosition();
    }

    private void recalculateAllExpectSendingTheValueToBackground() {
        calculateTotalDegrees();
        calculatePointerAngle();
        calculateProgressDegreesWithoutSendingTheValueToBackground();

        initRects();

        initPaths();

        calculatePointerXYPosition();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        if (mMaintainEqualCircle) {
            int min = Math.min(mWidth, mHeight);
            setMeasuredDimension(min, min);
        } else {
            setMeasuredDimension(mWidth, mHeight);
        }

        // Set the circle width and height based on the view for the moment
        mCircleHeight = (float) mHeight / 2f - mCircleStrokeWidth - mPointerRadius - (mPointerHaloBorderWidth * 1.5f);
        mCircleWidth = (float) mWidth / 2f - mCircleStrokeWidth - mPointerRadius - (mPointerHaloBorderWidth * 1.5f);

        // If it is not set to use custom
        if (mCustomRadii) {
            // Check to make sure the custom radii are not out of the view. If
            // they are, just use the view values
            if ((mCircleYRadius - mCircleStrokeWidth - mPointerRadius - mPointerHaloBorderWidth) < mCircleHeight) {
                mCircleHeight = mCircleYRadius - mCircleStrokeWidth - mPointerRadius - (mPointerHaloBorderWidth * 1.5f);
            }

            if ((mCircleXRadius - mCircleStrokeWidth - mPointerRadius - mPointerHaloBorderWidth) < mCircleWidth) {
                mCircleWidth = mCircleXRadius - mCircleStrokeWidth - mPointerRadius - (mPointerHaloBorderWidth * 1.5f);
            }
        }

        if (mMaintainEqualCircle) { // Applies regardless of how the values weredetermined
            if (mCircleHeight < mCircleWidth) {
                mCircleHeight = mCircleHeight;
                mCircleWidth = mCircleHeight;
            } else {
                mCircleHeight = mCircleWidth;
                mCircleWidth = mCircleWidth;
            }
        }

        recalculateAll();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Convert coordinates to our internal coordinate system
        float x = event.getX() - getWidth() / 2;
        float y = event.getY() - getHeight() / 2;

        // Get the distance from the center of the circle in terms of x and y
        float distanceX = mCircleRectF.centerX() - x;
        float distanceY = mCircleRectF.centerY() - y;

        // Get the distance from the center of the circle in terms of a radius
        float touchEventRadius = (float) Math.sqrt((Math.pow(distanceX, 2) + Math.pow(distanceY, 2)));

        float minimumTouchTarget = MIN_TOUCH_TARGET_DP * DPTOPX_SCALE; // Convert minimum touch target into px
        float additionalRadius = minimumTouchTarget / 2; // Either uses the minimumTouchTargetsize or larger if the ring/pointer is larger

        if (mCircleStrokeWidth < minimumTouchTarget) { // If the width is less than the minimumTouchTarget, use the minimumTouchTarget
            additionalRadius = minimumTouchTarget / 2;
        } else {
            additionalRadius = mCircleStrokeWidth / 2; // Otherwise use the width
        }
        float outerRadius = Math.max(mCircleHeight, mCircleWidth) + additionalRadius; // Max outer radius of the circle, including the minimumTouchTarget or wheel width
        float innerRadius = Math.min(mCircleHeight, mCircleWidth) - additionalRadius; // Min inner radius of the circle, including the minimumTouchTarget or wheel width

        if (mPointerRadius < (minimumTouchTarget / 2)) { // If the pointer radius is less than the minimumTouchTarget, use the minimumTouchTarget
            additionalRadius = minimumTouchTarget / 2;
        } else {
            additionalRadius = mPointerRadius; // Otherwise use the radius
        }

        float touchAngle;
        touchAngle = (float) ((java.lang.Math.atan2(y, x) / Math.PI * 180) % 360); // Verified
        touchAngle = (touchAngle < 0 ? 360 + touchAngle : touchAngle); // Verified

        cwDistanceFromStart = touchAngle - mStartAngle; // Verified
        cwDistanceFromStart = (cwDistanceFromStart < 0 ? 360f + cwDistanceFromStart : cwDistanceFromStart); // Verified
        ccwDistanceFromStart = 360f - cwDistanceFromStart; // Verified

        cwDistanceFromEnd = touchAngle - mEndAngle; // Verified
        cwDistanceFromEnd = (cwDistanceFromEnd < 0 ? 360f + cwDistanceFromEnd : cwDistanceFromEnd); // Verified
        ccwDistanceFromEnd = 360f - cwDistanceFromEnd; // Verified

        if (calculateAngles) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // These are only used for ACTION_DOWN for handling if the
                    // pointer was the part that was touched
                    float pointerRadiusDegrees = (float) ((mPointerRadius * 180) / (Math.PI * Math.max(mCircleHeight, mCircleWidth)));
                    cwDistanceFromPointer = touchAngle - mPointerPosition;
                    cwDistanceFromPointer = (cwDistanceFromPointer < 0 ? 360f + cwDistanceFromPointer : cwDistanceFromPointer);
                    ccwDistanceFromPointer = 360f - cwDistanceFromPointer;
                    // This is for if the first touch is on the actual pointer.
                    if (((touchEventRadius >= innerRadius) && (touchEventRadius <= outerRadius)) && ((cwDistanceFromPointer <= pointerRadiusDegrees) || (ccwDistanceFromPointer <= pointerRadiusDegrees))) {
                        setProgressBasedOnAngle(mPointerPosition);
                        lastCWDistanceFromStart = cwDistanceFromStart;
                        mIsMovingCW = true;
                        recalculateAll();
                        invalidate();
                        if (mOnCircularSeekBarChangeListener != null) {
                            mOnCircularSeekBarChangeListener.onStartTrackingTouch(this);
                        }
                        mUserIsMovingPointer = true;
                        lockAtEnd = false;
                        lockAtStart = false;
                    } else if (cwDistanceFromStart > mTotalCircleDegrees) { // If the user is touching outside of the start AND end
                        mUserIsMovingPointer = false;
                        return false;
                    } else if ((touchEventRadius >= innerRadius) && (touchEventRadius <= outerRadius)) { // If the user is touching near the circle
                        setProgressBasedOnAngle(touchAngle);
                        lastCWDistanceFromStart = cwDistanceFromStart;
                        mIsMovingCW = true;
                        // mPointerHaloPaint.setAlpha(mPointerAlphaOnTouch);
                        // mPointerHaloPaint.setColor(mPointerHaloColorOnTouch);
                        recalculateAll();
                        invalidate();
                        if (mOnCircularSeekBarChangeListener != null) {
                            mOnCircularSeekBarChangeListener.onStartTrackingTouch(this);
                            mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, true);
                        }
                        mUserIsMovingPointer = true;
                        lockAtEnd = false;
                        lockAtStart = false;
                    } else { // If the user is not touching near the circle
                        mUserIsMovingPointer = false;
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mUserIsMovingPointer) {
                        if (lastCWDistanceFromStart < cwDistanceFromStart) {
                            if ((cwDistanceFromStart - lastCWDistanceFromStart) > 180f && !mIsMovingCW) {
                                lockAtStart = true;
                                lockAtEnd = false;
                            } else {
                                mIsMovingCW = true;
                            }
                        } else {
                            if ((lastCWDistanceFromStart - cwDistanceFromStart) > 180f && mIsMovingCW) {
                                lockAtEnd = true;
                                lockAtStart = false;
                            } else {
                                mIsMovingCW = false;
                            }
                        }

                        if (lockAtStart && mIsMovingCW) {
                            lockAtStart = false;
                        }
                        if (lockAtEnd && !mIsMovingCW) {
                            lockAtEnd = false;
                        }
                        if (lockAtStart && !mIsMovingCW && (ccwDistanceFromStart > 90)) {
                            lockAtStart = false;
                        }
                        if (lockAtEnd && mIsMovingCW && (cwDistanceFromEnd > 90)) {
                            lockAtEnd = false;
                        }
                        // Fix for passing the end of a semi-circle quickly
                        if (!lockAtEnd && cwDistanceFromStart > mTotalCircleDegrees && mIsMovingCW && lastCWDistanceFromStart < mTotalCircleDegrees) {
                            lockAtEnd = true;
                        }

                        if (lockAtStart) {
                            // TODO: Add a check if mProgress is already 0, in
                            // which
                            // case don't call the listener
                            mProgress = 0;
                            recalculateAll();
                            invalidate();
                            if (mOnCircularSeekBarChangeListener != null) {
                                mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, true);
                            }
                        } else if (lockAtEnd) {
                            mProgress = mMax;
                            recalculateAll();
                            invalidate();
                            if (mOnCircularSeekBarChangeListener != null) {
                                mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, true);
                            }
                        } else if ((mMoveOutsideCircle) || (touchEventRadius <= outerRadius)) {
                            if (!(cwDistanceFromStart > mTotalCircleDegrees)) {
                                setProgressBasedOnAngle(touchAngle);
                            }
                            recalculateAll();
                            invalidate();
                            if (mOnCircularSeekBarChangeListener != null) {
                                mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, true);
                            }
                        } else {
                            break;
                        }

                        lastCWDistanceFromStart = cwDistanceFromStart;
                    } else {
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (mUserIsMovingPointer) {
                        mUserIsMovingPointer = false;
                        invalidate();
                        if (mOnCircularSeekBarChangeListener != null) {
                            mOnCircularSeekBarChangeListener.onStopTrackingTouch(this);
                        }
                    } else {
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_CANCEL: // Used when the parent view
                                                // intercepts touches for things
                                                // like scrolling
                    mUserIsMovingPointer = false;
                    invalidate();
                    break;
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE && getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return true;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        Bundle state = new Bundle();
        state.putParcelable("PARENT", superState);
        state.putInt("MAX", mMax);
        state.putFloat("PROGRESS", mProgress);
        state.putInt("mCircleColor", mCircleColor);
        state.putInt("mCircleProgressColor", mCircleProgressColor);
        state.putInt("mPointerColor", mPointerColor);
        state.putInt("mPointerHaloColor", mPointerHaloColor);
        state.putInt("mPointerHaloColorOnTouch", mPointerHaloColorOnTouch);
        state.putInt("mPointerAlpha", mPointerAlpha);
        state.putInt("mPointerAlphaOnTouch", mPointerAlphaOnTouch);

        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle savedState = (Bundle) state;

        Parcelable superState = savedState.getParcelable("PARENT");
        super.onRestoreInstanceState(superState);

        mMax = savedState.getInt("MAX");
        mProgress = savedState.getInt("PROGRESS");
        mCircleColor = savedState.getInt("mCircleColor");
        mCircleProgressColor = savedState.getInt("mCircleProgressColor");
        mPointerColor = savedState.getInt("mPointerColor");
        mPointerHaloColor = savedState.getInt("mPointerHaloColor");
        mPointerHaloColorOnTouch = savedState.getInt("mPointerHaloColorOnTouch");
        mPointerAlpha = savedState.getInt("mPointerAlpha");
        mPointerAlphaOnTouch = savedState.getInt("mPointerAlphaOnTouch");

        initPaints();

        recalculateAll();
    }

    public void setOnSeekBarChangeListener(OnCircularSeekBarChangeListener l) {
        mOnCircularSeekBarChangeListener = l;
    }

    /**
     * Listener for the CircularSeekBar. Implements the same methods as the normal OnSeekBarChangeListener.
     */
    public interface OnCircularSeekBarChangeListener {

        public abstract void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser);

        public abstract void onStopTrackingTouch(CircularSeekBar seekBar);

        public abstract void onStartTrackingTouch(CircularSeekBar seekBar);
    }

    /**
     * Set the max of the CircularSeekBar. If the new max is less than the current progress, then the progress will be set to zero. If the progress is changed as a result, then any listener will receive a onProgressChanged event.
     * 
     * @param max
     *            The new max for the CircularSeekBar.
     */
    public void setMax(int max) {
        if (!(max <= 0)) { // Check to make sure it's greater than zero
            if (max <= mProgress) {
                mProgress = 0; // If the new max is less than current progress,
                               // set progress to zero
                if (mOnCircularSeekBarChangeListener != null) {
                    mOnCircularSeekBarChangeListener.onProgressChanged(this, mProgress, false);
                }
            }
            mMax = max;

            recalculateAll();
            invalidate();
        }
    }

    public void setShowCircle(Boolean value) {
        this.showCircle = value;
    }

    public void setShowThumb(Boolean value) {
        this.showThumb = value;
        calculateAngles = value;
        invalidate();
    }

    public void setBottomCircularBar(CircularSeekBar instance) {
        this.bottomCircularSeekBar = instance;
    }

    public void setProgressDegrees(float value) {
        this.mProgressDegrees = value;
        invalidate();
    }

    public void setTextTime(boolean value) {
        // System.out.println("================================ values" + value);
    
        this.showTime = value;
    }

    public int getThumbDim() {
        // return seekBarThumb.getWidth() * getWidth() / 928;
        // System.out.println("=================== margin" + thumbWidth);
        return (int) thumbWidth;
    }

    public float getProgressDegrees() {
        return mProgressDegrees;
    }

    public void calculateProgressFromTouchEvents(Boolean value) {
        this.calculateAngles = value;
    }

    /**
     * set the minimum, the maximum and the starting values in seconds
     * 
     * @param angle
     */
    public void setSeekBarValues(int min, int max, float startingValue) {
        if (max != 0) {
            this.minAngle = (min * 360) / max;
            this.mProgress = (startingValue * 360) / max;

            this.minValue = min;
            this.maxValue = max;
         
        } else {
            
            minAngle = 0;
            mProgress = 0;
            maxValue = 1;
        }
        recalculateAllExpectSendingTheValueToBackground();
        invalidate();
    }

    public void setLargeDigitalDouble() {
        this.largeDigitalDouble = true;
        this.largeNormalSingle = false;
        this.smallDigitalDouble = false;
        this.smallDigitalSingle = false;

        this.separator = this.separatorLargeDigitalDouble;

        this.chosenTextSize = textSizeLargeDigitalDouble;

        this.paint.setTypeface(digitalTypeface);
    }

    public void setLargeNormalSingle() {
        this.largeDigitalDouble = false;
        this.largeNormalSingle = true;
        this.smallDigitalDouble = false;
        this.smallDigitalSingle = false;

        this.chosenTextSize = this.textSizeLargeNormalSingle;

        Typeface newTypeFace = Typeface.create(normalTypeface, Typeface.ITALIC);

        this.paint.setTypeface(newTypeFace);
    }

    public void setSmallDigitalDouble() {
        this.largeDigitalDouble = false;
        this.largeNormalSingle = false;
        this.smallDigitalDouble = true;
        this.smallDigitalSingle = false;

        this.separator = this.separatorSmallDigitalDouble;

        this.chosenTextSize = this.textSizeSmallDigitalDouble;

        this.paint.setTypeface(digitalTypeface);
    }

    public void setSmallDigitalSingle() {
        this.largeDigitalDouble = false;
        this.largeNormalSingle = false;
        this.smallDigitalDouble = false;
        this.smallDigitalSingle = true;

        this.chosenTextSize = this.textSizeSmallDigitalSingle;

        this.paint.setTypeface(digitalTypeface);
    }

    public void setSeekBarColor(Bitmap bitmap) {
        this.seekBarProgress = bitmap;
        invalidate();
    }

    public float getProgressValue() {
        return mProgress;
    }

    public void setCurrentProgress(int seconds) {
        this.mProgress = (seconds * 360) / this.maxValue;

        invalidate();
    }

    public void setIsSmall(boolean isSmall) {
        this.isSmall = isSmall;
    }

    public void setProgressImageType(int type) {

        switch (type) {
            case CircularSeekBarHandler.PROGRESS_TYPE_BLUE:
                if (isSmall) {
                    setSeekBarColor(BitmapFactory.decodeResource(getResources(), R.drawable.circular_seekbar_gradient_background_small));
                } else {
                    setSeekBarColor(BitmapFactory.decodeResource(getResources(), R.drawable.circular_seekbar_gradient_background_large));
                }
                break;
            case CircularSeekBarHandler.PROGRESS_TYPE_GREEN:
                if (isSmall) {
                    setSeekBarColor(BitmapFactory.decodeResource(getResources(), R.drawable.circular_seekbar_green_small));
                } else {
                    setSeekBarColor(BitmapFactory.decodeResource(getResources(), R.drawable.circular_seekbar_green_large));
                }
                break;
            case CircularSeekBarHandler.PROGRESS_TYPE_MIXED:
                setSeekBarColor(BitmapFactory.decodeResource(getResources(), R.drawable.circular_seekbar_mixed_background_small));
                break;
        }

    }

    public void setRoundLastDigit(boolean value) {
        this.roundLastDigit = value;
    }

    public int getProgressInMillis() {
        return (int) totalSeconds * 1000;
    }
}
