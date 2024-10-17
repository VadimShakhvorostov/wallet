package ru.wallet.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Wallet {

    @Id
    @UuidGenerator
    private UUID id;
    private long balance;
    @Version
    @Setter(AccessLevel.NONE)
    private long version;
    
}
