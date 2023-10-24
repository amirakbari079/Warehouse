package com.example.warehouse.Exception;

public class CustomException extends Exception{
    public CustomException(){
        super("some error happened here");
    }
    public CustomException(String message){
        super(message);
    }
}
