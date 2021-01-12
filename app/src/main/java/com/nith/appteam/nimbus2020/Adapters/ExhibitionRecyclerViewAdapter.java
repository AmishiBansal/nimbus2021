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

import com.nith.appteam.nimbus2020.Activities.Add_exhibition_details;
import com.nith.appteam.nimbus2020.Models.ExhibitionModel;
import com.nith.appteam.nimbus2020.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class ExhibitionRecyclerViewAdapter extends
        RecyclerView.Adapter<ExhibitionRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<ExhibitionModel> exhibitionList;

    public ExhibitionRecyclerViewAdapter(Context context, List<ExhibitionModel> exhibition) {
        this.context = context;
        exhibitionList = exhibition;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exhibition_info,
                parent, false);

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

        ExhibitionModel exhibitions = exhibitionList.get(position);
        String imageLink = exhibitions.getImageExh();
        //holder.regUrl.setText(exhibitions.getRegURLExh());
        //holder.info.setText(exhibitions.getInfoExh());
        holder.date.setText(exhibitions.getDateExh());
        holder.venue.setText(exhibitions.getVenueExh());
        holder.name.setText(exhibitions.getNameExh());
        Picasso.with(context).load(imageLink).placeholder(android.R.drawable.ic_btn_speak_now).into(
                holder.imgExh);


    }

    @Override
    public int getItemCount() {
        return exhibitionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, date, venue;
        ImageView imgExh;
        ImageView round_big, round_small;

        public ViewHolder(@NonNull View itemView, final Context ctx) {
            super(itemView);

            context = ctx;
            name = itemView.findViewById(R.id.exhibtionNameID);
            imgExh = itemView.findViewById(R.id.exhibitionImageID);
            venue = itemView.findViewById(R.id.exhibitionVenueID);
            date = itemView.findViewById(R.id.ExhibitionDate);
            round_big = itemView.findViewById(R.id.round_big);
            round_small = itemView.findViewById(R.id.round_small);
            imgExh.setClipToOutline(true);
            //info=(TextView) itemView.findViewById(R.id.exhibtionInfoID);
            //regUrl=(TextView) itemView.findViewById(R.id.regURLExhibtion);
            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    ExhibitionModel exhModel = exhibitionList.get(getAdapterPosition());
                    Intent intent = new Intent(context, Add_exhibition_details.class);
                    intent.putExtra("exhibition", exhModel);
                    ctx.startActivity(intent);

                    //Toast until its ui is not ready
                    //Toast.makeText(ctx, "Coming Soon..", Toast.LENGTH_SHORT).show();
                }

            });

        }

        @Override
        public void onClick(View view) {

        }
    }
}

