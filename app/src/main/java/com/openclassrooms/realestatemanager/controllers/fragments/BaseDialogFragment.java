package com.openclassrooms.realestatemanager.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.viewbinding.ViewBinding;

public abstract class BaseDialogFragment < T extends ViewBinding> extends DialogFragment {

    public abstract T getViewBinding();
    protected T binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initBinding();
    }

    private View initBinding() {
        binding = getViewBinding();
        return binding.getRoot();
    }
}
