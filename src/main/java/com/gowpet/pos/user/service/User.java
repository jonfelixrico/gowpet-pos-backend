package com.gowpet.pos.user.service;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Getter
@Table(name = "app_user") // Can't use "user" since it's a reserved keyword in some DBs (pg)
public class User { // TODO turn this into an abstract; decouple from DB
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}
