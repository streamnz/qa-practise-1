package com.streamnz.qapractise1.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * @Author cheng hao
 * @Date 23/10/2025 16:27
 */
@DisplayName( "Login API Test")
@Execution(ExecutionMode.SAME_THREAD)
@Slf4j
public class LoginApiTest {

    private static Playwright playwright;
    private static Browser browser;
    private Page page;

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100)
        );
    }

    @BeforeEach
    void setPage() {
        this.page = browser.newPage();
    }

    @AfterEach
    void closePage() {
        page.close();
    }

    @AfterAll
    static void teardown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test
    @DisplayName("Sample Login API Test")
    void sampleLoginApiTest() {
        // successful login test
        log.info("Login API Test executed successfully.");


    }
}
