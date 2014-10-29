
package com.innovzen.fragments;

import com.innovzen.o2chair.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.innovzen.fragments.base.FragAnimationBase;
import com.innovzen.handlers.FooterHandler;

/**
 * Fragment should be called with some extras. See ANIMATION_xxx for types of animations available (int values) and also use {@link #KEY_ANIMATION_TYPE} for the bundle key<br/>
 * Note: If no animation type specified in the bundle, the default {@link #ANIMATION_PETALS} will be used
 * 
 * @author MAB
 * 
 */
public class FragAnimationPhone extends FragAnimationBase implements OnClickListener {

    // Hold view references
    private View subtitle_container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animation, container, false);

        super.onView(view);

        initialize(view);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.animation_animation_container:
                pauseExercise();
                break;
            case R.id.animation_fullscreen:
                toggleFullscreen();
                break;
        }

    }

    /**
     * Does proper initializations
     * 
     * @author MAB
     */
    private void initialize(View view) {

        // Get references
        super.footer = (RelativeLayout) view.findViewById(R.id.animation_footer);
        View animation_container = view.findViewById(R.id.animation_animation_container);
        subtitle_container = view.findViewById(R.id.animation_type);

        // Set event listeners
        animation_container.setOnClickListener(this);
        view.findViewById(R.id.animation_fullscreen).setOnClickListener(this);
        view.findViewById(R.id.animation_play_overlay_btn).setOnClickListener(super.mClickListener);

        /*
         * Init the footer
         */
        super.mFooterHandler = new FooterHandler(super.activityListener, super.footer, FooterHandler.HOME, FooterHandler.HELP, FooterHandler.TIMER);
        super.mFooterHandler.setRightIconOnClickListener(super.mClickListener);

    }

    private void toggleFullscreen() {

        // Check if it's in fullscreen mode by looking at the visibility of the footer
        if (super.footer.getVisibility() == View.VISIBLE) { // currently NOT in fullscreen mode

            /* Make stuff disappear */

            super.enableFullscreen();

            subtitle_container.setVisibility(View.GONE);

        } else { // currently in fullscreen mode

            /* Make stuff appear */

            super.disableFullscreen();

            subtitle_container.setVisibility(View.VISIBLE);
        }
    };
}
