package com.example.warehouse.Exception;

public class InvalidValueFieldException extends Exception{
    public InvalidValueFieldException(){
        super("Invalid value exception");
    }

    public InvalidValueFieldException(String field){
        super("Out of range "+field+" or null value!!!");
    }
}
