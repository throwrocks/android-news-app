package athrow.rocks.android_news_app.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * JSONParser
 * Created by jose on 8/7/16.
 */
public class JSONParser {
    // Field constants
    private static final String ARTICLE_ID = "id";
    private static final String ARTICLE_TYPE = "type";
    private static final String ARTICLE_SECTION_ID = "sectionId";
    private static final String ARTICLE_SECTION_NAME = "sectionName";
    private static final String ARTICLE_WEB_PLUBLICATION_DATE = "webPublicationDate";
    private static final String ARTICLE_WEB_TITLE = "webTitle";
    private static final String ARTICLE_WEB_URL = "webUrl";
    private static final String ARTICLE_API_URL = "apiUrl";
    private static final String ARTICLE_IS_HOSTED = "isHosted";
    private static ArrayList<Articles> mArticlesArrayList;

    /**
     * JSONParser Constructor
     */
    public JSONParser() {
    }

    public static ArrayList<Articles> getArticlesFromJSON(String articlesJSONString) {
        //Log.e("String : ", "" + articlesJSONString);
        try {
            // Create the articlesJSON object with the articlesJSONString parameter
            JSONObject jsonObject = new JSONObject(articlesJSONString);
            //Log.e("JSONObject: ", "" + jsonObject);
            JSONObject responseObject = jsonObject.getJSONObject("response");
            //JSONObject resultsObject = responseObject.getJSONObject("results");
            JSONArray resultsArray = responseObject.getJSONArray("results");
            //Log.e("ResultsArray: ", "" + resultsArray);
            int articlesQty = resultsArray.length();

            mArticlesArrayList = new ArrayList<>();
            // Loop through the articlesArray to parse each Json object needed
            for (int i = 0; i < articlesQty; i++) {
                JSONObject ArticleRecord = resultsArray.getJSONObject(i);
                //Log.e(LOG_TAG, "ArticleRecord: " + ArticleRecord);
                // Parse the individual data elements needed
                String articleId = ArticleRecord.getString(ARTICLE_ID);
                String articleType = ArticleRecord.getString(ARTICLE_TYPE);
                String articleSectionId = ArticleRecord.getString(ARTICLE_SECTION_ID);
                String articleSectionName = ArticleRecord.getString(ARTICLE_SECTION_NAME);
                String articleWebPublicationDate = ArticleRecord.getString(ARTICLE_WEB_PLUBLICATION_DATE);
                String articleWebTitle = ArticleRecord.getString(ARTICLE_WEB_TITLE);
                String articleWebUrl = ArticleRecord.getString(ARTICLE_WEB_URL);
                String articleApiUrl = ArticleRecord.getString(ARTICLE_API_URL);
                Boolean articleIsHosted = ArticleRecord.getBoolean(ARTICLE_IS_HOSTED);
                // Create an Article Object
                Articles article = new Articles(
                        articleId,
                        articleType,
                        articleSectionId,
                        articleSectionName,
                        articleWebPublicationDate,
                        articleWebTitle,
                        articleWebUrl,
                        articleApiUrl,
                        articleIsHosted
                );
                // Add the Article Object to the ArrayList
                mArticlesArrayList.add(article);
            }
        } catch (JSONException e) {
            Log.e("Exception: ", "" + e);
            e.printStackTrace();
        }
        //Log.e("Parse ", "" + mArticlesArrayList.size());
        return mArticlesArrayList;
    }

}
