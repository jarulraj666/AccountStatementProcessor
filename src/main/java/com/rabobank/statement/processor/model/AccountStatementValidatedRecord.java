package com.rabobank.statement.processor.model;

/**
 * Processes the Statement Record and provides the validated result
 */
public record AccountStatementValidatedRecord(Integer reference, String description, String[] errors) {
}
