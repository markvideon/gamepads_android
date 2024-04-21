#include "paddleboat/paddleboat.h"
#include "jni.h"

JNIEXPORT void JNICALL destroy(JNIEnv *env) {
    return Paddleboat_destroy(env);
}

JNIEXPORT int32_t JNICALL init(JNIEnv *env, jobject jcontext) {
    return Paddleboat_init(env, jcontext);
}