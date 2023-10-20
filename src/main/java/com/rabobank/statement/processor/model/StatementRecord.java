package com.rabobank.statement.processor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record StatementRecord(@JsonProperty("Reference") Integer reference,
                              @JsonProperty("Account Number") String accountNumber,
                              @JsonProperty("Description") String description,
                              @JsonProperty("Start Balance") BigDecimal startBalance,
                              @JsonProperty("Mutation") BigDecimal mutation,
                              @JsonProperty("End Balance") BigDecimal endBalance,
                              @JsonProperty("Transaction Date") String transactionDate) {
}
