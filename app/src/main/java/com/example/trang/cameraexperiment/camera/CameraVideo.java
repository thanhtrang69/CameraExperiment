package com.example.trang.cameraexperiment.camera;

import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Trang on 4/9/2017.
 */

@SuppressWarnings("deprecation")
public class CameraVideo extends SurfaceView implements SurfaceHolder.Callback {
    private MediaRecorder mediaRecorder;
    private SurfaceHolder surfaceHolder;
    private Context mContext;
    private Camera camera;
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private static final int RECORDING_STAGE = 0;
    private static final int STOP_RECORDING_STAGE = 1;
    private static int STAGE = STOP_RECORDING_STAGE;
    private static String path = Environment.getExternalStorageDirectory().getPath() + "/";

    public CameraVideo(Context context) {
        super(context);
        this.mContext = context;
        init();
    }


    public CameraVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraVideo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        camera = Camera.open(cameraId);
        camera.setDisplayOrientation(90);
        mediaRecorder = new MediaRecorder();
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        camera.unlock();
        mediaRecorder.setCamera(camera);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
        mediaRecorder.setOutputFile(path + System.currentTimeMillis() + ".mp4");


    }

    public void startCaputerVideo() {

        try {
            init();
            mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
            mediaRecorder.prepare();
            mediaRecorder.start();
            STAGE = RECORDING_STAGE;
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void stopCaputerVideo() {
        if (mediaRecorder != null) {
            surfaceHolder.removeCallback(this);
            surfaceHolder = null;
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            camera.lock();
            camera.release();
            camera = null;
            STAGE = STOP_RECORDING_STAGE;

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mediaRecorder.setPreviewDisplay(holder.getSurface());
            mediaRecorder.prepare();
            mediaRecorder.start();
            STAGE = RECORDING_STAGE;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopCaputerVideo();
    }

    public boolean isStopVideo() {
        return (STAGE == STOP_RECORDING_STAGE);
    }

    public boolean isRecordingVideo() {
        return (STAGE == RECORDING_STAGE);
    }


}
