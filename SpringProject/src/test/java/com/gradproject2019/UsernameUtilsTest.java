package com.gradproject2019;

import com.gradproject2019.utils.UsernameUtils;
import org.junit.Assert;
import org.junit.Test;

public class UsernameUtilsTest {

    @Test
    public void shouldCheckNoSpaces() {
        Assert.assertFalse(UsernameUtils.validate("Sophia Grace"));
        Assert.assertTrue(UsernameUtils.validate("SophiaGrace"));
    }
}
