package com.loop.finalnews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Model>> {
private Adapter adap;
private TextView tv;
private static String URL="https://content.guardianapis.com/search?api-key=57778053-1565-4609-b191-4aee452f62f4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = findViewById(R.id.lv);
        tv = findViewById(R.id.tv);
        lv.setEmptyView(tv);
        adap = new Adapter(this, new ArrayList<Model>());
        lv.setAdapter(adap);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Model cn = adap.getItem(position);
        Uri mainnews = Uri.parse(cn.url());
        Intent i = new Intent(Intent.ACTION_VIEW, mainnews);
        startActivity(i);
        }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
        LoaderManager lm = getLoaderManager();
        lm.initLoader(1, null, this);
        } else {
        tv.setText("No_internet_connection");
        }
        }
@Override
public Loader<List<Model>> onCreateLoader ( int id, Bundle args){
        Uri u1 = Uri.parse(URL);
        Uri.Builder u2 = u1.buildUpon();
        return new Load(this, u2.toString());
        }

@Override
public void onLoadFinished (Loader < List < Model >> loader, List < Model > news){
        tv.setText("No news here");
        adap.clear();

        if (news != null && !news.isEmpty()) {
        adap.addAll(news);
        }
        }
@Override
public void onLoaderReset (Loader < List < Model >> loader) {
        adap.clear();
        }

}
