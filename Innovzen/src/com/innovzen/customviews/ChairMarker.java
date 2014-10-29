
package com.innovzen.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class ChairMarker extends RelativeLayout {

    private Context mCtx;

    public ChairMarker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCtx = context;
        // setAttributes(context, attrs);

        init();
    }

    public ChairMarker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCtx = context;
        // setAttributes(context, attrs);

        init();
    }

    public ChairMarker(Context context) {
        super(context);
        this.mCtx = context;

        init();
    }

    private void init() {

    }
}
