package com.example.marius.shoppingapp.utils;

import com.example.marius.shoppingapp.classes.ShoppingList;

import java.util.Comparator;

public class ChronologicalComparator implements Comparator<ShoppingList> {
    @Override
    public int compare(ShoppingList shoppingList, ShoppingList t1) {
        return shoppingList.getDate().compareTo(t1.getDate());
    }
}
