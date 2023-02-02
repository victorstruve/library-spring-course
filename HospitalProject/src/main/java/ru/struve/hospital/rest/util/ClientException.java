package ru.struve.hospital.rest.util;

public class ClientException extends RuntimeException{
    public ClientException(String msg){
        super(msg);
    }
}
