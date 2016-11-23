package com.dandelion.lifeadviser;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayAdvice extends Fragment implements View.OnClickListener {

    private static final String DATE_KEY = "Current date";
    private static final String ADVICE_KEY = "Current advice";

    private TextView dayAdvice;
    private TextView dayAdviceAuthor;
    private ImageView dayAdviceStarImage;
    private List<String> dayAdviceContainer;

    private boolean savedInDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_advice_, container, false);

        TextView dayAdviceHeaderTextView = (TextView)
                view.findViewById(R.id.dayAdviceHeaderTextView);
        dayAdvice = (TextView) view.findViewById(R.id.dayAdvice);
        dayAdviceAuthor = (TextView) view.findViewById(R.id.dayAdviceAuthor);
        dayAdviceStarImage = (ImageView) view.findViewById(R.id.dayAdviceStarImage);
        dayAdviceStarImage.setOnClickListener(this);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        dayAdviceHeaderTextView.setTypeface(typeface);
        dayAdvice.setTypeface(typeface);
        dayAdviceAuthor.setTypeface(typeface);

        parsXml();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dayAdviceStarImage:
                if (!savedInDatabase) {
                    dayAdviceStarImage.setImageResource(R.drawable.star_yellow);
                    MainActivity.database.create(dayAdviceContainer.get(1)
                            , dayAdviceContainer.get(0));
                    savedInDatabase = true;
                }
                break;
            default:
                break;
        }
    }

    private void parsXml() {
        XmlPullParser xmlPullParser = getResources().getXml(R.xml.day_advices);

        int randomDayAdvice = prepareDayAdvice();
        int rightDayAdvise = 0;
        boolean dayAdviceFound = false;
        boolean correctLocale = false;
        dayAdviceContainer = new ArrayList<>();

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
                    && !dayAdviceFound) {
                String name = xmlPullParser.getName();
                String text = xmlPullParser.getText();
                switch (xmlPullParser.getEventType()) {
                    case XmlPullParser.START_TAG:
                        if (correctLocale && "dict".equals(name)) {
                            rightDayAdvise++;
                        } else if (locale.equals(name)) {
                            correctLocale = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if (rightDayAdvise == randomDayAdvice && !("avtor".equals(text))
                                && !("advise".equals(text))) {
                            dayAdviceContainer.add(text);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (rightDayAdvise == randomDayAdvice && "dict".equals(name)) {
                            dayAdviceFound = true;
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
        dayAdviceAuthor.setText(dayAdviceContainer.get(0));
        dayAdvice.setText(dayAdviceContainer.get(1));
    }

    private int prepareDayAdvice() {
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        int preferenceDate = sp.getInt(DATE_KEY, 10);
        int currentDate = Integer.parseInt(String.valueOf(new SimpleDateFormat("yyyy.MM.dd")
                .format(new Date()).replace(".", "")));
        int randomDayAdvice;
        if (preferenceDate < currentDate) {
            randomDayAdvice = (int) (Math.random() * 212 + 1);
            sp.edit().putInt(DATE_KEY, currentDate).commit();
            sp.edit().putInt(ADVICE_KEY, randomDayAdvice).commit();
            return randomDayAdvice;
        }
        randomDayAdvice = sp.getInt(ADVICE_KEY, 10);
        return randomDayAdvice;
    }
}