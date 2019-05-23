package com.kiongroup.dc.function;

import static com.microsoft.azure.functions.HttpMethod.GET;
import static com.microsoft.azure.functions.HttpStatus.BAD_REQUEST;
import static com.microsoft.azure.functions.HttpStatus.OK;
import static com.microsoft.azure.functions.annotation.AuthorizationLevel.ANONYMOUS;

import java.util.List;

import com.kiongroup.dc.function.core.model.SearchResult;
import org.jsoup.helper.StringUtil;

import com.kiongroup.dc.function.search.GoogleReferenceSearcher;
import com.kiongroup.dc.function.search.SearchReferencesHttpResponse;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

public class SearchReferenceFunction extends AbstractAuthenticatedFunction {

	@FunctionName("SearchReferences")
	public HttpResponseMessage searchReferences(
		@HttpTrigger(name = "req", methods = {GET}, authLevel = ANONYMOUS) HttpRequestMessage<List<String>> request,
		final ExecutionContext context) {
		// return request.createResponseBuilder(OK).body("Test").build();

		return createResponse(request);
	}

	@Override
	protected <T> HttpResponseMessage handleResponseCreation(HttpRequestMessage<T> request) throws Exception {
		String searchTerm = request.getQueryParameters().get("searchTerm");
		
		if (StringUtil.isBlank(searchTerm)) {
			return errorReponse(request, BAD_REQUEST, new UnsupportedOperationException("SearchTerm can't be empty"));
		}

		List<SearchResult> results = GoogleReferenceSearcher.getSearchReferencesFor(searchTerm);
		return successResponse(request, new SearchReferencesHttpResponse(results));
	}
}