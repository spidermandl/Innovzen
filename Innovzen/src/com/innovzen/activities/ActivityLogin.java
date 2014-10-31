package com.innovzen.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovzen.o2chair.R;
import com.innovzen.utils.PersistentUtil;
import com.innovzen.utils.Util;

/**
 * Class (activity) used only in case of the "O2Chair" app to display a login screen to the user}
 * 
 * @author MAB
 */
public class ActivityLogin extends ActivityBase implements OnClickListener, OnTouchListener {

    /*
     * -----------------------------------------------------
     */
    /** The password the user needs to enter to be able to continue with the app. */
    private static final String PASS = "innovzen";
    /*
     * -----------------------------------------------------
     *///<>
     //</>

    /** The width of the input field calculated as percentage from the entire width of the screen. Value used for TABLETS */
    private static final float WIDTH_INPUT_FRAC_TABLET = 0.5f;

    /** The width of the input field calculated as percentage from the entire width of the screen. Value used for PHONES */
    private static final float WIDTH_INPUT_FRAC_PHONE = 0.8f;

    /** Used to store a flag in shared preferences to indicate if the user already entered the password correctly */
    public static final String SHARED_PREF_IS_LOGGED_IN = "already_logged_in";

    // View references
    private RelativeLayout input_container;
    private EditText input_pass;
    private TextView confirm_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.activity_login_confirm_btn) {

            enableInputNormalState();

            onConfirmPassClicked();

        } else if (id == R.id.activity_login_password_input) {

            enableInputNormalState();

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v.getId() == R.id.activity_login_password_input) {
            enableInputNormalState();
        }

        return false;
    }

    /**
     * Does proper initializations
     * 
     * @author MAB
     */
    private void init() {

        // Get view references
        input_container = (RelativeLayout) findViewById(R.id.activity_login_password_input_container);
        confirm_btn = (TextView) findViewById(R.id.activity_login_confirm_btn);
        input_pass = (EditText) findViewById(R.id.activity_login_password_input);

        // Set event listeners
        confirm_btn.setOnClickListener(this);
        input_pass.setOnClickListener(this);

        // Set the width of the input field
        int width = 0;
        if (ActivityBase.IS_TABLET) {
            width = (int) (WIDTH_INPUT_FRAC_TABLET * Util.getScreenDimensions(this)[0]);
        } else {
            width = (int) (WIDTH_INPUT_FRAC_PHONE * Util.getScreenDimensions(this)[0]);
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) input_container.getLayoutParams();
        params.width = width;
        input_container.setLayoutParams(params);

        input_pass.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enableInputNormalState();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Enable the normal state of the input field by default
        enableInputNormalState();

    }

    /**
     * When the user clicks on the confirm button, check to see if the password is the same as the predefined one
     * 
     * @author MAB
     */
    private void onConfirmPassClicked() {

        if (input_pass != null) {
            // If the password is correct
          /*  if (input_pass.getText().toString().equals(ActivityLogin.PASS)) {*/
        	//<chy>
        	if (input_pass.getText().toString().equals("123")) {
        	//<chy/>

                // Store a flag to indicate we've successfuly entered the credentials
                PersistentUtil.setBoolean(this, true, ActivityLogin.SHARED_PREF_IS_LOGGED_IN);

                // Jump to the main menu activity
                Intent intent = new Intent(this, ActivityMain.class);
                startActivity(intent);
                finish();

            } else { // Otherwise display a warning/error

                enableInputErrorState();

            }
        }

    }

    /**
     * Set the normal mode for the input field
     * 
     * @author MAB
     */
    private void enableInputNormalState() {

        if (input_pass != null) {
            input_pass.setBackgroundResource(R.drawable.shape_activity_login_input);
        }

    }

    /**
     * Set the error mode for the input field. Call this when the user gets the password wrong
     * 
     * @author MAB
     */
    private void enableInputErrorState() {

        if (input_pass != null) {
            input_pass.setBackgroundResource(R.drawable.shape_activity_login_input_error);
        }

    }
}
