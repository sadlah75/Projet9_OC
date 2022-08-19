package com.openclassrooms.realestatemanager.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private List<Photo> mPhotoList = new ArrayList<>();

    public PictureAdapter(List<Photo> photos) {
        mPhotoList = photos;
    }

    @NonNull
    @Override
    public PictureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_picture_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureAdapter.ViewHolder holder, int position) {
        Photo photo = mPhotoList.get(position);
        Glide.with(holder.getPhoto().getContext())
                .load(photo.getUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.getPhoto());
        holder.getTitle().setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public void updateList(List<Photo> photos) {
        this.mPhotoList = photos;
        notifyDataSetChanged();
    }

    public static   class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo;
        private TextView title;

        public ImageView getPhoto() { return photo; }
        public TextView getTitle() { return title; }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.fragment_picture_item_image);
            title = (TextView) itemView.findViewById(R.id.fragment_picture_item_title);
        }
    }

}
