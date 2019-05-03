package com.kiongroup.dc.function.search;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.kiongroup.dc.function.core.mixin.JsonMixin;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "searchReferences" })
public class SearchReferencesHttpResponse implements JsonMixin {
	
	@JsonProperty("searchReferences")
	private List<String> searchReferences;
	
	public SearchReferencesHttpResponse(List<String> searchReferences) {
		this.searchReferences = searchReferences == null ? new ArrayList<String>() : searchReferences;
	}

	public List<String> getSearchReferences() {
		return searchReferences;
	}

	public void setSearchReferences(List<String> searchReferences) {
		this.searchReferences = searchReferences;
	}

}