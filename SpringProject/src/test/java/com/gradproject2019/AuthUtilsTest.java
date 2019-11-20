package com.gradproject2019;

import com.gradproject2019.users.service.UserServiceImpl;
import com.gradproject2019.utils.AuthUtils;
import org.junit.Assert;
import org.junit.Test;

public class AuthUtilsTest {

    @Test
    public void shouldCheckPasswordIsValidAgainstRegex() {
        Assert.assertFalse(AuthUtils.validate("1@Aa", UserServiceImpl.PASSWORD_PATTERN));
        Assert.assertFalse(AuthUtils.validate("@Password", UserServiceImpl.PASSWORD_PATTERN));
        Assert.assertFalse(AuthUtils.validate("1@password", UserServiceImpl.PASSWORD_PATTERN));
        Assert.assertFalse(AuthUtils.validate("1Password", UserServiceImpl.PASSWORD_PATTERN));

        Assert.assertTrue(AuthUtils.validate("1@Password", UserServiceImpl.PASSWORD_PATTERN));

    }

    @Test
    public void shouldCheckEmailIsValidAgainstRegex () {
        Assert.assertTrue(AuthUtils.validate("Gracesophia!1@gmail.com", UserServiceImpl.EMAIL_PATTERN));

        Assert.assertFalse(AuthUtils.validate("gracesophiagmail.com", UserServiceImpl.EMAIL_PATTERN));
        Assert.assertFalse(AuthUtils.validate("gracesophia@gmailcom", UserServiceImpl.EMAIL_PATTERN));
        Assert.assertFalse(AuthUtils.validate("gracesophia @gmailcom", UserServiceImpl.EMAIL_PATTERN));
    }

    @Test
    public void shouldCheckUsernameNoSpaces() {
        Assert.assertFalse(AuthUtils.validate("Sophia Grace", UserServiceImpl.USERNAME_PATTERN));
        Assert.assertTrue(AuthUtils.validate("SophiaGrace", UserServiceImpl.USERNAME_PATTERN));
    }

    @Test
    public void shouldCheckPasswordMatchesHashedPassword() {
        //given
        String password = "password123";

        //when
        String hashedPassword = AuthUtils.hash(password);

        //then
        Assert.assertTrue(AuthUtils.verifyHash(password, hashedPassword));
    }

    @Test
    public void shouldCheckPasswordDoesNotMatchHashedPassword() {
        //given
        String password = "password123";

        //when
        String hashedPassword = AuthUtils.hash(password);

        //then
        Assert.assertFalse(AuthUtils.verifyHash("WrongPassword", hashedPassword));
    }
}