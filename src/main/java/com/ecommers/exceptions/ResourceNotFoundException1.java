package com.ecommers.exceptions;

public class ResourceNotFoundException1 extends RuntimeException {

    public ResourceNotFoundException1() {

        super("resource not found!!");

    }

    public ResourceNotFoundException1(String user, String username, String email) {

        super(user + "with " + username + " : " + email + "not found !!");

    }
}