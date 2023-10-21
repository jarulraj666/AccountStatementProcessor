package com.rabobank.statement.processor.exception;

/**
 * Exception to handle unknown file format
 */
public class FileTypeNotSupportedException extends Exception {
    public FileTypeNotSupportedException(String message) {
        super(message);
    }
}
