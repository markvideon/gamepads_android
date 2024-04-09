import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'gamepads_android_platform_interface.dart';

/// An implementation of [GamepadsAndroidPlatform] that uses method channels.
class MethodChannelGamepadsAndroid extends GamepadsAndroidPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('gamepads_android');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
