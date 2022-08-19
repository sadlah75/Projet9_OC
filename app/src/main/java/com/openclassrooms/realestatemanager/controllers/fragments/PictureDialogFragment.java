package com.openclassrooms.realestatemanager.controllers.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PictureAdapter;
import com.openclassrooms.realestatemanager.databinding.FragmentDialogPictureFormBinding;
import com.openclassrooms.realestatemanager.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class PictureDialogFragment extends BaseDialogFragment<FragmentDialogPictureFormBinding> {

    private static final int CONSTANT_SELECT_PHOTO = 200;
    private static final String TAG = "PictureDialogFragment";

    private PictureAdapter mAdapter;
    private List<Photo> mPhotos = new ArrayList<>();
    private Uri uriImageSelected;
    private OnInputSelected mOnIputSelected;

    @Override
    public FragmentDialogPictureFormBinding getViewBinding() {
        return FragmentDialogPictureFormBinding.inflate(getLayoutInflater());
    }

    @Override
    public void init() {
        this.configureRecyclerView();
        this.initSelectPictureListener();
        this.initAddPictureListener();

        this.initSaveDataListener();
        this.initCancelDataListener();
    }

    private void configureRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        this.mAdapter = new PictureAdapter(mPhotos);
        binding.recyclerViewPictures.recyclerViewPicture.setAdapter(this.mAdapter);
        binding.recyclerViewPictures.recyclerViewPicture.setLayoutManager(layoutManager);
    }


    private void initSelectPictureListener() {
        binding.imageViewSelectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, CONSTANT_SELECT_PHOTO);
            }
        });
    }

    private void initAddPictureListener() {
        binding.imageViewAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.editTextPictureTitle.getText().toString().equals("") && uriImageSelected != null) {
                    String title = binding.editTextPictureTitle.getText().toString();
                    String url = uriImageSelected.toString();
                    mPhotos.add(new Photo(title,url));
                    updatePictures(mPhotos);
                    resetUI();
                }else {
                    Toast.makeText(getContext(), "The title or the picture is missing!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initSaveDataListener() {
        binding.actionButtonSaveCancel.actionButtonSave
                .setOnClickListener(view -> saveData(mPhotos));
    }

    private void saveData(List<Photo> mPhotos) {
        if(mPhotos.size() > 0) {
            mOnIputSelected.sendPictureData(mPhotos);
            Toast.makeText(getContext(), "Photos added successfully", Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
        }else {
            Toast.makeText(getContext(), "Please add at least one item", Toast.LENGTH_SHORT).show();
        }
    }

    private void initCancelDataListener() {
        binding.actionButtonSaveCancel.actionButtonCancel
                .setOnClickListener(view -> getDialog().dismiss());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Calling the appropriate method after activity result
        this.handleResponse(requestCode, resultCode, data);
    }


    private void handleResponse(int requestCode, int resultCode, Intent data) {
        if (requestCode == CONSTANT_SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                this.uriImageSelected = data.getData();
                Glide.with(this)
                        .load(this.uriImageSelected)
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.imageViewSelectPicture);
            } else {
                Toast.makeText(getContext(), getString(R.string.toast_title_no_image_chosen), Toast.LENGTH_SHORT).show();
            }
        }
    }


    // --- Update UI
    private void resetUI() {
        this.binding.editTextPictureTitle.setText("");
        this.binding.imageViewSelectPicture.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_image));
    }

    private void updatePictures(List<Photo> photos) {
        mAdapter.updateList(photos);
    }

    // --- Callback ---

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnIputSelected = (OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG,"onAttach: ClassCastException : " + e.getMessage());
        }
    }

    public interface OnInputSelected {
        void sendPictureData(List<Photo> photoList);
    }
}
