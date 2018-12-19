package com.ric.cmn.excp;

/**
 * Exception - неиспользуемый код (например ошибки)
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class UnusableCode extends Exception {

	public UnusableCode(String message) {
        super(message);
    }
}
