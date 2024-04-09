
import 'gamepads_android_platform_interface.dart';

class GamepadsAndroid {
  Future<String?> getPlatformVersion() {
    return GamepadsAndroidPlatform.instance.getPlatformVersion();
  }
}
