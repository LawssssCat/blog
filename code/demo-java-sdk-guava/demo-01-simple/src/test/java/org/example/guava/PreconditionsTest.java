package org.example.guava;

import com.google.common.base.Preconditions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.Objects;

/**
 * 条件检查，fail fast
 */
public class PreconditionsTest {
    @DisplayName("Preconditions.checkNotNull")
    @Test
    void testCheckNotNull() {
        Assertions.assertThrowsExactly(NullPointerException.class, () -> Preconditions.checkNotNull(null, "should not null"));
        Assertions.assertThrowsExactly(NullPointerException.class, () -> Objects.requireNonNull(null, "should not null"));
    }

    @DisplayName("Preconditions.checkArgument")
    @Test
    void testCheckNotEmpty() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> Preconditions.checkArgument(StringUtils.isNotBlank(""), "should not empty"));
    }
}
