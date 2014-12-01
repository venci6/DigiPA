package com.example.liz.digipa;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by Charlene on 12/1/2014.
 */
public class RegisterTest extends ActivityInstrumentationTestCase2<Register> {

    private Solo solo;

    public RegisterTest() {
        super(Register.class);
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

    public void inputTextEnableRegistration() throws Exception {
        // check that we have the right activity
        solo.assertCurrentActivity("wrong activity", Register.class);

        // Cancel
        solo.clickOnButton(0);
        solo.assertCurrentActivity("wrong activity", Login.class);

        // go back to register
        solo.clickOnButton(1);
        solo.assertCurrentActivity("wrong activity", Register.class);

        solo.enterText(0, "myUsesrname");
        solo.enterText(1, "myEmail@gmail.com");
        solo.enterText(2, "myFirstName");
        solo.enterText(3, "myLastName");
        solo.enterText(4, "myPassword");
        solo.enterText(5, "myPassword");

        // click on Register
        solo.clickOnButton(1);

        // should go to month
        solo.assertCurrentActivity("wrong activity", month.class);
    }
}
