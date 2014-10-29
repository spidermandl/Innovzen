package com.innovzen.animations.entities;


/**
 * 
 * See link for calculated positions<br/>
 * https://docs.google.com/spreadsheets/d/1kbqtlLaA5bgZw9mvXvgJYu9kuPraALHMuR7ggz1orA8/edit#gid=170125102
 * 
 * @author MAB
 */
public class LungsStagePositions {

	/**
	 * The index of the stage in the overall animation<br/>
	 * Value needs to be >= 0
	 */
	public int stageIndex = -1;

	/**
	 * The number of bubbles displayed for this stage on the left side of the animation<br/>
	 * NOTE ! In case there is only one row of bubbles for this stage, then either this or {@link #nrItemsRight} can be 0
	 */
	public int nrItemsLeft = -1;

	/**
	 * The number of bubbles displayed for this stage on the right side of the animation<br/>
	 * NOTE ! In case there is only one row of bubbles for this stage, then either this or {@link #nrItemsLeft} can be 0
	 */

	public int nrItemsRight = -1;

	/**
	 * If <b>false</b>, the animation for each individual bubble ({@link #nrItemsLeft}) will be started at the same time<br/>
	 * If <b>true<b/> the animations will be started with an offset
	 */
	public boolean delayLeft = false;

	/**
	 * If <b>false</b>, the animation for each individual bubble ({@link #nrItemsRight}) will be started at the same time<br/>
	 * If <b>true<b/> the animations will be started with an offset
	 */
	public boolean delayRight = false;

	/**
	 * The [x,y] position from which the left side bubbles need to start for this stage<br/>
	 * May be null in case {@link #nrItemsLeft} == 0
	 */
	public float[] startPosLeft;

	/**
	 * The [x,y] position from which the right side bubbles need to start for this stage<br/>
	 * May be null in case {@link #nrItemsRight} == 0
	 */
	public float[] startPosRight;

	/**
	 * An array of [x,y] positions for each bubble animated on the left side of the view<br/>
	 * Note ! The size of the list needs to match {@link #nrItemsLeft}
	 */
	public float[][] endPosLeft;

	/**
	 * An array of [x,y] positions for each bubble animated on the right side of the view<br/>
	 * Note ! The size of the list needs to match {@link #nrItemsRight}
	 */
	public float[][] endPosRight;
	
}
