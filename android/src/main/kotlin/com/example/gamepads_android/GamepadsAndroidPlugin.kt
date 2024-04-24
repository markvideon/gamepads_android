package com.example.gamepads_android

import androidx.annotation.NonNull
import androidx.annotation.Keep
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import android.util.Log

/** GamepadsAndroidPlugin */
// Get Context: FlutterPlugin.FlutterPluginBinding.getApplicationContext();
@Keep
class GamepadsAndroidPlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var channel : MethodChannel

  init {
      Log.i("GamepadsAndroidPlugin", "before load library")
      System.loadLibrary("c++_shared")
      System.loadLibrary("gamepads_android")
      Log.i("GamepadsAndroidPlugin", "after load library")
  }

  external fun destroy();
  external fun init();

  external fun setControllerStatusCallback();
  external fun setMotionDataCallback();
  external fun update();

  /*
  external fun addControllerRemapData(const Int remapTableEntryCount);
  external fun addControllerRemapDataFromFd();
  external fun addControllerRemapDataFromFileBuffer();
  external fun getActiveAxisMask();
  external fun getBackButtonConsumed();
  external fun getControllerData();
  external fun getControllerInfo();
  external fun getControllerName();
  external fun getControllerStatus();
  external fun getIntegratedMotionSensorFlags();
  external fun getLastKeycode();
  external fun getMouseData();
  external fun getMouseStatus();
  external fun getPhysicalKeyboardStatus();
  external fun isInitialized();
  external fun onStart();
  external fun onStop();
  external fun processInputEvent();
  external fun setBackButtonConsumed();
  external fun setControllerLight();
  external fun setControllerVibrationData();
  external fun setMotionDataCallbackWithIntegratedFlags();
  external fun setMouseStatusCallback();
  external fun setPhysicalKeyboardStatusCallback();
  */

  fun listGamepads(): List<Map<String, String>>  {
    Log.i("GamepadsAndroidPlugin", "listGamepads()")
    return mockListGamepads()
  }

  fun mockListGamepads() : List<Map<String, String>> {
    Log.i("GamepadsAndroidPlugin", "mockListGamepads()")
    return listOf(
        mapOf("id" to "mockId", "name" to "mockName")
    );
  }

  fun mockGamepadEvent() : Map<String, Any> {
    Log.i("GamepadsAndroidPlugin", "mockGamepadEvent()")
    return mapOf(
            "gamepadId" to "mockId",
            "time" to 0,
            "type" to "analog",
            "key" to "mockKey",
            "value" to 0.0
    )
  }

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "xyz.luan/gamepads")
    channel.setMethodCallHandler(this)

    init()
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
}
