
package com.innovzen.animations.entities;

import java.util.List;

public class AnimationLungsStages {

    // Hold the membership of each bubble to each group of bubbles
    public float[][] membershipLeft;
    public float[][] membershipRight;

    // Hold the list of positions for the bubbles for every stage
    public List<LungsStagePositions> stagePositions;

    /**
     * Get and store the starting positions for each stage for the left bubbles
     * 
     * @return
     * @author MAB
     */
    public float[][] getStartingPositionsLeft() {

        if (stagePositions == null || stagePositions.size() != 3) {
            return null;
        }

        return new float[][] {
                stagePositions.get(0).startPosLeft, stagePositions.get(1).startPosLeft, stagePositions.get(2).startPosLeft
        };

    }

    /**
     * Get and store the starting positions for each stage for the right bubbles
     * 
     * @return
     * @author MAB
     */
    public float[][] getStartingPositionsRight() {

        if (stagePositions == null || stagePositions.size() != 3) {
            return null;
        }

        return new float[][] {
                stagePositions.get(0).startPosRight, stagePositions.get(1).startPosRight, stagePositions.get(2).startPosRight
        };

    }

}
