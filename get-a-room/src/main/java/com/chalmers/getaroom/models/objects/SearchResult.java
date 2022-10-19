package com.chalmers.getaroom.models.objects;

import java.util.List;

/**
 * Contains the result of a search
 *
 * @param searchQuery The search query that created the result
 * @param results     A list of results
 */
public record SearchResult(SearchQuery searchQuery, List<SearchRecord> results) {
}

