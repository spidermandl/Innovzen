
package com.innovzen.handlers;

import com.innovzen.o2chair.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.innovzen.interfaces.FragmentCommunicator;

public class FooterHandler implements OnClickListener {

    // Constants representing the types of icons that the footer can hold
    public static final int HOME = 0;
    public static final int TIMER = 1;
    public static final int HELP = 2;
    public static final int PLAY = 3;

    // Hold references to objects received in constructor
    private FragmentCommunicator mActivityListener;
    private int mLeftType = -1;
    private int mMiddleType = -1;
    private int mRightType = -1;

    // Hold the on click listeners for the footer icons (in case we want to override the normal event listener)
    private OnClickListener mOnClickListenerLeft;
    private OnClickListener mOnClickListenerMiddle;
    private OnClickListener mOnClickListenerRight;

    /**
     * @param activityListener
     * @param footerView
     * @param left
     *            see constants in {@link FooterHandler} for the types supported<br/>
     *            -1 if not available
     * @param middle
     *            see constants in {@link FooterHandler} for the types supported<br/>
     *            -1 if not available
     * @param right
     *            see constants in {@link FooterHandler} for the types supported<br/>
     *            -1 if not available
     */
    public FooterHandler(FragmentCommunicator activityListener, RelativeLayout footerView, int left, int middle, int right) {
        this.mActivityListener = activityListener;
        mLeftType = left;
        mMiddleType = middle;
        mRightType = right;

        init(footerView);
    }

    /**
     * Does proper initializations
     * 
     * @param view
     * @author MAB
     */
    private void init(RelativeLayout view) {

        View item;

        if (mLeftType != -1) {
            item = view.findViewById(R.id.footer_icon_left);
            item.setOnClickListener(this);
            item.setVisibility(View.VISIBLE);
            setIconDrawable((ImageView) item, mLeftType);
        }

        if (mMiddleType != -1) {
            item = view.findViewById(R.id.footer_icon_middle);
            item.setOnClickListener(this);
            item.setVisibility(View.VISIBLE);
            setIconDrawable((ImageView) item, mMiddleType);
        }

        if (mRightType != -1) {
            item = view.findViewById(R.id.footer_icon_right);
            item.setOnClickListener(this);
            item.setVisibility(View.VISIBLE);
            setIconDrawable((ImageView) item, mRightType);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.footer_icon_left:
                if (mOnClickListenerLeft != null) {
                    mOnClickListenerLeft.onClick(v);
                } else {
                    onFooterIconClicked(mLeftType);
                }
                break;
            case R.id.footer_icon_middle:
                if (mOnClickListenerMiddle != null) {
                    mOnClickListenerMiddle.onClick(v);
                } else {
                    onFooterIconClicked(mMiddleType);
                }
                break;
            case R.id.footer_icon_right:
                if (mOnClickListenerRight != null) {
                    mOnClickListenerRight.onClick(v);
                } else {
                    onFooterIconClicked(mRightType);
                }
                break;
        }
    }

    /**
     * Depending on the type of the icon, it sets the appropriate drawable on the imageview
     * 
     * @param view
     * @param type
     * @author MAB
     */
    private void setIconDrawable(ImageView view, int type) {

        if (view == null) {
            return;
        }
        switch (type) {
            case HOME:
                view.setImageResource(R.drawable.selector_icon_home);
                break;
            case PLAY:
                view.setImageResource(R.drawable.selector_icon_play);
                break;
            case TIMER:
                view.setImageResource(R.drawable.selector_icon_timer);
                break;
            case HELP:
                view.setImageResource(R.drawable.selector_icon_help);
                break;
        }
    }

    /**
     * When an item from the footer is clicked, it goes here
     * 
     * @param iconType
     * @author MAB
     */
    private void onFooterIconClicked(int iconType) {

        if (mActivityListener == null) {
            return;
        }

        switch (iconType) {
            case HOME:
                mActivityListener.fragGoToHome(false);
                break;
            case PLAY:
                mActivityListener.fragGoToAnimation(false);
                break;
            case TIMER:
                mActivityListener.fragGoToTimer(true);
                break;
            case HELP:
                mActivityListener.fragGoToHelp(true);
                break;
        }
    }

    /**
     * Override the normal event listener for the left icon<br/>
     * In your event listener callback you'll need to check for R.id.footer_icon_left
     * 
     * @param listener
     * @author MAB
     */
    public void setLeftIconOnClickListener(OnClickListener listener) {
        this.mOnClickListenerLeft = listener;
    }

    /**
     * Override the normal event listener for the middle icon<br/>
     * In your event listener callback you'll need to check for R.id.footer_icon_middle
     * 
     * @param listener
     * @author MAB
     */
    public void setMiddleIconOnClickListener(OnClickListener listener) {
        this.mOnClickListenerMiddle = listener;
    }

    /**
     * Override the normal event listener for the right icon<br/>
     * In your event listener callback you'll need to check for R.id.footer_icon_right
     * 
     * @param listener
     * @author MAB
     */
    public void setRightIconOnClickListener(OnClickListener listener) {
        this.mOnClickListenerRight = listener;
    }
    
}
