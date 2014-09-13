package com.codepath.jaiminshah.popularphotosfeed;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jaimins on 9/12/14.
 */
public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {
    //View lookup Cache
    private static class ViewHolder{
        TextView tvUsername;
        ImageView cimgUser;
        ImageView imgPhoto;
        TextView tvCaption;

    }

    public InstagramPhotoAdapter(Context context, ArrayList<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent,false);
            viewHolder.tvUsername   = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.cimgUser     = (ImageView) convertView.findViewById(R.id.cimgUser);
            viewHolder.imgPhoto     = (ImageView) convertView.findViewById(R.id.imgPhoto);
            viewHolder.tvCaption    = (TextView) convertView.findViewById(R.id.tvCaption);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Set the Username text
        viewHolder.tvUsername.setText(photo.username);

        // Reset the image from the recycled view
        viewHolder.cimgUser.setImageResource(0);
        if (photo.userImgUrl != null) {
            // fetch the profile picture for the user
            Picasso.with(getContext()).load(photo.userImgUrl).into(viewHolder.cimgUser);
        }
        // Set the height of the image view to be same the screen width as a placeholder.
        // Instagram images seems to be all squares
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        viewHolder.imgPhoto.getLayoutParams().height = displayMetrics.widthPixels;

        // fetch the photo
        viewHolder.imgPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(viewHolder.imgPhoto);

        // Set the caption for the photo
        viewHolder.tvCaption.setText(photo.caption);

        return convertView;
    }
}
