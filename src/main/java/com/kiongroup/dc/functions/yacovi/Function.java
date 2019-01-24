package com.kiongroup.dc.functions.yacovi;

import com.kiongroup.dc.functions.yacovi.core.mixin.AuthenticationMixin;
import com.kiongroup.dc.functions.yacovi.core.model.YaCoViHttpErrorResponse;
import com.kiongroup.dc.functions.yacovi.core.model.YaCoViHttpResponse;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import java.util.Optional;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function implements AuthenticationMixin {

    private static final String YACOVI_COGNITIVE_SERVICE_API_KEY_LABEL = "YACOVI-COGNITIVE-SERVICE-API-KEY";

    @FunctionName("GetYaCoViConfig")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        context.getLogger().info("YaCoVi Config Service processed a request.");

        String apiKey = System.getenv(YACOVI_COGNITIVE_SERVICE_API_KEY_LABEL);
        String authorizationHeader = request.getHeaders().getOrDefault(HttpHeaders.AUTHORIZATION.toLowerCase(), "");

        Boolean isAuthenticated = this.isJwtValid(authorizationHeader);

        HttpStatus status = (isAuthenticated) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        String responseBody = (isAuthenticated) ? new YaCoViHttpResponse(apiKey).toJson() : new YaCoViHttpErrorResponse(status.name()).toJson();

        return request.createResponseBuilder(status)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .body(responseBody)
            .build();

    }
}
