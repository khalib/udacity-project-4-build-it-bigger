package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import com.calebwhang.Joke;

/**
 * Created by caleb on 1/14/16.
 */
public class JokeCloudTask extends AsyncTask<String, Void, String> {

    private final String LOG_TAG = JokeCloudTask.class.getSimpleName();

    private OnPostExecute mOnPostExecute;

    /**
     * Interface definition for a callback to be invoked when the task is completed.
     */
    public interface OnPostExecute {
        void onPostExecute(String joke);
    }

    /**
     * Register a callback to be invoked when the task is completed.
     *
     * @param listener the callback that will be run.
     */
    public void setOnPostExecute(OnPostExecute listener) {
        mOnPostExecute = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.v(LOG_TAG, "===== doInBackground()");

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (null != mOnPostExecute) {
            mOnPostExecute.onPostExecute("THIS IS THE JOKE FROM THE CLOUD!!!");
        }
    }
}
