package com.streamnz.qapractise1.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

/**
 * @Author cheng hao
 * @Date 23/10/2025 18:44
 */

public class BaseApiTest {

    static Playwright playwright;
    static APIRequestContext apiRequestContext;
    private static final String BASE_URL = "https://aigame.streamnz.com"; // Replace with actual base URL

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        apiRequestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(BASE_URL)
                        .setExtraHTTPHeaders(Map.of("Content-type", "application/json"))
        );
    }

    @AfterAll
    static void teardown() {
        if (apiRequestContext != null) apiRequestContext.dispose();
        if (playwright != null) playwright.close();
    }
}
