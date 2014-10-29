
package com.innovzen.customviews;

import com.innovzen.o2chair.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.SeekBar;

import com.innovzen.activities.ActivityMain;

public class CustomSeekBarWithText extends SeekBar {

    protected String overlayText = "180";
    protected Paint textPaint;
    private float textSize = 40;
    private float textPositionY = 58;

    private Context mCtx;

    public CustomSeekBarWithText(Context context) {
        super(context);
        this.mCtx = context;
        // Set up drawn text attributes here
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextAlign(Align.LEFT);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((ActivityMain) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        textPaint.setTextSize(textSize * (screenHeight / 1920f));
        textPositionY = textPositionY * (screenHeight / 1920f);

        init();
    }

    public CustomSeekBarWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCtx = context;
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextAlign(Align.LEFT);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((ActivityMain) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        textPaint.setTextSize(textSize * (screenHeight / 1920f));
        textPositionY = textPositionY * (screenHeight / 1920f);

        init();
    }

    public CustomSeekBarWithText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.mCtx = context;
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextAlign(Align.LEFT);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((ActivityMain) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        textPaint.setTextSize(textSize * (screenHeight / 1920f));
        textPositionY = textPositionY * (screenHeight / 1920f);

        init();
    }

    private void init() {
        Drawable newThumb = mCtx.getResources().getDrawable(R.drawable.seekbar_thumb);
        newThumb.setBounds(0, 0, 150, 150);
        setThumb(newThumb);
    }

    // This attempts to ensure that the text fits inside your SeekBar on a
    // resize
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    // Clients use this to change the displayed text
    public void setOverlayText(String text) {
        this.overlayText = text;
        invalidate();
    }

    // Draws the text onto the SeekBar
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // No text, no problem
        if (overlayText.length() == 0) {
            return;
        }

        canvas.save();

        float textWidth = textPaint.measureText(overlayText);
        int textHeight = (int) (Math.abs(textPaint.ascent()) + textPaint.descent() + 1);

        int height = getHeight();

        float positionX = (float) ((float) this.getWidth() - ((float) getThumbOffset() * 2)) / (float) getMax() * (float) this.getProgress();
        textPaint.setColor(Color.WHITE);

        int y = height / 2 + textHeight / 4;
        float x = positionX + getThumbOffset() - textWidth / 2f;

        canvas.drawText(overlayText, x, y, textPaint);

        canvas.restore();
    }
}
