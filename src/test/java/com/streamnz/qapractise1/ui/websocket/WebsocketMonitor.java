package com.streamnz.qapractise1.ui.websocket;

import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @Author cheng hao
 * @Date 24/10/2025 12:11
 */
@Slf4j
public class WebsocketMonitor {
    private final List<String> messages = new CopyOnWriteArrayList<>();
    private final CompletableFuture<Void> connectionFuture = new CompletableFuture<>();
    private volatile boolean connected = false;

    public void setupWebsocketLisener(Page page) {
        page.onWebSocket(webSocket -> {
            log.info("WebSocket opened: " + webSocket.url());
            connectionFuture.complete(null);
            connected = true;

            webSocket.onFrameReceived(frame -> {
                log.info("WebSocket frame received: " + frame.text());
                messages.add(frame.text());
            });

            webSocket.onClose(ws -> {
                log.info("WebSocket closed: " + ws.url());
            });
        });
    }

    /**
     * count messages received
     */
    public int getMessageCount() {
        return messages.size();
    }

    /**
     * Wait for WebSocket connection
     */
    public boolean waitForConnection(int timeoutMs) {
        try {
            connectionFuture.get(timeoutMs, TimeUnit.MILLISECONDS);
            return true;
        } catch (Exception e) {
            log.error("WebSocket connection timeout: " + e.getMessage());
            return false;
        }
    }

    public boolean isConnected() {
        return connected;
    }
}
