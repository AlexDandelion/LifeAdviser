package com.dandelion.lifeadviser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import java.util.Locale;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class AppPreference extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String LANGUAGE_KEY = "preference_language";
    public static final String DAY_ADVICE_KEY = "preference_day_advice";
    public static boolean changeLocale;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences
            , String key) {
        if (key.equals(LANGUAGE_KEY)) {
            Locale myLocale = new Locale(sharedPreferences.getString(key,
                    Locale.getDefault().toString()));
            Locale.setDefault(myLocale);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            changeLocale = true;
            Intent refresh = new Intent(getActivity(), MainActivity.class);
            startActivity(refresh);
            getActivity().finish();
        } else if (key.equals(DAY_ADVICE_KEY)) {
            SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
            sp.edit().putBoolean(DAY_ADVICE_KEY, sharedPreferences
                    .getBoolean(DAY_ADVICE_KEY, true)).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}