package org.example.common.utils.key;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于时间生成唯一ID
 *
 * 参考：
 * https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Framework/Generic/src/main/java/ghidra/util/UniversalIdGenerator.java
 */
@Slf4j
@ToString(of = {"sessionID", "baseTime", "instanceCount"})
public class UniversalIdUtils {
    private long baseTime;
    private long instanceCount = Integer.MAX_VALUE; // set max, so that getNextID will trigger new time
    private long sessionID;
    private long idBase; // combination of baseTime and uniqueID

    UniversalIdUtils() {
        this.sessionID = (timeGen() >> 4) & 0xffff;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }

    private long getNewBaseTime() {
        long newTime = timeGen();
        if (newTime <= baseTime) {
            // 处理时间冲突
            newTime = baseTime + 1;
        }
        return newTime;
    }

    /**
     * @return 0 - 0000000 00000000 00000000 00000000 00000000 000 - 00000 00000000 000 - 00000
     */
    public synchronized long getNextID() {
        if (instanceCount >= 32) {
            baseTime = getNewBaseTime();
            idBase = (baseTime << 21) | (sessionID) << 5;
            instanceCount = 0;
        }
        return idBase + (instanceCount++);
    }

    public static void main(String[] args) {
        UniversalIdUtils gen1 = new UniversalIdUtils();
        UniversalIdUtils gen2 = new UniversalIdUtils();
        for (int i = 0; i < 500; i++) {
            log.info("gen1={}, id={}, next={}", gen1, gen1.getNextID(), gen1.getNextID());
            log.info("gen2={}, id={}, next={}", gen2, gen2.getNextID(), gen2.getNextID());
        }
    }
}
