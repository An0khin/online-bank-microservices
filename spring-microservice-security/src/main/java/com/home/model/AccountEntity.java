package com.home.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "accounts")
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String hash;
}

