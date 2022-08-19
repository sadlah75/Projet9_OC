package com.openclassrooms.realestatemanager.adapters;

import android.annotation.SuppressLint;
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
import com.openclassrooms.realestatemanager.model.Property;

import java.util.ArrayList;
import java.util.List;


public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    /**
     * The list of properties the adapter deals with
     */
    @NonNull
    private List<Property> mProperties;

    private final OnClickItemRecyclerViewListener mListener;

    /**
     * The listener for when a property needs to be displayed
     */
    public interface OnClickItemRecyclerViewListener {
        void OnClickItemRecyclerView(int position);
    }

    /**
     * Instantiates a new property
     * @param mProperties the list of properties the adapter deals with to set
     */
    public PropertyAdapter(@NonNull List<Property> mProperties,@NonNull final OnClickItemRecyclerViewListener mListener) {
        this.mProperties = mProperties;
        this.mListener = mListener;
    }

    /**
     * Updates the list of properties the adapter deals with
     * @param mProperties the list of properties the adapter deals with to set
     */
    @SuppressLint("NotifyDataSetChanged")
    public void updateProperties(ArrayList<Property> mProperties) {
        this.mProperties = mProperties;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PropertyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_property_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyAdapter.ViewHolder holder, int position) {
        Property property = mProperties.get(position);
        if(property.getUrlPicture() != null) {
            Glide.with(holder.getPicture().getContext())
                    .load(property.getUrlPicture())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.getPicture());
        }
        holder.getType().setText(property.getType());
        holder.getLocation().setText(property.getArea());
        holder.getPrice().setText(String.valueOf(property.getPrice()));

        holder.itemView.setOnClickListener(view -> mListener.OnClickItemRecyclerView(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mProperties.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView picture;
        private final TextView type,area,price;

        public ImageView getPicture() {
            return picture;
        }
        public TextView getType() {
            return type;
        }
        public TextView getLocation() {
            return area;
        }
        public TextView getPrice() {
            return price;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.property_item_picture);
            type = (TextView) itemView.findViewById(R.id.property_item_type);
            area = (TextView) itemView.findViewById(R.id.property_item_location);
            price = (TextView) itemView.findViewById(R.id.property_item_price);
        }
    }
}
