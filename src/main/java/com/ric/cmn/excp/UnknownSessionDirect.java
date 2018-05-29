package com.ric.cmn.excp;

/**
 * Exception возникающий если не заполнена сессия от Директа
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class UnknownSessionDirect  extends Exception {

	public UnknownSessionDirect(String message) {
        super(message);
    }
}
