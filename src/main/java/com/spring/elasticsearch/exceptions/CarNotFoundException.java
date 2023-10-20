package com.spring.elasticsearch.exceptions;

public class CarNotFoundException extends Exception{
    public CarNotFoundException(String msg){
        super(msg);
    }
}
