
package com.innovzen.handlers;

import com.innovzen.o2chair.R;
import android.content.res.Resources;
import android.text.Html;
import android.widget.TextView;

public class SubheaderHandler {

    public SubheaderHandler(Resources res, TextView view, String first, String second) {

        if (res == null || view == null || first == null || second == null) {
            return;
        }

        String str = "<font color=\"" + res.getColor(R.color.subtitle_text_base) + "\">";
        str += first;
        str += "</font> ";
        str += "<b><font color=\"" + res.getColor(R.color.subtitle_text_highlighted) + "\">";
        str += second;
        str += "</font></b>";

        view.setText(Html.fromHtml(str));

    }
}
