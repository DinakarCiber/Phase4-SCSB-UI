package org.recap.model.usermanagement;

import org.junit.Test;
import org.mockito.Mock;
import org.recap.BaseTestCase;
import org.springframework.validation.Errors;

/**
 * Created by hemalathas on 21/2/17.
 */
public class LoginValidatorUT extends BaseTestCase {

    @Mock
    Errors errors;
    @Test
    public void testLoginValidator(){
        LoginValidator loginValidator = new LoginValidator();
        Object object = new UserForm();
        loginValidator.validate(object,errors);
    }

}