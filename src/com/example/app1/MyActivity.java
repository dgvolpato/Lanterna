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
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            cam.setParameters(p);
            //p.getFlashMode();
            cam.startPreview();
            isLightOn = true;
        }
        else {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            cam.setParameters(p);
            cam.stopPreview();
            isLightOn = false;
        }
    }

    public void exit (View view) {
        cam.release();
        finish();
    }
}
