package com.example.android.newsfeedapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsArticle>> {

    // Constant value for the article loader ID.
    private static final int ARTICLE_LOADER_ID = 1;

    /**
     * URL for article data from the Guardian API
     */
    private static final String REQUEST_URL =
            "http://content.guardianapis.com/search?section=science&show-fields=byline&api-key=test";

    // Adapter that provides views for the RecyclerView
    private ListAdapter adapter;

    // RecyclerView in the layout
    private RecyclerView recyclerView;

    // Progress bar
    private ProgressBar progressBar;

    // TextView that shows when RecyclerView is empty
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the progress bar
        progressBar = findViewById(R.id.progress_bar);

        // Find the TextView that shows when RecyclerView is empty
        emptyView = findViewById(R.id.empty_view);

        // Find the RecyclerView in the layout
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Set the adapter on the RecyclerView
        adapter = new ListAdapter(new ArrayList<NewsArticle>());
        recyclerView.setAdapter(adapter);

        // Check if there is network connection
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            // Initialize the loader
            getLoaderManager().initLoader(ARTICLE_LOADER_ID, null, this);
        } else {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_network);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new ArticleLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsArticle>> loader, List<NewsArticle> earthquakes) {

        // Hide the progress bar
        progressBar.setVisibility(View.GONE);

        if (earthquakes != null && !earthquakes.isEmpty()) {
            // Set the loaded data set to the adapter
            adapter.setArticles(earthquakes);

            // Show the RecyclerView and hide the no data message
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        } else {
            // Show the no data message and hide the RecyclerView
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsArticle>> loader) {
        // Set the empty data set to the adapter
        adapter.setArticles(new ArrayList<NewsArticle>());
    }
}
