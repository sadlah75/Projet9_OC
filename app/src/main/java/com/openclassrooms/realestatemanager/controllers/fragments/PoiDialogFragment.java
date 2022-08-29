package com.openclassrooms.realestatemanager.controllers.fragments;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PoiAdapter;
import com.openclassrooms.realestatemanager.databinding.FragmentDialogPoiFormBinding;

import java.util.ArrayList;
import java.util.List;

public class PoiDialogFragment extends BaseDialogFragment<FragmentDialogPoiFormBinding> {


    private static final String TAG = "PoiDialogFragment";
    private List<String> mPoiList = new ArrayList<>();
    private ArrayAdapter<CharSequence> mAdapter;

    private PoiAdapter mAdapterPoi;
    private OnInputSelected mOnIputSelected;


    @Override
    public FragmentDialogPoiFormBinding getViewBinding() {
        return FragmentDialogPoiFormBinding.inflate(getLayoutInflater());
    }

    public void setPoi(List<String> pois) {
        mPoiList = pois;
    }

    @Override
    public void init() {
        initAndShowGUI();
    }

    private void initAndShowGUI() {
        getDialog().setTitle("Select point of interest");
        this.configureRecyclerView();
        this.configureSpinner();

        // Init Listener
        this.configureAddPoiListener();
        this.configureCancelListener();
        this.configureSaveListener();
    }

    private void configureRecyclerView() {
        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewObject.recyclerViewPoi.setLayoutManager(layoutManager);
        mAdapterPoi = new PoiAdapter(mPoiList);
        // Set CustomAdapter as the adapter for RecyclerView.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.getRoot().getContext(),
                layoutManager.getOrientation());
        binding.recyclerViewObject.recyclerViewPoi.addItemDecoration(dividerItemDecoration);
        binding.recyclerViewObject.recyclerViewPoi.setAdapter(mAdapterPoi);
    }

    private void configureSpinner() {
        if (getContext() != null) {
            mAdapter = ArrayAdapter.createFromResource(getContext(), R.array.point_of_interest, android.R.layout.simple_spinner_item);
            mAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            binding.poiSpinner.setAdapter(mAdapter);
        }
    }

    private void configureAddPoiListener() {
        binding.poiButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = binding.poiSpinner.getSelectedItem().toString();
                if(!mPoiList.contains(input)) {
                    mPoiList.add(input);
                }
                mAdapterPoi.updateList(mPoiList);
            }
        });
    }

    private void configureCancelListener() {
        binding.actionButtonSaveCancel.actionButtonCancel
                .setOnClickListener(view -> getDialog().dismiss());
    }

    private void configureSaveListener() {
        binding.actionButtonSaveCancel.actionButtonSave
                .setOnClickListener(view -> saveData());
    }

    private void saveData() {
        if (mPoiList.size() > 0) {
            mOnIputSelected.sendPOIData(mPoiList);
            Toast.makeText(getContext(), "Poi added successfully", Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
        } else {
            Toast.makeText(getContext(), "Please add at least one item", Toast.LENGTH_SHORT).show();
        }
    }


    // --- Callback---


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnIputSelected = (OnInputSelected)getTargetFragment();
        }catch (ClassCastException e) {
            Log.e(TAG,"onAttach: ClassCastException : " + e.getMessage());
        }
    }

    public interface OnInputSelected {
        void sendPOIData(List<String> poiList);
    }
}
