package com.innovzen.ui;

import com.innovzen.o2chair.R;
import com.innovzen.utils.Util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * 等比测量View
 * @author desmond.duan
 *
 */
public class MeasureView extends View{

	int rate_up;//屏幕等分比例分子
	int rate_down;//屏幕等分比例分母
	boolean orientation;//方向
	Context cT;
	private final static boolean HORIZONTAL=false,
			             VERTICAL=true;
	
	public MeasureView(Context context) {
		super(context);
		init(context, null);
	}
	
	public MeasureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	
	public MeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs){
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MeasureView);
		rate_up = a.getInt(R.styleable.MeasureView_rate_up, 1);
		rate_down = a.getInt(R.styleable.MeasureView_rate_down, 1);
		orientation = a.getBoolean(R.styleable.MeasureView_orientation, false);
		cT=context;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		setMeasuredDimension(orientation==HORIZONTAL?Util.getScreenDimensions(cT)[0]*rate_up/rate_down:1, 
				orientation==VERTICAL?Util.getScreenDimensions(cT)[1]*rate_up/rate_down:1);
	}
}
