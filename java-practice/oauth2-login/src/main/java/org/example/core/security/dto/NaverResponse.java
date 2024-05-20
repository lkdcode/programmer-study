package org.example.core.security.dto;

import java.util.Map;

public class NaverResponse implements OAuth2Response {
    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attribute.get("id"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(attribute.get("email"));
    }

    @Override
    public String getName() {
        return String.valueOf(attribute.get("name"));
    }
}
