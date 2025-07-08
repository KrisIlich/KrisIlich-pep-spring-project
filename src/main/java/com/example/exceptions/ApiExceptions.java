package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ApiExceptions {
 
    //exception for duplicate username (409)
    @ResponseStatus(HttpStatus.CONFLICT)
    public static class DuplicateResourceException extends RuntimeException{
        public DuplicateResourceException(String msg){
            super(msg);
        }
    }

    // general bad request (400)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class BadRequestException extends RuntimeException{
        public BadRequestException(String msg) {
            super(msg);
        }
    }

    // Resource not found - general bad request (400)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class ResourceNotFoundException extends RuntimeException{
        public ResourceNotFoundException(String msg) {
            super(msg);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    public static class ExceptionKnownButOk extends RuntimeException{
        public ExceptionKnownButOk(String msg) {
            super(msg);
        }
    }

    //unauthorized login (401)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String msg) {
            super(msg);
        }
    }



    

}
