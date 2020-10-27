package com.example.social_network.utils;

public class NoEntityException extends Throwable{

    public NoEntityException(Long id){
        super(String.format("User with id:%d not found", id));
    }
}
