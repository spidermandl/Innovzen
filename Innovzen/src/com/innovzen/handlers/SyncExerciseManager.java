package com.innovzen.handlers;


import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.innovzen.bluetooth.BluetoothCommand;
import com.innovzen.entities.ExerciseTimes;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.interfaces.FragmentCommunicator;

/**
 * 
 * @author Desmond
 * ʵ��ͬ������exercise����
 */
public class SyncExerciseManager extends ExerciseManager{

	/**
	 * ͨѶ�¼�����
	 */
	static final int DELTA_TIME=500;
	/**
	 * �ȴ������������λ��ָ��
	 * waitHandler��ʵ��ֻ����һ�ݣ���isRunningΪtrueʱ����ʱ���ڽ��еȴ�״̬
	 */
	private WaitHandler waitHandler=new WaitHandler(),//�ȴ��̣߳��ȴ�ʱ����ﵽ��λ
	           exhaleHoldHandler=new WaitHandler(),//������ס�ȴ��߳�
	           inhaleExerciseHandler = new WaitHandler(),//�����ȴ��߳�
	           inhaleHoldHandler=new WaitHandler(),//������ס�߳�
	           exhaleExerciseHandler = new WaitHandler();//�����ȴ��߳�
	
	/**
	 * У����λ�ͷ���
	 */
	private PositionCheckRunnable 
	           exhaleHoldRunnable=new PositionCheckRunnable(EXERCISE_HOLD_EXHALE),//������ס�ȴ��߼�
	           inhaleExerciseRunnable = new PositionCheckRunnable(EXERCISE_INHALE),//�����ȴ��߼�
	           inhaleHoldRunnable=new PositionCheckRunnable(EXERCISE_HOLD_INHALE),//������ס�ȴ��߼�
	           exhaleExerciseRunnable = new PositionCheckRunnable(EXERCISE_EXHALE);//�����ȴ��߼�
	
	/**
	 * ͬ��ʱ�����
	 */
	private long inhaleTimeStart=0,//������ʼʱ��
			     inhaleTimeEnd=0,//��������ʱ��
			     exhaleTimeStart=0,//������ʼʱ��
			     exhaleTimeEnd=0;//��������ʱ��
	
	/**
	 * �ȴ��̴߳�����
	 */
	private Runnable waitRunnable=new Runnable() {
		public void run() {
			long subtime;
			switch (mCurExercise) {
			case EXERCISE_INHALE:
				subtime=BluetoothCommand.getInstance()==null?0:
					(System.currentTimeMillis()-inhaleTimeEnd);
	        	if(subtime!=0&&subtime<mTimes.inhale+DELTA_TIME){
	        		//long a = mTimes.inhale;
	        		Log.e("inhale �ȴ��ɹ�", subtime+"");
	        		waitHandler.sendEmptyMessage(0);
	        	}else{
	        		Log.e("inhale û�ȵ�", subtime+"");
	        		waitHandler.postDelayed(waitRunnable,BluetoothCommand.DELAY_TIME);
	        	}
				break;
			case EXERCISE_HOLD_INHALE:
				subtime=BluetoothCommand.getInstance()==null?0:
					(System.currentTimeMillis()-exhaleTimeStart);
	        	if(subtime!=0&&subtime<mTimes.holdInhale){
	        		Log.e("EXERCISE_HOLD_INHALE �ȴ��ɹ�", subtime+"");
	        		waitHandler.sendEmptyMessage(0);
	        	}else{
	        		Log.e("EXERCISE_HOLD_INHALE û�ȵ�", subtime+"");
	        		waitHandler.postDelayed(waitRunnable,BluetoothCommand.DELAY_TIME);
	        	}
				break;
			case EXERCISE_EXHALE:
				subtime=BluetoothCommand.getInstance()==null?0:
					(System.currentTimeMillis()-exhaleTimeEnd);
	        	if(subtime!=0&&subtime<mTimes.exhale+DELTA_TIME){
	        		Log.e("exhale �ȴ��ɹ�", subtime+"");
	        		waitHandler.sendEmptyMessage(0);
	        	}else{
	        		Log.e("exhale û�ȵ�", subtime+"");
	        		waitHandler.postDelayed(waitRunnable,BluetoothCommand.DELAY_TIME);
	        	}
				break;
			case EXERCISE_HOLD_EXHALE:
				subtime=BluetoothCommand.getInstance()==null?0:
					(System.currentTimeMillis()-inhaleTimeStart);
	        	if(subtime!=0&&subtime<mTimes.holdExhale){
	        		Log.e("EXERCISE_HOLD_EXHALE �ȴ��ɹ�", subtime+"");
	        		waitHandler.sendEmptyMessage(0);
	        	}else{
	        		Log.e("EXERCISE_HOLD_EXHALE û�ȵ�", subtime+"");
	        		waitHandler.postDelayed(waitRunnable,BluetoothCommand.DELAY_TIME);
	        	}
				break;

			default:
				break;
			}

		}
	};
	
	/**
	 * ���캯��
	 * @param fragmentAnimation
	 * @param animationHandler
	 * @param soundHandler
	 * @param times
	 * @param voiceSoundId
	 * @param ambianceSoundId
	 */
	public SyncExerciseManager(FragAnimationBase fragmentAnimation,
			ExerciseAnimationHandler animationHandler,
			FragmentCommunicator soundHandler, ExerciseTimes times,
			int voiceSoundId, int ambianceSoundId) {
		super(fragmentAnimation, animationHandler, soundHandler, times, voiceSoundId,
				ambianceSoundId);
		// TODO Auto-generated constructor stub
	}

    /**
     * ����ʱ��ͬ��
     */
	@Override
	protected void startAppropriateExerciseType(float fraction) {
		boolean isContinue=true;//�����˶��ͺ�����Ϊfalse
		long subtime;
		switch (mCurExercise) {
        case EXERCISE_INHALE:
        	/**
        	 * ��ȡ���һ��12��λ��ʱ��
        	         �����ǰʱ���ȥ���ʱ��Ͳ�С��mTimes.inhale ˵����Ħ���˶����ˣ���ôֱ�ӵ���startAppropriateExerciseType(1f);
        	      �����ǰʱ���ȥ���ʱ��Ͳ����mTimes.inhale����animation.getAnimatedFraction() ���ֵΪ1f�� ˵�����ְ�Ħ���˶����ˣ���ô�͵ȴ�����100msΪ��λ�ȴ�
        	 */

        	subtime=BluetoothCommand.getInstance()==null?0:
        		(System.currentTimeMillis()-inhaleTimeEnd);
        	if(subtime!=0&&subtime<mTimes.inhale+DELTA_TIME){//�����˶���ǰ

        		//long a = mTimes.inhale;
        		//Log.e("INHALE ��ǰ", subtime+"");
        		fraction=1f;
        		break;
        	}else if(subtime>mTimes.inhale+DELTA_TIME&&fraction==1f){//�����˶��ͺ�
        		//Log.e("INHALE �ͺ�", subtime+"");
        		if(!waitHandler.isWaiting()){
	        		waitHandler.setWaiting(true);
	        		waitHandler.postDelayed(waitRunnable, BluetoothCommand.DELAY_TIME); // 1 ms
        		}
	        	isContinue=false;
        	}else{//��������״̬
        		//Log.e("INHALE ��������״̬", "fraction"+fraction);
        	}
        	
            break;
        case EXERCISE_HOLD_INHALE:
        	subtime=BluetoothCommand.getInstance()==null?0:
        		(System.currentTimeMillis()-exhaleTimeStart);
        	if(subtime!=0&&subtime<mTimes.holdInhale){//�����˶���ǰ
        		fraction=1f;
        		break;
        	}else if(subtime>mTimes.holdInhale&&fraction==1f){//�����˶��ͺ�
        		if(!waitHandler.isWaiting()){
	        		waitHandler.setWaiting(true);
	        		waitHandler.postDelayed(waitRunnable, BluetoothCommand.DELAY_TIME); // 1 ms
        		}
	        	isContinue=false;
        	}else{//��������״̬
        		
        	}
            break;
        case EXERCISE_EXHALE:
        	subtime=BluetoothCommand.getInstance()==null?0:
        		(System.currentTimeMillis()-exhaleTimeEnd);
        	if(subtime!=0&&subtime<mTimes.exhale+DELTA_TIME){//�����˶���ǰ
        		fraction=1f;
        		break;
        	}else if(subtime>mTimes.exhale+DELTA_TIME&&fraction==1f){//�����˶��ͺ�
        		if(!waitHandler.isWaiting()){
	        		waitHandler.setWaiting(true);
	        		waitHandler.postDelayed(waitRunnable, BluetoothCommand.DELAY_TIME); // 1 ms
        		}
        		isContinue=false;

        	}else{//��������״̬
        		
        	}
        	
            break;
        case EXERCISE_HOLD_EXHALE:
        	subtime=BluetoothCommand.getInstance()==null?0:
        		(System.currentTimeMillis()-inhaleTimeStart);
        	if(subtime!=0&&subtime<mTimes.holdExhale){//�����˶���ǰ
        		fraction=1f;
        		break;
        	}else if(subtime>mTimes.holdExhale&&fraction==1f){//�����˶��ͺ�
        		if(!waitHandler.isWaiting()){
	        		waitHandler.setWaiting(true);
	        		waitHandler.postDelayed(waitRunnable, BluetoothCommand.DELAY_TIME); // 1 ms
        		}
        		isContinue=false;

        	}else{//��������״̬
        		
        	}
        	
            break;
		}
		
		if(isContinue)
			super.startAppropriateExerciseType(fraction);
	}
    
	/**
	 * �ȴ�Handler
	 * @author desmond.duan
	 *
	 */
	class WaitHandler extends Handler{
		private boolean isWaiting=false;//�ж��߳��Ƿ���ִ��
		@Override
		public void handleMessage(Message msg) {
			SyncExerciseManager.super.startAppropriateExerciseType(1f);
			isWaiting=false;
		}
		
		public boolean isWaiting() {
			return isWaiting;
		}
		public void setWaiting(boolean isWaiting) {
			this.isWaiting = isWaiting;
		}
	}
	
	@Override
	public void start() {
		inhaleExerciseHandler.setWaiting(true);
		inhaleExerciseHandler.post(inhaleExerciseRunnable);
		inhaleHoldHandler.setWaiting(true);
		inhaleHoldHandler.post(inhaleHoldRunnable);
		exhaleExerciseHandler.setWaiting(true);
		exhaleExerciseHandler.post(exhaleExerciseRunnable);
		exhaleHoldHandler.setWaiting(true);
		exhaleHoldHandler.post(exhaleHoldRunnable);
		super.start();
	}
	
	@Override
	public void pause(boolean showPlayBtnOverlay) {
//		inhaleExerciseHandler.setWaiting(false);
//		inhaleHoldHandler.setWaiting(false);
//		exhaleExerciseHandler.setWaiting(false);
//		exhaleHoldHandler.setWaiting(false);
		super.pause(showPlayBtnOverlay);
	}
	
	/**
	 * ��λ�޵ȴ��߼�
	 * @author Desmond Duan
	 *
	 */
	class PositionCheckRunnable implements Runnable{

		int exerciseType;
		int lastPositison=BluetoothCommand.WALKING_POSITION_STATUS5;
		int lastDirection=BluetoothCommand.DIRECTION_STATUS_RETAIN;
		
		PositionCheckRunnable(int type){
			exerciseType=type;
		}
		@Override
		public void run() {
			switch (exerciseType) {
			case EXERCISE_INHALE:
				if(!inhaleExerciseHandler.isWaiting()){
				}else{
					BluetoothCommand mBC=BluetoothCommand.getInstance();
					if(mBC!=null){
						int currentPos=mBC.getValue(BluetoothCommand.WALKING_POSITION_STATUS);
						int currentDir=mBC.getValue(BluetoothCommand.DIRECTION_STATUS);
						if((lastPositison==BluetoothCommand.WALKING_POSITION_STATUS11&&currentPos==BluetoothCommand.WALKING_POSITION_STATUS12)||
								(lastDirection==BluetoothCommand.DIRECTION_STATUS_UP&&currentDir==BluetoothCommand.DIRECTION_STATUS_STOP)){
//						if((lastPositison==BluetoothCommand.WALKING_POSITION_STATUS12&&currentPos==BluetoothCommand.WALKING_POSITION_STATUS11)||
//									(lastDirection==BluetoothCommand.DIRECTION_STATUS_STOP&&currentDir==BluetoothCommand.DIRECTION_STATUS_DOWN)){	
							/**
							 * ��λ��11��12 ����λ��������Ϊֹͣ
							 */
							inhaleTimeEnd=System.currentTimeMillis();
						}
						lastPositison=currentPos;
						lastDirection=currentDir;
					}
					inhaleExerciseHandler.postDelayed(inhaleExerciseRunnable, BluetoothCommand.DELAY_TIME/2);
				}
				break;
			case EXERCISE_HOLD_INHALE:
				if(!inhaleHoldHandler.isWaiting()){
				}else{
					BluetoothCommand mBC=BluetoothCommand.getInstance();
					if(mBC!=null){
						int currentPos=mBC.getValue(BluetoothCommand.WALKING_POSITION_STATUS);
						int currentDir=mBC.getValue(BluetoothCommand.DIRECTION_STATUS);
						if((lastPositison==BluetoothCommand.WALKING_POSITION_STATUS12&&currentPos==BluetoothCommand.WALKING_POSITION_STATUS11)||
								(lastDirection==BluetoothCommand.DIRECTION_STATUS_STOP&&currentDir==BluetoothCommand.DIRECTION_STATUS_DOWN)){
							/**
							 * ��λ��12��11 ����λ��ֹͣ��Ϊ����
							 */
							exhaleTimeStart=System.currentTimeMillis();
						}
						lastPositison=currentPos;
						lastDirection=currentDir;
					}
					inhaleExerciseHandler.postDelayed(inhaleHoldRunnable, BluetoothCommand.DELAY_TIME/2);
				}
				break;
			case EXERCISE_EXHALE:
				if(!exhaleExerciseHandler.isWaiting()){
				}else{
					BluetoothCommand mBC=BluetoothCommand.getInstance();
					if(mBC!=null){
						int currentPos=mBC.getValue(BluetoothCommand.WALKING_POSITION_STATUS);
						int currentDir=mBC.getValue(BluetoothCommand.DIRECTION_STATUS);
						if((lastPositison==BluetoothCommand.WALKING_POSITION_STATUS2&&currentPos==BluetoothCommand.WALKING_POSITION_STATUS1)||
								(lastDirection==BluetoothCommand.DIRECTION_STATUS_DOWN&&currentDir==BluetoothCommand.DIRECTION_STATUS_STOP)){
//						if((lastPositison==BluetoothCommand.WALKING_POSITION_STATUS1&&currentPos==BluetoothCommand.WALKING_POSITION_STATUS2)||
//									(lastDirection==BluetoothCommand.DIRECTION_STATUS_STOP&&currentDir==BluetoothCommand.DIRECTION_STATUS_UP)){
							/**
							 * ��λ��2��1 ����λ��������Ϊֹͣ
							 */
							exhaleTimeEnd=System.currentTimeMillis();
						}
						lastPositison=currentPos;
						lastDirection=currentDir;
					}
					inhaleExerciseHandler.postDelayed(exhaleExerciseRunnable, BluetoothCommand.DELAY_TIME/2);
				}
				break;
			case EXERCISE_HOLD_EXHALE:
				if(!exhaleHoldHandler.isWaiting()){
				}else{
					BluetoothCommand mBC=BluetoothCommand.getInstance();
					if(mBC!=null){
						int currentPos=mBC.getValue(BluetoothCommand.WALKING_POSITION_STATUS);
						int currentDir=mBC.getValue(BluetoothCommand.DIRECTION_STATUS);
						if((lastPositison==BluetoothCommand.WALKING_POSITION_STATUS1&&currentPos==BluetoothCommand.WALKING_POSITION_STATUS2)||
								(lastDirection==BluetoothCommand.DIRECTION_STATUS_STOP&&currentDir==BluetoothCommand.DIRECTION_STATUS_UP)){
							/**
							 * ��λ��1��2 ����λ��ֹͣ��Ϊ����
							 */
							inhaleTimeStart=System.currentTimeMillis();
						}
						lastPositison=currentPos;
						lastDirection=currentDir;
					}
					exhaleHoldHandler.postDelayed(exhaleHoldRunnable, BluetoothCommand.DELAY_TIME/2);
				}
				break;

			default:
				break;
			}
			
		}
		
	}
	/**
	 * �Թ���������
	 */
//	@Override
//	protected void inhale(float fraction, float globalFraction) {
//		// Start appropriate sounds
//        playSounds(SoundItem.INSPIREZ, mTimes.inhale);
//
//        // Set the volume of the ambiance sound
//        setAmbianceVolume(fraction);
//
//        // Render animation frame
//        mAnimationHandler.inhale(fraction, globalFraction);
//
//        // If step animation ended
//        if (fraction == 1f) {
//            // Set the flag to point to the next appropriate step
//            mCurExercise = EXERCISE_EXHALE;
//
//            // Restart the value animator
//            startValueAnimator(mTimes.exhale);
//
//            // Reset the flag that indicates if we've started the sounds for the new step
//            mPlayedSounds = false;
//
//        }
//	}
//	
//	@Override
//	protected void exhale(float fraction, float globalFraction) {
//		// Start appropriate sounds
//        playSounds(SoundItem.EXPIREZ, mTimes.exhale);
//
//        // Set the volume of the ambiance sound
//        setAmbianceVolume(fraction);
//
//        // Render animation frame
//        mAnimationHandler.exhale(fraction, globalFraction);
//
//        // If step animation ended
//        if (fraction == 1f) {
//
//            // Check if the entire exercise is done. If so, then stop here
//            if (globalFraction == 1f) {
//
//                reset(true);
//
//                return;
//            }
//
//            // Set the flag to point to the next appropriate step
//            mCurExercise = EXERCISE_INHALE;
//
//            // Restart the value animator
//            startValueAnimator(mTimes.inhale);
//
//            // Reset the flag that indicates if we've started the sounds for the new step
//            mPlayedSounds = false;
//
//        }
//	}
}
