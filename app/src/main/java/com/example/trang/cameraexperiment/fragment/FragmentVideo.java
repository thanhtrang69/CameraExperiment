package com.example.trang.cameraexperiment.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.trang.cameraexperiment.R;
import com.example.trang.cameraexperiment.camera.CameraVideo;

/**
 * Created by Trang on 4/9/2017.
 */

public class FragmentVideo extends Fragment implements View.OnClickListener {
    private View view;
    private ImageButton imgFlashOn;
    private ImageButton imgFlashOff;
    private ImageButton imgTakePhoto;
    private ImageButton imgPlayVideo;
    private ImageButton imgStopVideo;
    private ImageButton imgAlbum;
    private CameraVideo cameraVideo;
    private static final int REQUEST_ID_VIDEO_CAPTURE = 101;
    private OnClickButtonListener onClickButtonListener;

    public void setOnClickButtonListener(OnClickButtonListener onClickButtonListener) {
        this.onClickButtonListener = onClickButtonListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video, container, false);
        initView();
        return view;
    }

    private void initView() {

        imgFlashOff = (ImageButton) view.findViewById(R.id.img_btflash_off_video);
        imgFlashOn = (ImageButton) view.findViewById(R.id.img_btflash_on_video);
        imgAlbum = (ImageButton) view.findViewById(R.id.take_ablum_video);
        imgPlayVideo = (ImageButton) view.findViewById(R.id.play_video);
        imgTakePhoto = (ImageButton) view.findViewById(R.id.take_photo_video);
        imgStopVideo = (ImageButton) view.findViewById(R.id.stop_video);
        imgTakePhoto.setOnClickListener(this);
        imgPlayVideo.setOnClickListener(this);
        imgStopVideo.setOnClickListener(this);
        imgFlashOn.setOnClickListener(this);
        imgAlbum.setOnClickListener(this);
        imgFlashOff.setOnClickListener(this);
        cameraVideo = (CameraVideo) view.findViewById(R.id.preview_video);
        imgStopVideo.setVisibility(View.VISIBLE);
        imgPlayVideo.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btflash_off_video:
                break;
            case R.id.img_btflash_on_video:
                break;
            case R.id.take_ablum_video:
                onClickButtonListener.onClickAmbul();
                break;
            case R.id.take_photo_video:
                onClickButtonListener.onClickTakeVideo();
                break;
            case R.id.play_video:
                if (cameraVideo.isStopVideo()) {
                    cameraVideo.startCaputerVideo();
                    imgStopVideo.setVisibility(View.VISIBLE);
                    imgPlayVideo.setVisibility(View.GONE);
                }
                break;
            case R.id.stop_video:
                if (cameraVideo.isRecordingVideo()) {
                    cameraVideo.stopCaputerVideo();
                    imgPlayVideo.setVisibility(View.VISIBLE);
                    imgStopVideo.setVisibility(View.GONE);
                }
                break;
            default:
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ID_VIDEO_CAPTURE) {
            if (resultCode == getActivity().RESULT_OK) ;
            Toast.makeText(getActivity(), "Video has been saved to :\n" + data.getData(), Toast.LENGTH_SHORT).show();
        } else if (resultCode == getActivity().RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Video Recording cancelled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Failed to record ic_video", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public interface OnClickButtonListener {
        void onClickTakeVideo();

        void onClickAmbul();
    }
}
