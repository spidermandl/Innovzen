package com.innovzen.fragments;

import adapters.MyPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovzen.interfaces.FragChairChildrenInterface;
import com.innovzen.interfaces.FragmentOnBackPressInterface;
import com.innovzen.o2chair.R;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;

public class FragChairInfo extends Fragment implements FragChairChildrenInterface, FragmentOnBackPressInterface {

    private MyPagerAdapter pageAdapter;
    private JazzyViewPager viewPager;

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.viewpager_layout, container, false);

        // initialize the pager
        pageAdapter = new MyPagerAdapter(getFragmentManager(), this);
        viewPager = (JazzyViewPager) view.findViewById(R.id.myViewPager);

        // set the desired effect
        // pager.setTransitionEffect(TransitionEffect.Accordion);

        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // Tell fragments (position-1) and (position+1) to reset themselves
                pageAdapter.resetFragments(position);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // set the adapter for the jazzy viewpager
        viewPager.setAdapter(pageAdapter);
        return view;
    }

    @Override
    public boolean onBackPress() {

        if (viewPager != null) {

            int curPageIndex = viewPager.getCurrentItem();

            if (curPageIndex > 0) {
                viewPager.setCurrentItem(curPageIndex - 1);

                return true;
            }
        }

        return false;
    }

    @Override
    public void setCurItem(int x) {
        viewPager.setCurrentItem(x);
    }

    @Override
    public void addExtraInfoFrag(Fragment frag) {
        pageAdapter.addExtraInformationScreen(frag);
    }

    @Override
    public void restoreAfterExtraInfoFrag() {
        pageAdapter.resetFragmentsAfterInfo();
    }

    @Override
    public int getNumberOfPassesForBreatheSecond() {
        return pageAdapter.numberOfPassesForSecondBreathe;
    }

    @Override
    public void decrementNumberOfPassesForBeatheSecond() {
        pageAdapter.numberOfPassesForSecondBreathe--;
    }

    @Override
    public int getNumberOfPassesForExtraInfo() {
        return pageAdapter.numberOfPassesForExtraInfo;
    }

    @Override
    public void decrementNumberOfPassesForExtraInfo() {
        pageAdapter.numberOfPassesForExtraInfo--;
    }

    @Override
    public void addBreatheSecondFrag() {
        pageAdapter.addBreatheSecond();
    }

    @Override
    public void removeBreatheSecondFrag() {
        pageAdapter.removeBreatheSecond();
    }

    @Override
    public Boolean isBreatheSecondActive() {
        return pageAdapter.isBreatheSecondActive;
    }
}
