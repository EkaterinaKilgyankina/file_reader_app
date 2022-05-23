package com.sibdev.file_reader_app.exception;

public class FileProcessingException extends RuntimeException {
    public FileProcessingException(String message) {
        super(message);
    }

    public FileProcessingException(Throwable cause) {
        super(cause);
    }
}

