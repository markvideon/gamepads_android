#include "paddleboat/paddleboat.h"
#include "jni.h"

JNIEXPORT void JNICALL Java_com_example_gamepads_1android_GamepadsAndroidPlugin_destroy(JNIEnv *env, jobject jcontext) {
    return Paddleboat_destroy(env);
}

JNIEXPORT jint JNICALL Java_com_example_gamepads_1android_GamepadsAndroidPlugin_init(JNIEnv *env, jobject jcontext) {
    return Paddleboat_init(env, jcontext);
}