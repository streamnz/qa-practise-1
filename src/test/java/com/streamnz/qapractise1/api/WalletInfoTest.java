package com.streamnz.qapractise1.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

/**
 * @Author cheng hao
 * @Date 23/10/2025 18:43
 */
@Execution(ExecutionMode.SAME_THREAD)
@DisplayName("Wallet Info API Test")
@Slf4j
public class WalletInfoTest extends BaseApiTest{

    @Test
    @Order(2)
    @DisplayName("Sample Wallet Info API Test")
    void sampleWalletInfoApiTest() throws JsonProcessingException {
        // successful wallet info test
        APIRequestContext apiRequestContext = BaseApiTest.apiRequestContext;
        APIResponse response = apiRequestContext.get("/user/wallet-info", RequestOptions.create().setHeader("Authorization", "Bearer " + LoginApiTest.getAccessToken(null, null)));
        log.info("Response: {}", response.text());
        Assertions.assertTrue(response.ok());
        log.info("Wallet Info API Test executed successfully.");
        Gson gson = new Gson();
        Map<String,Object> map = gson.fromJson(response.text(), Map.class);
        WalletInfoDTO walletInfo = gson.fromJson(gson.toJson(map.get("wallet_info")), WalletInfoDTO.class);
        log.info("Wallet Info: {}", walletInfo.toString());
    }

    /**
     * "wallet_info": {
     *     "bind_time": "2025-10-22T22:28:04",
     *     "has_wallet_bound": true,
     *     "wallet_address": "0xcf635b209925da2035a3632e3201d5227c5b06c6",
     *     "wallet_type": "metamask"
     *   }
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    class WalletInfoDTO {
        public String bind_time;
        public boolean has_wallet_bound;
        public String wallet_address;
        public String wallet_type;


    }

}
