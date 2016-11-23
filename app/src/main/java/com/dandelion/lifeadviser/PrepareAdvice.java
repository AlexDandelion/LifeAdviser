package com.dandelion.lifeadviser;

import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PrepareAdvice extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_advice_not_readyt,
                container, false);

        ImageView adviceLoadingImage = (ImageView)
                view.findViewById(R.id.adviceLoadingImage);
        adviceLoadingImage.setBackgroundResource(R.drawable.animation_reloaded_tip);
        AnimationDrawable animationDrawable = (AnimationDrawable)
                adviceLoadingImage.getBackground();
        animationDrawable.start();

        Typeface typeface = Typeface.createFromAsset(getActivity()
                .getAssets(), "font.ttf");
        TextView tipAvailableTextView = (TextView)
                view.findViewById(R.id.tipAvailableTextView);
        TextView favoriteTextView = (TextView)
                view.findViewById(R.id.favoriteTextView);
        TextView tipOfTheDayTextView = (TextView)
                view.findViewById(R.id.dayAdviceHeaderTextView);
        tipAvailableTextView.setTypeface(typeface);
        favoriteTextView.setTypeface(typeface);
        tipOfTheDayTextView.setTypeface(typeface);

        adviceInAstral();

        return view;
    }

    private void adviceInAstral() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((MainActivity.adviceAstralSeconds * 1000));
                    if (null != getActivity()) {
                        Fragment currentFragment = getActivity().getSupportFragmentManager()
                                .findFragmentById(R.id.mainFrameLayout);
                        if (currentFragment instanceof PrepareAdvice) {
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.mainFrameLayout, new MainFragment()).commit();
                            MainActivity.adviceAvailable = true;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
