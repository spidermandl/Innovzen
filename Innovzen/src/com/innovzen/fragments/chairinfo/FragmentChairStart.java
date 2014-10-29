
package com.innovzen.fragments.chairinfo;

import com.innovzen.o2chair.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.innovzen.activities.ActivityBase;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.FooterHandler;

public class FragmentChairStart extends FragBase implements OnClickListener {

    private FooterHandler mFooterHandler;
    
    private ImageView imageLeft;
    private ImageView imageRight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chair_info_start, container, false);
        init(view);
        return view;
    }

    @Override
    public void init(View view) {
        if (ActivityBase.IS_TABLET) {
            imageLeft = (ImageView)view.findViewById(R.id.waves_left);
            imageRight = (ImageView)view.findViewById(R.id.waves_right);            
            
            // Set ViewTree observers
            imageRight.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    
                    LayoutParams paramsLeft = (LayoutParams) imageLeft.getLayoutParams();
                    paramsLeft.height = imageRight.getHeight();
                    paramsLeft.width = imageRight.getWidth();
                    
                    imageLeft.setLayoutParams(paramsLeft);

                    return true;
                }
            });
            
        } else {
            float density = getResources().getDisplayMetrics().density;
            if (density <= 1.5 ) {
                ImageView imageWaves = (ImageView)view.findViewById(R.id.viewpager_start_curved_line);
                LayoutParams params = (LayoutParams) imageWaves.getLayoutParams();
                params.topMargin = (int) - getResources().getDimension(R.dimen.margin_top_waves);
                imageWaves.setLayoutParams(params);
            }
            mFooterHandler = new FooterHandler(super.activityListener, (RelativeLayout) view.findViewById(R.id.viewpager_footer), FooterHandler.HOME, -1, FooterHandler.PLAY);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }

    }

}
