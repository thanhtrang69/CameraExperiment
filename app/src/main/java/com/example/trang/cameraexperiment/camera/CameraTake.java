package com.example.trang.cameraexperiment.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Environment;
import android.view.TextureView;
import android.view.ViewTreeObserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Trang on 4/9/2017.
 */

@SuppressWarnings("deprecation")
public class CameraTake implements ViewTreeObserver.OnGlobalLayoutListener, TextureView.SurfaceTextureListener {
    private TextureView textureView;
    private Camera camera;
    private int cameraId;
    private int width;
    private int height;
    private boolean isPreviewSizeDone;
    private OnCameraReadListner onCameraReadListner;
    private Camera.Parameters parameters;
    private static final int RECORDING_STAGE_TAKE = 0;
    private static final int STOP_RECORDING_STAGE_TAKE = 1;
    private static int STAGE_TAKE = STOP_RECORDING_STAGE_TAKE;

    public void setOnCameraReadListner(OnCameraReadListner ReadListner) {
        this.onCameraReadListner = ReadListner;
    }

    public CameraTake(TextureView textureView) {
        this.textureView = textureView;
        this.textureView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    }

    public void openCamera() {
        if (camera != null) {
            closeCamera();
        }
        camera = Camera.open(cameraId);
        camera.setDisplayOrientation(90);
        parameters = camera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        Camera.Size size = getCameraPreviewSizeOptimal(sizes);
        parameters.setPictureSize(size.width, size.height);
        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(parameters);
    }

    public void closeCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public void toggleCamera() {
        if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        closeCamera();
        openCamera();
        try {

            camera.setPreviewTexture(textureView.getSurfaceTexture());
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Camera.Size getCameraPreviewSizeOptimal(
            List<Camera.Size> previewSizes) {
        Camera.Size sizeOptimal = null;


        float ratio = width > height ?
                (float) width / height : (float) height / width;
        int multiple = width * height;

        int delta = Integer.MAX_VALUE;
        for (Camera.Size size : previewSizes) {
            int w = size.width;
            int h = size.height;
            float r = w > h ? (float) w / h : (float) h / w;
            if (Math.abs(ratio - r) <= 0.1F) {
                int m = w * h;
                if (Math.abs(multiple - m) < delta) {
                    delta = Math.abs(multiple - m);
                    sizeOptimal = size;
                }
            }
        }

        if (sizeOptimal != null) {
            return sizeOptimal;
        }

        delta = Integer.MAX_VALUE;
        for (Camera.Size size : previewSizes) {
            int w = size.width;
            int h = size.height;
            int m = w * h;
            if (Math.abs(multiple - m) < delta) {
                delta = Math.abs(multiple - m);
                sizeOptimal = size;
            }
        }

        return sizeOptimal;
    }

    public void caputrePhoto() {
        final Calendar calendar = Calendar.getInstance();
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/" + Environment.DIRECTORY_PICTURES + "/" + calendar.getTimeInMillis();
                File file = new File(path);
                try {

                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                } catch (FileNotFoundException e) {

                }
                camera.startPreview();
            }
        });
    }

    public void openFlash() {
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        STAGE_TAKE = RECORDING_STAGE_TAKE;
    }

    public void colseFlash() {
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        STAGE_TAKE = STOP_RECORDING_STAGE_TAKE;
    }

    @Override
    public void onGlobalLayout() {
        if (isPreviewSizeDone) {
            return;
        }
        isPreviewSizeDone = true;
        width = textureView.getWidth();
        height = textureView.getHeight();
        textureView.setSurfaceTextureListener(this);
        onCameraReadListner.Ready();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            camera.setPreviewTexture(surface);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public interface OnCameraReadListner {
        void Ready();
    }

    public boolean isRecoding() {
        return (STAGE_TAKE == RECORDING_STAGE_TAKE);
    }

    public boolean isStop() {
        return (STAGE_TAKE == STOP_RECORDING_STAGE_TAKE);
    }
}
