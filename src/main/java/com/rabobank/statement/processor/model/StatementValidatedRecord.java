package com.rabobank.statement.processor.model;

public record StatementValidatedRecord(Integer reference, String description, String[] errors) {
}
