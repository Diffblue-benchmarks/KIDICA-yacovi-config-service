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

	private static String buildSearchUrl(String searchTerm) {
		return BING_SEARCH_URL + "?q=" + searchTerm + "&cc=de";
	}

	private static String buildReferenceSearchUrl(String appendix) {
		return BING_URL + appendix + "&cc=de";
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
		List<Element> elements = document.select(".carousel-content a.cardToggle");

		if (elements.isEmpty()) {
			return new ArrayList<>();
		}


		int count = RESULT_COUNT;
		if (elements.size() <= count) {
			count = elements.size();
		}

		elements = elements.subList(0, count);

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

	public static List<SearchResult> getSearchReferencesFor(String searchTerm) throws IOException {
		System.out.println("Searching bing for " + searchTerm);
		Document document = Jsoup.connect(buildSearchUrl(searchTerm)).userAgent(USER_AGENT).get();
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
		Document document = Jsoup.connect(buildSearchUrl(searchTerm)).userAgent(USER_AGENT).get();

		try {
			Element referenceHeadingClickable = document.selectFirst(".b_entityTP").selectFirst(".b_float_img").selectFirst(".rms_iac");

			if (referenceHeadingClickable == null) {
				return "";
			}

			return BING_URL + referenceHeadingClickable.attr("data-src");
		} catch (Exception e) {
			return "";
		}
	}
}
