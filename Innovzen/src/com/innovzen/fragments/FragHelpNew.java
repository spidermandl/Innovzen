package com.innovzen.fragments;

import adapters.AdapterHelp;
import com.innovzen.o2chair.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.innovzen.fragments.base.FragBase;
import com.innovzen.interfaces.FragmentOnBackPressInterface;
import com.innovzen.utils.CustomClickListener.OnCustomClickListener;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;

public class FragHelpNew extends FragBase implements OnCustomClickListener, FragmentOnBackPressInterface {

    // Hold the viewpager reference
    private JazzyViewPager viewpager;

    // Hold the viewpager adapter
    private AdapterHelp mAdapter;

	private ImageView helpBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_helpnew, container, false);

        init(view);

        return view;
    }

    @Override
    public void OnCustomClick(View view, int position) {
        switch (view.getId()) {
            case R.id.reusable_header_arrow_left:
                if (!onGenericBackPress(position)) {
                    getActivity().onBackPressed();
                }
                break;
            case R.id.reusable_header_arrow_right:
                switch (position) {
                    case 0:
                        viewpager.setCurrentItem(1);
                        break;
                    case 1:
                        viewpager.setCurrentItem(2);
                        break;
                }
                break;
            case R.id.helpBack:
            	getActivity().onBackPressed();
            	break;
        }
    }

    @Override
    public boolean onBackPress() {
        return onGenericBackPress(viewpager.getCurrentItem());
    }

    @Override
    public void init(View view) {
    	//<chy>  ·µ»Ø°´Å¥
         helpBack = (ImageView) view.findViewById(R.id.helpBack);
         helpBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 getActivity().onBackPressed();			
			}
		});
    	//</chy>
        // Get references
        viewpager = (JazzyViewPager) view.findViewById(R.id.help_viewpager);

        // Init the viewpager adapter
        mAdapter = new AdapterHelp(getActivity(), super.activityListener, this);

        // Set the adapter to the viewpager
        viewpager.setAdapter(mAdapter);

    }

    /**
     * When either the user presses the physical back button or presses the left arrow, it goes here
     * 
     * @param pageIndex
     *            the index of the page currently being shown
     * @return true if we've handled the event, or false if the activity should handle it (aka remove the fragment)
     * @author MAB
     */
    private boolean onGenericBackPress(int pageIndex) {
        switch (pageIndex) {
            case 0:
                return false;
            case 1:
                viewpager.setCurrentItem(0);
                return true;
            case 2:
                viewpager.setCurrentItem(1);
                return true;
        }

        return false;
    }
}
