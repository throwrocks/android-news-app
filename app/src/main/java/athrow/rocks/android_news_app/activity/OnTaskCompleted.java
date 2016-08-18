package athrow.rocks.android_news_app.activity;

import java.util.ArrayList;

import athrow.rocks.android_news_app.data.Articles;

/**
 * OnTaskCompleted
 * Interface to pass the response back from the FetchTask to the ArticleListActivity
 * Created by josel on 8/16/2016.
 */
public interface OnTaskCompleted {
    void onTaskComplete(ArrayList<Articles> articles);
}