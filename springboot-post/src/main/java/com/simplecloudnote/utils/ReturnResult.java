package com.simplecloudnote.utils;

import cn.hutool.json.JSONUtil;
import java.util.HashMap;

/**
* @author 十玖八柒
* @date 2022
* @GitHub https://github.com/ooahz
* @desc 统一处理返回结果
*/
public class ReturnResult {
    /**
     *
     * @param result 结果
     * @param msg 信息
     * @return
     */
    public static String result(String result,String msg){
        HashMap<String, String> map = new HashMap<>();
        map.put("result",result);
        map.put("msg",msg);
        return JSONUtil.toJsonStr(map);
    }
}
