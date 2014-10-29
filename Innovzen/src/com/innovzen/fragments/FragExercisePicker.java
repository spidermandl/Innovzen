package com.innovzen.fragments;

import com.innovzen.o2chair.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.entities.ExerciseTimes;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.ExerciseInfoOverlayHandler;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.handlers.HeaderHandler;
import com.innovzen.handlers.SubheaderHandler;
import com.innovzen.interfaces.FragmentOnBackPressInterface;
import com.innovzen.utils.PersistentUtil;

public class FragExercisePicker extends FragBase implements OnClickListener, FragmentOnBackPressInterface {

    /** The times for the balance exercise */
    private ExerciseTimes mTimesBalance = new ExerciseTimes(5000, 1000, 5000, 1000, -1);
    /** The times for the performance intermediate */
    private ExerciseTimes mTimesPerformance = new ExerciseTimes(6000, 1000, 4000, 1000, -1);
    /** The times for the relax pro */
    private ExerciseTimes mTimesRelax = new ExerciseTimes(4000, 1000, 6000, 1000, -1);

    // Hold the header handler
    private HeaderHandler mHeaderHandler;

    // Hold the exercise info overlay handler
    private ExerciseInfoOverlayHandler mOverlayHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_picker, container, false);

        init(view);

        return view;
    }

    @Override
    public boolean onBackPress() {
        if (mOverlayHandler.isVisible()) {

            // Hide it
            mOverlayHandler.hide();

            // Let the caller know we've handled the event
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exercise_picker_echilibre:
                // Set the time values
                PersistentUtil.setInt(getActivity(), mTimesBalance.inhale, FragAnimationBase.PERSIST_TIME_INHALE);
                PersistentUtil.setInt(getActivity(), mTimesBalance.holdInhale, FragAnimationBase.PERSIST_TIME_HOLD_INHALE);
                PersistentUtil.setInt(getActivity(), mTimesBalance.exhale, FragAnimationBase.PERSIST_TIME_EXHALE);
                PersistentUtil.setInt(getActivity(), mTimesBalance.holdExhale, FragAnimationBase.PERSIST_TIME_HOLD_EXHALE);
                PersistentUtil.setInt(getActivity(), 5 * 60000, FragAnimationBase.PERSIST_TOTAL_SELECTED_EXERCISE_DURATION);
                // Set the subtitle text values
                PersistentUtil.setInt(getActivity(), FragAnimationBase.EXERCISE_TYPE_START_NORMAL, FragAnimationBase.PERSIST_EXERCISE_TYPE_START);
                PersistentUtil.setInt(getActivity(), -1, FragAnimationBase.PERSIST_EXERCISE_TYPE_MIDDLE);
                PersistentUtil.setInt(getActivity(), FragAnimationBase.EXERCISE_TYPE_END_BALANCE, FragAnimationBase.PERSIST_EXERCISE_TYPE_END);
                // Go to the animation
                super.activityListener.fragGoToAnimationFromTimer();
                break;
            case R.id.exercise_picker_performance:
                // Set the time values
                PersistentUtil.setInt(getActivity(), mTimesPerformance.inhale, FragAnimationBase.PERSIST_TIME_INHALE);
                PersistentUtil.setInt(getActivity(), mTimesPerformance.holdInhale, FragAnimationBase.PERSIST_TIME_HOLD_INHALE);
                PersistentUtil.setInt(getActivity(), mTimesPerformance.exhale, FragAnimationBase.PERSIST_TIME_EXHALE);
                PersistentUtil.setInt(getActivity(), mTimesPerformance.holdExhale, FragAnimationBase.PERSIST_TIME_HOLD_EXHALE);
                PersistentUtil.setInt(getActivity(), 5 * 60000, FragAnimationBase.PERSIST_TOTAL_SELECTED_EXERCISE_DURATION);
                // Set the subtitle text values
                PersistentUtil.setInt(getActivity(), FragAnimationBase.EXERCISE_TYPE_START_NORMAL, FragAnimationBase.PERSIST_EXERCISE_TYPE_START);
                PersistentUtil.setInt(getActivity(), -1, FragAnimationBase.PERSIST_EXERCISE_TYPE_MIDDLE);
                PersistentUtil.setInt(getActivity(), FragAnimationBase.EXERCISE_TYPE_END_PERFORMANCE, FragAnimationBase.PERSIST_EXERCISE_TYPE_END);
                // Go to the animation
                super.activityListener.fragGoToAnimationFromTimer();
                break;
            case R.id.exercise_picker_relax:
                // Set the time values
                PersistentUtil.setInt(getActivity(), mTimesRelax.inhale, FragAnimationBase.PERSIST_TIME_INHALE);
                PersistentUtil.setInt(getActivity(), mTimesRelax.holdInhale, FragAnimationBase.PERSIST_TIME_HOLD_INHALE);
                PersistentUtil.setInt(getActivity(), mTimesRelax.exhale, FragAnimationBase.PERSIST_TIME_EXHALE);
                PersistentUtil.setInt(getActivity(), mTimesRelax.holdExhale, FragAnimationBase.PERSIST_TIME_HOLD_EXHALE);
                PersistentUtil.setInt(getActivity(), 5 * 60000, FragAnimationBase.PERSIST_TOTAL_SELECTED_EXERCISE_DURATION);
                // Set the subtitle text values
                PersistentUtil.setInt(getActivity(), FragAnimationBase.EXERCISE_TYPE_START_NORMAL, FragAnimationBase.PERSIST_EXERCISE_TYPE_START);
                PersistentUtil.setInt(getActivity(), -1, FragAnimationBase.PERSIST_EXERCISE_TYPE_MIDDLE);
                PersistentUtil.setInt(getActivity(), FragAnimationBase.EXERCISE_TYPE_END_RELAX, FragAnimationBase.PERSIST_EXERCISE_TYPE_END);
                // Go to the animation
                super.activityListener.fragGoToAnimationFromTimer();
                break;
            case R.id.exercise_picker_custom:
                super.activityListener.fragGoToTimer(true);
                break;
            case R.id.exercise_picker_echilibre_info:
                mOverlayHandler.show(ExerciseInfoOverlayHandler.OVERLAY_TYPE_ECHILIBRE);
                break;
            case R.id.exercise_picker_performance_info:
                mOverlayHandler.show(ExerciseInfoOverlayHandler.OVERLAY_TYPE_PERFORMANCE);
                break;
            case R.id.exercise_picker_relax_info:
                mOverlayHandler.show(ExerciseInfoOverlayHandler.OVERLAY_TYPE_RELAX);
                break;
            case R.id.exercise_picker_custom_info:
                mOverlayHandler.show(ExerciseInfoOverlayHandler.OVERLAY_TYPE_CUSTOM);
                break;
        }

    }

    @Override
    public void init(View view) {

        // Init the header
        mHeaderHandler = new HeaderHandler(getActivity(), (RelativeLayout) view.findViewById(R.id.reusable_header), true, false, null);
        mHeaderHandler.showLeftArrow(null);

        // Init the subheader
        new SubheaderHandler(getResources(), (TextView) view.findViewById(R.id.reusable_subheader), getString(R.string.subheader_base_choose_exericse), getString(R.string.subheader_exercise_picker));

        // Init the footer
        new FooterHandler(super.activityListener, (RelativeLayout) view.findViewById(R.id.exercise_picker_footer), FooterHandler.HOME, -1, -1);

        // Init the exercise info overlay
        mOverlayHandler = new ExerciseInfoOverlayHandler(getActivity(), view.findViewById(R.id.exercise_picker_overlay));

        // Set event listeners
        view.findViewById(R.id.exercise_picker_echilibre).setOnClickListener(this);
        view.findViewById(R.id.exercise_picker_performance).setOnClickListener(this);
        view.findViewById(R.id.exercise_picker_relax).setOnClickListener(this);
        view.findViewById(R.id.exercise_picker_custom).setOnClickListener(this);
        view.findViewById(R.id.exercise_picker_echilibre_info).setOnClickListener(this);
        view.findViewById(R.id.exercise_picker_performance_info).setOnClickListener(this);
        view.findViewById(R.id.exercise_picker_relax_info).setOnClickListener(this);
        view.findViewById(R.id.exercise_picker_custom_info).setOnClickListener(this);

    }

}
