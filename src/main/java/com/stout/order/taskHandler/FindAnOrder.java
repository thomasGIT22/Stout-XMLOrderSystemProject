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
public class FindAnOrder implements OrderSystemHandler{

    @Override
    public void handleTask(OrderSystemDAO dao) {

        UIUtility.showSectionTitle("Find an Order");

        String prompt = "Enter an order number:";
        String orderNumber = UIUtility.getUserString(prompt);
        UIUtility.showMessage("Searching for order number " + orderNumber
                + "...");
        Order order;
        try{
            order = dao.getOrderByNumber(orderNumber);
            if(null == order){
                UIUtility.showMessage("No order found with number: "
                        + orderNumber);
            } else {
                UIUtility.showMessage("Retrieved Order: " + order);
            }
        } catch(OrderSystemException cde){
            UIUtility.showErrorMessage(cde.getMessage(), true);
        }
        UIUtility.showMessage("\nFind an Order complete.");
        UIUtility.pressEnterToContinue();
    }

    public Order getOrderByNumber(OrderSystemDAO dao, String orderNumber)
            throws OrderSystemException{
        return dao.getOrderByNumber(orderNumber);
    }

}
