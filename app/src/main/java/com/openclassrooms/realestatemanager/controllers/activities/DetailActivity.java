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

public class DetailActivity extends BaseActivity<ActivityDetailBinding> {

    private DetailFragment mDetailFragment;

    @Override
    ActivityDetailBinding getViewBinding() {
        return ActivityDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void init() {
        getProperty();
        displayDetailFragment();
    }

    // retrieve property from PropertyActivity and send him to DetailFragment
    private void getProperty() {
        PropertyAndAddressAndPhotos property = (PropertyAndAddressAndPhotos) getIntent().getSerializableExtra(PropertyActivity.PROPERTY_DETAILS);
        getIntent().putExtra(PropertyActivity.PROPERTY_DETAILS,property);
    }


    private void displayDetailFragment() {
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
}