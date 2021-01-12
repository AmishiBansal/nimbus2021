package com.nith.appteam.nimbus2020.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nith.appteam.nimbus2020.Models.ScheduleModel;
import com.nith.appteam.nimbus2020.R;

import java.util.List;
import java.util.Random;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<ScheduleModel> scheduleList;

    public ScheduleRecyclerViewAdapter(Context context, List<ScheduleModel> schedule) {
        this.context = context;
        scheduleList = schedule;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_add, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.item_animation));
        ScheduleModel scheduleModel = scheduleList.get(position);
        //    String imageLink=scheduleModel.getImage();
        //holder.regUrl.setText(talks.getRegURL());
        //holder.info.setText(talks.getInfo());
        holder.deptNAme.setText(scheduleModel.getDeptSch());
        holder.date.setText(scheduleModel.getTimeSch());
        holder.venue.setText(scheduleModel.getVenueSch());
        holder.name.setText(scheduleModel.getNameSch());
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fast_anim_v1);
        Random rand = new Random();
        animation.setDuration(rand.nextInt(2000) + 2000);
        Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.slow_anim_v1);
        animation1.setDuration(rand.nextInt(2000) + 2000);
        holder.round_big.startAnimation(animation1);
        holder.round_small.startAnimation(animation);
        // Picasso.with(context).load(imageLink).placeholder(android.R.drawable.ic_btn_speak_now).into(holder.imgSpkr);
    }


    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, date, venue, deptNAme;
        RelativeLayout container;
        ImageView round_big, round_small;

        public ViewHolder(@NonNull View itemView, final Context ctx) {
            super(itemView);
            context = ctx;
            container = itemView.findViewById(R.id.contSch);
            name = itemView.findViewById(R.id.EventSchNameID);
            deptNAme = itemView.findViewById(R.id.EventSchVenueID);
            venue = itemView.findViewById(R.id.EventSchVenueID);
            date = itemView.findViewById(R.id.EventSchDate);
            round_big = itemView.findViewById(R.id.round_big);
            round_small = itemView.findViewById(R.id.round_small);
            //   info=(TextView) itemView.findViewById(R.id.speakerInfoID);
            //  regUrl=(TextView) itemView.findViewById(R.id.regURL);
            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Scroll only", Toast.LENGTH_SHORT).show();
                }

            });

        }

        @Override
        public void onClick(View view) {

        }
    }
}





