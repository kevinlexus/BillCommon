package com.ric.cmn.excp;

/**
 * Exception возникающий во время формирования
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class ErrorWhileGen  extends Exception {

	public ErrorWhileGen(String message) {
        super(message);
    }
}
