package com.ric.cmn.excp;

/**
 * Exception возникающий при невозможности блокировки лицевого счета, дома и другого объекта,
 * по которому нужен эксклюзивный доступ
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class CantLock  extends Exception {

	public CantLock(String message) {
        super(message);
    }
}
