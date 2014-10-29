
package com.innovzen.animations.entities;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.innovzen.animations.view.LungsView;

public class Bubble {

    // The top and left (x and Y coordinates)
    private static final int LEFT = 0;
    private static final int TOP = 1;

    /** The number of stages in the lungs animation */
    public static final int LUNGS_NR_STAGES = 3;

    /** The current stage in the lungs animation in which this bubble is at the moment. Different bubbles may be in different stage at any one point in time. */
    private int mCurStageIndex = -1;

    /** Rectangle used to specify the position of the bubble */
    private Rect mRect = new Rect();

    /**
     * The starting position of this bubble. May differ from the stage/step ones because of the offset.<br/>
     * <b>1x3 array</b>
     */
    private float[] mStagesStart = new float[3];

    /**
     * A list of 3x2 values. Each row represents an (x,y) coordinate for the START position in the specific stage<br/>
     * <b>3x2 array of fractions</b>
     */
    private float[][] mStartPositions = new float[3][2];

    /**
     * A list of 3x2 values. Each row represents an (x,y) coordinate for the END position in the specific stage<br/>
     * <b>3x2 array</b>
     */
    private float[][] mEndPositions = new float[3][2];

    /**
     * If available for the current step, draws a bubble on the canvas at (pre)calculated coordinates
     * 
     * @param view
     * @param canvas
     *            the canvas on which to draw the bubble
     * @param stepProgress
     *            the overall step progress (inhale, exhale, holdinhale, holdexhale)
     * @author MAB
     */
    public void draw(LungsView view, Canvas canvas, float stepProgress) {

        // Determine the appropriate stage in which this bubble needs to be right now
        setCurStage(stepProgress);

        if (view == null || canvas == null || stepProgress == -1 || mCurStageIndex == -1) {
            return;
        }

        // Get the stage progress (different from the step progress because of the offset and all that :) )
        float stageProgress = getLocalProgressFromStepProgress(stepProgress);

        /*
         * Determine the left and top positions for this bubble
         * { [ ( e - s) * p + s ] * w } - (b/2)
         */
        float left = mEndPositions[mCurStageIndex][LEFT] - mStartPositions[mCurStageIndex][LEFT];
        float top = mEndPositions[mCurStageIndex][TOP] - mStartPositions[mCurStageIndex][TOP];

        left *= stageProgress;
        top *= stageProgress;

        left += mStartPositions[mCurStageIndex][LEFT];
        top += mStartPositions[mCurStageIndex][TOP];

        left *= view.width;
        top *= view.height;

        left = left - view.bubbleDimHalf;
        top = top - view.bubbleDimHalf;

        // Set up the bounds
        mRect.set((int) left, (int) top, (int) (left + view.bubbleDim), (int) (top + view.bubbleDim));

        // Draw the bubble
        canvas.drawBitmap(view.bubbleBmp, null, mRect, null);
    }

    /**
     * Based on the overall step progress, determines in which stage of the lungs animation this bubble needs to be and sets the index of that stage
     * 
     * @param stepProgress
     * @author MAB
     */
    private void setCurStage(float stepProgress) {

        if (mStagesStart.length != 3) {
            return;
        }

        // STAGE 0
        if (stepProgress >= mStagesStart[0] && stepProgress <= mStagesStart[1] && mCurStageIndex != 0) {
            mCurStageIndex = 0;
        } else
        // STAGE 1
        if (stepProgress > mStagesStart[1] && stepProgress <= mStagesStart[2] && mCurStageIndex != 1) {
            mCurStageIndex = 1;
        } else
        // STAGE 2
        if (stepProgress > mStagesStart[2] && mCurStageIndex != 2) {
            mCurStageIndex = 2;
        }
    }

    public void setStartPosition(int stage, float[] position) {
        if (stage >= 0 && stage < 3) {
            mStartPositions[stage] = position;
        }
    }

    public void setEndPosition(int stage, float[] position) {
        if (stage >= 0 && stage < 3) {
            mEndPositions[stage] = position;
        }
    }

    public void setStageStart(int stage, float progress) {
        if (stage >= 0 && stage < 3) {
            mStagesStart[stage] = progress;
        }
    }

    /**
     * Based on the step progress (inhale, exhale, ...) it determines the progress of the appropriate animation stage
     * 
     * @return
     * @author MAB
     */
    private float getLocalProgressFromStepProgress(float stepProgress) {

        float start = 0;
        float end = 0;

        switch (mCurStageIndex) {
            case 0:
                start = mStagesStart[0];
                end = mStagesEnd[0];
                break;
            case 1:
                start = mStagesStart[1];
                end = mStagesEnd[1];
                break;
            case 2:
                start = mStagesStart[2];
                end = mStagesEnd[2];
                break;
        }

        if (stepProgress > mStagesEnd[2]) {
            stepProgress = mStagesEnd[2];
        }

        if (end >= start && stepProgress >= start && stepProgress <= end) {

            return (stepProgress - start) / (end - start);

        }

        return 0f;
    }

    // --------------------------------------------------------------------
    private float[] mStagesEnd = new float[3];

    public void setStageEnd(int stage, float progress) {
        if (stage >= 0 && stage < 3) {
            mStagesEnd[stage] = progress;
        }
    }
}
