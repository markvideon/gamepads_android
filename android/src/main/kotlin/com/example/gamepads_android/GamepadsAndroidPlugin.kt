package com.example.gamepads_android

import androidx.annotation.NonNull
import android.app.Activity
import android.content.Context
import android.hardware.input.InputManager
import android.util.Log
import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent

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
  private val gamepads = GamepadsListener()

  private fun listGamepads(): List<Map<String, String>>  {
    val results = mutableListOf<Map<String, String>>()
    gamepads.getDevices().forEach({
      results.add(mapOf(
        "id" to it.key.toString(),
        "name" to it.value.name
      ))
    })

    return results
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

  override fun onAttachedToActivity(activityPluginBinding: ActivityPluginBinding) {
    onAttachedToActivityShared(activityPluginBinding.activity)
  }

  fun onAttachedToActivityShared(activity: Activity) {
    val compatibleActivity = activity as GamepadsCompatibleActivity
    compatibleActivity.registerInputDeviceListener(gamepads, null)
    compatibleActivity.registerKeyEventHandler({ it: KeyEvent -> onKeyEvent(it) })
    compatibleActivity.registerMotionEventHandler({ it: MotionEvent -> onMotionEvent(it) })
  }

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "xyz.luan/gamepads")
    channel.setMethodCallHandler(this)
  }

  override fun onDetachedFromActivity() {
    // No-op
  }

  override fun onDetachedFromActivityForConfigChanges() {
    // No-op
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  private fun onKeyEvent(keyEvent: KeyEvent): Boolean {
    Log.i(TAG, "onKeyEvent")
    val arguments = mapOf(
      "gamepadId" to keyEvent.getDeviceId().toString(),
      "time" to keyEvent.getEventTime(),
      "type" to "button",
      "key" to KeyEvent.keyCodeToString(keyEvent.getKeyCode()),
      "value" to keyEvent.getAction().toDouble()
    )
    channel.invokeMethod("onGamepadEvent", arguments)
    return true
  }



  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "listGamepads") {
      result.success(listGamepads())
    } else {
      result.notImplemented()
    }
  }

  private fun onMotionEvent(motionEvent: MotionEvent): Boolean {
    // Y-value is inverted for consistency with other platforms.
    val yValue = motionEvent.getAxisValue(MotionEvent.AXIS_Y) * -1;

    val xArguments = mapOf(
      "gamepadId" to motionEvent.getDeviceId().toString(),
      "time" to motionEvent.getEventTime(),
      "type" to "analog",
      "key" to MotionEvent.axisToString(MotionEvent.AXIS_X),
      "value" to motionEvent.getAxisValue(MotionEvent.AXIS_X)
    )
    val yArguments = mapOf(
      "gamepadId" to motionEvent.getDeviceId().toString(),
      "time" to motionEvent.getEventTime(),
      "type" to "analog",
      "key" to MotionEvent.axisToString(MotionEvent.AXIS_Y),
      "value" to yValue
    )
    channel.invokeMethod("onGamepadEvent", xArguments)
    channel.invokeMethod("onGamepadEvent", yArguments)
    return true
  }

  override fun onReattachedToActivityForConfigChanges(activityPluginBinding: ActivityPluginBinding) {
    onAttachedToActivityShared(activityPluginBinding.activity)
  }
}
