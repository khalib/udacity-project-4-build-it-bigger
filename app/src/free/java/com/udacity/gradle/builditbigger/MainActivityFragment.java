package com.udacity.gradle.builditbigger;

import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * MainActivityFragment for the free flavor of the app.
 */
public class MainActivityFragment extends Fragment {

    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private InterstitialAd mInterstitialAd;
    private String mDeviceID;
    private ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                ((MainActivity) getActivity()).displayJoke();
            }
        });

        requestNewInterstitial();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        mDeviceID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(mDeviceID)
                .build();

        mAdView.loadAd(adRequest);

        Button buttonView = (Button) root.findViewById(R.id.joke_button);
        mProgressBar = (ProgressBar) root.findViewById(R.id.load_progress_bar);

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    // Show loader.
                    mProgressBar.setVisibility(View.VISIBLE);

                    mInterstitialAd.show();
                } else {
                    // Check if there's connection.
                    if (!Utilities.isNetworkAvailable(getActivity())) {
                        // Alert the user that there's no connection.
                        Toast.makeText(getActivity(), R.string.network_connection_error, Toast.LENGTH_SHORT).show();
                    } else {
                        // Show loader.
                        mProgressBar.setVisibility(View.VISIBLE);
                        ((MainActivity) getActivity()).displayJoke();
                    }
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

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(mDeviceID)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

}
