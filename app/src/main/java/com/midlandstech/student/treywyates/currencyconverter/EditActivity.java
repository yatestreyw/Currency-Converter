package com.midlandstech.student.treywyates.currencyconverter;

import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//Lets the user edit a Currency in the database
public class EditActivity extends AppCompatActivity {
    DatabaseManager dbManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        updateView();
    }

    // Creates the View with a list of all added currencies
    public void updateView() {
        ArrayList<Currency> currencies = dbManager.selectAll();
        if (currencies.size() > 0) {
            // Creates ScrollView and GridLayout
            ScrollView scrollView = new ScrollView(this);
            GridLayout grid = new GridLayout(this);
            grid.setRowCount(currencies.size());
            grid.setColumnCount(4);

            // Creates all the arrays of the components
            TextView[] ids = new TextView[currencies.size()];
            EditText[][] namesAndPrices = new EditText[currencies.size()][2];
            Button[] buttons = new Button[currencies.size()];
            ButtonHandler bh = new ButtonHandler();

            // Get the width of the screen
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            int width = size.x;

            int i = 0;
            for (Currency currency : currencies) {
                // Creates the TextView for the currency's id
                ids[i] = new TextView(this);
                ids[i].setGravity(Gravity.CENTER);
                ids[i].setText("" + currency.getId());

                // Creates the two EditTexts for the currency's name and price
                namesAndPrices[i][0] = new EditText(this);
                namesAndPrices[i][1] = new EditText(this);
                namesAndPrices[i][0].setText(currency.getName());
                namesAndPrices[i][1].setText("" + currency.getPrice());
                namesAndPrices[i][1]
                        .setInputType(InputType.TYPE_CLASS_NUMBER |
                                InputType.TYPE_NUMBER_FLAG_DECIMAL);
                namesAndPrices[i][0].setId(10 * currency.getId());
                namesAndPrices[i][1].setId(10 * currency.getId() + 1);

                // Creates and labels the button
                buttons[i] = new Button(this);
                buttons[i].setText("Update");
                buttons[i].setId(currency.getId());

                // Sets up the event handling
                buttons[i].setOnClickListener(bh);

                // Adds everything to the grid
                grid.addView(ids[i], width / 10,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(namesAndPrices[i][0], (int) (width * .3),
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(namesAndPrices[i][1], (int) (width * .27),
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                grid.addView(buttons[i], (int) (width * .30),
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                i++;
            }
            scrollView.addView(grid);
            setContentView(scrollView);
        }
    }

    //Handles button clicks
    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {

            // Gets the name and price from the edit texts
            int currencyId = v.getId();
            EditText nameET = (EditText) findViewById(10 * currencyId);
            EditText priceET = (EditText) findViewById(10 * currencyId + 1);
            String name = nameET.getText().toString();
            String priceString = priceET.getText().toString();

            // Updates the entry in the database
            try {
                double price = Double.parseDouble(priceString);
                dbManager.updateById(currencyId, name, price);
                Toast.makeText(EditActivity.this, "Currency updated",
                        Toast.LENGTH_SHORT).show();

                // Refreshes/updates the screen
                updateView();
            } catch (NumberFormatException nfe) {
                Toast.makeText(EditActivity.this,
                        "Price error", Toast.LENGTH_LONG).show();
            }
        }
    }
}
