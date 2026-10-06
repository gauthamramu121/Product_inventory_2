package com.example.products.service;

import com.example.products.responseDTO.DatabaseCredentials;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VaultService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    private static final String VAULT_ADDR = "http://vault:8200";
    private static final String ROLE = "products";

    private static final Path SERVICE_ACCOUNT_TOKEN =
            Path.of("/var/run/secrets/kubernetes.io/serviceaccount/token");

    public VaultService(ObjectMapper objectMapper) {
        this.webClient = WebClient.builder()
                .baseUrl(VAULT_ADDR)
                .build();

        this.objectMapper = objectMapper;
    }

    public String getVaultToken() throws IOException {

        String jwt = Files.readString(SERVICE_ACCOUNT_TOKEN);

        String response = webClient.post()
                .uri("/v1/auth/kubernetes/login")
                .bodyValue("""
                        {
                            "role": "%s",
                            "jwt": "%s"
                        }
                        """.formatted(ROLE, jwt))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode json = objectMapper.readTree(response);

        return json
                .path("auth")
                .path("client_token")
                .asText();
    }

    public DatabaseCredentials getDatabaseCredentials() throws IOException {

        String vaultToken = getVaultToken();

        String response = webClient.get()
                .uri("/v1/secret/data/product")
                .header("X-Vault-Token", vaultToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode json = objectMapper.readTree(response);

        String username = json
                .path("data")
                .path("data")
                .path("username")
                .asText();

        String password = json
                .path("data")
                .path("data")
                .path("password")
                .asText();

        return new DatabaseCredentials(username, password);
    }
}