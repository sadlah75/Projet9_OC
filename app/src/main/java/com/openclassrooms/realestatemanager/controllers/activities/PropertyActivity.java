package com.openclassrooms.realestatemanager.controllers.activities;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ViewModel.PropertyViewModel;
import com.openclassrooms.realestatemanager.controllers.fragments.DetailFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.PropertyDialogForm;
import com.openclassrooms.realestatemanager.controllers.fragments.PropertyFragment;
import com.openclassrooms.realestatemanager.databinding.ActivityPropertyBinding;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.PropertyAndAddressAndPhotos;
import com.openclassrooms.realestatemanager.model.User;
import com.openclassrooms.realestatemanager.utils.SharedPreferencesHelper;


public class PropertyActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,PropertyFragment.OnItemClickListener {

    public static final int USER_ID = 1;
    public static final String PROPERTY_DETAILS = "PropertyDetails";
    private static final String FRAGMENT_FORM_TAG = "PropertyDialogForm";

    private PropertyFragment mPropertyFragment;
    private DetailFragment mDetailFragment;

    private PropertyViewModel mPropertyViewModel;

    // Variables : Navigation View HEADER
    private TextView mUsername;
    private ImageView mPicture;

    private RecyclerView recyclerView;
    private @NonNull ActivityPropertyBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropertyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

    }

    protected void init() {
        recyclerView = findViewById(R.id.fragment_property_recyclerview);

        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureNavigationHeader();
        
        this.configureAndShowPropertyFragment();
        this.configureAndShowDetailFragment();
    }

    // -------------------------
    // Configuration
    // -------------------------


    // Configure ToolBar
    private void configureToolbar() {
        setSupportActionBar(binding.toolbar);
    }

    // Configure Drawer Layout
    private void configureDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                binding.activityPropertyDrawerLayout,
                binding.toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        binding.activityPropertyDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView() {
        binding.activityPropertyNavigationView.setNavigationItemSelectedListener(this);
    }

    // Configure Navigation Header
    private void configureNavigationHeader() {

        View headerView = binding.activityPropertyNavigationView.getHeaderView(0);
        mUsername = headerView.findViewById(R.id.menu_drawer_username);
        mPicture = headerView.findViewById(R.id.menu_drawer_picture);

        getCurrentUser(USER_ID);
    }

    // Get current user
    private void getCurrentUser(int userId) {
        mPropertyViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this))
                .get(PropertyViewModel.class);
        mPropertyViewModel.init(USER_ID);
        mPropertyViewModel.getUser(USER_ID).observe(this, user -> updateHeaderUser(user));
    }

    // Update Nav Header with user'data
    private void updateHeaderUser(User user) {
        mUsername.setText(user.getUsername());
        Glide.with(this).load(user.getUrlPhoto())
                .apply(RequestOptions.circleCropTransform()).into(this.mPicture);
    }


    // Inflate the menu and add it to the Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    // Handle actions on menu items
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                SharedPreferencesHelper.setActionPropertyMode(this,SharedPreferencesHelper.MODE_CREATE);
                displayPropertyDialogForm();
                return true;
            case R.id.menu_update:
                configureCheckbox();
                return true;
            case R.id.menu_search:
                Toast.makeText(this, "Item Search", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void configureCheckbox() {
        SharedPreferencesHelper.setVisibility(this,
                !SharedPreferencesHelper.isVisible(this));
        PropertyFragment.mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if(binding.activityPropertyDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.activityPropertyDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    // Handle Navigation Item Click
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_drawer_map:
                break;
            case R.id.nav_drawer_settings:
                break;
            case R.id.nav_drawer_logout:
                break;
            default:
                break;
        }
        binding.activityPropertyDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    // -------------------
    // FRAGMENTS
    // -------------------


    private void configureAndShowPropertyFragment() {
        // A - Get FragmentManager and try to find existing instance of fragment in FrameLayout container
        mPropertyFragment = (PropertyFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_layout_property_activity);
        if(mPropertyFragment == null) {
            // B -- Create new property fragment
            mPropertyFragment = new PropertyFragment();
            // C - Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_property_activity,mPropertyFragment)
                    .commit();
        }
    }

    private void configureAndShowDetailFragment() {
        mDetailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_layout_detail_activity);
        // A - We only add DetailFragment in Tablet mode (If found frame_layout_detail_fragment)
        Log.i("detail","result 1: " + String.valueOf(mDetailFragment == null));
        Log.i("detail","result 2: " + String.valueOf( findViewById(R.id.frame_layout_detail_activity) != null));
        if (mDetailFragment == null && findViewById(R.id.frame_layout_detail_activity) != null) {
            mDetailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_detail_activity,mDetailFragment);
        }
    }

    // --------------
    // Action
    // --------------

    /**
     * @method displayPropertyDialogForm
     * Create and open custom dialog by applying full screen style
     */

    private void displayPropertyDialogForm() {
        PropertyDialogForm propertyDialogForm = new PropertyDialogForm();
        propertyDialogForm.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog_FullScreen);
        propertyDialogForm.show(getSupportFragmentManager(), FRAGMENT_FORM_TAG);
    }

    // --- Callback ---
    @Override
    public void onItemClickSelected(PropertyAndAddressAndPhotos property) {
        if (SharedPreferencesHelper.getActionPropertyMode(this).equals(SharedPreferencesHelper.MODE_UPDATE)) {
            displayPropertyDialogForm();
        }else {
            if (mDetailFragment != null && mDetailFragment.isVisible()) {
                mDetailFragment.displayPropertyOnTablet(property);
            } else {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra(PROPERTY_DETAILS, property);
                startActivity(intent);
            }
        }
    }
}