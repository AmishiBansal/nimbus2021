package com.nith.appteam.nimbus2021.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nith.appteam.nimbus2021.Models.Sponsor;
import com.nith.appteam.nimbus2021.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SponsorsAdapter extends RecyclerView.Adapter<SponsorsAdapter.SponsorsViewHolder> {

    List<Sponsor> mSponsorList;
    Activity mActivity;

    public SponsorsAdapter(List<Sponsor> sponsorList, Activity activity) {
        mSponsorList = sponsorList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public SponsorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sponsors, parent, false);
        return new SponsorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SponsorsViewHolder holder, int position) {

        final Sponsor sponsor = mSponsorList.get(position);
        if(!sponsor.getName().isEmpty() && !sponsor.getName().contains("null"))
            holder.sponsorTextView.setText(sponsor.getName());
//        if (!sponsor.getImageUrl().isEmpty())
        Glide.with(mActivity).load(sponsor.getImageUrl()).placeholder(R.drawable.noimageplaceholder).apply(new RequestOptions().override(80, 80)).into(holder.sponsorImageView);
        Log.e("imageUrl",sponsor.getImageUrl());
        if(!sponsor.getLink().isEmpty() && !sponsor.getLink().contains("null")){
            holder.sponsorCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Uri uri = Uri.parse(sponsor.getLink()); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        mActivity.startActivity(intent);
                }
            });
        }
        }


    @Override
    public int getItemCount() {
        return mSponsorList.size();
    }


    public class SponsorsViewHolder extends RecyclerView.ViewHolder {

        ImageView sponsorImageView;
        TextView sponsorTextView;
        CardView sponsorCardView;

        public SponsorsViewHolder(@NonNull View itemView) {
            super(itemView);
            sponsorImageView = itemView.findViewById(R.id.sponsorImageView);
            sponsorTextView = itemView.findViewById(R.id.sponsorTextView);
            sponsorCardView = itemView.findViewById(R.id.card_sponsor);
            sponsorImageView.setClipToOutline(true);
        }
    }
}
