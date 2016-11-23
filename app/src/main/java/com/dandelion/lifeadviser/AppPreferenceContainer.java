package com.dandelion.lifeadviser;

import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AppPreferenceContainer extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preference, container, false);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        TextView settingsTextView = (TextView) view.findViewById(R.id.settingsTextView);
        settingsTextView.setTypeface(typeface);

        return view;
    }
}
