package usecase;

import org.assertj.core.api.Assertions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo01$JDKVersion {
    public void test(Integer lessVersion) {
        String version = System.getProperty("java.version");
        log.debug("java.version: {}", version);
        Assertions.assertThat(Integer.parseInt(version))
            .as("请选择JDK" + lessVersion + "及以上版本")
            .isGreaterThanOrEqualTo(lessVersion);
    }
}
