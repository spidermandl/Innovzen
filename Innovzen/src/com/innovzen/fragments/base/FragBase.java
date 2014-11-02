
package com.innovzen.fragments.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.innovzen.interfaces.FragmentCommunicator;
import com.innovzen.o2chair.R;

/**
 * fragment ����
 * ����fragment��������
 * @author Desmond Duan
 *
 */
public abstract class FragBase extends Fragment {

    // Holds the reference to where the activity listens for messages from the fragments
    protected FragmentCommunicator activityListener;
    private ImageView iv;
    
    /**
     * desmond
     * ������������
     */
   

    /**
     * Does proper initializations after inflating the view
     * 
     * @param view
     * @author MAB
     */
    public abstract void init(View view);
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
     
        if (activity instanceof FragmentCommunicator) {
            activityListener = (FragmentCommunicator) activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implement FragmentCommunicator");
        }
        
        /**
         * desmond
         * ʵ���������������������
         */
        if(activity.findViewById(R.id.left_toolbar)!=null){
        	 ImageView leftTop;
        	    final ImageView leftMid;
        	    final ImageView leftBottom;
        	    final LinearLayout voice_progressbar;
        	leftTop=(ImageView)activity.findViewById(R.id.left_top);
        	leftMid=(ImageView)activity.findViewById(R.id.left_mid);
        	leftBottom=(ImageView)activity.findViewById(R.id.left_bottom);
        	
        	//����л�ͼƬ
        	voice_progressbar = (LinearLayout) activity.findViewById(R.id.voice_progressbar);
        	leftBottom.setOnClickListener(new OnClickListener() {			
    			@Override
    			public void onClick(View v) {
    			Toast.makeText(getActivity(), "sssssssss", 0).show();
                     leftMid.setVisibility(View.INVISIBLE);
                     leftBottom.setVisibility(View.INVISIBLE);
                     voice_progressbar.setVisibility(View.VISIBLE);
    			}
    		});
        }
        
        
    }
}
