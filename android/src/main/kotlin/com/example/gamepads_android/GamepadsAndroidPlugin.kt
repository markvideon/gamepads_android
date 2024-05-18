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
import kotlin.concurrent.thread

class GamepadsAndroidPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  private lateinit var channel : MethodChannel
  private val TAG = "GamepadsAndroidPlugin"
  private val gamepads = ConnectionListener()
  private val events = EventListener()

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

  // FlutterPlugin
  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    Log.i(TAG, "onAttachedToEngine")
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "xyz.luan/gamepads")
    channel.setMethodCallHandler(this)
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

  // Activity Aware
  override fun onAttachedToActivity(activityPluginBinding: ActivityPluginBinding) {
    onAttachedToActivityShared(activityPluginBinding.activity)
  }

  fun onAttachedToActivityShared(activity: Activity) {
    Log.i(TAG, "onAttachedToActivityShared")
    val compatibleActivity = activity as GamepadsCompatibleActivity
    compatibleActivity.registerInputDeviceListener(gamepads, null)
    compatibleActivity.registerKeyEventHandler({ it: KeyEvent -> events.onKeyEvent(it, channel) })
    compatibleActivity.registerMotionEventHandler({ it: MotionEvent -> events.onMotionEvent(it, channel) })
  }

  override fun onDetachedFromActivity() {
    // No-op
  }

  override fun onDetachedFromActivityForConfigChanges() {
    // No-op
  }

  override fun onReattachedToActivityForConfigChanges(activityPluginBinding: ActivityPluginBinding) {
    onAttachedToActivityShared(activityPluginBinding.activity)
  }
}
