package com.example.todayerror.security.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormLoginFilter extends UsernamePasswordAuthenticationFilter {
    final private ObjectMapper objectMapper;

    public FormLoginFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try {
            JsonNode requestBody = objectMapper.readTree(request.getInputStream());
<<<<<<< HEAD
            String username = requestBody.get("nickName").asText();
=======
            String username = requestBody.get("nickname").asText();
>>>>>>> 9be7c7af23281f296848466cb821fd0cec0d15ef
            String password = requestBody.get("password").asText();
            authRequest = new UsernamePasswordAuthenticationToken(username, password); // 인증 객체 생성
        } catch (Exception e) {
            throw new RuntimeException("nickname과 password를 입력해주세요");
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}