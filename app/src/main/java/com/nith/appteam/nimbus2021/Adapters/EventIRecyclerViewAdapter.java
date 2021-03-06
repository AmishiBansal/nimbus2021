package com.nith.appteam.nimbus2021.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nith.appteam.nimbus2021.Activities.Add_institute_Activity_Detail;
import com.nith.appteam.nimbus2021.Models.instituteEvent;
import com.nith.appteam.nimbus2021.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class EventIRecyclerViewAdapter extends
        RecyclerView.Adapter<EventIRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<instituteEvent> eventList;

    public EventIRecyclerViewAdapter(Context context, List<instituteEvent> events) {
        this.context = context;
        eventList = events;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instituteevents_info,
                parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.fast_anim_v1);
        Random rand = new Random();
        animation.setDuration(rand.nextInt(2000) + 2000);
        Animation animation1 = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slow_anim_v1);
        animation1.setDuration(rand.nextInt(2000) + 2000);
        holder.round_big.startAnimation(animation1);
        holder.round_small.startAnimation(animation);
        instituteEvent Ievents = eventList.get(position);
        String imageLinkEVE = Ievents.getImageIEVE();
        // holder.regUrlEVEI.setText(Ievents.getRegURLIEVE());
        //holder.infoEVEI.setText(Ievents.getInfoIEVE());
        holder.datEVEI.setText(Ievents.getDateIEVE());
        holder.venueEVEI.setText(Ievents.getVenueIEVE());
        holder.nameEVEI.setText(Ievents.getNameIEVE());
        Picasso.with(context.getApplicationContext()).load(imageLinkEVE.replace("http", "https"))
                .resize(90, 90).into(holder.imgEVEVi, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                holder.imgEVEVi.setImageResource(R.drawable.nimbus_logo);
            }
        });

    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameEVEI, datEVEI, infoEVEI, regUrlEVEI, venueEVEI;
        ImageView round_big, round_small, imgEVEVi;

        public ViewHolder(@NonNull View itemView, final Context ctx) {
            super(itemView);
            context = ctx;
            nameEVEI = itemView.findViewById(R.id.EventINameID);
            imgEVEVi = itemView.findViewById(R.id.EventIImageID);
            venueEVEI = itemView.findViewById(R.id.EventIVenueID);
            datEVEI = itemView.findViewById(R.id.EventIDate);
            imgEVEVi.setClipToOutline(true);
            round_big = itemView.findViewById(R.id.round_big);
            round_small = itemView.findViewById(R.id.round_small);
            //infoEVEI=(TextView) itemView.findViewById(R.id.EventIInfoID);
            //  regUrlEVEI=(TextView) itemView.findViewById(R.id.regURLEvevntI);
            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
//
                    instituteEvent instituteEvent = eventList.get(getAdapterPosition());
                    Intent intent = new Intent(context, Add_institute_Activity_Detail.class);
                    intent.putExtra("instituteEvents", instituteEvent);
                    ctx.startActivity(intent);
                    //Display toast until UI is not ready
                    //Toast.makeText(ctx, "Coming Soon..", Toast.LENGTH_SHORT).show();
                }

            });

        }

        @Override
        public void onClick(View view) {

        }
    }
}


