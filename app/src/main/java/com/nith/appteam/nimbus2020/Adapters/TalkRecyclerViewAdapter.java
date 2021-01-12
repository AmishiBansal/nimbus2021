package com.nith.appteam.nimbus2020.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nith.appteam.nimbus2020.Activities.Add_talk_details;
import com.nith.appteam.nimbus2020.Models.TalkModel;
import com.nith.appteam.nimbus2020.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class TalkRecyclerViewAdapter extends
        RecyclerView.Adapter<TalkRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<TalkModel> talksList;

    public TalkRecyclerViewAdapter(Context context, List<TalkModel> talks) {
        this.context = context;
        talksList = talks;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talks_info, parent,
                false);

        return new ViewHolder(view, context);
    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.fast_anim_v1);
        Random rand = new Random();
        animation.setDuration(rand.nextInt(2000) + 2000);
        Animation animation1 = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slow_anim_v1);
        animation1.setDuration(rand.nextInt(2000) + 2000);
        holder.round_big.startAnimation(animation1);
        holder.round_small.startAnimation(animation);
        TalkModel talks = talksList.get(position);
        String imageLink = talks.getImage();
        //holder.regUrl.setText(talks.getRegURL());
        //holder.info.setText(talks.getInfo());
        holder.date.setText(talks.getDate());
        holder.venue.setText(talks.getVenue());
        holder.name.setText(talks.getName());
        Picasso.with(context).load(imageLink).into(
                holder.imgSpkr);


    }

    @Override
    public int getItemCount() {
        return talksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, date, venue;
        ImageView imgSpkr;
        ImageView round_big, round_small;

        public ViewHolder(@NonNull View itemView, final Context ctx) {
            super(itemView);
            context = ctx;

            name = itemView.findViewById(R.id.speakerNameID);
            imgSpkr = itemView.findViewById(R.id.speakerImageID);
            venue = itemView.findViewById(R.id.speakerVenueID);
            date = itemView.findViewById(R.id.SpeakerDate);
            imgSpkr.setClipToOutline(true);

            //   info=(TextView) itemView.findViewById(R.id.speakerInfoID);
            //  regUrl=(TextView) itemView.findViewById(R.id.regURL);
            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    TalkModel talkModel = talksList.get(getAdapterPosition());
                    Intent intent = new Intent(context, Add_talk_details.class);
                    intent.putExtra("talk", talkModel);
                    ctx.startActivity(intent);
                    ((Activity)ctx).overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                    //Display toast until UI is ready
                    //Toast.makeText(ctx, "Coming Soon..", Toast.LENGTH_SHORT).show();
                }

            });

        }

        @Override
        public void onClick(View view) {

        }
    }
}

