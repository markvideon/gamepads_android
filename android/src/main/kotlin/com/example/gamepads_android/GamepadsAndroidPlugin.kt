package com.example.gamepads_android

import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** GamepadsAndroidPlugin */
// Get Context: FlutterPlugin.FlutterPluginBinding.getApplicationContext();
class GamepadsAndroidPlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var channel : MethodChannel

  companion object {
    init {
        java.lang.System.loadLibrary("c++_shared")
        java.lang.System.loadLibrary("gamepads_android")
    }
  }

  external fun destroy();
  external fun init(): Int;

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
  external fun setControllerStatusCallback();
  external fun setControllerVibrationData();
  external fun setMotionDataCallback();
  external fun setMotionDataCallbackWithIntegratedFlags();
  external fun setMouseStatusCallback();
  external fun setPhysicalKeyboardStatusCallback();
  external fun update();
  */

  fun listGamepads(): List<Map<String, String>>  {
    return mockListGamepads()
  }

  fun mockListGamepads() : List<Map<String, String>> {
    return listOf(
        mapOf("id" to "mockId", "name" to "mockName")
    );
  }

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "xyz.luan/gamepads")
    channel.setMethodCallHandler(this)
    init()
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    destroy()
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
