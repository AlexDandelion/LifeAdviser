package com.dandelion.lifeadviser;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FavoriteAdvice extends Fragment implements View.OnClickListener {

    private ImageView adviceStarImage;
    private boolean available = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_advice, container, false);

        TextView adviceHeaderTextView = (TextView) view.findViewById(R.id.adviceHeaderTextView);
        TextView adviceText = (TextView) view.findViewById(R.id.adviceText);
        TextView adviceAuthor = (TextView) view.findViewById(R.id.adviceAuthor);

        adviceStarImage = (ImageView) view.findViewById(R.id.adviceStarImage);
        adviceStarImage.setOnClickListener(this);

        List<String> adviceContainer = MainActivity.database.get(MainActivity.database.getLine());
        adviceText.setText(adviceContainer.get(0));
        adviceAuthor.setText(adviceContainer.get(1));

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        adviceHeaderTextView.setTypeface(typeface);
        adviceText.setTypeface(typeface);
        adviceAuthor.setTypeface(typeface);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.adviceStarImage && available) {
            adviceStarImage.setImageResource(R.drawable.star);
            MainActivity.database.delete(MainActivity.database.getLine());
            available = false;
        }
    }
}
