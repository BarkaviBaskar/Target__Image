package com.example.customdesign


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var cameraButton1: Button
    private lateinit var cameraButton2: Button
    private lateinit var cameraButton3: Button

    private lateinit var overlay: TargetOverlay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraButton1 = findViewById(R.id.cameraButton1)
        cameraButton2 = findViewById(R.id.cameraButton2)
        cameraButton3 = findViewById(R.id.cameraButton3)
        overlay = findViewById(R.id.overlay)

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels

        val centerX1 = width / 2f
        val centerY1 = height / 2f
        val centerX2 = width / 2f
        val centerY2 = height / 12f
        val centerX3 = width / 2f
        val centerY3 = height / 4f

        cameraButton1.setOnClickListener{
            overlay.clearShapes()
            overlay.addCircle(centerX3, centerY3,100f,430f,600f,150f )

        }
        cameraButton2.setOnClickListener{
            overlay.clearShapes()
            overlay.addRectangle(centerX2,centerY2,500f,700f,100f,150f)
        }
        cameraButton3.setOnClickListener{
            overlay.clearShapes()
            overlay.addSquare(centerX1,centerY1,200f)
        }
    }
}



