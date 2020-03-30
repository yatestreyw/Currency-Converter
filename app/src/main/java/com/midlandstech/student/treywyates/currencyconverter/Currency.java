package com.midlandstech.student.treywyates.currencyconverter;

//Holds the information needed for a Currency
public class Currency {
    private int id;
    private String name;
    private double price;

    //Creates a currency with an id, name, and price 
    public Currency(int newId, String newName, double newPrice) {
        setId(newId);
        setName(newName);
        setPrice(newPrice);
    }

    //Sets a new id
    public void setId(int newId) {
        id = newId;
    }

    //Sets a new name
    public void setName(String newName) {
        name = newName;
    }

    //Sets a new price
    public void setPrice(double newPrice) {
        if (newPrice >= 0.0)
            price = newPrice;
    }

    //Returns the current id
    public int getId() {
        return id;
    }

    //Returns the current name
    public String getName() {
        return name;
    }

    //Returns the current price
    public double getPrice() {
        return price;
    }

    //toString() for output 
    public String toString() {
        return id + "; " + name + "; " + price;
    }
}
