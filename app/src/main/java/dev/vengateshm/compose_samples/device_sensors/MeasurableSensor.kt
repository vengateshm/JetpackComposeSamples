package dev.vengateshm.compose_samples.device_sensors

abstract class MeasurableSensor(protected val sensorType: Int) {
    protected var onSensorValuesChanged: ((List<Float>) -> Unit)? = null
    abstract val doesSensorExist: Boolean
    abstract fun startListening()
    abstract fun stopListening()

    fun setOnSensorValuesChangedListener(listener: (List<Float>) -> Unit) {
        onSensorValuesChanged = listener
    }
}