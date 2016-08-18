package athrow.rocks.android_news_app.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import athrow.rocks.android_news_app.R;
import athrow.rocks.android_news_app.data.Articles;
import athrow.rocks.android_news_app.util.Utilities;

/**
 * ArticleListAdapter
 * Created by jose on 8/10/16.
 */
public class ArticleListAdapter
        extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {
    private final static String DATE_DISPLAY = "MMM dd, yyyy";;
    private final ArrayList<Articles> mValues;

    public ArticleListAdapter(ArrayList<Articles> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        // Get the article variables
        String title = holder.mItem.getWebTitle();
        String date = holder.mItem.getWebPublicationDate();
        String section = holder.mItem.getSectionName();
        String type = holder.mItem.getType();
        // Convert the date to readable format
        Date dateObj = Utilities.getStringAsDate(date,DATE_DISPLAY,null);
        String dateSting = Utilities.getDateAsString(dateObj, DATE_DISPLAY, null);
        // Set the views
        holder.mArticleTitleView.setText(title);
        holder.mArticleDateView.setText(dateSting);
        holder.mArticleSection.setText(section);
        holder.mArticleType.setText(type);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mValues != null) {
            return mValues.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mArticleTitleView;
        public final TextView mArticleDateView;
        public final TextView mArticleSection;
        public final TextView mArticleType;

        public Articles mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mArticleTitleView = (TextView) view.findViewById(R.id.article_title);
            mArticleDateView = (TextView) view.findViewById(R.id.article_date);
            mArticleSection = (TextView) view.findViewById(R.id.article_section);
            mArticleType = (TextView) view.findViewById(R.id.article_type);

        }
    }
}