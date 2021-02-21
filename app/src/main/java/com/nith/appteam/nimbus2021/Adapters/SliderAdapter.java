package com.nith.appteam.nimbus2021.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nith.appteam.nimbus2021.Models.GallerySliderItem;
import com.nith.appteam.nimbus2021.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{

    private List<GallerySliderItem> gallerySliderItems;
    private ViewPager2 viewPager2;
    private Context mcontext;

    public SliderAdapter(List<GallerySliderItem> gallerySliderItems, ViewPager2 viewPager2,Context context) {
        this.gallerySliderItems = gallerySliderItems;
        this.viewPager2 = viewPager2;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_slider_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Glide.with(mcontext).load(gallerySliderItems.get(position).getImage()).centerCrop().into(holder.imageView);
        if(position == gallerySliderItems.size()-2)
        {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return gallerySliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_slide);
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            gallerySliderItems.addAll(gallerySliderItems);
            notifyDataSetChanged();
        }
    };
}
