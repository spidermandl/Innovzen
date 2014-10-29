package com.innovzen.handlers;

import com.innovzen.o2chair.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChairMarkerHandler {

	public static final int ABOVE = 0;
	public static final int BELOW = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	private View view;
	private String text;
	private OnClickListener clickListener;
	private int textPosition = -1, textMargin = -1;
	private int buttonLeft = -1;
	private int buttonTop = -1;

	private TextView textView;
	private ImageButton button;

	public ChairMarkerHandler(RelativeLayout view, int textPosition, String text, OnClickListener listener, int left, int top, int textLeftMargin) {
		this.textPosition = textPosition;
		this.text = text;
		this.clickListener = listener;
		this.view = view;
		this.textMargin = textLeftMargin;
		this.buttonLeft = left;
		this.buttonTop = top;

		init();
	}

	public ChairMarkerHandler(RelativeLayout view, int textPosition, String text, OnClickListener listener, int left, int top) {
		this(view, textPosition, text, listener, left, top, -1);
	}

	private void init() {
		button = (ImageButton) view.findViewById(R.id.chair_marker_button);
		textView = (TextView) view.findViewById(R.id.chair_marker_text);

		button.setOnClickListener(clickListener);
		textView.setText(text);

		positionStuff();
	}

	private void positionStuff() {

		RelativeLayout.LayoutParams paramsText = null;
		RelativeLayout.LayoutParams paramsButton = null;
		paramsText = (RelativeLayout.LayoutParams) textView.getLayoutParams();
		paramsButton = (RelativeLayout.LayoutParams) button.getLayoutParams();

		switch (textPosition) {
		case RIGHT:
			paramsText.addRule(RelativeLayout.RIGHT_OF, R.id.chair_marker_button);
			paramsText.setMargins(10, buttonTop + 7, -1, -1);
			paramsButton.setMargins(buttonLeft, buttonTop, -1, -1);
			break;
		case BELOW:
			paramsText.addRule(RelativeLayout.BELOW, R.id.chair_marker_button);
			button.setPadding(textMargin, 0, 0, 0);
			paramsText.setMargins(buttonLeft - 20, 0, -1, -1);
			paramsButton.setMargins(buttonLeft, buttonTop, -1, -1);
			break;
		case LEFT:
			paramsButton.addRule(RelativeLayout.RIGHT_OF, R.id.chair_marker_text);
			paramsText.addRule(RelativeLayout.CENTER_VERTICAL);
			paramsText.setMargins(buttonLeft, buttonTop + 12, -1, -1);
			paramsButton.setMargins(10, buttonTop / 2, -1, -1);
			break;
		case ABOVE:
			paramsText.addRule(RelativeLayout.ABOVE, R.id.chair_marker_text);
			button.setPadding(textMargin, 0, 0, 0);
			paramsText.setMargins(buttonLeft - 20, buttonTop - 25, -1, -1);
			paramsButton.setMargins(buttonLeft, buttonTop, -1, -1);
			break;
		default:
			break;
		}

		textView.setLayoutParams(paramsText);
		button.setLayoutParams(paramsButton);
	}

}
