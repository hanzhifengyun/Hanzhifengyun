package com.hanzhifengyun.base.util.http.factory;


import com.hanzhifengyun.base.util.LogUtil;
import com.hanzhifengyun.base.util.RandomUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonFactory {
    private static final String TAG = "JsonFactory";

    /**
     * 获取ERP格式Json字符串
     *
     * @param method 接口方法名
     * @return json字符串
     */
    public static String getJson(String method) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        String id = RandomUtils.get1To100RandomInt() + "";
        try {
            jsonObject.put("id", id);
            jsonObject.put("method", method);
            jsonObject.put("params", jsonArray);
            LogUtil.i(TAG, jsonObject.toString());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取ERP格式Json字符串
     *
     * @param method 接口方法名
     * @param params 参数
     * @return json字符串
     */
    public static String getJson(String method, Object... params) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        String id = RandomUtils.get1To100RandomInt() + "";
        try {
            jsonObject.put("id", id);
            jsonObject.put("method", method);

            if (params != null) {
                for (Object param : params) {
                    jsonArray.put(param);
                }
                jsonObject.put("params", jsonArray);
            }
            LogUtil.i(TAG, jsonObject.toString());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 获取ERP格式Json字符串
     *
     * @param method 接口方法名
     * @param json   参数
     * @return json字符串
     */
    public static String getJson(String method, JSONObject json) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        String id = RandomUtils.get1To100RandomInt() + "";
        try {
            jsonObject.put("id", id);
            jsonObject.put("method", method);

            if (json != null) {
                jsonArray.put(json);
                jsonObject.put("params", jsonArray);
            }
            LogUtil.i(TAG, jsonObject.toString());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }


}
