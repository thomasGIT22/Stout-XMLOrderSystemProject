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
public class DeleteOrder implements OrderSystemHandler {

    @Override
    public void handleTask(OrderSystemDAO dao) {
        UIUtility.showSectionTitle("Delete an Order");
        try {
            List<Order> orders = dao.getAllOrders();
            String[] menuOptions = new String[orders.size()];
            for (int i = 0; i < menuOptions.length; i++) {
                menuOptions[i] = (i + 1) + ") " + orders.get(i).toString();
            }
            String userChoice = UIUtility.showMenuOptions(
                    "Select an Order to Delete",
                     "Your Choice:",
                     menuOptions);
            try {
                int actualChoice = Integer.parseInt(userChoice) - 1;
                Order order = orders.get(actualChoice);
                dao.deleteOrder(order);
                UIUtility.showMessage("Order deleted successfully.");
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                UIUtility.showErrorMessage("No valid Order Selected.", false);
            }

        } catch (OrderSystemException ex) {
            UIUtility.showErrorMessage(ex.getMessage(), true);
        }
        UIUtility.showMessage("\nDelete an Order complete.");
        UIUtility.pressEnterToContinue();

    }

}
