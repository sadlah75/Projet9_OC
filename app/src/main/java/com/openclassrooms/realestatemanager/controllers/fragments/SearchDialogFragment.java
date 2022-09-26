package com.openclassrooms.realestatemanager.controllers.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModel.PropertyViewModel;
import com.openclassrooms.realestatemanager.databinding.FragmentDialogSearchBinding;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.PropertyAndAddressAndPhotos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchDialogFragment extends DialogFragment {

    public static final int USER_ID = 1;

    private FragmentDialogSearchBinding binding;
    private ArrayList<PropertyAndAddressAndPhotos> mProperties = new ArrayList<>();
    private PropertyViewModel mPropertyViewModel;

    public SearchDialogFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogSearchBinding.inflate(getLayoutInflater());
        initGUI();
        return binding.getRoot();
    }

    private void initGUI() {
        this.configViewModel();
        this.displayListOfType();

        // Configure Listeners
        this.searchDataListener();
        this.cancelDataListener();
    }

    private void configViewModel() {
        mPropertyViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this.requireContext()))
                .get(PropertyViewModel.class);
        mPropertyViewModel.getPropertiesByUser(USER_ID).observe(this, this::updatePropertyList);
    }
    private void updatePropertyList(List<PropertyAndAddressAndPhotos> properties) {
        this.mProperties = (ArrayList<PropertyAndAddressAndPhotos>) properties;
        updateProperties();
    }

    private void updateProperties() {
        PropertyFragment.mAdapter.updateProperties(mProperties);
    }

    private void displayListOfType() {
        ArrayAdapter<CharSequence> mArrayAdapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.property_type, android.R.layout.simple_spinner_item);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        binding.spinnerType.setAdapter(mArrayAdapter);
    }

    // ----------
    // Action
    // ----------

    private void searchDataListener() {
        binding.actionButtonSearch.setOnClickListener(view -> executeSearch());
    }

    private void cancelDataListener() {
        binding.actionButtonCancel.setOnClickListener(view -> getDialog().dismiss());
    }

    private void executeSearch() {
        String type = !binding.spinnerType.getSelectedItem().toString().equals("") ? binding.spinnerType.getSelectedItem().toString() : "%";
        String area = !Objects.requireNonNull(binding.searchArea.getText()).toString().equals("") ? binding.searchArea.getText().toString() : "%";
        String surfaceMin = !Objects.requireNonNull(binding.searchSurfaceMin.getText()).toString().equals("") ? binding.searchSurfaceMin.getText().toString() : "0";
        String surfaceMax = !Objects.requireNonNull(binding.searchSurfaceMax.getText()).toString().equals("") ? binding.searchSurfaceMax.getText().toString() : "100000";
        String priceMin = !Objects.requireNonNull(binding.searchPriceMin.getText()).toString().equals("") ? binding.searchPriceMin.getText().toString() : "0";
        String priceMax = !Objects.requireNonNull(binding.searchPriceMax.getText()).toString().equals("") ? binding.searchPriceMax.getText().toString() : "999999999999";
        String roomMin = !Objects.requireNonNull(binding.searchRoomMin.getText()).toString().equals("") ? binding.searchRoomMin.getText().toString() : "0";
        String roomMax = !Objects.requireNonNull(binding.searchRoomMax.getText()).toString().equals("") ? binding.searchRoomMax.getText().toString() : "100";

        mPropertyViewModel.searchProperty(type, area, Integer.valueOf(surfaceMin), Integer.valueOf(surfaceMax), Long.valueOf(priceMin),
                Long.valueOf(priceMax), Integer.valueOf(roomMin), Integer.valueOf(roomMax), USER_ID).observe(this, this::updatePropertyList);

        getDialog().dismiss();

    }


}
