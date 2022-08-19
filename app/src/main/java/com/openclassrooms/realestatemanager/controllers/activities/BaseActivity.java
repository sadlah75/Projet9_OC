package com.openclassrooms.realestatemanager.controllers.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.viewbinding.ViewBinding;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {
    abstract T getViewBinding();
    protected abstract void init();
    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    private void initBinding() {
        binding = getViewBinding();
        init();
        setContentView(binding.getRoot());
    }
}