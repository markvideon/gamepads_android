# gamepads_android

Note: This project will be migrated into the [gamepads monorepo](https://github.com/flame-engine/gamepads). 

## Getting Started

This plugin functions by forwarding input events from your application's Acitivty. Therefore you will need to modify your activity to ensure this happens. Your activity should look something like...

```
package [YOUR_PACKAGE_NAME]

import android.hardware.input.InputManager
import android.os.Handler
import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent
import io.flutter.embedding.android.FlutterActivity
import dev.markvideon.gamepads_android.GamepadsCompatibleActivity

class MainActivity: FlutterActivity(), GamepadsCompatibleActivity {
    var keyListener: ((KeyEvent) -> Boolean)? = null
    var motionListener: ((MotionEvent) -> Boolean)? = null

    override fun dispatchGenericMotionEvent(motionEvent: MotionEvent): Boolean {
        motionListener?.invoke(motionEvent)
        return true
    }

    override fun dispatchKeyEvent(keyEvent: KeyEvent): Boolean {
        keyListener?.invoke(keyEvent)
        return true
    }

    override fun isGamepadsInputDevice(device: InputDevice): Boolean {
        return device.sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD
            || device.sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK
    }

    override fun registerInputDeviceListener(listener: InputManager.InputDeviceListener, handler: Handler?) {
        val inputManager = getSystemService(INPUT_SERVICE) as InputManager
        inputManager.registerInputDeviceListener(listener, null)
    }

    override fun registerKeyEventHandler(handler: (KeyEvent) -> Boolean) {
        keyListener = handler
    }

    override fun registerMotionEventHandler(handler: (MotionEvent) -> Boolean) {
        motionListener = handler
    }
}
```
