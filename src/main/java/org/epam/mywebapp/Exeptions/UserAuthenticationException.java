package org.epam.mywebapp.Exeptions;

public class UserAuthenticationException extends  Exception {

    public UserAuthenticationException(String message) {
        super(message);
    }
}
