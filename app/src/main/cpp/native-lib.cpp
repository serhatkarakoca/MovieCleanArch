#include <jni.h>
#include <string>


extern "C" JNIEXPORT jstring JNICALL
Java_com_karakoca_moviecleanarch_data_local_Keys_apiKey(JNIEnv *env, jobject thiz) {
    std::string key = "bc38e02d";
    return env->NewStringUTF(key.c_str());
}