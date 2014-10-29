
package com.innovzen.animations.entities;

import java.util.List;

import android.graphics.Canvas;

import com.innovzen.animations.view.LungsView;

public class BubblesHandler {

    private List<Bubble> mLeftBubbles;
    private List<Bubble> mRightBubbles;

    public BubblesHandler(List<Bubble> leftBubbles, List<Bubble> rightBubbles) {
        mLeftBubbles = leftBubbles;
        mRightBubbles = rightBubbles;
    }

    public void setLeftBubbles(List<Bubble> list) {
        mLeftBubbles = list;
    }

    public void setrightBubbles(List<Bubble> list) {
        mRightBubbles = list;
    }

    /**
     * Calls the {@link Bubble#draw(Canvas, float)} method of all the bubbles, left and right.
     * 
     * @param canvas
     * @param stepProgress
     * @author MAB
     */
    public void draw(LungsView view, Canvas canvas, float stepProgress) {

        if (mLeftBubbles != null) {
            for (Bubble bubble : mLeftBubbles) {
                bubble.draw(view, canvas, stepProgress);
            }
        }

        if (mRightBubbles != null) {
            for (Bubble bubble : mRightBubbles) {
                bubble.draw(view, canvas, stepProgress);
            }
        }
    }
}
