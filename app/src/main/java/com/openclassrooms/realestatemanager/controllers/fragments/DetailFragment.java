package com.openclassrooms.realestatemanager.controllers.fragments;


import com.openclassrooms.realestatemanager.controllers.activities.PropertyActivity;
import com.openclassrooms.realestatemanager.databinding.FragmentDetailBinding;
import com.openclassrooms.realestatemanager.model.Property;

public class DetailFragment extends BaseFragment<FragmentDetailBinding> {


    @Override
    public FragmentDetailBinding getViewBinding() {
        return FragmentDetailBinding.inflate(getLayoutInflater());
    }

    public DetailFragment() {}

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void init() {
        getPropertyFromDetailActivity();
    }

    private void getPropertyFromDetailActivity() {
        Property property = (Property) getActivity().getIntent().
                getSerializableExtra(PropertyActivity.PROPERTY_DETAILS);
        displayPropertyDetails(property);
    }

    private void displayPropertyDetails(Property property) {
        binding.detailDescription.setText(property.getDescription());
        // --- Details rooms ---
        binding.roomsDetailsLayout.detailSurfaceQuantity.setText(String.valueOf(property.getSurface()));
        binding.roomsDetailsLayout.detailRoomsQuantity.setText(String.valueOf(property.getNumberOfRoom()));
        binding.roomsDetailsLayout.detailBathroomsQuantity.setText(String.valueOf(property.getNumberOfBathroom()));
        binding.roomsDetailsLayout.detailBedroomQuantity.setText(String.valueOf(property.getNumberOfBedroom()));

        // --- Details Location ---


    }
}
