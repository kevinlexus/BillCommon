package com.ric.cmn.excp;

/**
 * Некорректная таблица
 * @author lev
 *
 */
public class WrongTableException extends Exception {

	public WrongTableException(String message) {
		super(message);
	}

}
