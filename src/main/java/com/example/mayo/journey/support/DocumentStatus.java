package com.example.mayo.journey.support;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DocumentStatus {

    @JsonProperty("draft")
    DRAFT,
    @JsonProperty("published")
    PUBLISHED,
    @JsonProperty("deleted")
    DELETED
}
