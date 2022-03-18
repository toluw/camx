package com.tech.camx.ui

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Material
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.ux.ArFragment
import com.tech.camx.R
import com.tech.camx.utils.MIN_OPENGL_VERSION
import com.tech.camx.utils.TAG
import java.util.*

class MainActivity : AppCompatActivity() {

    var  arFragment: ArFragment? = null
    var distanceTextView: TextView? = null

    private var cubeRenderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkIsSupportedDeviceOrFinish(this)

        setContentView(R.layout.activity_main)

        initViews()

        initModel();
    }

    private fun initViews() {
        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment?
        distanceTextView = findViewById(R.id.distanceTextView)
    }

    private fun initModel() {
        MaterialFactory.makeTransparentWithColor(this,com.google.ar.sceneform.rendering.Color(Color.BLUE))
            .thenAccept { material: Material? ->
                val vector3 = Vector3(0.05f, 0.01f, 0.01f)
                cubeRenderable = ShapeFactory.makeCube(vector3, Vector3.zero(), material)
                cubeRenderable?.isShadowCaster = false
                cubeRenderable?.isShadowReceiver = false
            }
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