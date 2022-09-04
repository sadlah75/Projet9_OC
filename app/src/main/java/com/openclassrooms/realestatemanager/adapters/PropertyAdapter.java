package com.openclassrooms.realestatemanager.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.App;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.SharedPreferencesHelper;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyAndAddressAndPhotos;

import java.util.ArrayList;
import java.util.List;


public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    /**
     * The list of properties the adapter deals with
     */
    @NonNull
    private List<PropertyAndAddressAndPhotos> mProperties;

    private final OnClickItemRecyclerViewListener mListener;

    /**
     * The listener for when a property needs to be displayed
     */
    public interface OnClickItemRecyclerViewListener {
        void onClickItemRecyclerView(int position);
    }

    /**
     * Instantiates a new property
     * @param mProperties the list of properties the adapter deals with to set
     */
    public PropertyAdapter(@NonNull List<PropertyAndAddressAndPhotos> mProperties,@NonNull final OnClickItemRecyclerViewListener mListener) {
        this.mProperties = mProperties;
        this.mListener = mListener;
    }

    /**
     * Updates the list of properties the adapter deals with
     * @param mProperties the list of properties the adapter deals with to set
     */
    @SuppressLint("NotifyDataSetChanged")
    public void updateProperties(ArrayList<PropertyAndAddressAndPhotos> mProperties) {
        this.mProperties = mProperties;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PropertyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_property_item,parent,false);
        SharedPreferencesHelper.setActionPropertyMode(App.getContext(),"MODE_INIT");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyAdapter.ViewHolder holder, int position) {
        Property property = (mProperties.get(position)).property;
        if(property.getUrlPicture() != null) {
            Glide.with(holder.getPicture().getContext())
                    .load(property.getUrlPicture())
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.getPicture());
        }
        holder.getType().setText(property.getType());
        holder.getLocation().setText(property.getArea());

        String priceFormatted = App.getContext().getResources().getString(R.string.item_price,
                Utils.getFormattedPrice(property.getPrice()));
        holder.getPrice().setText(priceFormatted);
        holder.itemView.setOnClickListener(view -> {
            mListener.onClickItemRecyclerView(holder.getAdapterPosition());
            SharedPreferencesHelper.setActionPropertyMode(App.getContext(),"MODE_DETAILS");
        });

        // --- configure checkbox ---
        AppCompatCheckBox checkBox = holder.itemView.findViewById(R.id.property_item_checkbox);
        checkBox.setVisibility(SharedPreferencesHelper.isVisible(App.getContext()) ? View.VISIBLE : View.GONE);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    SharedPreferencesHelper.setActionPropertyMode(App.getContext(),
                            SharedPreferencesHelper.MODE_UPDATE);
                    SharedPreferencesHelper.setPosition(App.getContext(),
                            holder.getAdapterPosition());
                    mListener.onClickItemRecyclerView(holder.getAdapterPosition());
                }else{
                    SharedPreferencesHelper.setActionPropertyMode(App.getContext(),
                            "MODE_INIT");
                }
            }
        });
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
