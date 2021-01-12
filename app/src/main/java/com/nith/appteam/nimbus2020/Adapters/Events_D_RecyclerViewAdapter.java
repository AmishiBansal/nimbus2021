package com.nith.appteam.nimbus2020.Adapters;

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

import com.nith.appteam.nimbus2020.Activities.Add_deptEvents_detail;
import com.nith.appteam.nimbus2020.Models.departmentEvent;
import com.nith.appteam.nimbus2020.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class Events_D_RecyclerViewAdapter extends
        RecyclerView.Adapter<Events_D_RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<departmentEvent> eventListDep;

    public Events_D_RecyclerViewAdapter(Context context, List<departmentEvent> events) {
        this.context = context;
        eventListDep = events;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.department_events_info, parent, false);

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
        departmentEvent Devents = eventListDep.get(position);
        String imageLinkEVED = Devents.getImageDEVE();
        //holder.regUrlEVED.setText(Devents.getRegURLDEVE());
        //holder.infoEVED.setText(Devents.getInfoDEVE());
        holder.datEVED.setText(Devents.getDateDEVE());
        holder.venueEVED.setText(Devents.getVenueDEVE());
        holder.nameEVED.setText(Devents.getNameDEVE());
        Picasso.with(context).load(imageLinkEVED).placeholder(
                android.R.drawable.ic_btn_speak_now).into(holder.imgEVEVD);
    }

    @Override
    public int getItemCount() {
        return eventListDep.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameEVED, datEVED, infoEVED, regUrlEVED, venueEVED;
        ImageView imgEVEVD, round_big, round_small;

        public ViewHolder(@NonNull View itemView, final Context ctx) {
            super(itemView);

            context = ctx;
            nameEVED = itemView.findViewById(R.id.EventDNameID);
            imgEVEVD = itemView.findViewById(R.id.EventDImageID);
            venueEVED = itemView.findViewById(R.id.EventDVenueID);
            datEVED = itemView.findViewById(R.id.EventDDate);
            imgEVEVD.setClipToOutline(true);
            round_big = itemView.findViewById(R.id.round_big);
            round_small = itemView.findViewById(R.id.round_small);
            //infoEVED=(TextView) itemView.findViewById(R.id.EventDInfoID);
            //  regUrlEVED=(TextView) itemView.findViewById(R.id.regURLEvevntD);
            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    departmentEvent dept = eventListDep.get(getAdapterPosition());
                    Intent intent = new Intent(context, Add_deptEvents_detail.class);
                    intent.putExtra("departmentEvents", dept);
                    ctx.startActivity(intent);
                    //Display toast until ui is not ready
                  //  Toast.makeText(ctx, "Coming Soon..", Toast.LENGTH_SHORT).show();
                }

            });

        }

        @Override
        public void onClick(View view) {

        }
    }
}


