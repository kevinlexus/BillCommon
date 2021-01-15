package com.ric.cmn.excp;

/**
 * Exception возникающий если произошла ошибка при выполнении начисления
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class ErrorWhileLoad extends Exception {

	public ErrorWhileLoad(String message) {
        super(message);
    }
}
