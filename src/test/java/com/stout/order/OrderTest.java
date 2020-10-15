/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stout.order;

import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * IF YOU GET TIME, TEST THE CONSTRUCTORS
 * @author ragbr
 */
public class OrderTest {
    
    public OrderTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getOrderNumber method, of class Order.
     */
    @Test
    public void testGetOrderNumber() {
        Order instance = new Order("BBY01-12345678", LocalDate.now(), 16453);
        String expResult = "BBY01-12345678";
        String result = instance.getOrderNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of setOrderNumber method, of class Order.
     */
    @Test
    public void testSetOrderNumber() {
        String orderNumber = "12345678";
        Order instance = new Order();
        instance.setOrderNumber(orderNumber);
        assertEquals(orderNumber, instance.getOrderNumber());
    }

    /**
     * Test of getOrderDate method, of class Order.
     */
    @Test
    public void testGetOrderDate() {
        Order instance = new Order("BBY01-12345678", LocalDate.now(), 16453);
        LocalDate expResult = LocalDate.now();
        LocalDate result = instance.getOrderDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of setOrderDate method, of class Order.
     */
    @Test
    public void testSetOrderDate() {
        LocalDate orderDate = LocalDate.now();
        Order instance = new Order();
        instance.setOrderDate(orderDate);
        assertEquals(orderDate, instance.getOrderDate());
    }

    /**
     * Test of getVendorID method, of class Order.
     */
    @Test
    public void testGetVendorID() {
        Order instance = new Order("BBY01-12345678", LocalDate.now(), 16453);
        int expResult = 16453;
        int result = instance.getVendorID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setVendorID method, of class Order.
     */
    @Test
    public void testSetVendorID() {
        int vendorID = 1001;
        Order instance = new Order();
        instance.setVendorID(vendorID);
        assertEquals(vendorID, instance.getVendorID());
    }

    /**
     * Test of getHighestAllowedOrderDate method, of class Order.
     */
    @Test
    public void testGetHighestAllowedOrderDate() {
        LocalDate expResult = LocalDate.now();
        LocalDate result = Order.getHighestAllowedOrderDate();
        assertEquals(expResult, result);
    }
    @Test
    public void testGetLowestAllowedOrderDate() {
        LocalDate expResult = LocalDate.now();
        LocalDate result = Order.getLowestAllowedOrderDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Order.
     */
    @Test
    public void testToString() {
        Order instance = new Order("BBY01-12345678", LocalDate.now(), 16453);
        String expResult = "BBY01-12345678" + " " + LocalDate.now() +
                " " + "16453";
        //return orderNumber + " " + orderDate + " " + vendorID;
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of compareTo method, of class Order.
     */
    @Test
    public void testCompareToPositive() {
        System.out.println("compareTo");
        Order other = null;
        Order instance = new Order();
        int expResult = 0;
        int result = instance.compareTo(other);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    @Test
    public void testCompareToNegative() {
        System.out.println("compareTo");
        Order other = null;
        Order instance = new Order();
        int expResult = 0;
        int result = instance.compareTo(other);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    @Test
    public void testCompareToEqual() {
        System.out.println("compareTo");
        Order other = null;
        Order instance = new Order();
        int expResult = 0;
        int result = instance.compareTo(other);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
