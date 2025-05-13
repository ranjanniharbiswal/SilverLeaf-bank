package com.bank.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.bank.backend.constant.TransactionMode;

import java.time.LocalDateTime;

public record TransactionDTO(
        @JsonFormat(pattern = "dd MMM yyyy | hh:mm:ss a")
        LocalDateTime timestamp,
        TransactionMode mode,
        double balance
) {
}
