package org.mflix.forecast.exception;

import org.mflix.forecast.enumeration.StatusEnumeration;

public class ApplicationException extends Throwable {
    private static final long serialVersionUID = 1L;

    public static ApplicationException with(StatusEnumeration statusEnumeration) {
        String message = statusEnumeration.getMessage();
        return new ApplicationException(message);
    }

    public ApplicationException(String message) {
        super(message);
    }
}