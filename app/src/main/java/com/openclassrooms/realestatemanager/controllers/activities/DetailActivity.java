package com.openclassrooms.realestatemanager.controllers.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.DetailFragment;
import com.openclassrooms.realestatemanager.databinding.ActivityDetailBinding;
import com.openclassrooms.realestatemanager.model.Property;

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
        Property property = (Property) getIntent().getSerializableExtra(PropertyActivity.PROPERTY_DETAILS);
        getIntent().putExtra(PropertyActivity.PROPERTY_DETAILS,property);
    }


    private void displayDetailFragment() {
        mDetailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_detail_frame_layout);
        if (mDetailFragment == null) {
            mDetailFragment = DetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_detail_frame_layout,mDetailFragment)
                    .commit();
        }
    }
}