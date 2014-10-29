package adapters;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.innovzen.fragments.chairinfo.FragmentBenefices;
import com.innovzen.fragments.chairinfo.FragmentBreathe;
import com.innovzen.fragments.chairinfo.FragmentBreatheSecond;
import com.innovzen.fragments.chairinfo.FragmentChairFullInfo;
import com.innovzen.fragments.chairinfo.FragmentChairStart;
import com.innovzen.fragments.chairinfo.FragmentChairTour;
import com.innovzen.fragments.chairinfo.FragmentLeConcept;
import com.innovzen.fragments.chairinfo.FragmentLesFaits;
import com.innovzen.fragments.chairinfo.FragmentPulse;
import com.innovzen.fragments.chairinfo.FragmentSpecifications;
import com.innovzen.interfaces.FragChairChildrenInterface;
import com.innovzen.interfaces.IResetFragmentContent;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private final int BREATHESECONDFRAGMENTNUMBER = 4;

    public int numberOfPassesForSecondBreathe = 0;
    public int numberOfPassesForExtraInfo = 0;
    public Boolean isBreatheSecondActive = false;

    private FragChairChildrenInterface mParentFragInterface;

    private List<Fragment> fragments;

    /**
     * @param fm
     *            the fragment manager required for view pager
     */
    public MyPagerAdapter(FragmentManager fm, FragChairChildrenInterface parentFragInterface) {
        super(fm);
        // initialize the fragment list
        this.mParentFragInterface = parentFragInterface;
        this.fragments = new ArrayList<Fragment>();

        InitFragments();
    }

    private void InitFragments() {
        // add the fragments to the list in the desired order
        fragments.add(new FragmentChairStart());
        fragments.add(new FragmentLesFaits());

        FragmentLeConcept fragLeConcept = new FragmentLeConcept();
        fragLeConcept.setArguments(mParentFragInterface);
        fragments.add(fragLeConcept);

        FragmentBreathe fragBreathe = new FragmentBreathe();
        fragBreathe.setArguments(mParentFragInterface);
        fragments.add(fragBreathe);

        fragments.add(new FragmentPulse());

        FragmentBenefices fragBenefices = new FragmentBenefices();
        fragBenefices.setArguments(mParentFragInterface);
        fragments.add(fragBenefices);

        FragmentChairFullInfo newFullInfo = new FragmentChairFullInfo();
        newFullInfo.setArguments(mParentFragInterface);
        fragments.add(newFullInfo);

        fragments.add(new FragmentChairTour());
        fragments.add(new FragmentSpecifications());
        // fragments.add(new FragmentRelax());
        // fragments.add(new FragmentAroma());
        // fragments.add(new FragmentSoftware());
        // fragments.add(new FragmentHeat());
        // fragments.add(new FragmentSound());
        // fragments.add(new FragmentZeroGravity());
        // fragments.add(new FragmentLeds());
        // fragments.add(new FragmentCheck());
        // fragments.add(new FragmentPure());
    }

    public void addBreatheSecond() {
        fragments.add(BREATHESECONDFRAGMENTNUMBER, new FragmentBreatheSecond());
        numberOfPassesForSecondBreathe = 2;
        isBreatheSecondActive = true;
        notifyDataSetChanged();
    }

    public void removeBreatheSecond() {
        fragments.remove(BREATHESECONDFRAGMENTNUMBER);
        isBreatheSecondActive = false;
        notifyDataSetChanged();
    }

    /**
     * This is for adding the neccessary fragment when a button is clicked on the chair full info fragment
     */
    public void addExtraInformationScreen(Fragment frag) {
        fragments.remove(fragments.size() - 1);
        fragments.remove(fragments.size() - 1);
        fragments.add(frag);
        numberOfPassesForExtraInfo = 2;
        notifyDataSetChanged();
    }

    public void resetFragmentsAfterInfo() {
        fragments.remove(fragments.size() - 1);
        fragments.add(new FragmentChairTour());
        fragments.add(new FragmentSpecifications());

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Object obj = super.instantiateItem(container, position);
        ((JazzyViewPager) container).setObjectForPosition(obj, position);
        return obj;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    /**
     * Reset the content of the (position+1) and (position-1) fragments
     * 
     * @param position
     * @author MAB
     */
    public void resetFragments(int position) {

        if (fragments == null) {
            return;
        }

        // Reset the fragment to the LEFT
        if (position - 1 >= 0 && fragments.size() >= 1) {
            if (fragments.get(position - 1) instanceof IResetFragmentContent) {
                ((IResetFragmentContent) fragments.get(position - 1)).resetContent();
            }

        }

        // Reset the fragment to the RIGHT
        if (position + 1 < fragments.size()) {
            if (fragments.get(position + 1) instanceof IResetFragmentContent) {
                ((IResetFragmentContent) fragments.get(position + 1)).resetContent();
            }
        }

    }
}