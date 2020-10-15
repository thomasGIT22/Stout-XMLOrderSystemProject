/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stout.order.taskHandler;

import com.stout.order.Order;
import com.stout.order.data.OrderSystemDAO;
import com.stout.order.data.OrderSystemException;
import com.stout.ui.UIUtility;

/**
 *
 * @author ragbr
 */
public class AddOrder implements OrderSystemHandler {

    @Override
    public void handleTask(OrderSystemDAO dao) {
        Order order = new Order();
        UIUtility.showSectionTitle("Add an Order");
        order.setOrderNumber(
                UIUtility.getUserString("Enter the order number: \n"));
        String prompt = "Enter the Vendor ID: \n";
        String notAnIntMessage = "That was not a whole number.";
        String prompt2 = "Enter the Order Date: \n";
        String notADateMessage = "That was a not a valid date.";
        int vendorID;
        boolean needed = true;
        while(needed){
            try{
                order.setOrderDate(UIUtility.getUserDate(prompt2, notADateMessage));
                needed = false;
            } catch(IllegalArgumentException iae ){
                UIUtility.getUserDate(prompt, notADateMessage);
            }
            try{
                order.setVendorID(UIUtility.getUserInt(prompt, notAnIntMessage));
                needed = false;
            } catch(IllegalArgumentException iae){
                UIUtility.showErrorMessage(iae.getMessage(), true);
            }
        }
        
        try{
            dao.createOrderRecord(order);
            UIUtility.showMessage("Order added: " + order);
        } catch (OrderSystemException ex) {
            UIUtility.showErrorMessage(ex.getMessage(), false);
        }
        UIUtility.showMessage("Add Order Complete");
        UIUtility.pressEnterToContinue();
    }

}
