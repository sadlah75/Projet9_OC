package com.openclassrooms.realestatemanager.controllers.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModel.PropertyViewModel;
import com.openclassrooms.realestatemanager.adapters.PropertyAdapter;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.PropertyAndAddressAndPhotos;
import java.util.ArrayList;
import java.util.List;

public class PropertyFragment extends Fragment implements PropertyAdapter.OnClickItemRecyclerViewListener {

    public static final int USER_ID = 1;

    private ArrayList<PropertyAndAddressAndPhotos> mProperties = new ArrayList<>();
    private PropertyViewModel propertyViewModel;
    public static PropertyAdapter mAdapter;
    private RecyclerView recyclerView;

    // Declare callback
    private OnItemClickListener mOnItemClickListener;

    public PropertyFragment() {}

    // Declare our interface that will be implemented by any container activity
    public interface OnItemClickListener {
        void onItemClickSelected(PropertyAndAddressAndPhotos property);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property_list,container,false);
        recyclerView = view.findViewById(R.id.fragment_property_recyclerview);
        initGUI();
        return view;
    }

    public void initGUI() {
        this.initRecyclerView();
        this.configViewModel();
    }

    private void initRecyclerView() {
        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PropertyAdapter(mProperties,this);
        // Set CustomAdapter as the adapter for RecyclerView.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(mAdapter);
    }

    private void configViewModel() {
        propertyViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this.requireContext()))
                .get(PropertyViewModel.class);
        propertyViewModel.getPropertiesByUser(USER_ID).observe(this, new Observer<List<PropertyAndAddressAndPhotos>>() {
            @Override
            public void onChanged(@Nullable List<PropertyAndAddressAndPhotos> propertyAndAddressAndPhotos) {
                updatePropertyList(propertyAndAddressAndPhotos);
            }
        });
    }

    private void updatePropertyList(List<PropertyAndAddressAndPhotos> properties) {
        this.mProperties = (ArrayList<PropertyAndAddressAndPhotos>) properties;
        updateProperties();
    }

    private void updateProperties() {
        this.mAdapter.updateProperties(mProperties);
    }

    @Override
    public void onClickItemRecyclerView(int position) {
        mOnItemClickListener.onItemClickSelected(mProperties.get(position));
    }

    // --- Callback ---

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    // Create callback to parent activity
    private void createCallbackToParentActivity() {
        try {
            //Parent activity will automatically subscribe to callback
            mOnItemClickListener = (OnItemClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement OnItemClickListener");
        }
    }
}