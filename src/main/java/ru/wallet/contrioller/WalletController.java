package ru.wallet.contrioller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.wallet.entity.Transaction;
import ru.wallet.entity.Wallet;
import ru.wallet.service.WalletServiceImpl;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletServiceImpl walletService;

    @PostMapping
    public Wallet creatWallet(@Valid @RequestBody Wallet wallet) {
        return walletService.save(wallet);
    }

    @PutMapping
    public Transaction updateWallet(@Valid @RequestBody Transaction transaction) {
        return walletService.updateWallet(transaction);
    }

    @GetMapping("/{uuid}")
    public Wallet findWalletById(@PathVariable(name = "uuid") UUID uuid) {
        return walletService.findById(uuid);
    }

    @GetMapping("/test")
    public String getTest() {
        return "тест";
    }


}
