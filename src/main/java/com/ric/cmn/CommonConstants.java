package com.ric.cmn;

import java.math.BigDecimal;

/**
 * Общие константы
 * @version 1.1
 */
public interface CommonConstants {

    // маркер, для проверки необходимости остановки потоков начисления и прочего
    // (только если весь фонд задан для расчета)
    static final String stopMark = "processMng.genProcess";
    // маркер для остановки процесса итогового формирования
    static final String stopMarkAmntGen = "AmountGeneration";

    // норматив по эл.энерг. ОДН в доме без лифта на м2 общ.имущ.
    static final BigDecimal ODN_EL_NORM = new BigDecimal("2.7");
    // норматив по эл.энерг. ОДН в доме с лифтом на м2 общ.имущ.
    static final BigDecimal ODN_EL_NORM_WITH_LIFT = new BigDecimal("4.1");

}
