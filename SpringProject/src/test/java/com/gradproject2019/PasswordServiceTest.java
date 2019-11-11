package com.gradproject2019;

import com.gradproject2019.users.service.PasswordService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PasswordServiceTest {

    @InjectMocks
    private PasswordService passwordService;

    @Test
    public void shouldHashPassword() {
        System.out.println(passwordService.hash("password"));
    }

    @Test
    public void shouldCheckLength() {
        Assert.assertFalse(passwordService.validate("1@Aa"));
        Assert.assertTrue(passwordService.validate("1@Password"));
        Assert.assertFalse(passwordService.validate("1@Aalonglonglonglong"));
    }

    @Test
    public void shouldCheckContainsNumber() {
        Assert.assertFalse(passwordService.validate("@Password"));
        Assert.assertTrue(passwordService.validate("1@Password"));
        Assert.assertTrue(passwordService.validate("1@Password2"));
    }

    @Test
    public void shouldCheckContainsCapital() {
        Assert.assertFalse(passwordService.validate("1@password"));
        Assert.assertTrue(passwordService.validate("1@Password"));
        Assert.assertTrue(passwordService.validate("1@PassworD"));
    }

    @Test
    public void shouldCheckContainsSpecialChar() {
        Assert.assertFalse(passwordService.validate("1Password"));
        Assert.assertTrue(passwordService.validate("1@Password"));
        Assert.assertTrue(passwordService.validate("1@Pa$$word"));
    }
}
