/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stout.order.data;

import com.stout.order.Order;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ragbr
 */
public class OrderSystemDAOXML implements OrderSystemDAO {

    private static final String FILE_NAME = "orders.xml";
    private static ArrayList<Order> orders;

    private void readFromFile() throws OrderSystemException {
        try ( InputStream inputStream = new FileInputStream(FILE_NAME)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(inputStream);

            NodeList orderNodeList = document.getElementsByTagName("order");
            orders = new ArrayList<>();
            for (int i = 0; i < orderNodeList.getLength(); i++) {
                Node currentOrderNode = orderNodeList.item(i);
                orders.add(buildOrderFromNode(currentOrderNode));
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private static Order buildOrderFromNode(Node orderNode) {
        Order newOrder = new Order();

        NamedNodeMap orderAttributeMap = orderNode.getAttributes();
        Attr attr = (Attr) orderAttributeMap.getNamedItem("order-number");
        newOrder.setOrderNumber(attr.getValue());

        NodeList orderDataNodeList = orderNode.getChildNodes();
        for (int i = 0; i < orderDataNodeList.getLength(); i++) {
            Node dataNode = orderDataNodeList.item(i);
            if (dataNode instanceof Element) {
                Element dataElement = (Element) dataNode;
                switch (dataElement.getTagName()) {
                    case "orderDate":
                        String OrderDateValue = dataElement.getTextContent();
                        newOrder.setOrderDate(LocalDate.parse(OrderDateValue));
                        break;
                    case "vendorID":
                        int vendorIDValue = Integer.parseInt(dataElement.getTextContent());
                        newOrder.setVendorID(vendorIDValue);
                        break;
                    /*case "orderNumber":
                        String orderNumberValue = dataElement.getTextContent();
                        newOrder.setOrderNumber(orderNumberValue);
                        break;*/
                    default:
                        break;
                }
            }
        }
        return newOrder;
    }

    private void saveToFile() throws OrderSystemException {
        try ( FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
            // output file automatically closes after the try block is 
            // completed.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element rootElement = document.createElement("order");
            document.appendChild(rootElement);

            /*Element orderElement = document.createElement("order");
            rootElement.appendChild(orderElement);*/

            for (Order currentOrder : orders) {
                DocumentFragment orderFragment = buildOrderFragment(document, currentOrder);
                rootElement.appendChild(orderFragment);
            }

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.transform(source, new StreamResult(fos));

        } catch (Exception ex) {
            throw new OrderSystemException(ex);
        }
    }

    private static DocumentFragment buildOrderFragment(Document document, Order order) {
        DocumentFragment orderFragment = document.createDocumentFragment();

        Element orderElement = document.createElement("order");
        orderElement.setAttribute("order-number", order.getOrderNumber().toString());
        
        LocalDate localDate = order.getOrderDate();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String dateSwitchString = localDate.format(formatter);
        
        Element orderDateElement = document.createElement("orderDate");
        orderDateElement.setTextContent(dateSwitchString);
        orderElement.appendChild(orderDateElement);

        Element modelElement = document.createElement("vendorID");
        modelElement.setTextContent(Integer.toString(order.getVendorID()));
        orderElement.appendChild(modelElement);

        Element orderNumberElement = document.createElement("orderNumber");
        orderNumberElement.setTextContent(order.getOrderNumber());
        orderElement.appendChild(orderNumberElement);

        orderFragment.appendChild(orderElement);

        return orderFragment;
    }

    private void verifyOrderList() throws OrderSystemException {
        if (null == orders) {
            readFromFile();
        }
    }

    @Override
    public void createOrderRecord(Order order) throws OrderSystemException {
        verifyOrderList();
        // Look to see if there is already a car with the same license plate
        // value
        Order checkOrder = getOrderByNumber(order.getOrderNumber());
        // If there was a matching car, throw an exception.  The license plate
        // is used as a unique identifier in this example.
        if (null != checkOrder) {
            throw new OrderSystemException("License Plates must be unique.");
        }
        // No other car has the same license plate, so we can add this Car to
        // the data store.
        orders.add(order);
        saveToFile();
    }

    @Override
    public Order getOrderByNumber(String orderNumber) throws OrderSystemException {
        verifyOrderList();
        Order order = null;
        for (Order order1 : orders) {
            // See if the car has a matching license plate
            if (order1.getOrderNumber().equals(orderNumber)) {
                // found a match, so it is the car we want
                order = order1;
                // leave the loop
                break;
            }
        }
        return order;
    }

    /**
     *
     * @return
     * @throws OrderSystemException
     */
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
            if (order.equals(original)) {
                // found a match!
                foundOrder = order;
                break;
            }
        }
        if (null == foundOrder) {
            // did not find a match to the original!
            throw new OrderSystemException("Original record not found for update.");
        }
        // If no error, update all but the unique identifier
        foundOrder.setOrderDate(updated.getOrderDate());
        foundOrder.setVendorID(updated.getVendorID());
        foundOrder.setOrderNumber(updated.getOrderNumber());
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
            if (order.getOrderNumber().equals(orderNumber)) {
                foundOrder = order;
                break;
            }
        }
        if (null == foundOrder) {
            // did not find a match to the original!
            throw new OrderSystemException("Record not found for delete.");
        }
        // If no error, update all but the unique identifier
        orders.remove(foundOrder);
        saveToFile();
    }
}