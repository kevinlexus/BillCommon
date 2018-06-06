package com.ric.cmn.excp;

/**
 * Exception возникающий если задана пустая услуга 
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class ErrorWhileDistDeb  extends Exception {

	public ErrorWhileDistDeb(String message) {
        super(message);
    }
}
