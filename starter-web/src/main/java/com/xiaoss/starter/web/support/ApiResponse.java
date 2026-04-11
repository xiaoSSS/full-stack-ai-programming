package com.xiaoss.starter.web.support;

public record ApiResponse<T>(boolean success, String code, String message, T data, PageableInfo pageable) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "0", "OK", data, null);
    }

    public static <T> ApiResponse<T> success(T data, PageableInfo pageable) {
        return new ApiResponse<>(true, "0", "OK", data, pageable);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(false, code, message, null, null);
    }

    public record PageableInfo(long pageNum, long pageSize, long total, long totalPages) {

        public static PageableInfo of(long pageNum, long pageSize, long total) {
            long safePageSize = pageSize <= 0 ? 1 : pageSize;
            long pages = (total + safePageSize - 1) / safePageSize;
            return new PageableInfo(pageNum, pageSize, total, pages);
        }
    }
}
