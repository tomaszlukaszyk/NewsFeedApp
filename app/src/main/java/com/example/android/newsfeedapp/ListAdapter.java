package com.example.android.newsfeedapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    // List of all articles to display
    private final ArrayList<NewsArticle> articles;
    private Context context;

    // Provides a reference to the views for each data item
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView articleDetails;
        private final TextView articleTitle;

        ViewHolder(View itemView) {
            super(itemView);
            articleDetails = itemView.findViewById(R.id.article_details);
            articleTitle = itemView.findViewById(R.id.article_title);
        }
    }

    // Constructor for adapter
    ListAdapter(ArrayList<NewsArticle> articles) {
        this.articles = articles;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final NewsArticle currentArticle = articles.get(position);

        final String section = currentArticle.getSection();
        final String date = currentArticle.getDate().split("T")[0];
        final String author = currentArticle.getAuthor();

        viewHolder.articleDetails.setText(String.format("%s  %s  %s", section, date, author));

        final String title = currentArticle.getTitle();

        viewHolder.articleTitle.setText(title);

        final String url = currentArticle.getUrl();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build the intent
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));

                // Verify it resolves
                PackageManager packageManager = context.getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(i, 0);
                boolean isIntentSafe = activities.size() > 0;

                // Start an activity if it's safe
                if (isIntentSafe) {
                    context.startActivity(i);
                }
            }
        });
    }

    // Return the size of list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<NewsArticle> new_data) {
        articles.clear();
        articles.addAll(new_data);
        notifyDataSetChanged();
    }
}
