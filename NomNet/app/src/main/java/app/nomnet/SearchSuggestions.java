package app.nomnet;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Rebecca on 4/23/2015.
 */
public class SearchSuggestions extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.nomnet.SearchSuggestions";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestions() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
