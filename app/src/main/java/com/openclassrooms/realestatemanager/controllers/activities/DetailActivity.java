package com.openclassrooms.realestatemanager.controllers.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.DetailFragment;
import com.openclassrooms.realestatemanager.databinding.ActivityDetailBinding;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyAndAddressAndPhotos;
import com.openclassrooms.realestatemanager.utils.SharedPreferencesHelper;

public class DetailActivity extends AppCompatActivity {

    private DetailFragment mDetailFragment;
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());

        SharedPreferencesHelper.setActionPropertyMode(this,
                SharedPreferencesHelper.MODE_DETAIL);
        this.configureAndShowDetailFragment();
        setContentView(binding.getRoot());
    }



    @Override
    protected void onResume() {
        super.onResume();
        this.updateDetailFragmentPropertyIntent();
    }

    private void configureAndShowDetailFragment() {
        // A - Get FragmentManager and try to find existing instance of fragment in FrameLayout container
        mDetailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_layout_detail_activity);
        if (mDetailFragment == null) {
            // B -- Create new detail fragment
            mDetailFragment = new DetailFragment();
            // C - Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_detail_activity,mDetailFragment)
                    .commit();
        }
    }

    // retrieve property from PropertyActivity and send him to DetailFragment
    private void updateDetailFragmentPropertyIntent() {
        PropertyAndAddressAndPhotos property = (PropertyAndAddressAndPhotos) getIntent().getSerializableExtra(PropertyActivity.PROPERTY_DETAILS);
        //getIntent().putExtra(PropertyActivity.PROPERTY_DETAILS,property);
        mDetailFragment.displayPropertyOnTablet(property);
    }
}