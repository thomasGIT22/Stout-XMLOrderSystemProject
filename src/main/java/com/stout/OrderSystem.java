/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stout;

import com.stout.order.data.OrderSystemDAO;
import com.stout.order.data.OrderSystemDAOCSV;
import com.stout.order.data.OrderSystemDAOFactory;
import com.stout.order.taskHandler.AddOrder;
import com.stout.order.taskHandler.DeleteOrder;
import com.stout.order.taskHandler.FindAnOrder;
import com.stout.order.taskHandler.ShowAllOrders;
import com.stout.order.taskHandler.UpdateOrder;
import com.stout.ui.UIUtility;

/**
 *
 * @author ragbr
 */
public class OrderSystem {

    public static void main(String[] args) {
        UIUtility.showMessage("Program starting...");

        String menuTitle = "Main Menu";
        String[] menuOptions = {
            "1) Add an Order",
            "2) Find an Order",
            "3) Show All Orders",
            "4) Update an Order",
            "5) Delete an Order",
            "6) Exit"
        };
        String prompt = "Your choice:";
        String errorMessage = "Invalid option.  Please try again.";
        String userChoice;
        OrderSystemDAO dao = OrderSystemDAOFactory.getOrderSystemDAO();

        // Start the primary program logic
        boolean working = true;
        while (working) {
            userChoice = UIUtility.showMenuOptions(menuTitle,
                    prompt, menuOptions);
            switch (userChoice) {
                case "1":
                    new AddOrder().handleTask(dao);
                    break;
                case "2":
                    new FindAnOrder().handleTask(dao);
                    break;
                case "3":
                    new ShowAllOrders().handleTask(dao);
                    break;
                case "4":
                    new UpdateOrder().handleTask(dao);
                    break;
                case "5":
                    new DeleteOrder().handleTask(dao);
                    break;
                case "6":
                    working = false;
                    break;
                default:
                    UIUtility.showErrorMessage(errorMessage, true);
            }
        }
        UIUtility.showMessage("\nProgram complete.");
    }

}
