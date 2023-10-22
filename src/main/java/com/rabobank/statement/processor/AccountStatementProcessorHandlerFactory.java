package com.rabobank.statement.processor;

import com.rabobank.statement.processor.exception.FileTypeNotSupportedException;


class AccountStatementProcessorHandlerFactory {
    public static AccountStatementProcessor createAccountStatementProcessor(String filePath) throws FileTypeNotSupportedException {
        if (filePath.endsWith(".json")) {
            return new JsonAccountStatementProcessor();
        } else if (filePath.endsWith(".csv")) {
            return new CsvAccountStatementProcessor();
        }
        // Add more file type checks if needed

        throw new FileTypeNotSupportedException("File format not supported");
    }
}
