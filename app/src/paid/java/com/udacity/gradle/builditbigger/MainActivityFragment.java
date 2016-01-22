package com.udacity.gradle.builditbigger;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * MainActivityFragment for the paid flavor of the app.
 */
public class MainActivityFragment extends Fragment {

    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Log.v(LOG_TAG, "===== onCreateView()");
        Log.v(LOG_TAG, "PAID PAID PAID PAID PAID PAID PAID PAID PAID PAID PAID ");

        Button buttonView = (Button) root.findViewById(R.id.joke_button);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "===== onClick() HERE HERE HERE PAID PAID PAID");
                ((MainActivity) getActivity()).displayJoke();
            }
        });

        return root;
    }

}
