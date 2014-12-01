package com.example.liz.digipa;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by Charlene on 12/1/2014.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<Login> {

    private Solo solo;

    public LoginTest() {
        super(Login.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void inputTextEnableLogin() throws Exception {
        // check that we have the right activity
        solo.assertCurrentActivity("wrong activity", Login.class);

        // click on Register
        solo.clickOnButton(1);
        // should go to Register
        solo.assertCurrentActivity("wrong activity", Register.class);

        solo.goBack();
        solo.assertCurrentActivity("wrong activity", Login.class);

        // click on login; nothing should happen
        solo.clickOnButton(0);

        solo.enterText(0,"myUsername");
        // click on login; nothing should happen
        solo.clickOnButton(0);

        solo.enterText(1,"myPassword");

        // click on login
        solo.clickOnButton(0);

        // TODO check login credentials?
        // should go to month
        solo.assertCurrentActivity("wrong activity", month.class);
    }
}
