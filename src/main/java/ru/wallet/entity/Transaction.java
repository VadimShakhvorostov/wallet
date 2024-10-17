package ru.wallet.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.wallet.annotation.ValidTypeOperation;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private UUID id;
    @Positive(message = "amount negative")
    @NotNull(message = "amount empty")
    private long amount;
    @NotBlank
    @ValidTypeOperation(enumClass = OperationTypes.class, message = "invalid operation type")
    private String operationType;
}
