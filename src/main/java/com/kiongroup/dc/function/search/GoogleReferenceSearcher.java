package com.kiongroup.dc.function.search;

import static com.kiongroup.dc.function.search.Constants.GOOGLE_SEARCH_URL;
import static com.kiongroup.dc.function.search.Constants.REGEX_NON_ASCII_CHARS;
import static com.kiongroup.dc.function.search.Constants.RESULT_COUNT;
import static com.kiongroup.dc.function.search.Constants.USER_AGENT;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleReferenceSearcher {

	private static String buildGoogleSearchUrl(String searchTerm) throws UnsupportedEncodingException {
		return GOOGLE_SEARCH_URL + "?q=" + searchTerm + "&num=" + RESULT_COUNT;
	}

	private static Elements extractSearchResultChildren(Document document) {

		Elements parents = document.select("[data-attrid^='kc:/']").select("[data-attrid$='sideways']");

		if (parents.isEmpty()) {
			return new Elements();
		}

		return parents.first().children().select("[data-reltype='sideways']");
	}

	private static String extractDataContent(Element element) {

		Elements children = element.children().select("div[data-original-name]");

		if (children.isEmpty()) {
			return "";
		}

		return children.first().attr("data-original-name");
	}

	private static String removeNonAsciiCharacters(String input) {
		return input.replaceAll(REGEX_NON_ASCII_CHARS, " ").trim();
	}

	public static List<String> googleSearchReferencesFor(String searchTerm) throws IOException {
		Document document = Jsoup.connect(buildGoogleSearchUrl(searchTerm)).userAgent(USER_AGENT).get();
		return extractSearchResultChildren(document)
				.stream()
				.map(child -> extractDataContent(child))
				.filter(data -> !StringUtil.isBlank(data))
				.map(data -> removeNonAsciiCharacters(data))
				.collect(toList());
	}
}
