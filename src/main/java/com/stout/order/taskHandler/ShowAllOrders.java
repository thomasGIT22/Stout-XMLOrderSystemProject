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
import java.util.List;

/**
 *
 * @author ragbr
 */
public class ShowAllOrders implements OrderSystemHandler {

    @Override
    public void handleTask(OrderSystemDAO dao) {
        UIUtility.showSectionTitle("Show All Orders");

        try {
            List<Order> orders = dao.getAllOrders();
            for (Order order : orders) {
                UIUtility.showMessage("\t" + order);
            }
        } catch (OrderSystemException ex) {
            UIUtility.showErrorMessage(ex.getMessage(), true);
        }

        UIUtility.showMessage("\nShow All Orders complete.");
        UIUtility.pressEnterToContinue();

    }

    public List<Order> getAllOrders(OrderSystemDAO dao) throws OrderSystemException{
        return dao.getAllOrders();
    }
}
