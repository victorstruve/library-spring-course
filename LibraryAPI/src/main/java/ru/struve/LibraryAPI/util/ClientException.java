package ru.struve.LibraryAPI.util;

public class ClientException extends RuntimeException {
    public ClientException(String msg){
        super(msg);
    }
}
