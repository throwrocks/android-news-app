package athrow.rocks.android_news_app.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        OnTaskCompleted onTaskCompleted = this;

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            FetchTask fetchTask = new FetchTask(onTaskCompleted);
            fetchTask.execute();
        } else {
            CharSequence text = getString(R.string.no_network_connection);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new ArticleListAdapter(mArticles, getApplicationContext());
        recyclerView.setAdapter(mAdapter);
    }

    public void onTaskComplete(ArrayList<Articles> articles) {
        mArticles = articles;
        View recyclerView = findViewById(R.id.article_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        mAdapter.notifyDataSetChanged();
    }

}
