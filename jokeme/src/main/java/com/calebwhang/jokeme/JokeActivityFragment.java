package com.calebwhang.jokeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JokeActivityFragment extends Fragment {

    private final String LOG_TAG = JokeActivityFragment.class.getSimpleName();

    private String mJoke;

    public JokeActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_joke_activity, container, false);

        if (savedInstanceState != null) {
            mJoke = savedInstanceState.getString(JokeActivity.INTENT_EXTRA_JOKE);
        } else {
            // Get the joke that is passed in from the intent.
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(JokeActivity.INTENT_EXTRA_JOKE)) {
                mJoke = intent.getStringExtra(JokeActivity.INTENT_EXTRA_JOKE);
            }
        }

        // Load the view.
        TextView jokeView = (TextView) rootView.findViewById(R.id.joke);
        jokeView.setText(mJoke);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Store states.
        outState.putString(JokeActivity.INTENT_EXTRA_JOKE, mJoke);

        super.onSaveInstanceState(outState);
    }

}
