package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import junit.framework.TestCase;

import org.junit.Test;
import static org.junit.Assert.*;


class ItemTest  {

    @Test
    public void testToString() {
        Item item = new Item("a","b","c","d","e","f","g","h");
        assertEquals("c",item.getCondition());
    }
}