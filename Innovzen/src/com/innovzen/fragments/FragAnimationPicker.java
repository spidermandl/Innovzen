
package com.innovzen.fragments;

import com.innovzen.o2chair.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.ExerciseAnimationHandler;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.handlers.HeaderHandler;
import com.innovzen.handlers.SubheaderHandler;

public class FragAnimationPicker extends FragBase implements OnClickListener {

    // Hold shared pref keys
    public static final String PERSIST_SELECTED_EXERCISE_ANIMATION = "selected_exercise_animation";

    // Hold the default exercise animation in case one has not yet been selected
    public static final int DEFAULT_EXERCISE_ANIMATION = ExerciseAnimationHandler.ANIMATION_GRADIENT;

    // Hold the footer handler
    private FooterHandler mFooterHandler;

    // Hold the header handler
    private HeaderHandler mHeaderHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animation_picker, container, false);

        init(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.animation_picker_gradient_btn:
                // Let the activity know we've selected one
                super.activityListener.fragGradientAnimationPicked();
                break;
            case R.id.animation_picker_petals_btn:
                // Let the activity know we've selected one
                super.activityListener.fragPetalsAnimationPicked();
                break;
            case R.id.animation_picker_lungs_btn:
                // Let the activity know we've selected one
                super.activityListener.fragLungsAnimationPicked();
                break;
            case R.id.animation_picker_beach_btn:
                // Let the activity know we've selected one
                super.activityListener.fragBeachAnimationPicked();
                break;
        }
    }

    @Override
    public void init(View view) {

        // Init the footer
        mFooterHandler = new FooterHandler(super.activityListener, (RelativeLayout) view.findViewById(R.id.animation_picker_footer), FooterHandler.HOME, -1, -1);

        // Init the header
        mHeaderHandler = new HeaderHandler(getActivity(), (RelativeLayout) view.findViewById(R.id.reusable_header));
        mHeaderHandler.showLeftArrow(null);

        // Init the subheader handler
        new SubheaderHandler(getResources(), (TextView) view.findViewById(R.id.reusable_subheader), getString(R.string.subheader_base_reglages), getString(R.string.subheader_animation_picker));

        // Set event listeners
        view.findViewById(R.id.animation_picker_gradient_btn).setOnClickListener(this);
        view.findViewById(R.id.animation_picker_beach_btn).setOnClickListener(this);
        view.findViewById(R.id.animation_picker_petals_btn).setOnClickListener(this);
        view.findViewById(R.id.animation_picker_lungs_btn).setOnClickListener(this);
    }

}
