package com.gradproject2019;

import com.gradproject2019.users.service.UserServiceImpl;
import com.gradproject2019.utils.AuthUtils;
import org.junit.Assert;
import org.junit.Test;

public class AuthUtilsTest {

    private final String password = "password123";
    private final String hashedPassword = AuthUtils.hash(password);

    @Test
    public void shouldCheckPasswordIsValidAgainstRegex() {
        Assert.assertTrue(AuthUtils.validate("1@Password", UserServiceImpl.PASSWORD_VALIDATION_PATTERN));
    }

    @Test
    public void shouldCheckPasswordLengthGreaterThan8Characters() {
        Assert.assertFalse(AuthUtils.validate("1@Aaaaa", UserServiceImpl.PASSWORD_VALIDATION_PATTERN));
    }

    @Test
    public void shouldCheckPasswordIsLessThan16Characters() {
        Assert.assertFalse(AuthUtils.validate("1@Aaaaaaaaaaaaaaa", UserServiceImpl.PASSWORD_VALIDATION_PATTERN));
    }

    @Test
    public void shouldCheckPasswordContainsNumber() {
        Assert.assertFalse(AuthUtils.validate("@Password", UserServiceImpl.PASSWORD_VALIDATION_PATTERN));
    }

    @Test
    public void shouldCheckPasswordContainsCapital() {
        Assert.assertFalse(AuthUtils.validate("1@password", UserServiceImpl.PASSWORD_VALIDATION_PATTERN));
    }

    @Test
    public void shouldCheckPasswordContainsSpecialCharacter() {
        Assert.assertFalse(AuthUtils.validate("1Password", UserServiceImpl.PASSWORD_VALIDATION_PATTERN));
    }

    @Test
    public void shouldCheckEmailIsValidAgainstRegex() {
        Assert.assertTrue(AuthUtils.validate("Gracesophia!1@gmail.com", UserServiceImpl.EMAIL_VALIDATION_PATTERN));
    }

    @Test
    public void shouldCheckEmailContainsDomain() {
        Assert.assertFalse(AuthUtils.validate("gracesophiagmail.com", UserServiceImpl.EMAIL_VALIDATION_PATTERN));
    }

    @Test
    public void shouldCheckEmailContainsDot() {
        Assert.assertFalse(AuthUtils.validate("gracesophia@gmailcom", UserServiceImpl.EMAIL_VALIDATION_PATTERN));
    }

    @Test
    public void shouldCheckEmailContainsNoSpaces() {
        Assert.assertFalse(AuthUtils.validate("gracesophia @gmailcom", UserServiceImpl.EMAIL_VALIDATION_PATTERN));
    }

    @Test
    public void shouldCheckUsernameIsValidAgainstRegex() {
        Assert.assertTrue(AuthUtils.validate("SophiaGrace", UserServiceImpl.USERNAME_VALIDATION_PATTERN));
    }

    @Test
    public void shouldCheckUsernameContainsNoSpaces() {
        Assert.assertFalse(AuthUtils.validate("Sophia Grace", UserServiceImpl.USERNAME_VALIDATION_PATTERN));
    }

    @Test
    public void shouldCheckPasswordMatchesHashedPassword() {
        Assert.assertTrue(AuthUtils.verifyHash(password, hashedPassword));
    }

    @Test
    public void shouldCheckPasswordDoesNotMatchHashedPassword() {
        Assert.assertFalse(AuthUtils.verifyHash("WrongPassword", hashedPassword));
    }
}