package com.aptoon.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aptoon.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ErrorRecoveryActivity extends AppCompatActivity {
    @Bind(R.id.txt_error) TextView txtError;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_recover);
        ButterKnife.bind(this);

        String error = getIntent().getStringExtra("error");
        txtError.setText(error);
    }

    @Override
    public void onDestroy(){
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
