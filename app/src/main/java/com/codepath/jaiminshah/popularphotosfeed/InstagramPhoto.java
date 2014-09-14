package com.codepath.jaiminshah.popularphotosfeed;

import android.content.Context;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by jaimins on 9/12/14.
 */
public class InstagramPhoto {
    public String username;
    public String userImgUrl;
    public String caption;
    public String imageUrl;
    public int imgHeight;
    public int imgWidth;
    public int likesCount;
    public double latitude;
    public double longitude;
    public String locality = "Unknown";

    public InstagramPhoto(JSONObject photoJSON) {
        try {
            if (!photoJSON.isNull("user")) {

                this.username = photoJSON.getJSONObject("user").getString("username");
                this.userImgUrl = photoJSON.getJSONObject("user").getString("profile_picture");
            }
            if (!photoJSON.isNull("caption")) {
                this.caption = photoJSON.getJSONObject("caption").getString("text");
            }
            if (!photoJSON.isNull("images")) {
                this.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                this.imgHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                this.imgWidth = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("width");
            }
            if (!photoJSON.isNull("likes")) {
                this.likesCount = photoJSON.getJSONObject("likes").getInt("count");
            }

            if (!photoJSON.isNull("location")) {
                this.latitude = photoJSON.getJSONObject("location").getDouble("latitude");
                this.longitude = photoJSON.getJSONObject("location").getDouble("longitude");
                if (photoJSON.getJSONObject("location").has("name")) {
                    this.locality = photoJSON.getJSONObject("location").getString("name");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setLocality(Context context) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(this.latitude, this.longitude, 1);
            if (addresses.size() > 0) {
                this.locality = addresses.get(0).getLocality();
                if (this.locality == null) {
                    this.locality = "Unknown";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Spannable getFormattedCaption() {
        Spannable text = new SpannableString(this.username + " " + this.caption);
        text.setSpan(new ForegroundColorSpan(0xFF3F729B), 0, this.username.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new StyleSpan(Typeface.BOLD), 0, this.username.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return text;
    }
}
