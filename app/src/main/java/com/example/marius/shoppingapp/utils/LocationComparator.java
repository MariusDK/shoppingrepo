package com.example.marius.shoppingapp.utils;

import com.example.marius.shoppingapp.classes.ShoppingList;

import java.util.Comparator;

public class LocationComparator implements Comparator<ShoppingList> {
    @Override
    public int compare(ShoppingList shoppingList, ShoppingList t1) {
        return shoppingList.getLocation().compareTo(t1.getLocation());
    }
}
