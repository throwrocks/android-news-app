package athrow.rocks.android_news_app.data;;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import athrow.rocks.android_news_app.activity.OnTaskCompleted;

/**
 * FetchTask
 * Created by jose on 8/7/16.
 */
public class FetchTask extends AsyncTask<String, Void, ArrayList<Articles>> {
    public OnTaskCompleted listener = null;

    public FetchTask(OnTaskCompleted listener){
        this.listener = listener;
    }

    @Override
    protected ArrayList<Articles> doInBackground(String... String) {
        String jsonResults = API.callAPI();
        Log.e("Json ", jsonResults);
        return JSONParser.getArticlesFromJSON(jsonResults);
    }

    @Override
    protected void onPostExecute(ArrayList<Articles> articlesArrayList) {
        super.onPostExecute(articlesArrayList);
        Log.e("Array ", "" + articlesArrayList.size());
        listener.onTaskComplete(articlesArrayList);
    }

}
