package com.streamnz.qapractise1.ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.streamnz.qapractise1.ui.pages.StreamnzHomePage;
import com.streamnz.qapractise1.ui.websocket.WebsocketMonitor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * @Author cheng hao
 * @Date 23/10/2025 16:27
 */
@DisplayName( "Login UI Test")
@Execution(ExecutionMode.SAME_THREAD)
@Slf4j
public class LoginUITest {

    private static Playwright playwright;
    private static Browser browser;
    private Page page;

    private StreamnzHomePage streamnzHomePage;
    private WebsocketMonitor websocketMonitor;

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
        this.streamnzHomePage = new StreamnzHomePage(page);
        // monitor WebSocket connections
        this.websocketMonitor = new WebsocketMonitor();
        this.websocketMonitor.setupWebsocketLisener(page);
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
    @DisplayName("UI login success test")
    void sampleLoginApiTest(){

        performLogin("hao.streamnz@gmail.com", "Pass1234");

        // wait and verify todo del when dev finished
//        page.waitForTimeout(3000);  // wait for 3 seconds to observe the result

        // setting button is visible means login is successful
        Assertions.assertTrue(streamnzHomePage.isLoginSuccessful(), "Login should be successful");
        log.info("UI Login successful");
    }

    private void performLogin(String username, String password) {
        // navigate to login page
        streamnzHomePage.navigate();

        // open login modal
        streamnzHomePage.openLoginModal();

        // fill in username and password
        streamnzHomePage.fillLoginForm(username, password);

        // submit login form
        streamnzHomePage.submitLoginForm();
    }

    @Test
    @DisplayName("UI login with WebSocket monitoring test")
    void loginWithWebSocketMonitoringTest() {
        // based on the previous login test
        performLogin("hao.streamnz@gmail.com", "Pass1234");

        // enter game
        streamnzHomePage.clickStartGameButton();
        streamnzHomePage.clickPlayAsWhiteButton();
        boolean isStart = streamnzHomePage.waitForGameBoard();
        Assertions.assertTrue(isStart, "Game board should be visible");
        log.info("Game Start !");

        boolean connected = websocketMonitor.waitForConnection(5000);
        Assertions.assertTrue(connected, "WebSocket should be connected");
        log.info("WebSocket connection established");


        int count = websocketMonitor.getMessageCount();
        log.info("WebSocket messages received count: {}", count);
        Assertions.assertTrue(count > 0, "WebSocket should have received messages");
        
    }
    
}
