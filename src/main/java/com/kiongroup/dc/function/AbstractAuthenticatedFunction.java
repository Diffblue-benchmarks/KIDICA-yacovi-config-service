package com.kiongroup.dc.function;

import static com.microsoft.azure.functions.HttpStatus.INTERNAL_SERVER_ERROR;
import static com.microsoft.azure.functions.HttpStatus.OK;
import static com.microsoft.azure.functions.HttpStatus.UNAUTHORIZED;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;

import com.kiongroup.dc.function.core.mixin.AuthenticationMixin;
import com.kiongroup.dc.function.core.mixin.JsonMixin;
import com.kiongroup.dc.function.core.model.HttpErrorResponse;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import org.glassfish.jersey.internal.util.ExceptionUtils;

public abstract class AbstractAuthenticatedFunction implements AuthenticationMixin {

	protected boolean isAuthenticated(HttpRequestMessage<?> request) {
		String authorizationHeader = request.getHeaders().getOrDefault(HttpHeaders.AUTHORIZATION.toLowerCase(), "");
		return this.isJwtValid(authorizationHeader);
	}
	
	protected static <T> HttpResponseMessage errorReponse(HttpRequestMessage<T> request, HttpStatus status, Exception e) {
		return request
				.createResponseBuilder(status)
				.header(CONTENT_TYPE, APPLICATION_JSON)
				.body(new HttpErrorResponse("#1: " + ExceptionUtils.exceptionStackTraceAsString(e)).toJson())
				.build();
	}
	
	protected static <T> HttpResponseMessage successResponse(HttpRequestMessage<T> request, JsonMixin content) {
		return request
				.createResponseBuilder(OK)
				.header(CONTENT_TYPE, APPLICATION_JSON)
				.body(content.toJson())
				.build();
	}
	
	protected <T> HttpResponseMessage createResponse(HttpRequestMessage<T> request) {
		
		if (!isAuthenticated(request)) {
			return errorReponse(request, UNAUTHORIZED, new NotAuthorizedException("Unauthorized."));
		}

		try {
			return handleResponseCreation(request);
		} catch (Exception e) {
			// TODO log exception
			return errorReponse(request, INTERNAL_SERVER_ERROR, e);
		}
	}
	
	protected abstract <T> HttpResponseMessage handleResponseCreation(HttpRequestMessage<T> request) throws Exception;
}