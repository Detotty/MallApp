package com.mallapp.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.naver.android.helloyako.imagecrop.util.BitmapLoadUtils;
import com.naver.android.helloyako.imagecrop.view.ImageCropView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Sharjeel on 2/9/2016.
 */
public class CropActivity extends Activity {
    public static final String TAG = "CropActivity";

    private ImageCropView imageCropView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        imageCropView = (ImageCropView) findViewById(R.id.image);

        Intent i = getIntent();
        Uri uri = i.getData();

//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        int imageWidth = (int) ( (float) metrics.widthPixels / 1.5 );
//        int imageHeight = (int) ( (float) metrics.heightPixels / 1.5 );
//
//        bitmap = BitmapLoadUtils.decode(uri.toString(), imageWidth, imageHeight);
//
//        imageCropView.setImageBitmap(bitmap);

        imageCropView.setImageFilePath(BitmapLoadUtils.getPathFromUri(this, uri));

        imageCropView.setAspectRatio(16, 9);



        findViewById(R.id.crop_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageCropView.isChangingScale()) {
                    AddCardActivity.CropedImage = imageCropView.getCroppedImage();
                    finish();
                }
            }
        });

        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isPossibleCrop(int widthRatio, int heightRatio){
        int bitmapWidth = imageCropView.getViewBitmap().getWidth();
        int bitmapHeight = imageCropView.getViewBitmap().getHeight();
        if(bitmapWidth < widthRatio && bitmapHeight < heightRatio){
            return false;
        } else {
            return true;
        }
    }

    public File bitmapConvertToFile(Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        File bitmapFile = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory("image_crop_sample"),"");
            if (!file.exists()) {
                file.mkdir();
            }

            bitmapFile = new File(file, "IMG_" + (new SimpleDateFormat("yyyyMMddHHmmss")).format(Calendar.getInstance().getTime()) + ".jpg");
            fileOutputStream = new FileOutputStream(bitmapFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            MediaScannerConnection.scanFile(this, new String[]{bitmapFile.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {

                }

                @Override
                public void onScanCompleted(String path, Uri uri) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CropActivity.this, "file saved", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                }
            }
        }

        return bitmapFile;
    }

}
