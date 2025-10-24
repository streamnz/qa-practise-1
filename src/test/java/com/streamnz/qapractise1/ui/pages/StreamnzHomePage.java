package com.streamnz.qapractise1.ui.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

/**
 * @Author cheng hao
 * @Date 23/10/2025 21:58
 */
public class StreamnzHomePage {

    private final Page page;

    public StreamnzHomePage(Page page) {
        this.page = page;
    }

    /**
     * Navigate to the Streamnz home page.
     */
    public void navigate() {
        page.navigate("https://www.streamnz.com");

        // Wait for the page to load completely
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);

    }

    /**
     * Open the login modal on the home page.
     */
    public void openLoginModal() {
        if(page.isVisible("text=Login")){
            page.click("text=Login");
        }
    }

    public void fillLoginForm(String username, String password) {
        page.fill("input[placeholder='Enter your email']", username);
        page.fill("input[placeholder='Enter your password']", password);
    }

    public void submitLoginForm() {
        page.click("button[type='submit']");
    }

    /**
     * Check if login was successful.
     * class="navbar-btn settings-btn"
     */
    public boolean isLoginSuccessful() {
        if(page.isVisible(".navbar-btn.settings-btn")){
            return true;
        } else {
            return false;
        }
    }

    public void clickStartGameButton() {
        page.click("text=Enter Game");
    }

    public void clickPlayAsWhiteButton() {
        if(page.isVisible("text=Play as White")){
            page.click("text=Play as White");
        }
    }



    public boolean waitForGameBoard() {
        try{
            page.waitForSelector(".gomoku-game-board", new Page.WaitForSelectorOptions().setTimeout(5000));
            return true;
        }catch (Exception e){
            try{
                page.waitForLoadState(LoadState.DOMCONTENTLOADED);
                return page.isVisible(".gomoku-game-board");
            }catch (Exception e2){
                return false;
            }
        }
    }
}
