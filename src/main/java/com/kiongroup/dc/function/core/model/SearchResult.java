package com.kiongroup.dc.function.core.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kiongroup.dc.function.core.mixin.JsonMixin;

@JsonSerialize
public class SearchResult implements JsonMixin {
    private String referenceTitle;
    private String referenceImageUrl;

    public SearchResult(String referenceTitle, String referenceImageUrl) {
        this.referenceTitle = referenceTitle;
        this.referenceImageUrl = referenceImageUrl;
    }

    public SearchResult setTitle(String newTitle) {
        this.referenceTitle = newTitle;
        return this;
    }

    public String getReferenceTitle() {
        return this.referenceTitle;
    }

    public String getReferenceImageUrl() {
        return this.referenceImageUrl;
    }
}
