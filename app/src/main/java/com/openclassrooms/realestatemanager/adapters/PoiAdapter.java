package com.openclassrooms.realestatemanager.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;
import java.util.List;

public class PoiAdapter extends RecyclerView.Adapter<PoiAdapter.ViewHolder> {

    private List<String> mPoiList;

    public PoiAdapter() {
        this.mPoiList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PoiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_poi_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoiAdapter.ViewHolder viewHolder, int position) {
        viewHolder.getLabel().setText(mPoiList.get(position));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<String> poiList) {
        mPoiList = poiList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPoiList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView label;

        public TextView getLabel() { return label; }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.poi_item_label);
        }
    }
}
