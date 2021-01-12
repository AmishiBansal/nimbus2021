package com.nith.appteam.nimbus2020.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nith.appteam.nimbus2020.Models.Sponsor;
import com.nith.appteam.nimbus2020.R;
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

        Sponsor sponsor = mSponsorList.get(position);
        holder.sponsorTextView.setText(sponsor.getName());
//        if (!sponsor.getImageUrl().isEmpty())
        {
            Picasso.with(mActivity).load(sponsor.getImageUrl()).resize(80, 80).centerCrop().into(holder.sponsorImageView);
        }

    }

    @Override
    public int getItemCount() {
        return mSponsorList.size();
    }


    public class SponsorsViewHolder extends RecyclerView.ViewHolder {

        ImageView sponsorImageView;
        TextView sponsorTextView;

        public SponsorsViewHolder(@NonNull View itemView) {
            super(itemView);
            sponsorImageView = itemView.findViewById(R.id.sponsorImageView);
            sponsorTextView = itemView.findViewById(R.id.sponsorTextView);
            sponsorImageView.setClipToOutline(true);
        }
    }
}
