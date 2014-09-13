package com.codepath.jaiminshah.popularphotosfeed;

import org.json.JSONException;
import org.json.JSONObject;

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

    public InstagramPhoto(JSONObject photoJSON) {
        try {
            if (!photoJSON.isNull("user")){

                this.username   = photoJSON.getJSONObject("user").getString("username");
                this.userImgUrl = photoJSON.getJSONObject("user").getString("profile_picture");
            }
            if (!photoJSON.isNull("caption")) {
                this.caption    = photoJSON.getJSONObject("caption").getString("text");
            }
            if (!photoJSON.isNull("images")) {
                this.imageUrl   = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                this.imgHeight  = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                this.imgWidth  = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("width");
            }
            if (!photoJSON.isNull("likes")) {
                this.likesCount = photoJSON.getJSONObject("likes").getInt("count");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
