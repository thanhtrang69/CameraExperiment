package com.example.trang.cameraexperiment.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.trang.cameraexperiment.R;
import com.example.trang.cameraexperiment.activity.MainActivity;
import com.example.trang.cameraexperiment.camera.CameraTake;

/**
 * Created by Trang on 4/9/2017.
 */

public class FragmentCameraTake extends Fragment implements View.OnClickListener, CameraTake.OnCameraReadListner {

    private boolean is;

    private View view;
    private ImageButton imgFlashOn;
    private ImageButton imgFlashOff;
    private ImageButton imgTakePhoto;
    private ImageButton imgTakeAlbum;
    private ImageButton imgTakeVideo;
    private TextureView textureView;
    private ImageButton imgBack;

    private CameraTake cameraTake;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_takephoto, container, false);
        initView();
        return view;
    }

    private void initView() {
        imgFlashOn = (ImageButton) view.findViewById(R.id.img_btn_flash_on);
        imgFlashOff = (ImageButton) view.findViewById(R.id.img_btn_flash_off);
        imgTakeAlbum = (ImageButton) view.findViewById(R.id.img_btn_take_ablum);
        imgTakePhoto = (ImageButton) view.findViewById(R.id.img_btn_take_photo);
        imgTakeVideo = (ImageButton) view.findViewById(R.id.img_btn_take_video);
        textureView = (TextureView) view.findViewById(R.id.preview);
        imgBack = (ImageButton) view.findViewById(R.id.img_btn_back);
        imgBack.setOnClickListener(this);
        imgTakeVideo.setOnClickListener(this);
        imgTakePhoto.setOnClickListener(this);
        imgTakeAlbum.setOnClickListener(this);
        imgFlashOff.setOnClickListener(this);
        imgFlashOn.setOnClickListener(this);
        cameraTake = new CameraTake(textureView);
        cameraTake.setOnCameraReadListner(this);
        imgFlashOff.setVisibility(View.GONE);
        imgFlashOn.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_flash_off:
                if (cameraTake.isRecoding()) {
                    cameraTake.colseFlash();
                    imgFlashOff.setVisibility(View.GONE);
                    imgFlashOn.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.img_btn_flash_on:
                if (cameraTake.isStop()) {
                    cameraTake.openFlash();
                    imgFlashOff.setVisibility(View.VISIBLE);
                    imgFlashOn.setVisibility(View.GONE);
                }
                break;
            case R.id.img_btn_back:
                cameraTake.toggleCamera();
                break;
            case R.id.img_btn_take_ablum:
                ((MainActivity) getActivity()).showFragmentAlbum();
                break;
            case R.id.img_btn_take_photo:
                cameraTake.caputrePhoto();
                break;
            case R.id.img_btn_take_video:
                ((MainActivity) getActivity()).showFragmentVideo();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        cameraTake.closeCamera();
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (is) {
            cameraTake.openCamera();
        }
    }

    @Override
    public void Ready() {
        is = true;
        cameraTake.openCamera();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
