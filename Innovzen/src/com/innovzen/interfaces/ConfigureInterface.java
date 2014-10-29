
package com.innovzen.interfaces;

import android.content.Context;

/**
 * Used whenever a class needs to configure something.<br/>
 * i.e. an object that is populated using the gson library will call {@link #configure(Context)} to further populate the fields and do appropriate processing with the parsed values
 * 
 * @author MAB
 * 
 */
public interface ConfigureInterface {

    public void configure(Context ctx);

}
