package org.example.common.exception.user;

/**
 * 用户不存在异常类
 */
public class UserNotExistsException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super("user.exists.not", null);
    }
}