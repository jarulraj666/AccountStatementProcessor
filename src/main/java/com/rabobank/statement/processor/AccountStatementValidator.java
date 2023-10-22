package com.rabobank.statement.processor;

import com.rabobank.statement.processor.exception.FileTypeNotSupportedException;
import com.rabobank.statement.processor.model.AccountStatementValidatedRecord;

import java.io.IOException;
import java.util.List;

/**
 * Use this Interface to validate account statement record
 * Supported File formats CSV, Json
 */
public sealed interface AccountStatementValidator permits AccountStatementValidatorImpl{

    /**
     * Method responsible to validate Account statement for a given filePath
     * @param filePath Relative path of the file
     * @return Return Invalid records with detailed description
     * @throws FileTypeNotSupportedException Currently only supported for Json and CSV. Throws exception for other file formats
     * @throws IOException If any issues in accessing the file
     */
    List<AccountStatementValidatedRecord> validateAccountStatementFile(String filePath) throws FileTypeNotSupportedException, IOException;
}
