package ru.wallet.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.wallet.contrioller.WalletController;

import ru.wallet.entity.Transaction;
import ru.wallet.entity.Wallet;
import ru.wallet.exception.BalanceException;
import ru.wallet.exception.NotFoundException;
import ru.wallet.repository.WalletRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class WalletControllerTest {

    private final long TEST_BALANCE = 1000L;
    private final long TEST_DEPOSIT = 500L;
    private final long TEST_WITHDRAW = 500L;
    @Autowired
    WalletServiceImpl walletService;
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletController walletController;


    private void deleteTestData(Wallet wallet) {

        walletRepository.deleteById(wallet.getId());
    }

    private Wallet getTestWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(TEST_BALANCE);
        return wallet;
    }

    private Transaction getTestTransactionDeposit(UUID uuid) {
        Transaction transaction = new Transaction();
        transaction.setId(uuid);
        transaction.setOperationType("DEPOSIT");
        transaction.setAmount(500);
        return transaction;
    }

    private Transaction getTestTransactionWithdraw(UUID uuid) {
        Transaction transaction = new Transaction();
        transaction.setId(uuid);
        transaction.setOperationType("WITHDRAW");
        transaction.setAmount(500);
        return transaction;
    }

    @Test
    @DisplayName("must add data in db")
    public void saveTest() {

        Wallet wallet = walletController.creatWallet(getTestWallet());
        Wallet walletInDb = walletController.findWalletById(wallet.getId());

        assertThat(wallet.getBalance()).isEqualTo(walletInDb.getBalance());
        assertThat(wallet.getId()).isEqualTo(walletInDb.getId());

        deleteTestData(wallet);
    }

    @Test
    @DisplayName("must update data in db")
    public void updateTestDeposit() {

        Wallet wallet = walletController.creatWallet(getTestWallet());
        Transaction transaction = getTestTransactionDeposit(wallet.getId());
        walletController.updateWallet(transaction);

        Wallet walletInDb = walletController.findWalletById(wallet.getId());

        assertThat(walletInDb.getBalance()).isEqualTo(TEST_BALANCE + TEST_DEPOSIT);

        deleteTestData(wallet);
    }

    @Test
    @DisplayName("must update data in db")
    public void updateTestWithdraw() {

        Wallet wallet = walletController.creatWallet(getTestWallet());
        Transaction transaction = getTestTransactionWithdraw(wallet.getId());
        walletController.updateWallet(transaction);

        Wallet walletInDb = walletController.findWalletById(wallet.getId());

        assertThat(walletInDb.getBalance()).isEqualTo(TEST_BALANCE - TEST_WITHDRAW);
        deleteTestData(wallet);
    }

    @Test
    @DisplayName("should throw an BalanceException")
    public void updateTestWithdrawNotEnough() {

        Wallet wallet = walletController.creatWallet(getTestWallet());
        Transaction transaction = getTestTransactionWithdraw(wallet.getId());
        transaction.setAmount(2000L);

        Exception exception = assertThrows(BalanceException.class, () -> {
            walletController.updateWallet(transaction);
        });

        String expectedMessage = "not enough money";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        deleteTestData(wallet);
    }

    @Test
    @DisplayName("should throw an NotFoundException")
    public void updateTestInvalidId() {

        Wallet wallet = walletController.creatWallet(getTestWallet());
        Transaction transaction = getTestTransactionWithdraw(UUID.fromString("11111111-3db1-4f28-9e75-7125e0ce3dc8"));
        transaction.setAmount(2000L);

        Exception exception = assertThrows(NotFoundException.class, () -> {
            walletController.updateWallet(transaction);
        });

        String expectedMessage = "not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        deleteTestData(wallet);
    }

}
