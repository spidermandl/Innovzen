package com.innovzen.fragments.chairinfo;

import com.innovzen.o2chair.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.innovzen.activities.ActivityMain;
import com.innovzen.fragments.base.FragBase;
import com.innovzen.handlers.FooterHandler;
import com.innovzen.imagemap.ImageMap;
import com.innovzen.interfaces.FragChairChildrenInterface;
import com.innovzen.utils.Util;

public class FragmentChairFullInfo extends FragBase implements OnClickListener {

    // private TextView pageContent;
    // private TextView pageSubtitle;
    private final int FRAGMENTNUMBERTOJUMPTO = 8;

    private ImageMap mImageMap;

    private FragChairChildrenInterface parentInterface;

    private FooterHandler mFooterHandler;
    
    public void setArguments(FragChairChildrenInterface parentInterface) {
        this.parentInterface = parentInterface;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(View view) {
        // TODO Auto-generated method stub

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chair_info_full, container, false);

        mFooterHandler = new FooterHandler(super.activityListener, (RelativeLayout) view.findViewById(R.id.viewpager_footer), FooterHandler.HOME, -1, FooterHandler.PLAY);
        
        mImageMap = (com.innovzen.imagemap.ImageMap) view.findViewById(R.id.viewpager_page_large_image);
        mImageMap.setImageResource(R.drawable.chair_info_full_test);

        // add a click handler to react when areas are tapped
        mImageMap.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {
            @Override
            public void onImageMapClicked(int id, ImageMap imageMap) {
                // when the area is tapped, show the name in a
                // text bubble
                switch (id) {
                    case R.id.area1:
                        parentInterface.addExtraInfoFrag(new FragmentSoftware());
                        break;
                    case R.id.area2:
                        parentInterface.addExtraInfoFrag(new FragmentHeat());
                        break;
                    case R.id.area3:
                        parentInterface.addExtraInfoFrag(new FragmentSound());
                        break;
                    case R.id.area4:
                        parentInterface.addExtraInfoFrag(new FragmentZeroGravity());
                        break;
                    case R.id.area5:
                        parentInterface.addExtraInfoFrag(new FragmentAroma());
                        break;
                    case R.id.area6:
                        parentInterface.addExtraInfoFrag(new FragmentLeds());
                        break;
                    case R.id.area7:
                        parentInterface.addExtraInfoFrag(new FragmentRelax());
                        break;
                    case R.id.area8:
                        parentInterface.addExtraInfoFrag(new FragmentPulse());
                        break;
                    case R.id.area9:
                        parentInterface.addExtraInfoFrag(new FragmentPure());
                        break;
                    case R.id.area10:
                        parentInterface.addExtraInfoFrag(new FragmentSwing());
                        break;
                    default:
                        break;
                }
                parentInterface.setCurItem(FRAGMENTNUMBERTOJUMPTO);
            }

            @Override
            public void onBubbleClicked(int id) {
                // react to info bubble for area being tapped
            }
        });

        if (ActivityMain.IS_TABLET) {

            int screenH = Util.getScreenDimensions(getActivity())[1];
            int screenW = Util.getScreenDimensions(getActivity())[0];
            // Set up the fog image
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImageMap.getLayoutParams();
            double test = (int) screenW * 0.66489361;
            params.width = (int) test;
            mImageMap.setLayoutParams(params);
        }

        return view;
    }

}
