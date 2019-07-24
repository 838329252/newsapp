package com.example.newsapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amitshekhar.utils.Utils;
import com.bumptech.glide.Glide;
import com.example.newsapp.db.GlobalData;
import com.example.newsapp.db.User;
import com.example.newsapp.fragment.MeFragment;
import com.example.newsapp.util.HttpUtil;
import com.example.newsapp.util.MD5Util;
import com.example.newsapp.util.SaveBitmap;
import com.example.newsapp.util.UrlToBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.Url;

import static org.litepal.LitePalApplication.getContext;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout changeHeadClick;
    private RelativeLayout changeUsernameClick;
    private RelativeLayout changePasswordClick;
    private LinearLayout changeUsername;
    private LinearLayout changePassword;
    private Toolbar toolbar;
    private ImageView head;
    private ImageView goTochangeHead;
    private TextView username;
    private ImageView goToChangeUsername;
    private EditText getNewUsername;
    private Button saveUsername;
    private ImageView goToChangePassword;
    private EditText oldPassword;
    private EditText newPassword;
    private Button savePassword;
    private Button cancleChageName;
    private Button cancleChangePass;
    private TextView lotout;
    private String imagePath;
    private String new_pass;
    private HttpUtil httpUtil=new HttpUtil();
    private static final int CHOOSE_PHOTO = 2;
    private static final int CROP_REQUEST_CODE = 3;
    private MD5Util md5Util=new MD5Util();
    private Uri uri;
    private Uri mCutUri;
    private File cutfile;
    private String url;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        viewHolder();
        requestWritePermission();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        User user = DataSupport.where("user_id=?", GlobalData.getUserId() + "").findFirst(User.class);
        if(user.getHeadPicture().equals("")){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.head);
            head.setImageBitmap(bitmap);
        }else{
            SharedPreferences sharedPreferences=getContext().getSharedPreferences("testSP", Context.MODE_PRIVATE);
            if(sharedPreferences!=null){
                bitmap=SaveBitmap.getBitmap(sharedPreferences);
                head.setImageBitmap(bitmap);
            }else{
                url="http://192.168.43.166:3000/headPicture/"+user.getHeadPicture();
                MyTask mTask = new MyTask();
                mTask.execute();
            }

        }
        changeHeadClick.setOnClickListener(this);
        changeUsernameClick.setOnClickListener(this);
        changePasswordClick.setOnClickListener(this);
        username.setText(user.getUsername());
        lotout.setOnClickListener(this);
    }

    private void viewHolder() {
        changeHeadClick = (RelativeLayout) findViewById(R.id.changeHeadClick);
        changeUsernameClick = (RelativeLayout) findViewById(R.id.changeUsernameClick);
        changePasswordClick = (RelativeLayout) findViewById(R.id.changePasswordClick);
        changeUsername = (LinearLayout) findViewById(R.id.changeUsername);
        changePassword = (LinearLayout) findViewById(R.id.changePassword);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        head = (ImageView) findViewById(R.id.head);
        goTochangeHead = (ImageView) findViewById(R.id.goToChangeHead);
        username = (TextView) findViewById(R.id.username);
        goToChangeUsername = (ImageView) findViewById(R.id.goToChangeUsername);
        getNewUsername = (EditText) findViewById(R.id.getNewUsername);
        saveUsername = (Button) findViewById(R.id.saveUsername);
        goToChangePassword = (ImageView) findViewById(R.id.goToChangePassword);
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        savePassword = (Button) findViewById(R.id.savePassword);
        cancleChageName = (Button) findViewById(R.id.cancleChangeName);
        cancleChangePass = (Button) findViewById(R.id.cancleChangePass);
        lotout = (TextView) findViewById(R.id.logout);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeHeadClick:
                if (ContextCompat.checkSelfPermission(Settings.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Settings.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 1);
                } else {
                    openAlbum();
                }
                break;
            case R.id.changeUsernameClick:
                changeUsername.setVisibility(View.VISIBLE);
                saveUsername.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        username.setText(getNewUsername.getText().toString());
                        User user = new User();
                        user.setUsername(getNewUsername.getText().toString());
                        user.updateAll("user_id=?", GlobalData.getUserId() + "");
                        changeUsername.setVisibility(View.GONE);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                httpUtil.sendOkHttpToChangeUsername(GlobalData.getUserId(),getNewUsername.getText().toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getNewUsername.setText("");
                                    }
                                });
                            }
                        }).start();
                    }
                });
                cancleChageName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNewUsername.setText("");
                        changeUsername.setVisibility(View.GONE);
                    }
                });
                break;

            case R.id.changePasswordClick:
                changePassword.setVisibility(View.VISIBLE);
                savePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = GlobalData.getUserId();
                        String old_pass = md5Util.encrypt(oldPassword.getText().toString());
                        new_pass = md5Util.encrypt(newPassword.getText().toString());
                        User user = DataSupport.where("user_id=?", id + "").findFirst(User.class);
                        if (user.getPassword().equals(old_pass)) {
                            User user1 = new User();
                            user1.setPassword(new_pass);
                            user1.updateAll("user_id=?", id + "");
                            oldPassword.setText("");
                            changePassword.setVisibility(View.GONE);
                            Toast.makeText(Settings.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    httpUtil.sendOkHttpToChangePassword(GlobalData.getUserId(),new_pass);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            newPassword.setText("");
                                        }
                                    });
                                }
                            }).start();
                        } else {
                            Toast.makeText(Settings.this, "原密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                            oldPassword.setText("");
                        }
                    }
                });
                cancleChangePass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        oldPassword.setText("");
                        newPassword.setText("");
                        changePassword.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.logout:
                Intent intent = new Intent(Settings.this, LoginActivity.class);
                startActivity(intent);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(Settings.this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    private void requestWritePermission() {
        if (ContextCompat.checkSelfPermission(Settings.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Settings.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
    }
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    startPhotoZoom(intent,CROP_REQUEST_CODE);
                }
                break;
            case CROP_REQUEST_CODE:
                if(mCutUri!=null){
                    displayImage(mCutUri);
                }break;
            default:
                break;
        }
    }
    private void startPhotoZoom(Intent data, int REQUE_CODE_CROP) {
        try{
            uri=data.getData();
            Intent intent = new Intent("com.android.camera.action.CROP");
            //设置裁剪之后的图片路径文件
            cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                    "cutcamera.png"); //随便命名一个
            imagePath=Environment.getExternalStorageDirectory().getPath()+"/cutcamera.png";
            if (cutfile.exists()){ //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = uri; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            outputUri = Uri.fromFile(cutfile);
            mCutUri = outputUri;
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop",true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            if (Build.MANUFACTURER.equals("HUAWEI")) {
                intent.putExtra("aspectX", 9998);
                intent.putExtra("aspectY", 9999);
            } else {
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
            }
            //设置要裁剪的宽高
            intent.putExtra("outputX", 700); //200dp
            intent.putExtra("outputY",700);
            intent.putExtra("scale",true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data",false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(intent, REQUE_CODE_CROP);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void displayImage(Uri uri) {
        try{
            if (uri != null) {
                final Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                head.setImageBitmap(bitmap);
                SaveBitmap.SaveBitmap(bitmap);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        uploadImage();
                    }
                }).start();
            } else {
                /*Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();*/
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    public void uploadImage() {
        String url="http://192.168.43.166:3000/get/updateHead";
        try{
            OkHttpClient okHttpClient = new OkHttpClient();
            File file = cutfile;
            RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);//文件与类型放入请求体
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(),image)
                    .addFormDataPart("userId",GlobalData.getUserId()+"")
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            String responseData=response.body().string();
            JSONArray jsonArray=new JSONArray(responseData);
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            String headPicture=jsonObject.getString("headPicture");
            User user=new User();
            user.setHeadPicture(headPicture);
            user.updateAll("user_id=?",GlobalData.getUserId()+"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private class MyTask extends AsyncTask<Void,Integer, Boolean> {

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                bitmap=UrlToBitmap.UrlToBitmap(url);
                publishProgress();
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }
        // 方法4：onPostExecute（）
        // 作用：接收线程任务执行结果、将执行结果显示到UI组件
        // 注：必须复写，从而自定义UI操作
        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                head.setImageBitmap(bitmap);
            }
        }
    }
}

