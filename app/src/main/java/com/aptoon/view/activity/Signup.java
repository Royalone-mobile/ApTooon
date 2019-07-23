package com.aptoon.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aptoon.utils.LocaleHelper;
import com.aptoon.controllers.Apis;
import com.aptoon.view.design.KProgressHUD;
import com.aptoon.entity.User_Signup;
import com.aptoon.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    private ImageView imBack;
    private EditText input_email;
    private TextInputLayout input_layout_email;
    private EditText input_fullname;
    private TextInputLayout input_layout_fullname;
    private EditText input_password;
    private TextInputLayout input_layout_password;
    private EditText input_cpassword;
    private TextInputLayout input_layout_cpassword;
    private TextView tvLoginNowText;
    private Button Signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void initView() {
        imBack = findViewById(R.id.imBack);
        input_email = findViewById(R.id.input_email);
        input_layout_email = findViewById(R.id.input_layout_email);
        input_fullname = findViewById(R.id.input_fullname);
        input_layout_fullname = findViewById(R.id.input_layout_fullname);
        input_password = findViewById(R.id.input_password);
        input_layout_password = findViewById(R.id.input_layout_password);
        input_cpassword = findViewById(R.id.input_cpassword);
        input_layout_cpassword = findViewById(R.id.input_layout_cpassword);
        tvLoginNowText = findViewById(R.id.tvLoginNowText);
        Signup_button = findViewById(R.id.Signup_button);
        imBack.setOnClickListener(this);
        tvLoginNowText.setOnClickListener(this);
        Signup_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imBack:
                finish();
                break;
            case R.id.Signup_button:
                submit();
                break;
            case R.id.tvLoginNowText:
                finish();
                break;
        }

    }

    private void submit() {
        // validate
        String email = input_email.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            TastyToast.makeText(getApplicationContext(), "Please Enter Email and Mobile No.", TastyToast.LENGTH_LONG, TastyToast.WARNING);

            return;
        }

        String fullname = input_fullname.getText().toString().trim();
        if (TextUtils.isEmpty(fullname)) {

            TastyToast.makeText(getApplicationContext(), "Please Enter FullName", TastyToast.LENGTH_LONG, TastyToast.WARNING);

            return;
        }

        String password = input_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {

            TastyToast.makeText(getApplicationContext(), "Please Enter Password", TastyToast.LENGTH_LONG, TastyToast.WARNING);

            return;
        }

        String cpassword = input_cpassword.getText().toString().trim();
        if (TextUtils.isEmpty(cpassword)) {
            TastyToast.makeText(getApplicationContext(), "Please Enter Confirm Password", TastyToast.LENGTH_LONG, TastyToast.WARNING);

            return;
        }
        // TODO validate success, do something
        Map<String, String> map = new HashMap<>();
        //(email,type,name,password)
        map.put("email", email);
        map.put("name", fullname);
        map.put("password", password);
        map.put("type", "simple");
        User_SignUP(map);
    }

    private void User_SignUP(Map<String, String> map) {
            final KProgressHUD hud = KProgressHUD.create(Signup.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
            hud.setSize(100, 100);
            hud.show();
            Call<User_Signup> call = Apis.getAPIService().User_Signup(map);
            call.enqueue(new Callback<User_Signup>() {
                @SuppressLint("Assert")
                @Override
                public void onResponse(Call<User_Signup> call, Response<User_Signup> response) {
                    hud.dismiss();
                   User_Signup userdata=response.body();
                    if(userdata.getStatus().equals("200")){
                        TastyToast.makeText(getApplicationContext(), userdata.getMessage(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        Intent signin=new Intent(getApplication(),SignIn.class);
                        startActivity(signin);
                    }else {
                        TastyToast.makeText(getApplicationContext(), userdata.getMessage(), TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                }

                @Override
                public void onFailure(Call<User_Signup> call, Throwable t) {
                    hud.dismiss();
                    Log.d("response", "vv" + t.getMessage());
                }
            });
        }
    }

