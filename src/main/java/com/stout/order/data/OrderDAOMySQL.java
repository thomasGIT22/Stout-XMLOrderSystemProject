/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stout.order.data;

import com.stout.order.Order;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;

/**
 *
 * @author ragbr
 */
public class OrderDAOMySQL implements OrderSystemDAO {

    private static ArrayList<Order> orders;

    private Connection buildConnection() throws SQLException {
        String databaseUrl = "localhost";
        String databasePort = "3306";
        String databaseName = "orders";
        String userName = "root";
        String password = "password";

        String connectionString = "jdbc:mysql://" + databaseUrl + ":"
                + databasePort + "/" + databaseName + "?"
                + "user=" + userName + "&"
                + "password=" + password + "&"
                + "useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(connectionString);
    }
    
    private void readFromDatabase() throws OrderSystemException {
        try ( Connection connection = buildConnection()) {
            if (connection.isValid(2)) {
                orders = new ArrayList<>();
                Statement statement = connection.createStatement();
                CallableStatement callableStatement = 
               connection.prepareCall("CALL sp_get_all_Orders();");
            ResultSet resultSet = callableStatement.executeQuery();
            String orderNumber;
            LocalDate orderDate;
            int vendorID;
            while(resultSet.next()){
                orderNumber =resultSet.getString("Order_Number");
                orderDate = resultSet.getDate("Order_Date").toLocalDate();
                vendorID = resultSet.getInt("Vendor_ID");
                orders.add(new Order(orderNumber, orderDate, vendorID));
            }
            resultSet.close();
            callableStatement.close();
            }
        } catch (Exception exception) {
            System.out.println("Exception message: " + exception.getMessage());
            if (exception instanceof SQLException) {
                SQLException sqlException = (SQLException) exception;
                System.out.println("Error Code: " + sqlException.getErrorCode());
                System.out.println("SQL State: " + sqlException.getSQLState());
            }
        }
    }

    private void verifyOrderList() throws OrderSystemException {
        if (null == orders) {
            readFromDatabase();
        }
    }

    @Override
    public void createOrderRecord(Order order) throws OrderSystemException {
    // Verifies that the car isn't in the ArrayList before adding it
    verifyOrderList();
    Order checkOrder = getOrderByNumber(order.getOrderNumber());
    if(null != checkOrder){
        throw new OrderSystemException("Order Numbers must be unique.");
    }
    orders.add(order);
    // Creates new car record in the database
    try{
        Connection conn = buildConnection();
        CallableStatement callableStatement
                = conn.prepareCall("CALL sp_add_Order(?,?,?);");
        callableStatement.setString(1, order.getOrderNumber());
        callableStatement.setString(2, order.getOrderDate().toString());
        callableStatement.setInt(3, order.getVendorID());

        callableStatement.execute();
        callableStatement.close();
        conn.close();

    } catch(SQLException ex){
        throw new OrderSystemException(ex);
    }
}

    @Override
    public Order getOrderByNumber(String orderNumber) throws OrderSystemException {
    Order order = null;
    // Use this code if you want to directly look the car up from a database query
    try{
        Connection conn = buildConnection();
        CallableStatement callableStatement
                = conn.prepareCall("CALL sp_get_Order_by_Order_Number(?);");
        callableStatement.setString(1, orderNumber);

        ResultSet resultSet = callableStatement.executeQuery();
        LocalDate orderDate;
        int vendorID;
        if(resultSet.next()){
            orderNumber = resultSet.getString("Order_Number");
            orderDate = resultSet.getDate("Order_Date").toLocalDate();
            vendorID = resultSet.getInt("Vendor_ID");
            order = new Order(orderNumber, orderDate, vendorID);
        }
        callableStatement.close();
        conn.close();

    } catch(SQLException ex){
        throw new OrderSystemException(ex);
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
    // Verify that the original car is in the ArrayList before updating it
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
    foundOrder.setOrderDate(updated.getOrderDate());
    foundOrder.setVendorID(updated.getVendorID());
    // Update the car in the database
    try{
        Connection conn = buildConnection();
        CallableStatement callableStatement
                = conn.prepareCall("CALL sp_update_Order(?,?,?,?);");
        callableStatement.setString(1, original.getOrderNumber());
        callableStatement.setString(2, updated.getOrderNumber());
        callableStatement.setString(3, updated.getOrderDate().toString());
        callableStatement.setInt(4, updated.getVendorID());

        callableStatement.execute();
        callableStatement.close();
        conn.close();

    } catch(SQLException ex){
        throw new OrderSystemException(ex);
    }
}

@Override
public void deleteOrder(Order order) throws OrderSystemException {
    deleteOrder(order.getOrderNumber());
}

@Override
public void deleteOrder(String orderNumber) throws OrderSystemException {
    // Verify that the car is in the ArrayList before removing it
    verifyOrderList();
    Order foundOrder = null;
    for (Order order : orders) {
        if(order.getOrderNumber().equals(orderNumber)){
            foundOrder = order;
            break;
        }
    }
    if(null == foundOrder){
        throw new OrderSystemException("Record not found for delete.");
    }
    String orderNumberToDelete = foundOrder.getOrderNumber();
    orders.remove(foundOrder);
    // Delete the car from the database
    try{
        Connection conn = buildConnection();
        CallableStatement callableStatement
                = conn.prepareCall("CALL sp_delete_from_Order(?);");
        callableStatement.setString(1, orderNumberToDelete);
        callableStatement.execute();
        callableStatement.close();
        conn.close();

    } catch(SQLException ex){
        throw new OrderSystemException(ex);
    }
}

}
