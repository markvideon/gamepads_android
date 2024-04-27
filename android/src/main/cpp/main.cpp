#include "paddleboat.h"
#include "jni.h"
#include <android/log.h>

extern "C" {
    JNIEXPORT jint JNICALL Java_com_example_gamepads_1android_GamepadsAndroidPlugin_init
            (JNIEnv * env, jobject plugin, jobject activity) {
        __android_log_print(ANDROID_LOG_INFO, "JNI_GamepadsAndroidPlugin", "Initialising Paddleboat %d.%d.%d / %d", PADDLEBOAT_MAJOR_VERSION, PADDLEBOAT_MINOR_VERSION, PADDLEBOAT_BUGFIX_VERSION, PADDLEBOAT_PACKED_VERSION);
        return Paddleboat_init(env, activity);
    }

    JNIEXPORT jboolean JNICALL Java_com_example_gamepads_1android_GamepadsAndroidPlugin_isInitialised
            (JNIEnv * env, jobject plugin) {
        return Paddleboat_isInitialized();
    }

    JNIEXPORT void JNICALL Java_com_example_gamepads_1android_GamepadsAndroidPlugin_destroy
    (JNIEnv * env, jobject plugin) {
        if (Paddleboat_isInitialized()) {
            Paddleboat_destroy(env);
            __android_log_print(ANDROID_LOG_INFO, "JNI_GamepadsAndroidPlugin", "Paddleboat destroyed");
        } else {
            __android_log_print(ANDROID_LOG_INFO, "JNI_GamepadsAndroidPlugin", "Paddleboat was not destroyed, was not initialised.");
        }
    }

    JNIEXPORT void JNICALL Java_com_example_gamepads_1android_GamepadsAndroidPlugin_update
    (JNIEnv * env, jobject plugin) {
        Paddleboat_update(env);
    }
}
