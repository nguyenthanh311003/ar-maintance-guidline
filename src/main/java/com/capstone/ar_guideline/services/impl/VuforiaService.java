package com.capstone.ar_guideline.services.impl;
import com.capstone.ar_guideline.dtos.requests.Vuforia.DatasetRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.Map;

@Service
public class VuforiaService {

    private final WebClient webClient;

    @Value("{vuforia.grant_type}")
    private String grantType;

    @Value("${vuforia.username}")
    private String username;

    @Value("${vuforia.password}")
    private String password;

    public VuforiaService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://vws.vuforia.com").build();
    }

    /**
     * Obtain JWT Token using Password Grant
     */
    public Mono<String> getTokenWithPasswordGrant() {
        return webClient.post()
                .uri("/oauth2/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("grant_type=password&username=" + username + "&password=" + password)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"));
    }

    public String downloadAndStoreDataset(String uuid) {
        return getTokenWithPasswordGrant()
                .flatMap(token -> webClient.get()
                        .uri("/modeltargets/advancedDatasets/" + uuid + "/dataset")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .accept(MediaType.APPLICATION_OCTET_STREAM)
                        .retrieve()
                        .bodyToMono(InputStream.class)
                        .map(FileStorageService::storeZipFile))
                .block(); // Blocking for simplicity
    }
    public String createDataset(DatasetRequest datasetRequest) {
        return getTokenWithPasswordGrant()
                .flatMap(token -> webClient.post()
                        .uri("/modeltargets/datasets")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .bodyValue(datasetRequest)  // Automatically converts DTO to JSON
                        .retrieve()
                        .bodyToMono(String.class))
                .block(); // Blocking for simplicity
    }

}
