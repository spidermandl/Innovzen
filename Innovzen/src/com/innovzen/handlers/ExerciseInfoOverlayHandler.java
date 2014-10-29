package com.innovzen.handlers;

import com.innovzen.o2chair.R;
import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.activities.ActivityBase;
import com.innovzen.utils.Util;

public class ExerciseInfoOverlayHandler implements OnClickListener {

    // Hold the overlay types
    public static final int OVERLAY_TYPE_RELAX = 0;
    public static final int OVERLAY_TYPE_PERFORMANCE = 1;
    public static final int OVERLAY_TYPE_ECHILIBRE = 2;
    public static final int OVERLAY_TYPE_CUSTOM = 3;

    // Hold the resources reference
    private Resources mRes;

    // Hold the overlay view
    private RelativeLayout overlay;

    // Hold the views inside the overlay for quick reference
    private ImageView icon;
    private TextView text;

    public ExerciseInfoOverlayHandler(Context ctx, View overlay) {
        this.mRes = ctx.getResources();
        this.overlay = (RelativeLayout) overlay;

        init(ctx);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exercise_picker_overlay_close_btn:
                hide();
                break;
        }

    }

    /**
     * Does proper initializations
     * 
     * @param ctx
     * @author MAB
     */
    private void init(Context ctx) {

        // Get view references
        this.icon = (ImageView) this.overlay.findViewById(R.id.exercise_picker_overlay_icon);
        this.text = (TextView) this.overlay.findViewById(R.id.exercise_picker_overlay_text);

        // Set event listeners
        this.overlay.findViewById(R.id.exercise_picker_overlay_close_btn).setOnClickListener(this);

        // In case the device is a tablet, then resize the width of the text
        if (ActivityBase.IS_TABLET) {
            ViewGroup.LayoutParams params = this.text.getLayoutParams();
            params.width = (int) (Util.getScreenDimensions(ctx)[0] * 0.4f);
            this.text.setLayoutParams(params);
        }

    }

    /**
     * Makes the overlay visible but before that it adds the appropriate icon and text to it
     * 
     * @param overlayType
     *            see the constants defined in this class
     * @author MAB
     */
    public void show(int overlayType) {

        String strBeginning = null;
        String strMain = null;
        int iconResId = -1;

        switch (overlayType) {
            case OVERLAY_TYPE_ECHILIBRE:
                strBeginning = mRes.getString(R.string.exercise_picker_echilibre_text).replace("\n", "");
                strMain = mRes.getString(R.string.exercise_picker_overlay_echilibre);
                iconResId = R.drawable.icon_overlay_echilibre;
                break;
            case OVERLAY_TYPE_PERFORMANCE:
                strBeginning = mRes.getString(R.string.exercise_picker_performance_text).replace("\n", "");
                strMain = mRes.getString(R.string.exercise_picker_overlay_performance);
                iconResId = R.drawable.icon_overlay_performance;
                break;
            case OVERLAY_TYPE_RELAX:
                strBeginning = mRes.getString(R.string.exercise_picker_relax_text).replace("\n", "");
                strMain = mRes.getString(R.string.exercise_picker_overlay_relax);
                iconResId = R.drawable.icon_overlay_relax;
                break;
            case OVERLAY_TYPE_CUSTOM:
                strBeginning = mRes.getString(R.string.exercise_picker_custom_text);
                strMain = mRes.getString(R.string.exercise_picker_overlay_custom);
                iconResId = R.drawable.icon_overlay_custom;
                break;
        }

        if (strBeginning != null && strMain != null && iconResId != -1) {
            // Set the text
            String str = "<font color=\"" + mRes.getColor(R.color.exercise_picker_overlay_text_beginning) + "\">";
            str += strBeginning;
            // str += (overlayType == OVERLAY_TYPE_CUSTOM) ? "" : ".";
            str += " : ";
            str += "</font> ";
            str += strMain;
            text.setText(Html.fromHtml(str));

            // Set the icon
            icon.setImageResource(iconResId);

            // Make the overlay visible
            overlay.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Hides the info overlay
     * 
     * @author MAB
     */
    public void hide() {
        if (overlay != null) {
            overlay.setVisibility(View.GONE);
        }
    }

    /**
     * @return true if the overlay is currently visible
     * @author MAB
     */
    public boolean isVisible() {
        if (overlay != null) {
            return (overlay.getVisibility() == View.VISIBLE);
        }

        return false;
    }

}
