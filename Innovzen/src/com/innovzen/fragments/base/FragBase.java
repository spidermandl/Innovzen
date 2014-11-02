
package com.innovzen.fragments.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.innovzen.interfaces.FragmentCommunicator;
import com.innovzen.o2chair.R;

/**
 * fragment 父类
 * 所有fragment共有属性
 * @author Desmond Duan
 *
 */
public abstract class FragBase extends Fragment {

    // Holds the reference to where the activity listens for messages from the fragments
    protected FragmentCommunicator activityListener;
    private ImageView iv;
    
    /**
     * desmond
     * 界面左侧控制栏
     */
    protected ImageView leftTop;
    protected ImageView leftMid;
    protected ImageView leftBottom;

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
        
    }
}
