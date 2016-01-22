package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.calebwhang.jokeme.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * MainActivityFragment for the free flavor of the app.
 */
public class MainActivityFragment extends Fragment implements JokeCloudTask.OnPostExecute {

    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private InterstitialAd mInterstitialAd;
    private String mDeviceID;

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
                ((MainActivity)getActivity()).displayJoke();
            }
        });

        requestNewInterstitial();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Log.v(LOG_TAG, "===== onCreateView()");
        Log.v(LOG_TAG, "FREE FREE FREE FREE FREE FREE FREE FREE FREE FREE");

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        mDeviceID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.v(LOG_TAG, "Device ID: " + mDeviceID);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(mDeviceID)
                .build();

        mAdView.loadAd(adRequest);

        Button buttonView = (Button) root.findViewById(R.id.joke_button);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "===== onClick() HERE HERE HERE FREE FREE FREE");
                Log.v(LOG_TAG, "is ad loaded: " + Boolean.toString(mInterstitialAd.isLoaded()));

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    ((MainActivity)getActivity()).displayJoke();
                }
            }
        });

        return root;
    }

    private void requestNewInterstitial() {
        Log.v(LOG_TAG, "===== requestNewInterstitial()");

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(mDeviceID)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onPostExecute(String joke) {
        Log.v(LOG_TAG, "FRAGMENT: The joke is " + joke);

        Intent intent = new Intent(getActivity(), JokeActivity.class);
        intent.putExtra(JokeActivity.INTENT_EXTRA_JOKE, joke);
        startActivity(intent);
    }

}
