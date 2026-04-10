package com.xiaoss.starter.web.support.base;

import com.xiaoss.starter.web.support.exception.ParamIsNullException;
import com.xiaoss.starter.web.support.exception.ParamTooLongException;

public class BaseAction extends Base {

    protected <T> void notEmpty(T data, String desc) {
        String errorMsg = desc;
        if (data == null) {
            throw new ParamIsNullException(errorMsg);
        }
        if (data instanceof String && "".equals(String.valueOf(data).trim())) {
            throw new ParamIsNullException(errorMsg);
        }
    }

    protected <T> void maxLen(T data, int maxLen, String desc) {
        if (data instanceof String text
            && !text.trim().isEmpty()
            && text.length() > maxLen) {
            String errorMsg = desc + " 不能超过 " + maxLen + " 个字符";
            throw new ParamTooLongException(errorMsg);
        }
    }

    protected <T> void maxLenAndNotEmpty(T data, int max, String desc) {
        notEmpty(data, desc);
        maxLen(data, max, desc);
    }
}
