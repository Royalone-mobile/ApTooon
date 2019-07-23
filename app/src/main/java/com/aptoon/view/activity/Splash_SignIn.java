package com.aptoon.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aptoon.utils.LocaleHelper;
import com.aptoon.R;
import com.aptoon.utils.*;

public class Splash_SignIn extends AppCompatActivity implements View.OnClickListener   {
    private Button signin;
    LinearLayout internet_conectivity;
    private TextView try_again,text1111;
    String status;
    public static final String NOT_CONNECT = "NOT_CONNECT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__sign_in);
        initView();
        status=NetworkUtil.getConnectivityStatusString(getApplication());
        if (NetworkUtil.getConnectivityStatus(Splash_SignIn.this) > 0 ) {
            internet_conectivity.setVisibility(View.GONE);
        }
        else {
            internet_conectivity.setVisibility(View.VISIBLE);
        }
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void initView() {
        internet_conectivity = findViewById(R.id.internet_conectivity);
        signin = findViewById(R.id.signin);
        text1111 = findViewById(R.id.text1111);
        try_again = findViewById(R.id.try_again);
        signin.setOnClickListener(this);
    }
//    public  void Connection(String log){
//        if (log.equals(NOT_CONNECT)) {
//            internet_conectivity.setVisibility(View.VISIBLE);
//            TastyToast.makeText(getApplication(), status, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
//        }
//        else {
//            internet_conectivity.setVisibility(View.GONE);
//            TastyToast.makeText(getApplication(), status, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
//
        //}
    //}
    @Override
    protected void onPause() {

        super.onPause();
        if (NetworkUtil.getConnectivityStatus(Splash_SignIn.this) > 0 ) {
            internet_conectivity.setVisibility(View.GONE);
        }
        else {
            internet_conectivity.setVisibility(View.VISIBLE);
        }// On Pause notify the Application
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (NetworkUtil.getConnectivityStatus(Splash_SignIn.this) > 0 ) {
            internet_conectivity.setVisibility(View.GONE);
        }
        else {
            internet_conectivity.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                Intent i = new Intent(Splash_SignIn.this, SignIn.class);
                startActivity(i);
                break;
        }
    }

}
