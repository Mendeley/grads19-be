package com.gradproject2019;

import com.gradproject2019.utils.PasswordUtils;
import org.junit.Assert;
import org.junit.Test;

public class PasswordUtilsTest {

    @Test
    public void shouldCheckLength() {
        Assert.assertFalse(PasswordUtils.validate("1@Aa"));
        Assert.assertTrue(PasswordUtils.validate("1@Password"));
        Assert.assertFalse(PasswordUtils.validate("1@Aalonglonglonglong"));
    }

    @Test
    public void shouldCheckContainsNumber() {
        Assert.assertFalse(PasswordUtils.validate("@Password"));
        Assert.assertTrue(PasswordUtils.validate("1@Password"));
        Assert.assertTrue(PasswordUtils.validate("1@Password2"));
    }

    @Test
    public void shouldCheckContainsCapital() {
        Assert.assertFalse(PasswordUtils.validate("1@password"));
        Assert.assertTrue(PasswordUtils.validate("1@Password"));
        Assert.assertTrue(PasswordUtils.validate("1@PassworD"));
    }

    @Test
    public void shouldCheckContainsSpecialChar() {
        Assert.assertFalse(PasswordUtils.validate("1Password"));
        Assert.assertTrue(PasswordUtils.validate("1@Password"));
        Assert.assertTrue(PasswordUtils.validate("1@Pa$$word"));
    }
}

