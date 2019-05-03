package com.kiongroup.dc.function.core.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "kty", "use", "kid", "x5t", "n", "e", "x5c", "issuer" })
public class AzureTenantLoginKey {
	@JsonProperty("kty")
	private String kty;
	@JsonProperty("use")
	private String use;
	@JsonProperty("kid")
	private String kid;
	@JsonProperty("x5t")
	private String x5t;
	@JsonProperty("n")
	private String n;
	@JsonProperty("e")
	private String e;
	@JsonProperty("x5c")
	private List<String> x5c = null;
	@JsonProperty("issuer")
	private String issuer;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("kty")
	public String getKty() {
		return kty;
	}

	@JsonProperty("kty")
	public void setKty(String kty) {
		this.kty = kty;
	}

	@JsonProperty("use")
	public String getUse() {
		return use;
	}

	@JsonProperty("use")
	public void setUse(String use) {
		this.use = use;
	}

	@JsonProperty("kid")
	public String getKid() {
		return kid;
	}

	@JsonProperty("kid")
	public void setKid(String kid) {
		this.kid = kid;
	}

	@JsonProperty("x5t")
	public String getX5t() {
		return x5t;
	}

	@JsonProperty("x5t")
	public void setX5t(String x5t) {
		this.x5t = x5t;
	}

	@JsonProperty("n")
	public String getN() {
		return n;
	}

	@JsonProperty("n")
	public void setN(String n) {
		this.n = n;
	}

	@JsonProperty("e")
	public String getE() {
		return e;
	}

	@JsonProperty("e")
	public void setE(String e) {
		this.e = e;
	}

	@JsonProperty("x5c")
	public List<String> getX5c() {
		return x5c;
	}

	@JsonProperty("x5c")
	public void setX5c(List<String> x5c) {
		this.x5c = x5c;
	}

	@JsonProperty("issuer")
	public String getIssuer() {
		return issuer;
	}

	@JsonProperty("issuer")
	public void setIssuer(String issuer) {
		this.issuer = issuer;
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
