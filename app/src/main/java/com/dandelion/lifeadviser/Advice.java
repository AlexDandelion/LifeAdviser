package com.dandelion.lifeadviser;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Advice extends Fragment implements View.OnClickListener {

    private TextView adviceText, adviceAuthor;
    private ImageView adviceStarImage;
    private List<String> adviceContainer;
    private boolean savedInDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advice, container, false);

        TextView adviceHeaderTextView = (TextView) view.findViewById(R.id.adviceHeaderTextView);
        adviceText = (TextView) view.findViewById(R.id.adviceText);
        adviceAuthor = (TextView) view.findViewById(R.id.adviceAuthor);
        adviceStarImage = (ImageView) view.findViewById(R.id.adviceStarImage);
        adviceStarImage.setOnClickListener(this);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        adviceHeaderTextView.setTypeface(typeface);
        adviceText.setTypeface(typeface);
        adviceAuthor.setTypeface(typeface);

        parsXml();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adviceStarImage:
                if (!savedInDatabase) {
                    adviceStarImage.setImageResource(R.drawable.star_yellow);
                    MainActivity.database.create(adviceContainer.get(1)
                            , adviceContainer.get(0));
                    savedInDatabase = true;
                }
                break;
            default:
                break;
        }
    }

    private void parsXml() {
        XmlPullParser xmlPullParser = getResources()
                .getXml(R.xml.question_advices);

        int randomAdvice = (int) (Math.random() * 172 + 1);
        int rightAdvise = 0;
        boolean adviceFound = false;
        boolean localeFound = false;
        adviceContainer = new ArrayList<>();

        String locale = Locale.getDefault().toString();
        if (("uk".equals(locale)) || ("uk_UA".equals(locale))) {
            locale = "uk";
        } else if (("ru".equals(locale)) || ("ru_RU".equals(locale))) {
            locale = "ru";
        } else {
            locale = "en";
        }

        try {
            while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT
                    && !adviceFound) {
                String name = xmlPullParser.getName();
                String text = xmlPullParser.getText();
                switch (xmlPullParser.getEventType()) {
                    case XmlPullParser.START_TAG:
                        if (localeFound && "dict".equals(name)) {
                            rightAdvise++;
                        } else if (locale.equals(name)) {
                            localeFound = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if (rightAdvise == randomAdvice && !("avtor".equals(text))
                                && !("advise".equals(text))) {
                            adviceContainer.add(text);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (rightAdvise == randomAdvice && "dict".equals(name)) {
                            adviceFound = true;
                        }
                        break;
                    default:
                        break;
                }
                xmlPullParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        adviceAuthor.setText(adviceContainer.get(0));
        adviceText.setText(adviceContainer.get(1));
    }
}
