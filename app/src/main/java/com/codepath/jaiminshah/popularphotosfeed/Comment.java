package com.codepath.jaiminshah.popularphotosfeed;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jaimins on 9/15/14.
 */
public class Comment {

    public String username;
    public String userImgUrl;
    public String text;

    public Comment(JSONObject commentJSON) {
        try {
            this.text = commentJSON.getString("text");
            this.username = commentJSON.getJSONObject("from").getString("username");
            this.userImgUrl = commentJSON.getJSONObject("from").getString("profile_picture");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Spannable getFormattedComment() {
        Spannable text = new SpannableString(this.username + " " + this.text);
        text.setSpan(new ForegroundColorSpan(0xFF3F729B), 0, this.username.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new StyleSpan(Typeface.BOLD), 0, this.username.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return text;
    }

}
