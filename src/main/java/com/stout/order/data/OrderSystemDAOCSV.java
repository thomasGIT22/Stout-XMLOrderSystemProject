/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stout.order.data;

import com.stout.order.Order;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ragbr
 */
public class OrderSystemDAOCSV implements OrderSystemDAO {

    private static final String FILE_NAME = "orders.csv";
    private static ArrayList<Order> orders;

    private void readFromFile() throws OrderSystemException {
        try(Scanner in = new Scanner(new File(FILE_NAME))){
            orders = new ArrayList<>();
            int lineCount = 0;
            String line;
            String[] fields;
            //String licensePlate;
            //String make;
            //String model;
            String orderNumber;
            LocalDate orderDate;
            int vendorID;
            
            //int modelYear;
            while(in.hasNextLine()){
                lineCount++;
                line = in.nextLine();
                fields = line.split(",");
                orderNumber = fields[0];
                try{
                    orderDate = LocalDate.parse(fields[1]);
                } catch(NumberFormatException nfe) {
                    throw new OrderSystemException(nfe.getMessage()
                            + "CSV Line " + lineCount);
                }
                try{
                    vendorID = Integer.parseInt(fields[2]);
                } catch(NumberFormatException nfe){
                    throw new OrderSystemException(nfe.getMessage()
                            + "CSV Line " + lineCount);
                }
                orders.add(new Order(orderNumber, orderDate, vendorID));
            }
        } catch(FileNotFoundException fnfe){
            throw new OrderSystemException(fnfe);
        }
    }

    private void saveToFile() throws OrderSystemException {
        try(PrintWriter writer = new PrintWriter(new File(FILE_NAME))){
            String line;
            for (Order order : orders) {
                line = order.getOrderNumber() + ","
                        + order.getOrderDate() + ","
                        + order.getVendorID() + ",";
                writer.println(line);
            }
        } catch (FileNotFoundException ex) {
            throw new OrderSystemException(ex);
        }
    }
    
    private void verifyOrderList() throws OrderSystemException {
        if(null == orders){
            readFromFile();
        }
    }

    @Override
    public void createOrderRecord(Order order) throws OrderSystemException {
        verifyOrderList();
        Order checkOrder = getOrderByNumber(order.getOrderNumber());
        if(null != checkOrder){
            throw new OrderSystemException("Order Numbers must be unique.");
        }
        orders.add(order);
        saveToFile();
    }

    @Override
    public Order getOrderByNumber(String orderNumber) throws OrderSystemException {
        verifyOrderList();
        Order order = null;
        for (Order order1 : orders) {
            if(order1.getOrderNumber().equals(orderNumber)){
                order = order1;
                break;
            }
        }
        return order;
    }

    @Override
    public ArrayList<Order> getAllOrders() throws OrderSystemException {
        verifyOrderList();
        return orders;
    }

    @Override
    public void updateOrder(Order original, Order updated) throws OrderSystemException {
        verifyOrderList();
        Order foundOrder = null;
        for (Order order : orders) {
            if(order.equals(original)){
                foundOrder = order;
                break;
            }
        }
        if(null == foundOrder){
            throw new OrderSystemException("Original record not found for update.");
        }
        foundOrder.setOrderNumber(updated.getOrderNumber());
        foundOrder.setOrderDate(updated.getOrderDate());
        foundOrder.setVendorID(updated.getVendorID());
        saveToFile();
    }

    @Override
    public void deleteOrder(Order order) throws OrderSystemException {
        deleteOrder(order.getOrderNumber());
    }

    @Override
    public void deleteOrder(String orderNumber) throws OrderSystemException {
        verifyOrderList();
        Order foundOrder = null;
        for (Order order : orders) {
            if(order.getOrderNumber().equals(orderNumber)){
                foundOrder = order;
                break;
            }
        }
        if(null == foundOrder){
            throw new OrderSystemException("Record record not found for delete.");
        }
        orders.remove(foundOrder);
        saveToFile();
    }

}
