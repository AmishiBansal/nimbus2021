package com.nith.appteam.nimbus2021.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nith.appteam.nimbus2021.Activities.ClubsGallery;
import com.nith.appteam.nimbus2021.Models.ClubDetail;
import com.nith.appteam.nimbus2021.Models.TeamMember;
import com.nith.appteam.nimbus2021.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ClubViewHolder> {
    List<ClubDetail> mClubList;
    Activity mActivity;

    public GalleryAdapter(List<ClubDetail> clubList, FragmentActivity activity) {
        mClubList = clubList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public GalleryAdapter.ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gallery, parent, false);
        return new GalleryAdapter.ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.ClubViewHolder holder, int position) {
        final ClubDetail club = mClubList.get(position);
        holder.name.setText(club.getName());
        if (!club.getImageUrl().isEmpty()) {
            Picasso.with(mActivity).load(club.getImageUrl()).resize(80, 80).centerCrop().into(
                    holder.imageView);
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ClubsGallery.class);
                intent.putExtra("club_id",club.getClub_id());
                mActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mClubList.size();
    }

    public class ClubViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        CardView cv;

        public ClubViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.club_image);
            name = itemView.findViewById(R.id.name);
            cv = itemView.findViewById(R.id.clubs_cardview);
        }
    }
}
