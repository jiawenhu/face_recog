package com.android.opencvdemo3.face;

import com.android.opencvdemo3.util.Base64Util;
import com.android.opencvdemo3.util.FileUtil;
import com.android.opencvdemo3.util.HttpUtil;

import java.net.URLEncoder;

/**
 * 人脸识别类
 */

public class faceRecognize {
    public static String identify() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/identify";
        try {
            // 本地文件路径
            String filePath = "D:\\Work\\OpencvDemo3\\app\\src\\main\\res\\drawable\\face.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String filePath2 = "D:\\Work\\OpencvDemo3\\app\\src\\main\\res\\drawable\\face3.jpg";
            byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
            String imgStr2 = Base64Util.encode(imgData2);
            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

            String param = "group_id=" + "face_recog_test1" + "&user_top_num=" + "1" + "&face_top_num=" + "1" + "&images=" + imgParam + "," + imgParam2;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.91848a788d48f447d15147aec770a628.2592000.1527929510.282335-11121511";

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        faceRecognize.identify();
    }
}
