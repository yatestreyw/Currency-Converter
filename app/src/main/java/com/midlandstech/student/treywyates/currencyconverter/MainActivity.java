package com.midlandstech.student.treywyates.currencyconverter;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    //Instance of our Model
    public static Currency currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        currency = new Currency(0, null, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                Log.w("MainActivity", "Add selected");
                Intent addIntent
                        = new Intent(this, AddActivity.class);
                this.startActivity(addIntent);
                return true;
            case R.id.action_edit:
                Log.w("MainActivity", "Edit selected");
                Intent editIntent
                        = new Intent(this, EditActivity.class);
                this.startActivity(editIntent);
                return true;
            case R.id.action_from:
                Log.w("MainActivity", "From selected");
                Intent fromIntent
                        = new Intent(this, FromActivity.class);
                this.startActivity(fromIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Calculates the US dollar amount of the input amount of the chosen currency
    public void calculate(View view) {

        //Retrieves the user input
        EditText amountET = findViewById(R.id.ETInput);
        TextView answerTV = findViewById(R.id.TVAnswer);
        Double amount = Double.parseDouble(amountET.getText().toString());

        //If the price is 0, then a Currency has not been chosen, so alert the user
        //(0 is the default of the static instance of the Model)
        if (currency.getPrice() == 0) {
            Toast.makeText(MainActivity.this, "Choose a currency first",
                    Toast.LENGTH_SHORT).show();
        } else {
            //Otherwise we calculate the amount and output to the TextView
            Double convertedAmount = currency.getPrice() * amount;
            DecimalFormat format = new DecimalFormat("0.00");
            answerTV.setText(amount + " " + currency.getName() + " = $" + format.format(convertedAmount)
                    + " " + "USD");
        }


    }
}
