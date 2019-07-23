package com.aptoon.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.aptoon.utils.LocaleHelper;
import com.aptoon.controllers.Apis;
import com.aptoon.view.design.CircularImageView;
import com.aptoon.view.design.KProgressHUD;
import com.aptoon.entity.Update_profile;
import com.aptoon.R;
import com.aptoon.utils.UserSessionManager;
import com.aptoon.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sdsmdg.tastytoast.TastyToast;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.*;
import java.util.*;

import static android.media.MediaRecorder.VideoSource.CAMERA;
import static com.aptoon.controllers.Apis.Photo_Base_URL;

public class Account extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = Account.class.getSimpleName();
    private ImageView imBack;
    private CircularImageView user_image;
    private EditText user_name;
    private TextView user_email_mobile_no;
    private Button save;
    private View bottomSheet;
    BottomSheetBehavior bottomSheetBehavior;
    private TextView cancel;
    UserSessionManager session;
    String ID, name, email, picture;
    String picturePath;
    String user_name1;
    File file;
    String ss;
    private static final String IMAGE_DIRECTORY = "/Apptoon Camara Image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        ID = user.get(session.KEY_ID);
        name = user.get(session.KEY_NAME);
        email = user.get(session.KEY_EMAIL);
        picture = user.get(session.KEY_PICTURE);
        initView();
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
           ss = Photo_Base_URL + picture;
            Log.e("picture", ss);
        }

        //Implement click listeners
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void initView() {
        imBack = findViewById(R.id.imBack);
        user_image = findViewById(R.id.user_image);
        user_name = findViewById(R.id.user_name);
        bottomSheet = findViewById(R.id.bottom_sheet);
        cancel = findViewById(R.id.cancel);
        user_email_mobile_no = findViewById(R.id.user_email_mobile_no);
        save = findViewById(R.id.save);
        save.setOnClickListener(this);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        findViewById(R.id.gallery).setOnClickListener(this);
        findViewById(R.id.camera).setOnClickListener(this);
        user_image.setOnClickListener(this);
        cancel.setOnClickListener(this);
        imBack.setOnClickListener(this);
        user_name.setText(name);
        user_email_mobile_no.setText(email);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                submit();
                break;
            case R.id.user_image:
                //permission allow by user
                Utils.checkAndRequestPermissions(this);
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else {
                    bottomSheet.setVisibility(View.VISIBLE);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                bottomSheetBehavior.setPeekHeight(500);
                bottomSheetBehavior.setHideable(true);
                break;
            case R.id.gallery:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else {
                    bottomSheet.setVisibility(View.GONE);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                bottomSheetBehavior.setPeekHeight(500);
                bottomSheetBehavior.setHideable(true);
                break;
            case R.id.camera:
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, 1);
              if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else {
                    bottomSheet.setVisibility(View.GONE);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                bottomSheetBehavior.setPeekHeight(500);
                bottomSheetBehavior.setHideable(true);
                break;
            case R.id.cancel:
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else {
                    bottomSheet.setVisibility(View.GONE);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                bottomSheetBehavior.setPeekHeight(500);
                bottomSheetBehavior.setHideable(true);
                break;
            case R.id.imBack:
                finish();
                break;
        }
    }
    /* Camara and Gallery get a image path*/
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            return;
        }
        if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            picturePath = saveImage(thumbnail);
             user_image.setImageBitmap(thumbnail);
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                 picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("PATH", picturePath + "");
                user_image.setImageBitmap(thumbnail);
            }
        }

    private void submit() {
        // validate
        user_name1 = user_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "name不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something

        HashMap<String,String> hashmap =new HashMap<>();
        hashmap.put("user_id",ID);
        hashmap.put("name",user_name1);
        hashmap.put("email",email);
        if (picturePath!=null){
            hashmap.put("picture",picturePath);
        }else
        {
            hashmap.put("picture","");
        }
        try {
            hashmap.put("oldpicture",picture);
        }catch (Exception e){
            e.printStackTrace();
        }
        Update_Account(hashmap);
    }

    private void Update_Account(HashMap<String,String> datapart) {
        final KProgressHUD hud = KProgressHUD.create(Account.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
            hud.setSize(100, 100);
            hud.show();
         file= new File(datapart.get("picture"));
        // Map is used to multipart the file using okhttp3.RequestBody

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part user_id = MultipartBody.Part.createFormData("user_id",Objects.requireNonNull(datapart.get("user_id")));
        MultipartBody.Part name = MultipartBody.Part.createFormData("name", Objects.requireNonNull(datapart.get("name")));
        MultipartBody.Part user_email = MultipartBody.Part.createFormData("email", Objects.requireNonNull(datapart.get("email")));
        MultipartBody.Part profile_photo;
        if(picturePath!=null &&  !picturePath.isEmpty()){
            profile_photo= MultipartBody.Part.createFormData("picture", file.getName(), requestBody);
        }else {
            profile_photo= MultipartBody.Part.createFormData("picture", "");
        }
        MultipartBody.Part  oldpicture;
        if(picture!=null && !picture.isEmpty()){
         oldpicture = MultipartBody.Part.createFormData("oldpicture", picture);
        }else {
            oldpicture = MultipartBody.Part.createFormData("oldpicture", "");
        }

        RequestBody.create(MediaType.parse("text/plain"), file.getName());
        // Parsing any Media type file
        Call<Update_profile> call = Apis.getAPIService().Update_profile(user_id,name,user_email,oldpicture,profile_photo);
        call.enqueue(new Callback<Update_profile>() {
            @SuppressLint("Assert")
            @Override
            public void onResponse(Call<Update_profile> call, Response<Update_profile> response) {
                hud.dismiss();
                Update_profile userdata=response.body();
                if(userdata.getStatus().equals("200")) {
                   session.createUserLoginSession(String.valueOf(userdata.getData().getId()),
                           userdata.getData().getName(), userdata.getData().getEmail(),
                           userdata.getData().getPicture(), String.valueOf(userdata.getData().getStatus()),                 userdata.getData().getNotification(), userdata.getData().getType(), String.valueOf(userdata.getData().getPayment()),
                           userdata.getData().getRemember_token(), userdata.getData().getCreated_at(), userdata.getData().getUpdated_at());
                    TastyToast.makeText(getApplicationContext(), userdata.getMessage(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    startActivity(new Intent(Account.this, Dashboard.class));
                }else {
                    TastyToast.makeText(getApplicationContext(), userdata.getMessage(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                }

            }
            @Override
            public void onFailure(Call<Update_profile> call, Throwable t) {
                hud.dismiss();
                Log.d("response", "vv" + t.getMessage());
            }
        });

    }
}
