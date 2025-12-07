package com.example.toolserverhttpcounter.controller.login;

import com.example.toolserverhttpcounter.bean.Result;
import com.example.toolserverhttpcounter.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/rest-v2/login")
public class LoginControllerV2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginControllerV2.class);

    @RequestMapping(method = POST, path = "/access_token")
    public Result<AccessTokenResponse> accessToken(@RequestHeader String rfst, @RequestBody AccessTokenRequest request) throws JsonProcessingException {
        LOGGER.info("<=== {}", JsonUtils.parseToString(request));
        LOGGER.info("<=== header/rfst={}", rfst);
        AccessTokenResponse response = new AccessTokenResponse();
        response.setAccessToken(UUID.randomUUID().toString());
        response.setTokenType("normal");
        return Result.success(response);
    }

    @Data
    private static class AccessTokenRequest {
        private String username;
        private String password;
    }

    @Data
    private static class AccessTokenResponse {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("token_type")
        private String tokenType;
    }
}
