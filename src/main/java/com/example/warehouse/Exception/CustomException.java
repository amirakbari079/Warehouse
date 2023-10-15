package com.example.warehouse.Exception;

public class CustomException extends Exception{
    public CustomException(){
        super("some error happend here");
    }
    public CustomException(String message){
        super(message);
    }
}
