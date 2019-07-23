package com.aptoon.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aptoon.utils.LocaleHelper;
import com.aptoon.R;

public class App_Settings extends AppCompatActivity implements View.OnClickListener {

    LinearLayout cellular;
    SwitchCompat notification_switch;
    LinearLayout notifications;
    SwitchCompat downloads_switch;
    LinearLayout language;
    LinearLayout download_option;
    ImageView next;
    ImageView back;
    LinearLayout privacy_policy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app__settings);
        initView();
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
    private void initView() {
        cellular = findViewById(R.id.cellular);
        notification_switch = findViewById(R.id.notification_switch);
        notifications = findViewById(R.id.notifications);
        downloads_switch = findViewById(R.id.downloads_switch);
        language = findViewById(R.id.language);
        download_option = findViewById(R.id.download_option);
        next = findViewById(R.id.next);
        back=findViewById(R.id.back);
        privacy_policy = findViewById(R.id.privacy_policy);
        language.setOnClickListener(this);
        back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cellular:
                break;
            case R.id.notification_switch:
                break;
            case R.id.notifications:
                break;
            case R.id.downloads_switch:
                break;
            case R.id.language:
                Intent language=new Intent(getApplication(),Language_Screen.class);
                startActivity(language);
               // startActivityForResult(language,1);
                finish();
                //TastyToast.makeText(getApplication(),"language selected",TastyToast.LENGTH_SHORT,TastyToast.WARNING);
                break;
            case R.id.download_option:
                break;
            case R.id.next:
                break;
            case R.id.privacy_policy:
                break;
            case R.id.back:
                startActivity(new Intent(this, Dashboard.class));
                finish();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1){
            finish();
            startActivity(getIntent());
        }
    }
}
