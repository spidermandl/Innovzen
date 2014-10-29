
package com.innovzen.animations.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.util.AttributeSet;
import android.view.View;

public class GradientView extends View {

    private RadialGradient gradient;
    private Paint p = new Paint();
    private int width = 0, height = 0, radius = 0;

    public GradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientView(Context context) {
        super(context);
    }

    public void init(Context context) {
        if (radius != 0) {
            gradient = new RadialGradient(width / 2, height / 2, radius, 0xFF35A9B9, 0xFF6FBB8D, android.graphics.Shader.TileMode.CLAMP);
            p.setDither(true);
            p.setShader(gradient);
            p.setAntiAlias(true);
        }
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRadius(Context ctx, int radius) {
        this.radius = radius;
        
        invalidate();
        
        init(ctx);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width / 2, height / 2, radius, p);
    }

}
