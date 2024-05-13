package com.example.gamepads_android

import androidx.annotation.NonNull
import android.app.Activity
import android.util.Log
import android.view.InputDevice

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread


class GamepadsAndroidPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  private lateinit var channel : MethodChannel
  private val TAG = "GamepadsAndroidPlugin"

  private lateinit var thread: Thread

  private fun listGamepads(): List<Map<String, String>>  {
    return mockListGamepads()
  }

  private fun mockListGamepads() : List<Map<String, String>> {
    return listOf(
        mapOf("id" to "mockId", "name" to "mockName")
    );
  }

  private fun mockGamepadEvent() : Map<String, Any> {
    return mapOf(
            "gamepadId" to "mockId",
            "time" to 0,
            "type" to "analog",
            "key" to "mockKey",
            "value" to 0.0
    )
  }

  // https://developer.android.com/develop/ui/views/touch-and-input/game-controllers/controller-input#input
  fun getGameControllerIds(): List<Int> {
    val gameControllerDeviceIds = mutableListOf<Int>()
    val deviceIds = InputDevice.getDeviceIds()
    deviceIds.forEach { deviceId ->
      InputDevice.getDevice(deviceId).apply {
        if (this != null) {
          // Verify that the device has gamepad buttons, control sticks, or both.
          if (sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD
                  || sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK) {
            // This device is a game controller. Store its device ID.
            gameControllerDeviceIds
                    .takeIf { !it.contains(deviceId) }
                    ?.add(deviceId)
          }
        }
      }
    }
    return gameControllerDeviceIds
  }

  override fun onAttachedToActivity(activityPluginBinding: ActivityPluginBinding) {
    onAttachedToActivityShared(activityPluginBinding.activity)
  }

  fun onAttachedToActivityShared(activity: Activity) {

  }

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "xyz.luan/gamepads")
    channel.setMethodCallHandler(this)
  }

  override fun onDetachedFromActivity() {
    onDetachedFromActivityShared()
  }

  override fun onDetachedFromActivityForConfigChanges() {
    onDetachedFromActivityShared()
  }

  fun onDetachedFromActivityShared() {

  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "listGamepads") {
      result.success(listGamepads())
    } else {
      result.notImplemented()
    }
  }

  override fun onReattachedToActivityForConfigChanges(activityPluginBinding: ActivityPluginBinding) {
    onAttachedToActivityShared(activityPluginBinding.activity)
  }
}
