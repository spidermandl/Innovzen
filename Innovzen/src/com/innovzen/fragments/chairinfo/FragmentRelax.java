package com.innovzen.fragments.chairinfo;

import com.innovzen.o2chair.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.FooterHandler;

public class FragmentRelax extends FragBase implements OnClickListener{
    
    private FooterHandler mFooterHandler;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chair_info_reusable, container, false);
        init(view);
        return view;
    }
    
    @Override
    public void init(View view) {
        mFooterHandler = new FooterHandler(super.activityListener, (RelativeLayout)view.findViewById(R.id.viewpager_footer), FooterHandler.HOME, -1, FooterHandler.PLAY);
        view.findViewById(R.id.viewpager_page_more_button).setVisibility(View.INVISIBLE);
        ((TextView)view.findViewById(R.id.viewpager_page_content)).setText(R.string.relax_page_content);
        ((TextView)view.findViewById(R.id.viewpager_page_info_title)).setText(R.string.relax_page_title);
        ((TextView)view.findViewById(R.id.viewpager_page_info_subtitle)).setText(R.string.relax_page_subtitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        } 
    }

}
