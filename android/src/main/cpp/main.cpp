#include "paddleboat/paddleboat.h"
#include "jni.h"
#include <android/log.h>

extern "C" {
    JNIEXPORT void JNICALL Java_com_example_gamepads_1android_GamepadsAndroidPlugin_init
            (JNIEnv * env, jobject jcontext) {
        __android_log_print(ANDROID_LOG_INFO, "JNI_GamepadsAndroidPlugin", "Initialising Paddleboat %d.%d.%d / %d", PADDLEBOAT_MAJOR_VERSION, PADDLEBOAT_MINOR_VERSION, PADDLEBOAT_BUGFIX_VERSION, PADDLEBOAT_PACKED_VERSION);

        // init causes crash
        // __android_log_print(ANDROID_LOG_INFO, "JNI_GamepadsAndroidPlugin", "Paddleboat init result: %d", Paddleboat_init(env, jcontext));

        // destroy causes crash
        // __android_log_print(ANDROID_LOG_INFO, "JNI_GamepadsAndroidPlugin", "Paddleboat before destroy");
        // Paddleboat_destroy(env);
        // __android_log_print(ANDROID_LOG_INFO, "JNI_GamepadsAndroidPlugin", "Paddleboat after destroy");
    }

    JNIEXPORT void JNICALL Java_com_example_gamepads_1android_GamepadsAndroidPlugin_destroy
    (JNIEnv * env, jobject jcontext) {
        Paddleboat_destroy(env);
        __android_log_print(ANDROID_LOG_INFO, "JNI_GamepadsAndroidPlugin", "Paddleboat destroyed");
    }

    JNIEXPORT void JNICALL Java_com_example_gamepads_1android_GamepadsAndroidPlugin_setControllerStatusCallback
    (JNIEnv * env, jobject jcontext) {
        // ToDo:
    }

    JNIEXPORT void JNICALL Java_com_example_gamepads_1android_GamepadsAndroidPlugin_setMotionDataCallback
    (JNIEnv * env, jobject jcontext) {
        // ToDo:
    }

    JNIEXPORT void JNICALL Java_com_example_gamepads_1android_GamepadsAndroidPlugin_update
    (JNIEnv * env, jobject jcontext) {
        Paddleboat_update(env);
    }
}

