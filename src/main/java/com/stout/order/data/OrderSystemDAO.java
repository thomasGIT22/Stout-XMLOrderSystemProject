/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stout.order.data;

import com.stout.order.Order;
import java.util.ArrayList;

/**
 *
 * @author ragbr
 */
public interface OrderSystemDAO {

    /**
     * Creates a new Order record based on the values in the supplied Order.
     *
     * @param order the Order object with the record values
     * @throws OrderSystemException
     */
    void createOrderRecord(Order order) throws OrderSystemException;

    /**
     * Returns the Order record associated with the orderNumber or null if there
     * is no such Order.
     *
     * @param orderNumber the identifier of the desired Order record
     * @return the associated Order or null if not found
     * @throws OrderSystemException
     */
    Order getOrderByNumber(String orderNumber) throws OrderSystemException;

    /**
     * Returns a list of all the current Order records.
     *
     * @return list of Order records
     * @throws OrderSystemException
     */
    ArrayList<Order> getAllOrders() throws OrderSystemException;

    /**
     * Looks for the original Order and updates it to match the updated Order. If
     * the original Car cannot be found, the method will throw an Exception.
     *
     * @param original The Order record to be updated
     * @param updated The Order containing the updated values
     * @throws OrderSystemException
     */
    void updateOrder(Order original, Order updated) throws OrderSystemException;

    /**
     * Permanently deletes the supplied Order record
     *
     * @param order the Order to delete
     * @throws OrderSystemException
     */
    void deleteOrder(Order order) throws OrderSystemException;

    /**
     * Permanently deletes the Order record with the supplied license plate value.
     *
     * @param orderNumber the unique identifier for the Order to be deleted
     * @throws OrderSystemException
     */
    void deleteOrder(String orderNumber) throws OrderSystemException;


}

