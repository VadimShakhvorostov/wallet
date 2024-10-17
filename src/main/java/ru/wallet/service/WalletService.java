package ru.wallet.service;

import ru.wallet.entity.Transaction;
import ru.wallet.entity.Wallet;

import java.util.UUID;

public interface WalletService {
    Wallet save(Wallet wallet);

    Wallet findById(UUID uuid);

    Transaction updateWallet(Transaction transaction);
}
