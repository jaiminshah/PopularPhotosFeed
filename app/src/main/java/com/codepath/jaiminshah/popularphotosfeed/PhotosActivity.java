package com.codepath.jaiminshah.popularphotosfeed;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;


public class PhotosActivity extends Activity implements OnRefreshListener {
    private static final String CLIENT_ID = "9cc126c2f956488586f13b3f4c116364";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotoAdapter aPhotos;
    private PullToRefreshLayout mPullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptrlPhotosFeed);

        ActionBarPullToRefresh.from(this)
                .options(Options.create()
                        .scrollDistance(0.4f)
                        .build())
                .allChildrenArePullable()
                .listener(this)
                .setup(mPullToRefreshLayout);

        fetchPopularPhotos();


    }

    private void fetchPopularPhotos() {
        photos = new ArrayList<InstagramPhoto>();

        aPhotos = new InstagramPhotoAdapter(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aPhotos);

        //Create Url
        String popPhotosUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        //Creat a network client
        AsyncHttpClient client = new AsyncHttpClient();
        //Trigger Network request
        client.get(popPhotosUrl, new JsonHttpResponseHandler() {
            //define success and failure callbacks

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJSON = null;
                try {
                    photos.clear();
                    photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); ++i) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto(photoJSON);
//                        photo.setLocality(getBaseContext());
                        photos.add(photo);
                    }
                    aPhotos.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mPullToRefreshLayout.setRefreshComplete();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                mPullToRefreshLayout.setRefreshComplete();
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    @Override
    public void onRefreshStarted(View view) {
        fetchPopularPhotos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
