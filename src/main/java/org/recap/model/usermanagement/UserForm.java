package org.recap.model.usermanagement;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dharmendrag on 29/11/16.
 */
@Setter
@Getter
public class UserForm implements Serializable {

    private Integer userId;

    private String username;

    private String password;

    private boolean rememberMe;

    private String wrongCredentials;

    private boolean passwordMatcher;

    private String institution;

    private String errorMessage;

    private Set<String> permissions=new HashSet<>();


}
