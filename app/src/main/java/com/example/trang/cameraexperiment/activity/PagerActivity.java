package com.example.trang.cameraexperiment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.example.trang.cameraexperiment.R;
import com.example.trang.cameraexperiment.adapter.CameraAdapter;
import com.example.trang.cameraexperiment.fragment.FragmentAlbum;
import com.example.trang.cameraexperiment.model.Image;
import com.example.trang.cameraexperiment.manager.ImageManager;
import com.example.trang.cameraexperiment.adapter.CameraViewPager;

import java.util.ArrayList;

/**
 * Created by Trang on 4/10/2017.
 */

public class PagerActivity extends Activity {
    private ImageManager manager;
    private ArrayList<Image> arrayList;
    private CameraAdapter adapter;
    private ViewPager viewPager;
    private CameraViewPager cameraViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        initView();
    }

    private void initView() {
        int posisson = getIntent().getIntExtra(FragmentAlbum.POSITION, 0);
        viewPager = (ViewPager) findViewById(R.id.view_pager_context);
        manager = new ImageManager();
        arrayList = manager.getAll(this);
        cameraViewPager = new CameraViewPager(this, arrayList);
        viewPager.setAdapter(cameraViewPager);
        viewPager.setCurrentItem(posisson);

    }

}
