package com.kiongroup.dc.function.search;

import static com.kiongroup.dc.function.search.Constants.*;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleReferenceSearcher {

	private static String buildGoogleSearchUrl(String searchTerm) throws UnsupportedEncodingException {
		return BING_SEARCH_URL + "?q=" + searchTerm;
	}

	private static String buildReferenceSearchUrl(String appendix) {
		return BING_URL + appendix;
	}

	private static String getReferenceSearchUrl(Document document) {
		try {
			Element referenceHeadingClickable = document.selectFirst(".b_entityTP").select(".b_subModule").last().selectFirst(".b_moreLink");

			if (referenceHeadingClickable == null) {
				return "";
			}

			return referenceHeadingClickable.attr("href");
		} catch (Exception e) {
			return "";
		}
	}

	private static List<Element> extractSearchResultChildren(Document document) {
		List<Element> elements = document.select(".carousel-content a.cardToggle").subList(0, RESULT_COUNT);

		if (elements.isEmpty()) {
			return new Elements();
		}

		return elements;
	}

	private static String extractDataContent(Element element) {
		return element.attr("title");
	}

	private static String removeNonAsciiCharacters(String input) {
		return input.replaceAll(REGEX_NON_ASCII_CHARS, " ").trim();
	}

	public static List<String> googleSearchReferencesFor(String searchTerm) throws IOException {
		System.out.println("Searching bing for " + searchTerm);
		Document document = Jsoup.connect(buildGoogleSearchUrl(searchTerm)).userAgent(USER_AGENT).get();
		String referenceUrl = getReferenceSearchUrl(document);
		if (referenceUrl.isEmpty()) {
			return new ArrayList<>();
		}
		String googleReferenceUrl = buildReferenceSearchUrl(referenceUrl);

		Document referenceDocument = Jsoup.connect(googleReferenceUrl).userAgent(USER_AGENT).get();

		return extractSearchResultChildren(referenceDocument)
				.stream()
				.map(GoogleReferenceSearcher::extractDataContent)
				.filter(data -> !StringUtil.isBlank(data))
				.map(GoogleReferenceSearcher::removeNonAsciiCharacters)
				.collect(toList());
	}
}
