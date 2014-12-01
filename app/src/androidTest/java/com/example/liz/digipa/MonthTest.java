package com.example.liz.digipa;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by Charlene on 12/1/2014.
 */
public class MonthTest extends ActivityInstrumentationTestCase2<month> {

    private Solo solo;

    public MonthTest() {
        super(month.class);
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
        solo.assertCurrentActivity("wrong activity", month.class);

        // Cancel
        solo.clickOnButton("PREV");

        solo.clickOnButton("NEXT");

        solo.clickOnButton("December 2014");

        solo.clickOnButton("Add Event");
        solo.assertCurrentActivity("wrong activity", CreateEvent.class);
        solo.goBack();

        solo.clickOnButton("Add Task");
        solo.assertCurrentActivity("wrong activity", CreateTask.class);
        solo.goBack();
    }
}
