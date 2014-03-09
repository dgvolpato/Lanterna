package com.example.app1;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private Boolean isLightOn = false;
    private Boolean turnTwinkleOn = false;
    private Integer twinkleDelay = 50; // 50 ms TODO decide if this will be a CONST

    Camera cam = Camera.open();
    Camera.Parameters p = cam.getParameters();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    //TODO deal with all states of the activity
    //TODO develop error handling
    //TODO improve layout
    //TODO find out why the flash turns off after some time using another app
    //TODO create a logo for the app
    //TODO create a fork with the flash bang button (twinkle on high frequency)
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
        cam.release();
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

        turnTwinkleOn = (turnTwinkleOn) ? false : true;

        Context context = this;
        context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        //call background task (need to free UI!)



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



}