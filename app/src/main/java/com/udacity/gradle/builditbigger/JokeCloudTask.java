package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import com.calebwhang.Joke;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.jokecloud.myApi.MyApi;
import com.udacity.gradle.builditbigger.jokecloud.myApi.model.MyBean;

import java.io.IOException;

/**
 * Created by caleb on 1/14/16.
 */
public class JokeCloudTask extends AsyncTask<String, Void, String> {

    private final String LOG_TAG = JokeCloudTask.class.getSimpleName();

    private OnPostExecute mOnPostExecute;
    private static MyApi mMyApi;

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
        // Connect to GCE.
        if (null == mMyApi) {
            MyApi.Builder builder = new MyApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:1024/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            mMyApi = builder.build();
        }

        try {
            // Get the joke.
            return mMyApi.joke().execute().getData();
        } catch (IOException e) {
            Log.v(LOG_TAG, "ERROR: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (null != mOnPostExecute) {
            mOnPostExecute.onPostExecute(s);
        }
    }
}
