import 'package:flutter_test/flutter_test.dart';
import 'package:gamepads_android/gamepads_android.dart';
import 'package:gamepads_android/gamepads_android_platform_interface.dart';
import 'package:gamepads_android/gamepads_android_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockGamepadsAndroidPlatform
    with MockPlatformInterfaceMixin
    implements GamepadsAndroidPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final GamepadsAndroidPlatform initialPlatform = GamepadsAndroidPlatform.instance;

  test('$MethodChannelGamepadsAndroid is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelGamepadsAndroid>());
  });

  test('getPlatformVersion', () async {
    GamepadsAndroid gamepadsAndroidPlugin = GamepadsAndroid();
    MockGamepadsAndroidPlatform fakePlatform = MockGamepadsAndroidPlatform();
    GamepadsAndroidPlatform.instance = fakePlatform;

    expect(await gamepadsAndroidPlugin.getPlatformVersion(), '42');
  });
}
