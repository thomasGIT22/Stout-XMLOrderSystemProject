/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stout.order.data;

/**
 *
 * @author ragbr
 */
public class OrderSystemDAOFactory {
    private static final String DAO_SOURCE = "XML";

    public static OrderSystemDAO getOrderSystemDAO(){
        OrderSystemDAO dao = null;
        switch(DAO_SOURCE){
            case "CSV":
                //dao = new OrderSystemDAOCSV();
                break;
            case "XML":
                dao = new OrderSystemDAOXML();
                break;
            case "MYSQL":
                break;
            default:
        }
        return dao;
    }

}
