package com.bank.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Embeddable
@Data
public class Credential {
    @Column(unique = true, length = 50, nullable = false)
    @Email
    private String accountEmail;
    @Column(nullable = false)
    private String accountPassword;
}
