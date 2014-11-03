package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.o2chair.R;
import com.innovzen.activities.ActivitySplashScreen;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.handlers.HeaderHandler;
import com.innovzen.handlers.SubheaderHandler;
import com.innovzen.interfaces.FragmentCommunicator;
import com.innovzen.utils.CustomClickListener;
import com.innovzen.utils.CustomClickListener.OnCustomClickListener;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;

public class AdapterHelp extends PagerAdapter {

    // Hold references to objects received in constructor
    private Context mCtx;
    private FragmentCommunicator mActivityListener;
    private OnCustomClickListener mListener; // Used for on back/forward press

    // Hold inflater
    private LayoutInflater mInflater;

    public AdapterHelp(Context ctx, FragmentCommunicator activityListener, OnCustomClickListener listener) {
        this.mCtx = ctx;
        this.mActivityListener = activityListener;
        this.mListener = listener;

        this.mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 3; // Only two views available for the help screen
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        // Inflate a new page
    	//<chy>
       // final View page = mInflater.inflate(R.layout.page_help, view, false);
    	final View page = mInflater.inflate(R.layout.page_help, view, false);
     //   </chy>
        Resources res = mCtx.getResources();

        // Initialize the header
        HeaderHandler header = new HeaderHandler((Activity) mCtx, (RelativeLayout) page.findViewById(R.id.reusable_header));
        header.showLeftArrow(new CustomClickListener(mListener, position));
        if (position == 0 || position == 1) {
            header.showRightArrow(new CustomClickListener(mListener, position));
        }

        // Initialize the subheader
        new SubheaderHandler(res, (TextView) page.findViewById(R.id.reusable_subheader), res.getString(R.string.subheader_base_notice), res.getString(R.string.subheader_help));

        // Initialize the footer
        new FooterHandler(mActivityListener, (RelativeLayout) page.findViewById(R.id.page_help_footer), FooterHandler.HOME, -1, -1);

        // Initialize the main container
        if (position == 0) {
            ((TextView) page.findViewById(R.id.page_help_main_content)).setText(Html.fromHtml(res.getString(R.string.help_first)));
        } else if (position == 1) {
            ((TextView) page.findViewById(R.id.page_help_main_content)).setText(Html.fromHtml(res.getString(R.string.help_second)));
        } else if (position == 2) {
            if (ActivitySplashScreen.isInnovzenApp()) {
                ((TextView) page.findViewById(R.id.page_help_main_content)).setText(Html.fromHtml(res.getString(R.string.help_third_innovzen)));
            } else {
                ((TextView) page.findViewById(R.id.page_help_main_content)).setText(Html.fromHtml(res.getString(R.string.help_third_o2chair)));
            }
        }

        // Keep the reference to the header handler so it won't get scrapped
        page.setTag(header);

        // Add the page to the viewpager
        ((JazzyViewPager) view).setObjectForPosition(page, position);
        ((JazzyViewPager) view).addView(page);

        return page;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {

        // Get the page of the viewpager
        RelativeLayout page = (RelativeLayout) obj;

        // Nullify the objects
        page.setTag(null);

        // Remove the page from the viewpager
        ((JazzyViewPager) container).removeView(page);

        // Nullify page
        page = null;

    }

}
