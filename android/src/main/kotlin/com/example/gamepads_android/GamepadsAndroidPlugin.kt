package com.example.gamepads_android

import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import android.util.Log
import android.app.Activity

class GamepadsAndroidPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  private lateinit var channel : MethodChannel
  private val TAG = "GamepadsAndroidPlugin"
  init {
      System.loadLibrary("c++_shared")
      System.loadLibrary("gamepads_android")
  }

  external fun destroy();
  external fun init(activity: Activity): Int;
  external fun getControllerData(); // Unimplemented
  external fun getControllerInfo(); // Unimplemented
  external fun getControllerName(); // Unimplemented
  external fun getControllerStatus(); // Unimplemented
  external fun isInitialised(): Boolean;
  external fun onStart(); // Unimplemented
  external fun onStop(); // Unimplemented
  external fun update();

  fun listGamepads(): List<Map<String, String>>  {
    return mockListGamepads()
  }

  fun mockListGamepads() : List<Map<String, String>> {
    return listOf(
        mapOf("id" to "mockId", "name" to "mockName")
    );
  }

  fun mockGamepadEvent() : Map<String, Any> {
    return mapOf(
            "gamepadId" to "mockId",
            "time" to 0,
            "type" to "analog",
            "key" to "mockKey",
            "value" to 0.0
    )
  }

  override fun onAttachedToActivity(activityPluginBinding: ActivityPluginBinding) {
    val paddleboatInitResult = init(activityPluginBinding.activity)
    if (!isInitialised()) {
      Log.i(TAG, "Paddleboat failed to initialise. Error code: ${paddleboatInitResult}")
    }
  }

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "xyz.luan/gamepads")
    channel.setMethodCallHandler(this)
  }

  override fun onDetachedFromActivity() {
    destroy()
  }

  override fun onDetachedFromActivityForConfigChanges() {
    destroy()
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
    val paddleboatInitResult = init(activityPluginBinding.activity)
    if (!isInitialised()) {
      Log.i(TAG, "Paddleboat failed to initialise. Error code: ${paddleboatInitResult}")
    }
  }
}
