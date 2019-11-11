package com.gradproject2019;

import com.gradproject2019.utils.PasswordUtils;
import org.junit.Assert;
import org.junit.Test;

public class PasswordUtilsTest {

    private PasswordUtils passwordUtils = new PasswordUtils();

    @Test
    public void shouldCheckLength() {
        Assert.assertFalse(passwordUtils.validate("1@Aa"));
        Assert.assertTrue(passwordUtils.validate("1@Password"));
        Assert.assertFalse(passwordUtils.validate("1@Aalonglonglonglong"));
    }

    @Test
    public void shouldCheckContainsNumber() {
        Assert.assertFalse(passwordUtils.validate("@Password"));
        Assert.assertTrue(passwordUtils.validate("1@Password"));
        Assert.assertTrue(passwordUtils.validate("1@Password2"));
    }

    @Test
    public void shouldCheckContainsCapital() {
        Assert.assertFalse(passwordUtils.validate("1@password"));
        Assert.assertTrue(passwordUtils.validate("1@Password"));
        Assert.assertTrue(passwordUtils.validate("1@PassworD"));
    }

    @Test
    public void shouldCheckContainsSpecialChar() {
        Assert.assertFalse(passwordUtils.validate("1Password"));
        Assert.assertTrue(passwordUtils.validate("1@Password"));
        Assert.assertTrue(passwordUtils.validate("1@Pa$$word"));
    }
}

