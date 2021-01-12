package com.nith.appteam.nimbus2020.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nith.appteam.nimbus2020.Activities.Schedule;
import com.nith.appteam.nimbus2020.Models.DiscoverModel;
import com.nith.appteam.nimbus2020.R;

import java.util.List;
import java.util.Random;


public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> implements View.OnClickListener {

    List<DiscoverModel> mDiscoverModelList;
    Activity mActivity;

    public DiscoverAdapter(List<DiscoverModel> discoverModelList, Activity activity) {
        mDiscoverModelList = discoverModelList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discover_item, parent, false);
        return new DiscoverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, final int position) {

        DiscoverModel discoverModel = mDiscoverModelList.get(position);

        Animation animation = AnimationUtils.loadAnimation(mActivity.getApplicationContext(), R.anim.scale);
//        holder.layout.startAnimation(animation);
        holder.eventName.setText(discoverModel.getEventName());
//        holder.location.setText(discoverModel.getLocation());
//        holder.time.setText(discoverModel.getTime());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = ""+(position+1);
                Intent intent = new Intent(mActivity, Schedule.class);
                intent.putExtra("Day",day);
                mActivity.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiscoverModelList.size();
    }

    @Override
    public void onClick(View v) {
    }


    public class DiscoverViewHolder extends RecyclerView.ViewHolder {

        TextView eventName;
        TextView location;
        TextView time;
        LinearLayout layout;
        ImageView round_big, round_small;

        public DiscoverViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            eventName = itemView.findViewById(R.id.eventName);
            location = itemView.findViewById(R.id.location);
            time = itemView.findViewById(R.id.time);
            round_big = itemView.findViewById(R.id.round_big);
            round_small = itemView.findViewById(R.id.round_small);
        }
    }
}
