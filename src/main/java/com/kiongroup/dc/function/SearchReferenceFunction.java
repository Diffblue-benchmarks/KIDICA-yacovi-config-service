package com.kiongroup.dc.function;

import static com.kiongroup.dc.function.search.Constants.USER_AGENT;
import static com.microsoft.azure.functions.HttpMethod.GET;
import static com.microsoft.azure.functions.HttpStatus.BAD_REQUEST;
import static com.microsoft.azure.functions.HttpStatus.OK;
import static com.microsoft.azure.functions.annotation.AuthorizationLevel.ANONYMOUS;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.kiongroup.dc.function.core.model.SearchResult;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;

import com.kiongroup.dc.function.search.GoogleReferenceSearcher;
import com.kiongroup.dc.function.search.SearchReferencesHttpResponse;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SearchReferenceFunction extends AbstractAuthenticatedFunction {

	@FunctionName("SearchReferences")
	public HttpResponseMessage searchReferences(
		@HttpTrigger(name = "req", methods = {GET}, authLevel = ANONYMOUS) HttpRequestMessage<List<String>> request,
		final ExecutionContext context) throws IOException {
		Document document = Jsoup.connect(GoogleReferenceSearcher.buildSearchUrl(request.getQueryParameters().get("searchTerm"))).userAgent(USER_AGENT).get();
		String referenceUrl = GoogleReferenceSearcher.getReferenceSearchUrl(document);
		if (referenceUrl.isEmpty()) {
			request.createResponseBuilder(OK).body("Reference url empty").build();
		}
		String googleReferenceUrl = GoogleReferenceSearcher.buildReferenceSearchUrl(referenceUrl);

		Document referenceDocument = Jsoup.connect(googleReferenceUrl).userAgent(USER_AGENT).get();
		if (referenceDocument == null) {
			request.createResponseBuilder(OK).body("Reference document is null").build();
		}

		List<Element> elements = document.select(".carousel-content a.cardToggle");
		return request.createResponseBuilder(OK).body(document.select(".carousel-content")).build();

		//return createResponse(request);
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