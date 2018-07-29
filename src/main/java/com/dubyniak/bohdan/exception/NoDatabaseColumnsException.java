package com.dubyniak.bohdan.exception;

import javax.ws.rs.WebApplicationException;

public class NoDatabaseColumnsException extends WebApplicationException {

    public NoDatabaseColumnsException(String message) {
        super(message);
    }
}
