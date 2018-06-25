package com.ric.cmn.excp;

/**
 * Exception возникающий если счетчики зациклены в графе, либо вложенность графа слишком высокая
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class CyclicMeter  extends Exception {

	public CyclicMeter(String message) {
        super(message);
    }
}
