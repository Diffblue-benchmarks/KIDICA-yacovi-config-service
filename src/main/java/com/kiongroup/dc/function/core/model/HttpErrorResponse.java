package com.kiongroup.dc.function.core.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.kiongroup.dc.function.core.mixin.JsonMixin;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "message" })
public class HttpErrorResponse implements JsonMixin {
	
	@JsonProperty("message")
	private String message;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	public HttpErrorResponse(final String message) {
		this.message = message;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
