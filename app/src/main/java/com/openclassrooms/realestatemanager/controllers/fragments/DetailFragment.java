package com.openclassrooms.realestatemanager.controllers.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PictureAdapter;
import com.openclassrooms.realestatemanager.controllers.activities.PropertyActivity;
import com.openclassrooms.realestatemanager.controllers.activities.VideoActivity;
import com.openclassrooms.realestatemanager.databinding.FragmentDetailBinding;
import com.openclassrooms.realestatemanager.model.Address;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyAndAddressAndPhotos;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    public static final String VIDEO_SELECTED = "video selected";
    private List<Photo> mPhotos = new ArrayList<>();
    private PictureAdapter mAdapter;
    private PictureAdapter.OnDeletePhotoListener mOnDeletePhoto;

    private FragmentDetailBinding binding;

    public DetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(getLayoutInflater());
        initGUI();
        return binding.getRoot();
    }

    private void initGUI() {
        configureRecyclerView();
        configureButtonBackListener();
        getPropertyFromDetailActivity();
    }

    private void configureButtonBackListener() {
        binding.fragmentDetailReturn.setOnClickListener(view -> getActivity().finish());
    }

    private void configureRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        this.mAdapter = new PictureAdapter(mPhotos, mOnDeletePhoto);
        binding.recyclerViewFragment.recyclerViewPicture.setAdapter(this.mAdapter);
        binding.recyclerViewFragment.recyclerViewPicture.setLayoutManager(layoutManager);
    }

    private void getPropertyFromDetailActivity() {
        PropertyAndAddressAndPhotos property = (PropertyAndAddressAndPhotos) getActivity().getIntent().
                getSerializableExtra(PropertyActivity.PROPERTY_DETAILS);
        displayPropertyDetails(property);
    }

    private void displayPropertyDetails(PropertyAndAddressAndPhotos property) {
        Property p = property.property;

        binding.detailDescription.setText(p.getDescription());
        // --- Details rooms ---

        String surface = getString(R.string.surface_size,p.getSurface());
        binding.roomsDetailsLayout.detailSurfaceQuantity.setText(surface);

        binding.roomsDetailsLayout.detailRoomsQuantity.setText(String.valueOf(p.getNumberOfRoom()));
        binding.roomsDetailsLayout.detailBathroomsQuantity.setText(String.valueOf(p.getNumberOfBathroom()));
        binding.roomsDetailsLayout.detailBedroomQuantity.setText(String.valueOf(p.getNumberOfBedroom()));

        // --- Details Location ---
        Address address = property.address.get(0);
        binding.locationDetailsLayout.detailAddressLine1.setText(address.getAddress1());

        if(!address.getAddress2().equals("")) {
            binding.locationDetailsLayout.detailAddressLine2.setVisibility(View.VISIBLE);
            binding.locationDetailsLayout.detailAddressLine2.setText(address.getAddress2());
        }

        binding.locationDetailsLayout.detailAddressCity.setText(address.getCity());
        binding.locationDetailsLayout.detailAddressState.setText(address.getState());
        binding.locationDetailsLayout.detailAddressZipCode.setText(address.getZip());

        // --- Display pictures ---
        mAdapter.updateList(property.photos);

        // --- Configure Launch video selected Listener ---
        binding.fragmentDetailMedia.setOnClickListener(view -> playVideo(property.property.getUrlVideo()));
    }

    public void displayPropertyOnTablet(PropertyAndAddressAndPhotos p) {
        Log.i("onTablet","OK");
        binding.detailDescription.setText(p.property.getDescription());

        //String surface = getActivity().getString(R.string.surface_size,p.property.getSurface());
        //binding.roomsDetailsLayout.detailSurfaceQuantity.setText(surface);

        /*

        binding.roomsDetailsLayout.detailRoomsQuantity.setText(String.valueOf(p.property.getNumberOfRoom()));
        binding.roomsDetailsLayout.detailBathroomsQuantity.setText(String.valueOf(p.property.getNumberOfBathroom()));
        binding.roomsDetailsLayout.detailBedroomQuantity.setText(String.valueOf(p.property.getNumberOfBedroom()));
        */

    }

    private void playVideo(String urlVideo) {
        Intent intent = new Intent(getContext(),VideoActivity.class);
        intent.putExtra(VIDEO_SELECTED,urlVideo);
        startActivity(intent);
    }
}
