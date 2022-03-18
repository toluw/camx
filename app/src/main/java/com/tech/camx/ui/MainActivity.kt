package com.tech.camx.ui

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tech.camx.R
import com.tech.camx.utils.MIN_OPENGL_VERSION
import com.tech.camx.utils.TAG
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkIsSupportedDeviceOrFinish(this)

        setContentView(R.layout.activity_main)
    }

    private fun checkIsSupportedDeviceOrFinish(activity: Activity){

        val openGlVersionString =
            (Objects.requireNonNull(activity.getSystemService(Context.ACTIVITY_SERVICE)) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion

        if (openGlVersionString.toDouble() < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later")
            Toast.makeText(activity, "Unsupported device", Toast.LENGTH_LONG)
                .show()
            activity.finish()

        }

    }
}