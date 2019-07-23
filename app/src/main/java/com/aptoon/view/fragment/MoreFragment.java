package com.aptoon.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aptoon.view.activity.Account;
import com.aptoon.view.activity.App_Settings;
import com.aptoon.view.activity.Downloads;
import com.aptoon.view.activity.Help;
import com.aptoon.view.activity.My_Coins;
import com.aptoon.view.activity.My_List;
import com.aptoon.view.design.CircularImageView;
import com.aptoon.R;
import com.aptoon.utils.UserSessionManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;

import static com.aptoon.controllers.Apis.Photo_Base_URL;
import static com.facebook.FacebookSdk.getApplicationContext;

public class MoreFragment extends Fragment implements View.OnClickListener {

    private CircularImageView user_image;
    private TextView user_name;
    private TextView coins_count;
    private LinearLayout mycoins;
    private LinearLayout mylist;
    private LinearLayout downloads;
    private LinearLayout appsetting;
    private LinearLayout account;
    private LinearLayout help;
    private LinearLayout signout;
    UserSessionManager session;
    String names;

//    private ImageView coin_image,mylist_image,download_image,appsetting_image,account_image,help_image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        session = new UserSessionManager(getApplicationContext());
        initView(view);
         HashMap<String, String> user = session.getUserDetails();
         String picture=user.get(session.KEY_PICTURE);
          names=user.get(session.KEY_NAME);
          user_name.setText(names);
        if (picture == null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.user_photo)
                    .error(R.drawable.user_photo);
            // Glide.with(this).load(Photo_Base_URL+picture).apply(options).into(user_image);
        } else {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.user_photo)
                    .error(R.drawable.user_photo);
            Glide.with(this).load(Photo_Base_URL + picture).apply(options).into(user_image);
           // ss = Photo_Base_URL + picture;
           // Log.e("picture", ss);
        }
        return view;
    }
    private void initView(View view) {
        user_image = view.findViewById(R.id.user_image);
        user_name = view.findViewById(R.id.user_name);
        coins_count = view.findViewById(R.id.coins_count);
        mycoins = view.findViewById(R.id.mycoins);
        mylist = view.findViewById(R.id.mylist);
        downloads = view.findViewById(R.id.downloads);
        appsetting = view.findViewById(R.id.appsetting);
        account = view.findViewById(R.id.account);
        help = view.findViewById(R.id.help);
        signout = view.findViewById(R.id.signout);
        mylist = view.findViewById(R.id.mylist);
        downloads = view.findViewById(R.id.downloads);
        appsetting = view.findViewById(R.id.appsetting);
        account = view.findViewById(R.id.account);
        help = view.findViewById(R.id.help);
        signout = view.findViewById(R.id.signout);
        mycoins.setOnClickListener(this);
        mylist.setOnClickListener(this);
        downloads.setOnClickListener(this);
        appsetting.setOnClickListener(this);
        account.setOnClickListener(this);
        help.setOnClickListener(this);
        signout.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mycoins:
                Intent mycoins = new Intent(getActivity(), My_Coins.class);
                startActivity(mycoins);
                break;
            case R.id.mylist:
                Intent mylist = new Intent(getActivity(), My_List.class);
                startActivity(mylist);
                break;
            case R.id.downloads:
                Intent downloads = new Intent(getActivity(), Downloads.class);
                startActivity(downloads);
                break;
            case R.id.appsetting:
                Intent appsetting = new Intent(getActivity(), App_Settings.class);
                startActivity(appsetting);
                break;
            case R.id.account:
                Intent account = new Intent(getActivity(), Account.class);
                startActivity(account);
                break;
            case R.id.help:
                Intent help = new Intent(getActivity(), Help.class);
                startActivity(help);
                break;
            case R.id.signout:
                session.logoutUser();
                break;

        }
    }
}

