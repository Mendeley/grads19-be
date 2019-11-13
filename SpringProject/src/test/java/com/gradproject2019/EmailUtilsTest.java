package com.gradproject2019;

import com.gradproject2019.utils.EmailUtils;

import org.junit.Assert;
import org.junit.Test;

public class EmailUtilsTest {

    @Test
    public void shouldAllowNumber() {
        Assert.assertTrue(EmailUtils.validate("grace123@gmail.com"));
        Assert.assertTrue(EmailUtils.validate("grace@gmail.com"));
    }


    @Test
    public void shouldAllowSpecialChar() {
        Assert.assertTrue(EmailUtils.validate("grace!_sophia$@gmail.com"));
        Assert.assertTrue(EmailUtils.validate("gracesophia@gmail.com"));
    }

    @Test
    public void shouldAllowCapitalLetter() {
        Assert.assertTrue(EmailUtils.validate("GraceSophia@gmail.com"));
        Assert.assertTrue(EmailUtils.validate("gracesophia@gmail.com"));
    }

    @Test
    public void shouldContainAt() {
        Assert.assertTrue(EmailUtils.validate("gracesophia@gmail.com"));
        Assert.assertFalse(EmailUtils.validate("gracesophiagmail.com"));
    }
    @Test
    public void shouldContainDotInDomain() {
        Assert.assertTrue(EmailUtils.validate("gracesophia@gmail.com"));
        Assert.assertFalse(EmailUtils.validate("gracesophia@gmailcom"));
    }
}
