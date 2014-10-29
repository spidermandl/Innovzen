
package com.innovzen.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class PetalImageView extends ImageView {
    public PetalImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public PetalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public PetalImageView(Context context) {
        super(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }

}
