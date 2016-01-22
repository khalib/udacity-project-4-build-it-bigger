package com.udacity.gradle.builditbigger;

import android.test.InstrumentationTestCase;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Connection test for the JokeCloudTask AsyncTask.
 */
public class JokeCloudTaskTest extends InstrumentationTestCase
        implements JokeCloudTask.OnPostExecute {

    private final String LOG_TAG = JokeCloudTaskTest.class.getSimpleName();

    private JokeCloudTask mJokeCloudTask;
    private CountDownLatch mCountDownLatch;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mCountDownLatch = new CountDownLatch(1);
        mJokeCloudTask = new JokeCloudTask();
        mJokeCloudTask.setOnPostExecute(JokeCloudTaskTest.this);
    }

    public void testVerifyJokeResponse() throws Throwable {
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                mJokeCloudTask.execute();

                try {
                    mCountDownLatch.await(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPostExecute(String joke) {
        assertFalse(joke.isEmpty());

        mCountDownLatch.countDown();
    }
}
