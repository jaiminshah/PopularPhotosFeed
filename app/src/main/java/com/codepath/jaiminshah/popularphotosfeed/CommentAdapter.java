package com.codepath.jaiminshah.popularphotosfeed;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by jaimins on 9/15/14.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {
    public CommentAdapter(Context context, ArrayList<Comment> objects) {
        super(context, R.layout.item_comment, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
            viewHolder.imgComment = (ImageView) convertView.findViewById(R.id.imgComment);
            viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.LTGRAY)
                .borderWidthDp(1)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        // fetch the profile picture for the user
        Picasso.with(getContext())
                .load(comment.userImgUrl)
                .fit()
                .transform(transformation)
                .into(viewHolder.imgComment);

        viewHolder.tvComment.setText(comment.getFormattedComment());

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgComment;
        TextView tvComment;
    }
}
