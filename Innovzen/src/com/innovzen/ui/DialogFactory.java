
package com.innovzen.ui;

import com.innovzen.o2chair.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;

import com.innovzen.callbacks.CallbackSingleOptionDialog;

public class DialogFactory {

    /**
     * Displays a dialog used to notify the user in case an error has occured which didn't crash the app but we can't exactly come back from it or we just don't want to handle it. :)
     * 
     * @param ctx
     * @param cause
     *            the cause of the error displayed after the "base message"<br/>
     *            Can be Null. If null, then a default "unknown" value will take its place
     * @param callback
     *            can be null
     * @author MAB
     */
    public static final void showUserExperienceDialog(Context ctx, String cause, final CallbackSingleOptionDialog callback) {

        // Get resources reference
        Resources res = ctx.getResources();

        // Instantiate alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        /*
         * Set its title and message
         */
        // Concat the message to be displayed
        String msg = ctx.getString(R.string.dialog_user_experience_msg);
        msg += "\n" + ctx.getString(R.string.dialog_user_experience_msg_cause) + " ";
        msg += (cause != null) ? cause : ctx.getString(R.string.dialog_user_experience_unknown_cause);
        // Get the tile to be displayed
        String title = ctx.getString(R.string.dialog_user_experience_title);
        // Set them both
        builder.setIcon(android.R.drawable.ic_dialog_info).setMessage(msg).setTitle(title);

        // Prevent the dialog to be dismissed on Back press or on semi-transparent black background tap
        builder.setCancelable(false);

        // Set event listeners for the buttons
        builder.setPositiveButton(res.getString(R.string.dialog_user_experience_positive_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onCallback();
                }
                dialog.dismiss();
            }
        }).show();

    }
}
