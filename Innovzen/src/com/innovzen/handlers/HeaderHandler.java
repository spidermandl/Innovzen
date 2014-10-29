
package com.innovzen.handlers;

import com.innovzen.o2chair.R;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class HeaderHandler implements OnClickListener {

    private RelativeLayout mHeader;

    private Activity mActivity;

    public HeaderHandler(Activity activity, RelativeLayout header, boolean leftVisible, boolean rightVisible, OnClickListener listenerForBothArrows) {
        this.mHeader = header;
        this.mActivity = activity;

        if (listenerForBothArrows != null) {
            if (leftVisible) {
                showLeftArrow(listenerForBothArrows);
            }

            if (rightVisible) {
                showRightArrow(listenerForBothArrows);
            }
        }

        header.findViewById(R.id.reusable_header_arrow_left).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reusable_header_arrow_left:
                mActivity.onBackPressed();
                break;
        }

    }

    public HeaderHandler(Activity activity, RelativeLayout header) {
        this(activity, header, false, false, null);
    }

    public void showLeftArrow(OnClickListener listener) {
        View view = mHeader.findViewById(R.id.reusable_header_arrow_left);
        if (listener != null) {
            view.setOnClickListener(listener);
        }
        view.setVisibility(View.VISIBLE);
    }

    public void hideLeftArrow() {
        View view = mHeader.findViewById(R.id.reusable_header_arrow_left);
        view.setVisibility(View.INVISIBLE);
    }

    public void showRightArrow(OnClickListener listener) {
        View view = mHeader.findViewById(R.id.reusable_header_arrow_right);
        if (listener != null) {
            view.setOnClickListener(listener);
        }
        view.setVisibility(View.VISIBLE);
    }

    public void hideRightArrow() {
        View view = mHeader.findViewById(R.id.reusable_header_arrow_right);
        view.setVisibility(View.INVISIBLE);
    }
}
