package com.example.liz.digipa;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by Liz on 12/1/2014.
 */
public class DailyTest extends ActivityInstrumentationTestCase2<Daily> {
    private Solo solo;

    public DailyTest() {
        super(Daily.class);
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
        solo.assertCurrentActivity("wrong activity", Daily.class);
    }
}
