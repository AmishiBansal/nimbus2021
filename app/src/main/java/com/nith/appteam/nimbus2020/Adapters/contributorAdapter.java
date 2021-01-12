package com.nith.appteam.nimbus2020.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.nith.appteam.nimbus2020.Models.contributorsItem;
import com.nith.appteam.nimbus2020.R;

import java.util.ArrayList;

/**
 * Created by jaykay12 on 8/3/17.
 */
public class contributorAdapter extends RecyclerView.Adapter<contributorAdapter.ViewHolder> {
    ArrayList<contributorsItem> contributorsItemArrayList;
    Context context;

    public contributorAdapter(ArrayList<contributorsItem> contributorsItemArrayList, Context context) {
        this.contributorsItemArrayList = contributorsItemArrayList;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return contributorsItemArrayList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_contributors, parent, false);
        ViewHolder view_holder = new ViewHolder(view);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (contributorsItemArrayList.get(position).name.isEmpty()) {
        } else {
            holder.contributorname.setText(contributorsItemArrayList.get(position).name);
        }
        if (!(contributorsItemArrayList.get(position).github_url.isEmpty())) {
            final String url = (contributorsItemArrayList.get(position).github_url);

            holder.githublink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.ease_in, R.anim.ease_out);


                }
            });
        }

        if (!(contributorsItemArrayList.get(position).img_url == null)) {
            Glide.with(context).asBitmap().load(contributorsItemArrayList.get(position).img_url).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.person_icon).into(new ImageViewTarget<Bitmap>(holder.image_url) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    drawable.setCircular(true);
                    holder.image_url.setImageDrawable(drawable);
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView contributorname;
        public final TextView githublink;
        ImageView image_url;

        public ViewHolder(View v) {
            super(v);
            this.image_url = v.findViewById(R.id.contributor_pic);
            this.contributorname = v.findViewById(R.id.contributor_name);
            this.githublink = v.findViewById(R.id.contributor_link);
            image_url.setClipToOutline(true);
        }
    }
}

