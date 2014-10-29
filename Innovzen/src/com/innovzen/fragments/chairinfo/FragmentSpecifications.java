
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
import com.innovzen.handlers.SubheaderHandler;

public class FragmentSpecifications extends FragBase implements OnClickListener {

    private FooterHandler mFooterHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chair_specifications, container, false);
        init(view);

        return view;
    }

    @Override
    public void init(View view) {
        mFooterHandler = new FooterHandler(super.activityListener, (RelativeLayout) view.findViewById(R.id.viewpager_footer), FooterHandler.HOME, -1, FooterHandler.PLAY);
        // ((TextView)view.findViewById(R.id.viewpager_page_content)).setText(R.string.zero_gravity_page_content);

        new SubheaderHandler(getResources(), (TextView) view.findViewById(R.id.reusable_subheader), getString(R.string.subheader_base_chair_info_technical_specifications), getString(R.string.subheader_chair_info_technical_specifications));

        // ((TextView)view.findViewById(R.id.viewpager_page_info_subtitle)).setText(R.string.zero_gravity_page_subtitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
