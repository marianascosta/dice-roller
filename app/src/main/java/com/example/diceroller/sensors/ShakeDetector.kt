package com.example.diceroller.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector(
    private val context: Context,
    private val shakeThreshold: Float = 10f,
    private val shakeTimeThreshold: Long = 400
) : SensorEventListener {

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var lastAcceleration: Float = 0f
    private var currentAcceleration: Float = 0f
    private var lastShakeTime: Long = 0

    var onShakeDetected: (() -> Unit)? = null

    init {
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val x = it.values[0]
                val y = it.values[1]
                val z = it.values[2]

                currentAcceleration = sqrt(x * x + y * y + z * z)

                if (currentAcceleration - lastAcceleration > shakeThreshold) {
                    val currentTime = System.currentTimeMillis()

                    if (currentTime - lastShakeTime > shakeTimeThreshold) {
                        lastShakeTime = currentTime
                        onShakeDetected?.invoke()
                    }
                }

                lastAcceleration = currentAcceleration
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    fun unregister() {
        sensorManager.unregisterListener(this)
    }

}