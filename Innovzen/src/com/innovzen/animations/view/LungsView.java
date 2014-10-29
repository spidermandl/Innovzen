
package com.innovzen.animations.view;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.innovzen.o2chair.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.innovzen.animations.entities.AnimationLungsStages;
import com.innovzen.animations.entities.Bubble;
import com.innovzen.animations.entities.BubblesHandler;
import com.innovzen.animations.entities.LungsStagePositions;
import com.innovzen.ui.DialogFactory;
import com.innovzen.utils.JsonUtil;

public class LungsView extends View {

    // Hold the ratio of the bubble height (and with. it's a square) in respect to the image height
    private static final float BUBBLE_DIM_RATIO = 0.05f;

    // Hold the thresholds that indicate when to trigger a new stage in the animation (fraction)
    private static float TRIGGER_STAGE_ONE = 0f;
    private static float TRIGGER_STAGE_TWO = 0.5f;
    private static float TRIGGER_STAGE_THREE = 0.7f;
    // private static float TRIGGER_STAGE_TWO = 0f;
    // private static float TRIGGER_STAGE_THREE = 0.5f;

    /** Hold the context */
    private Context mCtx;

    /** Hold the bitmap bubble into memory for quick reference and drawing on the canvas */
    public Bitmap bubbleBmp;

    /** Hold the dimensions of the lungs view */
    public int width = -1, height = -1;

    /** Hold the bubble dimensions (width and height are the same. it's a square) */
    public int bubbleDim = -1;

    /** The dimension of the bubble (width or height, doesn't matter, it's a square) halved */
    public float bubbleDimHalf = -1;

    /** The lists of left and right bubbles */
    private BubblesHandler mBubbles;

    /**
     * The current stage progress Keep it for the onDraw method
     */
    private float mCurStageTimeProgress = -1;

    // /** Hold the list of positions for the bubbles for every stages */
    // private AnimationLungsStages mStages;

    /** The starting positions of each of the stages */
    public float[][] startPositionsLeft;

    /** The starting positions of each of the stages */
    public float[][] startPositionsRight;

    // /** Keep the current stage index */
    // public int curStageIndex = -1;

    /** Hold the bubble rect */
    private Rect mRect;

    // /** Current lung stage handler in use */
    // private LungsStagePositions mCurLungStagePositions;

    public LungsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mCtx = context;

        init();
    }

    public LungsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mCtx = context;

        init();
    }

    public LungsView(Context context) {
        super(context);

        this.mCtx = context;

        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        // Draw the bubbles
        if (mBubbles != null && mCurStageTimeProgress != -1) {
            mBubbles.draw(this, canvas, mCurStageTimeProgress);
        }

    }

    /**
     * Does proper initializations
     * 
     * @author MAB
     */
    private void init() {

        mRect = new Rect();

    }

    /**
     * @param stepTimeFraction
     *            the time spent in the current exercise step<br/>
     *            value in interval [0.0, 1.0]
     * 
     * @param globalTimeFraction
     *            the amount of time passed from the beginning of the entire exercise<br/>
     *            value in interval [0.0, 1.0]
     */
    public void inhale(float stepTimeFraction, float globalTimeFraction) {

        // // Set the current stage in the animation
        // setCurAnimationStage(stepTimeFraction, TRIGGER_STAGE_ONE, TRIGGER_STAGE_TWO, TRIGGER_STAGE_THREE);

        // Store it. We'll use it later in the onDraw method
        mCurStageTimeProgress = stepTimeFraction;

        // Force the onDraw method to be called
        invalidate();

    }

    /**
     * @param stepTimeFraction
     *            the time spent in the current exercise step<br/>
     *            value in interval [0.0, 1.0]
     * @param globalTimeFraction
     *            the amount of time passed from the beginning of the entire exercise<br/>
     *            value in interval [0.0, 1.0]
     */
    public void exhale(float stepTimeFraction, float globalTimeFraction) {

        // // Set the current stage in the animation
        // setCurAnimationStage(stepTimeFraction, 1f - TRIGGER_STAGE_ONE, 1f - TRIGGER_STAGE_TWO, 1f - TRIGGER_STAGE_THREE);

        // Store it. We'll use it later in the onDraw method
        mCurStageTimeProgress = 1f - stepTimeFraction;

        // Force the onDraw method to be called
        invalidate();
    }

    /**
     * Loads the bubble bitmap into memory and resized it based on the dimensions of this view<br/>
     * IMPORTANT !! Call this method only after the width and height are available (e.g. you could use {@link ViewTreeObserver} for that)
     * 
     * @author MAB
     */
    public void initBitmapBubble() {

        // Keep the dimensions of the view
        width = getWidth();
        height = getHeight();

        // Determine the dimensions of the bubble based on the dimensions of the view
        bubbleDim = (int) (height * BUBBLE_DIM_RATIO);
        bubbleDimHalf = bubbleDim / 2;

        // Retrieve the bubble bitmap from the resourcesO
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.exercise_animation_lungs_bubble);

        // Resize and store the bubble bitmap
        bubbleBmp = Bitmap.createScaledBitmap(image, bubbleDim, bubbleDim, false);

    }

    AnimationLungsStages stages;

    /**
     * Loads the array of positions for the animation stages from the raw folder
     * 
     * @author MAB
     */
    public void initPositions() {

        // Get the input stream for the .json file that holds the sound info
        InputStream in = mCtx.getResources().openRawResource(R.raw.animation_lungs_bubbles_positions);

        try {
            // Parse the json
            stages = JsonUtil.jsonToObject(in, AnimationLungsStages.class);

            // Get and store the starting positions for each stage for the left and right bubbles
            startPositionsLeft = stages.getStartingPositionsLeft();
            startPositionsRight = stages.getStartingPositionsRight();

            // Create the lust of bubbles
            createBubblesList(stages);

            // if (mStages != null) {
            // mCurLungStepPositions = mSteps.stepPositions.get(0);
            // }
        } catch (Exception e) {
            e.printStackTrace();
            DialogFactory.showUserExperienceDialog(mCtx, mCtx.getResources().getString(R.string.dialog_user_experience_could_not_load_lungs_buble_positions), null);
        }

    }

    /**
     * From the data read in the JSON, calculate the delays for each bubble, their starting positions, etc
     * 
     * @param stages
     * @author MAB
     */
    private void createBubblesList(AnimationLungsStages stages) {

        // Instantiates bubbles lists
        List<Bubble> leftBubbles = new ArrayList<Bubble>();
        List<Bubble> rightBubbles = new ArrayList<Bubble>();

        // Calculate the delay for the bubbles in the first stage of the animation
        // Value in PROGRESS FRACTION
        float delayFractionProgress1 = (TRIGGER_STAGE_TWO - TRIGGER_STAGE_ONE) / 2f / 10;
        float delayFractionProgress2L = (TRIGGER_STAGE_THREE - TRIGGER_STAGE_TWO) / 1.1f / 10;
        float delayFractionProgress2R = (TRIGGER_STAGE_THREE - TRIGGER_STAGE_TWO) / 1.1f / 10;
        float delayFractionProgress3L = (1f - TRIGGER_STAGE_THREE) / 2f / 10;
        float delayFractionProgress3R = (1f - TRIGGER_STAGE_THREE) / 2f / 10;

        // Go through all the stages
        for (int iStage = 0; iStage < stages.stagePositions.size(); iStage++) {

            // Get the positions (both left and right) for the current stage
            LungsStagePositions positions = stages.stagePositions.get(iStage);

            // Go through the left side bubbles
            for (int iLeft = 0; iLeft < positions.endPosLeft.length; iLeft++) {

                // If the bubble has not yet been instantiated, then do so right now
                if (leftBubbles.size() <= iLeft) {
                    leftBubbles.add(new Bubble());
                }

                // Get the current bubble in which we need to add the stuff
                Bubble curBubble = leftBubbles.get(iLeft);

                // Set the starting point of the bubble for this stage
                curBubble.setStartPosition(iStage, positions.startPosLeft);

                // Set the ending point of the bubble for this stage
                curBubble.setEndPosition(iStage, positions.endPosLeft[iLeft]);

                // Calculate and set the start trigger for this bubble for this stage
                // Take the offset into consideration

                float stageStart = 0;
                float stageEnd = 0;
                switch (iStage) {
                    case 0:
                        stageStart = TRIGGER_STAGE_ONE + stages.membershipLeft[iStage][iLeft] * delayFractionProgress1;
                        stageEnd = TRIGGER_STAGE_TWO - (10 - 1 - stages.membershipLeft[iStage][iLeft]) * delayFractionProgress1;
                        break;
                    case 1:
                        stageStart = TRIGGER_STAGE_TWO - (10 - 1 - stages.membershipLeft[iStage][iLeft]) * delayFractionProgress1;
                        stageEnd = TRIGGER_STAGE_THREE - (10 - 1 - stages.membershipLeft[iStage][iLeft]) * delayFractionProgress2L;
                        break;
                    case 2:
                        stageStart = TRIGGER_STAGE_THREE - (10 - 1 - stages.membershipLeft[iStage][iLeft]) * delayFractionProgress2L;
                        stageEnd = 1f - (10 - 1 - stages.membershipLeft[iStage][iLeft]) * delayFractionProgress3L;
                        break;
                }

                // float stageStart = stages.membershipLeft[iStage][iLeft] * delayFractionProgress1 * (iStage + 1);

                // float stageStart = TRIGGER_STAGE_ONE;
                // if (iStage == 1) {
                // stageStart = TRIGGER_STAGE_TWO;
                // } else if (iStage == 2) {
                // stageStart = TRIGGER_STAGE_THREE;
                // }

                curBubble.setStageStart(iStage, stageStart);
                curBubble.setStageEnd(iStage, stageEnd);

            }

            // Go through the right side bubbles
            for (int iRight = 0; iRight < positions.endPosRight.length; iRight++) {

                // If the bubble has not yet been instantiated, then do so right now
                if (rightBubbles.size() <= iRight) {
                    rightBubbles.add(new Bubble());
                }

                // Get the current bubble in which we need to add the stuff
                Bubble curBubble = rightBubbles.get(iRight);

                // Set the starting point of the bubble for this stage
                curBubble.setStartPosition(iStage, positions.startPosRight);

                // Set the ending point of the bubble for this stage
                curBubble.setEndPosition(iStage, positions.endPosRight[iRight]);

                // Calculate and set the start trigger for this bubble for this stage
                // Take the offset into consideration
                // float stageStart = stages.membershipRight[iStage][iRight] * delayFractionProgress * (iStage + 1);

                float stageStart = 0;
                float stageEnd = 0;
                switch (iStage) {
                    case 0:
                        stageStart = TRIGGER_STAGE_ONE + stages.membershipRight[iStage][iRight] * delayFractionProgress1;
                        stageEnd = TRIGGER_STAGE_TWO - (10 - 1 - stages.membershipRight[iStage][iRight]) * delayFractionProgress1;
                        break;
                    case 1:
                        stageStart = TRIGGER_STAGE_TWO - (10 - 1 - stages.membershipRight[iStage][iRight]) * delayFractionProgress1;
                        stageEnd = TRIGGER_STAGE_THREE - (10 - 1 - stages.membershipRight[iStage][iRight]) * delayFractionProgress2R;
                        break;
                    case 2:
                        stageStart = TRIGGER_STAGE_THREE - (10 - 1 - stages.membershipRight[iStage][iRight]) * delayFractionProgress2R;
                        stageEnd = 1f - (10 - 1 - stages.membershipRight[iStage][iRight]) * delayFractionProgress3R;
                        break;
                }

                curBubble.setStageStart(iStage, stageStart);
                curBubble.setStageEnd(iStage, stageEnd);

            }
        }

        // Instantiate the handler and set the two lists
        mBubbles = new BubblesHandler(leftBubbles, rightBubbles);
    }

    // private float calculateBubbleStageStart(int stageIndex, int bubbleStageIndex) {
    //
    // switch (stageIndex) {
    // case 0:
    //
    // break;
    // case 1:
    // break;
    // case 2:
    // break;
    // }
    // }

    // /**
    // * Based on the progress of the animation in the current stage (inhale, exhale, holdinhale, holdexhale), determine the appropriate stage in the lungs animation
    // *
    // * @param stepTimeFraction
    // * @author MAB
    // */
    // private void setCurAnimationStage(float stepTimeFraction, float stage0, float stage1, float stage2) {
    // // STAGE 0
    // if (stepTimeFraction >= stage0 && stepTimeFraction <= stage1 && mCurStageIndex != 0) {
    // mCurStageIndex = 0;
    // mCurLungStagePositions = mStages.stagePositions.get(0);
    // } else
    // // STAGE 1
    // if (stepTimeFraction > stage1 && stepTimeFraction <= stage2 && mCurStageIndex != 1) {
    // mCurStageIndex = 1;
    // mCurLungStagePositions = mStages.stagePositions.get(1);
    // } else
    // // STAGE 2
    // if (stepTimeFraction > stage2 && mCurStageIndex != 2) {
    // mCurStageIndex = 2;
    // mCurLungStagePositions = mStages.stagePositions.get(2);
    // }
    // }

    // /**
    // * Based on the step progress (inhale, exhale, ...) it determines the progress of the appropriate animation stage
    // *
    // * @return
    // * @author MAB
    // */
    // private float getLocalProgressFromStepProgress(float stepTimeFraction) {
    //
    // float start = 0;
    // float end = 0;
    //
    // switch (mCurStageIndex) {
    // case 0:
    // start = TRIGGER_STAGE_ONE;
    // end = TRIGGER_STAGE_TWO;
    // break;
    // case 1:
    // start = TRIGGER_STAGE_TWO;
    // end = TRIGGER_STAGE_THREE;
    // break;
    // case 2:
    // start = TRIGGER_STAGE_THREE;
    // end = 1f;
    // break;
    // }
    //
    // if (end >= start && stepTimeFraction >= start && stepTimeFraction <= end) {
    //
    // return (stepTimeFraction - start) / (end - start);
    //
    // }
    //
    // return 0;
    //
    // }
}
