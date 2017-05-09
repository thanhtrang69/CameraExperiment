package com.example.trang.cameraexperiment.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import com.example.trang.cameraexperiment.R;
import com.example.trang.cameraexperiment.App;

/**
 * Created by Trang on 4/8/2017.
 */

public class Image {
    private String title;
    private String data;

    public Image(String title, String data) {
        this.title = title;
        this.data = data;
    }

    public Image(String data) {

        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getData() {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(data);
        if (bitmap != null) {
            return ThumbnailUtils.extractThumbnail(bitmap, 200, 200);
        } else {
            bitmap = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.ad);
            return ThumbnailUtils.extractThumbnail(bitmap, 200, 200);
        }
    }
//        Bitmap bitmap;
//        bitmap = BitmapFactory.decodeFile(data);
//        if (bitmap != null) {
//            return ThumbnailUtils.extractThumbnail(bitmap, 200, 200);
//        } else {
//            bitmap = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.ad);
//            return ThumbnailUtils.extractThumbnail(bitmap, 200, 200);
//        }
//    }

    public void setData(String data) {
        this.data = data;
    }
}
