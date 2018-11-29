package com.ric.cmn.excp;

/**
 * Exception возникающий при получении разных klsk по одному адресу
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class DifferentKlskBySingleAdress extends Exception {

	public DifferentKlskBySingleAdress(String message) {
        super(message);
    }
}
