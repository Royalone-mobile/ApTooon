package com.aptoon.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aptoon.utils.LocaleHelper;
import com.aptoon.R;

public class Language_Screen extends AppCompatActivity  implements View.OnClickListener {

    private SwitchCompat english;
    private LinearLayout notifications;
    private SwitchCompat germany;
    private SwitchCompat arabic;    SharedPreferences.Editor editor;
    private SwitchCompat french;
    private SwitchCompat spanish;
    private SwitchCompat russian;

    SharedPreferences preferences;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language__screen);
        initView();
        editor = getSharedPreferences("my_Pref", MODE_PRIVATE).edit();
        preferences = (SharedPreferences) getSharedPreferences("my_Pref", MODE_PRIVATE);
        int status = preferences.getInt("status", 1);
        if (status == 1) {
            setSwitchchecked();
            english.setChecked(true);
        } else if (status == 2) {
            germany.setChecked(true);
        } else if (status == 3) {
            setSwitchchecked();
            arabic.setChecked(true);
        }
        else if (status == 4) {
            setSwitchchecked();
            french.setChecked(true);
        }
        else if (status == 5) {
            setSwitchchecked();
            spanish.setChecked(true);
        }
        else if (status == 6) {
            setSwitchchecked();
            russian.setChecked(true);
        }

    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
    private void initView() {
        back=findViewById(R.id.back);
        english = findViewById(R.id.english);
        notifications = findViewById(R.id.notifications);
        germany = findViewById(R.id.germany);
        arabic = findViewById(R.id.arabic);
        french = findViewById(R.id.french);
        spanish = findViewById(R.id.spanish);
        russian = findViewById(R.id.russian);
        english.setOnClickListener(this);
        germany.setOnClickListener(this);
        arabic.setOnClickListener(this);
        french.setOnClickListener(this);
        spanish.setOnClickListener(this);
        russian.setOnClickListener(this);
        back.setOnClickListener(this);

    }
//    private void restartSelf() {
//        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        am.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 1000, // one second
//                PendingIntent.getActivity(this, 0, getIntent(), PendingIntent.FLAG_ONE_SHOT
//                        | PendingIntent.FLAG_CANCEL_CURRENT));
//        finish();
//    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.english:
                String mLanguageCode = "en";
                editor.putInt("status", 1);
                editor.apply();
                setSwitchchecked();
                english.setChecked(true);
                LocaleHelper.setLocale(Language_Screen.this, mLanguageCode);
                //It is required to recreate the activity to reflect the change in UI.
                recreate();
                break;
            case  R.id.germany:
                String mLanguageCode1 = "de";
                editor.putInt("status", 2);
                editor.apply();
                setSwitchchecked();
                germany.setChecked(true);
                LocaleHelper.setLocale(Language_Screen.this, mLanguageCode1);
                //It is required to recreate the activity to reflect the change in UI.
                recreate();
                break;
            case  R.id.arabic:
                String mLanguageCode2 = "ar";

                editor.putInt("status", 3);
                editor.apply();
                setSwitchchecked();
                arabic.setChecked(true);
                LocaleHelper.setLocale(Language_Screen.this, mLanguageCode2);

                //It is required to recreate the activity to reflect the change in UI.
                recreate();
                break;
            case  R.id.french:
                String mLanguageCode3 = "fr";

                editor.putInt("status", 4);
                editor.apply();
                setSwitchchecked();
                french.setChecked(true);
                LocaleHelper.setLocale(Language_Screen.this, mLanguageCode3);

                //It is required to recreate the activity to reflect the change in UI.
                recreate();
                break;
            case  R.id.spanish:
                String mLanguageCode4 = "es";
                editor.putInt("status", 5);
                editor.apply();
                setSwitchchecked();
                spanish.setChecked(true);
                LocaleHelper.setLocale(Language_Screen.this, mLanguageCode4);

                //It is required to recreate the activity to reflect the change in UI.
                recreate();
                break;
            case  R.id.russian:
                String mLanguageCode5 = "ru";
                editor.putInt("status", 6);
                editor.apply();
                setSwitchchecked();
                russian.setChecked(true);
                LocaleHelper.setLocale(Language_Screen.this, mLanguageCode5);
                //It is required to recreate the activity to reflect the change in UI.
                recreate();
                break;
            case  R.id.back:
                //When BACK BUTTON is pressed, the activity on the stack is restarted
                //Do what you want on the refresh procedure here
                setResult(Activity.RESULT_OK,getIntent());
                startActivity(new Intent(this,App_Settings.class));
                finish();
                break;
        }
    }

    public void setSwitchchecked(){
        english.setChecked(false);
        germany.setChecked(false);
        arabic.setChecked(false);
        french.setChecked(false);
        spanish.setChecked(false);
        russian.setChecked(false);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Dashboard.class));
        finish();
        super.onBackPressed();
    }
}


