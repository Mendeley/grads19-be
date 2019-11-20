package com.gradproject2019;

import com.gradproject2019.users.service.UserServiceImpl;
import com.gradproject2019.utils.AuthUtils;
import org.junit.Assert;
import org.junit.Test;

public class AuthUtilsTest {

    @Test
    public void shouldCheckLength() {
        Assert.assertFalse(AuthUtils.validate("1@Aa", UserServiceImpl.PASSWORD_PATTERN));
        Assert.assertTrue(AuthUtils.validate("1@Password", UserServiceImpl.PASSWORD_PATTERN));
        Assert.assertFalse(AuthUtils.validate("1@Aalonglonglonglong", UserServiceImpl.PASSWORD_PATTERN));
    }

    @Test
    public void shouldCheckContainsNumber() {
        Assert.assertFalse(AuthUtils.validate("@Password", UserServiceImpl.PASSWORD_PATTERN));
        Assert.assertTrue(AuthUtils.validate("1@Password", UserServiceImpl.PASSWORD_PATTERN));
        Assert.assertTrue(AuthUtils.validate("1@Password2", UserServiceImpl.PASSWORD_PATTERN));
    }

    @Test
    public void shouldCheckContainsCapital() {
        Assert.assertFalse(AuthUtils.validate("1@password", UserServiceImpl.PASSWORD_PATTERN));
        Assert.assertTrue(AuthUtils.validate("1@Password", UserServiceImpl.PASSWORD_PATTERN));
        Assert.assertTrue(AuthUtils.validate("1@PassworD", UserServiceImpl.PASSWORD_PATTERN));
    }

    @Test
    public void shouldCheckContainsSpecialChar() {
        Assert.assertFalse(AuthUtils.validate("1Password", UserServiceImpl.PASSWORD_PATTERN));
        Assert.assertTrue(AuthUtils.validate("1@Password", UserServiceImpl.PASSWORD_PATTERN));
        Assert.assertTrue(AuthUtils.validate("1@Pa$$word", UserServiceImpl.PASSWORD_PATTERN));
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

    @Test
    public void shouldAllowNumber() {
        Assert.assertTrue(AuthUtils.validate("grace123@gmail.com", UserServiceImpl.EMAIL_PATTERN));
        Assert.assertTrue(AuthUtils.validate("grace@gmail.com", UserServiceImpl.EMAIL_PATTERN));
    }


    @Test
    public void shouldAllowSpecialChar() {
        Assert.assertTrue(AuthUtils.validate("grace!_sophia$@gmail.com", UserServiceImpl.EMAIL_PATTERN));
        Assert.assertTrue(AuthUtils.validate("gracesophia@gmail.com", UserServiceImpl.EMAIL_PATTERN));
    }

    @Test
    public void shouldAllowCapitalLetter() {
        Assert.assertTrue(AuthUtils.validate("GraceSophia@gmail.com", UserServiceImpl.EMAIL_PATTERN));
        Assert.assertTrue(AuthUtils.validate("gracesophia@gmail.com", UserServiceImpl.EMAIL_PATTERN));
    }

    @Test
    public void shouldContainAt() {
        Assert.assertTrue(AuthUtils.validate("gracesophia@gmail.com", UserServiceImpl.EMAIL_PATTERN));
        Assert.assertFalse(AuthUtils.validate("gracesophiagmail.com", UserServiceImpl.EMAIL_PATTERN));
    }
    @Test
    public void shouldContainDotInDomain() {
        Assert.assertTrue(AuthUtils.validate("gracesophia@gmail.com", UserServiceImpl.EMAIL_PATTERN));
        Assert.assertFalse(AuthUtils.validate("gracesophia@gmailcom", UserServiceImpl.EMAIL_PATTERN));
    }

    @Test
    public void shouldCheckNoSpaces() {
        Assert.assertFalse(AuthUtils.validate("Sophia Grace", UserServiceImpl.USERNAME_PATTERN));
        Assert.assertTrue(AuthUtils.validate("SophiaGrace", UserServiceImpl.USERNAME_PATTERN));
    }
}