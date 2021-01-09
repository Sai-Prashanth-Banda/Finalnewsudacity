package com.loop.finalnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Model> {

    public Adapter(Context context, ArrayList<Model> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View cv, ViewGroup p1) {
        View view = cv;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.news, p1, false);
        }

        Model news = getItem(position);
        TextView tv = view.findViewById(R.id.title);
        String title = news.mainnews();
        tv.setText(title);


        TextView tv1 = view.findViewById(R.id.tv1);
        String t1 = news.type();
        tv1.setText(t1);

        TextView tv2 = view.findViewById(R.id.tv2);
        String t2 = news.getDate();
        tv2.setText(t2);

        TextView tv3 = view.findViewById(R.id.tv3);
        String t3 = news.name();
        tv3.setText(t3);
        return view;
    }
}