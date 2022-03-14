package com.company.kun.uz.expections;

public class BadRequestExeption extends RuntimeException{
    public BadRequestExeption(String message) {
        super(message);
    }
}
