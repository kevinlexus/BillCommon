package com.ric.cmn.excp;

/**
 * Exception возникающий если произошла ошибка при выполнении начисления пени
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class ErrorWhileChrgPen  extends Exception {

	public ErrorWhileChrgPen(String message) {
        super(message);
    }
}
