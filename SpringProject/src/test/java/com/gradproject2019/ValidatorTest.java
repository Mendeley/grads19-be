package com.gradproject2019;

import com.gradproject2019.users.service.Validator;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {

    @Test
    public void shouldCheckLength() {
        Validator validator = Validator.buildValidator(false, false, false, 8, 16);
        Assert.assertFalse(validator.validate("smol"));
        Assert.assertTrue(validator.validate("password"));
        Assert.assertFalse(validator.validate("longlonglonglonglong"));
    }

    @Test
    public void shouldCheckContainsNumber() {
        Validator validator = Validator.buildValidator(false, false, true, 8, 16);
        Assert.assertFalse(validator.validate("password"));
        Assert.assertTrue(validator.validate("password1"));
        Assert.assertTrue(validator.validate("password12"));
    }

    @Test
    public void shouldCheckContainsCapital() {
        Validator validator = Validator.buildValidator(false, true, false, 8, 16);
        Assert.assertFalse(validator.validate("password"));
        Assert.assertTrue(validator.validate("Password"));
        Assert.assertTrue(validator.validate("PassworD"));
    }

    @Test
    public void shouldCheckContainsSpecialChar() {
        Validator validator = Validator.buildValidator(true, false, false, 8, 16);
        Assert.assertFalse(validator.validate("password"));
        Assert.assertTrue(validator.validate("pas$word"));
        Assert.assertTrue(validator.validate("pa$$word!"));
    }
}
