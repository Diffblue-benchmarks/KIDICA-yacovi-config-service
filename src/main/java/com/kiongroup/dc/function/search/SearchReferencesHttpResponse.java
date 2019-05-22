package com.kiongroup.dc.function.search;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.kiongroup.dc.function.core.mixin.JsonMixin;
import com.kiongroup.dc.function.core.model.SearchResult;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "searchReferences" })
public class SearchReferencesHttpResponse implements JsonMixin {
	
	@JsonProperty("searchReferences")
	private List<SearchResult> searchReferences;
	
	public SearchReferencesHttpResponse(List<SearchResult> searchResult) {
		this.searchReferences = searchResult;
	}

	public List<SearchResult> getSearchReferences() {
		return this.searchReferences;
	}

	public void setSearchReferences(List<SearchResult> searchReferences) {
		this.searchReferences = searchReferences;
	}

}