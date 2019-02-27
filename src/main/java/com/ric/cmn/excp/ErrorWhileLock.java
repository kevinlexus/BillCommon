package com.ric.cmn.excp;

/**
 * Exception возникающий если произошла ошибка при попытке блокировки объекта
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class ErrorWhileLock extends Exception {

	public ErrorWhileLock(String message) {
        super(message);
    }
}
