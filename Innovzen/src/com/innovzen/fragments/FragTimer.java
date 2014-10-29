
package com.innovzen.fragments;

import com.innovzen.o2chair.R;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.activities.ActivityBase;
import com.innovzen.customviews.CircularSeekBar;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.CircularSeekBarHandler;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.handlers.HeaderHandler;
import com.innovzen.handlers.SubheaderHandler;
import com.innovzen.utils.PersistentUtil;

public class FragTimer extends FragBase implements OnClickListener {

    /** Default values for the timer (in milliseconds) */
    public static final int DEFAULT_TIMER_MIN = 5 * 60000; // 5 min
    public static final int DEFAULT_TIMER_MAX = 30 * 60000; // 30 min
    public static final int DEFAULT_TIMER_START = 5 * 60000; // 5 min

    // Hold references
    public CircularSeekBar topCircularSeekBar;
    public CircularSeekBar bottomCircularSeekBar;
    public ImageView timerCore;
    public ImageView timerContour;
    public ImageView timerBackground;

    // Hold the footer handler
    private FooterHandler mFooterHandler;

    // Hold the header handler
    private HeaderHandler mHeaderHandler;

    /** Handler for the timer */
    private CircularSeekBarHandler mTimerHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        init(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.footer_icon_right:
            case R.id.timer_large_play_btn:
                // Set the exercise type
                PersistentUtil.setInt(getActivity(), FragAnimationBase.EXERCISE_TYPE_START_CUSTOM, FragAnimationBase.PERSIST_EXERCISE_TYPE_START);
                // Set the time
                PersistentUtil.setInt(getActivity(), mTimerHandler.getProgressMilliseconds(), FragAnimationBase.PERSIST_TOTAL_SELECTED_EXERCISE_DURATION);
                // Go to the activity
                super.activityListener.fragGoToAnimationFromTimer();
                break;
            case R.id.footer_icon_middle:
            case R.id.timer_large_custom_btn:
                super.activityListener.fragGoToTimerAdvance(true);
                break;
        }
    }

    @Override
    public void init(View view) {

        Resources res = getResources();

        // Get references
        RelativeLayout footer = (RelativeLayout) view.findViewById(R.id.timer_footer);

        // Init the footer
        if (ActivityBase.IS_TABLET) {
            mFooterHandler = new FooterHandler(super.activityListener, footer, FooterHandler.HOME, -1, -1);
        } else {
            mFooterHandler = new FooterHandler(super.activityListener, footer, FooterHandler.HOME, FooterHandler.TIMER, FooterHandler.PLAY);
            mFooterHandler.setRightIconOnClickListener(this);
            mFooterHandler.setMiddleIconOnClickListener(this);
        }

        // Init the subheader
        new SubheaderHandler(res, (TextView) view.findViewById(R.id.reusable_subheader), res.getString(R.string.subheader_base_reglage), res.getString(R.string.subheader_timer));

        // Init the header
        mHeaderHandler = new HeaderHandler(getActivity(), (RelativeLayout) view.findViewById(R.id.reusable_header));
        mHeaderHandler.showLeftArrow(null);

        // Init the timer
        mTimerHandler = new CircularSeekBarHandler(view.findViewById(R.id.reusable_circular_seekbar_container), false, CircularSeekBarHandler.TIME_TYPE_LARGE_DIGITAL_DOUBLE, CircularSeekBarHandler.PROGRESS_TYPE_BLUE, -1, true);
        mTimerHandler.setProgressMillis(DEFAULT_TIMER_MIN, DEFAULT_TIMER_MAX, DEFAULT_TIMER_START);

        // Set event listeners
        if (ActivityBase.IS_TABLET) {
            view.findViewById(R.id.timer_large_play_btn).setOnClickListener(this);
            view.findViewById(R.id.timer_large_custom_btn).setOnClickListener(this);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        mTimerHandler.onResume();
    }
}
