package com.loop.finalnews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class Load  extends AsyncTaskLoader<List<Model>> {
    private static String URL;
    public Load(Context context,String url) {
        super(context);
        URL = url;
    }

    @Override
    protected void onStartLoading() {
        onForceLoad();
    }
    @Override
    public List<Model> loadInBackground() {
        if (URL == null) {
            return null;
        }

        List<Model> news1 = QueryUtils.fetchNewsData(URL);
        return news1;
    }
}
