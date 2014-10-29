package com.innovzen.fragments;

import com.innovzen.o2chair.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.activities.ActivityBase;
import com.innovzen.entities.ExerciseTimes;
import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.CircularSeekBarHandler;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.handlers.HeaderHandler;
import com.innovzen.handlers.SubheaderHandler;
import com.innovzen.utils.PersistentUtil;

public class FragTimerAdvance extends FragBase implements OnClickListener {

    /** Constants used to represent the beginner times */
    public static final int DEFAULT_TIMER_INHALE = 5000;
    public static final int DEFAULT_TIMER_HOLD_INHALE = 1000;
    public static final int DEFAULT_TIMER_EXHALE = 5000;
    public static final int DEFAULT_TIMER_HOLD_EXHALE = 1000;

    /** The times for the beginner exercise */
    private ExerciseTimes mTimesBeginner = new ExerciseTimes(4000, 4000, 4000, 1000, -1);
    /** The times for the beginner intermediate */
    private ExerciseTimes mTimesIntermediate = new ExerciseTimes(4000, 8000, 6000, 1000, -1);
    /** The times for the beginner pro */
    private ExerciseTimes mTimesPro = new ExerciseTimes(4000, 12000, 8000, 4000, -1);

    /** The minimum time (in miliseconds) for the inhale and exhale steps */
    public static final int MIN_INHALE_AND_EXHALE_TIMES = 2000; // 2 sec

    /** The minimum time (in miliseconds) for the hold steps */
    public static final int MIN_HOLD_TIMES = 1000; // 1 sec

    /** The maximum time (in miliseconds) for the timers */
    public static final int MAX_TIMER_TIME = 30000; // 30 sec

    // Hold view references
    private TextView tab_beginner;
    private TextView tab_intermediary;
    private TextView tab_pro;
    private TextView tab_custom;

    // Hold the footer handler
    private FooterHandler mFooterHandler;

    // Hold the header handler
    private HeaderHandler mHeaderHandler;

    /** The type of the exercise that has been selected from the tab menu */
    private int mExerciseTypeSelected = -1;

    /** The handler for the inhale timer */
    private CircularSeekBarHandler mTimerInhaleHandler;
    /** The handler for the hold inhale timer */
    private CircularSeekBarHandler mTimerHoldInhaleHandler;
    /** The handler for the exhale timer */
    private CircularSeekBarHandler mTimerExhaleHandler;
    /** The handler for the hold exhale timer */
    private CircularSeekBarHandler mTimerHoldExhaleHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer_advance, container, false);

        init(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timer_advance_validate_btn:
                // Save the exercise type selected
                saveSelectedExerciseType();
                // Save the exercise times
                saveSelectedExerciseTimes();
                // Go to the activity
                super.activityListener.fragGoToAnimationFromTimerAdvanced();
                break;
        }

    }

    TextView fuck;

    @Override
    public void init(View view) {

        Resources res = getResources();

        // Get references
        RelativeLayout footer = (RelativeLayout) view.findViewById(R.id.timer_advance_footer);
        tab_beginner = (TextView) view.findViewById(R.id.timer_advance_debutant);
        tab_intermediary = (TextView) view.findViewById(R.id.timer_advance_intermediare);
        tab_pro = (TextView) view.findViewById(R.id.timer_advance_pro);
        tab_custom = (TextView) view.findViewById(R.id.timer_advance_perso);

        // Init the footer
        mFooterHandler = new FooterHandler(super.activityListener, footer, FooterHandler.HOME, -1, -1);

        // Init the subheader
        new SubheaderHandler(res, (TextView) view.findViewById(R.id.reusable_subheader), res.getString(R.string.subheader_base_reglages), res.getString(R.string.subheader_timer_advance));

        // Init the header
        mHeaderHandler = new HeaderHandler(getActivity(), (RelativeLayout) view.findViewById(R.id.reusable_header));
        mHeaderHandler.showLeftArrow(null);

        // Init the "fancy" text below the two timers
        ((TextView) view.findViewById(R.id.timer_advance_hold_inhale_text)).setText(Html.fromHtml(getString(R.string.timer_advance_timer_hold_inhale)));
        ((TextView) view.findViewById(R.id.timer_advance_hold_exhale_text)).setText(Html.fromHtml(getString(R.string.timer_advance_timer_hold_exhale)));
        if (ActivityBase.IS_TABLET) {
            ((TextView) view.findViewById(R.id.timer_advance_inhale_text)).setText(Html.fromHtml(getString(R.string.timer_advance_timer_inhale)));
            ((TextView) view.findViewById(R.id.timer_advance_exhale_text)).setText(Html.fromHtml(getString(R.string.timer_advance_timer_exhale)));
        }

        view.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if (newFocus != null) {
                    switch (newFocus.getId()) {
                        case R.id.timer_advance_debutant:
                            onTabBeginnerClick();
                            break;
                        case R.id.timer_advance_intermediare:
                            onTabIntermediateClick();
                            break;
                        case R.id.timer_advance_pro:
                            onTabProClick();
                            break;
                        case R.id.timer_advance_perso:
                            onTabCustomClick();
                            break;
                    }
                }
            }
        });

        // Set the default exercise type
        mExerciseTypeSelected = FragAnimationBase.EXERCISE_TYPE_END_BEGINNER;

        // Set up the tabs
        XmlResourceParser xrp = getResources().getXml(R.drawable.selector_timer_advance_tabs_text_color);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            tab_beginner.setTextColor(csl);
            tab_intermediary.setTextColor(csl);
            tab_pro.setTextColor(csl);
            tab_custom.setTextColor(csl);
        } catch (Exception e) {
        }

        view.findViewById(R.id.timer_advance_validate_btn).setOnClickListener(this);

        /*
         * Init the handlers for the 4 timers
         */
        boolean isSmall;
        int progressTypeInhale;
        int progressTypeHoldInhale;
        int progressTypeExhale;
        int progressTypeHoldExhale;
        if (ActivityBase.IS_TABLET) { // TABLET
            isSmall = false;
            progressTypeInhale = CircularSeekBarHandler.PROGRESS_TYPE_GREEN;
            progressTypeHoldInhale = CircularSeekBarHandler.PROGRESS_TYPE_BLUE;
            progressTypeExhale = CircularSeekBarHandler.PROGRESS_TYPE_GREEN;
            progressTypeHoldExhale = CircularSeekBarHandler.PROGRESS_TYPE_BLUE;
        } else { // PHONE
            isSmall = true;
            progressTypeInhale = CircularSeekBarHandler.PROGRESS_TYPE_GREEN;
            progressTypeHoldInhale = CircularSeekBarHandler.PROGRESS_TYPE_BLUE;
            progressTypeExhale = CircularSeekBarHandler.PROGRESS_TYPE_BLUE;
            progressTypeHoldExhale = CircularSeekBarHandler.PROGRESS_TYPE_GREEN;
        }
        // INHALE
        mTimerInhaleHandler = new CircularSeekBarHandler(view.findViewById(R.id.timer_advance_timer_inhale), isSmall, CircularSeekBarHandler.TIME_TYPE_LARGE_NORMAL_SINGLE, progressTypeInhale, -1, true);
        mTimerInhaleHandler.showThumb(false);
        // HOLD INHALE
        mTimerHoldInhaleHandler = new CircularSeekBarHandler(view.findViewById(R.id.timer_advance_timer_hold_inhale), isSmall, CircularSeekBarHandler.TIME_TYPE_LARGE_NORMAL_SINGLE, progressTypeHoldInhale, -1, true);
        mTimerHoldInhaleHandler.showThumb(false);
        // EXHALE
        mTimerExhaleHandler = new CircularSeekBarHandler(view.findViewById(R.id.timer_advance_timer_exhale), isSmall, CircularSeekBarHandler.TIME_TYPE_LARGE_NORMAL_SINGLE, progressTypeExhale, -1, true);
        mTimerExhaleHandler.showThumb(false);
        // HOLD EXHALE
        mTimerHoldExhaleHandler = new CircularSeekBarHandler(view.findViewById(R.id.timer_advance_timer_hold_exhale), isSmall, CircularSeekBarHandler.TIME_TYPE_LARGE_NORMAL_SINGLE, progressTypeHoldExhale, -1, true);
        mTimerHoldExhaleHandler.showThumb(false);
        // Set the default times
        setTimesOnTimers(mTimesBeginner);

        /*
         * Set default values for timers
         */
        // Get
        ExerciseTimes times = new ExerciseTimes();
        times.inhale = PersistentUtil.getInt(getActivity(), FragAnimationBase.PERSIST_TIME_INHALE);
        times.holdInhale = PersistentUtil.getInt(getActivity(), FragAnimationBase.PERSIST_TIME_HOLD_INHALE);
        times.exhale = PersistentUtil.getInt(getActivity(), FragAnimationBase.PERSIST_TIME_EXHALE);
        times.holdExhale = PersistentUtil.getInt(getActivity(), FragAnimationBase.PERSIST_TIME_HOLD_EXHALE);
        if (times.inhale == -1 && times.holdInhale == -1 && times.exhale == -1 && times.holdExhale == -1) {
            times.inhale = FragTimerAdvance.DEFAULT_TIMER_INHALE;
            times.holdInhale = FragTimerAdvance.DEFAULT_TIMER_HOLD_INHALE;
            times.exhale = FragTimerAdvance.DEFAULT_TIMER_EXHALE;
            times.holdExhale = FragTimerAdvance.DEFAULT_TIMER_HOLD_EXHALE;
        }
        // Set
        setTimesOnTimers(times);

    }

    private void saveSelectedExerciseType() {
        // Get the exercise type based on the selected tab in the tab menu
        mExerciseTypeSelected = getExerciseTypeFromSelectedTab();

        // Save the exercise type
        PersistentUtil.setInt(getActivity(), FragAnimationBase.EXERCISE_TYPE_START_CUSTOM, FragAnimationBase.PERSIST_EXERCISE_TYPE_START);
        PersistentUtil.setInt(getActivity(), FragAnimationBase.EXERCISE_TYPE_MIDDLE_ADVANCE, FragAnimationBase.PERSIST_EXERCISE_TYPE_MIDDLE);
        PersistentUtil.setInt(getActivity(), mExerciseTypeSelected, FragAnimationBase.PERSIST_EXERCISE_TYPE_END);
    }

    private int getExerciseTypeFromSelectedTab() {
        if (tab_beginner.isFocused()) {
            return FragAnimationBase.EXERCISE_TYPE_END_BEGINNER;
        }
        if (tab_intermediary.isFocused()) {
            return FragAnimationBase.EXERCISE_TYPE_END_INTERMEDIARY;
        }
        if (tab_pro.isFocused()) {
            return FragAnimationBase.EXERCISE_TYPE_END_PRO;
        }
        if (tab_custom.isFocused()) {
            return FragAnimationBase.EXERCISE_TYPE_END_CUSTOM;
        }

        return FragAnimationBase.EXERCISE_TYPE_END_BEGINNER; // DEFAULT
    }

    private void saveSelectedExerciseTimes() {
        // Get the times
        ExerciseTimes times = getExerciseTimesForSelectedTab();

        // Save them in shared preferences
        storeExerciseTimesInPreferences(getActivity(), times);
    }

    public static void storeExerciseTimesInPreferences(Context ctx, ExerciseTimes times) {
        PersistentUtil.setInt(ctx, times.inhale, FragAnimationBase.PERSIST_TIME_INHALE);
        PersistentUtil.setInt(ctx, times.holdInhale, FragAnimationBase.PERSIST_TIME_HOLD_INHALE);
        PersistentUtil.setInt(ctx, times.exhale, FragAnimationBase.PERSIST_TIME_EXHALE);
        PersistentUtil.setInt(ctx, times.holdExhale, FragAnimationBase.PERSIST_TIME_HOLD_EXHALE);
    }

    private ExerciseTimes getExerciseTimesForSelectedTab() {
        return new ExerciseTimes(mTimerInhaleHandler.getProgressMilliseconds(), mTimerHoldInhaleHandler.getProgressMilliseconds(), mTimerExhaleHandler.getProgressMilliseconds(), mTimerHoldExhaleHandler.getProgressMilliseconds(), -1);
    }

    private void setTimesOnTimers(ExerciseTimes times) {
        mTimerInhaleHandler.setProgressMillis(MIN_INHALE_AND_EXHALE_TIMES, MAX_TIMER_TIME, times.inhale);
        mTimerHoldInhaleHandler.setProgressMillis(MIN_HOLD_TIMES, MAX_TIMER_TIME, times.holdInhale);
        mTimerExhaleHandler.setProgressMillis(MIN_INHALE_AND_EXHALE_TIMES, MAX_TIMER_TIME, times.exhale);
        mTimerHoldExhaleHandler.setProgressMillis(MIN_HOLD_TIMES, MAX_TIMER_TIME, times.holdExhale);
    }

    private void onTabBeginnerClick() {
        setThumbsForAllTimers(false);
        setTimesOnTimers(mTimesBeginner);
    }

    private void onTabIntermediateClick() {
        setThumbsForAllTimers(false);
        setTimesOnTimers(mTimesIntermediate);
    }

    private void onTabProClick() {
        setTimesOnTimers(mTimesPro);
        setThumbsForAllTimers(false);
    }

    private void onTabCustomClick() {
        setThumbsForAllTimers(true);
    }

    private void setThumbsForAllTimers(boolean show) {
        mTimerInhaleHandler.showThumb(show);
        mTimerHoldInhaleHandler.showThumb(show);
        mTimerExhaleHandler.showThumb(show);
        mTimerHoldExhaleHandler.showThumb(show);
    }
}
