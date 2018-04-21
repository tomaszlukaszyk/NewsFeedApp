package com.example.android.newsfeedapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<NewsArticle>> {

    // Query URL
    private String url;

    /**
     * Constructs a new {@link ArticleLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    ArticleLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    // Task to do on background thread
    @Override
    public List<NewsArticle> loadInBackground() {
        // If there is no URL exit from the method early
        if (url == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of articles.
        return QueryUtils.fetchArticleData(url);
    }
}
