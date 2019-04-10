package com.ric.cmn.excp;

/**
 * Exception возникающий если происходит ошибка в процессе распределения оплаты
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class ErrorWhileDistPay extends Exception {

	public ErrorWhileDistPay(String message) {
        super(message);
    }
}
