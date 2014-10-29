
package com.innovzen.interfaces;

import android.support.v4.app.Fragment;

public interface FragChairChildrenInterface {

    void setCurItem(int x);
    
    void addExtraInfoFrag(Fragment frag);
    
    void restoreAfterExtraInfoFrag();
    
    int getNumberOfPassesForBreatheSecond();
    
    void decrementNumberOfPassesForBeatheSecond();
    
    int getNumberOfPassesForExtraInfo();
    
    void decrementNumberOfPassesForExtraInfo();
    
    void addBreatheSecondFrag();
    
    void removeBreatheSecondFrag();
    
    Boolean isBreatheSecondActive();
}