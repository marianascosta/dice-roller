package com.example.diceroller.sensors

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log

class SensorActivity(context: Context) : Activity(), SensorEventListener {

    private val mSensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val mAccelerometer: Sensor? = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//    private val mAccelerometer: Sensor? = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    private val TAG = "SensorActivity"

    // Lambda function to send sensor data updates
    var onSensorDataChanged: ((x: Float, y: Float, z: Float) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Optionally set a layout if you have one
        // setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        mAccelerometer?.let {
            mSensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes here if needed
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Log the accelerometer values
            Log.d(TAG, "Accelerometer values: x=$x, y=$y, z=$z")

            // Call the lambda function with updated sensor values
            onSensorDataChanged?.invoke(x, y, z)
        }
    }

    fun registerListener() {
        mAccelerometer?.let {
            mSensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregisterListener() {
        mSensorManager.unregisterListener(this)
    }
}