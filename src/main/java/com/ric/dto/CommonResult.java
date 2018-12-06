package com.ric.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Результат выполнения потока
 * @author Lev
 *
 */
public class CommonResult {
	String lsk;
	Integer err;

	public CommonResult(String lsk, Integer err) {
		this.lsk = lsk;
		this.err = err;
	}

	public String getLsk() {
		return lsk;
	}

	public void setLsk(String lsk) {
		this.lsk = lsk;
	}

	public Integer getErr() {
		return err;
	}

	public void setErr(Integer err) {
		this.err = err;
	}
}
