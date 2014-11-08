package com.innovzen.activities;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.innovzen.fragments.FragAnimationTabletNew;
import com.innovzen.o2chair.R;
import com.innovzen.utils.Util;

public class ActivityBase extends FragmentActivity {

	public static int MyState=0;
    // A flag to indicate whether this is a tablet or phone
    // Use a static variable instead of calling isTablet every time so we don't
	/**
	 * Desmond
	 * �ж���android�ֻ�����androidƽ��
	 */
    public static boolean IS_TABLET;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Get the device type
        //�������Ƿ���ƽ�����
        /**
         * Desmond
         * д��ƽ��
         */
        ActivityBase.IS_TABLET = Util.isTablet(this);

        // If tablet, force in landscape. If not, keep in portrait mode
        //�������Ż�������
        /**
         * Desmond
         * ƽ����ʾΪ�������ֻ���ʾΪ����
         */
        if (ActivityBase.IS_TABLET) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    /**
     * Navigate to a specific fragment
     * 
     * @param fragmentClass
     * @param bundle
     *            May be null
     * @param addToBackstack
     * @param fragmentTag
     *            May be null
     * @author MAB
     */
    //��ǰfragment��֮ǰ�Ƿ���ͨһ�����֣��������,��bundle�����뵱ǰ��
    public void navigateTo(Class<?> fragmentClass, Bundle bundle, boolean addToBackstack, String fragmentTag) {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        String curr_fragment_TAG = "";
        if (currentFragment != null) {
        	                             //////
            curr_fragment_TAG = currentFragment.getClass().getSimpleName();
        }

        if (!curr_fragment_TAG.contentEquals(fragmentClass.getSimpleName())) {
            try {
                currentFragment = (Fragment) fragmentClass.newInstance();
                if (bundle != null) {
                    currentFragment.setArguments(bundle);
                }

                FragmentManager fragManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragManager.beginTransaction();
                transaction.replace(R.id.fragment_container, currentFragment);
                if (addToBackstack) {
                    transaction.addToBackStack(fragmentTag);//�����˻�ջ
                }
                
                /* this is not the proper resolve IllegalStateException: Can not perform this action after onSaveInstanceState */
                transaction.commitAllowingStateLoss();

                // Force the transaction to happen immediately and not be scheduled for later
                getSupportFragmentManager().executePendingTransactions();
                

            } catch (Exception e) {
                e.printStackTrace();
                // Do nothin'
            }
        } else {
            // Do nothin'. Same fragment
        }
    }

    /**
     * Navigate to a specific fragment
     * 
     * @param fragmentClass
     * @param bundle
     *            May be null
     * @param addToBackstack
     * @author MAB
     */
    public void navigateTo(Class<?> fragmentClass, Bundle bundle, boolean addToBackstack) {
        navigateTo(fragmentClass, bundle, addToBackstack, null);
    }

    /**
     * Navigate to a specific fragment
     * 
     * @param fragmentClass
     * @param bundle
     *            May be null
     * @author MAB
     */
    public void navigateTo(Class<?> fragmentClass, Bundle bundle) {
        navigateTo(fragmentClass, bundle, false);

    }

    /**
     * Navigate to a specific fragment
     * 
     * @param fragmentClass
     * @author MAB
     */
    public void navigateTo(Class<?> fragmentClass) {
        navigateTo(fragmentClass, null, false);

    }

    /**
     * Clears all the fragments in the backstack
     * 
     * @author MAB
     */
    //���fragments��back stack
    public void clearBackstack() {
        FragmentManager fm = getSupportFragmentManager();

        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fm.executePendingTransactions();
    }

    /**
     * Clears all the fragments in the backstack
     * 
     * @author MAB
     */
   // ���fragments��back stack
    public void clearFragFromBackstack(String tag) {

        if (tag != null) {

            FragmentManager fm = getSupportFragmentManager();

            fm.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            fm.executePendingTransactions();
        }

    }
}
