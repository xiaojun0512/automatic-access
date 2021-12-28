package com.cetc10.automaticaccess.util;

import lombok.Data;

@Data
public class ResultUtils {
    private int code = 200;
    private String msg = "操作成功";
    private Object data;

    public static ResultUtils success(Object data) {
        ResultUtils result = new ResultUtils();
        result.setData(data);
        return result;
    }

    public static ResultUtils error(String msg) {
        ResultUtils result = new ResultUtils();
        result.setCode(400);
        result.setMsg(msg);
        return result;
    }

}
