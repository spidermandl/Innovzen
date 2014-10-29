
package com.innovzen.ui;

import com.innovzen.o2chair.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AnimationBeachImageView extends ImageView {

    private Context mCtx;

    private Bitmap mImage;

    private int mDrawableResourceId;

    /** The rectangle holding the portion of the bitmap that we want to draw at this time */
    private Rect mRectDest = new Rect();

    /** The rectangle holding the bounds of this view */
    private Rect mRectSrc = new Rect();

    private int mTmpTop = -1;

    public AnimationBeachImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCtx = context;

        setAttributes(context, attrs);

        init();

    }

    public AnimationBeachImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCtx = context;

        setAttributes(context, attrs);

        init();
    }

    public AnimationBeachImageView(Context context) {
        super(context);
        this.mCtx = context;

        init();
    }

    private void init() {

        if (mDrawableResourceId != -1) {
            mImage = BitmapFactory.decodeResource(getResources(), mDrawableResourceId);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        
mImage = BitmapFactory.decodeResource(getResources(), mDrawableResourceId);
        
        mTmpTop = (int) (h * mImage.getWidth() / w);

        mRectSrc.set(0, mImage.getHeight() - mTmpTop, mImage.getWidth(), mImage.getHeight());

        // Re-set the destination rect (this entire view)
        if (mTmpTop > h) {
            mRectDest.set(0, h - (int) (mImage.getHeight() * w / mImage.getWidth()), w, h);
        } else {
            mRectDest.set(0, 0, w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mImage != null && mRectSrc != null && mRectDest != null) {
            canvas.drawBitmap(mImage, mRectSrc, mRectDest, null);
        }

    }

    /**
     * Retrieves the attributes entered in the layout .xml file and does appropriate initializations
     * 
     * @param ctx
     * @param attrs
     * @author MAB
     */
    private void setAttributes(Context ctx, AttributeSet attrs) {

        // Get the array of attributes
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.AnimationBeachImageView);

        // Get the attributes from the array
        try {
            mDrawableResourceId = a.getResourceId(R.styleable.AnimationBeachImageView_sunDrawable, -1);
        } finally {
            a.recycle();
        }

    }

}
