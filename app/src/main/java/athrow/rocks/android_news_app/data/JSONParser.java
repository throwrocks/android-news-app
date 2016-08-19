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
    private static final String JSON_RESPONSE = "response";
    private static final String JSON_RESULTS = "results";
    private static final String JSON_TAGS = "tags";
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
        try {
            // Create the articlesJSON object with the articlesJSONString parameter
            JSONObject jsonObject = new JSONObject(articlesJSONString);
            JSONObject responseObject = jsonObject.getJSONObject(JSON_RESPONSE);
            JSONArray resultsArray = responseObject.getJSONArray(JSON_RESULTS);
            int articlesQty = resultsArray.length();
            mArticlesArrayList = new ArrayList<>();
            // Loop through the articlesArray to parse each Json object needed
            for (int i = 0; i < articlesQty; i++) {
                JSONObject articleRecord = resultsArray.getJSONObject(i);
                // Parse the individual data elements needed
                String articleId = articleRecord.getString(ARTICLE_ID);
                String articleType = articleRecord.getString(ARTICLE_TYPE);
                String articleSectionId = articleRecord.getString(ARTICLE_SECTION_ID);
                String articleSectionName = articleRecord.getString(ARTICLE_SECTION_NAME);
                String articleWebPublicationDate = articleRecord.getString(ARTICLE_WEB_PLUBLICATION_DATE);
                String articleWebTitle = articleRecord.getString(ARTICLE_WEB_TITLE);
                String articleWebUrl = articleRecord.getString(ARTICLE_WEB_URL);
                String articleApiUrl = articleRecord.getString(ARTICLE_API_URL);
                Boolean articleIsHosted = articleRecord.getBoolean(ARTICLE_IS_HOSTED);
                // Try to get the author from the tags
                JSONArray articleTagsArray = articleRecord.getJSONArray(JSON_TAGS);
                String articleAuthor = "No Author";
                try {
                    JSONObject articleContributor = articleTagsArray.getJSONObject(0);
                    articleAuthor = articleContributor.getString(ARTICLE_WEB_TITLE);
                } catch (JSONException ignored) {
                }
                // Try to get the image
                String articleFile = "No Image";
                try {
                    JSONObject articleBlocks = articleRecord.getJSONObject("blocks");
                    JSONObject articleRequestedBlocks = articleBlocks.getJSONObject("requestedBodyBlocks");
                    JSONArray articleBodyLatestArray = articleRequestedBlocks.getJSONArray("body:latest");
                    JSONObject articleBodyLatest = articleBodyLatestArray.getJSONObject(0);
                    JSONArray articleElements = articleBodyLatest.getJSONArray("elements");
                    JSONObject articleAssets = articleElements.getJSONObject(1);
                    JSONArray articleImageArray = articleAssets.getJSONArray("assets");
                    JSONObject articleImage = articleImageArray.getJSONObject(3);
                    articleFile = articleImage.getString("file");

                } catch (JSONException ignored) {
                }
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
                        articleIsHosted,
                        articleAuthor,
                        articleFile
                );
                // Add the Article Object to the ArrayList
                mArticlesArrayList.add(article);
            }
        } catch (JSONException e) {
            Log.e("Exception: ", "" + e);
            e.printStackTrace();
        }
        return mArticlesArrayList;
    }

}
