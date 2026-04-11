package com.xiaoss.starter.web.support.base;

import com.xiaoss.starter.web.support.exception.NullException;
import com.xiaoss.starter.web.support.exception.ParamTooLongException;
import java.util.Collection;
import java.util.Map;

public class BaseService extends Base {

    protected <T> void assertNotNull(T data, String desc) {
        if (data == null) {
            throw new NullException(desc);
        }
    }

    protected <T> void assertNotEmpty(T data, String desc) {
        assertNotNull(data, desc);
        if (data instanceof String text && text.trim().isEmpty()) {
            throw new NullException(desc);
        }
        if (data instanceof Collection<?> collection && collection.isEmpty()) {
            throw new NullException(desc);
        }
        if (data instanceof Map<?, ?> map && map.isEmpty()) {
            throw new NullException(desc);
        }
    }

    protected <T> void assertMaxLen(T data, int maxLen, String desc) {
        if (data instanceof String text
            && !text.trim().isEmpty()
            && text.length() > maxLen) {
            String errorMsg = desc + " 不能超过 " + maxLen + " 个字符";
            throw new ParamTooLongException(errorMsg);
        }
    }

    protected <T> void assertMaxLenAndNotEmpty(T data, int max, String desc) {
        assertNotEmpty(data, desc);
        assertMaxLen(data, max, desc);
    }

    protected <T> boolean isNull(T data) {
        return data == null;
    }

    protected <T> boolean notNull(T data) {
        return !isNull(data);
    }

    protected <T> boolean isEmpty(T data) {
        if (data == null) {
            return true;
        }
        if (data instanceof String text) {
            return text.trim().isEmpty();
        }
        return false;
    }

    protected <T> boolean notEmpty(T data) {
        return !isEmpty(data);
    }
}
