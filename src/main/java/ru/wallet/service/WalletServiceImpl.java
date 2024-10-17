package ru.wallet.service;

import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wallet.entity.OperationTypes;
import ru.wallet.entity.Transaction;
import ru.wallet.entity.Wallet;
import ru.wallet.exception.BalanceException;
import ru.wallet.exception.NotFoundException;
import ru.wallet.repository.WalletRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {


    private final WalletRepository walletRepository;

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findById(UUID uuid) {
        return walletRepository.findById(uuid).orElseThrow(() -> new NotFoundException("not found"));
    }

    @Override
    public Transaction updateWallet(Transaction transaction) {
        int count = 0;

        while (count < 3) {

            Wallet wallet = walletRepository.findById(transaction.getId()).orElseThrow(() -> new NotFoundException("not found"));
            long balance = wallet.getBalance();
            long amount = transaction.getAmount();
            OperationTypes operationTypes = OperationTypes.valueOf(transaction.getOperationType());

            switch (operationTypes) {
                case DEPOSIT -> wallet.setBalance(balance + amount);
                case WITHDRAW -> {
                    if ((balance - amount) <= 0) {
                        throw new BalanceException("not enough money");
                    }
                    wallet.setBalance(balance - amount);

                }
            }

            try {
                save(wallet);
                count = 3;
                return transaction;
            } catch (OptimisticLockException ex) {
                count++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("repied");
    }
}
