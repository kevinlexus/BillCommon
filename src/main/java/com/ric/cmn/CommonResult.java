package com.ric.cmn;

import lombok.Getter;
import lombok.Setter;

/**
 * Результат выполнения потока
 * @author Lev
 *
 */
@Getter @Setter
@lombok.AllArgsConstructor
public class CommonResult {
	String lsk;
	Integer err;
}
