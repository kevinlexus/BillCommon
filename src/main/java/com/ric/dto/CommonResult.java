package com.ric.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Результат выполнения потока
 * @author Lev
 *
 */
public class CommonResult {
	long klskId;
	Integer err;

	public CommonResult(long klskId, Integer err) {
		this.klskId = klskId;
		this.err = err;
	}

	public long getLsk() {
		return klskId;
	}

	public void setKlskId(int klskId) {
		this.klskId = klskId;
	}

	public Integer getErr() {
		return err;
	}

	public void setErr(Integer err) {
		this.err = err;
	}
}
