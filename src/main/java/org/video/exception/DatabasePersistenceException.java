package org.video.exception;

public class DatabasePersistenceException extends RuntimeException{
    public DatabasePersistenceException(String message){
        super(message);
    }
}
