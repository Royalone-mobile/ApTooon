package com.aptoon.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
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
import com.aptoon.entity.Login_Data;
import com.aptoon.R;
import com.aptoon.utils.UserSessionManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private ImageView back;
    private EditText et_email;
    private EditText et_password;
    private AppCompatButton btn_signin;
    private TextView forgotpassword;
    private Button facebook;
    private Button google;
    private TextView signuplink;
    private SignInButton google_signin;
    private static final String TAG = SignIn.class.getSimpleName();
    private static final int RC_SIGN_IN = 430;
    private GoogleApiClient mGoogleApiClient;
    public CallbackManager callbackManager;
    GoogleSignInResult result;
    LoginButton facebook_login;
    public String id, name, email, gender, birthday;
    UserSessionManager session;
     KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_in);
        callbackManager = CallbackManager.Factory.create();
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
        initView();
       //  <--------Facebook integration---------->
        facebook_login = findViewById(R.id.facebook_login);
        List< String > permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken");
        //facebook_login.setReadPermissions(permissionNeeds);
        facebook_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {@Override
        public void onSuccess(LoginResult loginResult) {
            System.out.println("onSuccess");
            String accessToken = loginResult.getAccessToken()
                    .getToken();
            Log.i("accessToken", accessToken);
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {@Override
                    public void onCompleted(JSONObject object,
                                            GraphResponse response) {
                        Log.i("LoginActivity",
                                response.toString());
                        try {
                            id = object.getString("id");
                            name=object.getString("name");
                            email=object.getString("email");
                            try {
                                URL profile_pic = new URL(
                                        "http://graph.facebook.com/" + id + "/picture?type=large");
                                Log.i("profile_pic",
                                        profile_pic + "");
                                Log.e(TAG, "id: " + id + ", name: " + name + ", email: " + email + ", Image: " + profile_pic);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            Log.e("UserDate", String.valueOf(object));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields","id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }
            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }
            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
    private void initView() {
        back = findViewById(R.id.back);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_signin = findViewById(R.id.btn_signin);
        forgotpassword = findViewById(R.id.forgotpassword);
        facebook = findViewById(R.id.facebook);
        google = findViewById(R.id.google);
        signuplink = findViewById(R.id.signuplink);
        btn_signin.setOnClickListener(this);
        facebook.setOnClickListener(this);
        google.setOnClickListener(this);
        back.setOnClickListener(this);
        signuplink.setOnClickListener(this);
        initializeControls();
        initializeGPlusSettings();
    }
    //<------Google integration---------------->
    private void initializeControls() {
        google_signin = findViewById(R.id.google_signin);
        google_signin.setOnClickListener(this);
    }

    private void initializeGPlusSettings() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        google_signin.setSize(SignInButton.SIZE_STANDARD);
        google_signin.setScopes(gso.getScopeArray());
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void handleGPlusSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            //Fetch values
            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();
            String familyName = acct.getFamilyName();
            Log.e(TAG, "Name: " + personName + ", email: " + email + ", Image: " + personPhotoUrl + ", Family Name: " + familyName);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);

        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            result = (GoogleSignInResult) opr.get();
            handleGPlusSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback() {
                @Override
                public void onResult(@NonNull Result result) {

                }
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleGPlusSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGPlusSignInResult(result);
        }
        else{
            super.onActivityResult(requestCode, responseCode, data);
            callbackManager.onActivityResult(requestCode, responseCode, data);
        }
    }
    //<-----End google integration------->
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signin:
//                submit();
                startActivity(new Intent(SignIn.this, Dashboard.class));
                break;
            case R.id.facebook:
                 facebook_login.performClick();
                break;
            case R.id.google:
                signIn();
                break;
            case R.id.back:
                Intent back=new Intent(SignIn.this, Splash_SignIn.class);
                startActivity(back);
                break;
            case R.id.signuplink:
                Intent signuplink = new Intent(this, Signup.class);
                startActivity(signuplink);
                break;
        }
    }
    private void submit() {
        // validate
        String email = et_email.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            TastyToast.makeText(getApplicationContext(), "Please Enter Email and Mobile No.",TastyToast.LENGTH_SHORT, TastyToast.WARNING);

            return;
        }
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {

            TastyToast.makeText(getApplicationContext(), "Please Enter Password", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
            return;
        }
        Map<String, String> map = new HashMap<>();
        //(email,password)
        map.put("email", email);
        map.put("password", password);
        map.put("token", "dskajdkasj d asldjas");
        User_SignIn(map);
    }
    private void User_SignIn(Map<String, String> map) {
        final KProgressHUD hud = KProgressHUD.create(SignIn.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setSize(100, 100);
        hud.show();
        Call<Login_Data> call = Apis.getAPIService().Login_Data(map);
        call.enqueue(new Callback<Login_Data>() {
            @SuppressLint("Assert")
            @Override
            public void onResponse(Call<Login_Data> call, Response<Login_Data> response) {
                hud.dismiss();
                Login_Data userdata=response.body();
                if (userdata != null) {
                    if(userdata.getStatus().equals("200")){
                        if(String.valueOf(userdata.getData().getId()).equals(session.isLoggedIn())){
                            TastyToast.makeText(getApplicationContext(), userdata.getMessage(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            startActivity(new Intent(SignIn.this, Dashboard.class));
                        }else {
                            session.iseditor();
                            session.createUserLoginSession(String.valueOf(userdata.getData().getId()),
                                    userdata.getData().getName(), userdata.getData().getEmail(),
                                    userdata.getData().getPicture(), String.valueOf(userdata.getData().getStatus()),
                                    userdata.getData().getNotification(), userdata.getData().getType(), String.valueOf(userdata.getData().getPayment()),
                                    userdata.getData().getRemember_token(), userdata.getData().getCreated_at(), userdata.getData().getUpdated_at());
                            TastyToast.makeText(getApplicationContext(), userdata.getMessage(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            startActivity(new Intent(SignIn.this, Dashboard.class));
                        }
                    }else {
                        TastyToast.makeText(getApplicationContext(), userdata.getMessage(), TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                }
            }
            @Override
            public void onFailure(Call<Login_Data> call, Throwable t) {
                hud.dismiss();
                Log.d("response", "vv" + t.getMessage());
            }
        });
    }
    @Override
    public void onBackPressed() {
        finishAffinity();

        super.onBackPressed();
    }
}
