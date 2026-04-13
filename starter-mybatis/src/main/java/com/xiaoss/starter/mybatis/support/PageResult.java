package com.xiaoss.starter.mybatis.support;

import java.util.List;

public record PageResult<T>(List<T> records, long total, int pageNum, int pageSize) {
}
