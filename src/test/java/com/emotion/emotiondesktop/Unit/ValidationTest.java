package com.emotion.emotiondesktop.Unit;

import com.emotion.emotiondesktop.Helper.Validation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {

    @Test
    public void testIsValidNumberValidNumbers() {
        assertTrue(Validation.isValidNumber("123"));
        assertTrue(Validation.isValidNumber("123.456"));
        assertTrue(Validation.isValidNumber("-123.456"));
        assertTrue(Validation.isValidNumber("+123.456"));
    }

    @Test
    public void testIsValidNumberInvalidNumbers() {
        assertFalse(Validation.isValidNumber(""));
        assertFalse(Validation.isValidNumber("abc"));
        assertFalse(Validation.isValidNumber("12a3"));
        assertFalse(Validation.isValidNumber("12.34.56"));
        assertFalse(Validation.isValidNumber("-12.34.56"));
        assertFalse(Validation.isValidNumber("+12.34.56"));
    }

    @Test
    public void testIsNotEmptySingleInput() {
        assertTrue(Validation.isNotEmpty("test"));
        assertFalse(Validation.isNotEmpty(""));
    }

    @Test
    public void testIsNotEmptyMultipleInputs() {
        assertTrue(Validation.isNotEmpty("test1", "test2"));
        assertFalse(Validation.isNotEmpty("", "test2"));
        assertFalse(Validation.isNotEmpty("test1", ""));
        assertFalse(Validation.isNotEmpty("", ""));
    }
}
