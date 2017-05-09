package com.example.trang.cameraexperiment.manager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.trang.cameraexperiment.model.Image;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Trang on 4/8/2017.
 */

public class ImageManager {
    public ImageManager() {
    }

    public ArrayList<Image> getAll(Context mContext) {
        Cursor cursor;
        cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media.TITLE
                        , MediaStore.Images.Media.DATA}
                , null, null, MediaStore.Images.Media._ID + " DESC");
        if (cursor == null) {
            return new ArrayList<>();
        }
        if (cursor.getCount() == 0) {
            cursor.close();
            return new ArrayList<>();
        }
        ArrayList<Image> arrayList = new ArrayList<>();
        cursor.moveToFirst();
        int indextTitel = cursor.getColumnIndex(MediaStore.Images.Media.TITLE);
        int indextData = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(indextTitel);
            Uri data = Uri.parse(cursor.getString(indextData));

//            if (data != null) {
//                if (!new File(data.getPath()).isDirectory()) {
//                    String path = data.getPath();
//                    String lastPath = path.substring(path.length() - 3, path.length());
//                    Log.d("first", "getAll: "+lastPath);
//                    if ("jpg".equals(lastPath.trim().toLowerCase()) || "png".equals(lastPath.trim().toLowerCase())) {
//                        Log.d("ok", "add");
//                        arrayList.add(new Image(path));
//                    }
//                }
//            }
            if (data != null) {
                if (new File(data.getPath()).isDirectory()) {
                    String path = data.getPath();
                    String pathlist = path.substring(path.length() - 3, path.length());
                    if ("jpg".equals(pathlist.toLowerCase()) || "png".equals(pathlist.toLowerCase())) {
                        arrayList.add(new Image(path));
                    }
                }
            }
            cursor.moveToNext();

        }
        File file = new File(Environment.getExternalStorageDirectory().getPath() +
                "/" + Environment.DIRECTORY_PICTURES);
//        if (file.exists()) {
//            File[] files = file.listFiles();
//            for (int i = 0; i < files.length; i++) {
//                String path = files[i].getAbsolutePath();
//                if (!files[i].isDirectory()) {
//                    arrayList.add(new Image(path));
//                }
//
//            }
//        }
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                String path = files[i].getAbsolutePath();
                if (!files[i].isDirectory()) {
                    arrayList.add(new Image(path));
                }
            }
        }

        return arrayList;
    }
}
