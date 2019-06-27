package com.example.newsapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.example.newsapp.db.GlobalData;
import com.example.newsapp.db.User;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.zip.Inflater;

import okhttp3.Response;

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
    private static final int CHOOSE_PHOTO = 2;
    private static final int CROP_REQUEST_CODE = 3;


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
        User user = DataSupport.where("id=?", GlobalData.getUserId() + "").findFirst(User.class);
        if(user.getHeadPicture()==null){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.head);
            head.setImageBitmap(bitmap);
        }else{
            Bitmap bitmap = BitmapFactory.decodeFile(user.getHeadPicture());
            head.setImageBitmap(bitmap);
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
                        user.updateAll("id=?", GlobalData.getUserId() + "");
                        getNewUsername.setText("");
                        changeUsername.setVisibility(View.GONE);
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
                        String old_pass = oldPassword.getText().toString();
                        String new_pass = newPassword.getText().toString();
                        User user = DataSupport.where("id=?", id + "").findFirst(User.class);
                        if (user.getPassword().equals(old_pass)) {
                            User user1 = new User();
                            user1.setPassword(new_pass);
                            user1.updateAll("id=?", id + "");
                            oldPassword.setText("");
                            newPassword.setText("");
                            changePassword.setVisibility(View.GONE);
                            Toast.makeText(Settings.this, "密码修改成功", Toast.LENGTH_SHORT).show();
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

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        handleImageOnKitKat(intent);
                    } else {
                        handleImageBeforekitKat(intent);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        User user = new User();
        user.setHeadPicture(imagePath);
        user.updateAll("id=?", GlobalData.getUserId() + "");
        displayImage(imagePath);
    }

    private void handleImageBeforekitKat(Intent intent) {
        Uri uri = intent.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagepath) {
        if (imagepath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            head.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestWritePermission() {
        if (ContextCompat.checkSelfPermission(Settings.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Settings.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
    }
}

