package org.example.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    @Getter
    @Setter
    private Integer code;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
