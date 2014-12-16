package com.innovzen.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class CircleProgressBar extends View {

	private int maxProgress = 100;
	private int progress = 15;
	private int progressStrokeWidth = 2;
	private int marxArcStorkeWidth = 16;
	// 鐢诲渾鎵�湪鐨勮窛褰㈠尯鍩�
	RectF oval;
	Paint paint;

	public CircleProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		oval = new RectF();
		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		super.onDraw(canvas);
		int width = this.getWidth();
		int height = this.getHeight();

		width = (width > height) ? height : width;
		height = (width > height) ? height : width;

		paint.setAntiAlias(true); // 璁剧疆鐢荤瑪涓烘姉閿娇
		paint.setColor(Color.WHITE); // 璁剧疆鐢荤瑪棰滆壊
		canvas.drawColor(Color.TRANSPARENT); // 鐧借壊鑳屾櫙
		paint.setStrokeWidth(progressStrokeWidth); // 绾垮
		paint.setStyle(Style.STROKE);

		oval.left = marxArcStorkeWidth / 2; // 宸︿笂瑙抶
		oval.top = marxArcStorkeWidth / 2; // 宸︿笂瑙抷
		oval.right = width - marxArcStorkeWidth / 2; // 宸︿笅瑙抶
		oval.bottom = height - marxArcStorkeWidth / 2; // 鍙充笅瑙抷

		canvas.drawArc(oval, -90, 360, false, paint); // 缁樺埗鐧借壊鍦嗗湀锛屽嵆杩涘害鏉¤儗鏅�
		paint.setColor(Color.rgb(0x57, 0x87, 0xb6));
		paint.setStrokeWidth(marxArcStorkeWidth);
		canvas.drawArc(oval, -90, ((float) progress / maxProgress) * 360,
				false, paint); // 缁樺埗杩涘害鍦嗗姬锛岃繖閲屾槸钃濊壊

		paint.setStrokeWidth(1);
		String text = progress + "%";
		int textHeight = height / 4;
		paint.setTextSize(textHeight);
		int textWidth = (int) paint.measureText(text, 0, text.length());
		paint.setStyle(Style.FILL);
		canvas.drawText(text, width / 2 - textWidth / 2, height / 2
				+ textHeight / 2, paint);

	}

	public int getMaxProgress() {
		return maxProgress;
	}

	public void setMaxProgress(int maxProgress) {
		this.maxProgress = maxProgress;
	}

	/**
	 * 璁剧疆杩涘害
	 * 
	 * @param progress
	 *            杩涘害鐧惧垎姣�
	 * @param view
	 *            鏍囪瘑杩涘害鐨勮妭鐐硅鍥�
	 */
	public void setProgress(int progress, View view) {
		this.progress = progress;
		view.setAnimation(pointRotationAnima(0,
				(int) (((float) 360 / maxProgress) * progress)));
		this.invalidate();
	}

	/**
	 * 闈烇嫉锛╃嚎绋嬭皟鐢�
	 */
	public void setProgressNotInUiThread(int progress, View view) {
		this.progress = progress;
		view.setAnimation(pointRotationAnima(0,
				(int) (((float) 360 / maxProgress) * progress)));
		this.postInvalidate();
	}

	/**
	 * 杩涘害鏍囨敞鐐圭殑鍔ㄧ敾
	 * 
	 * @param fromDegrees
	 * @param toDegrees
	 * @return
	 */
	private Animation pointRotationAnima(float fromDegrees, float toDegrees) {
		int initDegress = 306;// 杩涘害鐐硅捣濮嬩綅缃�鍥剧墖鍋忕Щ绾�4搴�
		RotateAnimation animation = new RotateAnimation(fromDegrees,
				initDegress + toDegrees, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(1);// 璁剧疆鍔ㄧ敾鎵ц鏃堕棿
		animation.setRepeatCount(1);// 璁剧疆閲嶅鎵ц娆℃暟
		animation.setFillAfter(true);// 璁剧疆鍔ㄧ敾缁撴潫鍚庢槸鍚﹀仠鐣欏湪缁撴潫浣嶇疆
		return animation;
	}

}