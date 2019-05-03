package com.kiongroup.dc.functions.search;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.kiongroup.dc.function.search.GoogleReferenceSearcher;

@Ignore
public class SearchIntegrationTest {

	@Test
	public void someResults() throws IOException {
		executeSearch("kion");
		executeSearch("brad pitt");
		executeSearch("st pauli");
		executeSearch("bärbel schäfer");
		executeSearch("dirk nowitzki");
	}
	
	private void executeSearch(String searchTerm) throws IOException {
		for (String result : GoogleReferenceSearcher.googleSearchReferencesFor(searchTerm)) {
			System.out.printf("'%s' references to '%s' \n", searchTerm, result);
		}
	}
	
	@Test
	public void noResults() throws IOException {
		List<String> results = GoogleReferenceSearcher.googleSearchReferencesFor("bräd pütt");
		assertTrue(results.isEmpty());
	}
}
