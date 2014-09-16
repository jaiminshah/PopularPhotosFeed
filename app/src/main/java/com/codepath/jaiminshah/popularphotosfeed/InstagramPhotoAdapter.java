package com.codepath.jaiminshah.popularphotosfeed;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by jaimins on 9/12/14.
 */
public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotoAdapter(Context context, ArrayList<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.cimgUser = (ImageView) convertView.findViewById(R.id.imgUser);
            viewHolder.imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.tvTimeElapsed = (TextView) convertView.findViewById(R.id.tvTimeElapsed);
            viewHolder.imgTime = (ImageView) convertView.findViewById(R.id.imgTime);
            viewHolder.lvComments = (ListView)convertView.findViewById(R.id.lvComments);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Set the Username text
        viewHolder.tvUsername.setText(photo.username);

        // Reset the image from the recycled view
        viewHolder.cimgUser.setImageResource(0);
        if (photo.userImgUrl != null) {
            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.LTGRAY)
                    .borderWidthDp(1)
                    .cornerRadiusDp(30)
                    .oval(false)
                    .build();

            // fetch the profile picture for the user
            Picasso.with(getContext())
                    .load(photo.userImgUrl)
                    .fit()
                    .transform(transformation)
                    .into(viewHolder.cimgUser);
        }
        // Set the height of the image view to be same the screen width as a placeholder.
        // Instagram images seems to be all squares
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        viewHolder.imgPhoto.getLayoutParams().height = displayMetrics.widthPixels;

        // fetch the photo
        viewHolder.imgPhoto.setImageResource(0);
        Picasso.with(getContext())
                .load(photo.imageUrl)
                .into(viewHolder.imgPhoto);

        //Set time elapsed for photo
        CharSequence relative_time = DateUtils.getRelativeTimeSpanString(photo.created_time*1000, System.currentTimeMillis(),DateUtils.SECOND_IN_MILLIS);
        viewHolder.tvTimeElapsed.setText(relative_time);

        //Set number of likes
        viewHolder.tvLikes.setText(Integer.toString(photo.likesCount));

        // Set the caption for the photo
        viewHolder.tvCaption.setText(photo.getFormattedCaption());

        CommentAdapter aComments = new CommentAdapter(getContext(),photo.comments);
        viewHolder.lvComments.setAdapter(aComments);


        return convertView;
    }

    //View lookup Cache
    private static class ViewHolder {
        TextView tvUsername;
        ImageView cimgUser;
        ImageView imgPhoto;
        TextView tvLikes;
        TextView tvCaption;
        TextView tvTimeElapsed;
        ImageView imgTime;
        ListView lvComments;
    }
}
