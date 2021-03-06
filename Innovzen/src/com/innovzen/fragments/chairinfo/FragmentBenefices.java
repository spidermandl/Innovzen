package com.innovzen.fragments.chairinfo;

import com.innovzen.o2chair.R;
import com.innovzen.utils.Util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.interfaces.FragChairChildrenInterface;
import com.innovzen.interfaces.IResetFragmentContent;

public class FragmentBenefices extends FragBase implements OnClickListener, IResetFragmentContent {

    private TextView pageContent;
    private TextView pageSubtitle;
    private FooterHandler mFooterHandler;
    private FragChairChildrenInterface parentInterface;
    private View more_btn;

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
        ((ImageView) view.findViewById(R.id.viewpager_page_large_image)).setImageResource(R.drawable.chair_info_benefices);
        pageContent = (TextView) view.findViewById(R.id.viewpager_page_content);
        pageContent.setText(R.string.benefices_content_short);
        ((TextView) view.findViewById(R.id.viewpager_page_info_title)).setText(R.string.benefices_page_title);
        pageSubtitle = (TextView) view.findViewById(R.id.viewpager_page_info_subtitle);
        startResetThread();
    }

    /**
     * This checks how many passes have passed since the creation of the information fragment. Only at the second one we will delete the last fragment (the information one) and restore the usual last two fragments. This is neccessary to avoid the Recursive entry to executePendingTransactions exception when we call the notifydatasetchanged in adapter. It looks odd but it fixes the problem {@link #resetFragments()}
     */
    private void startResetThread() {
        int passes = this.parentInterface.getNumberOfPassesForExtraInfo();
        if (passes != 0) {
            if (passes == 2) {
                this.parentInterface.decrementNumberOfPassesForExtraInfo();
            } else {
                Thread newThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        resetFragments();
                    }
                });
                newThread.start();
            }
        }
    }

    /**
     * This only starts the method in the adapter that restores the usual flow of fragments. It needs to run on UI thread. Again, I know it look odd but it works
     */
    private void resetFragments() {
        this.parentInterface.decrementNumberOfPassesForExtraInfo();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parentInterface.restoreAfterExtraInfoFrag();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewpager_page_more_button:
                pageContent.setText(R.string.benefices_content_long);
                more_btn.setVisibility(View.INVISIBLE);
                pageSubtitle.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void resetContent() {
        if (Util.allViewsExist(pageContent, more_btn, pageSubtitle)) {
            pageContent.setText(R.string.benefices_content_short);
            more_btn.setVisibility(View.VISIBLE);
            pageSubtitle.setVisibility(View.VISIBLE);
        }

    }

}
