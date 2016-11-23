package com.dandelion.lifeadviser;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity
        implements View.OnTouchListener, SensorEventListener {

    private static final int SHAKE_THRESHOLD = 1500;

    public static int adviceAstralSeconds = 0;
    public static boolean adviceAvailable;

    private float lastAxisX;
    private float lastAxisY;
    private float lastAxisZ;
    private float onTouchDownX;
    private float onTouchDownY;
    private long lastTimeUpdate;
    private long lastClickTime;

    public static DatabaseHelper database;
    private OwlSound sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = DatabaseHelper.getInstance(this);
        sound = new OwlSound(this);

        switchFragments(new MainFragment());
        adviceAvailable = true;

        if (AppPreference.changeLocale) {
            switchFragments(new AppPreferenceContainer());
        }

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        FrameLayout mainFrameLayout = (FrameLayout) findViewById(R.id.mainFrameLayout);
        mainFrameLayout.setOnTouchListener(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font.ttf");
        TextView logoTextView = (TextView) findViewById(R.id.logoTextView);
        logoTextView.setTypeface(typeface);
    }

    private void adviceLoading() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    adviceAvailable = false;
                    adviceAstralSeconds = (int) (Math.random() * 25 + 5);
                    while (adviceAstralSeconds > 0) {
                        Thread.sleep((1000));
                        adviceAstralSeconds--;
                    }
                    adviceAvailable = true;
                    sound.playSound();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void switchFragments(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrameLayout, fragment).commit();
    }

    public void onClickFragments(View view) {
        switch (view.getId()) {
            case R.id.infoImageView:
                switchFragments(new InfoScreen());
                break;
            case R.id.quickTipImageView:
                switchFragments(new QuickTip());
                break;
            case R.id.startButton:
                if (adviceAvailable) {
                    switchFragments(new MainFragment());
                } else {
                    switchFragments(new PrepareAdvice());
                }
                break;
            case R.id.settingsImageView:
                switchFragments(new AppPreferenceContainer());
                break;
            case R.id.favoriteTextView:
                switchFragments(new FavoriteListContainer());
                break;
            case R.id.dayAdviceHeaderTextView:
                SharedPreferences sp = getPreferences(MODE_PRIVATE);
                boolean dayAdviceEnable = sp.getBoolean(AppPreference
                        .DAY_ADVICE_KEY, true);
                if (dayAdviceEnable) {
                    switchFragments(new DayAdvice());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        long millisDoubleClickTimeDelta = 300;
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.mainFrameLayout);

        if (currentFragment instanceof MainFragment) {
            float onTouchX = event.getX(), onTouchY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    long clickTime = System.currentTimeMillis();
                    if (clickTime - lastClickTime < millisDoubleClickTimeDelta) {
                        switchFragments(new Advice());
                        adviceLoading();
                    }
                    lastClickTime = clickTime;
                    onTouchDownX = onTouchX;
                    onTouchDownY = onTouchY;
                    return true;
                case MotionEvent.ACTION_UP:
                    if (onTouchX > (onTouchDownX + 100f) ||
                            onTouchX < (onTouchDownX - 100f) ||
                            onTouchY > (onTouchDownY + 100f) ||
                            onTouchY < (onTouchDownY - 100f)) {
                        switchFragments(new Advice());
                        adviceLoading();
                    }
                    return true;
                default:
                    return true;
            }
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.mainFrameLayout);
        if (currentFragment instanceof MainFragment && event.sensor.getType() ==
                Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();

            if ((curTime - lastTimeUpdate) > 100) {
                long diffTime = (curTime - lastTimeUpdate);
                lastTimeUpdate = curTime;

                float axisX = event.values[0],
                        axisY = event.values[1],
                        axisZ = event.values[2];

                float speed = Math.abs(axisX + axisY + axisZ - lastAxisX -
                        lastAxisY - lastAxisZ) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    switchFragments(new Advice());
                    adviceLoading();
                }
                lastAxisX = axisX;
                lastAxisY = axisY;
                lastAxisZ = axisZ;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
