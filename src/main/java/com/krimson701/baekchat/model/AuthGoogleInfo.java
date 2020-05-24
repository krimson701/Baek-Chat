package com.krimson701.baekchat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AuthGoogleInfo {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("email")
    private String email;

    @JsonProperty("verified_email")
    private String verifiedEmail;

    @JsonProperty("access_type")
    private String accessType;
}
