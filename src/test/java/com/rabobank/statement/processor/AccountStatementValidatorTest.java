package com.rabobank.statement.processor;

import com.rabobank.statement.processor.exception.FileTypeNotSupportedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AccountStatementValidatorTest {

    AccountStatementValidator accountStatementValidator;

    @Test
    public void whenJsonFile_process() {
        accountStatementValidator = new AccountStatementValidatorImpl();
        Assertions.assertDoesNotThrow(() -> accountStatementValidator.validateAccountStatementFile("src/test/resources/records.json"));
    }

    @Test
    public void whenCsvFile_process() {
        accountStatementValidator = new AccountStatementValidatorImpl();
        Assertions.assertDoesNotThrow(() -> accountStatementValidator.validateAccountStatementFile("src/test/resources/records.csv"));
    }

    @Test
    public void whenXmlFile_throwError() {
        accountStatementValidator = new AccountStatementValidatorImpl();
        Assertions.assertThrows(FileTypeNotSupportedException.class, () -> accountStatementValidator.validateAccountStatementFile("src/test/resources/records.txt"));
    }

    @Test
    public void whenNotExistCsvFile_throwError() {
        accountStatementValidator = new AccountStatementValidatorImpl();
        Assertions.assertThrows(IOException.class, () -> accountStatementValidator.validateAccountStatementFile("src/test/resources/records1.csv"));
    }

    @Test
    public void whenNotExistJsonFile_throwError() {
        accountStatementValidator = new AccountStatementValidatorImpl();
        Assertions.assertThrows(IOException.class, () -> accountStatementValidator.validateAccountStatementFile("src/test/resources/records1.json"));
    }

    @Test
    public void whenCorruptedJsonFile_throwError() {
        accountStatementValidator = new AccountStatementValidatorImpl();
        Assertions.assertThrows(IOException.class, () -> accountStatementValidator.validateAccountStatementFile("src/test/resources/records_corrupted.json"));
    }

    @Test
    public void whenCorruptedCsvFile_throwError() {
        accountStatementValidator = new AccountStatementValidatorImpl();
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountStatementValidator.validateAccountStatementFile("src/test/resources/records_corrupted.csv"));
    }
}
