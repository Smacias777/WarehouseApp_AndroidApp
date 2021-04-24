package com.example.project3;

import java.util.ArrayList;

/**
 * This class contains all the "keys" (item names) that were added to the database
 */
public class Keys {

    private static ArrayList<String> arr = new ArrayList<>();

    public void add(String value )
    {
        arr.add(value);
    }
    public String get(int index)
    {
        return arr.get(index);
    }
    public int size()
    {
        return arr.size();
    }

}
