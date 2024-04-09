import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'gamepads_android_method_channel.dart';

abstract class GamepadsAndroidPlatform extends PlatformInterface {
  /// Constructs a GamepadsAndroidPlatform.
  GamepadsAndroidPlatform() : super(token: _token);

  static final Object _token = Object();

  static GamepadsAndroidPlatform _instance = MethodChannelGamepadsAndroid();

  /// The default instance of [GamepadsAndroidPlatform] to use.
  ///
  /// Defaults to [MethodChannelGamepadsAndroid].
  static GamepadsAndroidPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [GamepadsAndroidPlatform] when
  /// they register themselves.
  static set instance(GamepadsAndroidPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
