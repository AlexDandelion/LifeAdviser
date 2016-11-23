package com.dandelion.lifeadviser;

import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFragment extends Fragment {

    private AnimationDrawable owlAnimationDrawable;
    private AnimationDrawable faceAnimationDrawable;
    private AnimationDrawable questionAnimationDrawable;
    private AnimationDrawable thoughtsAnimationDrawable;

    private Animation rotateWhiteCircleAnimation;
    private Animation rotateRedCircleAnimation;
    private Animation rotateCircleAnimation;
    private Animation makeQuestionMainButtonAnimation;
    private Animation knockButtonMainButtonAnimation;
    private Animation toGetAdviceTextViewAnimation;

    private ImageView whiteCircleImageView;
    private ImageView redCircleImageView;
    private ImageView circleImageView;
    private ImageView owlImageView;
    private ImageView faceImageView;
    private ImageView questionImageView;
    private ImageView thoughtsImageView;

    private TextView toGetAdviceTextView;

    private Button makeQuestionMainButton;
    private Button knockButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        TextView favoriteTextView = (TextView) view.findViewById(R.id.favoriteTextView);
        TextView tipOfTheDayTextView = (TextView) view.findViewById(R.id.dayAdviceHeaderTextView);

        whiteCircleImageView = (ImageView) view.findViewById(R.id.whiteCircleImageView);
        redCircleImageView = (ImageView) view.findViewById(R.id.redCircleImageView);
        circleImageView = (ImageView) view.findViewById(R.id.circleImageView);
        owlImageView = (ImageView) view.findViewById(R.id.owlImageView);
        faceImageView = (ImageView) view.findViewById(R.id.faceImageView);
        questionImageView = (ImageView) view.findViewById(R.id.questionImageView);
        thoughtsImageView = (ImageView) view.findViewById(R.id.thoughtsImageView);

        toGetAdviceTextView = (TextView) view.findViewById(R.id.toGetAdviceTextView);

        makeQuestionMainButton = (Button) view.findViewById(R.id.makeQuestionMainButton);
        knockButton = (Button) view.findViewById(R.id.knockButton);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        favoriteTextView.setTypeface(typeface);
        tipOfTheDayTextView.setTypeface(typeface);
        makeQuestionMainButton.setTypeface(typeface);
        knockButton.setTypeface(typeface);
        toGetAdviceTextView.setTypeface(typeface);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        runOwlAnimation();
        runFaceAnimation();
        runQuestionAnimation();
        runThoughtsAnimation();
        runRotationAnimation();
        runDisplacementAnimation();
    }

    private void runOwlAnimation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                owlImageView.setBackgroundResource(
                        R.drawable.animation_owl);
                owlAnimationDrawable = (AnimationDrawable)
                        owlImageView.getBackground();
                owlAnimationDrawable.stop();
                owlAnimationDrawable.start();
            }
        }).run();
    }

    private void runFaceAnimation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                faceImageView.setBackgroundResource(
                        R.drawable.animation_face);
                faceAnimationDrawable = (AnimationDrawable)
                        faceImageView.getBackground();
                faceAnimationDrawable.stop();
                faceAnimationDrawable.start();
            }
        }).run();
    }

    private void runQuestionAnimation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                questionImageView.setBackgroundResource(
                        R.drawable.animation_question);
                questionAnimationDrawable = (AnimationDrawable)
                        questionImageView.getBackground();
                questionAnimationDrawable.stop();
                questionAnimationDrawable.start();
            }
        }).run();
    }

    private void runThoughtsAnimation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                thoughtsImageView.setBackgroundResource(
                        R.drawable.animation_thoughts);
                thoughtsAnimationDrawable = (AnimationDrawable)
                        thoughtsImageView.getBackground();
                thoughtsAnimationDrawable.stop();
                thoughtsAnimationDrawable.start();
            }
        }).run();
    }

    private void runRotationAnimation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                rotateWhiteCircleAnimation =
                        AnimationUtils.loadAnimation(getActivity(),
                                R.anim.rotate_white_circle);
                rotateRedCircleAnimation =
                        AnimationUtils.loadAnimation(getActivity(),
                                R.anim.rotate_red_circle);
                rotateCircleAnimation =
                        AnimationUtils.loadAnimation(getActivity(),
                                R.anim.rotate_circle);
                whiteCircleImageView.startAnimation(rotateWhiteCircleAnimation);
                redCircleImageView.startAnimation(rotateRedCircleAnimation);
                circleImageView.startAnimation(rotateCircleAnimation);
                whiteCircleImageView.setVisibility(View.INVISIBLE);
                redCircleImageView.setVisibility(View.INVISIBLE);
            }
        }).run();
    }

    private void runDisplacementAnimation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                makeQuestionMainButtonAnimation =
                        AnimationUtils.loadAnimation(getActivity(),
                                R.anim.question_button);
                knockButtonMainButtonAnimation =
                        AnimationUtils.loadAnimation(getActivity(),
                                R.anim.knock_button);
                toGetAdviceTextViewAnimation =
                        AnimationUtils.loadAnimation(getActivity(),
                                R.anim.get_advice_text_view);
                makeQuestionMainButton.startAnimation(makeQuestionMainButtonAnimation);
                knockButton.startAnimation(knockButtonMainButtonAnimation);
                toGetAdviceTextView.startAnimation(toGetAdviceTextViewAnimation);
            }
        }).run();
    }
}
