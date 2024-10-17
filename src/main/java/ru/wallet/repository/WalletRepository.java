package ru.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.wallet.entity.Wallet;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Transactional
    Optional<Wallet> findById(UUID uuid);

    @Transactional
    void deleteById(UUID uuid);
}
