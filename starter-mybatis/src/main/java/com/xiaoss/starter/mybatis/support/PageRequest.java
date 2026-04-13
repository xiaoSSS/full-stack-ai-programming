package com.xiaoss.starter.mybatis.support;

public record PageRequest(int pageNum, int pageSize) {

    public int offset() {
        return (Math.max(pageNum, 1) - 1) * Math.max(pageSize, 1);
    }
}
