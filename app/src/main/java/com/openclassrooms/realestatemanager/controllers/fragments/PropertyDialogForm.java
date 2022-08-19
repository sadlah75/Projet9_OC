package com.openclassrooms.realestatemanager.controllers.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModel.AddressViewModel;
import com.openclassrooms.realestatemanager.ViewModel.PhotoViewModel;
import com.openclassrooms.realestatemanager.ViewModel.PropertyViewModel;
import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.databinding.FragmentDialogFormBinding;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.Address;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.Property;

import java.util.ArrayList;
import java.util.List;

public class PropertyDialogForm extends BaseDialogFragment<FragmentDialogFormBinding>
                                implements PoiDialogFragment.OnInputSelected,PictureDialogFragment.OnInputSelected{

    private static final String POI_DIALOG = "com.openclassrooms.realestatemanager.controllers." +
            "fragments.PoiDialogFragment";
    private static final String PICTURE_DIALOG = "com.openclassrooms.realestatemanager." +
            "controllers.fragments.PictureDialogFragment";
    private PropertyViewModel mPropertyViewModel;
    private AddressViewModel mAddressViewModel;
    private PhotoViewModel mPhotoViewModel;

    private ArrayAdapter<CharSequence> mAdapter;

    // For property
    private List<String> mPoiList = new ArrayList<>();
    private List<Photo> mPhotoList = new ArrayList<>();



    @Override
    public FragmentDialogFormBinding getViewBinding() {
        return FragmentDialogFormBinding.inflate(getLayoutInflater());
    }

    @Override
    public void init() {
        initAndCreateGUI();
    }

    private void initAndCreateGUI() {
        this.configureViewModel();
        this.configureSpinner();

        // Init Listener
        this.initAddPOIListener();
        this.initAddPictureListener();
        this.initAddVideoListener();
        ;
        this.initSoldDateListener();
        this.initEntryDateListener();

        this.saveDataListener();
        this.cancelDataListener();
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
        binding.includeButton.buttonAddPicture.setOnClickListener(view -> displayPictureDialog());
    }

    private void initAddVideoListener() {
        binding.videoInclude.buttonAddVideo.setOnClickListener(view -> Log.i("add", "addVideo"));
    }

    private void initEntryDateListener() {
        binding.includeDate.editTextEntryDate
                .setOnClickListener(view -> Log.i("add", "entry date"));
    }

    private void initSoldDateListener() {
        binding.includeDate.editTextSoldDate
                .setOnClickListener(view -> Log.i("add", "sold date"));
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
        poiDialogFragment.setTargetFragment(PropertyDialogForm.this, 1);
        if (getFragmentManager() != null) {
            poiDialogFragment.show(getFragmentManager(), POI_DIALOG);
        }
    }

    private void displayPictureDialog() {
        PictureDialogFragment pictureDialogFragment = new PictureDialogFragment();
        pictureDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog_FullScreen);
        pictureDialogFragment.setTargetFragment(PropertyDialogForm.this, 1);
        if (getFragmentManager() != null) {
            pictureDialogFragment.show(getFragmentManager(), PICTURE_DIALOG);
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


    // Save property's data
    private void addPropertyInDatabase() {
        String area = binding.editTextArea.getText().toString();
        String description = binding.editTextDescription.getText().toString();
        String price = binding.editTextPrice.getText().toString();
        String surface =  binding.editTextSurface.getText().toString();
        String nbRooms = binding.editTextNbRooms.getText().toString();
        String nbBathrooms = binding.editTextNbBathrooms.getText().toString();
        String nbBedrooms = binding.editTextNbBedrooms.getText().toString();
        String addr1 = binding.includeAddress.editTextAddressLine1.getText().toString();
        String addr2 = binding.includeAddress.editTextAddressLine2.getText().toString();
        String city = binding.includeAddress.editTextAddressCity.getText().toString();
        String state = binding.includeAddress.editTextAddressState.getText().toString();
        String zip = binding.includeAddress.editTextAddressZip.getText().toString();
        String type = binding.spinnerType.getSelectedItem().toString();

        Boolean result = !area.equals("") && !description.equals("") && !price.equals("") && !surface.equals("") && !nbRooms.equals("") &&
                !nbBathrooms.equals("") && !nbBedrooms.equals("") &&
                !addr1.equals("") && !city.equals("") && !state.equals("") && !zip.equals("")
                && !mPoiList.isEmpty()
                && !mPhotoList.isEmpty();

       if(result) {
           String url = mPhotoList.get(0).getUrl();
           long priceLong = Long.parseLong(price);
           int surfaceInt = Integer.parseInt(surface);
           int nbRoomsInt = Integer.parseInt(nbRooms);
           int nbBathroomsInt = Integer.parseInt(nbBathrooms);
           int nbBedroomsInt = Integer.parseInt(nbBedrooms);

           
           Address address = new Address(0,addr1,addr2,city,zip,state);

           Property property = new Property(url,type,area,description,priceLong,surfaceInt,nbRoomsInt,nbBathroomsInt,
                   nbBedroomsInt,true,null,mPoiList,address,mPhotoList,1);
           mPropertyViewModel.createProperty(property);

           getDialog().dismiss();

       }
    }
}