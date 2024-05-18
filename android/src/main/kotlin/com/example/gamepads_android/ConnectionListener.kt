package com.example.gamepads_android

import android.hardware.input.InputManager
import android.util.Log
import android.view.InputDevice

class ConnectionListener: InputManager.InputDeviceListener {
    private val devicesLookup: MutableMap<Int, InputDevice> = mutableMapOf()

    private val TAG = "ConnectionListener"

    init {
        getGameControllerIds()
    }

    fun getDevices(): Map<Int, InputDevice> {
        return devicesLookup.toMap()
    }

    // https://developer.android.com/develop/ui/views/touch-and-input/game-controllers/controller-input#input
    private fun getGameControllerIds() {
        val gameControllerDeviceIds = mutableListOf<Int>()
        val deviceIds = InputDevice.getDeviceIds()
        deviceIds.forEach { deviceId ->
            InputDevice.getDevice(deviceId).apply {
                if (this != null) {
                    // Verify that the device has gamepad buttons, control sticks, or both.
                    if (sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD
                        || sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK) {
                        // This device is a game controller. Store its device ID.
                        devicesLookup[deviceId] = this
                    }
                }
            }
        }
    }

    override fun onInputDeviceAdded(deviceId: Int) {
        Log.i(TAG, "onInputDeviceAdded id: $deviceId")
        val device: InputDevice? = InputDevice.getDevice(deviceId)
        Log.i(TAG, "onInputDeviceAdded controller number: ${device?.controllerNumber}")
        Log.i(TAG, "onInputDeviceAdded name: ${device?.name}")
        if (device != null && (device.sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD)) {
            devicesLookup[deviceId] = device
        }
    }

    override fun onInputDeviceRemoved(deviceId: Int) {
        Log.i(TAG, "onInputDeviceRemoved id: $deviceId")
        val device: InputDevice? = InputDevice.getDevice(deviceId)
        Log.i(TAG, "onInputDeviceRemoved controller number: ${device?.controllerNumber}")
        Log.i(TAG, "onInputDeviceRemoved name: ${device?.name}")
        devicesLookup.remove(deviceId)
    }

    override fun onInputDeviceChanged(deviceId: Int) {
        Log.i(TAG, "onInputDeviceChanged id: $deviceId")
        val device: InputDevice? = InputDevice.getDevice(deviceId)
        Log.i(TAG, "onInputDeviceChanged controller number: ${device?.controllerNumber}")
        Log.i(TAG, "onInputDeviceChanged name: ${device?.name}")
        if (device != null && (device.sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD)) {
            devicesLookup[deviceId] = device
        } else {
            devicesLookup.remove(deviceId)
        }
    }
}