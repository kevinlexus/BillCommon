package com.ric.cmn.excp;

/**
 * Exception возникающий если получен пустой идентификатор
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class EmptyId extends Exception {

	public EmptyId(String message) {
        super(message);
    }
}
