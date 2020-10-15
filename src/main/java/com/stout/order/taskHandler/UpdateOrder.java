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
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ragbr
 */
public class UpdateOrder implements OrderSystemHandler {

    @Override
    public void handleTask(OrderSystemDAO dao) {

        UIUtility.showSectionTitle("Update an Order");

        try {
            List<Order> orders = dao.getAllOrders();
            String[] menuOptions = new String[orders.size()];
            for (int i = 0; i < menuOptions.length; i++) {
                menuOptions[i] = (i + 1) + ") " + orders.get(i).toString();
            }
            String userChoice = UIUtility.showMenuOptions(
                    "Select an Order to Update",
                    "Your Choice:",
                    menuOptions);
            try {
                int actualChoice = Integer.parseInt(userChoice) - 1;
                Order order = orders.get(actualChoice);
                updateOrder(dao, order);
                UIUtility.showMessage("Order updated successfully.");
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                UIUtility.showErrorMessage("No valid Order selected.", false);
            }

        } catch (OrderSystemException ex) {
            UIUtility.showErrorMessage(ex.getMessage(), true);
        }

        UIUtility.showMessage("\nUpdate an Order complete.");
        UIUtility.pressEnterToContinue();
    }
    
    private void updateOrder(OrderSystemDAO dao, Order order) throws OrderSystemException {
        String msgUpdatingOrder = "Updating Order: " + order;
        String msgOrderNumber = "Order Number: " + order.getOrderNumber();
        String msgCurrentDate = "Current Date: " + order.getOrderDate();
        String msgCurrentVendorID = "Current VendorID: " + order.getVendorID();
        
        String current = " (Press Enter to keep the current value):";
        String promptEnterOrderNumber = "Enter the new order number" + current;
        String promptEnterDate = "Enter the new order date:";
        String promptVendorID = "Enter the new vendor ID" + current;
        String DateError = "That is not a valid date.";

        Order updated = new Order(order);
        UIUtility.showMessage(msgUpdatingOrder);
        UIUtility.showMessage(msgOrderNumber);


        String updatedOrderNumber = UIUtility.getUserString(promptEnterOrderNumber);
        if (!updatedOrderNumber.isBlank()) {
            updated.setOrderNumber(updatedOrderNumber);
        }

        UIUtility.showMessage(msgCurrentDate);
        LocalDate updatedOrderDate = UIUtility.getUserDate(
                promptEnterDate,
                DateError
        );
        updated.setOrderDate(updatedOrderDate);
        
        UIUtility.showMessage(msgCurrentVendorID);
        String updatedVendorID = UIUtility.getUserString(promptVendorID);
        if (!updatedVendorID.isBlank()) {
            updated.setVendorID(Integer.parseInt(updatedVendorID));
        }

        dao.updateOrder(order, updated);
    }

}
