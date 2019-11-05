package com.gradproject2019;

import org.junit.Assert;
import org.junit.Test;

import static com.gradproject2019.users.service.Validator.validate;

public class ValidatorTest {

    @Test
    public void shouldCheckLength() {
        Assert.assertFalse(validate("1@Aa"));
        Assert.assertTrue(validate("1@Password"));
        Assert.assertFalse(validate("1@Aalonglonglonglong"));
    }

    @Test
    public void shouldCheckContainsNumber() {
        Assert.assertFalse(validate("@Password"));
        Assert.assertTrue(validate("1@Password"));
        Assert.assertTrue(validate("1@Password2"));
    }

    @Test
    public void shouldCheckContainsCapital() {
        Assert.assertFalse(validate("1@password"));
        Assert.assertTrue(validate("1@Password"));
        Assert.assertTrue(validate("1@PassworD"));
    }

    @Test
    public void shouldCheckContainsSpecialChar() {
        Assert.assertFalse(validate("1Password"));
        Assert.assertTrue(validate("1@Password"));
        Assert.assertTrue(validate("1@Pa$$word"));
    }
}
