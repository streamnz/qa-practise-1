package com.streamnz.qapractise1.api;

import ch.qos.logback.core.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.platform.commons.util.StringUtils;

import java.util.Map;

/**
 * @Author cheng hao
 * @Date 23/10/2025 16:40
 */
@Execution(ExecutionMode.SAME_THREAD)
@DisplayName("Login API Test")
@Slf4j
public class LoginApiTest extends BaseApiTest {


    @Test
    @Order(1)
    @DisplayName("Sample Login API Test")
    void sampleLoginApiTest()  {
        // successful login test
        Map<String, Object> loginPayload = Map.of(
                "username", "hao.streamnz@gmail.com",
                "password", "Pass1234"
        );
        APIResponse post = apiRequestContext.post("/user/login", RequestOptions.create().setData(loginPayload));
        Assertions.assertTrue(post.ok());
        Map<String,Object> map = new Gson().fromJson(post.text(), Map.class);
        Object tokenObject = map.get("access_token");
        Assertions.assertNotNull(tokenObject, "Access token should not be null");
        String accessToken = tokenObject.toString();
        log.info("Login API Test executed successfully.");
        log.info("accessToken: {}", accessToken);
    }
    // edge cases can be added here


    static String getAccessToken(String username, String password) {
        Map<String, Object> loginPayload = Map.of(
                "username", StringUtil.isNullOrEmpty(username)?"hao.streamnz@gmail.com":username,
                 "password", StringUtil.isNullOrEmpty(password)?"Pass1234":password
        );
        APIResponse post = apiRequestContext.post("/user/login", RequestOptions.create().setData(loginPayload));
        Assertions.assertTrue(post.ok());
        Map<String,Object> map = new Gson().fromJson(post.text(), Map.class);
        Object tokenObject = map.get("access_token");
        Assertions.assertNotNull(tokenObject, "Access token should not be null");
        return tokenObject.toString();
    }



}
