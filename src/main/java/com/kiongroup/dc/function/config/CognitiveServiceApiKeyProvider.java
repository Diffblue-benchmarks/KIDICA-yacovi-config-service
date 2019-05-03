package com.kiongroup.dc.function.config;

public final class CognitiveServiceApiKeyProvider {

	private CognitiveServiceApiKeyProvider() {};
	
	private static final String COGNITIVE_SERVICE_API_KEY_LABEL = "YACOVI-COGNITIVE-SERVICE-API-KEY";
	
	public static String getCognitiveServiceApiKey() {
		return System.getenv(COGNITIVE_SERVICE_API_KEY_LABEL);
	}
}
