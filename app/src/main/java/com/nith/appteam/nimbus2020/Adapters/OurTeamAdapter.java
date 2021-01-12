package com.nith.appteam.nimbus2020.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nith.appteam.nimbus2020.Models.TeamMember;
import com.nith.appteam.nimbus2020.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class OurTeamAdapter extends RecyclerView.Adapter<OurTeamAdapter.OurTeamViewHolder> {
    List<TeamMember> mTeamList;
    Activity mActivity;

    public OurTeamAdapter(List<TeamMember> teamList, FragmentActivity activity) {
        mTeamList = teamList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public OurTeamAdapter.OurTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_our_team, parent, false);
        return new OurTeamAdapter.OurTeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OurTeamAdapter.OurTeamViewHolder holder, int position) {
        TeamMember teamMember = mTeamList.get(position);
        holder.name.setText(teamMember.getName());
        holder.designation.setText(teamMember.getDesignation());
        if (!teamMember.getImageUrl().isEmpty()) {
            Picasso.with(mActivity).load(teamMember.getImageUrl()).resize(80, 80).centerCrop().into(
                    holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return mTeamList.size();
    }

    public class OurTeamViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, designation;

        public OurTeamViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.team_image);
            name = itemView.findViewById(R.id.name);
            designation = itemView.findViewById(R.id.designation);
        }
    }
}
