package com.openclassrooms.realestatemanager.controllers.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.MediaController;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.DetailFragment;
import com.openclassrooms.realestatemanager.databinding.ActivityVideoBinding;

public class VideoActivity extends BaseActivity<ActivityVideoBinding> {

    @Override
    ActivityVideoBinding getViewBinding() {
        return ActivityVideoBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        String urlVideoSelected = (String) getIntent().getSerializableExtra(DetailFragment.VIDEO_SELECTED);
        launchVideo(urlVideoSelected);
    }

    private void launchVideo(String path) {
        binding.activityVideoView.setVideoURI(Uri.parse(path));
        MediaController mediaController = new MediaController(this);
        binding.activityVideoView.setMediaController(mediaController);
        mediaController.setAnchorView(binding.activityVideoView);

        //Add media controller on video view
        binding.activityVideoView.start();

    }
}