package com.midlandstech.student.treywyates.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//Adds a new Currency in the database
public class AddActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_add);
    }

    public void insert(View v) {
        // Gets the name and price from the EditTexts
        EditText nameEditText = (EditText) findViewById(R.id.input_name);
        EditText priceEditText = (EditText) findViewById(R.id.input_price);
        String name = nameEditText.getText().toString();
        String priceString = priceEditText.getText().toString();

        // Put the new currency into the database
        try {
            double price = Double.parseDouble(priceString);
            Currency currency = new Currency(0, name, price);
            dbManager.insert(currency);
            Toast.makeText(this, "Currency added", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException nfe) {
            Toast.makeText(this, "Price error", Toast.LENGTH_LONG).show();
        }

        // Clear the EditTexts
        nameEditText.setText("");
        priceEditText.setText("");
    }

    public void goBack(View v) {
        this.finish();
    }
}
