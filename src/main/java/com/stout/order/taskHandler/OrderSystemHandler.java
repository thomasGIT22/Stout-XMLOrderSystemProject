/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stout.order.taskHandler;

import com.stout.order.data.OrderSystemDAO;

/**
 *
 * @author ragbr
 */
public interface OrderSystemHandler {
    void handleTask(OrderSystemDAO dao);
}
