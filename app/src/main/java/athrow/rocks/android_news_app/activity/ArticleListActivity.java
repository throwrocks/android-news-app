package athrow.rocks.android_news_app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import athrow.rocks.android_news_app.R;
import athrow.rocks.android_news_app.adapter.ArticleListAdapter;
import athrow.rocks.android_news_app.data.Articles;
import athrow.rocks.android_news_app.data.FetchTask;

import java.util.ArrayList;

public class ArticleListActivity extends AppCompatActivity implements OnTaskCompleted {
    ArrayList<Articles> mArticles;
    ArticleListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        OnTaskCompleted onTaskCompleted = this;

        FetchTask fetchTask = new FetchTask(onTaskCompleted);
        fetchTask.execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new ArticleListAdapter(mArticles);
        recyclerView.setAdapter(mAdapter);
    }

    public void onTaskComplete(ArrayList<Articles> articles){
        mArticles = articles;
        View recyclerView = findViewById(R.id.article_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        mAdapter.notifyDataSetChanged();
    }

}
