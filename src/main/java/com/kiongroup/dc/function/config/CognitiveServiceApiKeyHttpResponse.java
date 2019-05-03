package com.kiongroup.dc.function.config;

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
@JsonPropertyOrder({ "cognitiveServiceApiKey" })
public class CognitiveServiceApiKeyHttpResponse implements JsonMixin {
	
	@JsonProperty("cognitiveServiceApiKey")
	private String cognitiveServiceApiKey;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	public CognitiveServiceApiKeyHttpResponse(String cognitiveServiceApiKey) {
		this.cognitiveServiceApiKey = cognitiveServiceApiKey;
	}

	@JsonProperty("cognitiveServiceApiKey")
	public String getCognitiveServiceApiKey() {
		return cognitiveServiceApiKey;
	}

	@JsonProperty("cognitiveServiceApiKey")
	public void setCognitiveServiceApiKey(String cognitiveServiceApiKey) {
		this.cognitiveServiceApiKey = cognitiveServiceApiKey;
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
