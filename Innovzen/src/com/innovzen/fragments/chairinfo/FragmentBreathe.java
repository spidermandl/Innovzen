package com.innovzen.fragments.chairinfo;

import com.innovzen.o2chair.R;
import com.innovzen.utils.Util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.interfaces.FragChairChildrenInterface;
import com.innovzen.interfaces.IResetFragmentContent;

public class FragmentBreathe extends FragBase implements OnClickListener, IResetFragmentContent {

    private TextView pageContent;
    private TextView pageSubtitle;
    private View more_btn;
    private FooterHandler mFooterHandler;
    private FragChairChildrenInterface parentInterface;

    public void setArguments(FragChairChildrenInterface parentInterface) {
        this.parentInterface = parentInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chair_info_reusable, container, false);
        init(view);
        return view;
    }

    @Override
    public void init(View view) {
        mFooterHandler = new FooterHandler(super.activityListener, (RelativeLayout) view.findViewById(R.id.viewpager_footer), FooterHandler.HOME, -1, FooterHandler.PLAY);
        more_btn = view.findViewById(R.id.viewpager_page_more_button);
        more_btn.setOnClickListener(this);
        pageContent = (TextView) view.findViewById(R.id.viewpager_page_content);
        pageContent.setText(R.string.breathe_content_short);
        ((TextView) view.findViewById(R.id.viewpager_page_info_title)).setText(R.string.breathe_page_title);
        pageSubtitle = (TextView) view.findViewById(R.id.viewpager_page_info_subtitle);
        pageSubtitle.setText(R.string.breathe_page_subtitle);

        if (parentInterface.isBreatheSecondActive()) {
            more_btn.setVisibility(View.INVISIBLE);
            pageContent.setText(R.string.breathe_content_long);
            pageSubtitle.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewpager_page_more_button:
                pageContent.setText(R.string.breathe_content_long);
                pageSubtitle.setVisibility(View.GONE);
                more_btn.setVisibility(View.INVISIBLE);
                parentInterface.addBreatheSecondFrag();
                break;
            default:
                break;
        }
    }

    @Override
    public void resetContent() {
        if (Util.allViewsExist(pageContent, pageSubtitle, more_btn)) {
            pageContent.setText(R.string.breathe_content_short);
            pageSubtitle.setVisibility(View.VISIBLE);
            more_btn.setVisibility(View.VISIBLE);
        }

    }

}
