package com.example.products;

import lombok.extern.slf4j.Slf4j;

import com.example.products.responseDTO.DatabaseCredentials;
import com.example.products.service.VaultService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VaultTestRunner implements CommandLineRunner {

    private final VaultService vaultService;

    public VaultTestRunner(VaultService vaultService) {
        this.vaultService = vaultService;
    }

    @Override
    public void run(String... args) throws Exception {

        DatabaseCredentials credentials =
                vaultService.getDatabaseCredentials();

        log.info("=================================");
        log.info("VAULT CONNECTION SUCCESSFUL");
        log.info("Vault username: " + credentials.username());
        log.info("Vault password retrieved: "
                + !credentials.password().isBlank());
        log.info("=================================");
    }
}