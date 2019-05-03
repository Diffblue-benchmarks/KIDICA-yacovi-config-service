package com.kiongroup.dc.function;

import static com.microsoft.azure.functions.HttpMethod.GET;
import static com.microsoft.azure.functions.annotation.AuthorizationLevel.ANONYMOUS;

import java.util.Optional;

import com.kiongroup.dc.function.config.CognitiveServiceApiKeyHttpResponse;
import com.kiongroup.dc.function.config.CognitiveServiceApiKeyProvider;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

public class ConfigFunction extends AbstractAuthenticatedFunction {
	
	@FunctionName("GetConfig")
	public HttpResponseMessage getConfig(
		@HttpTrigger(name = "req", methods = {GET}, authLevel = ANONYMOUS) HttpRequestMessage<Optional<String>> request,
		final ExecutionContext context) {
		return createResponse(request);
	}

	@Override
	protected <T> HttpResponseMessage handleResponseCreation(HttpRequestMessage<T> request) throws Exception {
		String key = CognitiveServiceApiKeyProvider.getCognitiveServiceApiKey();
		return successResponse(request, new CognitiveServiceApiKeyHttpResponse(key));
	}
}