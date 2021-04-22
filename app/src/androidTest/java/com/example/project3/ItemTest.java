package com.example.project3;

import junit.framework.TestCase;

import org.junit.Test;

class ItemTest {
    @Test
    void testToString() {
        Item newitem = new Item("name", "type", "brand", "condition", "quantity", "price", "color", "comments");
    }
}