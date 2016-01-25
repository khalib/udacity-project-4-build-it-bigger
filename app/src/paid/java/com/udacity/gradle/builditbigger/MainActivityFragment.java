package com.udacity.gradle.builditbigger;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.udacity.gradle.builditbigger.Utilities;

import java.lang.Boolean;
import java.lang.Override;


/**
 * MainActivityFragment for the paid flavor of the app.
 */
public class MainActivityFragment extends Fragment {

    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mProgressBar = (ProgressBar) root.findViewById(R.id.load_progress_bar);

        Button buttonView = (Button) root.findViewById(R.id.joke_button);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if there's connection.
                if (!Utilities.isNetworkAvailable(getActivity())) {
                    // Alert the user that there's no connection.
                    Toast.makeText(getActivity(), R.string.network_connection_error, Toast.LENGTH_SHORT).show();
                } else {
                    // Show progress bar and the joke.
                    mProgressBar.setVisibility(View.VISIBLE);
                    ((MainActivity) getActivity()).displayJoke();
                }
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }
}
