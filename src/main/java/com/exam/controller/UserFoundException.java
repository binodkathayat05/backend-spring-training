package com.exam.controller;

public class UserFoundException extends Exception{
    public UserFoundException() {
        super("User with this Username is not found in database");
    }
    public UserFoundException(String msg){super(msg);}
}
