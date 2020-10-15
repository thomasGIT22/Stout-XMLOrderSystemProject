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
public class OrderSystemException extends Exception {

    /**
     * Creates a new instance of <code>OrderSystemException</code> without detail
     * message.
     */
    public OrderSystemException() {
    }

    /**
     * Constructs an instance of <code>OrderSystemException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OrderSystemException(String msg) {
        super(msg);
    }

    public OrderSystemException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OrderSystemException(Throwable cause) {
        super(cause);
    }

    public OrderSystemException(String msg, Throwable cause
            , boolean enableSuppression, boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
    }

}
