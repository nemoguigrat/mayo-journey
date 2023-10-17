package com.example.mayo.journey.support;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserStatus {

    @JsonProperty("created")
    CREATED,

    @JsonProperty("blocked")
    BLOCKED,

    @JsonProperty("approved")
    APPROVED
}
