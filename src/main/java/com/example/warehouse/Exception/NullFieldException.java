package com.example.warehouse.Exception;

public class NullFieldException extends Exception{
    public NullFieldException(){
        super("Please fill necessary filed");
    }
    public NullFieldException(String field){
        super(field+" is necessary field please fill it");
    }
}
