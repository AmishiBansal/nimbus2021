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

import com.nith.appteam.nimbus2020.Activities.Add_workshop_details;
import com.nith.appteam.nimbus2020.Models.WorkshopModel;
import com.nith.appteam.nimbus2020.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class WorkshopRecyclerViewAdapter extends
        RecyclerView.Adapter<WorkshopRecyclerViewAdapter.ViewHolder> {
    private Context context;

    private List<WorkshopModel> workshopList;

    public WorkshopRecyclerViewAdapter(Context context, List<WorkshopModel> workshop) {
        this.context = context;
        workshopList = workshop;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshop_info, parent,
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

        WorkshopModel workshop = workshopList.get(position);
        String imageLinkWor = workshop.getImageWor();
        //holder.regUrlWor.setText(workshop.getUrlWor());
        //holder.infoWor.setText(workshop.getInfoWor());
        holder.dateWor.setText(workshop.getDateWor());
        holder.venueWor.setText(workshop.getVenueWor());
        holder.nameWor.setText(workshop.getNameWor());
        //holder.typeWor.setText(workshop.getTypeWor());
        Picasso.with(context).load(imageLinkWor).placeholder(android.R.drawable.ic_btn_speak_now).into(holder.imgSpkrWork);
    }


    @Override
    public int getItemCount() {
        return workshopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameWor, dateWor, venueWor;
        ImageView imgSpkrWork, round_big, round_small;

        public ViewHolder(@NonNull View itemView, final Context ctx) {
            super(itemView);
            context = ctx;
            nameWor = itemView.findViewById(R.id.workshopNameID);
            imgSpkrWork = itemView.findViewById(R.id.WorkshopImageID);
            venueWor = itemView.findViewById(R.id.workshopVenueID);
            dateWor = itemView.findViewById(R.id.WorkshopDate);
            round_big = itemView.findViewById(R.id.round_big);
            round_small = itemView.findViewById(R.id.round_small);
            imgSpkrWork.setClipToOutline(true);
            //infoWor=(TextView) itemView.findViewById(R.id.workshopInfoID);
            //regUrlWor=(TextView) itemView.findViewById(R.id.regURLWor);
            //typeWor=(TextView)itemView.findViewById(R.id.workshopTypeID);
            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    WorkshopModel workshopModel = workshopList.get(getAdapterPosition());
                    Intent intent = new Intent(context, Add_workshop_details.class);
                    intent.putExtra("workshop", workshopModel);
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
