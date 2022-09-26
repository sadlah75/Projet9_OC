package com.openclassrooms.realestatemanager.controllers.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.App;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModel.PropertyViewModel;
import com.openclassrooms.realestatemanager.databinding.FragmentDialogFormBinding;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.Address;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.Property;
import com.openclassrooms.realestatemanager.model.PropertyAndAddressAndPhotos;
import com.openclassrooms.realestatemanager.utils.SharedPreferencesHelper;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import pub.devrel.easypermissions.EasyPermissions;

public class PropertyDialogForm extends BaseDialogFragment<FragmentDialogFormBinding>
                                implements PoiDialogFragment.OnInputSelected,PictureDialogFragment.OnInputSelected,
                                PropertyFragment.OnItemClickListener {

    private static final String PERMISSION_TO_READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String POI_DIALOG = "com.openclassrooms.realestatemanager.controllers." +
            "fragments.PoiDialogFragment";
    private static final String PICTURE_DIALOG = "com.openclassrooms.realestatemanager." +
            "controllers.fragments.PictureDialogFragment";

    private static final int ACTION_SELECT_PERMISSION = 110;
    private static final int ACTION_SELECT_VIDEO = 220;

    private PropertyViewModel mPropertyViewModel;
    private ArrayAdapter<CharSequence> mAdapter;

    // For property
    private List<String> mPoiList = new ArrayList<>();
    private List<Photo> mPhotoList = new ArrayList<>();
    private Uri mUriVideoSelected;
    private PropertyAndAddressAndPhotos mPropertyAndAddressAndPhotos;
    private long mPropertyId;
    private long mAdressId;

    private Date mEntryDate = null;
    private Date mSoldDate = null;

    // Listener
    DatePickerDialog.OnDateSetListener mEntryDateSetListener = (datePicker, year, month, dayOfMonth) -> {
        String date = Utils.checkDigit(month) + "/" + Utils.checkDigit(dayOfMonth) + "/" + year;
        //For displaying in the view
        binding.includeDate.editTextEntryDate.setText(date);
        // Set to database
        try {
            mEntryDate = Utils.getDateFromDatePicker(dayOfMonth, month, year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    };

    DatePickerDialog.OnDateSetListener mSoldDateSetListener = (datePicker, year, month, dayOfMonth) -> {
        month = month + 1;
        String date = Utils.checkDigit(month) + "/" + Utils.checkDigit(dayOfMonth) + "/" + year;
        //For displaying in the view
        binding.includeDate.editTextSoldDate.setText(date);
        // Set to database
        try {
            mSoldDate = Utils.getDateFromDatePicker(dayOfMonth, month, year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    };


    @Override
    public FragmentDialogFormBinding getViewBinding() {
        return FragmentDialogFormBinding.inflate(getLayoutInflater());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        initAndCreateGUI();
        return view;
    }


    private void initAndCreateGUI() {
        this.configureViewModel();
        this.configureSpinner();

        if(SharedPreferencesHelper.getActionPropertyMode(Objects.requireNonNull(getActivity())).equals("MODE_UPDATE")) {
            initPropertyViewModel();
            binding.includeDate.editTextSoldDate.setEnabled(true);
        }

        // Init Listener
        this.initAddPOIListener();
        this.initAddPictureListener();
        this.initAddVideoListener();

        this.initSoldDateListener();
        this.initEntryDateListener();
        this.initSoldDateListener();

        this.saveDataListener();
        this.cancelDataListener();
    }

    private void initPropertyViewModel() {
        mPropertyViewModel.getPropertiesByUser(1)
                .observe(this, new Observer<List<PropertyAndAddressAndPhotos>>() {
                    @Override
                    public void onChanged(@Nullable List<PropertyAndAddressAndPhotos> properties) {
                        PropertyAndAddressAndPhotos property = properties.get(SharedPreferencesHelper.getPosition(App.getContext()));
                        updateUIFromProperty(property);
                    }
                });
    }

    private void configureViewModel() {
        configurePropertyViewModel();
    }

    private void configurePropertyViewModel() {
        mPropertyViewModel = new ViewModelProvider(this,ViewModelFactory.getInstance(this.requireContext()))
                .get(PropertyViewModel.class);
    }

    // --- Configure Spinner : values 'type'
    private void configureSpinner() {
        if (getContext() != null) {
            mAdapter = ArrayAdapter.createFromResource(getContext(), R.array.property_type, android.R.layout.simple_spinner_item);
            mAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            binding.spinnerType.setAdapter(mAdapter);
        }
    }

    // --- Listener ---
    public void initAddPOIListener() {
        binding.includeButton.buttonAddPointOfInterest.setOnClickListener(view -> displayPoiDialog());
    }

    public void initAddPictureListener() {
        binding.includeButton.buttonAddPicture.setOnClickListener(view -> {
            displayPictureDialog();
        });
    }

    private void initAddVideoListener() {
        binding.videoInclude.buttonAddVideo.setOnClickListener(view -> selectVideo());
    }

    private void initEntryDateListener() {
        binding.includeDate.editTextEntryDate
                .setOnClickListener(view -> displayEntryDatePickerDialog());
    }

    private void initSoldDateListener() {
        binding.includeDate.editTextSoldDate
                .setOnClickListener(view -> displaySoldDatePickerDialog());
    }

    private void saveDataListener() {
        binding.includeButtonSaveCancel.actionButtonSave
                .setOnClickListener(view -> addPropertyInDatabase());
    }

    private void cancelDataListener() {
        binding.includeButtonSaveCancel.actionButtonCancel
                .setOnClickListener(view -> getDialog().dismiss());
    }


    private void displayPoiDialog() {
        PoiDialogFragment poiDialogFragment = new PoiDialogFragment();
        poiDialogFragment.setStyle(android.app.DialogFragment.STYLE_NO_TITLE, R.style.Dialog_FullScreen);
        poiDialogFragment.setPoi(mPoiList);
        poiDialogFragment.setTargetFragment(PropertyDialogForm.this, 1);
        if (getFragmentManager() != null) {
            poiDialogFragment.show(getFragmentManager(), POI_DIALOG);
        }
    }

    private void displayPictureDialog() {
        PictureDialogFragment pictureDialogFragment = new PictureDialogFragment();
        pictureDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog_FullScreen);
        pictureDialogFragment.setPhotos(mPhotoList);
        pictureDialogFragment.setTargetFragment(PropertyDialogForm.this, 1);
        if (getFragmentManager() != null) {
            pictureDialogFragment.show(getFragmentManager(), PICTURE_DIALOG);
        }
    }

    private void displayEntryDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog entryDialog = new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Dialog_MinWidth,
                mEntryDateSetListener,year,month,day);
        if(entryDialog.getWindow() != null) {
            entryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        entryDialog.show();
    }

    private void displaySoldDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog entryDialog = new DatePickerDialog(Objects.requireNonNull(getContext()),android.R.style.Theme_Holo_Dialog_MinWidth,
                mSoldDateSetListener,year,month,day);
        if(entryDialog.getWindow() != null) {
            entryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        entryDialog.show();
    }

    private void selectVideo() {
        if(getContext() != null) {
            if(!EasyPermissions.hasPermissions(getContext(),PERMISSION_TO_READ)) {
                EasyPermissions.requestPermissions(this,
                        getString(R.string.popup_title_permission_files_access),
                        ACTION_SELECT_PERMISSION,PERMISSION_TO_READ);
                return;
            }
        }
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,ACTION_SELECT_VIDEO);
    }



    // --------------------
    // Permission
    // --------------------


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Calling the appropriate method after activity result
        this.handleResponse(requestCode, resultCode, data);
    }

    private void handleResponse(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_SELECT_VIDEO) {
            if (resultCode == RESULT_OK) { //SUCCESS
                this.mUriVideoSelected = data.getData();
                Glide.with(this) //SHOWING PREVIEW OF VIDEO
                        .load(this.mUriVideoSelected)
                        .apply(RequestOptions.centerCropTransform())
                        .into(this.binding.videoInclude.videoPlaceHolder);
            } else {
                Toast.makeText(getContext(), getString(R.string.toast_title_no_video_chosen), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void sendPOIData(List<String> poiList) {
        mPoiList = poiList;
    }

    @Override
    public void sendPictureData(List<Photo> photoList) {
        mPhotoList = photoList;
    }


    // Update Property's Data
    private void updateUIFromProperty(PropertyAndAddressAndPhotos property) {

        // For property
        Property p = property.property;
        mPropertyId = p.getId();
        // For Address
        mAdressId = property.address.get(0).getId();
        // For Photos


        // Configure the spinner
        List<String> types = Arrays.asList(getResources().getStringArray(R.array.property_type));
        int indexOfType = types.indexOf(property.property.getType());
        binding.spinnerType.setSelection(indexOfType);


        binding.editTextArea.setText(p.getArea());
        binding.editTextPrice.setText(String.valueOf(p.getPrice()));
        binding.editTextSurface.setText(String.valueOf(p.getSurface()));
        binding.editTextNbRooms.setText(String.valueOf(p.getNumberOfRoom()));
        binding.editTextNbBathrooms.setText((String.valueOf(p.getNumberOfBathroom())));
        binding.editTextNbBedrooms.setText(String.valueOf(p.getNumberOfBedroom()));
        binding.editTextDescription.setText(String.valueOf(p.getDescription()));

        // For address
        Address address =  property.address.get(0);

        binding.includeAddress.editTextAddressLine1.setText(address.getAddress1());
        binding.includeAddress.editTextAddressLine2.setText(address.getAddress2());
        binding.includeAddress.editTextAddressCity.setText(address.getCity());
        binding.includeAddress.editTextAddressState.setText(address.getState());
        binding.includeAddress.editTextAddressZip.setText(address.getZip());

        if(property.property.getEntryDate() != null) {
            String dateFormatted = Utils.getFormattedDate(property.property.getEntryDate(),"dd/MM/yyyy");
            binding.includeDate.editTextEntryDate.setText(dateFormatted);
        }


        // For Pictures
        mPhotoList = property.photos;

        // For Pois
        mPoiList = property.property.getPoi();

        // For video
        mUriVideoSelected = Uri.parse(property.property.getUrlVideo());
        Glide.with(this) //SHOWING PREVIEW OF VIDEO
                .load(this.mUriVideoSelected)
                .apply(RequestOptions.centerCropTransform())
                .into(this.binding.videoInclude.videoPlaceHolder);

    }

    private Property getPropertyFromForm() {
        String area = Objects.requireNonNull(binding.editTextArea.getText()).toString();
        String description = Objects.requireNonNull(binding.editTextDescription.getText()).toString();
        String price = Objects.requireNonNull(binding.editTextPrice.getText()).toString();
        String surface = Objects.requireNonNull(binding.editTextSurface.getText()).toString();
        String nbRooms = Objects.requireNonNull(binding.editTextNbRooms.getText()).toString();
        String nbBathrooms = Objects.requireNonNull(binding.editTextNbBathrooms.getText()).toString();
        String nbBedrooms = Objects.requireNonNull(binding.editTextNbBedrooms.getText()).toString();
        String addr1 = Objects.requireNonNull(binding.includeAddress.editTextAddressLine1.getText()).toString();
        String addr2 = Objects.requireNonNull(binding.includeAddress.editTextAddressLine2.getText()).toString();
        String city = Objects.requireNonNull(binding.includeAddress.editTextAddressCity.getText()).toString();
        String state = Objects.requireNonNull(binding.includeAddress.editTextAddressState.getText()).toString();
        String zip = Objects.requireNonNull(binding.includeAddress.editTextAddressZip.getText()).toString();
        String type = binding.spinnerType.getSelectedItem().toString();


        Boolean result = !type.equals("") && !area.equals("") && !description.equals("") && !price.equals("") && !surface.equals("") && !nbRooms.equals("") &&
                !nbBathrooms.equals("") && !nbBedrooms.equals("") &&
                !addr1.equals("") && !city.equals("") && !state.equals("") && !zip.equals("")
                && !mPoiList.isEmpty()
                && !mPhotoList.isEmpty()
                && mUriVideoSelected != null;

        if (result) {
            String url = mPhotoList.get(0).getUrl();
            long priceLong = Long.parseLong(price);
            int surfaceInt = Integer.parseInt(surface);
            int nbRoomsInt = Integer.parseInt(nbRooms);
            int nbBathroomsInt = Integer.parseInt(nbBathrooms);
            int nbBedroomsInt = Integer.parseInt(nbBedrooms);

            Address address = new Address(0, addr1, addr2, city, zip, state);
            List<Address> addresses = new ArrayList<>();
            addresses.add(address);

            Property property = new Property(url, type, area, description, priceLong, surfaceInt, nbRoomsInt, nbBathroomsInt,
                    nbBedroomsInt, true, mEntryDate,mSoldDate, mPoiList, mUriVideoSelected.toString(), addresses, mPhotoList, 1);
            return property;
        }
        return null;
    }

    private void addPropertyInDatabase() {
        SharedPreferencesHelper.setVisibility(App.getContext(),false);
                mAdapter.notifyDataSetChanged();
                Property property = getPropertyFromForm();
                if(property != null) {
                    if(SharedPreferencesHelper.getActionPropertyMode(App.getContext())
                            .equals(SharedPreferencesHelper.MODE_UPDATE)) {
                        // here BUG
                        //int propertyId = SharedPreferencesHelper.getPosition(App.getContext());
                        property.setId(mPropertyId);
                        property.getAddress().get(0).setId(mAdressId);
                mPropertyViewModel.updateProperty(property);
            }else {
                mPropertyViewModel.createProperty(property);
            }
            getDialog().dismiss();
        }
    }

    @Override
    public void onItemClickSelected(PropertyAndAddressAndPhotos property) {
        mPropertyAndAddressAndPhotos = property;
    }
}