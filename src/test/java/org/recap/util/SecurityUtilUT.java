package org.recap.util;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Key;

import static org.junit.Assert.assertNotNull;

public class SecurityUtilUT extends BaseTestCase {

    @Autowired
    SecurityUtil securityUtil;
    @Test
    public void testGetEncryptedValue() throws Exception{
        String inputValue= "KEY VALUE";
        String encryptedValue = securityUtil.getEncryptedValue(inputValue);
        assertNotNull(encryptedValue);
    }

    @Test
    public void testGetDecryptedValue() throws Exception{
        String encryptedValue= "20MQ6zoEQ1PpXb99+anlbw==";
        String decryptedValue = securityUtil.getDecryptedValue(encryptedValue);
    }
}
