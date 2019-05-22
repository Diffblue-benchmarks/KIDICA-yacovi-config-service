package com.kiongroup.dc.function.search;

import static com.kiongroup.dc.function.search.Constants.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.kiongroup.dc.function.core.model.SearchResult;
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

	private static SearchResult extractDataContent(Element element) {
		String result = element.attr("title");
		String image = BING_URL + element.selectFirst("img").attr("src");

		return new SearchResult(result, image);
	}

	private static SearchResult removeNonAsciiCharacters(SearchResult input) {
		return input.setTitle(input.getReferenceTitle().replaceAll(REGEX_NON_ASCII_CHARS, " ").trim());
	}

	public static List<SearchResult> googleSearchReferencesFor(String searchTerm) throws IOException {
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
				.filter(data -> !StringUtil.isBlank(data.getReferenceTitle()))
				.map(GoogleReferenceSearcher::removeNonAsciiCharacters)
				.collect(toList());
	}

	public static String getImage(String searchTerm) throws IOException {
		Document document = Jsoup.connect(buildGoogleSearchUrl(searchTerm)).userAgent(USER_AGENT).get();

		try {
			Element referenceHeadingClickable = document.selectFirst(".b_entityTP").selectFirst(".b_float_img").selectFirst("img");

			System.out.println(document.selectFirst(".b_entityTP").selectFirst(".b_float_img"));
			System.out.println(referenceHeadingClickable);

			if (referenceHeadingClickable == null) {
				return "";
			}

			return referenceHeadingClickable.attr("src");
		} catch (Exception e) {
			return "";
		}
	}
}
