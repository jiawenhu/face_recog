package com.android.opencvdemo3.face;

import com.android.opencvdemo3.util.Base64Util;
import com.android.opencvdemo3.util.FileUtil;
import com.android.opencvdemo3.util.HttpUtil;

import java.net.URLEncoder;

/**
 * 人脸注册类
 */

public class faceRegister {
    // 注册新人脸的函数
    public static String register(String facePath, String faceId, String userInfo) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";

        try {
            // 文件路径
            byte[] imgData = FileUtil.readFileByBytes(facePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "uid=" + faceId + "&user_info=" + userInfo + "&group_id=" + "face_recog_group" + "&images=" + imgParam;
            String accessToken = "24.0afcd23f0198e23e9b09aacde20d6eee.2592000.1528029961.282335-11121511";
            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*public static void main(String[] args) {
        faceRegister.register("D:\\Work\\OpencvDemo3\\app\\src\\main\\res\\drawable\\face3.jpg", "1", "test");
        // String log_id = parseJSON(result);
        // String logId = Long.toString(log_id);
        // System.out.println(log_id);
    }*/

    /*private static String parseJSON(String result) {
        String re = null;
        try {
            JSONObject jo = new JSONObject(result);
            re = jo.getString("log_id");
            // String err = jo.getString("error_code");
            System.out.println("注册码为：" + re);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return re;
    }*/
}



