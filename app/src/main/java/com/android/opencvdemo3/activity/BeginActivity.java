package com.android.opencvdemo3.activity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.opencvdemo3.result.RecognizeFail;
import com.android.opencvdemo3.result.RecognizeSuccess;
import com.android.opencvdemo3.result.RegisterSuccess;
import com.test.opencvdemo3.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Locale;

/**
 * 进入app的第一个活动
 */

public class BeginActivity extends BaseActivity {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int SHOW_PHOTO = 3;

    private TextView welcome;

    private Uri imageUri;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_layout);

        welcome = (TextView) findViewById(R.id.welcome);

        Button takePhoto = (Button) findViewById(R.id.take_photo);
        Button chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);

        /**
         * 拍照按钮事件
         */
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                * 查看SD卡
                */
                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                    Toast.makeText(BeginActivity.this, "内存卡异常，请检查内存卡插入是否正确", Toast.LENGTH_SHORT).show();
                }
                /**
                 * 创建一个目录用于存放拍的照片
                 */
                path = Environment.getExternalStorageDirectory() + "/DCIM/Camera/IMG_" + DateFormat.format("yyyyMMdd_hhmmss",
                        Calendar.getInstance(Locale.CHINA))   + ".jpg";
                Log.d("BeginActivity", "目录创建成功。");
                /**
                 * 创建File对象，用于存储拍照后的图片
                 */
                File outputImage = new File(path);
                Log.d("BeginActivity", "File创建成功。");

                /**
                * 将File对象转成Uri对象
                */
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(BeginActivity.this, "com.android.opencvdemo3.activity.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                /**
                * 构建隐式Intent，指定图片输出地址
                */
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                /**
                * 启动相机，并向下一个活动传递参数
                */
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        /**
         * 相册按钮事件
         */
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                /**
                * 打开相册
                */
                startActivityForResult(intent, CHOOSE_PHOTO);
            }
        });


        /**
         * 以下是用于测试界面跳转的按钮定义
         */
        Button layout1 = (Button) findViewById(R.id.layouttest1);
        Button layout2 = (Button) findViewById(R.id.layouttest2);
        Button layout3 = (Button) findViewById(R.id.layouttest3);

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeginActivity.this, RegisterSuccess.class);
                startActivity(intent);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeginActivity.this, RecognizeSuccess.class);
                startActivity(intent);
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeginActivity.this, RecognizeFail.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //拍照以后
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    handleTakenImage();
                }
                break;

            //从相册选照片以后
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        /**
                        * 4.4及以上版本
                        */
                        handleImageOnKitKat(data);
                    } else {
                        /**
                        * 4.4以下版本
                        */
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 对拍照后照片的操作
     */
    private void handleTakenImage() {
        Bitmap bitmap = getBitmap(imageUri);
        byte[] bytes = bitmap2Bytes(bitmap);
        String pathfromcamera = imageUri.getPath();
        Log.d("BeginActivity", pathfromcamera);

        /**
         * 隐式传递到HelloActivity
         * 加一个category用以区分
         */
        Intent intent = new Intent("com.android.opencvdemo3.activity.HelloActivity.ACTION_START");
        intent.addCategory("com.android.opencvdemo3.activity.HelloActivity.FOR_TAKEN_PHOTO");
        /**
         * intent的附加信息
         * 把Bitmap转成了bytes数组
         */
        intent.putExtra("imageBytes", bytes);
        intent.putExtra("pathfromcamera", pathfromcamera);
        /**
         * 启动跳转程序
         */
        startActivity(intent);
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        /**
        * 获取路径
        */
        Uri uri = data.getData();

        if (DocumentsContract.isDocumentUri(this, uri)) {
            /**
             *Document类型Uri对document id处理
             */
            String docId = DocumentsContract.getDocumentId(uri);
            /**
            * 与Uri中Authority部分比较,media格式需进一步解析
            * */
            if ("com.android.providers.media.documents".equals(
                    uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                /**
                * 传入getImagePath获得真实路径
                */
                imagePath = getImagePath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(
                    uri.getAuthority())) {
                /**
                * downloads格式获得路径
                */
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            /**
            * content类型的Uri，则使用普通方式处理
            */
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            /**
             * file类型的Uri，直接获取图片路径即可
             */
            imagePath = uri.getPath();
        }
        /**
        * 图片显示
        */
        handleChosenImage(imagePath);
    }

    /**
    * 4.4以下版本返回图片真实Uri，直接处理
    */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        handleChosenImage(imagePath);
    }

    /**
    * 获取图片路径
    */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            /**
            * 定位第一行返回指定列名称
            */
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            /**
            * 释放资源
            */
            cursor.close();
        }
        return path;
    }

    /**
     * 对从相册选照片后的操作
     */
    private void handleChosenImage(String imagePath) {
        /**
         * 隐式传递到HelloActivity
         * 顺便加一个category用以区分
         */
        Intent intent = new Intent("com.android.opencvdemo3.activity.HelloActivity.ACTION_START");
        intent.addCategory("com.android.opencvdemo3.activity.HelloActivity.FOR_CHOSEN_PHOTO");

        //intent的一些附加信息，跟拍照的不一样
        intent.putExtra("image_Path", imagePath);

        /**
         * 启动跳转程序
         */
        startActivity(intent);
    }

    // Uri获取bitmap
    private Bitmap getBitmap(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // bitmap转byte数组
    private byte[] bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }



}
