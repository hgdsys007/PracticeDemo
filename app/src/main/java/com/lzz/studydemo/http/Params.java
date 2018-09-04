package com.lzz.studydemo.http;


import java.util.HashMap;
import java.util.Map;

public class Params {

    /**
     * 公共参数存放统一处理
     *
     * @param map
     * @return
     */
    private Map<String, String> common(Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }

        return map;
    }

    public Map<String, String> common() {
        Map<String, String> map = new HashMap<>();
        return common(map);
    }

    /**
     * 用法例子
     * @param code
     * @return
     */
    public Map<String, String> getTest(int code) {
        Map<String, String> map = new HashMap<>();
        map.put("getTest", code + "");
        return common(map);
    }

}