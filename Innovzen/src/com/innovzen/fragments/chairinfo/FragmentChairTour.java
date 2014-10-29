package com.innovzen.fragments.chairinfo;

import com.innovzen.o2chair.R;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.innovzen.activities.ActivityMain;
import com.innovzen.customviews.CustomSeekBarWithText;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.utils.Util;

public class FragmentChairTour extends FragBase implements OnClickListener {

    private TextView pageContent;
    private TextView pageSubtitle;
    private FooterHandler mFooterHandler;
    private ImageView animImage;
    private CustomSeekBarWithText seekBar;

    // private ImageView chairFog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chair_tour, container, false);
        init(view);
        return view;
    }

    @Override
    public void init(View view) {
        mFooterHandler = new FooterHandler(super.activityListener, (RelativeLayout) view.findViewById(R.id.viewpager_footer), FooterHandler.HOME, -1, FooterHandler.PLAY);
        animImage = (ImageView) view.findViewById(R.id.viewpager_page_large_image);
        seekBar = (CustomSeekBarWithText) view.findViewById(R.id.viewpager_page_chiar_tour_seekbar_text);

        if (ActivityMain.IS_TABLET) {

            int screenH = Util.getScreenDimensions(getActivity())[1];

            // chairFog = (ImageView) view.findViewById(R.id.chair_tour_fog);

            RelativeLayout.LayoutParams params = (LayoutParams) seekBar.getLayoutParams();
            params.width = (int) (Util.getScreenDimensions(getActivity())[0] * 0.5f);
            seekBar.setLayoutParams(params);

            // Set up the chair dimensions
            RelativeLayout.LayoutParams paramsAnimImg = (RelativeLayout.LayoutParams) animImage.getLayoutParams();
            paramsAnimImg.height = (int) (screenH * 0.7558459f);
            paramsAnimImg.width = paramsAnimImg.height;
            animImage.setLayoutParams(paramsAnimImg);

            // Set up the fog image
            // RelativeLayout.LayoutParams paramsFog = (RelativeLayout.LayoutParams) chairFog.getLayoutParams();
            // paramsFog.height = (int) (screenH * 0.7971114f);
            // paramsFog.width = (int) (paramsFog.height * 1.16997411f);
            // chairFog.setLayoutParams(paramsFog);

        }

        // animImage.setBackgroundResource(R.drawable.chair_0);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setImage(progress);
            }
        });
    }

    private void setImage(int progress) {
        int prog = progress - 180;

        if (prog < 0) {
            prog += 361;
            if (prog == 360) {
                prog = 0;
            }
        }
        String uri = "drawable/chair_" + prog;

        int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
        Drawable image = getResources().getDrawable(imageResource);

        animImage.setImageDrawable(image);
        seekBar.setOverlayText(progress + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewpager_page_more_button:
                pageContent.setText(R.string.pulse_content_long);
                v.setVisibility(View.INVISIBLE);
                pageSubtitle.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

}
