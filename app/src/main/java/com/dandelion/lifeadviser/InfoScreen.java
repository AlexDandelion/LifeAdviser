package com.dandelion.lifeadviser;

import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Locale;

public class InfoScreen extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.infoRelativeLayout);
        String locale = Locale.getDefault().toString();
        if (("uk".equals(locale)) || ("uk_UA".equals(locale))) {
            layout.setBackgroundResource(R.drawable.background_info_uk);
        } else if (("ru".equals(locale)) || ("ru_RU".equals(locale))) {
            layout.setBackgroundResource(R.drawable.background_info_ru);
        } else {
            layout.setBackgroundResource(R.drawable.background_info_en);
        }

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        Button startButton = (Button) view.findViewById(R.id.startButton);
        Button howItWorksButton = (Button) view.findViewById(R.id.howItWorksButton);
        startButton.setTypeface(typeface);
        howItWorksButton.setTypeface(typeface);

        return view;
    }
}
