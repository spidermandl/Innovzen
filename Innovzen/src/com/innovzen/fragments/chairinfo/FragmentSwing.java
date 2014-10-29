package com.innovzen.fragments.chairinfo;

import com.innovzen.o2chair.R;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.FooterHandler;

public class FragmentSwing extends FragBase {

    private FooterHandler mFooterHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chair_info_reusable, container, false);
        init(view);
        return view;
    }

    @Override
    public void init(View view) {
        mFooterHandler = new FooterHandler(super.activityListener, (RelativeLayout) view.findViewById(R.id.viewpager_footer), FooterHandler.HOME, -1, FooterHandler.PLAY);

        view.findViewById(R.id.viewpager_page_more_button).setVisibility(View.INVISIBLE);

        ((ImageView) view.findViewById(R.id.viewpager_page_large_image)).setImageResource(R.drawable.chair_info_swing);
        ((TextView) view.findViewById(R.id.viewpager_page_content)).setText(Html.fromHtml(getResources().getString(R.string.swing_page_content)));
        ((TextView) view.findViewById(R.id.viewpager_page_info_title)).setText(R.string.swing_page_title);
        ((TextView) view.findViewById(R.id.viewpager_page_info_subtitle)).setText(R.string.swing_page_subtitle);
    }
}
