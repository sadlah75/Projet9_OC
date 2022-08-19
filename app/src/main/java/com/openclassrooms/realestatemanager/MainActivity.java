package com.openclassrooms.realestatemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewMain;
    private TextView textViewQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealEstateManagerDatabase db = RealEstateManagerDatabase.getInstance(this);
        setContentView(R.layout.activity_main);

        this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main);
        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);

        this.configureTextViewMain();
        this.configureTextViewQuantity();
    }

    private void configureTextViewMain(){
        this.textViewMain.setTextSize(15);
        this.textViewMain.setText(R.string.description_property);
    }

    private void configureTextViewQuantity(){
        int quantity = Utils.convertDollarToEuro(100);
        this.textViewQuantity.setTextSize(20);
        this.textViewQuantity.setText(String.format(Locale.getDefault(),"%d",quantity));
    }
}
