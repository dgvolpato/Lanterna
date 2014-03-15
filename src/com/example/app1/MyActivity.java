package com.example.app1;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private Boolean isLightOn = false;
    private Boolean turnTwinkleOn = false;
    private Integer twinkleDelay = 50; // 50 ms

    Camera cam = Camera.open();
    Camera.Parameters p = cam.getParameters();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    public void onDestroy(){
        Log.w(ACTIVITY_SERVICE, "App destroyed");
        super.onDestroy();
    }

    public void onPause() {
        turnTwinkleOn = false;
        isLightOn = false;

        releaseCamera();

        Log.w(ACTIVITY_SERVICE, "App paused");
        super.onPause();
    }

    private boolean safeCameraOpen(int id) {
        boolean qOpened = false;

        try {
            releaseCamera();
            cam = Camera.open(id);
            qOpened = (cam != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }

        return qOpened;
    }

    private void releaseCamera() {
        if (cam != null) {
            cam.release();
            cam = null;
        }
    }

    @Override
    protected void onStop() {
        Log.w(ACTIVITY_SERVICE, "App stopped");
        super.onStop();
    }


    //TODO deal with all states of the activity
    //TODO develop error handling
    //TODO improve layout
    //TODO about popup once the user taps the left button
    //TODO solve bug when flash or twinkle is on during the screen rotation

    public void myFlash (View view) {

        Context context = this;
        context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isLightOn) {
            turnFlashOn();
            isLightOn = true;
        }
        else {
            turnFlashOff();
            isLightOn = false;
        }
    }

    public void exit (View view) {

        turnTwinkleOn = false;

        releaseCamera();
        finish();
    }

    public void turnFlashOn () {
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(p);
        cam.startPreview();
    }

    public void turnFlashOff () {
        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        cam.setParameters(p);
        cam.stopPreview();
    }

    public void twinkle (View view) {

        turnTwinkleOn = !turnTwinkleOn;

        if (!turnTwinkleOn) {
            turnFlashOff();
            isLightOn = false;
        }

        Context context = this;
        context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        //call background task (need to free UI)

        Runnable runnable = new Runnable() {
            public void run() {
                while (turnTwinkleOn) {
                    if (!isLightOn) {
                        turnFlashOn();
                        isLightOn = true;
                    }
                    else {
                        turnFlashOff();
                        isLightOn = false;
                    }

                    try {
                        Thread.sleep(twinkleDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable).start();

    }



}