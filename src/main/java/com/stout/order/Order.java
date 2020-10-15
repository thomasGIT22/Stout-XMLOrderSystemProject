/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stout.order;

import java.time.LocalDate;

/**
 *
 * @author ragbr
 */
public class Order implements Comparable<Order>{
    private String orderNumber;
    private LocalDate orderDate;
    private int vendorID;
    private static final int MINIMUM_VENDOR_ID = 101;
    private static final LocalDate MINIMUM_ORDER_DATE = LocalDate.now();
    
    public Order(String orderNumber, LocalDate orderDate, int vendorID){
        //validateVendorID();
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.vendorID = vendorID;
    }
    
    public Order(Order other){
        this.orderNumber = new String(other.getOrderNumber().toCharArray());
        this.orderDate = other.getOrderDate();
        this.vendorID = other.getVendorID();
    }
    
    public Order() {
        this("BBY01-XXXXXXXX", LocalDate.now(), 12345);
    }
    
    public String getOrderNumber(){
        return orderNumber;
    }
    
    public void setOrderNumber(String orderNumber){
        this.orderNumber = orderNumber;
    }
    
    public LocalDate getOrderDate(){
        return orderDate;
    }
    
    public void setOrderDate(LocalDate orderDate) {
        //validateOrderDate(orderDate);
        this.orderDate = orderDate;
    }
    
    public int getVendorID(){
        return vendorID;
    }
    
    public void setVendorID(int vendorID){
        //validateVendorID(vendorID);
        this.vendorID = vendorID;
    }
    
    public static LocalDate getHighestAllowedOrderDate(){
        return LocalDate.now();
    }
    
    public static LocalDate getLowestAllowedOrderDate(){
        return LocalDate.now();
    }
    
    private void validateOrderDate(LocalDate OrderDate){
        if(Order.MINIMUM_ORDER_DATE.compareTo(OrderDate) > 0){
            throw new IllegalArgumentException(
                    "Order Dates can only be set to present dates.");
        }
        if(Order.getHighestAllowedOrderDate().compareTo(OrderDate) < 0){
            throw new IllegalArgumentException("Order Date cannot be later "
                    + "than " + getHighestAllowedOrderDate() + ".");
        }
    }
    
    @Override
    public String toString() {
        return orderNumber + " " + orderDate + " " + vendorID;
    }

    /*if returns a positive number, list is unsorted. negative number means
      list is sorted. 0 means that they are equal to each other
      when you used compareTo() in a method, always make sure it returns an
      integer */
    @Override
    public int compareTo(Order other) {
        return this.toString().compareTo(other.toString());
    }
}
