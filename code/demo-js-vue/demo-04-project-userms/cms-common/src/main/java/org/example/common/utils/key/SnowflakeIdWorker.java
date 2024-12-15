package org.example.common.utils.key;

/**
 * <h2>雪花算法</h2>
 * tweeter的snowflake 移植到Java:
 * <ul>
 * <li>
 * id构成: 42位的时间前缀 + 10位的节点标识 + 12位的sequence避免并发的数字(12位不够用时强制得到新的时间前缀)
 * 
 * <pre>
 * 1bit，表示正负             41bit，时间戳            10bit，工作机器id   12bit，序列号
 * |                              |                        |              |
 * 0 - 00000000 00000000 00000000 00000000 00000000 0 - 00000000 00 - 00000000 0000
 * </pre>
 * 
 * </li>
 * <li>对系统时间的依赖性非常强，需关闭ntp的时间同步功能。当检测到ntp时间调整后，将会拒绝分配id</li>
 * </ul>
 */
public class SnowflakeIdWorker {
    private static final long SEQUENCE_MASK = 0b11111111_1111L;
    private final static long WORK_ID_MAX = 0b1111_1L;
    private final static long DATA_CENTER_MIN = 0b1111_1L;

    /**
     * 时间起始标记点，作为基准。
     * 一般取系统的最近时间，以增长雪花算法能表示的时间。
     */
    private final static long EPOCH = 1403854494756L;

    private final long workerId;

    private final long dataCenterId;

    /**
     * 上一次生成ID的时间戳
     */
    private long lastTimestamp = -1;

    /**
     * 毫秒内序列（0~4095）
     */
    private long sequence = 0L;

    public SnowflakeIdWorker(long workerId, long dataCenterId) {
        if (1 > workerId || workerId > WORK_ID_MAX) {
            throw new IllegalArgumentException(
                String.format("workId must in [%s,%s] but %s", 1, WORK_ID_MAX, workerId));
        }
        if (1 > dataCenterId || dataCenterId > DATA_CENTER_MIN) {
            throw new IllegalArgumentException(
                String.format("dataCenterId must in [%s,%s] but %s", 1, DATA_CENTER_MIN, dataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明胸痛时钟回退过，这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            /*
            TODO 处理时钟回拨问题
            1. 回拨时间短（e.g. <= 100ms） —— sleep
            1. 回拨时间较短（e.g. <= 1s） —— 序列自增
            1. 回拨时间较长（e.g. <= 5s） —— 报错，重试其他序列节点
            1. 回拨时间长（e.g. >5s） —— 报错，重试其他序列节点，当前序列节点下线！（日志告警，自动处理/运维介入）
             */
            throw new RuntimeException(
                String.format("Clock moved backwards. Refusing to generate id for %d milliseconds.", lastTimestamp));
        }

        if (lastTimestamp == timestamp) {
            // 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环); 对新的timestamp，sequence从0开始
            sequence = sequence + 1 & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一毫秒，获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 如果时间戳改变，毫秒内序列重置
            sequence = 0;
        }

        // 上次生成ID的时间戳
        lastTimestamp = timestamp;

        // 移位并通过或运算拼接得到64位的ID
        return (timestamp - EPOCH << 22) // 时间戳
            | (dataCenterId << 17) // 数据标识
            | (workerId << 12) // 机器标识
            | sequence;
    }

    /**
     * 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后
     *
     * @param lastTimestamp 上次生成毫秒
     * @return 下一毫秒
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * @return 获得系统当前毫秒数
     */
    private static long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);
        for (int i = 0; i < 10; i++) {
            long id = idWorker.nextId();
            System.out.println(id);
        }
    }
}
