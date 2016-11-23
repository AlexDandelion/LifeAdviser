package com.dandelion.lifeadviser;

import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class QuickTip extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_tip, container, false);

        final ImageView quickTipImageViewAnswer = (ImageView)
                view.findViewById(R.id.quickTipImageViewAnswer);
        final Button makeQuestionButton = (Button)
                view.findViewById(R.id.makeQuestionButton);
        makeQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        makeQuestionButton.setVisibility(View.GONE);
                        makeQuestionButton.setClickable(false);
                        if (new Random().nextBoolean()) {
                            quickTipImageViewAnswer.setBackgroundResource(
                                    R.drawable.animation_quick_tip_yes);
                        } else {
                            quickTipImageViewAnswer.setBackgroundResource(
                                    R.drawable.animation_quick_tip_no);
                        }

                        AnimationDrawable animationDrawable = (AnimationDrawable)
                                quickTipImageViewAnswer.getBackground();
                        animationDrawable.start();
                    }
                }).run();
            }
        });

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        TextView quickTipTextView = (TextView) view.findViewById(R.id.quickTipTextView);
        quickTipTextView.setTypeface(typeface);
        makeQuestionButton.setTypeface(typeface);
        return view;
    }
}
