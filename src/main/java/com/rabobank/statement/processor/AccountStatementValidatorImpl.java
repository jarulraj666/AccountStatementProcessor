package com.rabobank.statement.processor;

import com.rabobank.statement.processor.exception.FileTypeNotSupportedException;
import com.rabobank.statement.processor.model.AccountStatementValidatedRecord;

import java.io.IOException;
import java.util.List;

public final class AccountStatementValidatorImpl implements AccountStatementValidator {
    @Override
    public List<AccountStatementValidatedRecord> validateAccountStatementFile(String filePath) throws FileTypeNotSupportedException, IOException {
        AccountStatementProcessor handler = AccountStatementProcessorHandlerFactory.createAccountStatementProcessor(filePath);
        return handler.validateAccountStatementFile(filePath);
    }
}
