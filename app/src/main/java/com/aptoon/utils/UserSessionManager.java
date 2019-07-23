package com.aptoon.utils;

import java.util.HashMap;

    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.content.SharedPreferences.Editor;

import com.aptoon.view.activity.Dashboard;
import com.aptoon.view.activity.SignIn;
import com.aptoon.view.activity.Splash_SignIn;

public class UserSessionManager {

        // Shared Preferences reference
         static  SharedPreferences pref;

        // Editor reference for Shared preferences
        Editor editor;

        // Context
        Context _context;

        // Shared pref mode
        int PRIVATE_MODE = 0;

        // Sharedpref file name
        private static final String PREFER_NAME = "User Login Data";

         private static final String IS_LOGIN = "IsLoggedIn";
        // User name (make variable public to access from outside)
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_EMAIL = "email";
        public static final String KEY_PICTURE = "picture";
        public static final String KEY_STATUS = "status";
        public static final String KEY_NOTIFICATION = "notification";
    public static final String KEY_TYPE = "type";
    public static final String KEY_PAYMENT = "payment";
    public static final String KEY_REMEMBER_TOKEN = "remember_token";
    public static final String KEY_CREATED_AT = "created_at";
    public static final String KEY_UPDATED_AT = "updated_at";

        // Constructor
        public UserSessionManager(Context context){
            this._context = context;
            pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

        //Create login session
        public void createUserLoginSession(String id,String name,String email,String picture,String status,
                                           String notification,String type,String payment,String remember_token,
                                           String created_at, String updated_at){
            // Storing login value as TRUE
            editor.putBoolean(IS_LOGIN, true);
            // Storing name in pref
            editor.putString(KEY_ID, id);
            editor.putString(KEY_NAME, name);
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_PICTURE, picture);
            editor.putString(KEY_STATUS, status);
            editor.putString(KEY_NOTIFICATION, notification);
            editor.putString(KEY_TYPE, type);
            editor.putString(KEY_PAYMENT, payment);
            editor.putString(KEY_REMEMBER_TOKEN, remember_token);
            editor.putString(KEY_CREATED_AT, created_at);
            editor.putString(KEY_UPDATED_AT, updated_at);
//            editor.putString(KEY_ID, String.valueOf(data.getId()));
//            editor.putString(KEY_NAME, data.getName());
//            editor.putString(KEY_EMAIL, data.getEmail());
//            editor.putString(KEY_PICTURE, data.getPicture());
//            editor.putString(KEY_STATUS, String.valueOf(data.getStatus()));
//            editor.putString(KEY_NOTIFICATION, data.getNotification());
//            editor.putString(KEY_TYPE, data.getType());
//            editor.putString(KEY_PAYMENT, String.valueOf(data.getPayment()));
//            editor.putString(KEY_REMEMBER_TOKEN, data.getRemember_token());
//            editor.putString(KEY_CREATED_AT, data.getCreated_at());
//            editor.putString(KEY_UPDATED_AT, data.getUpdated_at());
            // commit changes
            editor.apply();
            editor.commit();

        }


        /**
         * Get stored session data
         * */
        public HashMap<String, String> getUserDetails(){
            //Use hashmap to store user credentials
            HashMap<String, String> user = new HashMap<String, String>();
            // user name
            user.put(KEY_ID, pref.getString(KEY_ID, null));
            user.put(KEY_NAME, pref.getString(KEY_NAME, null));
            user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
            user.put(KEY_PICTURE, pref.getString(KEY_PICTURE, null));
            user.put(KEY_STATUS, pref.getString(KEY_STATUS, null));
            user.put(KEY_NOTIFICATION, pref.getString(KEY_NOTIFICATION, null));
            user.put(KEY_TYPE, pref.getString(KEY_TYPE, null));
            user.put(KEY_PAYMENT, pref.getString(KEY_PAYMENT, null));
            user.put(KEY_REMEMBER_TOKEN, pref.getString(KEY_REMEMBER_TOKEN, null));
            user.put(KEY_CREATED_AT, pref.getString(KEY_CREATED_AT, null));
            user.put(KEY_UPDATED_AT, pref.getString(KEY_UPDATED_AT, null));
            // return user
            return user;
        }
       public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Splash_SignIn.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
        else{
            Intent i1 = new Intent(_context, Dashboard.class);
            _context.startActivity(i1);
        }

    }
        /**
         * Clear session details
         * */
        public void logoutUser(){
            editor.putBoolean(IS_LOGIN, false);
            editor.apply();
            // Clearing all user data from Shared Preferences
//            editor.clear();
//            editor.commit();

            // After logout redirect user to Login Activity
            Intent i = new Intent(_context, SignIn.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
        }
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public  static  String isLoginId(){
            return pref.getString(KEY_ID,"0");

    }

    public  void  iseditor(){
        editor.clear();
        editor.commit();

    }
    }