
package com.innovzen.utils;

import android.view.View;
import android.view.View.OnClickListener;

public class CustomClickListener implements OnClickListener {

    public interface OnCustomClickListener {

        public void OnCustomClick(View view, int position);

    }

    private OnCustomClickListener mCallback;
    private int mPosition;

    // Pass in the callback and the row position
    public CustomClickListener(OnCustomClickListener callback) {
        this.mCallback = callback;
    }

    public CustomClickListener(OnCustomClickListener callback, int position) {
        this.mCallback = callback;
        this.mPosition = position;
    }

    // The onClick method which has NO position information
    @Override
    public void onClick(View v) {
        mCallback.OnCustomClick(v, mPosition);
    }

}
