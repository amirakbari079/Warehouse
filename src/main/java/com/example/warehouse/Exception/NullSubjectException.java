package com.example.warehouse.Exception;

public class NullSubjectException extends Exception{
    public NullSubjectException(){
        super("Please fill subject filed");
    }
}
