package com.company.kun.uz.expections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({ItemNotFoundExeption.class})
    public ResponseEntity<?> handlerExeption(ItemNotFoundExeption e)
    {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler({AuthExeption.class})
    public ResponseEntity<?> handlerExeption(AuthExeption e)
    {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler({ForBiddenExeption.class})
    public ResponseEntity<?> handlerExeption(ForBiddenExeption e)
    {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
