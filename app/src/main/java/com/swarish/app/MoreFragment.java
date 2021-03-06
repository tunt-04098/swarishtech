package com.swarish.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

/**
 * Created by TuNT on 9/25/2018.
 * tunt.program.04098@gmail.com
 */
public class MoreFragment extends Fragment implements BackView {

    public static MoreFragment getInstance() {
        MoreFragment fragment = new MoreFragment();
        return fragment;
    }

    SwitchCompat scNotification;

    Button btnShare;

    Button btnRateApp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        scNotification = view.findViewById(R.id.scNotification);
        btnShare = view.findViewById(R.id.btnShare);
        btnRateApp = view.findViewById(R.id.btnRateApp);

        scNotification.setChecked(((MainActivity) getActivity()).enableNotification());
        scNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean enable) {
                ((MainActivity) getActivity()).saveEnableNotification(enable);
                ((MainActivity) getActivity()).checkSubscribeFCM();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });

        btnRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateApp();
            }
        });

        return view;
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    private void share() {
        String shareBody = "https://swarish.in/";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share)));
    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
        }
    }
}