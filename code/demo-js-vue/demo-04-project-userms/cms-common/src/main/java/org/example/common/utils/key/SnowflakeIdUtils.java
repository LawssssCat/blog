package org.example.common.utils.key;

/**
 * 基于分组和时间生成唯一组件
 */
public class SnowflakeIdUtils {
    private static SnowflakeIdWorker WORKER_ORDER = new SnowflakeIdWorker(1, 1);
    private static SnowflakeIdWorker WORKER_USER = new SnowflakeIdWorker(1, 2);

    /**
     * @return 用户编号
     */
    public static long nextUserId() {
        return WORKER_USER.nextId();
    }

    /**
     * @return 订单编号
     */
    public static long nextOrderId() {
        return WORKER_ORDER.nextId();
    }
}
