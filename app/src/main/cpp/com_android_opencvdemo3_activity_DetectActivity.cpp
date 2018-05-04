//
// Created by wenjia on 2018/4/23.
//

#include "com_android_opencvdemo3_activity_DetectActivity.h"
#include <android/bitmap.h>
#include <opencv2/opencv.hpp>

using namespace cv;

extern"C";

JNIEXPORT void JNICALL Java_com_android_opencvdemo3_activity_DetectActivity_faceDetect
        (JNIEnv *env, jobject thizz, jobject bitmap) {

    AndroidBitmapInfo info;
    void *pixels;

    // 加载Haar特征检测分类器
    // haarcascade_frontalface_alt.xml系OpenCV自带的分类器
    const char *pstrCascadeFileName = "D:\\Work\\opencv\\data\\haarcascades\\haarcascade_frontalface_alt.xml";
    CvHaarClassifierCascade *pHaarCascade = NULL;
    pHaarCascade = (CvHaarClassifierCascade*)cvLoad(pstrCascadeFileName);

    // 载入图像
    CV_Assert(AndroidBitmap_getInfo(env, bitmap, &info) >= 0);
    CV_Assert(info.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
              info.format == ANDROID_BITMAP_FORMAT_RGB_565);
    CV_Assert(AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0);
    CV_Assert(pixels);

    if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        Mat temp(info.height, info.width, CV_8UC4, pixels);
        Mat gray;
        cvtColor(temp, gray, COLOR_RGBA2GRAY);
        Canny(gray, gray, 3, 9, 3);
        cvtColor(gray, temp, COLOR_GRAY2RGBA);
    } else {
        Mat temp(info.height, info.width, CV_8UC2, pixels);
        Mat gray;
        cvtColor(temp, gray, COLOR_RGB2GRAY);
        Canny(gray, gray, 3, 9, 3);
        cvtColor(gray, temp, COLOR_GRAY2RGB);
    }
    AndroidBitmap_unlockPixels(env, bitmap);
}

