package com.example.trang.cameraexperiment.activity;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.trang.cameraexperiment.R;
import com.example.trang.cameraexperiment.fragment.FragmentAlbum;
import com.example.trang.cameraexperiment.fragment.FragmentCameraTake;
import com.example.trang.cameraexperiment.fragment.FragmentVideo;

public class MainActivity extends AppCompatActivity implements FragmentVideo.OnClickButtonListener {
    private FragmentCameraTake fragmentCameraTake;
    private FragmentVideo fragmentVideo;
    private FragmentAlbum fragmentAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragmentTakePhoto();
    }

    public void showFragmentTakePhoto() {
        if (fragmentCameraTake == null) {
            fragmentCameraTake = new FragmentCameraTake();
        }
        getFragmentManager().beginTransaction().replace(android.R.id.content, fragmentCameraTake)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void showFragmentVideo() {
        if (fragmentVideo == null) {
            fragmentVideo = new FragmentVideo();
            fragmentVideo.setOnClickButtonListener(this);
        }
        getFragmentManager().beginTransaction().replace(android.R.id.content, fragmentVideo)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(FragmentVideo.class.getName())
                .commit();
    }

    public void showFragmentAlbum() {
        if (fragmentAlbum == null) {
            fragmentAlbum = new FragmentAlbum();
        }
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragmentAlbum)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(FragmentAlbum.class.getName())
                .commit();
    }

    @Override
    public void onClickTakeVideo() {
        showFragmentTakePhoto();
    }

    @Override
    public void onClickAmbul() {
        showFragmentAlbum();
    }
}
