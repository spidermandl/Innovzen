package com.innovzen.fragments;

import com.innovzen.o2chair.R;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innovzen.activities.ActivitySplashScreen;
import com.innovzen.fragments.base.FragBase;

public class FragMainMenu extends FragBase implements OnClickListener {

    // View references
    private LinearLayout buttons_container;
    private ImageButton chair_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        init(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu_chair_btn:
                super.activityListener.fragGoToChairInfo(true);
                break;
            case R.id.main_menu_play_btn:
                // TODO: remove this
                super.activityListener.fragGoToAnimation(true);
                // TODO: remove this
                break;
        }
    }

    /**
     * Does proper initializations
     * 
     * @author MAB
     */
    @Override
    public void init(View view) {

        // Get view references
        buttons_container = (LinearLayout) view.findViewById(R.id.main_menu_buttons_container);
        chair_btn = (ImageButton) view.findViewById(R.id.main_menu_chair_btn);

        // Set event listener
        view.findViewById(R.id.main_menu_play_btn).setOnClickListener(this);
        chair_btn.setOnClickListener(this);

        // Set the text for the title
        ((TextView) view.findViewById(R.id.main_menu_title)).setText(Html.fromHtml(getResources().getString(R.string.main_menu_title)));

        // Depending on the type of app this is, show or hide the slider portion of the app (slider button)
        if (ActivitySplashScreen.isInnovzenApp()) {

            buttons_container.setWeightSum(1);

            chair_btn.setVisibility(View.GONE);

        }
    }
}
