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
            //cam.release();
            isLightOn = false;
        }
    }
}
